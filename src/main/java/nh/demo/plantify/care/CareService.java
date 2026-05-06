package nh.demo.plantify.care;

import nh.demo.plantify.care.suggestions.CareSuggestion;
import nh.demo.plantify.care.suggestions.CareSuggestionService;
import nh.demo.plantify.plant.PlantType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.UUID;

@Service
public class CareService {

    private static final Logger log = LoggerFactory.getLogger(CareService.class);

    private final CareTaskRepository careTaskRepository;
    private final ApplicationEventPublisher applicationEventPublisher;
    private final CareSuggestionService careSuggestionService;

    CareService(CareTaskRepository careTaskRepository, ApplicationEventPublisher applicationEventPublisher, CareSuggestionService careSuggestionService) {
        this.careTaskRepository = careTaskRepository;
        this.applicationEventPublisher = applicationEventPublisher;
        this.careSuggestionService = careSuggestionService;
    }

    @Transactional
    public void setupInitialCareTasks(UUID plantId, UUID ownerId, PlantType plantType, String location) {
        var suggestionsForPlant = careSuggestionService.getBestSuggestionsByPlantType(
            plantType,
            location
        );

        var careTasks = suggestionsForPlant
            .stream()
            .map(t -> createFromSuggestion(
                plantId,
                t
            ))
            .toList();

        var savedCareTasks = careTaskRepository.saveAll(careTasks);

        log.info("""
            
            
            🌱
            🌱 Initial Care Tasks created for plant '{}' (owner '{}')
            🌱
            
            """, plantId, ownerId);

//        applicationEventPublisher.publishEvent(
//            CareTasksScheduledEvent.of(plantId, ownerId, careTasks)
//        );
    }

    private CareTask createFromSuggestion(UUID plantId, CareSuggestion suggestion) {
        return switch (suggestion) {
            case CareSuggestion.OneTimeCareSuggestion s -> new CareTask(
                plantId,
                s.taskType(),
                CareTaskSource.SYSTEM,
                s.dueDate(),
                null  // kein Interval
            );
            case CareSuggestion.RecurringCareSuggestion s -> new CareTask(
                plantId,
                s.taskType(),
                CareTaskSource.SYSTEM,
                LocalDate.now().plusDays(s.intervalDays()),
                s.intervalDays()
            );
        };
    }
}

package nh.demo.plantify.care;

import nh.demo.plantify.plant.PlantType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.UUID;

@Service
public class CareTaskService {

    private static final Logger log = LoggerFactory.getLogger(CareTaskService.class);

    private final CareTaskRepository careTaskRepository;
    private final ApplicationEventPublisher applicationEventPublisher;
    private final CareTaskSuggestionService careTaskSuggestionService;

    CareTaskService(CareTaskRepository careTaskRepository, ApplicationEventPublisher applicationEventPublisher, CareTaskSuggestionService careTaskSuggestionService) {
        this.careTaskRepository = careTaskRepository;
        this.applicationEventPublisher = applicationEventPublisher;
        this.careTaskSuggestionService = careTaskSuggestionService;
    }

    @Transactional
    public void setupInitialCareTasks(UUID plantId, PlantType plantType, String location) {
        var suggestionsForPlant = careTaskSuggestionService.getBestSuggestionsByPlantType(
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

        log.info(" 🌱 Initial Care Tasks created for plant '{}'", plantId);
    }

    private CareTask createFromSuggestion(UUID plantId, CareTaskSuggestion suggestion) {
        return switch (suggestion) {
            case CareTaskSuggestion.OneTimeCareTaskSuggestion s -> new CareTask(
                plantId,
                s.taskType(),
                CareTaskSource.SYSTEM,
                s.dueDate(),
                null  // kein Interval
            );
            case CareTaskSuggestion.RecurringCareTaskSuggestion s -> new CareTask(
                plantId,
                s.taskType(),
                CareTaskSource.SYSTEM,
                LocalDate.now().plusDays(s.intervalDays()),
                s.intervalDays()
            );
        };
    }
}

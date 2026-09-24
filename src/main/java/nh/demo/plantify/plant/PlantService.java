package nh.demo.plantify.plant;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@Service
class PlantService {

    private static final Logger log = LoggerFactory.getLogger(PlantService.class);

    private final PlantRepository plantRepository;
    private final ApplicationEventPublisher applicationEventPublisher;

    PlantService(
        PlantRepository plantRepository,
        ApplicationEventPublisher applicationEventPublisher
    ) {
        this.plantRepository = plantRepository;
        this.applicationEventPublisher = applicationEventPublisher;
    }

    @Transactional
    Plant registerPlant(UUID ownerId, String name, PlantType plantType, String location) {

        // Pflanze in DB speichern
        var plant = new Plant(ownerId, name, plantType, location);
        plantRepository.save(plant);

        applicationEventPublisher
            .publishEvent(
                new PlantRegisteredEvent(
                    plant.getId(),
                    plant.getOwnerId(),
                    plant.getPlantType(),
                    plant.getLocation()
                )
            );

        // Care-Tasks anlegen
//        careService.setupInitialCareTasks(
//            plant.getId(),
//            plant.getOwnerId(),
//            plant.getPlantType(),
//            plant.getLocation()
//        );
//
//        // Einrichtungsgebühr berechnen
//        usageTracker.registerSetupFee(
//            plant.getId(),
//            plant.getOwnerId()
//        );

        //

        log.info("""
            
            
            ✅
            ✅ New plant registered '{}'
            ✅
            
            """, plant.getId());

        return plant;
    }

    public Optional<UUID> findOwnerForPlant(UUID plantId) {
        return plantRepository
            .findById(plantId)
            .map(Plant::getOwnerId)
            ;
    }

}
package nh.demo.plantify.plant;

import nh.demo.plantify.billing.UsageTracker;
import nh.demo.plantify.care.CareTaskService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@Service
class PlantService {

    private static final Logger log = LoggerFactory.getLogger(PlantService.class);

    private final PlantRepository plantRepository;
    private final CareTaskService careTaskService;
    private final UsageTracker usageTracker;

    PlantService(PlantRepository plantRepository, CareTaskService careTaskService, UsageTracker usageTracker) {
        this.plantRepository = plantRepository;
        this.careTaskService = careTaskService;
        this.usageTracker = usageTracker;
    }

    @Transactional
    Plant registerPlant(UUID ownerId, String name, PlantType plantType, String location) {

        // Pflanze in DB speichern
        var plant = new Plant(ownerId, name, plantType, location);
        plantRepository.save(plant);

        // Care-Tasks anlegen
        careTaskService.setupInitialCareTasks(
            plant.getId(),
            plant.getPlantType(),
            plant.getLocation()
        );

        // Einrichtungsgebühr berechnen
        usageTracker.registerSetupFee(
            plant.getId(),
            plant.getOwnerId()
        );

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
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

    // Nur noch eine technische Abhängigkeit im Konstruktor:
    PlantService(PlantRepository plantRepository, ApplicationEventPublisher applicationEventPublisher) {
        this.plantRepository = plantRepository;
        this.applicationEventPublisher = applicationEventPublisher;
    }

    @Transactional
    Plant registerPlant(UUID ownerId, String name, PlantType plantType, String location) {

        // Pflanze in DB speichern
        var plant = new Plant(ownerId, name, plantType, location);
        plantRepository.save(plant);

        // Entkopplung: statt der beiden direkten Aufrufe nur noch ein Event publishen
        //  -> IntelliJ kann zu den Listenern navigieren!
        applicationEventPublisher.publishEvent(new PlantRegisteredEvent(
            plant.getId(),
            plant.getOwnerId(),
            plant.getPlantType(),
            plant.getLocation()
        ));

        if (true) {
            // Der Fehler geht an den Client zurück ✅
            // Aber:
            //  UsageTracker und CareService haben (asynchron) schon committed 😢
            // Logs:
            //  mit Glück sieht man erst die Exception,
            //  dann die erfolgreiche Event-Verarbeitung
            throw new IllegalStateException("Schade...");
        }

        // Care-Tasks anlegen -> jetzt im Listener (CareService.onPlantRegistered)
//        careService.setupInitialCareTasks(
//            plant.getId(),
//            plant.getOwnerId(),
//            plant.getPlantType(),
//            plant.getLocation()
//        );

        // Einrichtungsgebühr berechnen -> jetzt im Listener (UsageTracker.onPlantCreated)
//        usageTracker.registerSetupFee(
//            plant.getId(),
//            plant.getOwnerId()
//        );

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
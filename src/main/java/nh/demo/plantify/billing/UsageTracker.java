package nh.demo.plantify.billing;

import nh.demo.plantify.plant.PlantRegisteredEvent;
import nh.demo.plantify.shared.CareTaskType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.modulith.events.ApplicationModuleListener;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.UUID;

@Service
public class UsageTracker {

    private static final Logger log = LoggerFactory.getLogger(UsageTracker.class);
    private final UsageRepository usageRepository;

    UsageTracker(UsageRepository usageRepository) {
        this.usageRepository = usageRepository;
    }

    // Die drei Annotationen in einer:
    //  @TransactionalEventListener + @Transactional(REQUIRES_NEW) + @Async
    @ApplicationModuleListener
    void onPlantCreated(PlantRegisteredEvent event) {
        if (false) {
            // 🤔 Was passiert, wenn hier was schiefgeht?
            // Vor dem Zeigen nochmal DB leer machen!
            //  - Tabellen "plants" und "care_tasks" befüllt ✅
            //  - usage_record nicht, wir bekommen also kein Geld 😢
            //
            // 🕵️‍♂️ event_publication-Tabelle
            //   - Zwei Einträge (pro Listener einer)
            //   - completion_date ansehen (einmal gesetzt, einmal NULL)
            throw new RuntimeException("Nö");
        }

        registerSetupFee(event.plantId(), event.ownerId());
    }

    private void registerSetupFee(UUID plantId, UUID ownerId) {
        UsageRecord usageRecord = new UsageRecord(
            ownerId,
            UsageRecord.UsageType.SETUP_FEE,
            Instant.now(),
            1000L
        );

        usageRepository.save(usageRecord);

        log.info("""
            
            
            🤑
            🤑 Setup Fee registered for plant '{}'
            🤑
            
            """, plantId);
    }

    long getCareTaskCostCents(CareTaskType taskType) {
        var result = switch (taskType) {
            case PRUNING -> 400L;
            case WATERING -> 50L;
            case REPOTTING -> 500L;
            case FERTILIZING -> 100L;
            case PEST_CONTROL -> 300;
        };

        return result;
    }

}

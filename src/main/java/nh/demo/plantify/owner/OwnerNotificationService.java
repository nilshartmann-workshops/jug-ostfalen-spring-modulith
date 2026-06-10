package nh.demo.plantify.owner;

//import nh.demo.plantify.care.CareTasksScheduledEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
//import org.springframework.modulith.events.ApplicationModuleListener;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class OwnerNotificationService {

    private static final Logger log = LoggerFactory.getLogger(OwnerNotificationService.class);

    // 🔗 Reagiert auf das Event, das der CareService aus seinem eigenen
    //    Listener heraus publisht -> plant -> care -> owner
//    @ApplicationModuleListener
//    void onCareTasksScheduled(CareTasksScheduledEvent event) {
//        notifyOwner(event.ownerId(), event.plantId(), event.taskTypes().size());
//    }

    void notifyOwner(UUID ownerId, UUID plantId, int taskCount) {
        log.info("""


            📬
            📬 Notifying owner '{}': {} care task(s) scheduled for plant '{}'
            📬

            """, ownerId, taskCount, plantId);
    }
}

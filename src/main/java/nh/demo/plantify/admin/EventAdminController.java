package nh.demo.plantify.admin;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.modulith.events.FailedEventPublications;
//import org.springframework.modulith.events.ResubmissionOptions;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/// Admin-Endpoint, um fehlgeschlagene Events zur Laufzeit neu zuzustellen –
/// ohne App-Neustart. Eigenes Modul `admin`, weil Resubmission die *gesamte*
/// Event Publication Registry betrifft und nicht zu einer Fachlichkeit gehört.
@RestController
@RequestMapping("/api/admin/failed-events")
class EventAdminController {

    private static final Logger log = LoggerFactory.getLogger(EventAdminController.class);

    private final FailedEventPublications failedEventPublications;

    EventAdminController(FailedEventPublications failedEventPublications) {
        this.failedEventPublications = failedEventPublications;
    }

    @PostMapping("/resubmit")
    void resubmitFailedEvents() {
        log.info("🔁 Resubmitting failed event publications ...");
//        failedEventPublications.resubmit(
//            ResubmissionOptions.defaults()
//                // In einer echten Anwendung NICHT alles auf einmal resubmitten,
//                // sondern drosseln (z.B. nach einem längeren Ausfall):
//                .withBatchSize(50)      // pro Lauf höchstens 50 Publications
//                .withMaxInFlight(200)   // nie mehr als 200 gleichzeitig "in flight"
//        );
    }
}

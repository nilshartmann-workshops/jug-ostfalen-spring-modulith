package nh.demo.plantify;

import org.junit.jupiter.api.Test;
import org.springframework.modulith.core.ApplicationModules;
import org.springframework.modulith.docs.Documenter;

public class PlantifyModuleTest {

    @Test
    void verify_modules() {
        var modules = ApplicationModules
            .of(PlantifyApplication.class);

        // Der Zyklus ist durch die Event-Entkopplung verschwunden
        // (plant ruft care nicht mehr direkt auf) -> kein Filter mehr nötig.
        modules.detectViolations()
            .throwIfPresent();
    }

    @Test
    void write_document() {

        var modules = ApplicationModules
            .of(PlantifyApplication.class);

        // Nochmal Doku erzeugen
        //  - Wir sehen jetzt:
        //    - depends on: verwendet Klassen
        //    - uses: Verwendet Services
        //    - listens to: Verwendet Events
        //
        // Das können wir gleich für die Tests benutzen


        new Documenter(modules)
            .writeDocumentation();

        // -> Packges im Workspace kurz vorstellen
        // -> components.puml
        //    -> Abhängigkeiten zeigen
        //    -> was das bedeutet sehen wir später
    }

}

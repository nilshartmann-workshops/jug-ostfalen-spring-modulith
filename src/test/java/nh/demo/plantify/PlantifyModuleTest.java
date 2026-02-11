package nh.demo.plantify;

import org.junit.jupiter.api.Test;
import org.springframework.modulith.core.ApplicationModules;
import org.springframework.modulith.docs.Documenter;

public class PlantifyModuleTest {

    @Test
    void write_document() {

        var modules = ApplicationModules
            .of(PlantifyApplication.class);

        new Documenter(modules)
            .writeDocumentation();

        // -> Packges im Workspace kurz vorstellen
        // -> components.puml
        //    -> Abhängigkeiten zeigen
        //    -> was das bedeutet sehen wir später
        //    -> 🕵️‍♂️ Wir haben zirkuläre Abhängigkeiten
    }

}

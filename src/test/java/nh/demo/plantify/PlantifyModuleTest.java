package nh.demo.plantify;

import org.junit.jupiter.api.Test;
import org.springframework.modulith.core.ApplicationModules;
import org.springframework.modulith.core.Violation;
import org.springframework.modulith.docs.Documenter;

import static java.util.function.Predicate.not;

public class PlantifyModuleTest {

    @Test
    void verify_modules() {
        var modules = ApplicationModules
            .of(PlantifyApplication.class);

        modules.detectViolations()
            .filter(not(this::isPlantCareModuleCycle))
            .throwIfPresent();
    }

    private boolean isPlantCareModuleCycle(Violation violation) {
        return violation.hasMessageContaining("Cycle detected")
               && violation.hasMessageContaining("Slice plant")
               && violation.hasMessageContaining("Slice care");

        // Message:
        //        Cycle detected:
        //          Slice care ->
        //          Slice plant ->
        //          Slice care
    }

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

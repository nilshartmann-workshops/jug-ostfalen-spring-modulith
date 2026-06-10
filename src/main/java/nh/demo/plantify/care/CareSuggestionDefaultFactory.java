package nh.demo.plantify.care;

import nh.demo.plantify.plant.PlantType;
import nh.demo.plantify.shared.CareTaskType;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Component
class CareSuggestionDefaultFactory implements CareSuggestionFactory {

    @Override
    public List<CareSuggestion> createSuggestion(PlantType plantType, String location) {
        // Nur Defaults: spezialisierte Implementierungen einer CareSuggestionFactory
        // sollten bessere Werte liefern (z.B. anhängig von PlantType und Location)
        return List.of(
            // Jede Pflanze einmal umtopfen
            new CareSuggestion.OneTimeCareSuggestion(CareTaskType.REPOTTING, 1, LocalDate.now().plusDays(1)),

            // Jede Pflanze alle fünf Tage wässern
            new CareSuggestion.RecurringCareSuggestion(CareTaskType.WATERING, 1, 5)
        );
    }
}

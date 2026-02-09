package nh.demo.plantify.care;

import nh.demo.plantify.plant.PlantType;
import nh.demo.plantify.shared.CareTaskType;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Component
class CareTaskSuggestionDefaultFactory implements CareTaskSuggestionFactory {

    @Override
    public List<CareTaskSuggestion> createSuggestion(PlantType plantType, String location) {
        // Nur Defaults: spezialisierte Implementierungen einer CareTaskSuggestionFactory
        // sollten bessere Werte liefern (z.B. anhängig von PlantType und Location)
        return List.of(
            // Jede Pflanze einmal umtopfen
            new CareTaskSuggestion.OneTimeCareTaskSuggestion(CareTaskType.REPOTTING, 1, LocalDate.now().plusDays(1)),

            // Jede Pflanze alle fünf Tage wässern
            new CareTaskSuggestion.RecurringCareTaskSuggestion(CareTaskType.WATERING, 1, 5)
        );
    }
}

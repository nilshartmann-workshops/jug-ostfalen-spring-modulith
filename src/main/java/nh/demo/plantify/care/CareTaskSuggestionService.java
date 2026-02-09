package nh.demo.plantify.care;

import nh.demo.plantify.plant.PlantType;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
class CareTaskSuggestionService {

    private final List<CareTaskSuggestionFactory> factories;

    CareTaskSuggestionService(List<CareTaskSuggestionFactory> factories) {
        this.factories = factories;
    }

    public List<CareTaskSuggestion> getBestSuggestionsByPlantType(PlantType plantType, String location) {
        return factories.stream()
            .flatMap(f -> f.createSuggestion(plantType, location).stream())
            .collect(Collectors.groupingBy(
                CareTaskSuggestion::taskType,
                Collectors.maxBy(Comparator.comparingInt(CareTaskSuggestion::confidence))
            ))
            .values().stream()
            .flatMap(Optional::stream)
            .toList();
    }


}

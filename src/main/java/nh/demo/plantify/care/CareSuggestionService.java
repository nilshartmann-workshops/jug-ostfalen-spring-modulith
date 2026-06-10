package nh.demo.plantify.care;

import nh.demo.plantify.plant.PlantType;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
class CareSuggestionService {

    private final List<CareSuggestionFactory> factories;

    CareSuggestionService(List<CareSuggestionFactory> factories) {
        this.factories = factories;
    }

    public List<CareSuggestion> getBestSuggestionsByPlantType(PlantType plantType, String location) {
        return factories.stream()
            .flatMap(f -> f.createSuggestion(plantType, location).stream())
            .collect(Collectors.groupingBy(
                CareSuggestion::taskType,
                Collectors.maxBy(Comparator.comparingInt(CareSuggestion::confidence))
            ))
            .values().stream()
            .flatMap(Optional::stream)
            .toList();
    }


}

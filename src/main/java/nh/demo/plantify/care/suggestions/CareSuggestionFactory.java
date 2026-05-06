package nh.demo.plantify.care.suggestions;

import nh.demo.plantify.plant.PlantType;

import java.util.List;

public interface CareSuggestionFactory {

    List<CareSuggestion> createSuggestion(PlantType plantType, String location);

}

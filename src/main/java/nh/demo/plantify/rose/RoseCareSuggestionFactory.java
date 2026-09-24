package nh.demo.plantify.rose;

import nh.demo.plantify.care.suggestions.CareSuggestion;
import nh.demo.plantify.care.suggestions.CareSuggestionFactory;
import nh.demo.plantify.care.suggestions.CareSuggestionService;
import nh.demo.plantify.owner.OwnerRepository;
import nh.demo.plantify.plant.PlantType;
import org.springframework.stereotype.Component;

import java.util.List;

// Package private, keine öffentliche Komponente
@Component
class RoseCareSuggestionFactory implements CareSuggestionFactory {

    @Override
    public List<CareSuggestion> createSuggestion(PlantType plantType, String location) {
        return List.of();
    }
}

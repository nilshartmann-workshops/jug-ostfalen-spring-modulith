package nh.demo.plantify.rose;

import nh.demo.plantify.care.CareService;
import nh.demo.plantify.care.suggestions.CareSuggestion;
import nh.demo.plantify.care.suggestions.CareSuggestionFactory;
import nh.demo.plantify.plant.PlantType;
import org.springframework.stereotype.Component;

import java.util.List;

// Package private, keine öffentliche Komponente
@Component
class RoseCareSuggestionFactory implements CareSuggestionFactory {
//                                  ^--- IntelliJ Bug, wird weiterhin als Fehler angezeigt, Test ist grün
//                                                    ^--- Problem: internes API

    private CareService careService;
//                      ^--- Korrekt nicht verlaubt: CareService ist zwar public,
//                           aber nicht Bestandteil des NamedInterfaces
//                           das wir als einzige erlaubte Abhängigkeit
//                           eingetragen haben --> Test!


    @Override
    public List<CareSuggestion> createSuggestion(PlantType plantType, String location) {
//                      ^--- IntelliJ Bug, wird weiterhin als Fehler angezeigt, Test ist grün
//                      ^--- Problem: internes API

        return List.of();
    }
}

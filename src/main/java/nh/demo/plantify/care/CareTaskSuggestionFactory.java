package nh.demo.plantify.care;

import nh.demo.plantify.plant.PlantType;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

public interface CareTaskSuggestionFactory {

    List<CareTaskSuggestion> createSuggestion(PlantType plantType, String location);

}


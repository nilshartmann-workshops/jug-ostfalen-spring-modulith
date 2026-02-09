package nh.demo.plantify.plant;

import jakarta.validation.constraints.NotNull;
import nh.demo.plantify.shared.ResourceNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/plants")
class PlantController {

    private static final Logger log = LoggerFactory.getLogger(PlantController.class);

    private final PlantService plantService;
    private final PlantRepository plantRepository;

    PlantController(PlantService plantService, PlantRepository plantRepository) {
        this.plantService = plantService;
        this.plantRepository = plantRepository;
    }

    record NewPlantRequest(UUID ownerId, String name, PlantType plantType, String location) {
    }

    @GetMapping("/{plantId}")
    PlantDto getPlantById(@PathVariable UUID plantId) {
        return plantRepository
            .findById(plantId)
            .map(PlantDto::of)
            .orElseThrow(() -> new ResourceNotFoundException(Plant.class, plantId));

    }

    @PostMapping
    PlantDto addPlant(@RequestBody NewPlantRequest request) {
        log.info("Adding Plant from request {}", request);

        var newPlant = plantService.registerPlant(
            request.ownerId(),
            request.name(),
            request.plantType(),
            request.location()
        );

        log.info("🌻 New Plant registered: {}", newPlant);

        return PlantDto.of(newPlant);

    }

    /**
     * Mehr oder minder identisch mit Plant, Sinn eines eigenen DTOs hier zweifelhaft, aber für Demo benötigt
     */
    static record PlantDto(@NotNull UUID id, @NotNull UUID ownerId, @NotNull String name, @NotNull String location) {

        static PlantDto of(Plant plant) {
            return new PlantDto(
                plant.getId(),
                plant.getOwnerId(),
                plant.getName(),
                plant.getLocation()
            );
        }


    }
}
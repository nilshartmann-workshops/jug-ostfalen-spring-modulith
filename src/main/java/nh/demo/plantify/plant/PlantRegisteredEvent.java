package nh.demo.plantify.plant;

import nh.demo.plantify.plant.PlantType;

import java.util.UUID;

public record PlantRegisteredEvent(UUID plantId, UUID ownerId, PlantType plantType, String location) {
}
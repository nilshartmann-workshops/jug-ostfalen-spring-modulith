package nh.demo.plantify.shared;

import java.util.UUID;

public class ResourceNotFoundException extends RuntimeException {

    private final String resourceType;
    private final UUID resourceId;

    public ResourceNotFoundException(Class<?> resourceType, UUID resourceId) {
        super("Resource '%s' not found with id '%s'".formatted(resourceType.getName(), resourceId));
        this.resourceType = resourceType.getName();
        this.resourceId = resourceId;
    }

    public String getResourceType() {
        return resourceType;
    }

    public UUID getResourceId() {
        return resourceId;
    }

}

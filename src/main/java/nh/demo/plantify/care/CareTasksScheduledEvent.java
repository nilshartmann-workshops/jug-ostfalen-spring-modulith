package nh.demo.plantify.care;

import nh.demo.plantify.shared.CareTaskType;

import java.util.List;
import java.util.UUID;

public record CareTasksScheduledEvent(UUID plantId, UUID ownerId, List<CareTaskType> taskTypes) {

    /// Baut das Event aus den gerade angelegten Care-Tasks.
    /// Package-private, weil `CareTask` das Modul nicht verlässt --
    /// nach außen geht nur der `CareTaskType`.
    static CareTasksScheduledEvent of(UUID plantId, UUID ownerId, List<CareTask> careTasks) {
        return new CareTasksScheduledEvent(
            plantId,
            ownerId,
            careTasks.stream().map(CareTask::getType).toList()
        );
    }
}

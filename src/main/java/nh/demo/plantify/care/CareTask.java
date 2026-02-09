package nh.demo.plantify.care;

import jakarta.persistence.*;
import nh.demo.plantify.shared.CareTaskType;

import java.time.LocalDate;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "care_tasks", schema = "care_schema")
class CareTask {

    @Id
    private UUID id;

    @Column(name = "plant_id", nullable = false)
    private UUID plantId;

    @Column(name = "active", nullable = false)
    private boolean active;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "type", nullable = false)
    private CareTaskType type;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "source", nullable = false)
    private CareTaskSource source;

    @Column(name = "interval")
    private Integer interval;

    @Column(name = "next_due_date", nullable = false)
    private LocalDate nextDueDate;

    protected CareTask() {}

    public CareTask(UUID plantId, CareTaskType type, CareTaskSource source, LocalDate nextDueDate, Integer interval) {
        this.id = UUID.randomUUID();
        this.plantId = Objects.requireNonNull(plantId);
        this.type = Objects.requireNonNull(type);
        this.source = Objects.requireNonNull(source);

        this.nextDueDate = Objects.requireNonNull(nextDueDate);
        this.interval = interval;
        this.active = true;
    }

    /**
     * Markiert den Task als erledigt.
     * - Einmaliger Task (interval == null): wird deaktiviert
     * - Wiederkehrender Task: nextDueDate wird um das Intervall verschoben
     */
    public void complete() {
        if (!this.active) {
            throw new IllegalStateException("Cannot complete an inactive task");
        }

        if (isRecurring()) {
            this.nextDueDate = this.nextDueDate.plusDays(this.interval);
        } else {
            this.active = false;
        }
    }

    public UUID getId() {
        return id;
    }

    public UUID getPlantId() {
        return plantId;
    }

    public CareTaskType getType() {
        return type;
    }

    public boolean isRecurring() {
        return this.interval != null;
    }

    public CareTaskSource getSource() {
        return source;
    }

    public LocalDate getNextDueDate() {
        return nextDueDate;
    }

    public boolean isActive() {
        return active;
    }

    public Integer getInterval() {
        return interval;
    }
}

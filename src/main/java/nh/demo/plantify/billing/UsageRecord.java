package nh.demo.plantify.billing;

import jakarta.persistence.*;

import java.time.Instant;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(
    schema = "billing_schema",
    name = "usage_records"
)
class UsageRecord {

    public enum UsageType {
        SETUP_FEE,
        CARE_TASK_COMPLETED,
        STORAGE_COMPLETED
    }

    @Id
    private UUID id;
    
    @Column(name = "owner_id", nullable = false)
    private UUID ownerId;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "usage_type", nullable = false)
    private UsageType usageType;
    
    @Column(name = "recorded_at", nullable = false)
    private Instant recordedAt;

    @Column(name = "cost_cents", nullable = false)
    private long costCents;

    protected UsageRecord() {}

    public UsageRecord(UUID ownerId, UsageType usageType, Instant recordedAt, long costCents) {
        this.id = UUID.randomUUID();
        this.ownerId = Objects.requireNonNull(ownerId);
        this.usageType = Objects.requireNonNull(usageType);
        this.recordedAt = Objects.requireNonNull(recordedAt);
        this.costCents = costCents;
    }

    public UUID getId() { return id; }

    public UUID getOwnerId() {
        return ownerId;
    }

    public UsageType getUsageType() { return usageType; }
    public Instant getRecordedAt() { return recordedAt; }

    public long getCostCents() {
        return costCents;
    }
}
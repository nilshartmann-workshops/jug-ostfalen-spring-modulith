package nh.demo.plantify.billing;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

interface UsageRepository extends Repository<UsageRecord, UUID> {

    UsageRecord save(UsageRecord usageRecord);

    List<UsageRecord> findByOwnerId(UUID ownerId);

    @Query("SELECT DISTINCT u.ownerId FROM UsageRecord u WHERE u.recordedAt BETWEEN :start AND :end")
    List<UUID> findOwnerIdsBetween(Instant start, Instant end);

    @Query("SELECT sum (u.costCents) FROM UsageRecord u WHERE u.ownerId = :ownerId AND u.recordedAt BETWEEN :start AND :end")
    long getTotalCostsForOwnerRecordedBetween(
        UUID ownerId, Instant start, Instant end
    );

    @Query("SELECT u FROM UsageRecord u WHERE u.ownerId = :ownerId AND u.recordedAt BETWEEN :start AND :end")
    List<UsageRecord> getUsagesForOwnerRecordedBetween(
        UUID ownerId, Instant start, Instant end
    );
}
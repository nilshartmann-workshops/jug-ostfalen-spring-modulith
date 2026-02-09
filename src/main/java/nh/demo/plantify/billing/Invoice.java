package nh.demo.plantify.billing;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.util.List;
import java.util.UUID;

record Invoice(
    UUID id,
    LocalDateTime createdAt,
    UUID ownerId,
    String ownerName,
    YearMonth billingPeriod,
    BigDecimal amount,
    List<BillingItem> billingItems
) {

    record BillingItem(
        String description,
        BigDecimal amount
    ) {
    }
}




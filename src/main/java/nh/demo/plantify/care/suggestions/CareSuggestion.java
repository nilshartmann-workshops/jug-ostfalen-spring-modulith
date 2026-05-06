package nh.demo.plantify.care.suggestions;

import nh.demo.plantify.shared.CareTaskType;

import java.time.LocalDate;

public sealed interface CareSuggestion
    permits CareSuggestion.OneTimeCareSuggestion, CareSuggestion.RecurringCareSuggestion {

    CareTaskType taskType();
    int confidence();

    // Would be better to have thos default implementation in their own
    // files, but for demo purposes it's easier to have them here
    record OneTimeCareSuggestion(
        CareTaskType taskType,
        int confidence,
        LocalDate dueDate
    ) implements CareSuggestion {
    }

    record RecurringCareSuggestion(
        CareTaskType taskType,
        int confidence,
        int intervalDays
    ) implements CareSuggestion {
    }
}

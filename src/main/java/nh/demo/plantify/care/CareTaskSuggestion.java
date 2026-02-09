package nh.demo.plantify.care;

import nh.demo.plantify.shared.CareTaskType;

import java.time.LocalDate;

sealed interface CareTaskSuggestion
    permits CareTaskSuggestion.OneTimeCareTaskSuggestion, CareTaskSuggestion.RecurringCareTaskSuggestion {

    CareTaskType taskType();
    int confidence();

    // Would be better to have thos default implementation in their own
    // files, but for demo purposes it's easier to have them here
    record OneTimeCareTaskSuggestion(
        CareTaskType taskType,
        int confidence,
        LocalDate dueDate
    ) implements CareTaskSuggestion {
    }

    record RecurringCareTaskSuggestion(
        CareTaskType taskType,
        int confidence,
        int intervalDays
    ) implements CareTaskSuggestion {
    }
}
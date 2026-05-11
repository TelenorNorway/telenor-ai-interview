package no.telenor.ai.interview.support;

import java.time.LocalDateTime;

public record SupportCaseDto(
        Long id,
        String caseNumber,
        String title,
        String description,
        SupportCaseStatus status,
        int priority,
        LocalDateTime openedAt,
        LocalDateTime updatedAt
) {
}

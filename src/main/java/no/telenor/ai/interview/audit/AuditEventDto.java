package no.telenor.ai.interview.audit;

import java.time.LocalDateTime;

public record AuditEventDto(
        Long id,
        String customerNumber,
        String eventType,
        String description,
        LocalDateTime createdAt,
        String createdBy
) {
}

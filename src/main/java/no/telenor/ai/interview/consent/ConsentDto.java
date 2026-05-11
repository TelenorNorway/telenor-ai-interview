package no.telenor.ai.interview.consent;

import java.time.LocalDateTime;

public record ConsentDto(
        Long id,
        ConsentCategory category,
        boolean granted,
        LocalDateTime capturedAt,
        String channel
) {
}

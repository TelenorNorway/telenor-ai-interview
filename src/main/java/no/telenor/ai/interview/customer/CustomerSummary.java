package no.telenor.ai.interview.customer;

public record CustomerSummary(
        Long id,
        String customerNumber,
        String firstName,
        String lastName,
        String email,
        CustomerStatus status,
        int riskScore
) {
}

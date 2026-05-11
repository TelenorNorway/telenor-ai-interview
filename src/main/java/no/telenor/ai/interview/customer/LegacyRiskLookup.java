package no.telenor.ai.interview.customer;

public record LegacyRiskLookup(
        String customerNumber,
        String riskBand,
        String lookupUri
) {
}

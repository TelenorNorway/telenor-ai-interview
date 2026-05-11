package no.telenor.ai.interview.customer;

import java.time.LocalDate;

public record CustomerImportRow(
        String customerNumber,
        String firstName,
        String lastName,
        LocalDate dateOfBirth,
        String email,
        String phoneNumber,
        String streetAddress,
        String note
) {
}

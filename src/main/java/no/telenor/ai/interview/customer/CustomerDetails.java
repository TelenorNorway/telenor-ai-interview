package no.telenor.ai.interview.customer;

import no.telenor.ai.interview.account.AccountDto;
import no.telenor.ai.interview.consent.ConsentDto;
import no.telenor.ai.interview.support.SupportCaseDto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public record CustomerDetails(
        Long id,
        String customerNumber,
        String firstName,
        String lastName,
        LocalDate dateOfBirth,
        String email,
        String phoneNumber,
        CustomerStatus status,
        int riskScore,
        LocalDateTime createdAt,
        LocalDateTime updatedAt,
        List<AddressDto> addresses,
        List<AccountDto> accounts,
        List<ConsentDto> consents,
        List<SupportCaseDto> supportCases
) {
}

package no.telenor.ai.interview.account;

import java.math.BigDecimal;
import java.time.LocalDate;

public record AccountDto(
        Long id,
        String accountNumber,
        AccountType type,
        AccountStatus status,
        BigDecimal balance,
        LocalDate openedDate
) {
}

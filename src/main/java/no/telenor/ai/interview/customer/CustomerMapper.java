package no.telenor.ai.interview.customer;

import no.telenor.ai.interview.account.Account;
import no.telenor.ai.interview.account.AccountDto;
import no.telenor.ai.interview.consent.Consent;
import no.telenor.ai.interview.consent.ConsentDto;
import no.telenor.ai.interview.support.SupportCase;
import no.telenor.ai.interview.support.SupportCaseDto;

import java.util.List;
import java.util.stream.Collectors;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Component;

@Component
public class CustomerMapper {

    public CustomerSummary toSummary(Customer customer) {
        return new CustomerSummary(
                customer.getId(),
                customer.getCustomerNumber(),
                customer.getFirstName(),
                customer.getLastName(),
                customer.getEmail(),
                customer.getStatus(),
                customer.getRiskScore()
        );
    }

    public CustomerDetails toDetails(Customer customer) {
        return new CustomerDetails(
                customer.getId(),
                customer.getCustomerNumber(),
                customer.getFirstName(),
                customer.getLastName(),
                customer.getDateOfBirth(),
                customer.getEmail(),
                customer.getPhoneNumber(),
                customer.getStatus(),
                customer.getRiskScore(),
                customer.getCreatedAt(),
                customer.getUpdatedAt(),
                mapAddresses(customer.getAddresses()),
                mapAccounts(customer.getAccounts()),
                mapConsents(customer.getConsents()),
                mapSupportCases(customer.getSupportCases())
        );
    }

    public AccountDto toAccountDto(Account account) {
        return new AccountDto(
                account.getId(),
                account.getAccountNumber(),
                account.getType(),
                account.getStatus(),
                account.getBalance(),
                account.getOpenedDate()
        );
    }

    public SupportCaseDto toSupportCaseDto(SupportCase supportCase) {
        return new SupportCaseDto(
                supportCase.getId(),
                supportCase.getCaseNumber(),
                supportCase.getTitle(),
                supportCase.getDescription(),
                supportCase.getStatus(),
                supportCase.getPriority(),
                supportCase.getOpenedAt(),
                supportCase.getUpdatedAt()
        );
    }

    private List<AddressDto> mapAddresses(List<Address> addresses) {
        return CollectionUtils.emptyIfNull(addresses).stream()
                .map(address -> new AddressDto(
                        address.getId(),
                        address.getType(),
                        address.getStreetLine1(),
                        address.getStreetLine2(),
                        address.getPostalCode(),
                        address.getCity(),
                        address.getCountryCode(),
                        address.isPrimaryAddress()
                ))
                .collect(Collectors.toList());
    }

    private List<AccountDto> mapAccounts(List<Account> accounts) {
        return CollectionUtils.emptyIfNull(accounts).stream().map(this::toAccountDto).collect(Collectors.toList());
    }

    private List<ConsentDto> mapConsents(List<Consent> consents) {
        return CollectionUtils.emptyIfNull(consents).stream()
                .map(consent -> new ConsentDto(
                        consent.getId(),
                        consent.getCategory(),
                        consent.isGranted(),
                        consent.getCapturedAt(),
                        consent.getChannel()
                ))
                .collect(Collectors.toList());
    }

    private List<SupportCaseDto> mapSupportCases(List<SupportCase> supportCases) {
        return CollectionUtils.emptyIfNull(supportCases).stream().map(this::toSupportCaseDto).collect(Collectors.toList());
    }
}

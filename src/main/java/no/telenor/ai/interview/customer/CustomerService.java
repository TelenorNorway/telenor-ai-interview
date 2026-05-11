package no.telenor.ai.interview.customer;

import no.telenor.ai.interview.account.AccountDto;
import no.telenor.ai.interview.account.AccountRepository;
import no.telenor.ai.interview.audit.AuditEvent;
import no.telenor.ai.interview.audit.AuditEventDto;
import no.telenor.ai.interview.audit.AuditEventRepository;
import no.telenor.ai.interview.error.DuplicateCustomerException;
import no.telenor.ai.interview.error.ResourceNotFoundException;

import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.validator.routines.EmailValidator;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final AccountRepository accountRepository;
    private final AuditEventRepository auditEventRepository;
    private final CustomerMapper customerMapper;
    private final LegacyCustomerSnapshotService legacyCustomerSnapshotService;
    private final LegacyRiskLookupService legacyRiskLookupService;

    public CustomerService(CustomerRepository customerRepository,
                           AccountRepository accountRepository,
                           AuditEventRepository auditEventRepository,
                           CustomerMapper customerMapper,
                           LegacyCustomerSnapshotService legacyCustomerSnapshotService,
                           LegacyRiskLookupService legacyRiskLookupService) {
        this.customerRepository = customerRepository;
        this.accountRepository = accountRepository;
        this.auditEventRepository = auditEventRepository;
        this.customerMapper = customerMapper;
        this.legacyCustomerSnapshotService = legacyCustomerSnapshotService;
        this.legacyRiskLookupService = legacyRiskLookupService;
    }

    @Transactional(readOnly = true)
    public List<CustomerSummary> search(String query, String status) {
        CustomerStatus parsedStatus = parseStatus(status);
        String normalizedQuery = StringUtils.trimToNull(query);
        return customerRepository.search(normalizedQuery, parsedStatus).stream()
                .map(customerMapper::toSummary)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public CustomerDetails getCustomer(Long id) {
        return customerMapper.toDetails(findCustomer(id));
    }

    @Transactional
    public CustomerDetails createCustomer(CreateCustomerRequest request, String username) {
        String customerNumber = StringUtils.trimToEmpty(request.getCustomerNumber());
        String email = StringUtils.trimToEmpty(request.getEmail());
        validateLegacyEmail(email);
        if (customerRepository.existsByCustomerNumber(customerNumber)) {
            throw new DuplicateCustomerException("Customer number already exists: " + customerNumber);
        }
        Customer customer = new Customer(
                customerNumber,
                StringUtils.trimToEmpty(request.getFirstName()),
                StringUtils.trimToEmpty(request.getLastName()),
                request.getDateOfBirth(),
                email,
                StringUtils.trimToNull(request.getPhoneNumber())
        );
        customer.setRiskScore(request.getRiskScore());
        Customer saved = customerRepository.save(customer);
        auditEventRepository.save(new AuditEvent(saved.getCustomerNumber(), "CUSTOMER_CREATED", legacyCustomerSnapshotService.toAuditJson(saved, "CUSTOMER_CREATED"), username));
        return customerMapper.toDetails(saved);
    }

    @Transactional
    public CustomerDetails updateCustomer(Long id, UpdateCustomerRequest request, String username) {
        Customer customer = findCustomer(id);
        validateLegacyEmail(request.getEmail());
        customer.setFirstName(StringUtils.trimToEmpty(request.getFirstName()));
        customer.setLastName(StringUtils.trimToEmpty(request.getLastName()));
        customer.setDateOfBirth(request.getDateOfBirth());
        customer.setEmail(StringUtils.trimToEmpty(request.getEmail()));
        customer.setPhoneNumber(StringUtils.trimToNull(request.getPhoneNumber()));
        customer.setStatus(request.getStatus());
        customer.setRiskScore(request.getRiskScore());
        auditEventRepository.save(new AuditEvent(customer.getCustomerNumber(), "CUSTOMER_UPDATED", legacyCustomerSnapshotService.toAuditJson(customer, "CUSTOMER_UPDATED"), username));
        return customerMapper.toDetails(customer);
    }

    @Transactional
    public void deactivateCustomer(Long id, String username) {
        Customer customer = findCustomer(id);
        customer.setStatus(CustomerStatus.CLOSED);
        auditEventRepository.save(new AuditEvent(customer.getCustomerNumber(), "CUSTOMER_CLOSED", legacyCustomerSnapshotService.toAuditJson(customer, "CUSTOMER_CLOSED"), username));
    }

    @Transactional(readOnly = true)
    public List<AccountDto> getAccounts(Long customerId) {
        if (!customerRepository.existsById(customerId)) {
            throw new ResourceNotFoundException("Customer not found: " + customerId);
        }
        return accountRepository.findByCustomerIdOrderByOpenedDateDesc(customerId).stream()
                .map(customerMapper::toAccountDto)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<AuditEventDto> getAuditEvents(Long customerId) {
        Customer customer = findCustomer(customerId);
        return auditEventRepository.findByCustomerNumberOrderByCreatedAtDesc(customer.getCustomerNumber()).stream()
                .map(event -> new AuditEventDto(
                        event.getId(),
                        event.getCustomerNumber(),
                        event.getEventType(),
                        event.getDescription(),
                        event.getCreatedAt(),
                        event.getCreatedBy()
                ))
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public LegacyRiskLookup getLegacyRiskLookup(Long customerId) {
        return legacyRiskLookupService.lookup(findCustomer(customerId));
    }

    private Customer findCustomer(Long id) {
        return customerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found: " + id));
    }

    private CustomerStatus parseStatus(String status) {
        if (StringUtils.isBlank(status)) {
            return null;
        }
        return CustomerStatus.valueOf(status.trim().toUpperCase(Locale.ROOT));
    }

    private void validateLegacyEmail(String email) {
        if (!EmailValidator.getInstance().isValid(email)) {
            throw new IllegalArgumentException("Invalid email address");
        }
    }
}

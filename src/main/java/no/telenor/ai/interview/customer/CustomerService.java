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
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

@Service
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final AccountRepository accountRepository;
    private final AuditEventRepository auditEventRepository;
    private final CustomerMapper customerMapper;

    public CustomerService(CustomerRepository customerRepository,
                           AccountRepository accountRepository,
                           AuditEventRepository auditEventRepository,
                           CustomerMapper customerMapper) {
        this.customerRepository = customerRepository;
        this.accountRepository = accountRepository;
        this.auditEventRepository = auditEventRepository;
        this.customerMapper = customerMapper;
    }

    @Transactional(readOnly = true)
    public List<CustomerSummary> search(String query, String status) {
        CustomerStatus parsedStatus = parseStatus(status);
        String normalizedQuery = StringUtils.hasText(query) ? query.trim() : null;
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
        if (customerRepository.existsByCustomerNumber(request.getCustomerNumber())) {
            throw new DuplicateCustomerException("Customer number already exists: " + request.getCustomerNumber());
        }
        Customer customer = new Customer(
                request.getCustomerNumber(),
                request.getFirstName(),
                request.getLastName(),
                request.getDateOfBirth(),
                request.getEmail(),
                request.getPhoneNumber()
        );
        customer.setRiskScore(request.getRiskScore());
        Customer saved = customerRepository.save(customer);
        auditEventRepository.save(new AuditEvent(saved.getCustomerNumber(), "CUSTOMER_CREATED", "Customer created through API", username));
        return customerMapper.toDetails(saved);
    }

    @Transactional
    public CustomerDetails updateCustomer(Long id, UpdateCustomerRequest request, String username) {
        Customer customer = findCustomer(id);
        customer.setFirstName(request.getFirstName());
        customer.setLastName(request.getLastName());
        customer.setDateOfBirth(request.getDateOfBirth());
        customer.setEmail(request.getEmail());
        customer.setPhoneNumber(request.getPhoneNumber());
        customer.setStatus(request.getStatus());
        customer.setRiskScore(request.getRiskScore());
        auditEventRepository.save(new AuditEvent(customer.getCustomerNumber(), "CUSTOMER_UPDATED", "Customer profile updated through API", username));
        return customerMapper.toDetails(customer);
    }

    @Transactional
    public void deactivateCustomer(Long id, String username) {
        Customer customer = findCustomer(id);
        customer.setStatus(CustomerStatus.CLOSED);
        auditEventRepository.save(new AuditEvent(customer.getCustomerNumber(), "CUSTOMER_CLOSED", "Customer deactivated through API", username));
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

    private Customer findCustomer(Long id) {
        return customerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found: " + id));
    }

    private CustomerStatus parseStatus(String status) {
        if (!StringUtils.hasText(status)) {
            return null;
        }
        return CustomerStatus.valueOf(status.trim().toUpperCase(Locale.ROOT));
    }
}

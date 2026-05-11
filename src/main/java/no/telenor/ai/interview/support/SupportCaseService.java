package no.telenor.ai.interview.support;

import no.telenor.ai.interview.audit.AuditEvent;
import no.telenor.ai.interview.audit.AuditEventRepository;
import no.telenor.ai.interview.customer.Customer;
import no.telenor.ai.interview.customer.CustomerMapper;
import no.telenor.ai.interview.customer.CustomerRepository;
import no.telenor.ai.interview.error.ResourceNotFoundException;

import org.joda.time.DateTime;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SupportCaseService {

    private static final String CASE_NUMBER_PATTERN = "yyyyMMddHHmmss";

    private final SupportCaseRepository supportCaseRepository;
    private final CustomerRepository customerRepository;
    private final AuditEventRepository auditEventRepository;
    private final CustomerMapper customerMapper;

    public SupportCaseService(SupportCaseRepository supportCaseRepository,
                              CustomerRepository customerRepository,
                              AuditEventRepository auditEventRepository,
                              CustomerMapper customerMapper) {
        this.supportCaseRepository = supportCaseRepository;
        this.customerRepository = customerRepository;
        this.auditEventRepository = auditEventRepository;
        this.customerMapper = customerMapper;
    }

    @Transactional
    public SupportCaseDto createCase(Long customerId, CreateSupportCaseRequest request, String username) {
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found: " + customerId));
        SupportCase supportCase = new SupportCase(
                customer,
                "CASE-" + DateTime.now().toString(CASE_NUMBER_PATTERN),
                request.getTitle(),
                request.getDescription(),
                request.getPriority()
        );
        SupportCase saved = supportCaseRepository.save(supportCase);
        auditEventRepository.save(new AuditEvent(customer.getCustomerNumber(), "SUPPORT_CASE_CREATED", "Support case " + saved.getCaseNumber() + " created", username));
        return customerMapper.toSupportCaseDto(saved);
    }

    @Transactional
    public SupportCaseDto updateStatus(Long caseId, UpdateSupportCaseStatusRequest request, String username) {
        SupportCase supportCase = supportCaseRepository.findById(caseId)
                .orElseThrow(() -> new ResourceNotFoundException("Support case not found: " + caseId));
        supportCase.setStatus(request.getStatus());
        auditEventRepository.save(new AuditEvent(supportCase.getCustomer().getCustomerNumber(), "SUPPORT_CASE_STATUS_UPDATED", "Support case " + supportCase.getCaseNumber() + " status changed to " + request.getStatus(), username));
        return customerMapper.toSupportCaseDto(supportCase);
    }
}

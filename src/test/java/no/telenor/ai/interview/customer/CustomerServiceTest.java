package no.telenor.ai.interview.customer;

import static org.assertj.core.api.Assertions.assertThat;

import no.telenor.ai.interview.audit.AuditEventRepository;

import java.time.LocalDate;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
class CustomerServiceTest {

    @Autowired
    private CustomerService customerService;

    @Autowired
    private AuditEventRepository auditEventRepository;

    @Test
    void updatingCustomerWritesAuditEvent() {
        UpdateCustomerRequest request = new UpdateCustomerRequest();
        request.setFirstName("Mira");
        request.setLastName("Holm-Sand");
        request.setDateOfBirth(LocalDate.of(1984, 3, 12));
        request.setEmail("mira.holm.sand@example.com");
        request.setPhoneNumber("+47 555 01 101");
        request.setStatus(CustomerStatus.ACTIVE);
        request.setRiskScore(21);

        CustomerDetails updated = customerService.updateCustomer(1L, request, "service-test");

        assertThat(updated.lastName()).isEqualTo("Holm-Sand");
        assertThat(auditEventRepository.findByCustomerNumberOrderByCreatedAtDesc("C-100001"))
                .anySatisfy(event -> {
                    assertThat(event.getEventType()).isEqualTo("CUSTOMER_UPDATED");
                    assertThat(event.getCreatedBy()).isEqualTo("service-test");
                });
    }
}

package no.telenor.ai.interview.audit;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuditEventRepository extends JpaRepository<AuditEvent, Long> {

    List<AuditEvent> findByCustomerNumberOrderByCreatedAtDesc(String customerNumber);
}

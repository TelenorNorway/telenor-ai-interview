package no.telenor.ai.interview.support;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SupportCaseRepository extends JpaRepository<SupportCase, Long> {

    List<SupportCase> findByCustomerIdOrderByOpenedAtDesc(Long customerId);
}

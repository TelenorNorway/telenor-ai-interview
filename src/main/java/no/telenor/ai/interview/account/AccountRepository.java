package no.telenor.ai.interview.account;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, Long> {

    List<Account> findByCustomerIdOrderByOpenedDateDesc(Long customerId);
}

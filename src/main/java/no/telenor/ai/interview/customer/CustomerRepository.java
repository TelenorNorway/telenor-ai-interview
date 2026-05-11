package no.telenor.ai.interview.customer;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CustomerRepository extends JpaRepository<Customer, Long> {

    Optional<Customer> findByCustomerNumber(String customerNumber);

    boolean existsByCustomerNumber(String customerNumber);

    @Query("select c from Customer c where " +
            "(:query is null or lower(c.customerNumber) like lower(concat('%', :query, '%')) " +
            "or lower(c.firstName) like lower(concat('%', :query, '%')) " +
            "or lower(c.lastName) like lower(concat('%', :query, '%')) " +
            "or lower(c.email) like lower(concat('%', :query, '%'))) " +
            "and (:status is null or c.status = :status) " +
            "order by c.lastName asc, c.firstName asc")
    List<Customer> search(@Param("query") String query, @Param("status") CustomerStatus status);
}

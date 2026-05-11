package no.telenor.ai.interview.customer;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
class CustomerRepositoryTest {

    @Autowired
    private CustomerRepository customerRepository;

    @Test
    void searchFindsSeededCustomerByNameAndStatus() {
        assertThat(customerRepository.search("Holm", CustomerStatus.ACTIVE))
                .extracting(Customer::getCustomerNumber)
                .containsExactly("C-100001");
    }

    @Test
    void seededCustomerHasRelatedEnterpriseData() {
        Customer customer = customerRepository.findByCustomerNumber("C-100001").orElseThrow();

        assertThat(customer.getAddresses()).hasSize(2);
        assertThat(customer.getAccounts()).hasSize(2);
        assertThat(customer.getConsents()).hasSize(2);
        assertThat(customer.getSupportCases()).hasSize(1);
    }
}

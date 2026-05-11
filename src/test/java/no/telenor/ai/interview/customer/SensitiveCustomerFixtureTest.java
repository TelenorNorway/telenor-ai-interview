package no.telenor.ai.interview.customer;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.InputStream;
import java.util.List;
import org.junit.jupiter.api.Test;

class SensitiveCustomerFixtureTest {

    private final CustomerImportService importService = new CustomerImportService();

    @Test
    void sensitiveCustomerFixtureCanBeReadForLocalValidation() throws Exception {
        try (InputStream inputStream = getClass().getResourceAsStream("/sensitive/customer-fixtures.csv")) {
            assertThat(inputStream).isNotNull();

            List<CustomerImportRow> rows = importService.readPreview(inputStream);

            assertThat(rows).hasSize(5);
            assertThat(rows)
                    .extracting(CustomerImportRow::customerNumber)
                    .containsExactly("T-900001", "T-900002", "T-900003", "T-900004", "T-900005");
            assertThat(rows)
                    .allSatisfy(row -> assertThat(row.email()).endsWith("@example.com"));
        }
    }
}

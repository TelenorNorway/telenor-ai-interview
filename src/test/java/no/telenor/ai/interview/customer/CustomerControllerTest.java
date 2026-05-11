package no.telenor.ai.interview.customer;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.containsString;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
class CustomerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void customerApiRequiresAuthentication() throws Exception {
        mockMvc.perform(get("/api/customers"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void authenticatedSearchReturnsCustomerSummaries() throws Exception {
        mockMvc.perform(get("/api/customers")
                        .queryParam("query", "Holm")
                        .with(httpBasic("operator", "operator-password")))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].customerNumber").value("C-100001"));
    }

    @Test
    void invalidCustomerPayloadReturnsBadRequest() throws Exception {
        mockMvc.perform(post("/api/customers")
                        .with(httpBasic("operator", "operator-password"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "customerNumber": "C-900001",
                                  "firstName": "Ada",
                                  "lastName": "Example",
                                  "dateOfBirth": "1990-01-01",
                                  "email": "not-an-email",
                                  "riskScore": 10
                                }
                                """))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error").value("Validation failed"));
    }

    @Test
    void openApiDescriptionIsAvailableWithoutCredentials() throws Exception {
        mockMvc.perform(get("/v3/api-docs"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.paths").exists());
    }

    @Test
    void legacyRiskLookupReturnsDeterministicRiskBand() throws Exception {
        mockMvc.perform(get("/api/customers/3/risk-lookup")
                        .with(httpBasic("operator", "operator-password")))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.customerNumber").value("C-100003"))
                .andExpect(jsonPath("$.riskBand").value("HIGH"))
                .andExpect(jsonPath("$.lookupUri").value(org.hamcrest.Matchers.containsString("customerNumber=C-100003")));
    }

    @Test
    void legacyXmlExportReturnsCustomerData() throws Exception {
        mockMvc.perform(get("/api/customers/1/legacy-export")
                        .with(httpBasic("operator", "operator-password")))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_XML))
                .andExpect(content().string(containsString("<customerNumber>C-100001</customerNumber>")))
                .andExpect(content().string(containsString("<name>Mira Holm</name>")));
    }

    @Test
    void legacyFilterAddsResponseHeader() throws Exception {
        mockMvc.perform(get("/actuator/health"))
                .andExpect(status().isOk())
                .andExpect(header().string("X-Legacy-Filter", "legacy-servlet-filter"));
    }
}

package no.telenor.ai.interview.customer;

import java.net.URI;
import java.net.URISyntaxException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class LegacyRiskLookupService {

    private final CloseableHttpClient httpClient = HttpClients.createDefault();
    private final String baseUrl;

    public LegacyRiskLookupService(@Value("${legacy.risk.base-url}") String baseUrl) {
        this.baseUrl = baseUrl;
    }

    public LegacyRiskLookup lookup(Customer customer) {
        URI uri = buildUri(customer);
        HttpGet request = new HttpGet(uri);
        request.addHeader("X-Legacy-Client", httpClient.getClass().getSimpleName());
        return new LegacyRiskLookup(customer.getCustomerNumber(), riskBand(customer.getRiskScore()), request.getURI().toString());
    }

    private URI buildUri(Customer customer) {
        try {
            return new URIBuilder(baseUrl)
                    .addParameter("customerNumber", customer.getCustomerNumber())
                    .addParameter("birthDate", customer.getDateOfBirth().toString())
                    .addParameter("riskScore", Integer.toString(customer.getRiskScore()))
                    .build();
        } catch (URISyntaxException exception) {
            throw new IllegalArgumentException("Invalid legacy risk service URL", exception);
        }
    }

    private String riskBand(int riskScore) {
        if (riskScore >= 70) {
            return "HIGH";
        }
        if (riskScore >= 40) {
            return "MEDIUM";
        }
        return "LOW";
    }
}

package no.telenor.ai.interview.customer;

import no.telenor.ai.interview.account.AccountDto;
import no.telenor.ai.interview.audit.AuditEventDto;

import java.net.URI;
import java.security.Principal;
import java.util.List;
import javax.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/customers")
public class CustomerController {

    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping
    public List<CustomerSummary> search(@RequestParam(required = false) String query,
                                        @RequestParam(required = false) String status) {
        return customerService.search(query, status);
    }

    @GetMapping("/{id}")
    public CustomerDetails getCustomer(@PathVariable Long id) {
        return customerService.getCustomer(id);
    }

    @PostMapping
    public ResponseEntity<CustomerDetails> create(@Valid @RequestBody CreateCustomerRequest request, Principal principal) {
        CustomerDetails created = customerService.createCustomer(request, username(principal));
        return ResponseEntity.created(URI.create("/api/customers/" + created.id())).body(created);
    }

    @PutMapping("/{id}")
    public CustomerDetails update(@PathVariable Long id, @Valid @RequestBody UpdateCustomerRequest request, Principal principal) {
        return customerService.updateCustomer(id, request, username(principal));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deactivate(@PathVariable Long id, Principal principal) {
        customerService.deactivateCustomer(id, username(principal));
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}/accounts")
    public List<AccountDto> accounts(@PathVariable Long id) {
        return customerService.getAccounts(id);
    }

    @GetMapping("/{id}/audit")
    public List<AuditEventDto> audit(@PathVariable Long id) {
        return customerService.getAuditEvents(id);
    }

    @GetMapping("/{id}/risk-lookup")
    public LegacyRiskLookup riskLookup(@PathVariable Long id) {
        return customerService.getLegacyRiskLookup(id);
    }

    private String username(Principal principal) {
        return principal == null ? "unknown" : principal.getName();
    }
}

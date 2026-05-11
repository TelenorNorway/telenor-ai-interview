package no.telenor.ai.interview.support;

import java.net.URI;
import java.security.Principal;
import javax.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class SupportCaseController {

    private final SupportCaseService supportCaseService;

    public SupportCaseController(SupportCaseService supportCaseService) {
        this.supportCaseService = supportCaseService;
    }

    @PostMapping("/customers/{customerId}/support-cases")
    public ResponseEntity<SupportCaseDto> create(@PathVariable Long customerId,
                                                 @Valid @RequestBody CreateSupportCaseRequest request,
                                                 Principal principal) {
        SupportCaseDto created = supportCaseService.createCase(customerId, request, username(principal));
        return ResponseEntity.created(URI.create("/api/support-cases/" + created.id())).body(created);
    }

    @PutMapping("/support-cases/{id}/status")
    public SupportCaseDto updateStatus(@PathVariable Long id,
                                       @Valid @RequestBody UpdateSupportCaseStatusRequest request,
                                       Principal principal) {
        return supportCaseService.updateStatus(id, request, username(principal));
    }

    private String username(Principal principal) {
        return principal == null ? "unknown" : principal.getName();
    }
}

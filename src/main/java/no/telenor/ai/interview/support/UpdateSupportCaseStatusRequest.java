package no.telenor.ai.interview.support;

import javax.validation.constraints.NotNull;

public class UpdateSupportCaseStatusRequest {

    @NotNull
    private SupportCaseStatus status;

    public SupportCaseStatus getStatus() {
        return status;
    }

    public void setStatus(SupportCaseStatus status) {
        this.status = status;
    }
}

package no.telenor.ai.interview.support;

import no.telenor.ai.interview.customer.Customer;

import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "support_cases")
public class SupportCase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;

    @Column(name = "case_number", nullable = false, unique = true, length = 40)
    private String caseNumber;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false, length = 2000)
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 40)
    private SupportCaseStatus status;

    @Column(nullable = false)
    private int priority;

    @Column(name = "opened_at", nullable = false)
    private LocalDateTime openedAt;

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    protected SupportCase() {
    }

    public SupportCase(Customer customer, String caseNumber, String title, String description, int priority) {
        this.customer = customer;
        this.caseNumber = caseNumber;
        this.title = title;
        this.description = description;
        this.priority = priority;
        this.status = SupportCaseStatus.OPEN;
        this.openedAt = LocalDateTime.now();
        this.updatedAt = this.openedAt;
    }

    public Long getId() {
        return id;
    }

    public Customer getCustomer() {
        return customer;
    }

    public String getCaseNumber() {
        return caseNumber;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public SupportCaseStatus getStatus() {
        return status;
    }

    public void setStatus(SupportCaseStatus status) {
        this.status = status;
        this.updatedAt = LocalDateTime.now();
    }

    public int getPriority() {
        return priority;
    }

    public LocalDateTime getOpenedAt() {
        return openedAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }
}

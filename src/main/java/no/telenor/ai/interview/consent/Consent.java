package no.telenor.ai.interview.consent;

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
@Table(name = "consents")
public class Consent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 40)
    private ConsentCategory category;

    @Column(nullable = false)
    private boolean granted;

    @Column(name = "captured_at", nullable = false)
    private LocalDateTime capturedAt;

    @Column(nullable = false)
    private String channel;

    protected Consent() {
    }

    public Long getId() {
        return id;
    }

    public Customer getCustomer() {
        return customer;
    }

    public ConsentCategory getCategory() {
        return category;
    }

    public boolean isGranted() {
        return granted;
    }

    public LocalDateTime getCapturedAt() {
        return capturedAt;
    }

    public String getChannel() {
        return channel;
    }
}

package no.telenor.ai.interview.customer;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import org.joda.time.DateTime;

@XmlRootElement(name = "customer")
@XmlAccessorType(XmlAccessType.FIELD)
public class LegacyCustomerXml {

    @XmlElement
    private Long id;

    @XmlElement
    private String customerNumber;

    @XmlElement
    private String name;

    @XmlElement
    private String email;

    @XmlElement
    private String status;

    @XmlElement
    private int riskScore;

    @XmlElement
    private String exportedAt;

    public LegacyCustomerXml() {
    }

    static LegacyCustomerXml from(Customer customer) {
        LegacyCustomerXml xml = new LegacyCustomerXml();
        xml.id = customer.getId();
        xml.customerNumber = customer.getCustomerNumber();
        xml.name = customer.getFirstName() + " " + customer.getLastName();
        xml.email = customer.getEmail();
        xml.status = customer.getStatus().name();
        xml.riskScore = customer.getRiskScore();
        xml.exportedAt = DateTime.now().toString();
        return xml;
    }
}

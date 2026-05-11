package no.telenor.ai.interview.customer;

import no.telenor.ai.interview.error.ResourceNotFoundException;

import java.io.StringWriter;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class LegacyCustomerXmlExportService {

    private final CustomerRepository customerRepository;

    public LegacyCustomerXmlExportService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Transactional(readOnly = true)
    public String export(Long customerId) {
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found: " + customerId));
        try {
            JAXBContext context = JAXBContext.newInstance(LegacyCustomerXml.class);
            Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
            StringWriter writer = new StringWriter();
            marshaller.marshal(LegacyCustomerXml.from(customer), writer);
            return writer.toString();
        } catch (JAXBException exception) {
            throw new IllegalStateException("Could not export customer XML", exception);
        }
    }
}

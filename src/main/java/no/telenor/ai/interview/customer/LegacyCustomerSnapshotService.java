package no.telenor.ai.interview.customer;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.joda.time.DateTime;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
public class LegacyCustomerSnapshotService {

    private final Gson gson = new GsonBuilder().disableHtmlEscaping().create();
    private final ModelMapper modelMapper = new ModelMapper();

    public String toAuditJson(Customer customer, String action) {
        LegacyCustomerSnapshot snapshot = modelMapper.map(customer, LegacyCustomerSnapshot.class);
        snapshot.setAction(action);
        snapshot.setCapturedAt(DateTime.now().toString());
        return gson.toJson(snapshot);
    }
}

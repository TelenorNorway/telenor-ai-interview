package no.telenor.ai.interview.customer;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

@Service
public class CustomerImportService {

    public List<CustomerImportRow> readPreview(InputStream inputStream) throws IOException, CsvValidationException {
        try (CSVReader reader = new CSVReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8))) {
            List<CustomerImportRow> rows = new ArrayList<>();
            reader.readNext();
            int lineNumber = 1;
            String[] fields;
            while ((fields = reader.readNext()) != null) {
                lineNumber++;
                if (fields.length == 1 && StringUtils.isBlank(fields[0])) {
                    continue;
                }
                rows.add(parseFields(fields, lineNumber));
            }
            return rows;
        }
    }

    private CustomerImportRow parseFields(String[] fields, int lineNumber) {
        if (fields.length != 8) {
            throw new IllegalArgumentException("Invalid customer import row at line " + lineNumber);
        }
        return new CustomerImportRow(
                StringUtils.trimToEmpty(fields[0]),
                StringUtils.trimToEmpty(fields[1]),
                StringUtils.trimToEmpty(fields[2]),
                LocalDate.parse(StringUtils.trimToEmpty(fields[3])),
                StringUtils.trimToEmpty(fields[4]),
                StringUtils.trimToEmpty(fields[5]),
                StringUtils.trimToEmpty(fields[6]),
                StringUtils.trimToEmpty(fields[7])
        );
    }
}

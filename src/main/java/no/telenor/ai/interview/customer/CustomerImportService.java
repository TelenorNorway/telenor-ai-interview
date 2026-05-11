package no.telenor.ai.interview.customer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class CustomerImportService {

    public List<CustomerImportRow> readPreview(InputStream inputStream) throws IOException {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8))) {
            List<CustomerImportRow> rows = new ArrayList<>();
            String line = reader.readLine();
            int lineNumber = 1;
            while ((line = reader.readLine()) != null) {
                lineNumber++;
                if (line.trim().isEmpty()) {
                    continue;
                }
                rows.add(parseLine(line, lineNumber));
            }
            return rows;
        }
    }

    private CustomerImportRow parseLine(String line, int lineNumber) {
        String[] fields = line.split(",", -1);
        if (fields.length != 8) {
            throw new IllegalArgumentException("Invalid customer import row at line " + lineNumber);
        }
        return new CustomerImportRow(
                fields[0].trim(),
                fields[1].trim(),
                fields[2].trim(),
                LocalDate.parse(fields[3].trim()),
                fields[4].trim(),
                fields[5].trim(),
                fields[6].trim(),
                fields[7].trim()
        );
    }
}

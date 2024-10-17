package com.example.demo.service;
import org.springframework.web.multipart.MultipartFile;
import com.example.demo.repository.SourceMasterRepository;
import com.example.demo.repository.SourceDetailsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.demo.entity.SourceDetails;
import com.example.demo.entity.SourceMaster;
import com.opencsv.CSVReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
public class DataImportService {

    @Autowired
    private SourceMasterRepository sourceMasterRepository;

    @Autowired
    private SourceDetailsRepository sourceDetailsRepository;

    public void importData(MultipartFile file) throws Exception {
        // Check if the file is a CSV
        if (!file.getOriginalFilename().endsWith(".csv")) {
            throw new IllegalArgumentException("Invalid file type. Please upload a CSV file.");
        }

        try (CSVReader reader = new CSVReader(new InputStreamReader(file.getInputStream()))) {
            String[] nextLine;

            // Skip header
            nextLine = reader.readNext();  // Ensure this skips the header line
            if (nextLine == null || nextLine.length == 0) {
                throw new IllegalArgumentException("CSV file is empty or not formatted correctly.");
            }

            Map<Integer, UUID> sourceIdToUuidMap = new HashMap<>();

            while ((nextLine = reader.readNext()) != null) {
                if (nextLine.length < 4) {
                    // Skip any line that doesn't have the expected number of columns
                    continue;
                }

                try {
                    // Read data from the next line
                    int sourceId = Integer.parseInt(nextLine[0].trim()); // sourceid
                    String occupation = nextLine[1].trim(); // Occupation
                    String name = nextLine[2].trim(); // Name
                    String dobString = nextLine[3].trim(); // Dob

                    // Insert into source_master
                    if (!sourceIdToUuidMap.containsKey(sourceId)) {
                        SourceMaster master = new SourceMaster();
                        master.setSourceId(sourceId);
                        sourceMasterRepository.save(master);
                        sourceIdToUuidMap.put(sourceId, UUID.fromString(master.getUuid().toString())); // Ensure this returns a UUID
                    }

                    // Insert into source_details
                    SourceDetails details = new SourceDetails();
                    details.setSourceId(String.valueOf(sourceIdToUuidMap.get(sourceId))); // Convert UUID to String
                    details.setOccupation(occupation);
                    details.setName(name);
                    details.setDob(dobString);
                    sourceDetailsRepository.save(details);

                } catch (NumberFormatException e) {
                    // Handle the case where the sourceId or other fields cannot be parsed
                    System.err.println("Skipping invalid row: " + e.getMessage());
                }
            }
        }
    }

}
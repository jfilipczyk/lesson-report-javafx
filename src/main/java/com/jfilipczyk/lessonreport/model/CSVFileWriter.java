package com.jfilipczyk.lessonreport.model;

import com.opencsv.CSVWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class CSVFileWriter {
    
    public void write(String filepath, List<String[]> values) throws IOException {
        try (CSVWriter writer = new CSVWriter(new FileWriter(filepath), ',')) {
            values.forEach(v -> writer.writeNext(v));
        }
    }    
}

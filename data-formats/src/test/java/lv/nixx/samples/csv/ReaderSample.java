package lv.nixx.samples.csv;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.junit.Test;

import java.io.FileReader;
import java.io.IOException;
import java.io.StringReader;

public class ReaderSample {

    private final String csv = "Name,Surname, Age, Comments\n" +
            "John, Rambo, 35, N/A\n" +
            "Jack,,45,Comment \"Inside quotes\" line\n" +
            ",,,,,,\n" +
            "Pamela,Anderson,45,\"Coma , inside quotes\"";

    @Test
    public void fileReadSample() throws IOException {

        CSVFormat csvFormat = CSVFormat.RFC4180.builder()
                .setSkipHeaderRecord(true)
                .setHeader()
                .setSkipHeaderRecord(true)
                .setNullString("N/A")
                .build();

        Iterable<CSVRecord> records = csvFormat.parse(new FileReader("./src/test/resources/sample.csv"));

        for (CSVRecord record : records) {
            String f1 = record.get("Name");
            String f2 = record.get("Surname");
            String f3 = record.get("Age");
            String f4 = record.get("Comments");
            System.out.println(f1 + ":" + f2 + ":" + f3 + ":" + f4);
        }
    }

    @Test
    public void readCSVWithEmptyLine() throws IOException {

        CSVFormat csvFormat = CSVFormat.RFC4180.builder()
                .setSkipHeaderRecord(true)
                .setHeader()
                .setIgnoreEmptyLines(true)
                .setIgnoreSurroundingSpaces(true)
                .setSkipHeaderRecord(true)
                .setNullString("N/A")
                .build();

        Iterable<CSVRecord> records = csvFormat.parse(new StringReader(csv));

        for (CSVRecord record : records) {
            String f1 = record.get("Name");
            String f2 = record.get("Surname");
            String f3 = record.get("Age");
            String f4 = record.get("Comments");
            System.out.println(f1 + ":" + f2 + ":" + f3 + ":" + f4);
        }
    }

    @Test
    public void readCSVHeaderMap() throws IOException {

        CSVFormat csvFormat = CSVFormat.RFC4180.builder()
                .setSkipHeaderRecord(true)
                .setHeader(Headers.class)
                .setIgnoreSurroundingSpaces(true)
                .setIgnoreEmptyLines(true)
                .setSkipHeaderRecord(true)
                .setNullString("")
                .build();

        Iterable<CSVRecord> records = csvFormat.parse(new StringReader(csv));

        for (CSVRecord record : records) {
            String f1 = record.get(Headers.Name);
            String f2 = record.get(Headers.Surname);
            String f3 = record.get(Headers.Age);
            String f4 = record.get(Headers.Comments);

            System.out.println(f1 + ":" + f2 + ":" + f3 + ":" + f4);
        }

    }

    enum Headers {
        Name, Surname, Age, Comments
    }


}

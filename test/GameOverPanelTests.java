import java.io.Writer;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import com.opencsv.CSVWriter;

import javax.swing.*;

public class GameOverPanelTests {

    @Test
    public void testWriteToFile() {
        // Create GameResult
        GameResult result = new GameResult(true, 1, 5);

        // Create MockCSVWriter
        MockCSVWriter mockCsvWriter = new MockCSVWriter();

        // Create GameOverPanel
        GameOverPanel gameOverPanel = new GameOverPanel(new JPanel());

        LocalDateTime startTime = LocalDateTime.now();// Start Time

        // Call UUT
        gameOverPanel.setGameResults(result, mockCsvWriter);

        LocalDateTime endTime = LocalDateTime.now(); // End Time

        // Get lastRecord from Mock
        String[] lastRecord = mockCsvWriter.getLastRecord();

        // Parse the timestamp from lastRecord[0]
        LocalDateTime recordTime = LocalDateTime.parse(lastRecord[0], DateTimeFormatter.ISO_LOCAL_DATE_TIME);

        // Assert that the record timestamp is between startTime and endTime
        assertTrue(recordTime.isAfter(startTime) || recordTime.isEqual(startTime),
                "Record timestamp should be after or equal to start time.");
        assertTrue(recordTime.isBefore(endTime) || recordTime.isEqual(endTime),
                "Record timestamp should be before or equal to end time.");

        assertEquals(lastRecord[1], "5");
    }
}

// Test double for CSVWriter
class MockCSVWriter extends CSVWriter {
    private String[] lastRecord;

    public MockCSVWriter() {
        super((Writer) null); // Pass null or a mock Writer
    }

    @Override
    public void writeNext(String[] record) {
        this.lastRecord = record;
    }

    public String[] getLastRecord() {
        return lastRecord;
    }
}
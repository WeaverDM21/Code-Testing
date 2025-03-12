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
        // Uses dependency injection
        // Create GameResult
        GameResult result = new GameResult(true, 1, 5);

        // Create MockCSVWriter (Dependency Injection)
        MockCSVWriter mockCsvWriter = new MockCSVWriter();

        // Create GameOverPanel
        GameOverPanel gameOverPanel = new GameOverPanel(new JPanel());

        LocalDateTime startTime = LocalDateTime.now();// Start Time

        // Call UUT
        gameOverPanel.gameResultsToFile(result, mockCsvWriter);

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

    @Test
    public void testGetCorrectValue(){
        // Create GameOverPanel
        GameOverPanel gameOverPanel = new GameOverPanel(new JPanel());

        // Create GameResult
        GameResult result = new GameResult(true, 1, 5);

        assertEquals("The answer was 1.", gameOverPanel.getCorrectValue(result));
    }

    @Test
    public void testGetNumGuessesOneTrue(){
        // Create GameOverPanel
        GameOverPanel gameOverPanel = new GameOverPanel(new JPanel());

        // Create GameResult
        GameResult result = new GameResult(true, 1, 1);

        assertEquals("You guessed it on the first try!", gameOverPanel.getNumGuesses(result));
    }

    @Test
    public void testGetNumGuessesOneFalse(){
        // Create GameOverPanel
        GameOverPanel gameOverPanel = new GameOverPanel(new JPanel());

        // Create GameResult
        GameResult result = new GameResult(false, 1, 1);

        assertEquals("I guessed it on the first try!", gameOverPanel.getNumGuesses(result));
    }

    @Test
    public void testGetNumGuessesMoreThanOneTrue(){
        // Create GameOverPanel
        GameOverPanel gameOverPanel = new GameOverPanel(new JPanel());

        // Create GameResult
        GameResult result = new GameResult(true, 1, 5);

        assertEquals("It took you 5 guesses.", gameOverPanel.getNumGuesses(result));
    }

    @Test
    public void testGetNumGuessesMoreThanOneFalse(){
        // Create GameOverPanel
        GameOverPanel gameOverPanel = new GameOverPanel(new JPanel());

        // Create GameResult
        GameResult result = new GameResult(false, 1, 5);

        assertEquals("It took me 5 guesses.", gameOverPanel.getNumGuesses(result));
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
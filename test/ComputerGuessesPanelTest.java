import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import javax.swing.*;

public class ComputerGuessesPanelTest {
    @Test
    void testInitialState(){
        JPanel panel = new JPanel();
        GameOverPanel gameOverPanel = new GameOverPanel(panel);
        ComputerGuessesPanel cp = new ComputerGuessesPanel(panel, gameResult -> {
            gameOverPanel.gameResultsToScreen(gameResult);
        });

        assertEquals(0, cp.getNumGuesses());
        assertEquals(1000, cp.getUpperBound());
        assertEquals(1, cp.getLowerBound());
    }

    @Test
    void testHandleLastGuessHigher(){
        JPanel panel = new JPanel();
        GameOverPanel gameOverPanel = new GameOverPanel(panel);
        ComputerGuessesPanel cp = new ComputerGuessesPanel(panel, gameResult -> {
            gameOverPanel.gameResultsToScreen(gameResult);
        });

        JLabel guessMessage = (JLabel) cp.getComponent(0);
        cp.loadGame(guessMessage);
        cp.handleLastGuessHigher(guessMessage);

        assertEquals(502, cp.getLowerBound());
        assertEquals(751, cp.getLastGuess());
        assertEquals(1, cp.getNumGuesses());
        assertEquals(1000, cp.getUpperBound());
        assertEquals("I guess 751.", guessMessage.getText());
    }

    @Test
    void testHandleLastGuessLower(){
        JPanel panel = new JPanel();
        GameOverPanel gameOverPanel = new GameOverPanel(panel);
        ComputerGuessesPanel cp = new ComputerGuessesPanel(panel, gameResult -> {
            gameOverPanel.gameResultsToScreen(gameResult);
        });

        JLabel guessMessage = (JLabel) cp.getComponent(0);
        cp.loadGame(guessMessage);

        cp.handleLastGuessLower(guessMessage);

        assertEquals(1, cp.getLowerBound());
        assertEquals(251, cp.getLastGuess());
        assertEquals(1, cp.getNumGuesses());
        assertEquals(501, cp.getUpperBound());
        assertEquals("I guess 251.", guessMessage.getText());
    }

    @Test
    void testLoadGame(){
        JPanel panel = new JPanel();
        GameOverPanel gameOverPanel = new GameOverPanel(panel);
        ComputerGuessesPanel cp = new ComputerGuessesPanel(panel, gameResult -> {
            gameOverPanel.gameResultsToScreen(gameResult);
        });

        JLabel guessMessage = (JLabel) cp.getComponent(0);
        cp.loadGame(guessMessage);
        assertEquals(1, cp.getLowerBound());
        assertEquals(501, cp.getLastGuess());
        assertEquals(0, cp.getNumGuesses());
        assertEquals(1000, cp.getUpperBound());
        assertEquals("I guess 501.", guessMessage.getText());
    }
}

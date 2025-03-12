import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class HumanGuessesGameTests {
    @Test
    public void testMakeGuessLow(){
        HumanGuessesGame hgg = new HumanGuessesGame(10);
        GuessResult gr = hgg.makeGuess(5);
        assertEquals(GuessResult.LOW, gr);
    }

    @Test
    public void testMakeGuessHigh(){
        HumanGuessesGame hgg = new HumanGuessesGame(10);
        GuessResult gr = hgg.makeGuess(15);
        assertEquals(GuessResult.HIGH, gr);
    }

    @Test
    public void testMakeGuessEqual(){
        HumanGuessesGame hgg = new HumanGuessesGame(10);
        GuessResult gr = hgg.makeGuess(10);
        assertEquals(GuessResult.CORRECT, gr);
    }

    @Test
    public void testGetNumGuessesZero(){
        HumanGuessesGame hgg = new HumanGuessesGame(10);
        assertEquals(0, hgg.getNumGuesses());
    }

    @Test
    public void testGetNumGuessesOne(){
        HumanGuessesGame hgg = new HumanGuessesGame(10);
        GuessResult gr = hgg.makeGuess(5);
        assertEquals(1, hgg.getNumGuesses());
    }

    @Test
    public void testGetNumGuessesMultiple(){
        HumanGuessesGame hgg = new HumanGuessesGame(10);
        hgg.makeGuess(5);
        hgg.makeGuess(7);
        assertEquals(2, hgg.getNumGuesses());
    }

    @Test
    public void testIsDoneFalse(){
        HumanGuessesGame hgg = new HumanGuessesGame(10);
        hgg.makeGuess(5);
        hgg.makeGuess(7);
        assertFalse(hgg.isDone());
    }

    @Test
    public void testIsDoneTrue(){
        HumanGuessesGame hgg = new HumanGuessesGame(10);
        hgg.makeGuess(5);
        hgg.makeGuess(10);
        // Should fail
        assertTrue(hgg.isDone());
    }
}

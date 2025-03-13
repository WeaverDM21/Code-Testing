import org.junit.jupiter.api.Test;

import javax.swing.*;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;


public class StatsPanelTests {
    @Test
    void testSetBins(){
        JPanel rp = new JPanel();
        StatsPanel sp = new StatsPanel(rp);
        ArrayList<JLabel> resultsLabels = new ArrayList<>();

        sp.setBins(resultsLabels, rp);

        assertEquals(8, resultsLabels.size());
        assertEquals("1", ((JLabel)rp.getComponent(0)).getText());
        assertEquals("6-7", ((JLabel)rp.getComponent(6)).getText());
        assertEquals("10-11", ((JLabel)rp.getComponent(10)).getText());
        assertEquals("12-13", ((JLabel)rp.getComponent(12)).getText());
        assertEquals("14 or more", ((JLabel)rp.getComponent(14)).getText());

    }

    @Test
    void testGetBinName(){
        StatsPanel sp = new StatsPanel(new JPanel());

        assertEquals("1", sp.getBinName(0));
        assertEquals("2-3", sp.getBinName(1));
        assertEquals("4-5", sp.getBinName(2));
        assertEquals("6-7", sp.getBinName(3));
        assertEquals("8-9", sp.getBinName(4));
        assertEquals("10-11", sp.getBinName(5));
        assertEquals("12-13", sp.getBinName(6));
        assertEquals("14 or more", sp.getBinName(7));
    }

    @Test
    void testGetNumGames(){
        StatsPanel sp = new StatsPanel(new JPanel());

        ArrayList<Integer> vals = new ArrayList<>();
        for(int i = 1; i <= 15; i++){
            vals.add(i);
        }

        GameStats stats = new StatsFile(vals);

        assertEquals(1, sp.getNumGames(stats, 1,0));
        assertEquals(5, sp.getNumGames(stats, 2,1));
        assertEquals(9, sp.getNumGames(stats, 4,2));
        assertEquals(13, sp.getNumGames(stats, 6,3));
        assertEquals(17, sp.getNumGames(stats, 8,4));
        assertEquals(21, sp.getNumGames(stats, 10,5));
        assertEquals(25, sp.getNumGames(stats, 12,6));
        assertEquals(29, sp.getNumGames(stats, 14,7));
    }
}

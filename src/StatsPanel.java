import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

/**
 * Displays statistics about how many guesses the person took during past games
 * Loads data from the file and displays in a JPanel
 *
 * TODO: refactor this class
 */
public class StatsPanel extends JPanel {

    private final JPanel resultsPanel;

    // Stats will display the number of games in each "bin"
    // A bin goes from BIN_EDGES[i] through BIN_EDGES[i+1]-1, inclusive
    private static final int [] BIN_EDGES = {1, 2, 4, 6, 8, 10, 12, 14};
    private ArrayList<JLabel> resultsLabels;

    public StatsPanel(JPanel cardsPanel) {

        this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));

        JLabel title = new JLabel("Your Stats");
        this.add(title);
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel subtitle = new JLabel("(Past 30 Days)");
        this.add(subtitle);
        subtitle.setAlignmentX(Component.CENTER_ALIGNMENT);

        this.add(Box.createRigidArea(new Dimension(0,40)));

        resultsPanel = new JPanel();
        resultsLabels = new ArrayList<>();
        resultsPanel.setLayout(new GridLayout(0, 2));
        resultsPanel.add(new JLabel("Guesses"));
        resultsPanel.add(new JLabel("Games"));

        setBins(resultsLabels, resultsPanel);

        resultsPanel.setMinimumSize(new Dimension(120, 120));
        this.add(resultsPanel);
        resultsPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        updateResultsPanel();

        this.add(Box.createVerticalGlue());

        JButton quit = new JButton("Back to Home");
        quit.addActionListener(e -> {
            // See itemStateChanged in https://docs.oracle.com/javase/tutorial/uiswing/examples/layout/CardLayoutDemoProject/src/layout/CardLayoutDemo.java
            CardLayout cardLayout = (CardLayout) cardsPanel.getLayout();
            cardLayout.show(cardsPanel, ScreenID.HOME.name());
        });
        this.add(quit);
        quit.setAlignmentX(Component.CENTER_ALIGNMENT);

        this.add(Box.createRigidArea(new Dimension(0,20)));

        this.addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentShown(java.awt.event.ComponentEvent e) {
                updateResultsPanel();
            }
        });
    }

    public void setBins(ArrayList<JLabel> resultsLabels, JPanel resultsPanel){
        for(int binIndex=0; binIndex<BIN_EDGES.length; binIndex++){
            String binName = getBinName(binIndex);
            resultsPanel.add(new JLabel(binName));
            JLabel result = new JLabel("--");
            resultsLabels.add(result);
            resultsPanel.add(result);
        }
    }

    public String getBinName(int binIndex){
        // last bin
        if(binIndex == BIN_EDGES.length-1){
            return BIN_EDGES[binIndex] + " or more";
        }

        int upperBound = BIN_EDGES[binIndex+1] - 1;
        if(upperBound > BIN_EDGES[binIndex]){
            return BIN_EDGES[binIndex] + "-" + upperBound;
        }

        return Integer.toString(BIN_EDGES[binIndex]);
    }

    public void clearResults(){
        for(JLabel lbl : resultsLabels){
            lbl.setText("--");
        }
    }

    public void updateResultsPanel(){
        clearResults();

        GameStats stats = new StatsFile();

        for(int binIndex=0; binIndex<BIN_EDGES.length; binIndex++){
            final int lowerBound = BIN_EDGES[binIndex];
            int numGames = getNumGames(stats, lowerBound, binIndex);

            JLabel resultLabel = resultsLabels.get(binIndex);
            resultLabel.setText(Integer.toString(numGames));
        }
    }

    public int getNumGames(GameStats stats, final int lowerBound, int binIndex){
        int numGames = 0;
        if(binIndex == BIN_EDGES.length-1){ // last bin
            // Sum all the results from lowerBound on up
            for(int numGuesses=lowerBound; numGuesses<stats.maxNumGuesses(); numGuesses++){
                numGames += stats.numGames(numGuesses);
            }
            return numGames;
        }

        int upperBound = BIN_EDGES[binIndex+1];
        for(int numGuesses=lowerBound; numGuesses <= upperBound; numGuesses++) {
            numGames += stats.numGames(numGuesses);
        }

        return numGames;
    }
}

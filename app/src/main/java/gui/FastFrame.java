package gui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import types.GameSession;

public class FastFrame extends JFrame {

    private final GameSession session;

    private int playerWins1 = 0;
    private int houseWins1 = 0;
    private int playerWins2 = 0;
    private int houseWins2 = 0;
    private int round2Games = 0;

    // GUI components
    private JTextField timesField;
    private JTextArea outputArea;

    public FastFrame(GameSession session) {
        this.session = session;

        setTitle("Fast Game Simulator");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(700, 450);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        /* ---------- TOP PANEL ---------- */
        JPanel top = new JPanel(new FlowLayout());
        top.add(new JLabel("Number of games to simulate:"));

        timesField = new JTextField("10000", 10);
        top.add(timesField);

        JButton runButton = new JButton("Run Simulation");
        runButton.addActionListener(e -> runSimulation());
        top.add(runButton);

        add(top, BorderLayout.NORTH);

        /* ---------- OUTPUT AREA ---------- */
        outputArea = new JTextArea();
        outputArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(outputArea);
        add(scrollPane, BorderLayout.CENTER);

        /* ---------- BOTTOM PANEL ---------- */
        JButton back = new JButton("Back");
        back.addActionListener(e -> {
            dispose();
            new MenuFrame();
        });

        JPanel bottom = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        bottom.add(back);
        add(bottom, BorderLayout.SOUTH);

        setVisible(true);
    }

    /* ---------- SIMULATION ---------- */
    private void runSimulation() {
        outputArea.setText(""); // clear previous output
        playerWins1 = 0;
        houseWins1 = 0;
        playerWins2 = 0;
        houseWins2 = 0;

        int times;

        try {
            times = Integer.parseInt(timesField.getText());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Please enter a valid number.");
            return;
        }

        for (int i = 0; i < times; i++) {
            PlayGame();
        }

        double winRate = (double) (playerWins1 + playerWins2) / ((playerWins1 + playerWins2) + (houseWins1 + houseWins2)) * 100;

        outputArea.append("Simulation complete!\n\n");
        outputArea.append("Round 1 Player wins: " + playerWins1 + " * 10" + "\n");
        outputArea.append("Round 1 House wins: " + houseWins1 + " * 10" + "\n");
        outputArea.append("Round 2 Player wins: " + playerWins2 + " * 20" + "\n");
        outputArea.append("Round 2 House wins: " + houseWins2 + " * 10" + "\n");
        outputArea.append(session.deck.toString() + "\n");
        outputArea.append(String.format("Win rate: %.2f%%\n", winRate));
        outputArea.append("\n\n");

        double bet = 10.0;

        double totalGames = (playerWins1 + playerWins2) + (houseWins1 + houseWins2);
        double evPerGame = ((bet * (playerWins1 - houseWins1)) + ((bet * 2 * playerWins2) - (bet * houseWins2))) / totalGames;

        outputArea.append(String.format("Bet per Game: $%.2f\n", bet));
        outputArea.append(String.format("Expected Value per Game: $%.2f\n", evPerGame));
    }

    /* ---------- YOUR EXISTING LOGIC ---------- */
    void PlayGame() {
        session.deck.DistributeRound1(session.player, session.house);
        int round1 = Round1();

        switch (round1) {
            case 0 -> {
                playerWins1++;
            }
            case 1 -> {
                houseWins1++;
            }
            case 2 -> {
                int round2 = Round2();
                if (round2 == 0) {
                    playerWins2++;
                } else {
                    houseWins2++;
                }
            }
            case 3 -> {
                session.player.RemoveDuplicates();
                session.house.RemoveDuplicates();

                int round2 = Round2();
                if (round2 == 0) {
                    playerWins2++;
                } else {
                    houseWins2++;
                }

                session.player.ResetHand();
                session.house.ResetHand();
            }
            default ->
                throw new IllegalStateException("Unexpected round1 value: " + round1);
        }

        session.deck.ResetDeck(session.player, session.house);
        session.deck.RemoveAces();
    }

    int Round1() {
        boolean playerDup = session.PlayerHasDuplicate();
        boolean houseDup = session.HouseHasDuplicate();

        return switch (playerDup + ":" + houseDup) {
            case "true:false" ->
                0;
            case "false:true" ->
                1;
            case "false:false" ->
                2;
            case "true:true" ->
                3;
            default ->
                throw new IllegalStateException("Unexpected state");
        };
    }

    int Round2() {
        session.deck.AddAces();

        while (true) {

            if ("A".equals(session.deck.DistributeHouse(session.house))) {
                return 1;
            }
            if (session.HouseHasDuplicate()) {
                return 1;
            }
            if ("A".equals(session.deck.DistributePlayer(session.player))) {
                return 1;
            }
            if (session.PlayerHasDuplicate()) {
                return 0;
            }
        }

    }
}

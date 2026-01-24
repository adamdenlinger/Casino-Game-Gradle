package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingConstants;

import types.GameSession;
import types.Money;

public class SlowFrame extends JFrame {

    private final GameSession session;

    private JLabel houseHandLabel;
    private JLabel playerHandLabel;
    private JLabel statusLabel;
    private JLabel moneyLabel;
    private JLabel epLabel;
    private JLabel evLabel;

    private JButton dealHouseButton;
    private JButton dealPlayerButton;
    private JButton startButton;
    private JButton resetButton;

    private boolean playerWon = false;
    private boolean bothHaveDupes = false;
    private boolean round2 = false;

    private JSpinner bet;

    private int betAmount;
    private int roundsPlayed = 0;
    private int playerWins = 0;

    private double expectedProbability;
    private double expectedValue;

    // Make this a field so other methods/listeners can use it
    private JPanel headerPanel;
    private JPanel gamePanel;

    // initializes slowframe with the current game session
    public SlowFrame(GameSession session) {
        this.session = session;

        setTitle("Slow Game");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(900, 600);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Create panels
        headerPanel = createHeaderPanel();
        gamePanel = createGamePanel(); // assign to field
        JPanel footerPanel = createFooterPanel();

        // Add panels
        add(headerPanel, BorderLayout.NORTH);
        add(gamePanel, BorderLayout.CENTER);
        add(footerPanel, BorderLayout.SOUTH);

        // Add start/back buttons into the game panel
        addStartControls();

        setVisible(true);
    }

    // ===================== PANELS =====================
    // Creates the header panel
    private JPanel createHeaderPanel() {
        JPanel panel = new JPanel();
        panel.setPreferredSize(new Dimension(0, 60));
        panel.setBackground(new Color(30, 30, 30));

        JLabel title = new JLabel("Slow Game Mode");
        title.setForeground(Color.WHITE);
        title.setFont(new Font("SansSerif", Font.BOLD, 18));

        moneyLabel = new JLabel("Money: $" + Money.getAmount());
        moneyLabel.setForeground(Color.WHITE);
        moneyLabel.setFont(new Font("SansSerif", Font.BOLD, 18));

        epLabel = new JLabel("EP: " + EpCalc());
        epLabel.setForeground(Color.WHITE);
        epLabel.setFont(new Font("SansSerif", Font.BOLD, 14));

        evLabel = new JLabel("EV: " + EvCalc());
        evLabel.setForeground(Color.WHITE);
        evLabel.setFont(new Font("SansSerif", Font.BOLD, 14));

        panel.add(title, BorderLayout.WEST);
        panel.add(moneyLabel, BorderLayout.EAST);
        panel.add(epLabel, BorderLayout.EAST);
        panel.add(evLabel, BorderLayout.EAST);

        title.setBorder(BorderFactory.createEmptyBorder(0, 15, 0, 0));
        moneyLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 15));

        return panel;
    }

    // Creates the main game panel
    private JPanel createGamePanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(new Color(60, 120, 60)); // table green

        houseHandLabel = new JLabel("House: (not dealt)", SwingConstants.CENTER);
        houseHandLabel.setFont(new Font("SansSerif", Font.BOLD, 18));
        houseHandLabel.setForeground(Color.WHITE);

        statusLabel = new JLabel("Press Start to deal", SwingConstants.CENTER);
        statusLabel.setFont(new Font("SansSerif", Font.BOLD, 24));
        statusLabel.setForeground(Color.WHITE);

        playerHandLabel = new JLabel("Player: (not dealt)", SwingConstants.CENTER);
        playerHandLabel.setFont(new Font("SansSerif", Font.BOLD, 18));
        playerHandLabel.setForeground(Color.WHITE);

        panel.add(houseHandLabel, BorderLayout.NORTH);
        panel.add(statusLabel, BorderLayout.CENTER);
        panel.add(playerHandLabel, BorderLayout.SOUTH);

        return panel;
    }

    // Creates the footer panel
    private JPanel createFooterPanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        panel.setPreferredSize(new Dimension(0, 50));
        panel.setBackground(new Color(220, 220, 220));

        JButton exitButton = new JButton("Exit to Menu");
        exitButton.addActionListener(e -> {
            dispose();
            new MenuFrame();
        });

        panel.add(exitButton);
        return panel;
    }

    // ===================== BUTTONS / LOGIC =====================
    private void addStartControls() {
        startButton = new JButton("Start Slow Game");
        bet = new JSpinner(
                new SpinnerNumberModel(
                        10, // initial value
                        1, // min
                        1000, // max
                        5 // step
                ));
        dealHouseButton = new JButton("Deal House");
        dealPlayerButton = new JButton("Deal Player");
        resetButton = new JButton("Reset Game");
        dealHouseButton.setEnabled(false);
        dealPlayerButton.setEnabled(false);
        resetButton.setEnabled(false);

        JPanel controls = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        controls.setOpaque(false);
        controls.add(startButton);
        controls.add(bet);
        controls.add(dealHouseButton);
        controls.add(dealPlayerButton);
        controls.add(resetButton);

        JPanel bottom = new JPanel(new BorderLayout());
        bottom.setOpaque(false);
        bottom.add(playerHandLabel, BorderLayout.NORTH);
        bottom.add(controls, BorderLayout.SOUTH);

        // replace the existing SOUTH component with the combined bottom panel
        gamePanel.remove(playerHandLabel);
        gamePanel.add(bottom, BorderLayout.SOUTH);

        startButton.addActionListener(e -> {
            roundsPlayed++;
            startButton.setEnabled(false);
            gamePanel.revalidate();
            gamePanel.repaint();
            headerPanel.revalidate();
            headerPanel.repaint();

            if ((Integer) bet.getValue() > Money.getAmount()) {
                statusLabel.setText("Bet exceeds available money! Adjust your bet.");
                startButton.setEnabled(true);
                return;
            }
            betAmount = (Integer) bet.getValue();

            onStartRound1();
        });

        dealHouseButton.addActionListener(e -> {
            HouseTurn();
        });
        dealPlayerButton.addActionListener(e -> {
            PlayerTurn();
        });

        resetButton.addActionListener(e -> {
            GameReset(playerWon);
        });
    }

    // ===================== GAME LOGIC METHODS =====================
    // starts round 1 of the slow game and returns the result
    private void onStartRound1() {
        session.deck.DistributeRound1(session.player, session.house);
        houseHandLabel.setText("House: " + session.house.hand);
        playerHandLabel.setText("Player: " + session.player.hand);

        int result = session.Round1();
        if (result == 0) {
            statusLabel.setText("Player wins Round 1!");
            playerWon = true;
            resetButton.setEnabled(true);
            playerWins++;
        } else if (result == 1) {
            statusLabel.setText("House wins Round 1!");
            playerWon = false;
            resetButton.setEnabled(true);
        } else {
            round2 = true;
            if (session.HouseHasDuplicate() == true && session.PlayerHasDuplicate() == true) {
                bothHaveDupes = true;
                session.player.TransferDuplicates();
                session.house.TransferDuplicates();
                statusLabel.setText("No winner yet - House must draw.");
                session.deck.AddAces();
                dealHouseButton.setEnabled(true);
            } else {
                statusLabel.setText("No winner yet - House must draw.");
                session.deck.AddAces();
                dealHouseButton.setEnabled(true);
            }
        }

        gamePanel.revalidate();
        gamePanel.repaint();

    }

    // handles the house's turn in the slow game
    private void HouseTurn() {
        String card = session.deck.DistributeHouse(session.house);
        houseHandLabel.setText("House: " + session.house.hand);
        if (card == "A") {
            statusLabel.setText("House drew an Ace! House wins!");
            dealHouseButton.setEnabled(false);
            dealPlayerButton.setEnabled(false);
            playerWon = false;
            resetButton.setEnabled(true);
        } else if (session.HouseHasDuplicate() == true) {
            statusLabel.setText("House drew a " + card + " and now has a pair! House wins!");
            dealHouseButton.setEnabled(false);
            dealPlayerButton.setEnabled(false);
            playerWon = false;
            resetButton.setEnabled(true);
        } else {
            statusLabel.setText("House drew a " + card + ". Now Player's turn.");
            dealHouseButton.setEnabled(false);
            dealPlayerButton.setEnabled(true);
        }
        gamePanel.revalidate();
        gamePanel.repaint();
    }

    // handles the player's turn in the slow game
    private void PlayerTurn() {
        String card = session.deck.DistributePlayer(session.player);
        playerHandLabel.setText("Player: " + session.player.hand);
        if (card.equals("A")) {
            statusLabel.setText("Player drew an Ace! House wins!");
            dealHouseButton.setEnabled(false);
            dealPlayerButton.setEnabled(false);
            playerWon = false;
            resetButton.setEnabled(true);
        } else if (session.PlayerHasDuplicate() == true) {
            statusLabel.setText("Player drew a " + card + " and now has a pair! Player wins!");
            dealHouseButton.setEnabled(false);
            dealPlayerButton.setEnabled(false);
            playerWon = true;
            resetButton.setEnabled(true);
            playerWins++;
        } else {
            statusLabel.setText("Player drew a " + card + ". Now House's turn.");
            dealHouseButton.setEnabled(true);
            dealPlayerButton.setEnabled(false);
        }
        gamePanel.revalidate();
        gamePanel.repaint();
    }

    // resets the game state after a round is completed
    private void GameReset(boolean playerWon) {
        resetButton.setEnabled(false);

        if (round2 == true && playerWon == true) {
            betAmount *= 2;
        }

        if (playerWon) {
            Money.add(betAmount);
        } else {
            Money.add(-betAmount);
        }

        round2 = false;

        moneyLabel.setText("Money: $" + Money.getAmount());

        if (bothHaveDupes) {
            session.player.ResetHand();
            session.house.ResetHand();
        }
        session.deck.ResetDeck(session.player, session.house);
        session.deck.RemoveAces();

        houseHandLabel.setText("House: (not dealt)");
        playerHandLabel.setText("Player: (not dealt)");
        statusLabel.setText("Press Start to deal");

        startButton.setEnabled(true);
        dealHouseButton.setEnabled(false);
        dealPlayerButton.setEnabled(false);

        gamePanel.revalidate();
        gamePanel.repaint();
        updateHeaderStats();
        headerPanel.revalidate();
        headerPanel.repaint();
    }

    private double EpCalc() {
        if (roundsPlayed == 0)
            return 0.0;
        return (double) playerWins / roundsPlayed;
    }

    private double EvCalc() {
        if (roundsPlayed == 0) {
            System.out.println("Cannot divide by 0");
            return 0.0;
        }
        return (Money.getAmount() - 1000.0) / roundsPlayed;
    }

    private void updateHeaderStats() {
        moneyLabel.setText("Money: $" + Money.getAmount());
        epLabel.setText(String.format("EP: %.0f%%", EpCalc() * 100));
        evLabel.setText(String.format("EV: $%.2f", EvCalc()));
    }
}

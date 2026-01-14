package gui;

import javax.swing.*;
import java.awt.*;
import types.GameSession;

public class SlowFrame extends JFrame {

    private final GameSession session;

    private JLabel houseHandLabel;
    private JLabel playerHandLabel;
    private JLabel statusLabel;

    // Make this a field so other methods/listeners can use it
    private JPanel gamePanel;

    public SlowFrame(GameSession session) {
        this.session = session;

        setTitle("Slow Game");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(900, 600);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Create panels
        JPanel headerPanel = createHeaderPanel();
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

    private JPanel createHeaderPanel() {
        JPanel panel = new JPanel();
        panel.setPreferredSize(new Dimension(0, 60));
        panel.setBackground(new Color(30, 30, 30));

        JLabel title = new JLabel("Slow Game Mode");
        title.setForeground(Color.WHITE);
        title.setFont(new Font("SansSerif", Font.BOLD, 18));

        panel.add(title);
        return panel;
    }

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

    private JPanel createFooterPanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        panel.setPreferredSize(new Dimension(0, 50));
        panel.setBackground(new Color(220, 220, 220));

        JButton exitButton = new JButton("Exit to Menu");
        exitButton.addActionListener(e -> {
            dispose();
            new MenuFrame(); // if MenuFrame needs session, pass it appropriately
        });

        panel.add(exitButton);
        return panel;
    }

    // ===================== BUTTONS / LOGIC =====================
    private void addStartControls() {
        JButton startButton = new JButton("Start Slow Game");
        JButton backButton = new JButton("Back");

        JPanel controls = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        controls.setOpaque(false);
        controls.add(startButton);
        controls.add(backButton);

        JPanel bottom = new JPanel(new BorderLayout());
        bottom.setOpaque(false);
        bottom.add(playerHandLabel, BorderLayout.NORTH);
        bottom.add(controls, BorderLayout.SOUTH);

        // replace the existing SOUTH component with the combined bottom panel
        gamePanel.remove(playerHandLabel);
        gamePanel.add(bottom, BorderLayout.SOUTH);

        startButton.addActionListener(e -> {
            startButton.setEnabled(false);
            gamePanel.revalidate();
            gamePanel.repaint();

            onStartRound1();
        });

        backButton.addActionListener(e -> {
            dispose();
            new MenuFrame();
        });
    }

    private void onStartRound1() {
        session.deck.DistributeRound1(session.player, session.house);

        // show hands
        houseHandLabel.setText("House: " + session.house.hand);
        playerHandLabel.setText("Player: " + session.player.hand);

        int result = session.Round1();
        // whatever your Round1 returns: 0=player, 1=house, 2=tie/continue

        if (result == 0) {
            statusLabel.setText("Player wins Round 1!");
            // optionally disable further actions / show "Play Again"
        } else if (result == 1) {
            statusLabel.setText("House wins Round 1!");
        } else {
            statusLabel.setText("No winner yet — proceed to Round 2.");
            // optionally enable "Round 2" button
        }

        gamePanel.revalidate();
        gamePanel.repaint();
    }

    private void HouseTurn() {
        
    }

    private void PlayerTurn() {

    }

}
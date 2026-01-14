package gui;

import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import types.GameSession;
import types.Money;

public class MenuFrame extends JFrame {

    public MenuFrame() {
        setTitle("Choose Mode");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(420, 200);
        setLocationRelativeTo(null);

        JButton slowBtn = new JButton("Slow Game (play + watch)");
        JButton fastBtn = new JButton("Fast Game (simulate)");

        slowBtn.setFont(slowBtn.getFont().deriveFont(16f));
        fastBtn.setFont(fastBtn.getFont().deriveFont(16f));

        slowBtn.addActionListener(e -> {
            dispose();
            GameSession session = new GameSession();
            new SlowFrame(session);
        });

        fastBtn.addActionListener(e -> {
            dispose();
            GameSession session = new GameSession();
            new FastFrame(session);
        });

        JPanel panel = new JPanel(new GridLayout(2, 1, 12, 12));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        panel.add(slowBtn);
        panel.add(fastBtn);

        add(panel);
        setVisible(true);
    }
}

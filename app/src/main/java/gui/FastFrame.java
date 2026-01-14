package gui;

import javax.swing.*;

import types.GameSession;

import java.awt.*;

public class FastFrame extends JFrame {

    private final GameSession session;

    public FastFrame(GameSession session) {
        this.session = session;

        setTitle("Fast Game");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(700, 450);
        setLocationRelativeTo(null);

        add(new JLabel("Fast Game Screen (simulation controls go here)", SwingConstants.CENTER), BorderLayout.CENTER);

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
}

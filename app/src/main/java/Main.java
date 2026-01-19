// Adam Denlinger
import javax.swing.SwingUtilities;

import gui.MenuFrame;
import types.Money;

// plays the entire game
public class Main
{
    //starts with a set amount of money and then changes as you play the game wether your in slow or in fast
    public static void main(String [] args)
    {
        Money.setAmount(1000);
        SwingUtilities.invokeLater(() -> new MenuFrame());
    }
}
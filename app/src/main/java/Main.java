import javax.swing.SwingUtilities;

import gui.MenuFrame;
import types.Money;

public class Main
{
    public static void main(String [] args)
    {
        Money.setAmount(1000);
        SwingUtilities.invokeLater(() -> new MenuFrame());
    }
}
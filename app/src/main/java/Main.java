import javax.swing.SwingUtilities;
import gui.MenuFrame;

public class Main
{
    public static void main(String [] args)
    {
        SwingUtilities.invokeLater(MenuFrame::new);
    }
}
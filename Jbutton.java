import javax.swing.*;
import java.awt.*;


public class Jbutton extends JButton
{
	public Jbutton(String s)
	{
		super(s);
		setFont( new Font(Font.DIALOG_INPUT,Font.BOLD,18));
		setForeground(Color.DARK_GRAY);
	}
}
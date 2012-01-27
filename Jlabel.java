import javax.swing.*;
import java.awt.*;


public class Jlabel extends JLabel
{
	public Jlabel(String s)
	{
		super(s);
		setFont( new Font(Font.DIALOG_INPUT,Font.BOLD,18));
		setForeground(Color.DARK_GRAY);
	}
	public Jlabel(String s,int i)
	{
		super(s,i);
		setFont( new Font(Font.DIALOG_INPUT,Font.BOLD,18));
		setForeground(Color.DARK_GRAY);
	}
}
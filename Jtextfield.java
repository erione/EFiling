import javax.swing.*;
import java.awt.*;


public class Jtextfield extends JTextField
{
	public Jtextfield()
	{
		super();
		setFont( new Font(Font.DIALOG,Font.PLAIN,16));
	}
	public Jtextfield(String s)
	{
		super(s);
		setFont( new Font(Font.DIALOG,Font.PLAIN,16));
	}
}
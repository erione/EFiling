import javax.swing.*;
import java.awt.*;
import javax.swing.border.*;

public class Jpanel extends JPanel
{
	public Jpanel(String s)
	{
		super();
		setFont( new Font(Font.DIALOG_INPUT,Font.BOLD,18));
		setForeground(Color.DARK_GRAY);
		EtchedBorder e = new EtchedBorder(EtchedBorder.LOWERED,Color.DARK_GRAY,Color.DARK_GRAY);
		setBorder( new TitledBorder( e,s,TitledBorder.CENTER,TitledBorder.DEFAULT_POSITION,new Font(Font.DIALOG_INPUT,Font.BOLD,18),Color.DARK_GRAY) );
	}
}
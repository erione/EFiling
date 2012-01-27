import javax.swing.*;
import java.awt.*;


public class Jcombobox extends JComboBox
{
	public Jcombobox(Object[] items)
	{	
		super(items);
		setFont( new Font(Font.DIALOG,Font.PLAIN,14));
		setSelectedItem( null );
	}
	public Jcombobox()
	{	
		super();
		setFont( new Font(Font.DIALOG,Font.PLAIN,14));
		setSelectedItem( null );
	}
}
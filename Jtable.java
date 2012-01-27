import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;


public class Jtable extends JTable
{
	public Jtable(Object[][] rowData,Object[] columnData)
	{
		super(rowData,columnData);
		setForeground(Color.DARK_GRAY);
		setFont( new Font(Font.DIALOG_INPUT,Font.BOLD,18));
		setRowHeight(25);
	//	setCellSelectionEnabled( false );		
	}
}
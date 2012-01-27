import java.sql.*;
import javax.swing.*;
import javax.swing.border.*;
import java.io.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

public class EFiling
{
		String userName = null, password = null;
		Connection conn = null;
		public void pass()
		{
			try
			{
					
				Jbutton b1;
		  		pass = new JFrame("Login");
		   		String nativeLF = UIManager.getSystemLookAndFeelClassName(); // Install the look and feel 
			 	UIManager.setLookAndFeel( nativeLF);
			    p1 = new Jtextfield();
           	    p2 = new JPasswordField();
        	    b1 = new Jbutton("OK");
           	    b1.setToolTipText("Login");
           	    pass.add( new Jlabel("Enter Username : ",SwingConstants.CENTER) );
           	    pass.add( p1 );
           	    pass.add( new Jlabel("Enter Password : ",SwingConstants.CENTER) );
           	    pass.add( p2 );
           	    pass.add( new Jlabel("") );
               	pass.add( b1 );
           	   
           	   	KeyStroke ks = KeyStroke.getKeyStroke(KeyEvent.VK_ENTER,0,false);
           	   	p1.registerKeyboardAction(new passListener(), ks, JComponent.WHEN_FOCUSED);
			   	p2.registerKeyboardAction(new passListener(), ks, JComponent.WHEN_FOCUSED);
			   	b1.registerKeyboardAction(new passListener(), ks, JComponent.WHEN_FOCUSED);
           	   	b1.addActionListener( new passListener() );
      		   	pass.setLocation(250,250);
           	   	pass.setLayout( new GridLayout(3,2,7,7) );
           	   	pass.setVisible(true);
           	   	pass.setSize(400,150);
           	   	pass.setAlwaysOnTop(true);
           	   	pass.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		}
		catch(Exception e)
		{
			System.out.println(e.getMessage());
		}
	}
	class passListener implements ActionListener 
	{
		public void actionPerformed( ActionEvent event )
		{
			try
			{
				userName = p1.getText();
				password = p2.getText();
				String url = "jdbc:mysql://localhost/collection";
            	Class.forName ("com.mysql.jdbc.Driver").newInstance ();
            	conn = DriverManager.getConnection (url, userName, password);
		        System.out.println ("Database connection established");	
			    pass.hide();
				main_frame();
				p1=null;p2=null;
				System.gc();
				Runtime.getRuntime().gc();
			}
			catch(Exception e)
			{
				System.err.println( e.getMessage() );
			}
		}
	}
		
		public void main_frame()
		{
			try
			{
				main = new JFrame("Select Company");
				
				Jpanel title = new Jpanel("");
				title.setBackground(Color.GREEN);
				title.setLayout( new BorderLayout( 5,5 ) );
				title.setForeground(Color.DARK_GRAY);
				Jlabel tit_lb = new Jlabel("EXPERT",SwingConstants.CENTER);
				tit_lb.setFont( new Font(Font.DIALOG_INPUT,Font.BOLD,50));
				title.add( tit_lb,BorderLayout.CENTER);
				
				Statement st = conn.createStatement();
				st.executeUpdate("CREATE DATABASE IF NOT EXISTS company");
				st.execute("USE company");
				st.executeUpdate("CREATE TABLE IF NOT EXISTS co(id INT PRIMARY KEY AUTO_INCREMENT, name CHAR(30) , addr CHAR(40),addr1 CHAR(40),addr2 CHAR(40),addr3 CHAR(40), cst CHAR(20), vat CHAR(20), it CHAR(15), lic CHAR(20), auth CHAR(30)) ");
				Jpanel p = new Jpanel("Select Company");
				p.setLayout( new BorderLayout(5,5) );
				st.executeQuery("Select id,name FROM co ORDER BY name");
				ResultSet rs = st.getResultSet();	
				int i = 0;
				while( rs.next() )
					++i;
				rs = null;
				st = null;
				st = conn.createStatement();
				st.executeQuery("Select id,name FROM co ORDER BY name");
				rs = st.getResultSet();
				String [][] row = new String[i][2];
				String [] column = {"          ID","          Company Name"};
				i=0;
				while(rs.next())
				{
					row[i][0] = ""+rs.getInt("id");
					row[i][1] = rs.getString("name").toUpperCase();
					i++;
				}
				t = new Jtable(row,column);
				t.setAutoscrolls(true);
				t.setAutoResizeMode(Jtable.AUTO_RESIZE_ALL_COLUMNS);
				t.getColumn("          Company Name").setMinWidth(400);
			
				KeyStroke ks0 = KeyStroke.getKeyStroke(KeyEvent.VK_F1,0,false);
	 			t.registerKeyboardAction( new newCoListener(), ks0, JComponent.WHEN_FOCUSED);
	 			KeyStroke ks1 = KeyStroke.getKeyStroke(KeyEvent.VK_F3,0,false);
	 			t.registerKeyboardAction( new editCoListener(), ks1, JComponent.WHEN_FOCUSED);
	 			KeyStroke ks2 = KeyStroke.getKeyStroke(KeyEvent.VK_F4,0,false);
	 			t.registerKeyboardAction( new delCoListener(), ks2, JComponent.WHEN_FOCUSED);
				KeyStroke ks = KeyStroke.getKeyStroke(KeyEvent.VK_ENTER,0,false);
	 			t.registerKeyboardAction( new selectCoListener(), ks, JComponent.WHEN_FOCUSED);
	 			KeyStroke ks3 = KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE,0,false);
		 		t.registerKeyboardAction( new hideListener(), ks3, JComponent.WHEN_FOCUSED);

				JScrollPane j = Jtable.createScrollPaneForTable(t);
				j.setWheelScrollingEnabled(true);
				p.add(j,BorderLayout.NORTH);
				Jpanel p1 = new Jpanel("");
				p1.setLayout(new GridLayout(10,1,5,5));
				Jbutton b1 = new Jbutton("F1: Create");
				Jbutton b2 = new Jbutton("F3 : Alter");
				Jbutton b3 = new Jbutton("F4 : Delete");
				p1.add(new Jlabel(""));
				p1.add(b1);
				p1.add(b2);
				p1.add(b3);
				b1.addActionListener( new newCoListener() );
				b2.addActionListener( new editCoListener() );
				b3.addActionListener( new delCoListener() );
				main.add(title,BorderLayout.NORTH);
				main.add(p,BorderLayout.CENTER);
				main.add(p1,BorderLayout.EAST);
				main.setExtendedState(JFrame.MAXIMIZED_BOTH);
				main.setSize( 750,515 );
				main.setLocation( 150,75 );
				main.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
				main.setVisible( true );
				rs.close();
				st.close();		 
			}
			catch(Exception e)
			{
				System.err.println( e.getMessage() );
				e.printStackTrace();
			}
				
		}
		class newCoListener implements ActionListener 
		{
			public void actionPerformed( ActionEvent event )
			{
				addAcc = new JFrame("Add Company");
				j1 = new Jtextfield();
				j2 = new Jtextfield();
				j3 = new Jtextfield();
				j4 = new Jtextfield();
				j5 = new Jtextfield();
				j6 = new Jtextfield();
				j7 = new Jtextfield();
				j8 = new Jtextfield();
				j9 = new Jtextfield();
				j10 = new Jtextfield();
				addAcc.setLayout( new GridBagLayout() );
				GridBagConstraints c = new GridBagConstraints();
				c.fill = GridBagConstraints.BOTH;
				c.insets = new Insets(5,5,5,5);
				c.gridx = 0;
    			c.gridy = 0;
    			c.gridwidth = 1;
    			c.gridheight = 1;
    			c.weightx = c.weighty =0.0;
				addAcc.add( new Jlabel("Company Name : " , SwingConstants.RIGHT),c);
				c.gridx = 1;
    			c.gridy = 0;
    			c.gridwidth = 3;
    			c.gridheight = 1;
    			c.weightx =  3.0;
    			c.weighty =  0.0;
				addAcc.add(j1,c);
				c.gridx = 0;
    			c.gridy = 1;
    			c.gridwidth = 1;
    			c.gridheight = 1;
    			c.weightx = c.weighty = 0.0;
				addAcc.add( new Jlabel("Address : ", SwingConstants.RIGHT),c );
				c.gridx = 1;
    			c.gridy = 1;
    			c.gridwidth = 3;
    			c.gridheight = 1;
    			c.weightx = 0.0;
    			c.weighty = 0.0;
				addAcc.add(j2,c);
				c.gridx = 1;
    			c.gridy = 2;
    			c.gridwidth = 3;
    			c.gridheight = 1;
    			c.weightx = c.weighty = 0.0;
				addAcc.add(j3,c);
				c.gridx = 1;
    			c.gridy = 3;
    			c.gridwidth = 3;
    			c.gridheight = 1;
    			c.weightx = c.weighty = 0.0;
				addAcc.add(j4,c);
				c.gridx = 1;
    			c.gridy = 4;
    			c.gridwidth = 3;
    			c.gridheight = 1;
    			c.weightx = c.weighty = 0.0;
    			addAcc.add(j5,c);
    			c.gridx = 0;
    			c.gridy = 5;
    			c.gridwidth = 1;
    			c.gridheight = 1;
    			c.weightx = c.weighty = 0.0;
				addAcc.add( new Jlabel("CST No. : ", SwingConstants.RIGHT ),c);
				c.gridx = 1;
    			c.gridy = 5;
    			c.gridwidth = 1;
    			c.gridheight = 1;
    			c.weightx = 1.0;
    			c.weighty = 0.0;
				addAcc.add(j6,c);
				c.gridx = 2;
    			c.gridy = 5;
    			c.gridwidth = 1;
    			c.gridheight = 1;
    			c.weightx = c.weighty = 0.0;
				addAcc.add( new Jlabel("VAT No. : ", SwingConstants.RIGHT ),c);
				c.gridx = 3;
    			c.gridy = 5;
    			c.gridwidth = 1;
    			c.gridheight = 1;
    			c.weightx  = 1.0;
    			c.weighty = 0.0;
				addAcc.add(j7,c);
				c.gridx = 0;
    			c.gridy = 6;
    			c.gridwidth = 1;
    			c.gridheight = 1;
    			c.weightx = c.weighty = 0.0;
				addAcc.add( new Jlabel("IT No. : ", SwingConstants.RIGHT ),c);
				c.gridx = 1;
    			c.gridy = 6;
    			c.gridwidth = 1;
    			c.gridheight = 1;
    			c.weightx = c.weighty = 0.0;
				addAcc.add(j8,c);
				c.gridx = 2;
    			c.gridy = 6;
    			c.gridwidth = 1;
    			c.gridheight = 1;
    			c.weightx = c.weighty = 0.0;
				addAcc.add( new Jlabel("License No. : ", SwingConstants.RIGHT ),c);
				c.gridx = 3;
    			c.gridy = 6;
    			c.gridwidth = 1;
    			c.gridheight = 1;
    			c.weightx = c.weighty = 0.0;
				addAcc.add(j9,c);
				c.gridx = 0;
    			c.gridy = 7;
    			c.gridwidth = 1;
    			c.gridheight = 1;
    			c.weightx = c.weighty = 0.0;
				addAcc.add( new Jlabel("Auth. Signatory : ", SwingConstants.RIGHT ),c);
				c.gridx = 1;
    			c.gridy = 7;
    			c.gridwidth = 3;
    			c.gridheight = 1;
    			c.weightx = c.weighty = 0.0;
    			addAcc.add(j10,c);
    			KeyStroke ks0 = KeyStroke.getKeyStroke(KeyEvent.VK_ENTER,0,false);
	 			j1.registerKeyboardAction( new addCoListener(), ks0, JComponent.WHEN_FOCUSED);
	 			j2.registerKeyboardAction( new addCoListener(), ks0, JComponent.WHEN_FOCUSED);
	 			j3.registerKeyboardAction( new addCoListener(), ks0, JComponent.WHEN_FOCUSED);
	 			j4.registerKeyboardAction( new addCoListener(), ks0, JComponent.WHEN_FOCUSED);
	 			j5.registerKeyboardAction( new addCoListener(), ks0, JComponent.WHEN_FOCUSED);
	 			j6.registerKeyboardAction( new addCoListener(), ks0, JComponent.WHEN_FOCUSED);
	 			j7.registerKeyboardAction( new addCoListener(), ks0, JComponent.WHEN_FOCUSED);
	 			j8.registerKeyboardAction( new addCoListener(), ks0, JComponent.WHEN_FOCUSED);
	 			j9.registerKeyboardAction( new addCoListener(), ks0, JComponent.WHEN_FOCUSED);
	 			j10.registerKeyboardAction( new addCoListener(), ks0, JComponent.WHEN_FOCUSED);
	 			KeyStroke ks3 = KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE,0,false);
		 		j1.registerKeyboardAction( new hideListener(), ks3, JComponent.WHEN_FOCUSED);
		 		j2.registerKeyboardAction( new hideListener(), ks3, JComponent.WHEN_FOCUSED);
	 			j3.registerKeyboardAction( new hideListener(), ks3, JComponent.WHEN_FOCUSED);
	 			j4.registerKeyboardAction( new hideListener(), ks3, JComponent.WHEN_FOCUSED);
	 			j5.registerKeyboardAction( new hideListener(), ks3, JComponent.WHEN_FOCUSED);
	 			j6.registerKeyboardAction( new hideListener(), ks3, JComponent.WHEN_FOCUSED);
	 			j7.registerKeyboardAction( new hideListener(), ks3, JComponent.WHEN_FOCUSED);
	 			j8.registerKeyboardAction( new hideListener(), ks3, JComponent.WHEN_FOCUSED);
	 			j9.registerKeyboardAction( new hideListener(), ks3, JComponent.WHEN_FOCUSED);
	 			j10.registerKeyboardAction( new hideListener(), ks3, JComponent.WHEN_FOCUSED);
				j1.addFocusListener( new addF() );
				j2.addFocusListener( new addF() );
				j3.addFocusListener( new addF() );
				j4.addFocusListener( new addF() );
				j5.addFocusListener( new addF() );
				j6.addFocusListener( new addF() );
				j7.addFocusListener( new addF() );
				j8.addFocusListener( new addF() );
				j9.addFocusListener( new addF() );
				j10.addFocusListener( new addF() );
				addAcc.setLocation( 300,200 );
				addAcc.setDefaultCloseOperation( JFrame.HIDE_ON_CLOSE );
    			addAcc.setSize( 750,450 );
    			addAcc.setVisible( true );
    			System.gc();
				Runtime.getRuntime().gc();
			}
		}
	class addF implements FocusListener
	{
		public void focusGained(FocusEvent e)
		{	
			 if(j1.hasFocus()) j1.setBackground(Color.CYAN);
			 else if(j2.hasFocus()) j2.setBackground(Color.CYAN);
			 else if(j3.hasFocus()) j3.setBackground(Color.CYAN);
			 else if(j4.hasFocus()) j4.setBackground(Color.CYAN);
			 else if(j5.hasFocus()) j5.setBackground(Color.CYAN);
			 else if(j6.hasFocus()) j6.setBackground(Color.CYAN);
			 else if(j7.hasFocus()) j7.setBackground(Color.CYAN);
			 else if(j8.hasFocus()) j8.setBackground(Color.CYAN);
			 else if(j9.hasFocus()) j9.setBackground(Color.CYAN);
			 else if(j10.hasFocus()) j10.setBackground(Color.CYAN);
		}
		public void focusLost(FocusEvent e)
		{
			try
			{
				j1.setBackground(Color.WHITE);
				j2.setBackground(Color.WHITE);
				j3.setBackground(Color.WHITE);
				j4.setBackground(Color.WHITE);
				j5.setBackground(Color.WHITE);
				j6.setBackground(Color.WHITE);
				j7.setBackground(Color.WHITE);
				j8.setBackground(Color.WHITE);
				j9.setBackground(Color.WHITE);
				j10.setBackground(Color.WHITE);				
			}
			catch(Exception exp)
			{
			}
		}
	}
	class addCoListener implements ActionListener 
	{
		public void actionPerformed( ActionEvent event )
		{
			try
			{
				PreparedStatement ps;
				ps = conn.prepareStatement("INSERT INTO co(name,addr,addr1,addr2,addr3,cst,vat,it,lic,auth) VALUES(?,?,?,?,?,?,?,?,?,?)");
				ps.setString(1,j1.getText().toUpperCase());
				ps.setString(2,j2.getText().toUpperCase());
				ps.setString(3,j3.getText().toUpperCase());
				ps.setString(4,j4.getText().toUpperCase());
				ps.setString(5,j5.getText().toUpperCase());
				ps.setString(6,j6.getText().toUpperCase());
				ps.setString(7,j7.getText().toUpperCase());
				ps.setString(8,j8.getText().toUpperCase());
				ps.setString(9,j9.getText().toUpperCase());
				ps.setString(10,j10.getText().toUpperCase());
				ps.executeUpdate();
				ps.close();
				addAcc.hide();
				main.hide();main_frame();
				j1=null;j2=null;j3=null;j4=null;j5=null;
				j6=null;j7=null;j8=null;j9=null;j10=null;
				addAcc = null;
				System.gc();
				Runtime.getRuntime().gc();
			}
			catch(Exception e)
			{
				System.err.println( e.getMessage() );
			}
		}
	}
	class editCoListener implements ActionListener
	{
		public void actionPerformed( ActionEvent event )
		{
			try
			{
				addAcc = new JFrame("Edit Company");
				j1 = new Jtextfield();
				j2 = new Jtextfield();
				j3 = new Jtextfield();
				j4 = new Jtextfield();
				j5 = new Jtextfield();
				j6 = new Jtextfield();
				j7 = new Jtextfield();
				j8 = new Jtextfield();
				j9 = new Jtextfield();
				j10 = new Jtextfield();
				
				int r = t.getSelectedRow();
				int i = Integer.parseInt((String)t.getValueAt(r,0));
				PreparedStatement st = conn.prepareStatement("SELECT * FROM co WHERE id =?");
				st.setInt(1,i);
				st.executeQuery();
			
				ResultSet rst = st.getResultSet();
				rst.next();
				j = rst.getInt("id");
						
				j1.setText(rst.getString("name"));
				j2.setText(rst.getString("addr"));
				j3.setText(rst.getString("addr1"));
				j4.setText(rst.getString("addr2"));
				j5.setText(rst.getString("addr3"));
				j6.setText(rst.getString("cst"));
				j7.setText(rst.getString("vat"));
				j8.setText(rst.getString("it"));
				j9.setText(rst.getString("lic"));
				j10.setText(rst.getString("auth"));
				addAcc.setLayout( new GridBagLayout() );
				GridBagConstraints c = new GridBagConstraints();
				c.fill = GridBagConstraints.BOTH;
				c.insets = new Insets(5,5,5,5);
				c.gridx = 0;
    			c.gridy = 0;
    			c.gridwidth = 1;
    			c.gridheight = 1;
    			c.weightx = c.weighty =0.0;
				addAcc.add( new Jlabel("Company Name : " , SwingConstants.RIGHT),c);
				c.gridx = 1;
    			c.gridy = 0;
    			c.gridwidth = 3;
    			c.gridheight = 1;
    			c.weightx =  3.0;
    			c.weighty =  0.0;
				addAcc.add(j1,c);
				c.gridx = 0;
    			c.gridy = 1;
    			c.gridwidth = 1;
    			c.gridheight = 1;
    			c.weightx = c.weighty = 0.0;
				addAcc.add( new Jlabel("Address : ", SwingConstants.RIGHT),c );
				c.gridx = 1;
    			c.gridy = 1;
    			c.gridwidth = 3;
    			c.gridheight = 1;
    			c.weightx = 0.0;
    			c.weighty = 0.0;
				addAcc.add(j2,c);
				c.gridx = 1;
    			c.gridy = 2;
    			c.gridwidth = 3;
    			c.gridheight = 1;
    			c.weightx = c.weighty = 0.0;
				addAcc.add(j3,c);
				c.gridx = 1;
    			c.gridy = 3;
    			c.gridwidth = 3;
    			c.gridheight = 1;
    			c.weightx = c.weighty = 0.0;
				addAcc.add(j4,c);
				c.gridx = 1;
    			c.gridy = 4;
    			c.gridwidth = 3;
    			c.gridheight = 1;
    			c.weightx = c.weighty = 0.0;
    			addAcc.add(j5,c);
    			c.gridx = 0;
    			c.gridy = 5;
    			c.gridwidth = 1;
    			c.gridheight = 1;
    			c.weightx = c.weighty = 0.0;
				addAcc.add( new Jlabel("CST No. : ", SwingConstants.RIGHT ),c);
				c.gridx = 1;
    			c.gridy = 5;
    			c.gridwidth = 1;
    			c.gridheight = 1;
    			c.weightx = 1.0;
    			c.weighty = 0.0;
				addAcc.add(j6,c);
				c.gridx = 2;
    			c.gridy = 5;
    			c.gridwidth = 1;
    			c.gridheight = 1;
    			c.weightx = c.weighty = 0.0;
				addAcc.add( new Jlabel("VAT No. : ", SwingConstants.RIGHT ),c);
				c.gridx = 3;
    			c.gridy = 5;
    			c.gridwidth = 1;
    			c.gridheight = 1;
    			c.weightx  = 1.0;
    			c.weighty = 0.0;
				addAcc.add(j7,c);
				c.gridx = 0;
    			c.gridy = 6;
    			c.gridwidth = 1;
    			c.gridheight = 1;
    			c.weightx = c.weighty = 0.0;
				addAcc.add( new Jlabel("IT No. : ", SwingConstants.RIGHT ),c);
				c.gridx = 1;
    			c.gridy = 6;
    			c.gridwidth = 1;
    			c.gridheight = 1;
    			c.weightx = c.weighty = 0.0;
				addAcc.add(j8,c);
				c.gridx = 2;
    			c.gridy = 6;
    			c.gridwidth = 1;
    			c.gridheight = 1;
    			c.weightx = c.weighty = 0.0;
				addAcc.add( new Jlabel("License No. : ", SwingConstants.RIGHT ),c);
				c.gridx = 3;
    			c.gridy = 6;
    			c.gridwidth = 1;
    			c.gridheight = 1;
    			c.weightx = c.weighty = 0.0;
				addAcc.add(j9,c);
				c.gridx = 0;
    			c.gridy = 7;
    			c.gridwidth = 1;
    			c.gridheight = 1;
    			c.weightx = c.weighty = 0.0;
				addAcc.add( new Jlabel("Auth. Signatory : ", SwingConstants.RIGHT ),c);
				c.gridx = 1;
    			c.gridy = 7;
    			c.gridwidth = 3;
    			c.gridheight = 1;
    			c.weightx = c.weighty = 0.0;
    			KeyStroke ks0 = KeyStroke.getKeyStroke(KeyEvent.VK_ENTER,0,false);
	 			j1.registerKeyboardAction( new updateCoListener(), ks0, JComponent.WHEN_FOCUSED);
	 			j2.registerKeyboardAction( new updateCoListener(), ks0, JComponent.WHEN_FOCUSED);
	 			j3.registerKeyboardAction( new updateCoListener(), ks0, JComponent.WHEN_FOCUSED);
	 			j4.registerKeyboardAction( new updateCoListener(), ks0, JComponent.WHEN_FOCUSED);
	 			j5.registerKeyboardAction( new updateCoListener(), ks0, JComponent.WHEN_FOCUSED);
	 			j6.registerKeyboardAction( new updateCoListener(), ks0, JComponent.WHEN_FOCUSED);
	 			j7.registerKeyboardAction( new updateCoListener(), ks0, JComponent.WHEN_FOCUSED);
	 			j8.registerKeyboardAction( new updateCoListener(), ks0, JComponent.WHEN_FOCUSED);
	 			j9.registerKeyboardAction( new updateCoListener(), ks0, JComponent.WHEN_FOCUSED);
	 			j10.registerKeyboardAction( new updateCoListener(), ks0, JComponent.WHEN_FOCUSED);
	 			KeyStroke ks3 = KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE,0,false);
		 		j1.registerKeyboardAction( new hideListener(), ks3, JComponent.WHEN_FOCUSED);
		 		j2.registerKeyboardAction( new hideListener(), ks3, JComponent.WHEN_FOCUSED);
	 			j3.registerKeyboardAction( new hideListener(), ks3, JComponent.WHEN_FOCUSED);
	 			j4.registerKeyboardAction( new hideListener(), ks3, JComponent.WHEN_FOCUSED);
	 			j5.registerKeyboardAction( new hideListener(), ks3, JComponent.WHEN_FOCUSED);
	 			j6.registerKeyboardAction( new hideListener(), ks3, JComponent.WHEN_FOCUSED);
	 			j7.registerKeyboardAction( new hideListener(), ks3, JComponent.WHEN_FOCUSED);
	 			j8.registerKeyboardAction( new hideListener(), ks3, JComponent.WHEN_FOCUSED);
	 			j9.registerKeyboardAction( new hideListener(), ks3, JComponent.WHEN_FOCUSED);
	 			j10.registerKeyboardAction( new hideListener(), ks3, JComponent.WHEN_FOCUSED);
				addAcc.add(j10,c);
				j1.addFocusListener( new addF() );
				j2.addFocusListener( new addF() );
				j3.addFocusListener( new addF() );
				j4.addFocusListener( new addF() );
				j5.addFocusListener( new addF() );
				j6.addFocusListener( new addF() );
				j7.addFocusListener( new addF() );
				j8.addFocusListener( new addF() );
				j9.addFocusListener( new addF() );
				j10.addFocusListener( new addF() );
				addAcc.setLocation( 300,200 );
				addAcc.setDefaultCloseOperation( JFrame.HIDE_ON_CLOSE );
    			addAcc.setSize( 750,450 );
    			addAcc.setVisible( true );	
    			st.close();
    			rst.close();
    			System.gc();
				Runtime.getRuntime().gc();	
			}
			catch(Exception e)
			{
				System.err.println( e.getMessage() );
			}
				
		}
	}
	class updateCoListener implements ActionListener 
	{
		public void actionPerformed( ActionEvent event )
		{
			try
			{
				PreparedStatement ps;
				ps = conn.prepareStatement("UPDATE co SET name = ?,addr = ?,addr1 = ? ,addr2 = ?,addr3 = ?,cst = ?,vat = ?,it = ?,lic = ? ,auth = ? WHERE id = ?");
				ps.setString(1,j1.getText().toUpperCase());
				ps.setString(2,j2.getText().toUpperCase());
				ps.setString(3,j3.getText().toUpperCase());
				ps.setString(4,j4.getText().toUpperCase());
				ps.setString(5,j5.getText().toUpperCase());
				ps.setString(6,j6.getText().toUpperCase());
				ps.setString(7,j7.getText().toUpperCase());
				ps.setString(8,j8.getText().toUpperCase());
				ps.setString(9,j9.getText().toUpperCase());
				ps.setString(10,j10.getText().toUpperCase());
				ps.setInt(11,j);
				ps.executeUpdate();
				ps.close();
				addAcc.hide();
				main.hide();main_frame();
				j1=null;j2=null;j3=null;j4=null;j5=null;
				j6=null;j7=null;j8=null;j9=null;j10=null;
				addAcc = null;
				ps.close();
				System.gc();
				Runtime.getRuntime().gc();	
			}
			catch(Exception e)
			{
				System.err.println( e.getMessage() );
			}
		}
	}
	class delCoListener implements ActionListener 
	{
		public void actionPerformed( ActionEvent event )
		{
			try
			{
				int op = JOptionPane.showConfirmDialog(null,"Deleting will erase all the data of this\ncompany","Do you want to continue?",JOptionPane.YES_NO_OPTION,JOptionPane.WARNING_MESSAGE);
				if(op == 0)
				{
					PreparedStatement ps;
					int r = t.getSelectedRow();
					int i = Integer.parseInt((String)t.getValueAt(r,0));
					ps = conn.prepareStatement("DELETE FROM co WHERE id = ?");
					ps.setInt(1,i);
					ps.executeUpdate();
					ps.close();
					System.out.println("brijesh");
					Statement s = conn.createStatement();
					String sql = "DROP DATABASE IF EXISTS c"+(String)t.getValueAt(r,0);
					s.execute(sql);
					s.close();
					main.hide();main_frame();
					j1=null;j2=null;j3=null;j4=null;j5=null;
					j6=null;j7=null;j8=null;j9=null;j10=null;
					addAcc = null;
					System.gc();
				Runtime.getRuntime().gc();	
				}	
				
			}
			catch(Exception e)
			{
				System.err.println( e.getMessage() );
			}
		}
	}
	
	public void select_frame()
	{
		try
		{
					
					Jpanel title = new Jpanel("");
					title.setBackground(Color.GREEN);
					title.setLayout( new BorderLayout( 5,5 ) );
					title.setForeground(Color.DARK_GRAY);
					Jlabel tit_lb = new Jlabel("EXPERT",SwingConstants.CENTER);
					tit_lb.setFont( new Font(Font.DIALOG_INPUT,Font.BOLD,30));
					title.add( tit_lb,BorderLayout.CENTER);
					
					String id = (String)t.getValueAt(t.getSelectedRow(),0);
					String s = new String( (String)t.getValueAt(t.getSelectedRow(),1));
					select = new JFrame("");
					select.setLayout( new BorderLayout(5,5) );
					Jpanel p = new Jpanel("");
					Jlabel lb = new Jlabel( s );
					lb.setFont( new Font(Font.MONOSPACED,Font.BOLD,20)) ;
					p.setBackground( Color.PINK );
					p.add( lb );
				
					Statement st = conn.createStatement();
					String sql = new String("CREATE DATABASE IF NOT EXISTS c"+id);
					st.execute(sql);
					
					sql = new String("USE c"+id);
					st.execute(sql);
					st.execute("CREATE TABLE IF NOT EXISTS grp(name CHAR(30),under CHAR(30),sub ENUM('Yes','No'),bal FLOAT)");
					st.execute("CREATE TABLE IF NOT EXISTS ledg(name CHAR(30),under CHAR(30),op_bal FLOAT,type ENUM('Cr','Dr'),addr1 CHAR(30),addr2 CHAR(30),addr3 CHAR(30),state CHAR(15),it CHAR(15),vat CHAR(15),cst CHAR(15))");
				//	st.execute("CREATE TABLE IF NOT EXISTS sale()");				
					st.close();
						System.gc();
				Runtime.getRuntime().gc();
					
	/**********************Panel for account info *********************************/
			Jpanel p_grp = new Jpanel("");
			p_grp.setLayout( new BorderLayout(5,5) );
					int i = 0;
					Statement st1 = conn.createStatement();
					st1.executeQuery("SELECT name,under FROM grp ORDER BY name");
					ResultSet rs1 = st1.getResultSet();
					while( rs1.next() )
						++i;
				
					rs1 = null;
					st1 = null;
					st1 = conn.createStatement();
					st1.executeQuery("SELECT name,under FROM grp ORDER BY name");
					rs1 = st1.getResultSet();
					
					String [][] row = new String[i][2];
					String [] column = {"          Ledger Group Name","               Under"};
					i=0;
					while(rs1.next())
					{
						row[i][0] = rs1.getString("name");
						row[i][1] = rs1.getString("under");
						++i;
					}
				
					rs1.close();
					st1.close();
					t1 = new Jtable(row,column);
					t1.setAutoscrolls(true);
					t1.setAutoResizeMode(Jtable.AUTO_RESIZE_ALL_COLUMNS);
					t1.getColumn("          Ledger Group Name").setMinWidth(400);
					
			
					KeyStroke ks0 = KeyStroke.getKeyStroke(KeyEvent.VK_F1,0,false);
		 			t1.registerKeyboardAction( new newLedgrpListener(), ks0, JComponent.WHEN_FOCUSED);
		 			KeyStroke ks1 = KeyStroke.getKeyStroke(KeyEvent.VK_F3,0,false);
		 			t1.registerKeyboardAction( new editLedgrpListener(), ks1, JComponent.WHEN_FOCUSED);
		 			KeyStroke ks2 = KeyStroke.getKeyStroke(KeyEvent.VK_F4,0,false);
		 			t1.registerKeyboardAction( new delLedgrpListener(), ks2, JComponent.WHEN_FOCUSED);
		 			KeyStroke ks3 = KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE,0,false);
		 			t1.registerKeyboardAction( new hideListener(), ks3, JComponent.WHEN_FOCUSED);
					
					JScrollPane jsp1 = Jtable.createScrollPaneForTable(t1);
					jsp1.setWheelScrollingEnabled(true);
					Jpanel p_l1 = new Jpanel("Ledger Groups");
					p_l1.setLayout( new BorderLayout(5,5) );
					p_l1.add(jsp1,BorderLayout.NORTH);
					Jpanel p_l2 = new Jpanel("");
					p_l2.setLayout(new GridLayout(10,1,5,5));
					Jbutton b1 = new Jbutton("F1: Create");
					Jbutton b2 = new Jbutton("F3 : Alter");
					Jbutton b3 = new Jbutton("F4 : Delete");
					p_l2.add(new Jlabel(""));
					p_l2.add(b1);
					p_l2.add(b2);
					p_l2.add(b3);
					b1.addActionListener( new newLedgrpListener() );
					b2.addActionListener( new editLedgrpListener() );
					b3.addActionListener( new delLedgrpListener() );
			p_grp.add(p_l1,BorderLayout.CENTER);
			p_grp.add(p_l2,BorderLayout.EAST);
	/*****************************************************************************/	
			Jpanel p_ledger = new Jpanel("Ledger Groups");
			p_ledger.setLayout( new BorderLayout(5,5) );
			i=0;
			st1 = conn.createStatement();
					st1.executeQuery("SELECT name,under FROM ledg ORDER BY name");
					rs1 = st1.getResultSet();
					while( rs1.next() )
						++i;
				
					rs1 = null;
					st1 = null;
					st1 = conn.createStatement();
					st1.executeQuery("SELECT name,under FROM ledg ORDER BY name");
					rs1 = st1.getResultSet();
					
					String [][] row1 = new String[i][2];
					String [] column1 = {"               Ledger Name","               Under"};
					i=0;
					while(rs1.next())
					{
						row1[i][0] = rs1.getString("name");
						row1[i][1] = rs1.getString("under");
						++i;
					}
				
					rs1.close();
					st1.close();
					t2 = new Jtable(row1,column1);
					t2.setAutoscrolls(true);
					t2.setAutoResizeMode(Jtable.AUTO_RESIZE_ALL_COLUMNS);
					t2.getColumn("               Ledger Name").setMinWidth(400);
					JScrollPane jsp2 = Jtable.createScrollPaneForTable(t2);
					jsp2.setWheelScrollingEnabled(true);
					Jpanel p_l3 = new Jpanel("");
					p_l3.setLayout( new BorderLayout(5,5) );
					p_l3.add(jsp2,BorderLayout.NORTH);
					
					Jpanel p_l4 = new Jpanel("");
					p_l4.setLayout(new GridLayout(10,1,5,5));
					Jbutton b4 = new Jbutton("F1: Create");
					Jbutton b5 = new Jbutton("F3 : Alter");
					Jbutton b6 = new Jbutton("F4 : Delete");
					p_l4.add(new Jlabel(""));
					p_l4.add(b4);
					p_l4.add(b5);
					p_l4.add(b6);
					b4.addActionListener( new newLedgerListener() );
					b5.addActionListener( new editLedgerListener() );
					b6.addActionListener( new delLedgerListener() );
					
					ks0 = KeyStroke.getKeyStroke(KeyEvent.VK_F1,0,false);
		 			t2.registerKeyboardAction( new newLedgerListener(), ks0, JComponent.WHEN_FOCUSED);
		 			ks1 = KeyStroke.getKeyStroke(KeyEvent.VK_F3,0,false);
		 			t2.registerKeyboardAction( new editLedgerListener(), ks1, JComponent.WHEN_FOCUSED);
		 			ks2 = KeyStroke.getKeyStroke(KeyEvent.VK_F4,0,false);
		 			t2.registerKeyboardAction( new delLedgerListener(), ks2, JComponent.WHEN_FOCUSED);
		 			ks3 = KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE,0,false);
		 			t2.registerKeyboardAction( new hideListener(), ks3, JComponent.WHEN_FOCUSED);
					
			p_ledger.add(p_l3,BorderLayout.CENTER);
			p_ledger.add(p_l4,BorderLayout.EAST);	
	/*****************************************************************************/			
		Jtabbedpane tbp1 = new Jtabbedpane();
		tbp1.addTab( " Groups " , p_grp);
		tbp1.addTab( " Ledgers ", p_ledger);	
	
	
	/**********************Panel for sale details*********************************/
	
	Jpanel p_sale = new Jpanel("");
	/*****************************************************************************/	
	
	/**********************Panel for purchase details*********************************/
	
	Jpanel p_pur = new Jpanel("");
	/*****************************************************************************/	
	
	/**********************Panel for efiling*********************************/
	
	Jpanel p_efi = new Jpanel("");
	/*****************************************************************************/				
					
					Jtabbedpane tbp = new Jtabbedpane();
					tbp.addTab( " Accounts Info. " ,tbp1 );
					tbp.addTab( "    Sale    " , p_sale );
					tbp.addTab( "  Purchase  " , p_pur );
					tbp.addTab( "  E-Filing  " , p_efi );
					
					select.add(title,BorderLayout.NORTH);
					select.add(p,BorderLayout.SOUTH);
					select.add(tbp,BorderLayout.CENTER);
					select.setSize(700,500);
					select.setLocation(150,150);
					select.setExtendedState(JFrame.MAXIMIZED_BOTH);
					select.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
					select.setVisible( true );
					System.gc();
				Runtime.getRuntime().gc();	
			}
			catch(Exception e)
			{
				System.err.println( e.getMessage() );
				e.printStackTrace(System.out);
			}	
	}
	class hideListener implements ActionListener
	{
		public void actionPerformed( ActionEvent event )
		{
			try
			{
				if(addGrp!=null && addGrp.isActive()) addGrp.dispose();
				else if(addLeg!=null && addLeg.isActive()) addLeg.dispose();
				else if(addAcc!=null && addAcc.isActive()) addAcc.dispose();
				else if(select!=null && select.isActive())
				{ 
					select.dispose();
					Statement s = conn.createStatement();
					s.execute("USE company");
				}
				else if(main.isActive())
				{
					 main.dispose();
					 System.exit(0);
				}
					System.gc();
				Runtime.getRuntime().gc();
			}
			catch(Exception e)
			{
				System.err.println( e.getMessage() );
			}
		}
		
	}
	class selectCoListener implements ActionListener
	{
		public void actionPerformed( ActionEvent event )
		{
			select_frame();
				System.gc();
				Runtime.getRuntime().gc();
		}
		
	}
	class newLedgrpListener implements ActionListener
	{
		public void actionPerformed( ActionEvent event )
		{
			try
			{
				j1 = new Jtextfield();
				String data1[] = {"PRIMARY"};
				String data2[] = {"Yes","No"};
				jc1 = new Jcombobox(data1);
				jc2 = new Jcombobox(data2);
				Jlabel lb_gr = new Jlabel("Behaves as a sub-group : ",SwingConstants.RIGHT);
				lb_gr.setFont( new Font(Font.DIALOG_INPUT,Font.BOLD,12));
				
				Statement st1 = conn.createStatement();
				st1.executeQuery("SELECT name FROM grp ORDER BY name");
				ResultSet rs1 = st1.getResultSet();
				while(rs1.next())
				{
					jc1.addItem((String)rs1.getString("name") );
				}
				jc2.setSelectedIndex(0);
				addGrp = new JFrame("Add Group");
				addGrp.setLayout( new GridLayout(3,2,5,5) );
				addGrp.add( new Jlabel("Name : ",SwingConstants.RIGHT) );
				addGrp.add( j1 );
				addGrp.add( new Jlabel("Under : ",SwingConstants.RIGHT) );
				addGrp.add( jc1 );
				addGrp.add( lb_gr );
				addGrp.add( jc2 );
				KeyStroke ks0 = KeyStroke.getKeyStroke(KeyEvent.VK_ENTER,0,false);
	 			j1.registerKeyboardAction( new addLedgrpListener(), ks0, JComponent.WHEN_FOCUSED);
	 			jc1.registerKeyboardAction( new addLedgrpListener(), ks0, JComponent.WHEN_FOCUSED);
	 			jc2.registerKeyboardAction( new addLedgrpListener(), ks0, JComponent.WHEN_FOCUSED);
	 			KeyStroke ks3 = KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE,0,false);
		 		j1.registerKeyboardAction( new hideListener(), ks3, JComponent.WHEN_FOCUSED);
	 			jc1.registerKeyboardAction( new hideListener(), ks3, JComponent.WHEN_FOCUSED);
	 			jc2.registerKeyboardAction( new hideListener(), ks3, JComponent.WHEN_FOCUSED);
	 			
	 			j1.addFocusListener( new addF() );
	 				 			
				addGrp.setSize(400,125);
				addGrp.setLocation(300,200);
				addGrp.setVisible( true );
					System.gc();
				Runtime.getRuntime().gc();
				
			}	
			catch(Exception e)
			{
				System.err.println( e.getMessage() );
			}
		}	
	}
	class addLedgrpListener implements ActionListener
	{
		public void actionPerformed( ActionEvent event )
		{
			try
			{
				PreparedStatement ps;
				ps = conn.prepareStatement("INSERT INTO grp(name,under,sub,bal) VALUES (?,?,?,?)");
				ps.setString(1,j1.getText().toUpperCase() );
				ps.setString(2, ((String) jc1.getSelectedItem()).toUpperCase() );
				ps.setString(3, (String) jc2.getSelectedItem() );
				ps.setDouble(4,0.0 );
				ps.executeUpdate();
				ps.close();
				addGrp.dispose();
				select.dispose();
				select_frame();
				j1=null;
				jc1=null;jc2=null;
				addGrp=null;
					System.gc();
				Runtime.getRuntime().gc();
			}	
			catch(Exception e)
			{
				System.err.println( e.getMessage() );
			}
		}	
	}
	class editLedgrpListener implements ActionListener
	{
		public void actionPerformed( ActionEvent event )
		{
			try
			{
				j1 = new Jtextfield();
				String data1[] = {"PRIMARY"};
				String data2[] = {"Yes","No"};
				jc1 = new Jcombobox(data1);
				jc2 = new Jcombobox(data2);
				Jlabel lb_gr = new Jlabel("Behaves as a sub-group : ",SwingConstants.RIGHT);
				lb_gr.setFont( new Font(Font.DIALOG_INPUT,Font.BOLD,12));
				
				Statement st1 = conn.createStatement();
				st1.executeQuery("SELECT name FROM grp ORDER BY name");
				ResultSet rs1 = st1.getResultSet();	
				while(rs1.next())
				{
					jc1.addItem((String)rs1.getString("name") );
				}
				String sql = "SELECT sub FROM grp WHERE name = '" +( (String)t1.getValueAt( t1.getSelectedRow(), 0) )+"'";
				st1.executeQuery(sql);
				rs1 = st1.getResultSet();
				rs1.next();
				
				j1.setText( (String)t1.getValueAt( t1.getSelectedRow(), 0) );
				jc1.setSelectedItem( (String)t1.getValueAt( t1.getSelectedRow(), 1) );
				jc2.setSelectedItem((String)rs1.getString("sub"));
				
				rs1.close();
				st1.close();
				
				addGrp = new JFrame("Edit Group");
				addGrp.setLayout( new GridLayout(3,2,5,5) );
				addGrp.add( new Jlabel("Name : ",SwingConstants.RIGHT) );
				addGrp.add( j1 );
				addGrp.add( new Jlabel("Under : ",SwingConstants.RIGHT) );
				addGrp.add( jc1 );
				addGrp.add( lb_gr );
				addGrp.add( jc2 );
				KeyStroke ks0 = KeyStroke.getKeyStroke(KeyEvent.VK_ENTER,0,false);
	 			j1.registerKeyboardAction( new updateLedgrpListener(), ks0, JComponent.WHEN_FOCUSED);
	 			jc1.registerKeyboardAction( new updateLedgrpListener(), ks0, JComponent.WHEN_FOCUSED);
	 			jc2.registerKeyboardAction( new updateLedgrpListener(), ks0, JComponent.WHEN_FOCUSED);
	 			KeyStroke ks3 = KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE,0,false);
		 		j1.registerKeyboardAction( new hideListener(), ks3, JComponent.WHEN_FOCUSED);
	 			jc1.registerKeyboardAction( new hideListener(), ks3, JComponent.WHEN_FOCUSED);
	 			jc2.registerKeyboardAction( new hideListener(), ks3, JComponent.WHEN_FOCUSED);
	 			
	 			j1.addFocusListener( new addF() );
	 			
				addGrp.setSize(400,125);
				addGrp.setLocation(300,200);
				addGrp.setVisible( true );
					System.gc();
				Runtime.getRuntime().gc();					
			
			}	
			catch(Exception e)
			{
				System.err.println( e.getMessage() );
			}
		}	
	}
	class updateLedgrpListener implements ActionListener
	{
		public void actionPerformed( ActionEvent event )
		{
			try
			{
				
				PreparedStatement ps;
				ps = conn.prepareStatement("UPDATE grp SET name = ? ,under = ? ,sub = ? ,bal = ? WHERE name = ?");
				ps.setString(1,j1.getText().toUpperCase() );
				ps.setString(2, ((String) jc1.getSelectedItem()).toUpperCase() );
				ps.setString(3, (String) jc2.getSelectedItem() );
				ps.setDouble(4,0.0 );
				ps.setString(5,  (String)t1.getValueAt( t1.getSelectedRow(), 0) );
				ps.executeUpdate();
				
				ps = conn.prepareStatement("UPDATE grp SET under = ? WHERE under = ?");
				ps.setString(1,j1.getText().toUpperCase() );
				ps.setString(2,  (String)t1.getValueAt( t1.getSelectedRow(), 0) );
				ps.executeUpdate();
				
				ps.close();
				addGrp.dispose();
				select.dispose();
				select_frame();
				j1=null;
				jc1=null;jc2=null;
				addGrp=null;
					System.gc();
				Runtime.getRuntime().gc();
			
			}	
			catch(Exception e)
			{
				System.err.println( e.getMessage() );
			}
		}	
	}
	class delLedgrpListener implements ActionListener 
	{
		public void actionPerformed( ActionEvent event )
		{
			try
			{
				int op = JOptionPane.showConfirmDialog(null,"Deleting will erase all the data under this group","Do you want to continue?",JOptionPane.YES_NO_OPTION,JOptionPane.WARNING_MESSAGE);
				if(op == 0)
				{
					PreparedStatement ps;
					int r = t1.getSelectedRow();
					ps = conn.prepareStatement("DELETE FROM grp WHERE name = ?");
					ps.setString( 1,(String)t1.getValueAt(r,0) );
					ps.executeUpdate();
					ps.close();
					select.dispose();
					select_frame();					
					j1=null;j2=null;j3=null;j4=null;j5=null;
					j6=null;j7=null;j8=null;j9=null;j10=null;
					addAcc = null;
						System.gc();
				Runtime.getRuntime().gc();
				}	
				
			}
			catch(Exception e)
			{
				System.err.println( e.getMessage() );
			}
		}
	}
/////////////////////////////////////////////////////////////////////////////////
class newLedgerListener implements ActionListener
	{
		public void actionPerformed( ActionEvent event )
		{
			try
			{
				addLeg = new JFrame("Create Ledger");
				String[] state ={"None", "Andaman and Nicobar Islands","Andhra Pradesh","Arunachal Pradesh","Assam","Bihar","Chandigarh","Chattisgarh","Daman And  Diu","Delhi","Dadra and Nagar Haveli","Goa","Gujarat","Himachal Pradesh","Haryana","Jammu And Kashmir","Jharkand","Karnataka","Kerala","Lakshadweep","Meghalaya","Maharastra","Manipur","Madhya Pradesh","Mizoram","Nagaland","Orissa","Punjab","Pondichery","Rajasthan","Sikkim","Tamil Nadu","Tripura","Uttaranchal","Uttar Pradesh","West Bengal" };
				String[] type ={ "Dr","Cr"	};
				j1 = new Jtextfield();
				j2 = new Jtextfield("0.0");
				j3 = new Jtextfield();
				j4 = new Jtextfield();
				j5 = new Jtextfield();
				j6 = new Jtextfield();
				j7 = new Jtextfield();
				j8 = new Jtextfield();
				j9 = new Jtextfield();
				jc1 = new Jcombobox();
				jc2 = new Jcombobox(type);
				jc1.addItem("Select");
				jc1.setSelectedIndex(0);
				jc2.setSelectedIndex(0);
				jc3 = new Jcombobox(state);
				jc3.setSelectedIndex(0);
				
				Statement st = conn.createStatement();
				
				st.executeQuery("SELECT name FROM grp");
				
				ResultSet rst = st.getResultSet();
				while(rst.next())
				{
					jc1.addItem( rst.getString("name") );
				}
				addLeg.setLayout( new GridBagLayout() );
				GridBagConstraints c = new GridBagConstraints();
				c.fill = GridBagConstraints.BOTH;
				c.insets = new Insets(5,5,5,5);
				c.gridx = 0;
    			c.gridy = 0;
    			c.gridwidth = 1;
    			c.gridheight = 1;
    			c.weightx = 0.5;
    			c.weighty =0.0;
    			addLeg.add( new Jlabel(" Name :" , SwingConstants.RIGHT),c);
    			
    			c.gridx = 1;
    			c.gridy = 0;
    			c.gridwidth = 3;
    			c.gridheight = 1;
    			c.weightx =  3.0;
    			c.weighty =  0.0;
				addLeg.add(j1,c);
    			
    			c.gridx = 0;
    			c.gridy = 1;
    			c.gridwidth = 1;
    			c.gridheight = 1;
    			c.weightx =  0.0;
    			c.weighty =  0.0;
				addLeg.add( new Jlabel(" Under :" , SwingConstants.RIGHT),c);
			
				c.gridx = 1;
    			c.gridy = 1;
    			c.gridwidth = 3;
    			c.gridheight = 1;
    			c.weightx =  0.0;
    			c.weighty =  0.0;
    			addLeg.add(jc1,c);
    			
    			c.gridx = 0;
    			c.gridy = 2;
    			c.gridwidth = 1;
    			c.gridheight = 1;
    			c.weightx =  1.0;
    			c.weighty =  0.0;
				addLeg.add( new Jlabel(" Op. Balance :" , SwingConstants.RIGHT),c);
				
				c.gridx = 1;
    			c.gridy = 2;
    			c.gridwidth = 2;
    			c.gridheight = 1;
    			c.weightx =  2.0;
    			c.weighty =  0.0;
				addLeg.add(j2,c);
				
				c.gridx = 3;
    			c.gridy = 2;
    			c.gridwidth = 1;
    			c.gridheight = 1;
    			c.weightx =  0.25;
    			c.weighty =  0.0;
				addLeg.add(jc2,c);
				
				c.gridx = 0;
    			c.gridy = 3;
    			c.gridwidth = 1;
    			c.gridheight = 1;
    			c.weightx =  0.5;
    			c.weighty =  0.0;
				addLeg.add(new Jlabel(" Address :" , SwingConstants.RIGHT),c);
				
				c.gridx = 1;
    			c.gridy = 3;
    			c.gridwidth = 3;
    			c.gridheight = 1;
    			c.weightx =  3.0;
    			c.weighty =  0.0;
				addLeg.add(j3,c);
				
				c.gridx = 1;
    			c.gridy = 4;
    			c.gridwidth = 3;
    			c.gridheight = 1;
    			c.weightx =  0.0;
    			c.weighty =  0.0;
				addLeg.add(j4,c);
				
				c.gridx = 1;
    			c.gridy = 5;
    			c.gridwidth = 3;
    			c.gridheight = 1;
    			c.weightx =  0.0;
    			c.weighty =  0.0;
				addLeg.add(j5,c);
				
				c.gridx = 0;
    			c.gridy = 6;
    			c.gridwidth = 1;
    			c.gridheight = 1;
    			c.weightx =  0.5;
    			c.weighty =  0.0;
				addLeg.add(new Jlabel(" State :" , SwingConstants.RIGHT),c);
				
				c.gridx = 1;
    			c.gridy = 6;
    			c.gridwidth = 2;
    			c.gridheight = 1;
    			c.weightx =  2.0;
    			c.weighty =  0.0;
				addLeg.add(jc3,c);
				
				c.gridx = 0;
    			c.gridy = 7;
    			c.gridwidth = 1;
    			c.gridheight = 1;
    			c.weightx =  0.5;
    			c.weighty =  0.0;
				addLeg.add(new Jlabel(" IT no. :" , SwingConstants.RIGHT),c);
				
				c.gridx = 1;
    			c.gridy = 7;
    			c.gridwidth = 1;
    			c.gridheight = 1;
    			c.weightx =  1.5;
    			c.weighty =  0.0;
				addLeg.add(j6,c);
				
				c.gridx = 0;
    			c.gridy = 8;
    			c.gridwidth = 1;
    			c.gridheight = 1;
    			c.weightx =  0.5;
    			c.weighty =  0.0;
				addLeg.add(new Jlabel(" VAT no. :" , SwingConstants.RIGHT),c);
				
				c.gridx = 1;
    			c.gridy = 8;
    			c.gridwidth = 1;
    			c.gridheight = 1;
    			c.weightx =  2.5;
    			c.weighty =  0.0;
				addLeg.add(j7,c);
				
				c.gridx = 0;
    			c.gridy = 9;
    			c.gridwidth = 1;
    			c.gridheight = 1;
    			c.weightx =  0.5;
    			c.weighty =  0.0;
				addLeg.add(new Jlabel(" CST no. :" , SwingConstants.RIGHT),c);
				
				c.gridx = 1;
    			c.gridy = 9;
    			c.gridwidth = 1;
    			c.gridheight = 1;
    			c.weightx =  2.5;
    			c.weighty =  0.0;
				addLeg.add(j8,c);
				
				KeyStroke ks0 = KeyStroke.getKeyStroke(KeyEvent.VK_ENTER,0,false);
	 			jc1.registerKeyboardAction( new addLedgerListener(), ks0, JComponent.WHEN_FOCUSED);
	 			jc2.registerKeyboardAction( new addLedgerListener(), ks0, JComponent.WHEN_FOCUSED);
	 			jc3.registerKeyboardAction( new addLedgerListener(), ks0, JComponent.WHEN_FOCUSED);
	 			j1.registerKeyboardAction( new addLedgerListener(), ks0, JComponent.WHEN_FOCUSED);
	 			j2.registerKeyboardAction( new addLedgerListener(), ks0, JComponent.WHEN_FOCUSED);
	 			j3.registerKeyboardAction( new addLedgerListener(), ks0, JComponent.WHEN_FOCUSED);
	 			j4.registerKeyboardAction( new addLedgerListener(), ks0, JComponent.WHEN_FOCUSED);
	 			j5.registerKeyboardAction( new addLedgerListener(), ks0, JComponent.WHEN_FOCUSED);
	 			j6.registerKeyboardAction( new addLedgerListener(), ks0, JComponent.WHEN_FOCUSED);
	 			j7.registerKeyboardAction( new addLedgerListener(), ks0, JComponent.WHEN_FOCUSED);
	 			j8.registerKeyboardAction( new addLedgerListener(), ks0, JComponent.WHEN_FOCUSED);
	 			
	 			
	 			KeyStroke ks3 = KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE,0,false);
		 		jc1.registerKeyboardAction( new hideListener(), ks3, JComponent.WHEN_FOCUSED);
	 			jc2.registerKeyboardAction( new hideListener(), ks3, JComponent.WHEN_FOCUSED);
	 			jc3.registerKeyboardAction( new hideListener(), ks3, JComponent.WHEN_FOCUSED);
	 			j1.registerKeyboardAction( new hideListener(), ks3, JComponent.WHEN_FOCUSED);
	 			j2.registerKeyboardAction( new hideListener(), ks3, JComponent.WHEN_FOCUSED);
	 			j3.registerKeyboardAction( new hideListener(), ks3, JComponent.WHEN_FOCUSED);
	 			j4.registerKeyboardAction( new hideListener(), ks3, JComponent.WHEN_FOCUSED);
	 			j5.registerKeyboardAction( new hideListener(), ks3, JComponent.WHEN_FOCUSED);
	 			j6.registerKeyboardAction( new hideListener(), ks3, JComponent.WHEN_FOCUSED);
	 			j7.registerKeyboardAction( new hideListener(), ks3, JComponent.WHEN_FOCUSED);
	 			j8.registerKeyboardAction( new hideListener(), ks3, JComponent.WHEN_FOCUSED);
	 			
	 			j1.addFocusListener( new addF() );
				j2.addFocusListener( new addF() );
				j3.addFocusListener( new addF() );
				j4.addFocusListener( new addF() );
				j5.addFocusListener( new addF() );
				j6.addFocusListener( new addF() );
				j7.addFocusListener( new addF() );
				j8.addFocusListener( new addF() );
	 			
				addLeg.setSize(460,450);
				addLeg.setLocation(200,200);
				addLeg.setVisible( true );
					System.gc();
				Runtime.getRuntime().gc();	
			}	
			catch(Exception e)
			{
				System.err.println( e.getMessage() );
			}
		}	
	}
	class addLedgerListener implements ActionListener
	{
		public void actionPerformed( ActionEvent event )
		{
			try
			{
				PreparedStatement ps;
				ps = conn.prepareStatement("INSERT INTO ledg(name,under,op_bal,type,addr1,addr2,addr3,state,it,vat,cst) VALUES (?,?,?,?,?,?,?,?,?,?,?)");
				ps.setString(1,(String)j1.getText().toUpperCase());
				ps.setString(2,(String)jc1.getSelectedItem());
				ps.setDouble(3,Double.parseDouble(j2.getText()));
				ps.setString(4,(String)jc2.getSelectedItem());
				ps.setString(5,(String)j3.getText().toUpperCase());
				ps.setString(6,(String)j4.getText().toUpperCase());
				ps.setString(7,(String)j5.getText().toUpperCase());
				ps.setString(8,(String)jc3.getSelectedItem());
				ps.setString(9,(String)j6.getText().toUpperCase());
				ps.setString(10,(String)j7.getText().toUpperCase());
				ps.setString(11,(String)j8.getText().toUpperCase());
				ps.executeUpdate();
				ps.close();
				addLeg.dispose();
				j1=null;j2=null;j3=null;j4=null;j5=null;
				j6=null;j7=null;j8=null;jc1=null;jc2=null;jc3=null;
				addLeg=null;
					System.gc();
				Runtime.getRuntime().gc();
			}	
			catch(Exception e)
			{
				System.err.println( e.getMessage() );
			}
		}	
	}
	class editLedgerListener implements ActionListener
	{
		public void actionPerformed( ActionEvent event )
		{
			try
			{
				addLeg = new JFrame("Alter Ledger");
				String[] state ={"None", "Andaman and Nicobar Islands","Andhra Pradesh","Arunachal Pradesh","Assam","Bihar","Chandigarh","Chattisgarh","Daman And  Diu","Delhi","Dadra and Nagar Haveli","Goa","Gujarat","Himachal Pradesh","Haryana","Jammu And Kashmir","Jharkand","Karnataka","Kerala","Lakshadweep","Meghalaya","Maharastra","Manipur","Madhya Pradesh","Mizoram","Nagaland","Orissa","Punjab","Pondichery","Rajasthan","Sikkim","Tamil Nadu","Tripura","Uttaranchal","Uttar Pradesh","West Bengal" };
				String[] type ={ "Dr","Cr"	};
				j1 = new Jtextfield();
				j2 = new Jtextfield();
				j3 = new Jtextfield();
				j4 = new Jtextfield();
				j5 = new Jtextfield();
				j6 = new Jtextfield();
				j7 = new Jtextfield();
				j8 = new Jtextfield();
				j9 = new Jtextfield();
				jc1 = new Jcombobox();
				jc2 = new Jcombobox(type);
				jc1.addItem("Select");
				jc3 = new Jcombobox(state);
								
				Statement st = conn.createStatement();
				
				st.executeQuery("SELECT name FROM grp");
				
				ResultSet rst = st.getResultSet();
				while(rst.next())
				{
					jc1.addItem( rst.getString("name") );
				}
				
				st.executeQuery(new String("SELECT * FROM ledg WHERE name = '" + (String)t2.getValueAt( t2.getSelectedRow(), 0) + "'") );
			
				rst = st.getResultSet();
				rst.next();
				
				j1.setText( rst.getString(1) );
				j2.setText( rst.getString(3) );
				j3.setText( rst.getString(5) );
				j4.setText( rst.getString(6) );
				j5.setText( rst.getString(7) );
				j6.setText( rst.getString(9) );
				j7.setText( rst.getString(10) );
				j8.setText( rst.getString(11) );
				jc1.setSelectedItem( (String)rst.getString(2) );
				jc2.setSelectedItem( (String)rst.getString(4) );
				jc3.setSelectedItem( (String)rst.getString(8) );
				
				
				addLeg.setLayout( new GridBagLayout() );
				GridBagConstraints c = new GridBagConstraints();
				c.fill = GridBagConstraints.BOTH;
				c.insets = new Insets(5,5,5,5);
				c.gridx = 0;
    			c.gridy = 0;
    			c.gridwidth = 1;
    			c.gridheight = 1;
    			c.weightx = 0.5;
    			c.weighty =0.0;
    			addLeg.add( new Jlabel(" Name :" , SwingConstants.RIGHT),c);
    			
    			c.gridx = 1;
    			c.gridy = 0;
    			c.gridwidth = 3;
    			c.gridheight = 1;
    			c.weightx =  3.0;
    			c.weighty =  0.0;
				addLeg.add(j1,c);
    			
    			c.gridx = 0;
    			c.gridy = 1;
    			c.gridwidth = 1;
    			c.gridheight = 1;
    			c.weightx =  0.0;
    			c.weighty =  0.0;
				addLeg.add( new Jlabel(" Under :" , SwingConstants.RIGHT),c);
			
				c.gridx = 1;
    			c.gridy = 1;
    			c.gridwidth = 3;
    			c.gridheight = 1;
    			c.weightx =  0.0;
    			c.weighty =  0.0;
    			addLeg.add(jc1,c);
    			
    			c.gridx = 0;
    			c.gridy = 2;
    			c.gridwidth = 1;
    			c.gridheight = 1;
    			c.weightx =  1.0;
    			c.weighty =  0.0;
				addLeg.add( new Jlabel(" Op. Balance :" , SwingConstants.RIGHT),c);
				
				c.gridx = 1;
    			c.gridy = 2;
    			c.gridwidth = 2;
    			c.gridheight = 1;
    			c.weightx =  2.0;
    			c.weighty =  0.0;
				addLeg.add(j2,c);
				
				c.gridx = 3;
    			c.gridy = 2;
    			c.gridwidth = 1;
    			c.gridheight = 1;
    			c.weightx =  0.25;
    			c.weighty =  0.0;
				addLeg.add(jc2,c);
				
				c.gridx = 0;
    			c.gridy = 3;
    			c.gridwidth = 1;
    			c.gridheight = 1;
    			c.weightx =  0.5;
    			c.weighty =  0.0;
				addLeg.add(new Jlabel(" Address :" , SwingConstants.RIGHT),c);
				
				c.gridx = 1;
    			c.gridy = 3;
    			c.gridwidth = 3;
    			c.gridheight = 1;
    			c.weightx =  3.0;
    			c.weighty =  0.0;
				addLeg.add(j3,c);
				
				c.gridx = 1;
    			c.gridy = 4;
    			c.gridwidth = 3;
    			c.gridheight = 1;
    			c.weightx =  0.0;
    			c.weighty =  0.0;
				addLeg.add(j4,c);
				
				c.gridx = 1;
    			c.gridy = 5;
    			c.gridwidth = 3;
    			c.gridheight = 1;
    			c.weightx =  0.0;
    			c.weighty =  0.0;
				addLeg.add(j5,c);
				
				c.gridx = 0;
    			c.gridy = 6;
    			c.gridwidth = 1;
    			c.gridheight = 1;
    			c.weightx =  0.5;
    			c.weighty =  0.0;
				addLeg.add(new Jlabel(" State :" , SwingConstants.RIGHT),c);
				
				c.gridx = 1;
    			c.gridy = 6;
    			c.gridwidth = 2;
    			c.gridheight = 1;
    			c.weightx =  2.0;
    			c.weighty =  0.0;
				addLeg.add(jc3,c);
				
				c.gridx = 0;
    			c.gridy = 7;
    			c.gridwidth = 1;
    			c.gridheight = 1;
    			c.weightx =  0.5;
    			c.weighty =  0.0;
				addLeg.add(new Jlabel(" IT no. :" , SwingConstants.RIGHT),c);
				
				c.gridx = 1;
    			c.gridy = 7;
    			c.gridwidth = 1;
    			c.gridheight = 1;
    			c.weightx =  1.5;
    			c.weighty =  0.0;
				addLeg.add(j6,c);
				
				c.gridx = 0;
    			c.gridy = 8;
    			c.gridwidth = 1;
    			c.gridheight = 1;
    			c.weightx =  0.5;
    			c.weighty =  0.0;
				addLeg.add(new Jlabel(" VAT no. :" , SwingConstants.RIGHT),c);
				
				c.gridx = 1;
    			c.gridy = 8;
    			c.gridwidth = 1;
    			c.gridheight = 1;
    			c.weightx =  2.5;
    			c.weighty =  0.0;
				addLeg.add(j7,c);
				
				c.gridx = 0;
    			c.gridy = 9;
    			c.gridwidth = 1;
    			c.gridheight = 1;
    			c.weightx =  0.5;
    			c.weighty =  0.0;
				addLeg.add(new Jlabel(" CST no. :" , SwingConstants.RIGHT),c);
				
				c.gridx = 1;
    			c.gridy = 9;
    			c.gridwidth = 1;
    			c.gridheight = 1;
    			c.weightx =  2.5;
    			c.weighty =  0.0;
				addLeg.add(j8,c);
				
				KeyStroke ks0 = KeyStroke.getKeyStroke(KeyEvent.VK_ENTER,0,false);
	 			jc1.registerKeyboardAction( new updateLedgerListener(), ks0, JComponent.WHEN_FOCUSED);
	 			jc2.registerKeyboardAction( new updateLedgerListener(), ks0, JComponent.WHEN_FOCUSED);
	 			jc3.registerKeyboardAction( new updateLedgerListener(), ks0, JComponent.WHEN_FOCUSED);
	 			j1.registerKeyboardAction( new updateLedgerListener(), ks0, JComponent.WHEN_FOCUSED);
	 			j2.registerKeyboardAction( new updateLedgerListener(), ks0, JComponent.WHEN_FOCUSED);
	 			j3.registerKeyboardAction( new updateLedgerListener(), ks0, JComponent.WHEN_FOCUSED);
	 			j4.registerKeyboardAction( new updateLedgerListener(), ks0, JComponent.WHEN_FOCUSED);
	 			j5.registerKeyboardAction( new updateLedgerListener(), ks0, JComponent.WHEN_FOCUSED);
	 			j6.registerKeyboardAction( new updateLedgerListener(), ks0, JComponent.WHEN_FOCUSED);
	 			j7.registerKeyboardAction( new updateLedgerListener(), ks0, JComponent.WHEN_FOCUSED);
	 			j8.registerKeyboardAction( new updateLedgerListener(), ks0, JComponent.WHEN_FOCUSED);
	 			
	 			
	 			KeyStroke ks3 = KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE,0,false);
		 		jc1.registerKeyboardAction( new hideListener(), ks3, JComponent.WHEN_FOCUSED);
	 			jc2.registerKeyboardAction( new hideListener(), ks3, JComponent.WHEN_FOCUSED);
	 			jc3.registerKeyboardAction( new hideListener(), ks3, JComponent.WHEN_FOCUSED);
	 			j1.registerKeyboardAction( new hideListener(), ks3, JComponent.WHEN_FOCUSED);
	 			j2.registerKeyboardAction( new hideListener(), ks3, JComponent.WHEN_FOCUSED);
	 			j3.registerKeyboardAction( new hideListener(), ks3, JComponent.WHEN_FOCUSED);
	 			j4.registerKeyboardAction( new hideListener(), ks3, JComponent.WHEN_FOCUSED);
	 			j5.registerKeyboardAction( new hideListener(), ks3, JComponent.WHEN_FOCUSED);
	 			j6.registerKeyboardAction( new hideListener(), ks3, JComponent.WHEN_FOCUSED);
	 			j7.registerKeyboardAction( new hideListener(), ks3, JComponent.WHEN_FOCUSED);
	 			j8.registerKeyboardAction( new hideListener(), ks3, JComponent.WHEN_FOCUSED);
	 			
	 			j1.addFocusListener( new addF() );
				j2.addFocusListener( new addF() );
				j3.addFocusListener( new addF() );
				j4.addFocusListener( new addF() );
				j5.addFocusListener( new addF() );
				j6.addFocusListener( new addF() );
				j7.addFocusListener( new addF() );
				j8.addFocusListener( new addF() );

				addLeg.setSize(460,450);
				addLeg.setLocation(200,200);
				addLeg.setVisible( true );
					System.gc();
				Runtime.getRuntime().gc();	
				
			}	
			catch(Exception e)
			{
				System.err.println( e.getMessage() );
			}
		}	
	}
	class updateLedgerListener implements ActionListener
	{
		public void actionPerformed( ActionEvent event )
		{
			try
			{
				PreparedStatement ps;
				ps = conn.prepareStatement("UPDATE ledg SET name = ? ,under = ?,op_bal = ?,type = ?,addr1 = ? ,addr2 = ?,addr3 = ?,state = ?,it = ?,vat = ?,cst = ? WHERE name = ?");
				ps.setString(1,(String)j1.getText().toUpperCase());
				ps.setString(2,(String)jc1.getSelectedItem());
				ps.setDouble(3,Double.parseDouble(j2.getText()));
				ps.setString(4,(String)jc2.getSelectedItem());
				ps.setString(5,(String)j3.getText().toUpperCase());
				ps.setString(6,(String)j4.getText().toUpperCase());
				ps.setString(7,(String)j5.getText().toUpperCase());
				ps.setString(8,(String)jc3.getSelectedItem());
				ps.setString(9,(String)j6.getText().toUpperCase());
				ps.setString(10,(String)j7.getText().toUpperCase());
				ps.setString(11,(String)j8.getText().toUpperCase());
				ps.setString(12,(String)t2.getValueAt( t2.getSelectedRow(), 0) );
				ps.executeUpdate();
				ps.close();
				addLeg.dispose();
				j1=null;j2=null;j3=null;j4=null;j5=null;
				j6=null;j7=null;j8=null;jc1=null;jc2=null;jc3=null;
				addLeg=null;
				System.gc();
				Runtime.getRuntime().gc();
			}	
			catch(Exception e)
			{
				System.err.println( e.getMessage() );
			}
		}	
	}
	class delLedgerListener implements ActionListener
	{
		public void actionPerformed( ActionEvent event )
		{
			try
			{
				int op = JOptionPane.showConfirmDialog(null,"Deleting will erase all the data under this ledger","Do you want to continue?",JOptionPane.YES_NO_OPTION,JOptionPane.WARNING_MESSAGE);
				if(op == 0)
				{
					PreparedStatement ps;
					int r = t2.getSelectedRow();
					ps = conn.prepareStatement("DELETE FROM ledg WHERE name = ?");
					ps.setString( 1,(String)t2.getValueAt(r,0) );
					ps.executeUpdate();
					ps.close();	
					j1=null;j2=null;j3=null;j4=null;j5=null;
					j6=null;j7=null;j8=null;j9=null;j10=null;
					addAcc = null;
						System.gc();
				Runtime.getRuntime().gc();
				
				}
			}	
			catch(Exception e)
			{
				System.err.println( e.getMessage() );
			}
		}	
	}
/////////////////////////////////////////////////////////////////////////////////
	public static void main( String [] args)
	{
		EFiling ef = new EFiling();
		ef.pass();
	}

	private static JFrame pass,main,addAcc,sale,select,addGrp,addLeg;
	private static Jtextfield p1,j1,j2,j3,j4,j5,j6,j7,j8,j9,j10;
	private static JPasswordField p2;
	private static Jcombobox jc1,jc2,jc3;
	private static Jtable t,t1,t2;
	private static int j;
}

	
	
	
		

/**
* Tamerincode ~ This database tool is an application that
* provides you with a dynamic way of adding, deleting and updating
* records in a MySQL database.
* @author  Tamer Altintop
* @version 1.1
* @since 25/05/2017
* Web: www.tamerinblog.com
* GitHub: github.com/1istbesser
*/

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;


public class LoginWindow {	
	

	private JFrame frame;
	

	private JPanel panel, panelCenterMain, panelCenter, panelTop, panelBottom;

	private ImageIcon dbImage = null;

	private JLabel lblWelcome, lblUsername, lblPassword, lblHost, lblDataBase, lblAdditional;

	private JButton btnConnect;

	private JTextField tfUsername, tfHost, tfDataBase;

	private JPasswordField pfPassword;

	private String username, host, database, password;

	private GridBagConstraints c = new GridBagConstraints();

	private Color blueish = new Color(34, 153, 183);
	
	public LoginWindow(){
		createFrame();
		createPanel();
		addImages();
		addLabels();
		addTextFields();
		addButtons();
		setConnectButtonListener();
		frame.add(panel);
		frame.setVisible(true);
	}

	private void createFrame(){
		frame = new JFrame();
		frame.setTitle("Tamerincode ~ A database tool V1.1");
		frame.setSize(800, 600);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);	
	}
	
	private void createPanel(){
		//Initialization
		panel = new JPanel(new BorderLayout());
	    panelCenterMain = new JPanel(new BorderLayout());
		panelCenter = new JPanel(new GridBagLayout());
		panelTop = new JPanel(new GridBagLayout());
		panelBottom = new JPanel(new GridBagLayout());
		
		//Setting properties
		panelTop.setBackground(blueish);
		panelTop.setBorder(new EmptyBorder(10, 10, 10, 10));
		panelCenter.setBackground(blueish);
		
		//Adding them to the parent panel.
		panel.add(panelBottom, BorderLayout.SOUTH);
		panel.add(panelTop, BorderLayout.NORTH);
		panel.add(panelCenterMain, BorderLayout.CENTER);
		panelCenterMain.add(panelCenter, BorderLayout.CENTER);
	}

	private void addImages(){
		
		dbImage = new ImageIcon(this.getClass().getClassLoader().getResource("db4.png"));
		JLabel lblDatabaseImage = new JLabel(dbImage);
		panelCenterMain.add(lblDatabaseImage, BorderLayout.NORTH);
	}

	private void addLabels(){
		//Instantization
		lblWelcome = new JLabel("<html><span style='color:white;font-size:1.5em;'>Hello and welcome to this simple database management tool!</span></html>");
		lblAdditional = new JLabel("<html>There is a demo database set so you can try the application.<br> However, feel free to connect to your own.<br/>Suggest features and report bugs on <span style=\"color:rgb(165, 207, 255);\">www.tamerinblog.com</span></html>");
		lblUsername = new JLabel("Username:");
		lblPassword = new JLabel("Password:");
		lblHost = new JLabel("Host:");
		lblDataBase = new JLabel("Database:");
		
		//Setting properties
		lblAdditional.setForeground(Color.white);
		lblUsername.setForeground(Color.white);
		lblPassword.setForeground(Color.white);
		lblHost.setForeground(Color.white);
		lblDataBase.setForeground(Color.white);
		
		//Adding the labels to the panels.
		panelTop.add(lblWelcome);
		c.gridx=0;
		c.gridy=0;
		c.weightx=0;
		c.weighty=1;
		c.insets = new Insets(0,0,0,0);
		c.anchor = GridBagConstraints.CENTER;
		c.gridwidth = 3;
		panelCenter.add(lblAdditional, c);
		c.gridwidth = 1;
		c.weighty=0.2;
		c.weightx=0;
		c.gridx=0;
		c.gridy=1;
		c.insets = new Insets(0,0,0,0);
		c.anchor = GridBagConstraints.LINE_END;
		panelCenter.add(lblUsername, c);
		c.gridx=2;
		c.gridy=1;
		panelCenter.add(lblPassword, c);
		c.gridx=0;
		c.gridy=2;
		panelCenter.add(lblDataBase, c);
		c.gridx=2;
		c.gridy=2;
		panelCenter.add(lblHost, c);
	}

	private void addTextFields(){
		//Instantization
		tfUsername = new JTextField("root");
		pfPassword = new JPasswordField("");
		tfHost = new JTextField("localhost");
		tfDataBase = new JTextField("hikariTest");
		
		//Setting properties
		tfUsername.setPreferredSize(new Dimension(170,20));
		tfHost.setPreferredSize(new Dimension(170,20));
		tfDataBase.setPreferredSize(new Dimension(170,20));
		pfPassword.setPreferredSize(new Dimension(170,20));
		
		//Adding the text fields and password fields to the panels.
		c.anchor = GridBagConstraints.LINE_START;
		c.insets = new Insets(3,20,3,3);
		c.gridx=1;
		c.gridy=1;
		panelCenter.add(tfUsername, c);
		c.gridx=3;
		c.gridy=1;
		panelCenter.add(pfPassword, c);
		c.gridx=1;
		c.gridy=2;
		panelCenter.add(tfDataBase, c);
		c.gridx=3;
		c.gridy=2;
		panelCenter.add(tfHost, c);
	}

	private void addButtons(){
		//Instantization
		btnConnect = new JButton("Connect");
		
		//Adding the buttons to the panels
		c.anchor = GridBagConstraints.LINE_END;
		c.gridx=3;
		c.gridy=5;
		panelCenter.add(btnConnect, c);
	}

	private void setConnectButtonListener() {
		btnConnect.addActionListener(new ActionListener() {
			@SuppressWarnings("deprecation")
			public void actionPerformed(ActionEvent arg0) {	
				username = tfUsername.getText();
				password = pfPassword.getText();
				host = tfHost.getText();
				database = tfDataBase.getText();
				boolean connected = DatabaseOperations.checkCredentials(host, database, username, password);
				if(connected){
					frame.dispose();
					try {
						new ApplicationWindow();
					} catch (SQLException e) {
						e.printStackTrace();
					}
				} else {
					JOptionPane.showMessageDialog(frame, "Wrong connection details !\n");
				}
			}
		});
	}
}
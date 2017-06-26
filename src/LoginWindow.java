/**
* Tamerincode database tool is an application that
* provides you with a dynamic way of adding, deleting and updating
* records in a MySQL database.
* @author  Tamer Altintop, student id s4908098
* @version 1.0
* @since 25/05/2017
*/

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

/**
 * This class creates the first window seen by the user
 * upon starting the application. Its purpose <br>is to gather
 * the information about the database to which the user
 * wishes to connect to.<br><br>
 * 
 * Once the information has been gathered, it tests the connection<br>
 * and if the connection is successful, it calls the ApplicationWindow class<br>
 * passing as parameters the username, password, the database name and the host.
 * 
 */

public class LoginWindow {	
	
	/** The declaration of the frame. */
	private JFrame frame;
	
	/** The declaration of the panels.<br>
	 * <ol>
	 * <li><b>panel</b> - The main panel.</li>
	 * <li><b>panelMainCenter</b> - The sub-panel of panel taking up the center area.</li>
	 * <li><b>panelCenter</b> - The sub-panel of panelMainCenter taking up the center area.</li>
	 * <li><b>panelTop</b> - The sub-panel taking up the top area.</li>
	 * <li><b>panelBottom</b> - The sub-panel taking up bottom area.</li>
	 * </ol>*/
	private JPanel panel, panelCenterMain, panelCenter, panelTop, panelBottom;
	
	/** The declaration of the icon images. */
	private ImageIcon dbImage = null;
	
	/** The declaration of the labels.<br>
	 * <ol>
	 * <li><b>lblWelcome</b> - The label for the welcome message.</li>
	 * <li><b>lblUsername</b> - The label for the username text field.</li>
	 * <li><b>lblPassword</b> - The label for the password text field.</li>
	 * <li><b>lblHost</b> - The label for the host text field.</li>
	 * <li><b>lblDatabase</b> - The label for the database text field.</li>
	 * <li><b>lblAdditional</b> - The label for the additional information message.</li>
	 * </ol> */
	private JLabel lblWelcome, lblUsername, lblPassword, lblHost, lblDataBase, lblAdditional;
	
	/** The declaration of the buttons.<br>
	 * <ol>
	 * <li><b>btnConnect</b> - The button to connect to the database.</li>
	 * </ol> */
	private JButton btnConnect;
	
	/** The declaration of the text fields.<br>
	 * <ol>
	 * <li><b>tfUsername</b> - The text field for the username parameter.</li>
	 * <li><b>tfHost</b> - The text field for the host parameter.</li>
	 * <li><b>tfDatabase</b> - The text field for the database parameter.</li>
	 * </ol> */
	private JTextField tfUsername, tfHost, tfDataBase;
	
	/** The declaration of the password field 
	 * <ol>
	 * <li><b>pfPassword</b> - The password field for the password parameter.</li>
	 * </ol> */
	private JPasswordField pfPassword;
	
	/** The declaration of the String variables used to store the value of the database connection parameters.
	 * <ol>
	 * <li><b>username</b> - The string containing the username value.</li>
	 * <li><b>password</b> - The string containing the password value.</li>
	 * <li><b>database</b> - The string containing the database value.</li>
	 * <li><b>host</b> - The string containing the host value.</li>
	 * </ol> */
	private String username, host, database, password;
	
	/** The declaration of the Grid Bag Constraint.<br>
	 * <b>c</b> - The Grid Bag Constraint. Its name is kept as short as possible for
	 * it is widly used throughout the application. */
	private GridBagConstraints c = new GridBagConstraints();
	
	/** The declaration and initialization of the custom colors.<br>
	 * <b>blueish</b> - The custom color with the rgb(34, 153, 183). */
	private Color blueish = new Color(34, 153, 183);
	
	
	private DatabaseOperations dbOps = new DatabaseOperations();
	
	/**
	 * Instantiates a new login window.<br><br>
	 * <b>List of contained methods</b>
	 * <ol>
	 * <li>{@link #createFrame}</li>
	 * <li>{@link #createPanel}</li>
	 * <li>{@link #addImages}</li>
	 * <li>{@link #addLabels}</li>
	 * <li>{@link #addTextFields}</li>
	 * <li>{@link #addButtons}</li>
	 * <li>{@link #setConnectButtonListener}</li>
	 * </ol>
	 * 
	 */
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
	
	/**
	 * Creates the frame and sets the frame properties like size and title.
	 */
	private void createFrame(){
		frame = new JFrame();
		frame.setTitle("Tamerincode database tool v1.0.1");
		frame.setSize(800, 600);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);	
	}
	
	/**
	 * Creates the main panel and the sub-panels. It also sets up the properties of each panel or sub-panel and it adds each panel<br>
	 * to their parent panel.
	 * <ol>
	 * <li><b>panel</b> - The main panel.</li>
	 * <li><b>panelMainCenter</b> - The sub-panel of panel taking up the center area.</li>
	 * <li><b>panelCenter</b> - The sub-panel of panelMainCenter taking up the center area.</li>
	 * <li><b>panelTop</b> - The sub-panel taking up the top area.</li>
	 * <li><b>panelBottom</b> - The sub-panel taking up bottom area.</li>
	 * </ol>*/
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

	/**
	 * This method instantiates the images and adds them to the corresponding panel.
	 */
	private void addImages(){
		
		dbImage = new ImageIcon(this.getClass().getClassLoader().getResource("db4.png"));
		JLabel lblDatabaseImage = new JLabel(dbImage);
		panelCenterMain.add(lblDatabaseImage, BorderLayout.NORTH);
	}
	
	/**
	 * This method instantiates the labels and adds them to the corresponding panel using the Grid Bag Constraints.
	 */
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

	/**
	 * This method instantiates the text fields, password fields. It is also setting up the properties of the text fields<br>
	 * and it adds them to the panels.
	 */
	private void addTextFields(){
		//Instantization
		tfUsername = new JTextField("increible");
		pfPassword = new JPasswordField("increible");
		tfHost = new JTextField("160.153.128.32:3306");
		tfDataBase = new JTextField("SuperVShop");
		
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
	
	/**
	 * This method instantiates the buttons and it adds them to the panels.
	 */
	private void addButtons(){
		//Instantization
		btnConnect = new JButton("Connect");
		
		//Adding the buttons to the panels
		c.anchor = GridBagConstraints.LINE_END;
		c.gridx=3;
		c.gridy=5;
		panelCenter.add(btnConnect, c);
	}
	
	/**
	 * This method sets the listener for the 'Connect' button. The database connection is tested<br>
	 * with the parameters provided by the user through the text fields.<br><br>
	 * If the connection is successful, the connection is closed, the current window is <b>disposed</b>,<br>
	 * and the <b>ApplicationWindow</b> is invoked.<br><br>
	 */
	private void setConnectButtonListener() {
		btnConnect.addActionListener(new ActionListener() {
			@SuppressWarnings("deprecation")
			public void actionPerformed(ActionEvent arg0) {	
				username = tfUsername.getText();
				password = pfPassword.getText();
				host = tfHost.getText();
				database = tfDataBase.getText();
				dbOps.setCredentials(username, password, host, database);
				Boolean connected = dbOps.checkCredentials();
				if(connected){
					frame.dispose();
					new ApplicationWindow(username, password, host, database);
				} else {
					JOptionPane.showMessageDialog(frame, "Wrong connection details !\n");
				}
			}
		});
	}


}
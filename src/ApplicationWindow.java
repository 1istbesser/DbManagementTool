/**
* Tamerincode ~ This database tool is an application that
* provides you with a dynamic way of adding, deleting and updating
* records in a MySQL database.
* @author  Tamer Altintop
* @version 1.1
* @since 25/05/2017
* * Web: www.tamerinblog.com
* GitHub: github.com/1istbesser
*/

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.border.EmptyBorder;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;

public class ApplicationWindow implements TableModelListener {
	private DatabaseOperations dbOps = new DatabaseOperations();
	private JFrame frame;
	private JPanel panel, panelLeft, panelRight;
	private JButton btnOpen, btnAdd, btnDelete, btnExit;
	private JTable table;
	private String username = null, password = null, database = null, host = null, selectedTable = null;
	private final DefaultTableModel tableModel = new DefaultTableModel();
	private ResultSet rs;

	private final DefaultListModel<String> tablesNamesModel = new DefaultListModel<String>();
	private final JList<String> listOfTablesNames = new JList<String>(tablesNamesModel);
	private JScrollPane tablesListScrollPane, scrollTable;
	private JMenuBar menuBar;
	private JMenu appMenu, designMenu;
	private JMenuItem disconnect, appDetails, panelBackground, panelForeground, panelSelectedBackground, panelSelectedForeground, tableSelectedBackground, tableSelectedForeground, designReset;
	private JLabel listHeader;
	private Color someBlue = new Color(72, 121, 150), someBlue2 = new Color(12, 16, 56), someGreen = new Color(79, 249, 108);
	
	private NewRow newRow;

	public ApplicationWindow(String conUsername, String conPassword, String conHost, String conDatabase){
		dbOps.setCredentials(conUsername, conPassword, conHost, conDatabase);
		setCredentials(conUsername, conPassword, conHost, conDatabase);
		setFrame();
		addButtons();
		createMenu();
		setTablesList();
		createPanel();
		newRow = new NewRow();
		frame.add(panel);
		frame.setVisible(true);
	}

	private void setCredentials(String conUsername, String conPassword, String conHost, String conDatabase){
		host=conHost;
		database=conDatabase;
		username=conUsername;
		password=conPassword;
	}

	private void reconnect(){
		dbOps.closeConnection();
		dbOps.createConnection();
	}

	private void setFrame() {
		//Creating the frame
		frame = new JFrame();
		
		//Setting properties
		frame.setTitle("Tamerincode ~ A database tool V1.1");
		frame.setSize(800, 600);
		frame.setExtendedState(JFrame.MAXIMIZED_BOTH); 
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);	
	}

	private boolean checkConnection(){
			if(!dbOps.checkConnection()){
				int n = JOptionPane.showConfirmDialog(frame,"The connection was closed, reconnect?","Database security measure",
						JOptionPane.YES_NO_OPTION);
				//If the answer is yes, try to reconnect.
				if(n == JOptionPane.YES_OPTION){
					reconnect();
					
				//If the answer is no, dispose the frame and return to LoginWindow.
				} else if ( n == JOptionPane.NO_OPTION){
					frame.dispose();
					new LoginWindow();
				}
			}
			return true;
		}

	private void setTablesList(){
	tablesListScrollPane = new JScrollPane(listOfTablesNames); 
	tablesListScrollPane.setBorder(null);
	listOfTablesNames.setBackground(someBlue);
	listOfTablesNames.setForeground(Color.white);
	listOfTablesNames.setSelectionBackground(someBlue2);
	listOfTablesNames.setSelectionForeground(Color.white);
	// atrs = all tables resultset
	ResultSet atrs = null;
	atrs = dbOps.getAllTables();
	if(atrs==null){
		JOptionPane.showMessageDialog(frame, "Could not get the tables names!\n");
		frame.dispose();
		new LoginWindow();
	} else {
		try{
			while (atrs.next()) {
				String tableName = atrs.getString(3);
				tablesNamesModel.addElement(tableName);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			//Code 432 - atrs has no next or it couldn't use getString(3).
			JOptionPane.showMessageDialog(frame, "There is a SQLException error! Code 432.\n");
			frame.dispose();
			new LoginWindow();
		}  finally{
			try {
				atrs.close();
				dbOps.closeResultSet();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
		listOfTablesNames.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		listOfTablesNames.setSelectedIndex(0);
		listOfTablesNames.setVisibleRowCount(4);
		listOfTablesNames.setBorder(new EmptyBorder(3, 3, 3, 3));
	}

	private void addButtons(){
		//Instantisation of the buttons
		btnOpen = new JButton("Open table");
		btnAdd = new JButton("Add record");
		btnDelete = new JButton("Delete record");
		btnExit = new JButton("Exit");
		
		//Adding listeners
		ActionListener exitButton = new listenExitButton();
		ActionListener addButton = new listenAddButton();
		ActionListener removeButton = new listenRemoveButton();
		ActionListener openButton = new listenOpenButton();
		MouseListener dblClick = new dblClick();
		
		listOfTablesNames.addMouseListener(dblClick);
		btnOpen.addActionListener(openButton);
		btnExit.addActionListener(exitButton);
		btnAdd.addActionListener(addButton);
		btnDelete.addActionListener(removeButton);
	}

	private void createMenu(){
		//Instantiating the menu objects
		menuBar = new JMenuBar();
		appMenu = new JMenu("Application");
		designMenu = new JMenu("Appearance");
		disconnect = new JMenuItem("Disconnect from the database");
		appDetails = new JMenuItem("Application details");
		panelBackground = new JMenuItem("Panel background");
		panelForeground = new JMenuItem("Panel foreground");
		tableSelectedBackground = new JMenuItem("Table selected row background");
		tableSelectedForeground = new JMenuItem("Table selected row foreground");
		panelSelectedBackground = new JMenuItem("Panel selected item background");
		panelSelectedForeground = new JMenuItem("Panel selected item foreground");
		designReset = new JMenuItem("Reset to default");
		
		//Add the menu tabs to the menu bar
		menuBar.add(appMenu);
		menuBar.add(designMenu);
		
		//Add items to the menus
		appMenu.add(disconnect);
		appMenu.add(appDetails);
		designMenu.add(panelBackground);
		designMenu.add(panelForeground);
		designMenu.add(panelSelectedBackground);
		designMenu.add(panelSelectedForeground);
		designMenu.add(tableSelectedBackground);
		designMenu.add(tableSelectedForeground);
		designMenu.add(designReset);
		
		//Add listeners for each item
		disconnect.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				frame.setVisible(false);
				new LoginWindow();
			}
		});
		appDetails.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				JOptionPane.showMessageDialog(frame, "<html>Created by Tamer Altintop.<br/>Version V1.0.1<br/>Future versions available on www.tamerinblog.com/tamerincode/dbTool</html>");
			}
		});
		panelBackground.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				Color c = JColorChooser.showDialog(null, "Choose a Color", Color.blue);
				panelLeft.setBackground(c);
				listOfTablesNames.setBackground(c);
				listHeader.setBackground(c);
			}
		});

		panelForeground.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				Color c = JColorChooser.showDialog(null, "Choose a Color", Color.blue);
				panelLeft.setForeground(c);
				listOfTablesNames.setForeground(c);
				listHeader.setForeground(c);
			}
		});

		tableSelectedBackground.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				Color c = JColorChooser.showDialog(null, "Choose a Color", Color.blue);
				table.setSelectionBackground(c);
			}
		});
		
		panelSelectedBackground.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				Color c = JColorChooser.showDialog(null, "Choose a Color", Color.blue);
				listOfTablesNames.setSelectionBackground(c);
			}
		});
		
		tableSelectedForeground.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				Color c = JColorChooser.showDialog(null, "Choose a Color", Color.blue);
				table.setSelectionForeground(c);
			}
		});
		
		panelSelectedForeground.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				Color c = JColorChooser.showDialog(null, "Choose a Color", Color.blue);
				listOfTablesNames.setSelectionForeground(c);
			}
		});
		
		designReset.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				panelLeft.setForeground(Color.white);
				listOfTablesNames.setForeground(Color.white);
				listHeader.setForeground(Color.white);
				panelLeft.setBackground(someBlue);
				listOfTablesNames.setBackground(someBlue);
				listHeader.setBackground(someBlue);
				listOfTablesNames.setSelectionForeground(Color.white);
				listOfTablesNames.setSelectionBackground(someBlue2);
				table.setSelectionBackground(someGreen);
				table.setSelectionForeground(Color.black);
			}
		});
	}

	private void createPanel(){
		// Main panel
		panel = new JPanel(new BorderLayout());
		// Left side
		panelLeft = new JPanel(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		// Right side
		panelRight = new JPanel(new GridBagLayout());
		GridBagConstraints v = new GridBagConstraints();
		// Setting the ID column uneditable.
		table = new JTable(tableModel){
			private static final long serialVersionUID = -7251800887624374231L;
			@Override
			public boolean isCellEditable(int row, int column) {
				return column == 0 ? false : true;
			}};

		table.setSelectionBackground(someGreen);
		scrollTable = new JScrollPane();
		scrollTable.setViewportView(table);
		panel.add(menuBar, BorderLayout.NORTH);
		

		panel.add(panelRight, BorderLayout.EAST);
		v.gridy=0;
		v.gridx=0;
		v.weighty=0;
		v.weightx=0;
		v.fill=GridBagConstraints.HORIZONTAL;
		panelRight.add(btnAdd, v);
		v.gridy=1;
		v.gridx=0;
		v.weighty=0;
		v.weightx=0;
		panelRight.add(btnDelete, v);
		v.gridy=2;
		v.gridx=0;
		v.weightx=0;
		v.weighty=0;
		panelRight.add(btnExit, v);
		panel.add(panelLeft, BorderLayout.WEST);
		listHeader = new JLabel("<html><span style='font-size:2em;'>Tables</span></html>");
		listHeader.setOpaque(true);
		listHeader.setForeground(Color.WHITE);
		listHeader.setBackground(someBlue);
		listHeader.setBorder(new EmptyBorder(10,10,0,0));
		listHeader.setHorizontalTextPosition(JLabel.CENTER);
		c.gridy=0;
		c.gridx=0;
		c.weighty=0;
		c.weightx=1;
		c.fill=GridBagConstraints.NONE;
		c.anchor = GridBagConstraints.CENTER;
		panelLeft.add(listHeader, c);
		c.gridy=1;
		c.gridx=0;
		c.weighty=1;
		c.weightx=1;
		c.fill=GridBagConstraints.BOTH;
		c.anchor = GridBagConstraints.LINE_START;
		panelLeft.add(tablesListScrollPane, c);
		c.weightx=1;
		c.weighty=0;
		c.gridy=2;
		panelLeft.add(btnOpen, c);
		btnOpen.setBorder(new EmptyBorder(10,40,10,40));
		panelLeft.setBorder(new EmptyBorder(0,10,10,10));
		panelLeft.setBackground(someBlue);
		panel.add(scrollTable, BorderLayout.CENTER);
		selectedTable = (String) listOfTablesNames.getSelectedValue();
		rs = dbOps.executeQuery("SELECT * FROM " + selectedTable);
		loadData(rs);
		tableModel.addTableModelListener(this);
	}

	private class dblClick implements MouseListener{

		@Override
		public void mouseClicked(MouseEvent arg0) {
			if(arg0.getClickCount()==2){
				if(checkConnection()){
					selectedTable = (String) listOfTablesNames.getSelectedValue();
					//rs = st.executeQuery("SELECT * FROM " + selectedTable);
					rs = dbOps.executeQuery("SELECT * FROM " + selectedTable);
					loadData(rs);
				}	
	        }
		}

		@Override
		public void mouseEntered(MouseEvent arg0) {

			
		}

		@Override
		public void mouseExited(MouseEvent arg0) {

			
		}

		@Override
		public void mousePressed(MouseEvent arg0) {

			
		}

		@Override
		public void mouseReleased(MouseEvent arg0) {

			
		}
		
	}

	private class listenExitButton implements ActionListener {

		/* (non-Javadoc)
		 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
		 */
		@Override
		public void actionPerformed(ActionEvent arg0) {
			System.exit(0);
		}
	}

	private class listenAddButton implements ActionListener {

		/* (non-Javadoc)
		 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
		 */
		@Override
		public void actionPerformed(ActionEvent arg0){
			if(checkConnection()){
				int nrCols = table.getColumnCount();
				Vector<String> row = new Vector<String>();
				for(int i=1; i<= nrCols; i++){
					row.add("");
				}
				DefaultTableModel model = (DefaultTableModel)table.getModel();
				model.addRow(row);
			} 
		}
	}
	private class listenOpenButton implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			
			if(checkConnection()){
				selectedTable = (String) listOfTablesNames.getSelectedValue();
				String sqlString = "SELECT * FROM " + selectedTable;
				ResultSet tdrs = dbOps.executeQuery(sqlString);
				loadData(tdrs);
			}	
		}
	}

	private class listenRemoveButton implements ActionListener{

		/* (non-Javadoc)
		 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
		 */
		@Override
		public void actionPerformed(ActionEvent arg0){
			if(checkConnection()){
				DefaultTableModel model = (DefaultTableModel)table.getModel();
				int row = table.getSelectedRow();
				if(row<0){
					JOptionPane.showMessageDialog(frame,"No row is selected!", "Row not selected", JOptionPane.ERROR_MESSAGE);
				} else {
					String cellValue4 = String.valueOf( table.getValueAt(row, 0) );
					int id = Integer.parseInt(cellValue4);
					String selectedTable2 = (String) listOfTablesNames.getSelectedValue();
					boolean deleted = dbOps.deleteRecord(selectedTable2, id);
					if(deleted) model.removeRow(row);
				}
			} 
		}
	}

	private void loadData(ResultSet rss){

		tableModel.setDataVector(dbOps.loadTableData(rss), dbOps.loadColumnNames(rss));
	}

	@Override
	public void tableChanged(TableModelEvent e) {
		checkConnection();
		int row = e.getFirstRow();
		int column = e.getColumn();
		int type = e.getType();
		if(type==1){
			try {
				newRow.addNewColumn(selectedTable, username, password, database, host);
			} catch (ClassNotFoundException e1) {
				e1.printStackTrace();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			String selectedTable5 = (String) listOfTablesNames.getSelectedValue();
			rs = dbOps.executeQuery("SELECT * FROM " + selectedTable5);
			loadData(rs);
		} else if(type==0){
			if(row>=0 && column>=0){
				String selectedTable2 = (String) listOfTablesNames.getSelectedValue();
				String cellValue = String.valueOf(table.getValueAt(row, column));
				String namec = table.getColumnName(column);
				String cellValue3 = String.valueOf(table.getValueAt(row, 0));
				int id = Integer.parseInt(cellValue3);
				String query = "UPDATE " + selectedTable2 + " SET " + namec + "=" + "?" + " WHERE id='" + id + "'";
				dbOps.executePreparedStatement(query, cellValue);
			}
		}
	}
}

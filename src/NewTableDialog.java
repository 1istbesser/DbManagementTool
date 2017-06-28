import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.BoxLayout;

import javax.swing.InputMap;
import javax.swing.JButton;
import javax.swing.JCheckBox;

import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;

import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRootPane;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.KeyStroke;

public class NewTableDialog extends JDialog {
	private static final long serialVersionUID = 1L;
	private List<JTextField> tfNameArray = new ArrayList<JTextField>();
	private List<JTextField> tfLengthArray = new ArrayList<JTextField>();
	private List<JTextField> tfDataTypes = new ArrayList<JTextField>();
	private int count = 0;
	private JPanel mainPanel, northPanel, newTablePane;
	private List<JCheckBox> primaryList = new ArrayList<JCheckBox>();
	private List<JCheckBox> autoincrementList = new ArrayList<JCheckBox>();
	private JTextField txfTableName;
	private int primaryKeyUsed=0, autoincrementUsed=0;
	public NewTableDialog(JFrame parent, String title, String message) {
		newTablePane = new JPanel();
		newTablePane.setLayout(new BoxLayout(newTablePane,BoxLayout.Y_AXIS));

		northPanel = new JPanel();
		northPanel.setLayout(new FlowLayout());

		mainPanel = new JPanel();
		mainPanel.setLayout(new BorderLayout());
		mainPanel.setPreferredSize(new Dimension(700, 500));
		JScrollPane scroller = new JScrollPane(newTablePane);
		mainPanel.add(scroller, BorderLayout.CENTER);
		getContentPane().add(mainPanel);

		JLabel lblKeyList = new JLabel("Table name:");
		northPanel.add(lblKeyList);
		txfTableName = new JTextField("users");
		txfTableName.setPreferredSize(new Dimension(150, 30));
		northPanel.add(txfTableName);
		mainPanel.add(northPanel, BorderLayout.NORTH);


		JPanel buttonPane = new JPanel();
		JButton button = new JButton("Add row");
		JButton btnCreate = new JButton("Create table");
		northPanel.add(button);
		northPanel.add(btnCreate);

		button.addActionListener(new MyActionListener());
		btnCreate.addActionListener(new createTableListener());

		getContentPane().add(buttonPane, BorderLayout.PAGE_END);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		addRow();
		pack();
		setLocationRelativeTo(null);
		setVisible(true);
	}
	public void addRow(){
		if(count<20){
			JPanel row = new JPanel();
			row.setLayout(new FlowLayout());

			JTextField txtName = new JTextField("id");
			JTextField txtDataType = new JTextField("int");
			JTextField txtLength = new JTextField("255");

			txtName.setPreferredSize(new Dimension(150, 30));
			txtDataType.setPreferredSize(new Dimension(150, 30));
			txtLength.setPreferredSize(new Dimension(150, 30));

			tfNameArray.add(txtName);
			tfDataTypes.add(txtDataType);
			tfLengthArray.add(txtLength);


			JCheckBox primaryBox = new JCheckBox("Primary Key");
			if(primaryKeyUsed==1){
				primaryBox.setSelected(false);
				primaryBox.setEnabled(false);
			}
			primaryList.add(primaryBox);
			JCheckBox autoincrementBox = new JCheckBox("Auto Increment");
			if(autoincrementUsed==1){
				autoincrementBox.setSelected(false);
				autoincrementBox.setEnabled(false);
			}
			autoincrementList.add(autoincrementBox);

			row.add(tfNameArray.get(count));
			row.add(tfDataTypes.get(count));
			row.add(tfLengthArray.get(count));
			row.add(primaryList.get(count));
			row.add(autoincrementList.get(count));

			newTablePane.add(row);
			newTablePane.revalidate();
			newTablePane.repaint();
			count++;
		}
	}

	// override the createRootPane inherited by the JDialog, to create the rootPane.
	// create functionality to close the window when "Escape" button is pressed

	public JRootPane createRootPane() {

		JRootPane rootPane = new JRootPane();

		KeyStroke stroke = KeyStroke.getKeyStroke("ESCAPE");

		Action action = new AbstractAction() {
			private static final long serialVersionUID = 1L;
			public void actionPerformed(ActionEvent e) {

				System.out.println("escaping..");
				addRow();
				//				setVisible(false);
				//
				//				dispose();

			}

		};

		InputMap inputMap = rootPane.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);

		inputMap.put(stroke, "ESCAPE");

		rootPane.getActionMap().put("ESCAPE", action);

		return rootPane;

	}

	// an action listener to be used when an action is performed
	// (e.g. button is pressed)

	class MyActionListener implements ActionListener {

		public void actionPerformed(ActionEvent e) {

			System.out.println("disposing the window..");
			addRow();
			//			setVisible(false);
			//
			//			dispose();

		}

	}
	class createTableListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			Boolean canceled=false;
			int rows = tfNameArray.size();
			String tableName = txfTableName.getText();
			String query = "CREATE TABLE "+tableName+" (";

			for(int i=0; i<rows; i++){
				String name = tfNameArray.get(i).getText();
				String dataType = tfDataTypes.get(i).getText();
				String length = tfLengthArray.get(i).getText();
				Boolean pk=false, ai=false;
				if(primaryList.get(i).isSelected()){
					if(primaryKeyUsed == 0){
						primaryKeyUsed++;
						pk=true;
					} else {
						for(int j=0; j< primaryList.size(); j++){
							primaryList.get(j).setSelected(false);
							autoincrementList.get(j).setSelected(false);
						}
						primaryKeyUsed = 0;
						autoincrementUsed = 0;
						JOptionPane.showMessageDialog(getContentPane(),
								"You can't have multiple primary keys!",
								"Multiple primary keys error",
								JOptionPane.ERROR_MESSAGE);
						canceled=true;
					}
				}
				if(autoincrementList.get(i).isSelected()){
					if(autoincrementUsed == 0){
						autoincrementUsed++;
						ai=true;
					} else {
						for(int j=0; j< autoincrementList.size(); j++){
							primaryList.get(j).setSelected(false);
							autoincrementList.get(j).setSelected(false);
						}
						autoincrementUsed = 0;
						primaryKeyUsed = 0;
						JOptionPane.showMessageDialog(getContentPane(),
								"You can't have multiple auto increment fields!",
								"Multiple autoincrement fields error",
								JOptionPane.ERROR_MESSAGE);
						canceled=true;
					}

				}
				if(canceled) break;
				if(dataType.equals("int")){
					query = query + ""+name+" "+dataType+" ("+length+") NOT NULL";
					if(pk){
						query = query +"  PRIMARY KEY";
					}
					if(ai){
						if(pk){
							query = query + "  AUTO_INCREMENT";
						} else {
							for(int j=0; j< autoincrementList.size(); j++){
								primaryList.get(j).setSelected(false);
								autoincrementList.get(j).setSelected(false);
							}
							autoincrementUsed = 0;
							primaryKeyUsed = 0;
							JOptionPane.showMessageDialog(getContentPane(),
									"The auto increment must be set on a primary key!",
									"Auto increment implementation error",
									JOptionPane.ERROR_MESSAGE);
							canceled=true;
						}	

					}
					System.out.println(pk + " " + ai);	
				} else if (dataType.equals("varchar")){
					query = query + ""+name+" "+dataType+" ("+length+") NOT NULL";
				} else if (dataType.equals("text")){
					query = query + ""+name+" "+dataType+" NOT NULL";
				} else if (dataType.equals("date")){
					query = query + ""+name+" "+dataType+" NOT NULL";
				}

				if(i<(rows-1)){
					query = query +", ";
				}
				if(canceled) break;
			}
			query = query +");";
			if(!canceled){
				int result = DatabaseHandler.executeUpdate(query);
				System.out.println(query);
				System.out.println(result+" is the result");
				if(result==999){
					JOptionPane.showMessageDialog(getContentPane(),
							"This table already exists!",
							"Duplicate table",
							JOptionPane.ERROR_MESSAGE);
				} else {
					JOptionPane.showMessageDialog(getContentPane(),
							"Table created succesfully!",
							"Table creation succeeded",
							JOptionPane.INFORMATION_MESSAGE);
				}
				dispose();
				
			}
		}
	}
}
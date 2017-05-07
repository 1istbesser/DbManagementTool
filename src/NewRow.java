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

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import com.mysql.jdbc.PreparedStatement;
import com.mysql.jdbc.ResultSetMetaData;
import com.mysql.jdbc.Statement;

public class NewRow{
	private String selectedTable="";
	public NewRow(){

	}
	public void addNewColumn(String selTable, String conUsername, String conPassword, String conDatabase, String conHost) throws ClassNotFoundException, SQLException{
		selectedTable=selTable;
		String username = conUsername;
		String password = conPassword;
		String host= conHost;
		String database = conDatabase;
		ResultSetMetaData metaData = null;
		Statement st = null;
		ResultSet rs;
		//Test the connection
		String url = "jdbc:mysql://" + host + "/" +database+ "";
        Class.forName ("com.mysql.jdbc.Driver");
        Connection conn = DriverManager.getConnection (url, username, password);
        
        st= (Statement) conn.createStatement();
        rs = st.executeQuery("SELECT * FROM "+ selectedTable);
        metaData = (ResultSetMetaData) rs.getMetaData();
        String parameters ="";
        int nrCols = metaData.getColumnCount();
        for(int i=1; i<=nrCols; i++){
        	parameters=parameters+"?";
        	if(i<nrCols){
        		parameters=parameters+",";
        	}
        }
        System.out.println("Param nr: "+parameters);
        
        PreparedStatement addNewRecord = (PreparedStatement) conn.prepareStatement("Insert into " + selectedTable +" VALUES("+parameters+")");
        LocalDate today = LocalDate.now();
        for(int i=1; i<=nrCols; i++){
        	System.out.println(((ResultSetMetaData) metaData).getColumnType(i));
        	if(((ResultSetMetaData) metaData).getColumnType(i)==4)
        	addNewRecord.setInt(i, 0);
        	else if(((ResultSetMetaData) metaData).getColumnType(i)==12)
        	addNewRecord.setString(i, "");
        	else if(((ResultSetMetaData) metaData).getColumnType(i)==91)
        	addNewRecord.setString(i, today.toString());
        	else if(((ResultSetMetaData) metaData).getColumnType(i)==-1)
            addNewRecord.setString(i, "");
        }
        System.out.println(addNewRecord);
        addNewRecord.executeUpdate();
        conn.close();
	}

}

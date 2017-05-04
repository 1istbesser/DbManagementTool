import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;

import com.mysql.jdbc.DatabaseMetaData;
import com.mysql.jdbc.PreparedStatement;
import com.mysql.jdbc.ResultSetMetaData;


public class DatabaseOperations {
	private String username=null, password=null, host=null, database=null;
	private Connection conn=null;
	private ResultSet dbrs=null;
	private Statement dbst=null;
	
	public void setCredentials(String testUsername, String testPassword, String testHost, String testDatabase){
		username = testUsername;
		password = testPassword;
		host= testHost;
		database = testDatabase;
	}
	public boolean checkCredentials() {
		try{
			//Test the connection
			createConnection();
	        return true;
        }
        catch (Exception e){
            return false;
        } finally {
        	closeConnection();
        }
	}
	public void createConnection(){
		//Create the connection
		String url = "jdbc:mysql://" + host + "/" +database+ "";
        try {
			Class.forName ("com.mysql.jdbc.Driver");
			if(conn==null)
	        conn = DriverManager.getConnection (url, username, password);
	    //Test it against the database
	        dbst = conn.createStatement();
	        dbrs = dbst.executeQuery("SELECT 1");
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeResultSet();
			closeStatement();
		}
	}
	public boolean checkConnection(){
		try{
	        dbst = conn.createStatement();
	        dbrs = dbst.executeQuery("SELECT 1");
	        return true;
        }
        catch (Exception e){
        	e.printStackTrace();
        	return false;
        } finally {
        	closeResultSet();
        	closeStatement();
        }
	}
	public void closeConnection(){
        if (conn != null){
            try{
                conn.close ();
            }
            catch (Exception e) {
            	
            }
        }
	}
	public ResultSet getAllTables(){
		DatabaseMetaData dbmd;
		dbrs=null;
		if(conn==null)
			createConnection();
		try {
			dbmd = (DatabaseMetaData) conn.getMetaData();
			String[] types = {"TABLE"};
			dbrs = dbmd.getTables(null, null, "%", types);
			return dbrs;
		} catch (SQLException e) {
			e.printStackTrace();
			return dbrs;
		} finally{
			closeConnection();
		}
	}
	public void closeResultSet(){
		try {
			dbrs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	public void closeStatement(){
		try {
			dbst.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	public Vector<String> loadColumnNames(ResultSet cnrs){
		Vector<String> columnNames = new Vector<String>();
		try{
			//Setting up the name of the columns
			ResultSetMetaData metaData = (ResultSetMetaData) cnrs.getMetaData();
			columnNames = new Vector<String>();
			int columnCount = metaData.getColumnCount();
			for (int i = 1; i <= columnCount; i++) {
				columnNames.add(metaData.getColumnName(i));
			}
		} catch(Exception e){
			e.printStackTrace();
		}
		return columnNames;
	}	
	public Vector<Vector<Object>> loadTableData(ResultSet tdrs){
		Vector<Vector<Object>> data = new Vector<Vector<Object>>();
		try{
			//Setting up the data of the table
			ResultSetMetaData metaData = (ResultSetMetaData) tdrs.getMetaData();
			int columnCount = metaData.getColumnCount();
			while (tdrs.next()) {
				Vector<Object> vector = new Vector<Object>();
				for (int i = 1; i <= columnCount; i++) {
					vector.add(tdrs.getObject(i));        
				}
				data.add(vector);   
			}
		} catch(Exception e){
			e.printStackTrace();
		}
		return data;
	}	
	public boolean deleteRecord(String selectedTable, int id){
		PreparedStatement deleteRecord;
		try {
			deleteRecord = (PreparedStatement) conn.prepareStatement("DELETE FROM " + selectedTable + " WHERE id='" +id+ "'");
			deleteRecord.executeUpdate();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
	public ResultSetMetaData getMetaData(ResultSet rss){
		ResultSetMetaData rsmd = new ResultSetMetaData(null, false, false, null);
		try {
			rsmd = (ResultSetMetaData) rss.getMetaData();
			return rsmd;
		} catch (SQLException e) {
			return rsmd;
		}
	}
	public ResultSet executeQuery(String sqlString){
		Statement st = null;
		ResultSet rs = null;
		//Create the connection
		String url = "jdbc:mysql://" + host + "/" + database+ "";
        try {
			Class.forName ("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection (url, username, password);
			st = conn.createStatement();
			rs = st.executeQuery(sqlString);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return rs;
	}
	public void executePreparedStatement(String query, String cellValue){
		PreparedStatement preparedStmt;
		try {
			preparedStmt = (PreparedStatement) conn.prepareStatement(query);
			preparedStmt.setString(1, cellValue);
			preparedStmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}

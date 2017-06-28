/**
 * Javami database tool is an application that
 * provides you with a dynamic way of adding, deleting and updating
 * records in a MySQL database.
 * @author  Tamer Altintop
 * @version 2.0
 * Github project: github.com/1istbesser/DbManagementTool
 */

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Vector;


import com.mysql.jdbc.ResultSetMetaData;


public class DatabaseOperations {

	//	public static boolean checkCredentials(String host, String database, String user, String password) {
	//		boolean created = dbh.setupHikari(host, database, user, password);
	//		System.out.println(host +" "+database+" "+user+" "+password);
	//		return created;
	//	}

	public ResultSet getAllTables() throws SQLException{

		ResultSet rs = DatabaseHandler.getMetaData();
		return rs;
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
	public boolean deleteRecord(String selectedTable, int id) throws SQLException{
		String query = "DELETE FROM " + selectedTable + " WHERE id='" +id+ "'";
		int executed = DatabaseHandler.executeUpdate(query);
		boolean da;
		if(executed>0){
			da=true;
		} else { da = false;}
		return da;
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
	public ResultSet executeQuery(String sqlString) throws SQLException{
		ResultSet rs = DatabaseHandler.executeQuery(sqlString);
		return rs;
	}
	public void executePreparedStatement(String query) throws SQLException{
		DatabaseHandler.executeUpdate(query);
	}
	public void closeConn(){
		DatabaseHandler.closePool();
	}
	public void createTable(String query){
		DatabaseHandler.executeUpdate(query);
	}
	public static void addNewColumn(String selTable) throws SQLException{
		String selectedTable=selTable;
		ResultSetMetaData metaData = null;
		ResultSet rs;
		rs = DatabaseHandler.executeQuery("SELECT * FROM "+ selectedTable);
		metaData = (ResultSetMetaData) rs.getMetaData();
		String parameters ="";
		int nrCols = metaData.getColumnCount();
		for(int i=1; i<=nrCols; i++){
			parameters=parameters+"?";
			if(i<nrCols){
				parameters=parameters+",";
			}
		}
		Connection conn = DatabaseHandler.getConFromHikari();
		PreparedStatement addNewRecord = (PreparedStatement) conn.prepareStatement("Insert into " + selectedTable +" VALUES("+parameters+")");
		LocalDate today = LocalDate.now();
		for(int i=1; i<=nrCols; i++){
			if(((ResultSetMetaData) metaData).getColumnType(i)==4)
				addNewRecord.setInt(i, 0);
			else if(((ResultSetMetaData) metaData).getColumnType(i)==12)
				addNewRecord.setString(i, "");
			else if(((ResultSetMetaData) metaData).getColumnType(i)==91)
				addNewRecord.setString(i, today.toString());
			else if(((ResultSetMetaData) metaData).getColumnType(i)==-1)
				addNewRecord.setString(i, "");
		}
		addNewRecord.executeUpdate();
		conn.close();
		DatabaseHandler.closeConFromHikari();
	}
}

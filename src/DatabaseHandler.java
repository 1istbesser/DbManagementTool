import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

public class DatabaseHandler {
	private Connection con;
	private PreparedStatement pst;
	private ResultSet rs;
	private HikariConfig config;
	private HikariDataSource ds;
	public DatabaseHandler(){
	}
	public boolean setupHikari(String host, String database, String user, String password){
		config = new HikariConfig();
		config.setMaximumPoolSize(100);
		config.setDataSourceClassName("com.mysql.jdbc.jdbc2.optional.MysqlDataSource");
		config.addDataSourceProperty("serverName", host);
		config.addDataSourceProperty("port", "3306");
		config.addDataSourceProperty("databaseName", database);
		config.addDataSourceProperty("user", user);
		config.addDataSourceProperty("password", password);
		try{
			ds = new HikariDataSource(config);
			return true;
		} catch(Exception E){
			System.out.println("No se puede!");
		}
		return false;
	}
	public Connection getCon(){
		Connection con = null;
		try {
			con = ds.getConnection();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return con;
	}
	public ResultSet executeQuery(String query) throws SQLException{
        con = null;
        pst = null;
        rs = null;
		try  {
			con = ds.getConnection();
			pst = con.prepareStatement(query);
			rs = pst.executeQuery();
			return rs;
		} catch (SQLException e) {
			e.printStackTrace();
		} 
		con.close();
		return null;	
	}
	public int executeUpdate(String query) throws SQLException{
        con = null;
        pst = null;
        rs = null;
        int result = 0;
        
		try  {
			con = ds.getConnection();
			pst = con.prepareStatement(query);
			result = pst.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				pst.close();
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		con.close();
		return result;	
	}
	public ResultSet getMetaData() throws SQLException{
        con = null;
        DatabaseMetaData dbmd = null;
        ResultSet rsTables = null;
		try  {
			con = ds.getConnection();
			dbmd = con.getMetaData();
			String[] types = {"TABLE"};
			rsTables = dbmd.getTables(null, null, "%", types);
			return rsTables;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		con.close();
		return rsTables;	
	}
	public void closePool(){
		ds.close();
	}
}
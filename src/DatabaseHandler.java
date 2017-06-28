/**
 * Javami database tool is an application that
 * provides you with a dynamic way of adding, deleting and updating
 * records in a MySQL database.
 * @author  Tamer Altintop
 * @version 2.0
 * Github project: github.com/1istbesser/DbManagementTool 
 */
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DatabaseHandler {
	private static HikariDataSource dataSource;
	private static String hkHost, hkDatabase, hkUser, hkPassword;
	private static Connection borrowedConnection;

	public DatabaseHandler(){

	}
	public static HikariDataSource getDataSource(){
		if(dataSource == null || dataSource.isClosed()){
			HikariConfig config = new HikariConfig();
			config.setMaximumPoolSize(100);
			config.setDataSourceClassName("com.mysql.jdbc.jdbc2.optional.MysqlDataSource");
			config.addDataSourceProperty("serverName", (Object)hkHost);
			config.addDataSourceProperty("port", (Object)"3306");
			config.addDataSourceProperty("databaseName", (Object)hkDatabase);
			config.addDataSourceProperty("user", (Object)hkUser);
			config.addDataSourceProperty("password", (Object)hkPassword);
			dataSource = new HikariDataSource(config);
		}
		return dataSource;
	}
	public static boolean checkCredentials(String host, String database, String user, String password){
		hkHost = host;
		hkDatabase = database;
		hkUser = user;
		hkPassword = password;
		
		try {
			getDataSource();
			return true;
		} catch(Exception e){
			System.out.println("Could not get the data source with those credentials");
			return false;
		}
	}


	//	public boolean setupHikari(String host, String database, String user, String password) {
	//		this.config = new HikariConfig();
	//		this.config.setMaximumPoolSize(100);
	//		this.config.setDataSourceClassName("com.mysql.jdbc.jdbc2.optional.MysqlDataSource");
	//		this.config.addDataSourceProperty("serverName", (Object)host);
	//		this.config.addDataSourceProperty("port", (Object)"3306");
	//		this.config.addDataSourceProperty("databaseName", (Object)database);
	//		this.config.addDataSourceProperty("user", (Object)user);
	//		this.config.addDataSourceProperty("password", (Object)password);
	//		try {
	//			this.ds = new HikariDataSource(this.config);
	//			return true;
	//		}
	//		catch (Exception E) {
	//			System.out.println("No se puede!");
	//			return false;
	//		}
	//	}

	public static Connection getConFromHikari() {
		borrowedConnection = null;
		try {
			borrowedConnection = dataSource.getConnection();
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		return borrowedConnection;
	}
	public static void closeConFromHikari(){
		try {
			borrowedConnection.close();
		} catch (SQLException e) {
			System.out.println("Cannot close the burrowed connection.");
			e.printStackTrace();
		}
	}

	public static ResultSet executeQuery(String query) {
		Connection con = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		try {
			con = dataSource.getConnection();
			pst = con.prepareStatement(query);
			rs = pst.executeQuery();
			return rs;
		}
		catch (SQLException e) {
			e.printStackTrace();
			try {
				pst.close();
				con.close();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			return null;
		}
	}

	public static int executeUpdate(String query) {
		int result;
		Connection con = null;
		PreparedStatement pst = null;

		block11 : {
			result = 0;
			try {
				con = dataSource.getConnection();
				pst = con.prepareStatement(query);
				result = pst.executeUpdate();
				System.out.println(result+" is the result");
			}
			catch (SQLException e) {
				System.out.println("Can't executeUpdate on DatabaseHandler.");
				e.printStackTrace();
				result=999;
				break block11;
			}
		}

		try {
			pst.close();
			con.close();
		} catch (SQLException e) {
			System.out.println("Can't close the connection in the executeUpdate method from DatabaseHandler.");
			e.printStackTrace();
		}
		return result;
	}
	//
	public static ResultSet getMetaData() throws SQLException {
		ResultSet rsTables;
		Connection con = null;
		DatabaseMetaData dbmd = null;
		rsTables = null;
		try {
			con = dataSource.getConnection();
			dbmd =con.getMetaData();
			String[] types = new String[]{"TABLE"};
			ResultSet resultSet = rsTables = dbmd.getTables(null, null, "%", types);
			return resultSet;
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		finally {
			con.close();
		}
		return rsTables;
	}

	public static void closePool() {
		dataSource.close();
		System.out.println("datasource: "+dataSource);
	}
}
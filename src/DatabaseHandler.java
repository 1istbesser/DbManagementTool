/**
 * Javami database tool is an application that
 * provides you with a dynamic way of adding, deleting and updating
 * records in a MySQL database.
 * @author  Tamer Altintop
 * @version 2.0
 * Github project: github.com/1istbesser/DbManagementTool 
 *  */
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DatabaseHandler {
	private Connection con;
	private PreparedStatement pst;
	private ResultSet rs;
	private HikariConfig config;
	private HikariDataSource ds;

	public boolean setupHikari(String host, String database, String user, String password) {
		this.config = new HikariConfig();
		this.config.setMaximumPoolSize(100);
		this.config.setDataSourceClassName("com.mysql.jdbc.jdbc2.optional.MysqlDataSource");
		this.config.addDataSourceProperty("serverName", (Object)host);
		this.config.addDataSourceProperty("port", (Object)"3306");
		this.config.addDataSourceProperty("databaseName", (Object)database);
		this.config.addDataSourceProperty("user", (Object)user);
		this.config.addDataSourceProperty("password", (Object)password);
		try {
			this.ds = new HikariDataSource(this.config);
			return true;
		}
		catch (Exception E) {
			System.out.println("No se puede!");
			return false;
		}
	}

	public Connection getCon() {
		Connection con = null;
		try {
			con = this.ds.getConnection();
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		return con;
	}

	public ResultSet executeQuery(String query) throws SQLException {
		this.con = null;
		this.pst = null;
		this.rs = null;
		try {
			this.con = this.ds.getConnection();
			this.pst = this.con.prepareStatement(query);
			this.rs = this.pst.executeQuery();
			return this.rs;
		}
		catch (SQLException e) {
			e.printStackTrace();
			this.con.close();
			return null;
		}
	}

	public int executeUpdate(String query) throws SQLException {
		int result;
		block11 : {
			this.con = null;
			this.pst = null;
			this.rs = null;
			result = 0;
			try {
				try {
					this.con = this.ds.getConnection();
					this.pst = this.con.prepareStatement(query);
					result = this.pst.executeUpdate();
				}
				catch (SQLException e) {
					e.printStackTrace();
					try {
						this.pst.close();
						this.con.close();
					}
					catch (SQLException e2) {
						e2.printStackTrace();
					}
					break block11;
				}
			}
			catch (Throwable throwable) {
				try {
					this.pst.close();
					this.con.close();
				}
				catch (SQLException e) {
					e.printStackTrace();
				}
				throw throwable;
			}
			try {
				this.pst.close();
				this.con.close();
			}
			catch (SQLException e) {
				e.printStackTrace();
			}
		}
		this.con.close();
		return result;
	}

	public ResultSet getMetaData() throws SQLException {
		ResultSet rsTables;
		this.con = null;
		DatabaseMetaData dbmd = null;
		rsTables = null;
		try {
			this.con = this.ds.getConnection();
			dbmd = this.con.getMetaData();
			String[] types = new String[]{"TABLE"};
			ResultSet resultSet = rsTables = dbmd.getTables(null, null, "%", types);
			return resultSet;
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		finally {
			try {
				this.con.close();
			}
			catch (SQLException e) {
				e.printStackTrace();
			}
		}
		this.con.close();
		return rsTables;
	}

	public void closePool() {
		this.ds.close();
	}
}
package ua.khpi.krasov.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import org.apache.log4j.Logger;

/**
 * DB manager. Works with MySQL DB. 
 * Class implements singleton pattern
 * 
 * @author A.Krasov
 * @version 1.0
 * 
 */
public class DbManager {

	private static final Logger log = Logger.getLogger(DbManager.class);
	
	private static DbManager instance;
	
	/**
	 * Returns an instance of DbManager. Method allows to implement a 
	 * singleton pattern. Method is synchronized to use in multithreading.
	 * 
	 * @return An instance of DbManager
	 */
	public static synchronized DbManager getInstance() {
		if (instance == null) {
			instance = new DbManager();
		}
		return instance;
	}
	
	/**
	 * Returns a DB connection from the Pool Connections. Before using this
	 * method you must configure the Date Source and the Connections Pool in your
	 * WEB_APP_ROOT/META-INF/context.xml file.
	 * 
	 * @return A DB connection.
	 */
	public Connection getConnection() throws SQLException {
		Connection con = null;
		try {
			Context initContext = new InitialContext();
			DataSource ds  = (DataSource)initContext.lookup("java:comp/env/jdbc/isp");
			con = ds.getConnection();
		} catch (NamingException ex) {
			log.error("Cannot obtain a connection from the pool", ex);
		}
		return con;
	}

	/**
	 * Private contractor for singleton pattern.
	 */
	private DbManager() {}

	/**
	 * Commits and close the given connection.
	 * 
	 * @param con
	 *            Connection to be committed and closed.
	 */
	public void commitAndClose(Connection con) {
		try {
			con.commit();
			con.close();
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
	}

	/**
	 * Rollbacks and close the given connection.
	 * 
	 * @param con
	 *            Connection to be rollbacked and closed.
	 */
	public void rollbackAndClose(Connection con) {
		try {
			con.rollback();
			con.close();
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
	}
	
	/**
	 * Returns a DB connection. This method is for testing.
	 * It does not use a pool
	 * connections and not used in this project. It is preferable to use
	 * {@link #getConnection()} method instead.
	 * 
	 * @return A DB connection.
	 */
	public Connection getConnectionWithDriverManager() throws SQLException {
		Connection connection = DriverManager
				.getConnection("jdbc:mysql://localhost/practice8?serverTimezone=UTC&useSSL=false&"
						+ "user=root&password=12345");
		connection.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);
		connection.setAutoCommit(false);
		return connection;
	}
}

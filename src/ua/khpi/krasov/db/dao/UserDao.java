package ua.khpi.krasov.db.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import ua.khpi.krasov.db.DbManager;
import ua.khpi.krasov.db.dao.interfaces.UserDaoInterface;
import ua.khpi.krasov.db.entity.User;

/**
 * DAO User class. This class allows to work with DB.
 * This class allows to obtain data connecting with 'isp' DB
 * and table 'users'. Class implements UserDaoInterface
 * 
 * @author A.Krasov
 * @see UserDaoInterface
 * @see User
 * @version 1.0
 * 
 */
public class UserDao implements UserDaoInterface {

	private static String SQL_SELECT_ALL_USERS = "SELECT * FROM users";
	
	private static String SQL_SELECT_ALL_CLIENTS = "SELECT * FROM users WHERE role_id=1";
	
	private static String SQL_INSERT_USER = "INSERT INTO users VALUES (DEFAULT, ?, ?, ?, ?, DEFAULT, ?, DEFAULT)";
	
	private static String SQL_UPDATE_LOGIN = "UPDATE users SET login=? WHERE id=?";
	
	private static String SQL_FIND_USER_BY_LOGIN = "SELECT * FROM users WHERE login=?";
	
	private static String SQL_UPDATE_PASSWORD = "UPDATE users SET password=? WHERE id=?";

	private static String SQL_UPDATE_FIRST_NAME = "UPDATE users SET first_name=? WHERE id=?";
	
	private static String SQL_UPDATE_LAST_NAME = "UPDATE users SET last_name=? WHERE id=?";
	
	private static String SQL_UPDATE_STATUS = "UPDATE users SET status_id=? WHERE id=?";
	
	private static String SQL_UPDATE_BILL = "UPDATE users SET bill=? WHERE id=?";
	
	private static String SQL_DELETE_USER = "DELETE FROM users WHERE login=?";
	
	
	/**
	 * Method allows to obtained all data from DB.
	 * @return List of User objects which obtains from DB
	 * @see User
	 */
	@Override
	public List<User> getAllUsers() {
		List<User> users = new ArrayList<>();
		
		Connection con = null;
		Statement stmt = null;
		ResultSet rs = null;
		
		try {
			con = DbManager.getInstance().getConnection();
			stmt = con.createStatement();
			rs = stmt.executeQuery(SQL_SELECT_ALL_USERS);
			while (rs.next()) {
				User user = extractUser(rs);
				users.add(user);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				rs.close();
				stmt.close();
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return users;
	}
	
	/**
	 * Method allows to insert data to DB.
	 * 
	 * @param user which is instance of User
	 * @return true if data successfully saved
	 * @see User
	 */
	@Override
	public boolean insertUser(User user) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			con = DbManager.getInstance().getConnection();
			pstmt = con.prepareStatement(SQL_INSERT_USER, Statement.RETURN_GENERATED_KEYS);
			int k = 1;
			pstmt.setString(k++, user.getLogin());
			pstmt.setString(k++, user.getPassword());
			pstmt.setString(k++, user.getFirstName());
			pstmt.setString(k++, user.getLastName());
			pstmt.setInt(k++, user.getRoleId());
			if (pstmt.executeUpdate() > 0) {
				rs = pstmt.getGeneratedKeys();
				if (rs.next()) {
					user.setId(rs.getInt(1));
					return true;
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				pstmt.close();
				con.close();
				rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return false;
	}
	
	/**
	 * Method allows to update user entity login in DB.
	 * @param user entity
	 * @return true if user entity successfully updated
	 * @see User
	 */
	@Override
	public boolean updateLogin(User user) {
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try {
			con = DbManager.getInstance().getConnection();
			pstmt = con.prepareStatement(SQL_UPDATE_LOGIN);
			int k = 1;
			pstmt.setString(k++, user.getLogin());
			pstmt.setInt(k++, user.getId());
			if (pstmt.executeUpdate() > 0) {
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				pstmt.close();
				con.close();
				//rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return false;
	}

	/**
	 * Method allows to update user entity password in DB.
	 * @param user entity
	 * @return true if user entity successfully updated
	 * @see User
	 */
	@Override
	public boolean updatePassword(User user) {
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try {
			con = DbManager.getInstance().getConnection();
			pstmt = con.prepareStatement(SQL_UPDATE_PASSWORD);
			int k = 1;
			pstmt.setString(k++, user.getPassword());
			pstmt.setInt(k++, user.getId());
			if (pstmt.executeUpdate() > 0) {
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				pstmt.close();
				con.close();
				//rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return false;
	}

	/**
	 * Method allows to update user entity first name in DB.
	 * @param user entity
	 * @return true if user entity successfully updated
	 * @see User
	 */
	@Override
	public boolean updateFirstName(User user) {
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try {
			con = DbManager.getInstance().getConnection();
			pstmt = con.prepareStatement(SQL_UPDATE_FIRST_NAME);
			int k = 1;
			pstmt.setString(k++, user.getFirstName());
			pstmt.setInt(k++, user.getId());
			if (pstmt.executeUpdate() > 0) {
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				pstmt.close();
				con.close();
				//rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return false;
	}

	/**
	 * Method allows to update user entity last name in DB.
	 * @param user entity
	 * @return true if user entity successfully updated
	 * @see User
	 */
	@Override
	public boolean updateLastName(User user) {
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try {
			con = DbManager.getInstance().getConnection();
			pstmt = con.prepareStatement(SQL_UPDATE_LAST_NAME);
			int k = 1;
			pstmt.setString(k++, user.getLastName());
			pstmt.setInt(k++, user.getId());
			if (pstmt.executeUpdate() > 0) {
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				pstmt.close();
				con.close();
				//rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return false;
	}

	/**
	 * Method allows to extract user entity from DB.
	 * Method should be used only in DAO class as handler.
	 * 
	 * @param rs result of query
	 * @return User entity
	 * @throws SQLException if SQL exceptions happen
	 * @see ResultSet
	 * @see User
	 */
	@Override
	public User extractUser(ResultSet rs) throws SQLException {
		User user = new User();
		user.setId(rs.getInt("id"));
		user.setLogin(rs.getString("login"));
		user.setPassword(rs.getString("password"));
		user.setFirstName(rs.getString("first_name"));
		user.setLastName(rs.getString("last_name"));
		user.setBill(rs.getInt("bill"));
		user.setRoleId(rs.getInt("role_id"));
		user.setStatusId(rs.getInt("status_id"));
		return user;
	}

	/**
	 * Method allows to get User entity by its login in DB.
	 * @param login which will be searched for
	 * @return User entity
	 * @see User
	 */
	@Override
	public User getUserByLogin(String login) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			con = DbManager.getInstance().getConnection();
			pstmt = con.prepareStatement(SQL_FIND_USER_BY_LOGIN);
			int k = 1;
			pstmt.setString(k++, login);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				return extractUser(rs);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				pstmt.close();
				con.close();
				rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	/**
	 * Method allows to delete user entity by its login from DB.
	 * @param login which will be searched for
	 * @return true if user successfully deleted
	 * @see User
	 */
	@Override
	public boolean deleteUserByLogin(String login) {
		boolean res = false;
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			con = DbManager.getInstance().getConnection();
			pstmt = con.prepareStatement(SQL_DELETE_USER);
			int k = 1;
			pstmt.setString(k++, login);
			res = pstmt.executeUpdate() > 0;
		} catch (SQLException ex) {
			ex.printStackTrace();
		} finally {
			try {
				con.close();
				pstmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return res;
	}

	/**
	 * Method allows to update user entity bill in DB.
	 * @param user entity
	 * @return true if user entity successfully updated
	 * @see User
	 */
	@Override
	public boolean updateBill(User user) {
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try {
			con = DbManager.getInstance().getConnection();
			pstmt = con.prepareStatement(SQL_UPDATE_BILL);
			int k = 1;
			pstmt.setInt(k++, user.getBill());
			pstmt.setInt(k++, user.getId());
			if (pstmt.executeUpdate() > 0) {
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				pstmt.close();
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return false;
	}

	/**
	 * Method allows to update user entity status in DB.
	 * @param user entity
	 * @return true if user entity successfully updated
	 * @see User
	 */
	@Override
	public boolean updateStatus(User user) {
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try {
			con = DbManager.getInstance().getConnection();
			pstmt = con.prepareStatement(SQL_UPDATE_STATUS);
			int k = 1;
			pstmt.setInt(k++, user.getStatusId());
			pstmt.setInt(k++, user.getId());
			if (pstmt.executeUpdate() > 0) {
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				pstmt.close();
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return false;
	}

	/**
	 * Method allows to obtained all users who are clients.
	 * @return List of User objects which obtains from DB
	 * @see User
	 */
	@Override
	public List<User> getAllClients() {
		List<User> users = new ArrayList<>();
		
		Connection con = null;
		Statement stmt = null;
		ResultSet rs = null;
		
		try {
			con = DbManager.getInstance().getConnection();
			stmt = con.createStatement();
			rs = stmt.executeQuery(SQL_SELECT_ALL_CLIENTS);
			while (rs.next()) {
				User user = extractUser(rs);
				users.add(user);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				rs.close();
				stmt.close();
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return users;
	}

}

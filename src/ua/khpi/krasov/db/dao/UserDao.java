package ua.khpi.krasov.db.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import ua.khpi.krasov.db.DBManager;
import ua.khpi.krasov.db.dao.interfaces.UserDaoInterface;
import ua.khpi.krasov.db.entity.User;

public class UserDao implements UserDaoInterface{

	//private static final Logger log = Logger.getLogger(UserDao.class);

	private String SQL_SELECT_ALL_USERS = "SELECT * FROM users";
	
	private String SQL_INSERT_USER = "INSERT INTO users VALUES (DEFAULT, ?, ?, ?, ?, DEFAULT, ?, DEFAULT)";
	
	private String SQL_UPDATE_LOGIN = "UPDATE users SET login=? WHERE id=?";
	
	private String SQL_FIND_USER_BY_LOGIN = "SELECT * FROM users WHERE login=?";
	
	private String SQL_UPDATE_PASSWORD = "UPDATE users SET password=? WHERE id=?";

	private String SQL_UPDATE_FIRST_NAME = "UPDATE users SET first_name=? WHERE id=?";
	
	private String SQL_UPDATE_LAST_NAME = "UPDATE users SET last_name=? WHERE id=?";
	
	private String SQL_UPDATE_STATUS = "UPDATE users SET status_id=? WHERE id=?";
	
	private String SQL_UPDATE_BILL = "UPDATE users SET bill=? WHERE id=?";
	
	private String SQL_DELETE_USER = "DELETE FROM users WHERE login=?";
	
	
	
	@Override
	public List<User> getAllUsers() {
		List<User> users = new ArrayList<>();
		
		Connection con = null;
		Statement stmt = null;
		ResultSet rs = null;
		
		try {
			con = DBManager.getInstance().getConnection();
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
	

	@Override
	public boolean insertUser(User user) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			con = DBManager.getInstance().getConnection();
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

	@Override
	public boolean updateLogin(User user) {
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try {
			con = DBManager.getInstance().getConnection();
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

	@Override
	public boolean updatePassword(User user) {
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try {
			con = DBManager.getInstance().getConnection();
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

	@Override
	public boolean updateFirstName(User user) {
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try {
			con = DBManager.getInstance().getConnection();
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

	@Override
	public boolean updateLastName(User user) {
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try {
			con = DBManager.getInstance().getConnection();
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
		user.setStatus_id(rs.getInt("status_id"));
		return user;
	}

	@Override
	public User getUserByLogin(String login) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			con = DBManager.getInstance().getConnection();
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


	@Override
	public boolean deleteUser(User user) {
		boolean res = false;
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			con = DBManager.getInstance().getConnection();
			pstmt = con.prepareStatement(SQL_DELETE_USER);
			int k = 1;
			pstmt.setString(k++, user.getLogin());
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


	@Override
	public boolean updateBill(User user) {
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try {
			con = DBManager.getInstance().getConnection();
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


	@Override
	public boolean updateStatus(User user) {
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try {
			con = DBManager.getInstance().getConnection();
			pstmt = con.prepareStatement(SQL_UPDATE_STATUS);
			int k = 1;
			pstmt.setInt(k++, user.getStatus_id());
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

}

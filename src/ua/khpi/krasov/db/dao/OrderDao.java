package ua.khpi.krasov.db.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import ua.khpi.krasov.db.DbManager;
import ua.khpi.krasov.db.dao.interfaces.OrderDaoInterface;
import ua.khpi.krasov.db.entity.Order;
import ua.khpi.krasov.db.entity.User;

/**
 * DAO User class. This class allows to work with DB.
 * This class allows to obtain data connecting with 'isp' DB
 * and table 'orders'. Class implements OrderDaoInterface
 * 
 * @author A.Krasov
 * @see OrderDaoInterface
 * @version 1.0
 * 
 */
public class OrderDao implements OrderDaoInterface {
	
	
	private static String SQL_SELECT_ALL_ORDERS = "SELECT * FROM orders";
	
	private static String SQL_INSERT_ORDER = "INSERT INTO orders VALUES (DEFAULT, ?, ?, ?, ?)";
	
	private static String SQL_DELETE_ORDER = "DELETE FROM orders WHERE id=?";
	
	private static String SQL_FIND_ORDERS_BY_USER = "SELECT * FROM orders WHERE user_id=?";
	
	private static String SQL_UPDATE_ORDER_STATUS = "UPDATE orders SET status_id=? WHERE id=?";
	
	/**
	 * Method allows to obtained all data from DB.
	 * @return List of Order objects which obtains from DB
	 * @see Order
	 */
	@Override
	public List<Order> getAllOrders() {
		List<Order> orders = new ArrayList<>();
		
		Connection con = null;
		Statement stmt = null;
		ResultSet rs = null;
		
		try {
			con = DbManager.getInstance().getConnection();
			stmt = con.createStatement();
			rs = stmt.executeQuery(SQL_SELECT_ALL_ORDERS);
			while (rs.next()) {
				Order order = extractOrder(rs);
				orders.add(order);
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
		return orders;
	}
	

	/**
	 * Method allows to insert data to DB.
	 * 
	 * @param order which is instance of Order
	 * @return true if data successfully saved
	 * @see Order
	 */
	@Override
	public boolean insertOrder(Order order) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			con = DbManager.getInstance().getConnection();
			pstmt = con.prepareStatement(SQL_INSERT_ORDER, Statement.RETURN_GENERATED_KEYS);
			int k = 1;
			pstmt.setInt(k++, order.getUserId());
			pstmt.setInt(k++, order.getServiceId());
			pstmt.setInt(k++, order.getTariffId());
			pstmt.setInt(k++, order.getStatusId());
			if (pstmt.executeUpdate() > 0) {
				rs = pstmt.getGeneratedKeys();
				if (rs.next()) {
					order.setId(rs.getInt(1));
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
	 * Method allows to extract user from DB.
	 * Method should be used only in DAO class as handler.
	 * 
	 * @param rs result of query
	 * @return Order entity
	 * @throws SQLException if SQL exceptions happen
	 * @see ResultSet
	 * @see Order
	 */
	@Override
	public Order extractOrder(ResultSet rs) throws SQLException {
		Order order = new Order();
		order.setId(rs.getInt("id"));
		order.setServiceId(rs.getInt("service_id"));
		order.setStatusId(rs.getInt("status_id"));
		order.setTariffId(rs.getInt("tariff_id"));
		order.setUserId(rs.getInt("user_id"));
		return order;
	}
	
	/**
	 * Method allows to get all users whose id found in
	 * the Order entity.
	 * 
	 * @param user entity
	 * @return List of Order objects which obtains from DB
	 * @see ResultSet
	 * @see Order
	 */
	@Override
	public List<Order> getOrdersByUser(User user) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<Order> orders = new ArrayList<>();
		
		try {
			con = DbManager.getInstance().getConnection();
			pstmt = con.prepareStatement(SQL_FIND_ORDERS_BY_USER);
			int k = 1;
			pstmt.setInt(k++, user.getId());
			rs = pstmt.executeQuery();
			while (rs.next()) {
				Order order = extractOrder(rs);
				orders.add(order);
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
		return orders;
	}

	/**
	 * Method allows to delete order entity from DB.
	 * @param order entity
	 * @return true if order successfully deleted
	 * @see Order
	 */
	@Override
	public boolean deleteOrder(Order order) {
		boolean res = false;
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			con = DbManager.getInstance().getConnection();
			pstmt = con.prepareStatement(SQL_DELETE_ORDER);
			int k = 1;
			pstmt.setInt(k++, order.getId());
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
	 * Method allows to update order entity in DB.
	 * @param order entity
	 * @return true if order successfully updated
	 * @see Order
	 */
	@Override
	public boolean updateStatus(Order order) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			con = DbManager.getInstance().getConnection();
			pstmt = con.prepareStatement(SQL_UPDATE_ORDER_STATUS);
			int k = 1;
			pstmt.setInt(k++, order.getStatusId());
			pstmt.setInt(k++, order.getId());
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

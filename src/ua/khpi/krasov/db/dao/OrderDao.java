package ua.khpi.krasov.db.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import ua.khpi.krasov.db.DBManager;
import ua.khpi.krasov.db.dao.interfaces.OrderDaoInterface;
import ua.khpi.krasov.db.entity.Order;
import ua.khpi.krasov.db.entity.User;

public class OrderDao implements OrderDaoInterface {
	
	private String SQL_SELECT_ALL_ORDERS = "SELECT * FROM orders";
	
	private String SQL_INSERT_ORDER = "INSERT INTO orders VALUES (DEFAULT, ?, ?, ?, ?)";
	
	private String SQL_DELETE_ORDER = "DELETE FROM orders WHERE id=?";
	
	private String SQL_FIND_ORDERS_BY_USER = "SELECT * FROM orders WHERE user_id=?";
	
	private String SQL_UPDATE_ORDER_STATUS = "UPDATE orders SET status_id=? WHERE id=?";

	@Override
	public List<Order> getAllOrders() {
		List<Order> orders = new ArrayList<>();
		
		Connection con = null;
		Statement stmt = null;
		ResultSet rs = null;
		
		try {
			con = DBManager.getInstance().getConnection();
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

	@Override
	public boolean insertOrder(Order order) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			con = DBManager.getInstance().getConnection();
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

	@Override
	public List<Order> getOrdersByUser(User user) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<Order> orders = new ArrayList<>();
		
		try {
			con = DBManager.getInstance().getConnection();
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

	@Override
	public boolean deleteOrder(Order order) {
		boolean res = false;
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			con = DBManager.getInstance().getConnection();
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

	@Override
	public boolean updateStatus(Order order) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			con = DBManager.getInstance().getConnection();
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

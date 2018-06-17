package ua.khpi.krasov.db.dao.interfaces;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import ua.khpi.krasov.db.entity.Order;
import ua.khpi.krasov.db.entity.User;

public interface OrderDaoInterface {
	
	List<Order> getAllOrders();
	
	boolean insertOrder(Order order);
	
	Order extractOrder(ResultSet rs) throws SQLException;
	
	List<Order> getOrdersByUser(User user);
	
	boolean deleteOrder(Order order);
	
	boolean updateStatus(Order order);
}

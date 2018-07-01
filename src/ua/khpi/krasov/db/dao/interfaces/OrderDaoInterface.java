package ua.khpi.krasov.db.dao.interfaces;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import ua.khpi.krasov.db.entity.Order;
import ua.khpi.krasov.db.entity.Service;
import ua.khpi.krasov.db.entity.User;

/**
 * Interface sets a contract for a DAO Order.
 * DAO which works with Order entity must implement this interface.
 *  
 * @author A.Krasov
 * @see Order
 * @version 1.0
 *
 */
public interface OrderDaoInterface {
	
	/**
	 * Method allows to obtained all data from DB.
	 * @return List of Order objects which obtains from DB
	 * @see Order
	 */
	List<Order> getAllOrders();
	
	/**
	 * Method allows to insert data to DB.
	 * 
	 * @param order which is instance of Order
	 * @return true if data successfully saved
	 * @see Order
	 */
	boolean insertOrder(Order order);
	
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
	Order extractOrder(ResultSet rs) throws SQLException;
	
	/**
	 * Method allows to get all users whose id found in
	 * the Order entity.
	 * 
	 * @param user entity
	 * @return List of Order objects which obtains from DB
	 * @see ResultSet
	 * @see Order
	 */
	List<Order> getOrdersByUser(User user);
	
	/**
	 * Method allows to delete order entity from DB.
	 * @param order entity
	 * @return true if order successfully deleted
	 * @see Order
	 */
	boolean deleteOrder(Order order);
	
	/**
	 * Method allows to update order entity in DB.
	 * @param order entity
	 * @return true if order successfully updated
	 * @see Order
	 */
	boolean updateStatus(Order order);
	
	int getOrdersAmountByService(Service service);
}

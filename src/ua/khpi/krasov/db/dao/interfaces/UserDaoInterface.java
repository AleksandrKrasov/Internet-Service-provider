package ua.khpi.krasov.db.dao.interfaces;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import ua.khpi.krasov.db.entity.User;

/**
 * Interface sets a contract for a DAO User.
 * DAO which works with User entity must implement this interface.
 *  
 * @author A.Krasov
 * @see User
 * @version 1.0
 *
 */
public interface UserDaoInterface {
	
	/**
	 * Method allows to obtained all data from DB.
	 * @return List of User objects which obtains from DB
	 * @see User
	 */
	List<User> getAllUsers();
	
	/**
	 * Method allows to obtained all users who are clients.
	 * @return List of User objects which obtains from DB
	 * @see User
	 */
	List<User> getAllClients();
	
	/**
	 * Method allows to insert data to DB.
	 * 
	 * @param user which is instance of User
	 * @return true if data successfully saved
	 * @see User
	 */
	boolean insertUser(User user);
	
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
	User extractUser(ResultSet rs) throws SQLException;
	
	/**
	 * Method allows to get User entity by its login in DB.
	 * @param login which will be searched for
	 * @return User entity
	 * @see User
	 */
	User getUserByLogin(String login);
	
	/**
	 * Method allows to update user entity login in DB.
	 * @param user entity
	 * @return true if user entity successfully updated
	 * @see User
	 */
	boolean updateLogin(User user);
	
	/**
	 * Method allows to update user entity password in DB.
	 * @param user entity
	 * @return true if user entity successfully updated
	 * @see User
	 */
	boolean updatePassword(User user);
	
	/**
	 * Method allows to update user entity first name in DB.
	 * @param user entity
	 * @return true if user entity successfully updated
	 * @see User
	 */
	boolean updateFirstName(User user);
	
	/**
	 * Method allows to update user entity last name in DB.
	 * @param user entity
	 * @return true if user entity successfully updated
	 * @see User
	 */
	boolean updateLastName(User user);
	
	/**
	 * Method allows to update user entity bill in DB.
	 * @param user entity
	 * @return true if user entity successfully updated
	 * @see User
	 */
	boolean updateBill(User user);
	
	/**
	 * Method allows to update user entity status in DB.
	 * @param user entity
	 * @return true if user entity successfully updated
	 * @see User
	 */
	boolean updateStatus(User user);
	
	/**
	 * Method allows to delete user entity by its login from DB.
	 * @param login which will be searched for
	 * @return true if user successfully deleted
	 * @see User
	 */
	boolean deleteUserByLogin(String login);
	
}

package ua.khpi.krasov.db.dao.interfaces;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import ua.khpi.krasov.db.entity.User;

public interface UserDaoInterface {
	
	List<User> getAllUsers();
	
	List<User> getAllClients();
	
	boolean insertUser(User user);
	
	User extractUser(ResultSet rs) throws SQLException;
	
	User getUserByLogin(String login);
	
	boolean updateLogin(User user);
	
	boolean updatePassword(User user);
	
	boolean updateFirstName(User user);
	
	boolean updateLastName(User user);
	
	boolean updateBill(User user);
	
	boolean updateStatus(User user);
	
	boolean deleteUserByLogin(String login);
	
}

package ua.khpi.krasov.db.validation;

import java.util.List;

import ua.khpi.krasov.db.dao.interfaces.OrderDaoInterface;
import ua.khpi.krasov.db.dao.interfaces.UserDaoInterface;
import ua.khpi.krasov.db.entity.Order;
import ua.khpi.krasov.db.entity.User;

public class ValidationUtil {
	
	public static boolean validateUser(User user, UserDaoInterface userDao) {
		List<User> list = userDao.getAllUsers();
		for(User users : list) {
			if(users.getLogin().equals(user.getLogin())) {
				return false;
			}
		}
		if(user.getLogin().length() > 10 || user.getPassword().length() > 10 ||
				user.getFirstName().length() > 20 || user.getLastName().length() > 20)
			return false;
		return true;
	}
	
	public static boolean validateUser(User user) {
		if(user.getLogin().length() > 10 || user.getPassword().length() > 10 ||
				user.getFirstName().length() > 20 || user.getLastName().length() > 20)
			return false;
		return true;
	}
	
	public static boolean validateOrder(Order order, OrderDaoInterface orderDao) {
		List<Order> list = orderDao.getAllOrders();
		for (int i = 0; i < list.size(); i++) {
			if(list.get(i).getServiceId() == order.getServiceId() &&
					list.get(i).getUserId() == order.getUserId()) {
				return false;
			}
		}
		return true;
	}
}

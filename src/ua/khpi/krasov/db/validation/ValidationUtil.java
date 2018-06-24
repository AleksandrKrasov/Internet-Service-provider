package ua.khpi.krasov.db.validation;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import ua.khpi.krasov.db.dao.TariffDao;
import ua.khpi.krasov.db.dao.interfaces.OrderDaoInterface;
import ua.khpi.krasov.db.dao.interfaces.ServiceDaoInterface;
import ua.khpi.krasov.db.dao.interfaces.UserDaoInterface;
import ua.khpi.krasov.db.entity.Order;
import ua.khpi.krasov.db.entity.Service;
import ua.khpi.krasov.db.entity.Tariff;
import ua.khpi.krasov.db.entity.User;

/**
 * This is utility class. Class does not have any methods which
 * belong to the instance of this class. This class is intended to
 * validate all fields in this application.
 * 
 * @author A.Krasov
 * @version 1.0
 *
 */
public class ValidationUtil {
	
	/**
	 * This method validate User entity and use UserDaoInterface
	 * to get data from DB in order to check if such user already
	 * exists. Method encapsulates another methods from this class:
	 * validatePassword(String password), validateLogin(String login),
	 * validateName(String name).
	 * @param user which fields will be validated
	 * @param userDao to get data from DB
	 * @return true if user is valid
	 * @see User
	 * @see UserDaoInterface
	 */
	public static boolean validateUser(User user, UserDaoInterface userDao) {
		if (!validatePassword(user.getPassword())) {
			return false;
		}
		if (!validateLogin(user.getLogin())) {
			return false;
		}
		if (!validateName(user.getFirstName()) && !validateName(user.getLastName())) {
			return false;
		}
		List<User> list = userDao.getAllUsers();
		for (User users : list) {
			if (users.getLogin().equals(user.getLogin())) {
				return false;
			}
		}
		if (user.getLogin().length() > 10 || user.getPassword().length() > 10 
				|| user.getFirstName().length() > 20 || user.getLastName().length() > 20) {
			System.out.println(5);
			return false;
		}
		return true;
	}
	
	/**
	 * This method validate User entity. Method encapsulates another 
	 * methods from this class: validatePassword(String password), 
	 * validateLogin(String login), validateName(String name).
	 * 
	 * @param user which fields will be validated
	 * @return true if user is valid
	 * @see User
	 */
	public static boolean validateUser(User user) {
		if (user == null) {
			return false;
		}
		if (!validatePassword(user.getPassword())) {
			return false;
		}
		if (!validateLogin(user.getLogin())) {
			return false;
		}
		if (!validateName(user.getFirstName()) || !validateName(user.getLastName())) {
			return false;
		}
		if (user.getLogin().length() > 10 || user.getPassword().length() > 10 
				|| user.getFirstName().length() > 20 || user.getLastName().length() > 20) {
			return false;
		}
		return true;
	}
	
	/**
	 * This method validate Order entity and use OrderDaoInterface
	 * to get data from DB in order to check if such order already
	 * exists.
	 * 
	 * @param order which fields will be validated
	 * @param orderDao to get data from DB
	 * @return true if oder is valid
	 * @see OrderDaoInterface
	 * @see Order
	 */
	public static boolean validateOrder(Order order, OrderDaoInterface orderDao) {
		List<Order> list = orderDao.getAllOrders();
		for (int i = 0; i < list.size(); i++) {
			if (list.get(i).getServiceId() == order.getServiceId() 
					&& list.get(i).getUserId() == order.getUserId()) {
				return false;
			}
		}
		return true;
	}
	
	/**
	 * This method validate Service entity and use ServiceDaoInterface
	 * to get data from DB in order to check if such service already
	 * exists. Method encapsulates another method from this class:
	 * validateName(String name).
	 * 
	 * @param service which fields will be validated
	 * @param serviceDao to get data from DB
	 * @return true if service is valid
	 * @see Service
	 * @see ServiceDaoInterface
	 */
	public static boolean validateService(Service service, ServiceDaoInterface serviceDao) {
		if (!validateName(service.getName()) || !validateName(service.getNameRu())) {
			return false;
		}
		if (service.getName().length() > 20) {
			return false;
		}
		List<Service> services = serviceDao.getAllServices();
		for (Service ser : services) {
			if (ser.getName().equals(service.getName())) {
				return false;
			}
		}
		return true;
	}
	
	/**
	 * This method validate User entity. Method encapsulates another 
	 * methods from this class: validateName(String name), 
	 * validateTariffName(Tariff tariff), validateTariffNameRu(Tariff tariff).
	 * 
	 * @param tariff which fields will be validated
	 * @return true if user is valid
	 * @see User
	 */
	public static boolean validateTariff(Tariff tariff) {
		if (!validateName(tariff.getName()) && !validateName(tariff.getNameRu())) {
			return false;
		}
		if (tariff.getPrice() <= 0) {
			return false;
		}
		if (tariff.getName().length() > 20 && tariff.getDescription().length() > 100) {
			return false;
		}
		if (!validateTariffName(tariff) || !validateTariffNameRu(tariff)) {
			return false;
		}
		return true;
	}
	
	/**
	 * This method validates tariff entity's price.
	 * 
	 * @param tariff to get its price
	 * @return true if tariff's price less than 1000000
	 */
	public static boolean validateTariffPrice(Tariff tariff) {
		if (tariff.getPrice() <= 0 || tariff.getPrice() > 1000000) {
			return false;
		}
		return true;
	}
	
	/**
	 * This method validates tariff entity's description.
	 * 
	 * @param tariff to get its price
	 * @return true if tariff's description length less than 100
	 */
	public static boolean validateTariffDescription(Tariff tariff) {
		if (tariff.getDescription().isEmpty() || tariff.getDescription().length() > 100) {
			return false;
		}
		return true;
	}
	
	/**
	 * This method validates name. Method contains regular
	 * expression which allows to validate name. Regular
	 * expression means that mather returns true in case of 
	 * name consists of Latin and Cyrillic symbols and name length
	 * longer than 2.
	 * 
	 * @param name for comparing
	 * @return true if pattern is found
	 */
	public static boolean validateName(String name) {
		String regax = "^[A-Za-zА-Яа-яЁё]{2,}+$";
		Pattern pattern = Pattern.compile(regax);
		Matcher matcher = pattern.matcher(name);
		if (matcher.find()) {
			return true;
		}
		return false;
	}
	
	/**
	 * This method validates login. Method contains regular
	 * expression which allows to validate login. Regular
	 * expression means that mather returns true in case of 
	 * name consists of Latin symbols, numbers and name length
	 * longer than 4.
	 * 
	 * @param login for comparing
	 * @return true if pattern is found
	 */
	public static boolean validateLogin(String login) {
		String regax = "^[\\w\\d]{4,}$";
		Pattern pattern = Pattern.compile(regax);
		Matcher matcher = pattern.matcher(login);
		if (matcher.find()) {
			return true;
		}
		return false;
	}
	
	/**
	 * This method validates login. Method contains regular
	 * expression which allows to validate login. Regular
	 * expression means that mather returns true in case of 
	 * name consists at lest of Latin symbols in uppercase and
	 * lowcase, numbers and name length more than 4
	 * longer than 4.
	 * 
	 * @param password for comparing
	 * @return true if pattern is found
	 */
	public static boolean validatePassword(String password) {
		String regax = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])[0-9a-zA-Z]{4,}$";
		Pattern pattern = Pattern.compile(regax);
		Matcher matcher = pattern.matcher(password);
		if (matcher.find()) {
			return true;
		}
		return false;
	}
	
	/**
	 * This method validates tariff entity's name. This method
	 * gets data from DB in order to check if such name already
	 * exists
	 * @param tariff to get its price
	 * @return true if tariff's description length less than 100
	 */
	public static boolean validateTariffName(Tariff tariff) {
		List<Tariff> tariffs = new TariffDao().getAllTariffs();
		for (Tariff tar : tariffs) {
			if (tar.getName().equals(tariff.getName())) {
				return false;
			}
		}
		return true;
	}
	
	/**
	 * This method validates tariff entity's Russian name. This 
	 * method gets data from DB in order to check if such name already
	 * exists
	 * @param tariff to get its price
	 * @return true if tariff's description length less than 100
	 */
	public static boolean validateTariffNameRu(Tariff tariff) {
		List<Tariff> tariffs = new TariffDao().getAllTariffs();
		for (Tariff tar : tariffs) {
			if (tar.getNameRu().equals(tariff.getNameRu())) {
				return false;
			}
		}
		return true;
	}
}

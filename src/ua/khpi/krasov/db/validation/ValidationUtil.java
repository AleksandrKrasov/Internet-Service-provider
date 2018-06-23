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

public class ValidationUtil {
	
	public static boolean validateUser(User user, UserDaoInterface userDao) {
		if(!validatePassword(user.getPassword())) {
			System.out.println(1);
			return false;
		}
		if(!validateLogin(user.getLogin())) {
			System.out.println(2);
			return false;
		}
		if(!validateName(user.getFirstName()) && !validateName(user.getLastName())) {
			System.out.println(3);
			return false;
		}
		List<User> list = userDao.getAllUsers();
		for(User users : list) {
			if(users.getLogin().equals(user.getLogin())) {
				System.out.println(4);
				return false;
			}
		}
		if(user.getLogin().length() > 10 || user.getPassword().length() > 10 ||
				user.getFirstName().length() > 20 || user.getLastName().length() > 20) {
			System.out.println(5);
			return false;
		}
		return true;
	}
	
	public static boolean validateUser(User user) {
		if(!validatePassword(user.getPassword()))
			return false;
		if(!validateLogin(user.getLogin()))
			return false;
		if(!validateName(user.getFirstName()) || !validateName(user.getLastName()))
			return false;
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
	
	public static boolean validateService(Service service, ServiceDaoInterface serviceDao) {
		if(!validateName(service.getName()) || !validateName(service.getNameRu()))
			return false;
		if(service.getName().length() > 20)
			return false;
		List<Service> services = serviceDao.getAllServices();
		for(Service ser : services) {
			if(ser.getName().equals(service.getName()))
				return false;
		}
		return true;
	}
	
	public static boolean validateTariff(Tariff tariff) {
		if(!validateName(tariff.getName()) && !validateName(tariff.getNameRu())) {
			System.out.println(1);
			return false;
		}
		if(tariff.getPrice() <= 0) {
			System.out.println(2);
			return false;
		}
		if(tariff.getName().length() > 20 && tariff.getDescription().length() > 100) {
			System.out.println(3);
			return false;
		}
		/*List<Tariff> tariffs = new TariffDao().getAllTariffs();
		for(Tariff tar : tariffs) {
			if(tar.getName().equals(tariff.getName())) {
				System.out.println(tar.getName() + " === " + tariff.getName());
				return false;
			}
		}*/
		if(!validateTariffName(tariff) || validateTariffNameRu(tariff)) {
			System.out.println(4);
			return false;
		}
		return true;
		
	}
	
	public static boolean validateTariffPrice(Tariff tariff) {
		if(tariff.getPrice() <= 0 || tariff.getPrice() > 1000000)
			return false;
		return true;
	}
	
	public static boolean validateTariffDescription(Tariff tariff) {
		if(tariff.getDescription().isEmpty() || tariff.getDescription().length() > 100)
			return false;
		return true;
	}
	
	public static boolean validateName(String name) {
		String regax = "^[A-Za-zА-Яа-яЁё]{2,}+$";
		Pattern pattern = Pattern.compile(regax);
		Matcher matcher = pattern.matcher(name);
		if(matcher.find()) {
			return true;
		}
		return false;
	}
	
	public static boolean validateLogin(String login) {
		String regax = "^[\\w\\d]{4,}$";
		Pattern pattern = Pattern.compile(regax);
		Matcher matcher = pattern.matcher(login);
		if(matcher.find())
			return true;
		return false;
	}
	
	public static boolean validatePassword(String password) {
		String regax = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])[0-9a-zA-Z]{4,}$";
		Pattern pattern = Pattern.compile(regax);
		Matcher matcher = pattern.matcher(password);
		if(matcher.find())
			return true;
		return false;
	}
	
	public static boolean validateTariffName(Tariff tariff) {
		List<Tariff> tariffs = new TariffDao().getAllTariffs();
		for(Tariff tar : tariffs) {
			if(tar.getName().equals(tariff.getName())) {
				System.out.println(tar.getName() + " === " + tariff.getName());
				return false;
			}
		}
		return true;
	}
	
	public static boolean validateTariffNameRu(Tariff tariff) {
		List<Tariff> tariffs = new TariffDao().getAllTariffs();
		for(Tariff tar : tariffs) {
			if(tar.getNameRu().equals(tariff.getNameRu())) {
				System.out.println(tar.getNameRu() + " === " + tariff.getNameRu());
				return false;
			}
		}
		return true;
	}
}

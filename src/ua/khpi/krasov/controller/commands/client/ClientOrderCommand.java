package ua.khpi.krasov.controller.commands.client;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import ua.khpi.krasov.controller.Path;
import ua.khpi.krasov.controller.commands.Command;
import ua.khpi.krasov.db.dao.OrderDao;
import ua.khpi.krasov.db.dao.ServiceDao;
import ua.khpi.krasov.db.dao.TariffDao;
import ua.khpi.krasov.db.dao.UserDao;
import ua.khpi.krasov.db.dao.interfaces.OrderDaoInterface;
import ua.khpi.krasov.db.dao.interfaces.ServiceDaoInterface;
import ua.khpi.krasov.db.dao.interfaces.TariffDaoInterface;
import ua.khpi.krasov.db.entity.Order;
import ua.khpi.krasov.db.entity.User;
import ua.khpi.krasov.db.Status;

public class ClientOrderCommand implements Command {
	
	private static final Logger log = Logger.getLogger(ClientOrderCommand.class);

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		log.debug("Client orders command starts");
		
		HttpSession session = request.getSession();
		
		User user = (User) session.getAttribute("user");
		user = new UserDao().getUserByLogin(user.getLogin());
		session.setAttribute("user", user);
		session.setAttribute("status", Status.getStatus(user).getName());
		log.trace("User refreshed is session.");
		
		OrderDaoInterface orderDao = new OrderDao();
		
		List<Order> orders = orderDao.getOrdersByUser(user);
		log.trace("User's orders were obtained from DB ==> " + orders);
		
		List<String> serviceNames = new ArrayList<>();
		List<String> tariffNames = new ArrayList<>();
		List<String> statusNames = new ArrayList<>();
		List<Integer> prices = new ArrayList<>();
		
		ServiceDaoInterface serviceDao = new ServiceDao();
		TariffDaoInterface tariffDao = new TariffDao();
		
		String order = request.getParameter("orderId");
		log.trace("Request param ==> " + order);
		
		if(order != null) {
			log.debug("Deleting order starts");
			
			String errorMessage = null;		
			String forward = Path.ERROR_PAGE;
			
			Status status = Status.getStatus(user);
			
			if(!status.equals(Status.CONFIRMED)){
				log.error("User is not confirmed/blocked");
				errorMessage = "Yor account is not confirmed/blocked";
				request.setAttribute("errorMessage", errorMessage);
				return forward;
			}
			
			try {
				int orderId = Integer.parseInt(order);
				for (int i = 0; i < orders.size(); i++) {
					if(orders.get(i).getId() == orderId) {
						orderDao.deleteOrder(orders.get(i));
					}
				}
			} catch (Exception e) {
				errorMessage = "Can not cast orderId to int";
				request.setAttribute("errorMessage", errorMessage);
				log.error(errorMessage, e);
				return forward;
			}
			log.trace("Oder seccessfuly deleted");
			log.trace("No redirect to " + Path.CLIENT_ORDERS_REDIRECT);
			response.sendRedirect(Path.CLIENT_ORDERS_REDIRECT);
			return null;
		}
		
		//getting order info
		log.trace("Getting order's info starts");
		log.trace("Amount of orders ==> " + orders.size());
		for (int i = 0; i < orders.size(); i++) {
			serviceNames.add(serviceDao.getServiceById(orders.get(i).getServiceId()).getName());
			tariffNames.add(tariffDao.getTariffByID(orders.get(i).getTariffId()).getName());
			statusNames.add(Status.getStatus(orders.get(i)).getName());
			prices.add(tariffDao.getTariffByID(orders.get(i).getTariffId()).getPrice());
		}
		log.trace("Service names ==> " + serviceNames);
		log.trace("Tariff names ==> " + tariffNames);
		log.trace("Status names ==> " + statusNames);
		log.trace("Prices ==> " + prices);
		log.trace("Getting order's info finished");
		
		session.setAttribute("serviceNames", serviceNames);
		log.trace("Service names were added to session.");
		session.setAttribute("tariffNames", tariffNames);
		log.trace("Tariff names were added to session.");
		session.setAttribute("statusNames", statusNames);
		log.trace("Status names were added to session.");
		session.setAttribute("prices", prices);
		log.trace("Prices were added to session.");
		session.setAttribute("orderSize", orders.size());
		log.trace("Orders size was added to session.");
		session.setAttribute("orders", orders);
		log.trace("Orders were added to session.");
		
		return Path.CLIENT_ORDERS_PAGE;
	}
	

}

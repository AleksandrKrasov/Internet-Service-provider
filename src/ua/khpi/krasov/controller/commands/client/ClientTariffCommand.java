package ua.khpi.krasov.controller.commands.client;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.jstl.core.Config;
import org.apache.log4j.Logger;
import ua.khpi.krasov.controller.Path;
import ua.khpi.krasov.controller.commands.Command;
import ua.khpi.krasov.db.Language;
import ua.khpi.krasov.db.Status;
import ua.khpi.krasov.db.dao.OrderDao;
import ua.khpi.krasov.db.dao.ServiceDao;
import ua.khpi.krasov.db.dao.ServiceTariffsDao;
import ua.khpi.krasov.db.dao.TariffDao;
import ua.khpi.krasov.db.dao.UserDao;
import ua.khpi.krasov.db.dao.interfaces.OrderDaoInterface;
import ua.khpi.krasov.db.dao.interfaces.TariffDaoInterface;
import ua.khpi.krasov.db.entity.Order;
import ua.khpi.krasov.db.entity.Service;
import ua.khpi.krasov.db.entity.Tariff;
import ua.khpi.krasov.db.entity.User;
import ua.khpi.krasov.db.validation.ValidationUtil;

/**
 * Client tariff command class. It implements command pattern
 * and used to make an order. This
 * functional is not available for a administrator.
 * 
 * @author A.Krasov
 * @version 1.0
 *
 */
public class ClientTariffCommand implements Command {

	private static final Logger log = Logger.getLogger(ClientServiceCommand.class);

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		log.debug("Client tariff command starts");

		HttpSession session = request.getSession();
		
		if (session.getAttribute("user") == null) {
			return Path.LOGIN_PAGE;
		}
		
		User user = (User) session.getAttribute("user");
		user = new UserDao().getUserByLogin(user.getLogin());
		session.setAttribute("user", user);
		
		Locale locale = (Locale) Config.get(request.getSession(), Config.FMT_LOCALE);
		Language lang = Language.getLanguage(locale);
		
		if (lang.equals(Language.RU)) {
			session.setAttribute("status", Status.getStatus(user).getNameRu());
		} else {
			session.setAttribute("status", Status.getStatus(user).getName());
		}
		log.trace("User refreshed is session.");
		
		// error handler
		String errorMessage = null;
		String forward = Path.ERROR_PAGE;
		ResourceBundle bundle = ResourceBundle.getBundle("resources",
				(Locale) Config.get(request.getSession(), Config.FMT_LOCALE));

		String serviceName = request.getParameter("serviceName");
		log.trace("Selected service ==> " + serviceName);

		Service service = new ServiceDao().getServiceByName(serviceName);
		log.trace("Service found in DB ==> " + service);
		
		if (service != null) {
			session.setAttribute("service", service);
			log.trace("Attribute was added to session with value  ==> " + service);
		}

		String orderParam = request.getParameter("order");

		if (orderParam != null) {
			log.debug("Order selected.");
			
			Status status = Status.getStatus(user);
			
			if (!status.equals(Status.CONFIRMED)) {
				log.error("Yor account is not confirmed/blocked");
				errorMessage = bundle.getString("error.bill.blocked");
				request.setAttribute("errorMessage", errorMessage);
				return forward;
			}
			
			service = (Service) session.getAttribute("service");
			log.trace("Attribute was found in session with value ==>" + service);

			
			String tariffName = request.getParameter("tariffName");
			log.trace("Selected tariff ==> " + tariffName);
			
			TariffDaoInterface tariffDao = new TariffDao();
			
			Tariff tariff = tariffDao.getTarrifByName(tariffName);
			log.trace("Tariff found in DB ==> " + tariff);
			
			//creating an Order
			Order order = new Order();
			order.setUserId(user.getId());
			order.setServiceId(service.getId());
			order.setTariffId(tariff.getId());
			
			session.setAttribute("service", null);
			log.trace("Session attribute was deleted");
			
			String redirect = Path.SERVICES_REDIRECT_PAGE;
			
			//checking bill
			int bill = user.getBill();
			log.trace("User bill ==> " + bill);
			int price = tariff.getPrice();
			log.trace("Tariff price ==> " + price);
			
			//checking if service is unique in order list
			OrderDaoInterface orderDao = new OrderDao();
			List<Order> orders = orderDao.getOrdersByUser(user);
			log.trace("User's orders obtained == > " + orders);
			
			if (orders.isEmpty() || ValidationUtil.validateOrder(order, orderDao)) {
				if (bill >= price) {
					bill = bill - price;
					user.setBill(bill);
					log.trace("New user's bull ==> " + bill);
					new UserDao().updateBill(user);
					log.trace("User's bill was updated in DB.");
					order.setStatusId(Status.PAID.getStatusId());
				} else {
					order.setStatusId(Status.UNPAID.getStatusId());
				}
				orderDao.insertOrder(order);
				redirect += "&completed=True";
				log.trace("Order was added in DB ==>" + order);
				log.trace("Redirecting to " + redirect);
				response.sendRedirect(redirect);
			} else {
				log.trace("Service in order is not unique");
				redirect += "&completed=false";
				log.trace("Order was NOT added in DB ==>" + order);
				log.trace("Redirecting to " + redirect);
				response.sendRedirect(redirect);
			}
			
			return null;
			
		} else {

			List<Integer> idList = new ServiceTariffsDao().getAllIdByServiceId(service);
			log.trace("Service tariff found in DB with id ==> " + idList);

			List<Tariff> tariffs = new ArrayList<>();
			List<String> tariffNames = new ArrayList<>();
			List<String> tariffDesc = new ArrayList<>();

			TariffDao tariffDao = new TariffDao();

			log.debug("Finding tariffs starts");
			for (int i = 0; i < idList.size(); i++) {
				tariffs.add(tariffDao.getTariffById(idList.get(i)));
			}
			log.debug("Finding tariffs finished, amount of tariffs ==> " + tariffs.size());
			
			String sortBy = request.getParameter("sortBy");
			
			if (sortBy != null) {
				if (sortBy.equals("price")) {
					log.trace("Sort by price.");
					tariffs.sort((Tariff tar1, Tariff tar2) -> {
						return tar2.getPrice() - tar1.getPrice();
					});
				} else if (sortBy.equals("alphabet")) {
					log.trace("sortBy alphabet.");
					if (lang.equals(Language.EN)) {
						tariffs.sort((Tariff tar1, Tariff tar2) -> {
							return tar1.getName().compareTo(tar2.getName());
						});
					} else {
						tariffs.sort((Tariff tar1, Tariff tar2) -> {
							return tar1.getNameRu().compareTo(tar2.getNameRu());
						});
					}
				}
			}
			
			for (int i = 0; i < tariffs.size(); i++) {
				
				if (lang.equals(Language.RU)) {
					log.trace("Language ==> " + Language.RU);
					tariffNames.add(tariffs.get(i).getNameRu());
					tariffDesc.add(tariffs.get(i).getDescriptionRu());
				}
				
				if (lang.equals(Language.EN)) {
					tariffNames.add(tariffs.get(i).getName());
					tariffDesc.add(tariffs.get(i).getDescription());
				}
			}

			request.setAttribute("tariffs", tariffs);
			request.setAttribute("tariffNames", tariffNames);
			request.setAttribute("tariffDesc", tariffDesc);

			return Path.CLIENT_TARIFFS_PAGE;
		}
	}

}

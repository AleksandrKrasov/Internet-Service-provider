package ua.khpi.krasov.controller.commands.client;

import java.io.IOException;
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
import ua.khpi.krasov.db.dao.TariffDao;
import ua.khpi.krasov.db.dao.UserDao;
import ua.khpi.krasov.db.dao.interfaces.OrderDaoInterface;
import ua.khpi.krasov.db.dao.interfaces.TariffDaoInterface;
import ua.khpi.krasov.db.entity.Order;
import ua.khpi.krasov.db.entity.User;

/**
 * Bill refill command class. It implements command pattern
 * and used to refill the user bill. This
 * functional is not available for a administrator.
 * 
 * @author A.Krasov
 * @version 1.0
 *
 */
public class BillRefillCommand implements Command {

	private static final Logger log = Logger.getLogger(BillRefillCommand.class);
	
	/**
	 * Methods allows to get all tariffs from chosen
	 * service. Method also allows to make an order and to
	 * sort orders by price and name.
	 */
	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		log.trace("Bill Refill Command starts");
		
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

		String sum = request.getParameter("summ");
		
		
		//PRG pattern
		String completed = (String) session.getAttribute("completed");
		//PRG pattern
		
		// error handler
		String errorMessage = null;
		String forward = Path.ERROR_PAGE;
		ResourceBundle bundle = ResourceBundle.getBundle("resources",
				(Locale) Config.get(request.getSession(), Config.FMT_LOCALE));
		
		log.trace("Summ == > " + sum);

		if (sum != null && completed == null) {
			int summInt = 0;

			//Checking if sum contains "-"
			if (sum.contains("-")) {
				log.error("Sum contains \"-\"");
				errorMessage = bundle.getString("error.bill.minus");
				request.setAttribute("errorMessage", errorMessage);
				return forward;
			}

			try {
				summInt = Integer.valueOf(sum);
				log.trace("Casting to int complited.");
			} catch (Exception e) {
				log.error("Can not cast to int", e);
				errorMessage = bundle.getString("error.bill.digital");
				request.setAttribute("errorMessage", errorMessage);
				return forward;
			}
			
			Status status = Status.getStatus(user);
			
			if (!status.equals(Status.CONFIRMED)) {
				log.error("User is not confirmed/blocked");
				errorMessage = bundle.getString("error.bill.blocked");
				request.setAttribute("errorMessage", errorMessage);
				return forward;
			}
			
			if (user.getBill() + summInt > 1000000) {
				log.error("Bill is to big.");
				errorMessage = bundle.getString("error.bill.max");
				request.setAttribute("errorMessage", errorMessage);
				return forward;
			}

			user.setBill(user.getBill() + summInt);
			log.trace("New value of user's bill is " + user.getBill());
			
			log.trace("Now checking orders with status UNPAID");
			OrderDaoInterface orderDao = new OrderDao();
			List<Order> orders = orderDao.getOrdersByUser(user);
			
			if (orders.size() > 0) {
				TariffDaoInterface tariffDao = new TariffDao();
				log.trace("User's orders found in DB.");
				for (int i = 0; i < orders.size(); i++) {
					int price = tariffDao.getTariffById(orders.get(i).getTariffId()).getPrice();
					if (orders.get(i).getStatusId() == Status.UNPAID.getStatusId()
							&& user.getBill() >= price) {
						log.trace("Unpaid order found, automatically paid operation starts.");
						user.setBill(user.getBill() - price);
						orders.get(i).setStatusId(Status.PAID.getStatusId());
						orderDao.updateStatus(orders.get(i));
						log.trace("Order status was update in DB");
					}
				}
			} else {
				log.trace("User's orders NOT found in DB.");
			}

			if (new UserDao().updateBill(user)) {
				log.trace("Value of the bill in DB is refreshed.");
			} else {
				log.error("Value of the bill in DB is NOT refreshed.");
			}
			
			//PRG pattern
			session.setAttribute("completed", "true");
			//PRG pattern
		}
		
		//PRG pattern
		if (session.getAttribute("completed") != null) {
			session.setAttribute("completed", null);
			log.trace("Resending!!! Redirect to controller with parameter(billRefill)");
			response.sendRedirect(Path.BILL_REDIRECT_PAGE);
		} else {
			return Path.BILL_REFILL_PAGE;
		}
		//PRG pattern
		return null;
	}

}

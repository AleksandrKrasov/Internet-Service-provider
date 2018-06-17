package ua.khpi.krasov.controller.commands;

import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.log4j.Logger;
import ua.khpi.krasov.controller.Path;
import ua.khpi.krasov.db.Status;
import ua.khpi.krasov.db.dao.OrderDao;
import ua.khpi.krasov.db.dao.TariffDao;
import ua.khpi.krasov.db.dao.UserDao;
import ua.khpi.krasov.db.dao.interfaces.OrderDaoInterface;
import ua.khpi.krasov.db.dao.interfaces.TariffDaoInterface;
import ua.khpi.krasov.db.entity.Order;
import ua.khpi.krasov.db.entity.User;

public class BillRefillCommand implements Command {

	private static final Logger log = Logger.getLogger(BillRefillCommand.class);

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		log.trace("Bill Refill Command starts");
		
		HttpSession session = request.getSession();

		String sum = request.getParameter("summ");
		
		//PRG pattern
		String completed = (String) session.getAttribute("completed");
		//PRG pattern
		
		log.trace("Summ == > " + sum);

		if (sum != null && completed == null) {
			int summInt = 0;

			// error handler
			String errorMessage = null;
			String forward = Path.ERROR_PAGE;
			
			//Checking if sum contains "-"
			if(sum.contains("-")) {
				log.error("Sum contains \"-\"");
				errorMessage = "The sum can not contain \"-\"";
				request.setAttribute("errorMessage", errorMessage);
				return forward;
			}

			try {
				summInt = Integer.valueOf(sum);
				log.trace("Casting to int complited.");
			} catch (Exception e) {
				log.error("Can not cast to int", e);
				errorMessage = "You must type only digital value";
				request.setAttribute("errorMessage", errorMessage);
				return forward;
			}

			User user = (User) session.getAttribute("user");

			user.setBill(user.getBill() + summInt);
			log.trace("New value of user's bill is " + user.getBill());
			
			log.trace("Now checking orders with status UNPAID");
			OrderDaoInterface orderDao = new OrderDao();
			List<Order> orders = orderDao.getOrdersByUser(user);
			
			if(orders.size() > 0) {
				TariffDaoInterface tariffDao = new TariffDao();
				log.trace("User's orders found in DB.");
				for (int i = 0; i < orders.size(); i++) {
					int price = tariffDao.getTariffByID(orders.get(i).getTariffId()).getPrice();
					if(orders.get(i).getStatusId() == Status.UNPAID.getStatusId() && user.getBill() >= price) {
						log.trace("Unpaid order found, automatically paid operation starts.");
						user.setBill(user.getBill() - price);
						orders.get(i).setStatusId(Status.PAID.getStatusId());
						orderDao.updateStatus(orders.get(i));
						log.trace("Order status was update in DB");
					}
				}
			}else {
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
		if(session.getAttribute("completed") != null) {
			session.setAttribute("completed", null);
			log.trace("Resending!!! Redirect to controller with parameter(billRefill)");
			response.sendRedirect(Path.BILL_REDIRECT_PAGE);
		}else 
			return Path.BILL_REFILL_PAGE;
		//PRG pattern
		return null;
	}

}

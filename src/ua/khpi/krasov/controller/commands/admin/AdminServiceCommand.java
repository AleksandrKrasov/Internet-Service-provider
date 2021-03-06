package ua.khpi.krasov.controller.commands.admin;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.jstl.core.Config;

import org.apache.log4j.Logger;

import sun.net.NetworkServer;
import ua.khpi.krasov.controller.Path;
import ua.khpi.krasov.controller.commands.Command;
import ua.khpi.krasov.db.Language;
import ua.khpi.krasov.db.dao.OrderDao;
import ua.khpi.krasov.db.dao.ServiceDao;
import ua.khpi.krasov.db.dao.interfaces.ServiceDaoInterface;
import ua.khpi.krasov.db.entity.Service;

/**
 * Administrator service command class. It implements command pattern
 * and used to to show all services and change them.
 * 
 * @author A.Krasov
 * @version 1.0
 *
 */
public class AdminServiceCommand implements Command {

	private static final Logger log = Logger.getLogger(AdminServiceCommand.class);
	
	/**
	 * Method allows to get all services. Thanks to this
	 * command user is able to choose what to to do with service:
	 * to add a new one, to rename, to delete or to get service
	 * tariffs.
	 */
	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		log.trace("Admin service command starts");
		
		if (request.getSession().getAttribute("user") == null) {
			return Path.LOGIN_PAGE;
		}
		
		ServiceDaoInterface serviceDao = new ServiceDao();
		
		String serviceName = request.getParameter("serviceName");
		
		if (serviceName != null) {
			Service service = serviceDao.getServiceByName(serviceName);
			serviceDao.deleteService(service);
			log.trace("Service was deleted.");
			response.sendRedirect(Path.ADMIN_SERVICE_REDIRECT_PAGE);
		}
		
		//Getting services list
		List<Service> servicelist = serviceDao.getAllServices();
		List<String> serviceNames = new ArrayList<>();
		log.trace("List of services from DB obtained");
		
		//Amount of paid orders
		List<Integer> paidOrderAmount = new ArrayList<>();
		
		for(Service service: servicelist) {
			paidOrderAmount.add(new OrderDao().getOrdersAmountByService(service));
		}
		
		request.setAttribute("paidOrderAmount", paidOrderAmount);
		//////////////////
		
		//System.out.println(paidOrderAmount);
		
		Locale locale = (Locale) Config.get(request.getSession(), Config.FMT_LOCALE);
		Language lang = Language.getLanguage(locale);
		
		if (lang.equals(Language.RU)) {
			log.trace("Language ==> " + Language.RU);
			for (int i = 0; i < servicelist.size(); i++) {
				serviceNames.add(servicelist.get(i).getNameRu());
			}
		}
		
		if (lang.equals(Language.EN)) {
			log.trace("Language ==> " + Language.EN);
			for (int i = 0; i < servicelist.size(); i++) {
				serviceNames.add(servicelist.get(i).getName());
			}
		}
		
		request.setAttribute("servicelist", servicelist);
		request.setAttribute("serviceNames", serviceNames);
		
		return Path.ADMIN_SERVICE_PAGE;
	}

}

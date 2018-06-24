package ua.khpi.krasov.controller.commands.client;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
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
import ua.khpi.krasov.db.dao.ServiceDao;
import ua.khpi.krasov.db.dao.UserDao;
import ua.khpi.krasov.db.entity.Service;
import ua.khpi.krasov.db.entity.User;

/**
 * Client service command class. It implements command pattern
 * and used to show all possible services. This
 * functional is not available for a administrator.
 * 
 * @author A.Krasov
 * @version 1.0
 *
 */
public class ClientServiceCommand implements Command {
	
	private static final Logger log = Logger.getLogger(ClientServiceCommand.class);
	
	/**
	 * Methods allows to the user to chose the service he
	 * wants to order.
	 */
	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		log.trace("Client service command starts");
		
		if (request.getSession().getAttribute("user") == null) {
			return Path.LOGIN_PAGE;
		}
		
		HttpSession session = request.getSession();
		
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
		
		//Getting services list
		List<Service> servicelist = new ServiceDao().getAllServices();
		List<String> serviceNames = new ArrayList<>();
		log.trace("List of services from DB obtained");
		
		
		for (int i = 0; i < servicelist.size(); i++) {
			if (lang.equals(Language.RU)) {
				log.trace("Language ==> " + Language.RU);
				serviceNames.add(servicelist.get(i).getNameRu());
			}
			if (lang.equals(Language.EN)) {
				log.trace("Language ==> " + Language.EN);
				serviceNames.add(servicelist.get(i).getName());
			}
		}
		
		request.setAttribute("serviceNames", serviceNames);
		request.setAttribute("servicelist", servicelist);
		
		return Path.CLIENT_SERVICES_PAGE;
	}

}

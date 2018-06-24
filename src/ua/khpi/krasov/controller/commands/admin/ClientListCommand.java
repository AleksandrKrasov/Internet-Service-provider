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
import ua.khpi.krasov.controller.Path;
import ua.khpi.krasov.controller.commands.Command;
import ua.khpi.krasov.db.Language;
import ua.khpi.krasov.db.Status;
import ua.khpi.krasov.db.dao.UserDao;
import ua.khpi.krasov.db.dao.interfaces.UserDaoInterface;
import ua.khpi.krasov.db.entity.User;

/**
 * Client list command class. It implements command pattern
 * and used show all clients an make operations over them.
 * 
 * @author A.Krasov
 * @version 1.0
 *
 */
public class ClientListCommand implements Command {
	
	private static final Logger log = Logger.getLogger(ClientListCommand.class);
	
	/**
	 * Methods allows to get all clients. User may change 
	 * client status to confirmed or block and delete the user.
	 */
	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		log.debug("Client list command starts");
		
		if (request.getSession().getAttribute("user") == null) {
			return Path.LOGIN_PAGE;
		}
		
		UserDaoInterface userDao = new UserDao();
		
		String clientLogin = (String) request.getParameter("clientLogin");
		String delete = (String) request.getParameter("delete");
		String changeStatus = (String) request.getParameter("changeStatus");
		
		if (clientLogin != null && delete != null) {
			String redirect = Path.CLIENT_LIST_REDIRECT_PAGE;
			log.trace("Deleting user with loging ==> " + clientLogin);
			if (userDao.deleteUserByLogin(clientLogin)) {
				log.trace("User was deleted from DB.");
				log.trace("Redirecting to ==>" + redirect);
				response.sendRedirect(redirect);
			} else {
				log.trace("User was NOT deleted from DB.");
				log.trace("Redirecting to ==>" + redirect);
				response.sendRedirect(redirect);
			}
			return null;
		} else if (clientLogin != null && changeStatus != null) {
			User user = userDao.getUserByLogin(clientLogin);
			log.trace("Changing status for user with login ==> " + clientLogin);
			
			if (Status.BLOCKED.equals(Status.valueOf(changeStatus.toUpperCase()))) {
				user.setStatusId(Status.BLOCKED.getStatusId());
			}
			
			if (Status.CONFIRMED.equals(Status.valueOf(changeStatus.toUpperCase()))) {
				user.setStatusId(Status.CONFIRMED.getStatusId());
			}
			
			userDao.updateStatus(user);
			log.trace("User status changed in DB to ==> " + changeStatus);
			
			String redirect = Path.CLIENT_LIST_REDIRECT_PAGE;
			response.sendRedirect(redirect);
			return null;
		}
		
		List<User> clientList = userDao.getAllClients();
		List<String> statusNames = new ArrayList<>();
		List<String> radioStatusNames = new ArrayList<>();
		
		request.setAttribute("clientList", clientList);
		log.trace("Attribute was added to session with name (clients)");
		
		request.setAttribute("status", Status.values());
		
		Locale locale = (Locale) Config.get(request.getSession(), Config.FMT_LOCALE);
		Language lang = Language.getLanguage(locale);
		
		if (lang.equals(Language.RU)) {
			radioStatusNames.add(Status.CONFIRMED.getNameRu());
			radioStatusNames.add(Status.BLOCKED.getNameRu());
			log.trace("Language ==> " + Language.RU);
			for (int i = 0; i < clientList.size(); i++) {
				statusNames.add(Status.getStatus(clientList.get(i)).getNameRu());
			}
		}
		
		if (lang.equals(Language.EN)) {
			radioStatusNames.add(Status.CONFIRMED.getName());
			radioStatusNames.add(Status.BLOCKED.getName());
			log.trace("Language ==> " + Language.EN);
			for (int i = 0; i < clientList.size(); i++) {
				statusNames.add(Status.getStatus(clientList.get(i)).getName());
			}
		}
		
		for (int i = 0; i < clientList.size(); i++) {
			statusNames.add(Status.getStatus(clientList.get(i)).getName());
		}
		
		request.setAttribute("statusNames", statusNames);
		request.setAttribute("radioStatusNames", radioStatusNames);
		log.trace("Attribute was added to requset with name (statusNames)");
		
		return Path.CLIENT_LIST_PAGE;
	}

}

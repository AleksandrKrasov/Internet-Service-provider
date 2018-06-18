package ua.khpi.krasov.controller.commands.admin;

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
import ua.khpi.krasov.db.Status;
import ua.khpi.krasov.db.dao.UserDao;
import ua.khpi.krasov.db.dao.interfaces.UserDaoInterface;
import ua.khpi.krasov.db.entity.User;

public class ClientListCommand implements Command {
	
	private static final Logger log = Logger.getLogger(ClientListCommand.class);

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		log.debug("Client list command starts");
		
		UserDaoInterface userDao = new UserDao();
		HttpSession session = request.getSession();
		
		String clientLogin = (String) request.getParameter("clientLogin");
		String delete = (String) request.getParameter("delete");
		String changeStatus = (String) request.getParameter("changeStatus");
		
		if(clientLogin != null && delete != null) {
			String redirect = Path.CLIENT_LIST_REDIRECT_PAGE;
			log.trace("Deleting user with loging ==> " + clientLogin);
			if(userDao.deleteUserByLogin(clientLogin)) {
				log.trace("User was deleted from DB.");
				log.trace("Redirecting to ==>" + redirect);
				response.sendRedirect(redirect);
			} else {
				log.trace("User was NOT deleted from DB.");
				log.trace("Redirecting to ==>" + redirect);
				response.sendRedirect(redirect);
			}
			return null;
		} else if(clientLogin != null && changeStatus != null) {
			User user = userDao.getUserByLogin(clientLogin);
			log.trace("Changing status for user with login ==> " + clientLogin);
			
			if(Status.BLOCKED.equals(Status.valueOf(changeStatus.toUpperCase())))
				user.setStatus_id(Status.BLOCKED.getStatusId());
			
			if(Status.CONFIRMED.equals(Status.valueOf(changeStatus.toUpperCase())))
				user.setStatus_id(Status.CONFIRMED.getStatusId());
			
			userDao.updateStatus(user);
			log.trace("User status changed in DB to==> " + changeStatus);
			
			String redirect = Path.CLIENT_LIST_REDIRECT_PAGE;
			response.sendRedirect(redirect);
			return null;
		}
		
		List<User> clientList = userDao.getAllClients();
		List<String> statusNames = new ArrayList<>();
		
		session.setAttribute("clientList", clientList);
		log.trace("Attribute was added to session with name (clients)");
		
		session.setAttribute("status", Status.values());
		
		for (int i = 0; i < clientList.size(); i++) {
			statusNames.add(Status.getStatus(clientList.get(i)).getName());
		}
		
		session.setAttribute("statusNames", statusNames);
		log.trace("Attribute was added to session with name (statusNames)");
		
		
		
		

		
		
		return Path.CLIENT_LIST_PAGE;
	}

}

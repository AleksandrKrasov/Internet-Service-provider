package ua.khpi.krasov.controller.commands;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import ua.khpi.krasov.controller.Path;
import ua.khpi.krasov.db.Role;
import ua.khpi.krasov.db.Status;
import ua.khpi.krasov.db.dao.UserDao;
import ua.khpi.krasov.db.entity.User;


public class LoginCommand implements Command{
	
	private static final Logger log = Logger.getLogger(LoginCommand.class);

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		
		log.debug("Login command starts");
		
		String login = request.getParameter("login");
		String password = request.getParameter("password");
		log.trace("Request parameter: loging --> " + login);
		
		// error handler
		String errorMessage = null;		
		String forward = Path.ERROR_PAGE;
		
		HttpSession session = request.getSession();
		
		if(session.getAttribute("user") != null) {
			errorMessage = "You have already loged in as " + ((User)session.getAttribute("user")).getLogin();
			request.setAttribute("errorMessage", errorMessage);
			log.warn("User already loged in.");
			return forward;
		}
		
		if (login == null || password == null || login.isEmpty() || password.isEmpty()) {
			errorMessage = "Login/password cannot be empty";
			request.setAttribute("errorMessage", errorMessage);
			log.error("errorMessage --> " + errorMessage);
			return forward;
		}
		
		User user = new UserDao().getUserByLogin(login);
		log.trace("Found in DB: user --> " + user);
		
		if (user == null || !password.equals(user.getPassword())) {
			errorMessage = "Cannot find user with such login/password";
			request.setAttribute("errorMessage", errorMessage);
			log.error("errorMessage --> " + errorMessage);
			return forward;
		}
		
		Role role = Role.getRole(user);
		log.trace("userRole --> " + role);
		
		session.setAttribute("user", user);
		log.trace("Set the session attribute: user --> " + user);
		
		Status status = Status.getStatus(user);
		log.trace("userStatus --> " + status);
		
		session.setAttribute("status", status.getName());
		log.trace("Set the session attribute: status --> " + status);
		
		
		
		if(role.equals(Role.ADMIN)) {
			//TODO change
			return null;
		}
		
		if(role.equals(Role.CLIENT)) {
			log.trace("User as client.");
			return Path.CLIENT_PAGE;
		}
		
		
		log.debug("Login command finished");
		return null;
	}

}

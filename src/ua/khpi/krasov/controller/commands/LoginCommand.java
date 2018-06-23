package ua.khpi.krasov.controller.commands;

import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.jstl.core.Config;

import org.apache.log4j.Logger;
import ua.khpi.krasov.controller.Path;
import ua.khpi.krasov.db.Language;
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
		ResourceBundle bundle = ResourceBundle.getBundle("resources", request.getLocale());
		
		HttpSession session = request.getSession();
		
		Config.set( session, Config.FMT_LOCALE, request.getLocale());
		
		if(session.getAttribute("user") != null) {
			errorMessage = bundle.getString("error.repeatedLogin") + " " + ((User)session.getAttribute("user")).getLogin();
			request.setAttribute("errorMessage", errorMessage);
			log.warn("User already loged in.");
			return forward;
		}
		
		if (login == null || password == null || login.isEmpty() || password.isEmpty()) {
			errorMessage = bundle.getString("error.password");
			request.setAttribute("errorMessage", errorMessage);
			log.error("errorMessage --> " + errorMessage);
			return forward;
		}
		
		User user = new UserDao().getUserByLogin(login.trim());
		log.trace("Found in DB: user --> " + user);
		
		if (user == null || !password.trim().equals(user.getPassword().trim())) {
			errorMessage = bundle.getString("error.cannotFindUser");
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
		
		Locale locale = request.getLocale();
		Language lang = Language.getLanguage(locale);
		
		if(lang.equals(Language.RU)) {
			log.trace("Language ==> " + Language.RU);
			session.setAttribute("status", status.getNameRu());
			log.trace("Set the session attribute: status --> " + status);
		}
		
		if(lang.equals(Language.EN)) {
			log.trace("Language ==> " + Language.EN);
			session.setAttribute("status", status.getName());
			log.trace("Set the session attribute: status --> " + status);
		}
		
		
		if(role.equals(Role.ADMIN)) {
			log.trace("User as admin.");
			response.sendRedirect(Path.ADMIN_REDIRECT_PAGE);
		}
		
		if(role.equals(Role.CLIENT)) {
			log.trace("User as client.");
			response.sendRedirect(Path.CLIENT_REDIRECT_PAGE);
		}
		
		log.debug("Login command finished");
		return null;
	}

}

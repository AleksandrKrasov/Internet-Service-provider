package ua.khpi.krasov.controller.commands;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import ua.khpi.krasov.controller.Path;

/**
 * Logout command class. It implements command pattern and
 * used to logout.
 * 
 * @author A.Krasov
 * @version 1.0
 * 
 */
public class LogoutCommand implements Command {
	
	private static final Logger log = Logger.getLogger(LoginCommand.class);
	
	/**
	 * Methods allows to log out from the web site.
	 */
	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		log.debug("Logout command starts");
		
		HttpSession session = request.getSession(false);
		if (session != null) {
			session.invalidate();
			log.debug("session invalidated.");
		}

		log.debug("Command finished");
		
		//PNG pattern
		response.sendRedirect(request.getServletContext().getContextPath() + Path.LOGIN_PAGE);
		return null;
	}

}

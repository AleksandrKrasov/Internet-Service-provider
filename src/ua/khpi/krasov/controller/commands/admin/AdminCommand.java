package ua.khpi.krasov.controller.commands.admin;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import ua.khpi.krasov.controller.Path;
import ua.khpi.krasov.controller.commands.Command;
import ua.khpi.krasov.controller.commands.client.ClientCommand;

/**
 * Administrator command class. It implements command pattern
 * and used to forward to the administrator page. This
 * functional is not available for a client.
 * 
 * @author A.Krasov
 * @version 1.0
 *
 */
public class AdminCommand implements Command {
	
	private static final Logger log = Logger.getLogger(ClientCommand.class);
	
	/**
	 * Methods just forwards user to administrator page.
	 */
	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		log.debug("Admin command starts");
		
		if (request.getSession().getAttribute("user") == null) {
			return Path.LOGIN_PAGE;
		}
		
		return Path.ADMIN_PAGE;
	}

}

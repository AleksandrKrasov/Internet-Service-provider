package ua.khpi.krasov.controller.commands.client;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import ua.khpi.krasov.controller.Path;
import ua.khpi.krasov.controller.commands.Command;

/**
 * Client command class. It implements command pattern
 * and used to forward to the client page. This
 * functional is not available for a administrator.
 * 
 * @author A.Krasov
 * @version 1.0
 *
 */
public class ClientCommand implements Command {
	
	private static final Logger log = Logger.getLogger(ClientCommand.class);
	
	/**
	 * Methods just forwards user to client page.
	 */
	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		log.debug("Client command starts");
		
		if (request.getSession().getAttribute("user") == null) {
			return Path.LOGIN_PAGE;
		}
		
		return Path.CLIENT_PAGE;
	}

}

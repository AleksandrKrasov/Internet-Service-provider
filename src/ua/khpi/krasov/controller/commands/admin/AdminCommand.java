package ua.khpi.krasov.controller.commands.admin;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import ua.khpi.krasov.controller.Path;
import ua.khpi.krasov.controller.commands.Command;
import ua.khpi.krasov.controller.commands.client.ClientCommand;

public class AdminCommand implements Command {
	
	private static final Logger log = Logger.getLogger(ClientCommand.class);

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		log.debug("Admin command starts");
		
		return Path.ADMIN_PAGE;
	}

}

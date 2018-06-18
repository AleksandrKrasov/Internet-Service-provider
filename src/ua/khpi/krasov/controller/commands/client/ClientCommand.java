package ua.khpi.krasov.controller.commands.client;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import ua.khpi.krasov.controller.Path;
import ua.khpi.krasov.controller.commands.Command;

public class ClientCommand implements Command {
	
	private static final Logger log = Logger.getLogger(ClientCommand.class);

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		log.debug("Client command starts");
		
		return Path.CLIENT_PAGE;
	}

}

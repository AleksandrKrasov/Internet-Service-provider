package ua.khpi.krasov.controller.commands;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;


import ua.khpi.krasov.controller.Path;
import ua.khpi.krasov.db.dao.ServiceDao;
import ua.khpi.krasov.db.entity.Service;

public class ClientServiceCommand implements Command{
	
	private static final Logger log = Logger.getLogger(ClientServiceCommand.class);


	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		
		log.trace("Client service command starts");
		
		//Getting services list
		List<Service> servicelist = new ServiceDao().getAllServices();
		log.trace("List of services from DB obtained");
		
		HttpSession session = request.getSession();
		session.setAttribute("servicelist", servicelist);
		
		return Path.CLIENT_SERVICES_PAGE;
	}

}

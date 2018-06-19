package ua.khpi.krasov.controller.commands.admin;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import ua.khpi.krasov.controller.Path;
import ua.khpi.krasov.controller.commands.Command;
import ua.khpi.krasov.db.dao.ServiceDao;
import ua.khpi.krasov.db.dao.interfaces.ServiceDaoInterface;
import ua.khpi.krasov.db.entity.Service;

public class AdminServiceCommand implements Command{

	private static final Logger log = Logger.getLogger(AdminServiceCommand.class);

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		log.trace("Admin service command starts");
		
		ServiceDaoInterface serviceDao = new ServiceDao();
		
		String serviceName = request.getParameter("serviceName");
		
		if(serviceName != null) {
			Service service = serviceDao.getServiceByName(serviceName);
			serviceDao.deleteService(service);
			log.trace("Service was deleted.");
			response.sendRedirect(Path.ADMIN_SERVICE_REDIRECT_PAGE);
		}
		
		//Getting services list
		List<Service> servicelist = serviceDao.getAllServices();
		log.trace("List of services from DB obtained");
		
		HttpSession session = request.getSession();
		session.setAttribute("servicelist", servicelist);
		
		return Path.ADMIN_SERVICE_PAGE;
	}

}

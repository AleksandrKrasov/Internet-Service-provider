package ua.khpi.krasov.controller.commands.admin;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import ua.khpi.krasov.controller.Path;
import ua.khpi.krasov.controller.commands.Command;
import ua.khpi.krasov.db.dao.ServiceDao;
import ua.khpi.krasov.db.dao.interfaces.ServiceDaoInterface;
import ua.khpi.krasov.db.entity.Service;
import ua.khpi.krasov.db.validation.ValidationUtil;

public class AddServiceCommand implements Command {
	
	private static final Logger log = Logger.getLogger(AddServiceCommand.class);

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		log.debug("Add service command starts.");
		
		String name = request.getParameter("name");
		log.trace("Param value ==> " + name);
		
		String errorMessage = null;
		String forward = Path.ERROR_PAGE;
		
		if(name != null && name.isEmpty()) {
			errorMessage = "name can not be empty.";
			log.error(errorMessage);
			request.setAttribute("errorMessage", errorMessage);
			return forward;
		}
		
		if(name != null && !name.isEmpty()) {
			ServiceDaoInterface serviceDao = new ServiceDao();
			Service service = new Service();
			service.setName(name);
			
			if(ValidationUtil.validateService(service, serviceDao)) {
				serviceDao.insertService(service);
				log.trace("New Service was added to DB");
				response.sendRedirect(Path.ADMIN_SERVICE_REDIRECT_PAGE);
			} else {
				errorMessage = "Service name is invalid.";
				log.error(errorMessage);
				request.setAttribute("errorMessage", errorMessage);
				return forward;
			}
		}
		
		return Path.ADD_SERVICE_PAGE;
	}

}

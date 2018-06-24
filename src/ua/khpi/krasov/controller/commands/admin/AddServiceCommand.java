package ua.khpi.krasov.controller.commands.admin;

import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.jstl.core.Config;

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
		
		if(request.getSession().getAttribute("user") == null)
			return Path.LOGIN_PAGE;
		
		String nameRu = request.getParameter("nameRu");
		String nameEn = request.getParameter("nameEn");
		log.trace("Param value ru ==> " + nameRu);
		log.trace("Param value en ==> " + nameEn);
		
		String errorMessage = null;
		String forward = Path.ERROR_PAGE;
		ResourceBundle bundle = ResourceBundle.getBundle("resources", (Locale) Config.get(request.getSession(), Config.FMT_LOCALE));
		
		if(nameRu != null && nameRu.isEmpty() || nameEn != null && nameEn.isEmpty()) {
			errorMessage = bundle.getString("error.emptyFields");
			log.error("Any fields can not be empty.");
			request.setAttribute("errorMessage", errorMessage);
			return forward;
		}
		
		if(nameRu != null && !nameRu.isEmpty() && nameEn != null && !nameEn.isEmpty()) {		
			ServiceDaoInterface serviceDao = new ServiceDao();
			Service service = new Service();
			service.setName(nameEn);
			service.setNameRu(nameRu);
			
			if(ValidationUtil.validateService(service, serviceDao)) {
				serviceDao.insertService(service);
				log.trace("New Service was added to DB");
				response.sendRedirect(Path.ADMIN_SERVICE_REDIRECT_PAGE);
			} else {
				errorMessage = bundle.getString("error.service.wrongName");
				log.error("Service name is invalid.");
				request.setAttribute("errorMessage", errorMessage);
				return forward;
			}
		}
		
		return Path.ADD_SERVICE_PAGE;
	}

}

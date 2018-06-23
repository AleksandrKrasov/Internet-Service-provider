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

public class ServiceRanameCommand implements Command {

	private static final Logger log = Logger.getLogger(ServiceRanameCommand.class);

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		log.debug("Service rename command starts.");

		String serviceName = request.getParameter("serviceName");
		System.out.println(serviceName);

		//HttpSession session = request.getSession();

		String errorMessage = null;
		String forward = Path.ERROR_PAGE;
		ResourceBundle bundle = ResourceBundle.getBundle("resources",
				(Locale) Config.get(request.getSession(), Config.FMT_LOCALE));

		String newNameEn = request.getParameter("newNameEn");
		String newNameRu = request.getParameter("newNameRu");

		if (newNameEn != null || newNameRu != null) {

			if (newNameEn.isEmpty() || newNameRu.isEmpty()) {
				errorMessage = bundle.getString("error.emptyFields");
				log.error("Emty fields");
				request.setAttribute("errorMessage", errorMessage);
				return forward;

			}

			log.trace("reqest param of new name en ==> " + newNameEn);
			log.trace("reqest param of new name ru ==> " + newNameRu);

			ServiceDaoInterface serviceDao = new ServiceDao();

			serviceName = request.getParameter("serviceName");
			log.trace("Value of param ==> " + serviceName);

			if (serviceName != null) {

				Service service = serviceDao.getServiceByName(serviceName);

				String prevNameRu = service.getNameRu();
				String prevName = service.getName();

				log.trace("Serivce obtained from DB ==> " + service);

				service.setName(newNameEn);
				service.setNameRu(newNameRu);

				log.trace("New service name en ==> " + service.getName());
				log.trace("New service name ru ==> " + service.getNameRu());

				if (ValidationUtil.validateService(service, serviceDao)) {
					serviceDao.updateService(service);
					serviceDao.updateNameRu(service);
					log.trace("Service name was updated in DB.");
				} else {
					service.setName(prevName);
					service.setNameRu(prevNameRu);
					log.trace("Service name was NOT updated in DB.");
					errorMessage = bundle.getString("service.wrongName");
					log.error("invalid name");
					request.setAttribute("errorMessage", errorMessage);
					return forward;
				}

				response.sendRedirect(Path.ADMIN_SERVICE_REDIRECT_PAGE);
			}
		}

		return Path.SERVICE_RENAME_PAGE;
	}

}

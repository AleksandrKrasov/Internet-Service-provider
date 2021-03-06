package ua.khpi.krasov.controller.commands.admin;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.jstl.core.Config;
import org.apache.log4j.Logger;
import ua.khpi.krasov.controller.Path;
import ua.khpi.krasov.controller.commands.Command;
import ua.khpi.krasov.db.Language;
import ua.khpi.krasov.db.dao.ServiceDao;
import ua.khpi.krasov.db.dao.ServiceTariffsDao;
import ua.khpi.krasov.db.dao.TariffDao;
import ua.khpi.krasov.db.dao.interfaces.TariffDaoInterface;
import ua.khpi.krasov.db.entity.Service;
import ua.khpi.krasov.db.entity.Tariff;

/**
 * Add service command class. It implements command pattern
 * and used to to show all tariffs and change them.
 * 
 * @author A.Krasov
 * @version 1.0
 *
 */
public class AdminTariffCommand implements Command {
	
	private static final Logger log = Logger.getLogger(AdminTariffCommand.class);
	
	/**
	 * Methods allows to get all tariffs. Thanks to this
	 * command user is able to choose what to to do with tariff:
	 * to add a new one, to rename, to delete or.
	 */
	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		log.debug("Admin tariff command starts.");
		
		if (request.getSession().getAttribute("user") == null) {
			return Path.LOGIN_PAGE;
		}
		
		String tariffNameDelete = request.getParameter("tariffNameDelete");
		String serviceId = request.getParameter("serviceId");
		
		TariffDaoInterface tariffDao = new TariffDao();
		
		if (tariffNameDelete != null && serviceId != null) {
			log.trace("Delete tariff ==> " + tariffNameDelete);
			
			Tariff tariff = tariffDao.getTarrifByName(tariffNameDelete);
			log.trace("Tariff was obtained from DB ==> " + tariff);
			
			tariffDao.deleteTarrif(tariff);
			
			String param = "&serviceId=" + serviceId;
			
			response.sendRedirect(Path.ADMIN_SERVICE_TARIFFS_REDIRECT_PAGE + param);
		}
		
		log.trace("Selected service id ==> " + serviceId);

		Service service = new ServiceDao().getServiceById(Integer.valueOf(serviceId));
		log.trace("Service found in DB ==> " + service);
		
		
		List<Integer> tariffIds = new ServiceTariffsDao().getAllIdByServiceId(service);
		
		List<Tariff> tariffs = new ArrayList<>();
		List<String> tariffNames = new ArrayList<>();
		List<String> tariffDescs = new ArrayList<>();
		
		for (int id : tariffIds) {
			tariffs.add(tariffDao.getTariffById(id));
		}
		log.trace("Tariff list obtained, amount ==> " + tariffs.size());
		
		Locale locale = (Locale) Config.get(request.getSession(), Config.FMT_LOCALE);
		Language lang = Language.getLanguage(locale);
		
		if (lang.equals(Language.RU)) {
			for (Tariff tariff : tariffs) {
				tariffNames.add(tariff.getNameRu());
				tariffDescs.add(tariff.getDescriptionRu());
			}
		}
		
		if (lang.equals(Language.EN)) {
			for (Tariff tariff : tariffs) {
				tariffNames.add(tariff.getName());
				tariffDescs.add(tariff.getDescription());
			}
		}
	
		request.setAttribute("tariffNames", tariffNames);
		request.setAttribute("tariffDescs", tariffDescs);
		request.setAttribute("tariffs", tariffs);
		log.trace("Tariff list added to requsest");
		
		return Path.ADMIN_TARIFF_PAGE;
	}

}

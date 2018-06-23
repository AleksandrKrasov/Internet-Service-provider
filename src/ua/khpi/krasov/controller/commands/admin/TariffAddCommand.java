package ua.khpi.krasov.controller.commands.admin;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.jstl.core.Config;

import org.apache.log4j.Logger;

import ua.khpi.krasov.controller.Path;
import ua.khpi.krasov.controller.commands.Command;
import ua.khpi.krasov.db.DBManager;
import ua.khpi.krasov.db.dao.ServiceDao;
import ua.khpi.krasov.db.dao.ServiceTariffsDao;
import ua.khpi.krasov.db.dao.TariffDao;
import ua.khpi.krasov.db.dao.interfaces.ServiceDaoInterface;
import ua.khpi.krasov.db.dao.interfaces.ServiceTariffsDaoInterface;
import ua.khpi.krasov.db.dao.interfaces.TariffDaoInterface;
import ua.khpi.krasov.db.entity.Service;
import ua.khpi.krasov.db.entity.Tariff;
import ua.khpi.krasov.db.validation.ValidationUtil;

public class TariffAddCommand implements Command {

	private static final Logger log = Logger.getLogger(TariffAddCommand.class);

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		log.debug("Tariff add command starts.");

		String name = request.getParameter("name");
		String price = request.getParameter("price");
		String description = request.getParameter("description");
		String serviceId = request.getParameter("serviceId");
		
		String nameRu = request.getParameter("nameRu");
		String descriptionRu = request.getParameter("descriptionRu");

		if (name != null && !name.isEmpty() && price != null && !price.isEmpty() && description != null
				&& !description.isEmpty() && serviceId != null && !serviceId.isEmpty() &&
				nameRu != null && !nameRu.isEmpty() && descriptionRu!= null && !descriptionRu.isEmpty()) {
			log.trace("Name value ==> " + name);
			log.trace("Price value ==> " + price);
			log.trace("Description value ==> " + description);
			log.trace("ServiceId value ==> " + serviceId);
			log.trace("Name value ru ==> " + nameRu);
			log.trace("Description value ru ==> " + descriptionRu);

			int priceInt = 0;

			String errorMessage = null;
			String forward = Path.ERROR_PAGE;
			ResourceBundle bundle = ResourceBundle.getBundle("resources", (Locale) Config.get(request.getSession(), Config.FMT_LOCALE));

			try {
				priceInt = Integer.parseInt(price);
			} catch (NumberFormatException e) {
				errorMessage = bundle.getString("error.tariff.wrongPrice");
				log.error("Can not parse string price to int", e);
				request.setAttribute("errorMessage", errorMessage);
				return forward;
			}

			Tariff tariff = new Tariff();
			tariff.setName(name);
			tariff.setPrice(priceInt);
			tariff.setDescription(description);
			tariff.setNameRu(nameRu);
			tariff.setDescriptionRu(descriptionRu);

			if (ValidationUtil.validateTariff(tariff)) {

				ServiceTariffsDaoInterface serTarDao = new ServiceTariffsDao();
				ServiceDaoInterface serviceDao = new ServiceDao();
				TariffDaoInterface tariffDao = new TariffDao();

				try {
					Connection con = DBManager.getInstance().getConnection();
					con.setAutoCommit(false);

					tariffDao.insertTariff(tariff);
					log.trace("Tariff added to DB");
						

					tariff = tariffDao.getTarrifByName(tariff.getName());

					Service service = serviceDao.getServiceById(Integer.valueOf(serviceId));

					if(serTarDao.insertTariffForService(tariff, service)) {
						log.trace("tariff (" + tariff.getName() + ") added to service (" + service.getName() + ")");
					} else {
						con.rollback();
						log.warn("Tariff was not added to service, changes rollback");
					}
					
					con.commit();
					con.setAutoCommit(true);
					con.close();
					
					String param = "&serviceId=" + serviceId;
					
					response.sendRedirect(Path.ADMIN_SERVICE_TARIFFS_REDIRECT_PAGE + param);
				} catch (SQLException e) {
					log.error(e);
				}

			} else {
				errorMessage = bundle.getString("error.tariff.wrongTariff");
				log.error("invalid tariff");
				request.setAttribute("errorMessage", errorMessage);
				return forward;
			}

			Service service = new ServiceDao().getServiceById(Integer.valueOf(serviceId));
			log.trace("Service obtained from DB ==> " + service);

		}

		return Path.ADD_TATIFF_PAGE;
	}

}

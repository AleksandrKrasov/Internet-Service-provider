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
import ua.khpi.krasov.db.dao.TariffDao;
import ua.khpi.krasov.db.dao.interfaces.TariffDaoInterface;
import ua.khpi.krasov.db.entity.Tariff;
import ua.khpi.krasov.db.validation.ValidationUtil;

public class ChangeTariffCommand implements Command {

	private static final Logger log = Logger.getLogger(ChangeTariffCommand.class);

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		log.debug("Change tariff command starts.");
		
		if(request.getSession().getAttribute("user") == null)
			return Path.LOGIN_PAGE;

		String tariffName = request.getParameter("tariffName");
		log.trace("Param value ==> " + tariffName);

		TariffDaoInterface tariffDao = new TariffDao();

		Tariff tariff = tariffDao.getTarrifByName(tariffName);
		log.trace("Tariff found in DB ==> " + tariff);

		request.setAttribute("tariff", tariff);
		log.trace("Tariff added to request.");

		String name = request.getParameter("name");
		String price = request.getParameter("price");
		String description = request.getParameter("description");
		String nameRu = request.getParameter("nameRu");
		String descriptionRu = request.getParameter("descriptionRu");

		Tariff newTariff = new Tariff();

		if (tariff != null) {
			log.debug("Creation new tariff.");
			newTariff.setId(tariff.getId());
			newTariff.setPrice(tariff.getPrice());
			newTariff.setDescription(tariff.getDescription());
			newTariff.setName(tariff.getName());
			newTariff.setDescriptionRu(tariff.getDescriptionRu());
			newTariff.setNameRu(tariff.getNameRu());
		}

		String serviceId = request.getParameter("serviceId");
		log.trace("Param value of serviceId ==> " + serviceId);

		String redirect = Path.ADMIN_SERVICE_TARIFFS_REDIRECT_PAGE + "&serviceId=" + serviceId;

		boolean changed = false;

		String errorMessage = null;
		String forward = Path.ERROR_PAGE;
		ResourceBundle bundle = ResourceBundle.getBundle("resources", (Locale) Config.get(request.getSession(), Config.FMT_LOCALE));

		Connection con = null;

		try {
			con = DBManager.getInstance().getConnection();
			con.setAutoCommit(false);

			if (name != null && !name.isEmpty()) {
				newTariff.setName(name);
				if (ValidationUtil.validateTariffName(newTariff)) {
					log.trace("Tariff name was updeted in DB.");
					changed = true;
				} else {
					changed = false;
					con.rollback();
					errorMessage = bundle.getString("tariff.wrongName");
					log.error("invalid name");
					request.setAttribute("errorMessage", errorMessage);
					return forward;
				}
			}
			
			if (nameRu != null && !nameRu.isEmpty()) {
				newTariff.setNameRu(nameRu);
				if (ValidationUtil.validateTariffNameRu(newTariff)) {
					log.trace("Tariff name was updeted in DB.");
					changed = true;
				} else {
					changed = false;
					con.rollback();
					errorMessage = bundle.getString("tariff.wrongName");
					log.error("invalid name");
					request.setAttribute("errorMessage", errorMessage);
					return forward;
				}
			}

			if (price != null && !price.isEmpty()) {
				int priceInt = 0;

				try {
					priceInt = Integer.parseInt(price);
					newTariff.setPrice(priceInt);
					if (ValidationUtil.validateTariffPrice(newTariff)) {
						log.trace("Tariff price was updeted in DB.");
						changed = true;
					} else {
						changed = false;
						con.rollback();
						errorMessage = bundle.getString("tariff.wrongPrice");
						log.error("incorrect price");
						request.setAttribute("errorMessage", errorMessage);
						return forward;
					}
				} catch (NumberFormatException e) {
					changed = false;
					con.rollback();
					errorMessage = bundle.getString("tariff.wrongPrice");
					log.error("Can not parse string price to int", e);
					request.setAttribute("errorMessage", errorMessage);
					return forward;
				}
			}
			
			if (description != null && !description.isEmpty()) {
				newTariff.setDescription(description);
				if (ValidationUtil.validateTariffDescription(newTariff)) {
					log.trace("Tariff description was updeted in DB.");
					changed = true;
				} else {
					changed = false;
					con.rollback();
					errorMessage = bundle.getString("tariff.wrongDescription");
					log.error("invalid description");
					request.setAttribute("errorMessage", errorMessage);
					return forward;
				}
			}
			
			if (descriptionRu != null && !descriptionRu.isEmpty()) {
				newTariff.setDescriptionRu(descriptionRu);
				if (ValidationUtil.validateTariffDescription(newTariff)) {
					log.trace("Tariff description was updeted in DB.");
					changed = true;
				} else {
					changed = false;
					con.rollback();
					errorMessage = bundle.getString("tariff.wrongDescription");
					log.error("invalid description");
					request.setAttribute("errorMessage", errorMessage);
					return forward;
				}
			}
			
			if (changed) {
				tariffDao.updateDescription(newTariff);
				tariffDao.updatePrice(newTariff);
				tariffDao.updateName(newTariff);
				tariffDao.updateNameRu(newTariff);
				tariffDao.updateDescriptionRu(newTariff);
				
				log.trace("Changes saved");
				con.commit();
				response.sendRedirect(redirect);
			}
		} catch (SQLException e1) {
			log.error(e1);
		} finally {
			if(con != null) {
				try {
					log.debug("Connection closed.");
					con.setAutoCommit(true);
					con.close();
				} catch (SQLException e) {
					log.error(e);
				}
			}
		}

		return Path.CHANGE_TATIFF_PAGE;
	}

}

package ua.khpi.krasov.controller.commands;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.jstl.core.Config;
import org.apache.log4j.Logger;
import ua.khpi.krasov.controller.Path;
import ua.khpi.krasov.db.DbManager;
import ua.khpi.krasov.db.Language;
import ua.khpi.krasov.db.Status;
import ua.khpi.krasov.db.dao.UserDao;
import ua.khpi.krasov.db.dao.interfaces.UserDaoInterface;
import ua.khpi.krasov.db.entity.User;
import ua.khpi.krasov.db.validation.ValidationUtil;

/**
 * Settings command class. It implements command pattern and
 * used for login
 * 
 * @author A.Krasov
 * @version 1.0
 *
 */
public class SettingsCommand implements Command {

	private static final Logger log = Logger.getLogger(SettingsCommand.class);
	
	/**
	 * Methods allows to to tune the system. User can
	 * change login, password first and last name and 
	 * choose the appropriate language.
	 */
	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		log.debug("Settings command starts.");
		
		if (request.getSession().getAttribute("user") == null) {
			return Path.LOGIN_PAGE;
		}

		if (request.getParameter("submit") != null) {

			UserDaoInterface userDao = new UserDao();

			HttpSession session = request.getSession();

			User user = (User) session.getAttribute("user");
			
			Connection conn = null;
			
			try {
				conn = DbManager.getInstance().getConnection();
				conn.setAutoCommit(false);

				final String login = request.getParameter("login");
				final String password = request.getParameter("password");
				final String repeatPassword = request.getParameter("repeatPassword");
				final String firstName = request.getParameter("firstName");
				final String LastName = request.getParameter("lastName");
				final String language = request.getParameter("language");
				
				String errorMessage = null;		
				String forward = Path.ERROR_PAGE;
				
				Locale locale = (Locale) Config.get(request.getSession(), Config.FMT_LOCALE);
				if (locale == null) {
					locale = new Locale("ru");
					Config.set(session, Config.FMT_LOCALE, locale);
				}
				
				ResourceBundle bundle = ResourceBundle.getBundle("resources", 
						(Locale) Config.get(request.getSession(), Config.FMT_LOCALE));
				
				String userLogin = user.getLogin();
				String userPassword = user.getPassword();
				String userFirstName = user.getFirstName();
				String userLastName = user.getLastName();
				
				if (language != null) {
					locale = new Locale(language);
					Config.set(session, Config.FMT_LOCALE, locale);
					
					Language lang = Language.getLanguage(locale);
					
					Status status = Status.getStatus(user);
					log.trace("userStatus --> " + status);
					
					if (lang.equals(Language.RU)) {
						log.trace("Language ==> " + Language.RU);
						session.setAttribute("status", status.getNameRu());
						log.trace("Set the session attribute: status --> " + status);
					}
					
					if (lang.equals(Language.EN)) {
						log.trace("Language ==> " + Language.EN);
						session.setAttribute("status", status.getName());
						log.trace("Set the session attribute: status --> " + status);
					}
					log.trace("Language changed to ==> " + language);
				}

				if (login != null && !login.isEmpty()) {
					user.setLogin(login);
					if (ValidationUtil.validateUser(user, userDao)) {
						userDao.updateLogin(user);
						log.trace("Login was successfuly updated in DB.");
					} else {
						errorMessage = bundle.getString("error.settings.wronLogin");
						request.setAttribute("errorMessage", errorMessage);
						log.warn(errorMessage);
						conn.rollback();
						log.trace("Rollback changes in DB.");
						user.setLogin(userLogin);
						return forward;
					}
				}
				
				if (password != null && !password.isEmpty() 
						&& repeatPassword != null && !repeatPassword.isEmpty()) {
					log.debug("Changing password starts.");
					user.setPassword(password);
					if (ValidationUtil.validateUser(user) && password.equals(repeatPassword)) {
						userDao.updatePassword(user);
						log.trace("Password was successfuly updated in DB.");
					} else {
						errorMessage = bundle.getString("error.settings.password");
						String passwordMessage = bundle.getString("error.message.password");
						request.setAttribute("errorMessage", errorMessage);
						request.setAttribute("passwordMessage", passwordMessage);
						log.warn("Password is incorrect");
						conn.rollback();
						log.trace("Rollback changes in DB.");
						user.setPassword(userPassword);
						return forward;
					}
				}

				if (firstName != null && !firstName.isEmpty()) {
					user.setFirstName(firstName);
					if (ValidationUtil.validateUser(user)) {
						userDao.updateFirstName(user);
						log.trace("First name was successfuly updated in DB.");
					} else {
						errorMessage = bundle.getString("error.settings.firstName");
						request.setAttribute("errorMessage", errorMessage);
						log.warn("First name is incorrect");
						conn.rollback();
						log.trace("Rollback changes in DB.");
						user.setFirstName(userFirstName);
						return forward;
					}
				}

				if (LastName != null && !LastName.isEmpty()) {
					user.setLastName(LastName);
					if (ValidationUtil.validateUser(user)) {
						userDao.updateLastName(user);
						log.trace("Last name was successfuly updated in DB.");
					} else {
						errorMessage = bundle.getString("error.settings.lastName");
						request.setAttribute("errorMessage", errorMessage);
						log.warn("Last name is incorrect");
						conn.rollback();
						log.trace("Rollback changes in DB.");
						user.setLastName(userLastName);
						return forward;
					}
				}
				
				conn.commit();
				log.trace("Changes are commited.");

			} catch (SQLException e) {
				log.error(e);
			} finally {
				try {
					conn.setAutoCommit(true);
					conn.close();
					log.debug("Connection closed.");
				} catch (SQLException e) {
					log.error(e);
				}
			}

			log.trace("Redirecting to ==> " + Path.SETTING_REDIRECT_PAGE);
			response.sendRedirect(Path.SETTING_REDIRECT_PAGE);

			return null;
		}

		return Path.SETTING_PAGE;
	}

}

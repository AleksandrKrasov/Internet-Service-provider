package ua.khpi.krasov.controller.commands;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import ua.khpi.krasov.controller.Path;
import ua.khpi.krasov.db.DBManager;
import ua.khpi.krasov.db.dao.UserDao;
import ua.khpi.krasov.db.dao.interfaces.UserDaoInterface;
import ua.khpi.krasov.db.entity.User;
import ua.khpi.krasov.db.validation.ValidationUtil;

public class SettingsCommand implements Command {

	private static final Logger log = Logger.getLogger(SettingsCommand.class);

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		log.debug("Settings command starts.");

		if (request.getParameter("submit") != null) {

			UserDaoInterface userDao = new UserDao();

			HttpSession session = request.getSession();

			User user = (User) session.getAttribute("user");

			try {
				Connection conn = DBManager.getInstance().getConnection();
				conn.setAutoCommit(false);

				String login = request.getParameter("login");
				String password = request.getParameter("password");
				String repeatPassword = request.getParameter("repeatPassword");
				String firstName = request.getParameter("firstName");
				String LastName = request.getParameter("lastName");
				
				String errorMessage = null;		
				String forward = Path.ERROR_PAGE;
				
				String userLogin = user.getLogin();
				String userPassword = user.getPassword();
				String userFirstName = user.getFirstName();
				String userLastName = user.getLastName();

				if (login != null && !login.isEmpty()) {
					user.setLogin(login);
					if (ValidationUtil.validateUser(user, userDao)) {
						userDao.updateLogin(user);
						log.trace("Login was successfuly updated in DB.");
					} else {
						errorMessage = "Login is incorrect/such login already exists.";
						request.setAttribute("errorMessage", errorMessage);
						log.warn(errorMessage);
						conn.rollback();
						log.trace("Rollback changes in DB.");
						user.setLogin(userLogin);
						return forward;
					}
				}
				
				if (password != null && !password.isEmpty() && repeatPassword != null && !repeatPassword.isEmpty()
						&& password.equals(repeatPassword)) {
					user.setPassword(password);
					if (ValidationUtil.validateUser(user)) {
						userDao.updatePassword(user);
						log.trace("Password was successfuly updated in DB.");
					} else {
						errorMessage = "Password is incorrect";
						request.setAttribute("errorMessage", errorMessage);
						log.warn(errorMessage);
						conn.rollback();
						log.trace("Rollback changes in DB.");
						user.setPassword(userPassword);
						return forward;
					}
				}

				if (firstName != null && !firstName.isEmpty()) {
					user.setFirstName(firstName);
					if (ValidationUtil.validateUser(user)) {
						userDao.updatePassword(user);
						log.trace("First name was successfuly updated in DB.");
					} else {
						errorMessage = "First name is incorrect";
						request.setAttribute("errorMessage", errorMessage);
						log.warn(errorMessage);
						conn.rollback();
						log.trace("Rollback changes in DB.");
						user.setFirstName(userFirstName);
						return forward;
					}
				}

				if (LastName != null && !LastName.isEmpty()) {
					user.setFirstName(LastName);
					if (ValidationUtil.validateUser(user)) {
						userDao.updatePassword(user);
						log.trace("Last name was successfuly updated in DB.");
					} else {
						errorMessage = "Last name is incorrect";
						request.setAttribute("errorMessage", errorMessage);
						log.warn(errorMessage);
						conn.rollback();
						log.trace("Rollback changes in DB.");
						user.setLastName(userLastName);
						return forward;
					}
				}
				
				conn.commit();
				conn.close();
				log.trace("Changes are commited.");

			} catch (SQLException e) {
				log.error(e);
			}

			log.trace("Redirecting to ==> " + Path.SETTING_REDIRECT_PAGE);
			response.sendRedirect(Path.SETTING_REDIRECT_PAGE);

			return null;
		}

		return Path.SETTING_PAGE;
	}

}

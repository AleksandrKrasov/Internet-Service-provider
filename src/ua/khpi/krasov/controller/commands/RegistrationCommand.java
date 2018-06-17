package ua.khpi.krasov.controller.commands;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;

import ua.khpi.krasov.controller.Path;
import ua.khpi.krasov.db.dao.UserDao;
import ua.khpi.krasov.db.dao.interfaces.UserDaoInterface;
import ua.khpi.krasov.db.entity.User;
import ua.khpi.krasov.db.validation.ValidationUtil;

public class RegistrationCommand implements Command {
	
	private static final Logger log = Logger.getLogger(RegistrationCommand.class);

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) 
			throws IOException, ServletException {
		
		log.debug("Registration command starts");
		
		String login = request.getParameter("login");
		String password = request.getParameter("password");
		String repeatPassword = request.getParameter("repeatPassword");
		String firstName = request.getParameter("firstName");
		String lastName = request.getParameter("lastName");
		
		UserDaoInterface userDao = new UserDao();
		User user = userDao.getUserByLogin(login);
		
		// error handler
		String errorMessage = null;		
		String forward = Path.ERROR_PAGE;
		
		if(login == null || password == null || repeatPassword == null || 
				firstName == null || lastName == null || login.isEmpty() ||
				password.isEmpty() || firstName.isEmpty() || lastName.isEmpty()) {
			errorMessage = "Any fields can not be empty.";
			request.setAttribute("errorMessage", errorMessage);
			log.warn("Any fields can not be empty.");
			return forward;
		}
		
		if(user != null) {
			errorMessage = "User with such login already exists.";
			request.setAttribute("errorMessage", errorMessage);
			log.warn("User with such login already exists.");
			return forward;
		}
		
		if(!password.equals(repeatPassword)) {
			errorMessage = "Password does not equals repeated password.";
			request.setAttribute("errorMessage", errorMessage);
			log.warn("Password does not equals repeated password.");
			return forward;
		}
		
		user = new User();
		user.setLogin(login);
		user.setPassword(password);
		user.setFirstName(firstName);
		user.setLastName(lastName);
		user.setRoleId(1);
		
		if(!ValidationUtil.validateUser(user, userDao)) {
			errorMessage = "Invalid user.";
			request.setAttribute("errorMessage", errorMessage);
			log.warn("Invalid user.");
			return Path.ERROR_PAGE;
		}
		
		if(userDao.insertUser(user)) {
			log.trace("User was successfully added.");
			
			//PNG pattern
			response.sendRedirect(request.getServletContext().getContextPath() + Path.LOGIN_PAGE);
			return Path.LOGIN_PAGE;
		} else {
			log.warn("Can not add user.");
			return Path.REGISTRATION_PAGE;
		}
		
	}

}

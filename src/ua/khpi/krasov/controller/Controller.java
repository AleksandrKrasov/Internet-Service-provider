package ua.khpi.krasov.controller;

import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;
import ua.khpi.krasov.controller.commands.Command;
import ua.khpi.krasov.controller.commands.CommandContainer;

/**
 * Servlet implementation class Controller. Class
 * implements command pattern. It is a main class
 * in web logic. It serves as a dispatcher. Class
 * takes a command and forward to the result page.
 * 
 * @author A.Krasov
 * @version 1.0
 */
@WebServlet("/Controller")
public class Controller extends HttpServlet {

	private static final long serialVersionUID = -5837834088711423577L;

	private static final Logger log = Logger.getLogger(Controller.class);

	/** 
	 * Method only calls another method from this class.
	 * @see Controller#execute(HttpServletRequest, HttpServletResponse)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		execute(request, response);
	}

	/** 
	 * Method only calls another method from this class.
	 * @see Controller#execute(HttpServletRequest, HttpServletResponse)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		execute(request, response);
	}
	
	/**
	 * This method get command name from the request and
	 * takes a command from {@link CommandContainer#get(String)}
	 * which returns the URL. After taking it, method forwards to
	 * this URL.
	 * 
	 * @param request HttpServletRequest
	 * @param response HttpServletResponse
	 * @throws ServletException from HttpServletRequest
	 * @throws IOException from CommandContainer
	 * @see CommandContainer
	 */
	private void execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		log.debug("Controller starts");
		
		// extract command name from the request
		String commandName = request.getParameter("command");
		log.trace("Request parameter: command --> " + commandName);

		// obtain command object by its name
		Command command = CommandContainer.get(commandName);
		log.trace("Obtained command --> " + command);

		// execute command and get forward address
		String forward = command.execute(request, response);
		log.trace("Forward address --> " + forward);

		log.debug("Controller finished, now go to forward address --> " + forward);

		// if the forward address is not null go to the address
		if (forward != null && !response.isCommitted()) {
			RequestDispatcher disp = request.getRequestDispatcher(forward);
			disp.forward(request, response);
		}
	}

}

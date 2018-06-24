package ua.khpi.krasov.controller.commands;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Command interface for command pattern.
 * 
 * @author A.Krasov
 * @version 1.0
 */
public interface Command {
	
	String execute(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException;
}

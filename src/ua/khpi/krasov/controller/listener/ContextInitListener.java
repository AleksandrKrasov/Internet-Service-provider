package ua.khpi.krasov.controller.listener;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

/**
 * Context listener. It allows to initialize i18n subsystem
 * and Initializes log4j framework. The log4j properties file
 * stored in WEB-INF/log4j.properties.
 * 
 * @author A.Krasov
 *
 */
public class ContextInitListener implements ServletContextListener {
	
	private static final Logger log = Logger.getLogger(ContextInitListener.class);

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		ServletContext servletContext = sce.getServletContext();
		initLog4J(servletContext);
		initI18N(servletContext);
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		log("Servlet context destruction starts");
		// do nothing
		log("Servlet context destruction finished");
	}
	
	private void log(String msg) {
		System.out.println("[ContextListener] " + msg);
	}
	
	/**
	 * Initializes i18n subsystem.
	 */
	private void initI18N(ServletContext servletContext) {
		log.debug("I18N subsystem initialization started");
		
		String localesValue = servletContext.getInitParameter("locales");
		if (localesValue == null || localesValue.isEmpty()) {
			log.warn("'locales' init parameter is empty, the default encoding will be used");
		} else {
			List<String> locales = new ArrayList<String>();
			StringTokenizer st = new StringTokenizer(localesValue);
			while (st.hasMoreTokens()) {
				String localeName = st.nextToken();
				locales.add(localeName);
			}							
			
			log.debug("Application attribute set: locales --> " + locales);
			servletContext.setAttribute("locales", locales);
		}		
		
		log.debug("I18N subsystem initialization finished");
	}
	
	/**
	 * Initializes log4j framework.
	 * 
	 * @param servletContext to get add info
	 */
	private void initLog4J(ServletContext servletContext) {
		log("Log4J initialization started");
		try {
			PropertyConfigurator.configure(servletContext.getRealPath(
							"WEB-INF/log4j.properties"));
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		
		log("Log4J initialization finished");
	}
	
}

package ua.khpi.krasov.controller.filter;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import org.apache.log4j.Logger;

/**
 * Encoding filter.
 * 
 * @author A.Krasov
 * 
 */
public class EncodingFilter implements Filter {

	private static final Logger log = Logger.getLogger(EncodingFilter.class);

	private String encoding;

	/**
	 * Method is called when filter destruction starts.
	 */
	public void destroy() {
		log.debug("Filter destruction starts");
		// do nothing
		log.debug("Filter destruction finished");
	}
	
	/**
	 * DoFilter method. Method gets encoding from encoding if it needs.
	 */
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		log.debug("Filter starts");
		
		HttpServletRequest httpRequest = (HttpServletRequest)request;
		
		log.trace("Request uri --> " + httpRequest.getRequestURI());
		
		String requestEncoding = request.getCharacterEncoding();
		if (requestEncoding == null) {
			log.trace("Request encoding = null, set encoding --> " + encoding);
			request.setCharacterEncoding(encoding);
		}
		
		log.debug("Filter finished");		
		chain.doFilter(request, response);
	}
	
	/**
	 * Init method set encoding filed from initial parameter file.
	 */
	public void init(FilterConfig filterConfig) throws ServletException {
		log.debug("Filter initialization starts");
		encoding = filterConfig.getInitParameter("encoding");
		log.trace("Encoding from web.xml --> " + encoding);
		log.debug("Filter initialization finished");
	}

}
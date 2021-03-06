package ua.khpi.krasov.controller.pdf;

import com.itextpdf.text.DocumentException;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.jstl.core.Config;
import org.apache.log4j.Logger;

/**
 * Servlet implementation class PdfDownloader. Servlet
 * allows do download PDF file with tariffs description.
 * 
 * @author A.Krasov
 */
@WebServlet("/PdfDownloader")
public class PdfDownloader extends HttpServlet {
	
	private static final long serialVersionUID = -1085381210242513318L;
	
	private static final Logger log = Logger.getLogger(PdfDownloader.class);

	/**
	 * Method only calls another method from this class.
	 * @see PdfDownloader#execute(HttpServletRequest, HttpServletResponse)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException {
		execute(request, response);
	}

	/**
	 * Method only calls another method from this class.
	 * @see PdfDownloader#execute(HttpServletRequest, HttpServletResponse)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException {
		execute(request, response);
	}
	
	/**
	 * This method allows to download PDF file with tariffs 
	 * description using PdfFile class. Method get bytes from
	 * output file and write them in output stream of ttpServletResponse.
	 * 
	 * @param request HttpServletRequest
	 * @param response HttpServletResponse
	 * @see PdfFile#getPdfFile()
	 */
	private void execute(HttpServletRequest request, HttpServletResponse response) {
		log.debug("Pdf Downloader servlet starts.");
		
		String fileName = "tariff planes.pdf";
		log.trace("PDF file name ==> " + fileName);
		
		String path = getServletContext().getRealPath("/") + "pdf/" + fileName;
		log.trace("PDF file path ==> " + path);
		
		File file = new File(path);
        
		ServletOutputStream outputStream = null;
		BufferedInputStream inputStream = null;
	    
		Locale locale = (Locale) Config.get(request.getSession(), Config.FMT_LOCALE);
		ResourceBundle bundle = ResourceBundle.getBundle("resources", locale);
	 	
		try {
			new PdfFile(path, bundle).getPdfFile();
			outputStream = response.getOutputStream();
			response.setHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\"");
			log.trace("New header was set for response");
			response.setContentLength((int) file.length());
			inputStream = new BufferedInputStream(new FileInputStream(file));
			int readBytes = 0;
			while ((readBytes = inputStream.read()) != -1) {
				outputStream.write(readBytes);
			}
			log.trace("Bytes were added to response");
		} catch (IOException | DocumentException e) {
			log.trace(e);
		} finally {
			try {
				outputStream.flush();
				outputStream.close();
				inputStream.close();
				log.trace("Pdf Downloader servlet finishes.");
			} catch (IOException e) {
				log.trace(e);
			}
		}
	}

}

package ua.khpi.krasov.controller.pdf;


import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chapter;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Section;
import com.itextpdf.text.pdf.CMYKColor;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import ua.khpi.krasov.db.dao.ServiceDao;
import ua.khpi.krasov.db.dao.ServiceTariffsDao;
import ua.khpi.krasov.db.dao.TariffDao;
import ua.khpi.krasov.db.dao.interfaces.ServiceTariffsDaoInterface;
import ua.khpi.krasov.db.dao.interfaces.TariffDaoInterface;
import ua.khpi.krasov.db.entity.Service;
import ua.khpi.krasov.db.entity.Tariff;

public class PdfFile {
	
	private static final Logger log = Logger.getLogger(PdfFile.class);
	
	private String root;

	public PdfFile(String root) {
		this.root = root;
		log.trace("Value for the field root ==> " + root);
	}

	public void getPdfFile() throws DocumentException, MalformedURLException, IOException {
		log.debug("Creation pfd file starts.");
		
		List<Service> services = new ServiceDao().getAllServices();
		log.trace("Services were obtained from DB.");
		
		ServiceTariffsDaoInterface serviceTariffsDao = new ServiceTariffsDao();
		
		TariffDaoInterface tariffDao = new TariffDao();
		
		// Instantiation of document object
		Document document = new Document(PageSize.A4, 50, 50, 50, 50);

		// Creation of PdfWriter object
		PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(root));

		document.open();
		log.trace("Document opend.");
		
		Paragraph title = new Paragraph("Internt Service Provider",
				FontFactory.getFont(FontFactory.HELVETICA, 18, Font.BOLDITALIC, new CMYKColor(0, 255, 255, 17)));

		Chapter chapter = new Chapter(title, 1);
		chapter.setNumberDepth(0);

		for (int i = 0; i < services.size(); i++) {
			
			List<Integer> tariffIds = serviceTariffsDao.getAllIdByServiceId(services.get(i));
			List<Tariff> tariffs = new ArrayList<>();
			
			
			for(int id : tariffIds) {
				tariffs.add(tariffDao.getTariffByID(id));
			}
			
			log.trace("Tariffs were obtained from DB, amount ==> " + tariffs.size());
			
			Paragraph title1 = new Paragraph(services.get(i).getName(),
					FontFactory.getFont(FontFactory.HELVETICA, 16, Font.BOLD, new CMYKColor(0, 255, 255, 17)));
			Section section = chapter.addSection(title1);

			// Listing 6. Creation of table object
			PdfPTable t = new PdfPTable(3);

			t.setSpacingBefore(25);
			t.setSpacingAfter(25);
			Font font = new Font();
			font.setStyle(Font.BOLD);
			font.setSize(16);
			font.setColor(BaseColor.GREEN);
			Phrase phrase1 = new Phrase("Tariff", font);
			Phrase phrase2 = new Phrase("Description", font);
			Phrase phrase3 = new Phrase("Price", font);
			PdfPCell c1 = new PdfPCell(phrase1);
			t.addCell(c1);
			PdfPCell c2 = new PdfPCell(phrase2);
			t.addCell(c2);
			PdfPCell c3 = new PdfPCell(phrase3);
			t.addCell(c3);
			
			for(Tariff tariff : tariffs) {
				t.addCell(tariff.getName());
				t.addCell(tariff.getDescription());
				t.addCell(String.valueOf(tariff.getPrice()));
			}
			section.add(t);
			
			log.trace("Tariff table were created for service ==> " + services.get(i));

		}

		document.add(chapter);
		
		document.close();
		writer.close();
		log.trace("Document closed.");
	}
	
}

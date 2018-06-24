package ua.khpi.krasov.controller.pdf;


import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import org.apache.log4j.Logger;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chapter;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Section;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import ua.khpi.krasov.controller.Path;
import ua.khpi.krasov.db.Language;
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
	
	private ResourceBundle bundle;

	public PdfFile(String root, ResourceBundle bundle) {
		this.root = root;
		this.bundle = bundle;
		log.trace("Value for the field root ==> " + root);
		log.trace("Value for the field bundle ==> " + bundle);
	}

	public void getPdfFile() throws DocumentException, MalformedURLException, IOException {
		log.debug("Creation pfd file starts.");
		
		List<Service> services = new ServiceDao().getAllServices();
		log.trace("Services were obtained from DB.");
		
		ServiceTariffsDaoInterface serviceTariffsDao = new ServiceTariffsDao();
		
		TariffDaoInterface tariffDao = new TariffDao();
		
		BaseFont bf = BaseFont.createFont(Path.FONT, BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
		Font font = new Font(bf);
		font.setColor(BaseColor.RED);
		
		// Instantiation of document object
		Document document = new Document(PageSize.A4, 50, 50, 50, 50);

		// Creation of PdfWriter object
		PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(root));

		document.open();
		writer.setLanguage("ru");
		log.trace("Document opend.");
		
		Paragraph title = new Paragraph(bundle.getString("service.pdf.name"), font);
		title.setAlignment(Element.ALIGN_CENTER);
		
		Language lang = Language.getLanguage(bundle.getLocale());
		
		
		Chapter chapter = new Chapter(title, 1);
		chapter.setNumberDepth(0);

		for (int i = 0; i < services.size(); i++) {
			font.setColor(BaseColor.RED);
			
			List<Integer> tariffIds = serviceTariffsDao.getAllIdByServiceId(services.get(i));
			List<Tariff> tariffs = new ArrayList<>();
			
			
			for(int id : tariffIds) {
				tariffs.add(tariffDao.getTariffByID(id));
			}
			
			log.trace("Tariffs were obtained from DB, amount ==> " + tariffs.size());
			
			Paragraph title1 = null;
			
			if(lang.equals(Language.EN))
				title1 = new Paragraph(services.get(i).getName(), font);
			else
				title1 = new Paragraph(services.get(i).getNameRu(), font);
			
			
			Section section = chapter.addSection(title1);

			//Creation of table object
			PdfPTable t = new PdfPTable(3);

			t.setSpacingBefore(25);
			t.setSpacingAfter(25);
			Font font1 = new Font(bf);
			font1.setStyle(Font.BOLD);
			font1.setSize(16);
			font1.setColor(BaseColor.GREEN);
			Phrase phrase1 = new Phrase(bundle.getString("tariff.tariff"), font1);
			Phrase phrase2 = new Phrase(bundle.getString("tariff.description"), font1);
			Phrase phrase3 = new Phrase(bundle.getString("tariff.price"), font1);
			PdfPCell c1 = new PdfPCell(phrase1);
			t.addCell(c1);
			PdfPCell c2 = new PdfPCell(phrase2);
			t.addCell(c2);
			PdfPCell c3 = new PdfPCell(phrase3);
			t.addCell(c3);
			
			font.setColor(BaseColor.BLACK);
			
			for(Tariff tariff : tariffs) {
				Phrase name = null;
				Phrase description = null;
				Phrase price = new Phrase(String.valueOf(tariff.getPrice()) + " " + bundle.getString("head.currency"), font);
				
				if(lang.equals(Language.EN)) {
					name = new Phrase(tariff.getName(), font);
					description = new Phrase(tariff.getDescription(), font);
				} else {
					name = new Phrase(tariff.getNameRu(), font);
					description = new Phrase(tariff.getDescriptionRu(), font);
				}
				
				t.addCell(name);
				t.addCell(description);
				t.addCell(price);
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

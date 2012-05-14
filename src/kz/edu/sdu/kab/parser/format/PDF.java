package kz.edu.sdu.kab.parser.format;

import java.io.FileInputStream;

import com.lowagie.text.pdf.PdfReader;
import com.lowagie.text.pdf.parser.PdfTextExtractor;

public class PDF {

	public String getContentText(FileInputStream fis) {
		try {
			String contentText = "";
			PdfReader pdf = new PdfReader(fis);
			PdfTextExtractor pte = new PdfTextExtractor(pdf);
			
			for(int i = 1; i <= pdf.getNumberOfPages();i++) {
				contentText += pte.getTextFromPage(i);
			}
			
			return contentText;
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}
}

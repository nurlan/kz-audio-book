package kz.edu.sdu.kab.parser.format;

import java.io.FileInputStream;

import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.extractor.WordExtractor;

public class MicrosoftWord {

	public String getContentText(FileInputStream fis) {
		try {
			HWPFDocument document = new HWPFDocument(fis);
			WordExtractor extractor = new WordExtractor(document);
			String[] fileData = extractor.getParagraphText();
			String contentText = "";
			for (int i = 0; i < fileData.length; i++) {
				if (fileData[i] != null) {
					contentText += fileData[i];
				}
			}
			
			return contentText;
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	public String getHtmlContentText(FileInputStream fis) {
		try {
			HWPFDocument document = new HWPFDocument(fis);
			WordExtractor extractor = new WordExtractor(document);
			String[] fileData = extractor.getParagraphText();
			String contentText = "";
			for (int i = 0; i < fileData.length; i++) {
				if (fileData[i] != null) {
					contentText += fileData[i] + "</br>";
				}
			}
			
			return contentText;
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}
}

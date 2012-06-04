package kz.edu.sdu.kab.parser;

import java.io.File;
import java.io.FileInputStream;

import kz.edu.sdu.kab.parser.format.MicrosoftWord;
import kz.edu.sdu.kab.parser.format.PDF;

public class Parser {

	private MicrosoftWord microsoftWord;
	private PDF pdf;

	public String getFileContent(String path) {
		try {
			File file = new File(path);
			FileInputStream fis = new FileInputStream(file.getAbsolutePath());
			
			String fileContent = "";
			if(path.endsWith(".doc") || path.endsWith(".docx")) {
				microsoftWord = new MicrosoftWord();
				fileContent = microsoftWord.getContentText(fis);
			}
			else if(path.endsWith(".pdf")) {
				pdf = new PDF();
				fileContent = pdf.getContentText(fis);
			}
			else {
				Exception ufe = new Exception("UnsupportedFileFormatException: "+path);
				ufe.printStackTrace();
			}
			
			return fileContent;
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	public String getFileHtmlContent(String path) {
		try {
			File file = new File(path);
			FileInputStream fis = new FileInputStream(file.getAbsolutePath());
			
			String fileContent = "";
			if(path.endsWith(".doc") || path.endsWith(".docx")) {
				microsoftWord = new MicrosoftWord();
				fileContent = microsoftWord.getHtmlContentText(fis);
			}
			else if(path.endsWith(".pdf")) {
				pdf = new PDF();
				fileContent = pdf.getHtmlContentText(fis);
			}
			else {
				Exception ufe = new Exception("UnsupportedFileFormatException: "+path);
				ufe.printStackTrace();
			}
			
			return fileContent;
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}
}

package kz.edu.sdu.kab.test;

import kz.edu.sdu.kab.AudioBook;
import kz.edu.sdu.kab.config.XmlAudioBookConfig;
import kz.edu.sdu.kab.parser.Parser;

public class AudioBookMain {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		XmlAudioBookConfig xml = new XmlAudioBookConfig();

		AudioBook audioBook = new AudioBook();
		String fileName = audioBook.make("Өнер айтыс секілді қазақ өзіндік негізгі","wav");
		audioBook.playWAV(fileName);
		try {
			Thread.sleep(100000l);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

//		Parser parser = new Parser();
//		System.out.println(parser.getFileContent("/Users/nurlan/Documents/SDU/Management.doc"));
//		System.out.println(parser.getFileContent("/Users/nurlan/Documents/Guidebook.pdf"));
//
//		System.out.println(xml.getAudiobooksPath());
//		System.out.println(xml.getSyllablesPath());
//		System.out.println(xml.getBashscriptPath());
	}

}

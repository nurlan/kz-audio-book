package kz.edu.sdu.kab.test;

import kz.edu.sdu.kab.AudioBook;
import kz.edu.sdu.kab.parser.Parser;

public class AudioBookMain {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		AudioBook audioBook = new AudioBook();

		//audioBook.playWAV(audioBook.makeWAV("Өнер айтыс секілді қазақ өзіндік негізгі"));
//		audioBook.playMP3("1540749980");
		
		Parser parser = new Parser();
//		System.out.println(parser.getFileContent("/Users/nurlan/Documents/SDU/Management.doc"));
		System.out.println(parser.getFileContent("/Users/nurlan/Documents/Guidebook.pdf"));
		
	}

}

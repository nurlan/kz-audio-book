package kz.edu.sdu.kab.test;

import kz.edu.sdu.kab.AudioBook;

public class AudioBookMain {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		AudioBook audioBook = new AudioBook();

		audioBook.playWAV(audioBook.makeWAV("болды"));
//		audioBook.playMP3("1540749980");
	}

}

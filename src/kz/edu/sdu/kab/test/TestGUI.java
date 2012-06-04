package kz.edu.sdu.kab.test;

import kz.edu.sdu.kab.ui.AUI;

public class TestGUI {

	public TestGUI() {
		// TODO Auto-generated constructor stub
	}
	
	public void read() {
		System.out.println("Reading...");
	}
	
	public void stop() {
		System.out.println("Stop!!!");
	}
	
	public static void main(String [] args) {
		TestGUI gui = new TestGUI();
		
//		AUI aui = new AUI(gui);
		Thread t = new Thread(new AUI(gui));
		t.start();
	}
}

package kz.edu.sdu.kab.ui;

import kz.edu.sdu.kab.test.TestGUI;
import edu.cmu.sphinx.frontend.util.Microphone;
import edu.cmu.sphinx.recognizer.Recognizer;
import edu.cmu.sphinx.result.Result;
import edu.cmu.sphinx.util.props.ConfigurationManager;


public class AUI implements Runnable {
	
	private ConfigurationManager cm;
	private Recognizer recognizer;
	
	private GUI gui;
	private TestGUI tgui;
	
	public AUI(GUI gui) {
		cm = new ConfigurationManager(AUI.class.getResource("aui.config.xml"));
        
        recognizer = (Recognizer) cm.lookup("recognizer");
        recognizer.allocate();

        Microphone microphone = (Microphone) cm.lookup("microphone");
        if (!microphone.startRecording()) {
            System.out.println("Cannot start microphone.");
            recognizer.deallocate();
            System.exit(1);
        }

        System.out.println("Say: (Tokhta | Okhy)");
        
		this.gui = gui;
	}
	
	public AUI(TestGUI tgui) {
		cm = new ConfigurationManager(AUI.class.getResource("aui.config.xml"));
        
        recognizer = (Recognizer) cm.lookup("recognizer");
        recognizer.allocate();

        Microphone microphone = (Microphone) cm.lookup("microphone");
        if (!microphone.startRecording()) {
            System.out.println("Cannot start microphone.");
            recognizer.deallocate();
            System.exit(1);
        }

        System.out.println("Say: (Tokhta | Okhy)");
        
		this.tgui = tgui;
	}
	
	public void run() {
		while (true) {
			try {
	            System.out.println("Start speaking. Press Ctrl-C to quit.\n");
	
	            Result result = recognizer.recognize();
	
	            if (result != null) {
	                String resultText = result.getBestFinalResultNoFiller();
	                System.out.println("You said: " + resultText + '\n');
	                if(resultText.equals("okhy")) {
	                	gui.play();
	                }
	                else if(resultText.equals("tokhta")) {
	                	gui.stop();
	                }
	            } else {
	                System.out.println("I can't hear what you said.\n");
	            }
	            Thread.sleep(1000l);
			}
			catch(Exception e) {
				e.printStackTrace();
			}
        }
	}
	
	public static void main(String [] args) {
//		AUI aui = new AUI();
//
//        // loop the recognition until the programm exits.
//        while (true) {
//            System.out.println("Start speaking. Press Ctrl-C to quit.\n");
//
//            Result result = aui.recognizer.recognize();
//
//            if (result != null) {
//                String resultText = result.getBestFinalResultNoFiller();
//                System.out.println("You said: " + resultText + '\n');
//            } else {
//                System.out.println("I can't hear what you said.\n");
//            }
//        }
	}
}

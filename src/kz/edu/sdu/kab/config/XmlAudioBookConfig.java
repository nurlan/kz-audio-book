package kz.edu.sdu.kab.config;

import java.io.InputStream;

import org.dom4j.Document;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;

public class XmlAudioBookConfig {

	private static String audiobooksPath;
	private static String syllablesPath;
	private static String bashscriptPath;
	
	public XmlAudioBookConfig() {
		loadConfigurations();
	}
	
	public void loadConfigurations() {
		try {
			InputStream is = this.getClass().getClassLoader().getResourceAsStream("META-INF/audioBookConfig.xml");
			SAXReader reader = new SAXReader();
	        Document document = reader.read(is);
			
			Node audiobooks = document.selectSingleNode("//audioBookConfig/audiobooks");
			Node syllables = document.selectSingleNode("//audioBookConfig/syllables");
			Node bashscript = document.selectSingleNode("//audioBookConfig/bashscript");
			
			audiobooksPath = audiobooks.getText();
			syllablesPath = syllables.getText();
			bashscriptPath = bashscript.getText();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}

	public static String getAudiobooksPath() {
		return audiobooksPath;
	}

	public static String getSyllablesPath() {
		return syllablesPath;
	}

	public static String getBashscriptPath() {
		return bashscriptPath;
	}
	
}

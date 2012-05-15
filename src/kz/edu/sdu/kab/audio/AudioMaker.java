package kz.edu.sdu.kab.audio;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.SequenceInputStream;
import java.io.Writer;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;

import kz.edu.sdu.kab.config.XmlAudioBookConfig;

public class AudioMaker {

	private XmlAudioBookConfig xmlAudioBookConfig;
	
	private ProcessBuilder processBuilder;
	private Map<String, String> syllableFileMap;
	
	public AudioMaker() {
		try {
			processBuilder = new ProcessBuilder("./collectAudioBook.sh");
			processBuilder.directory(new File(xmlAudioBookConfig.getBashscriptPath()));
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public Map<String, String> getSyllableFileMap(String extension) {
		try {
			File folder = new File(xmlAudioBookConfig.getSyllablesPath()+extension);
			File[] fileArray = folder.listFiles();
	
			syllableFileMap = new HashMap<String, String>();
			
			for(int i = 0; i < fileArray.length; i++) {
				if (fileArray[i].isFile()) {
					syllableFileMap.put(fileArray[i].getName(), fileArray[i].getAbsolutePath());
				}
			}
			System.out.println(syllableFileMap);
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		
		return syllableFileMap;
	}
	
	public void makeMP3(List<String> pathList, String fileName) {
		try {
			if (pathList != null) {
				String files = "";
				for (String path : pathList) {
					files += "\"" + path + "\",";
				}
				files = files.substring(0, files.length() - 1);
				
				StringBuilder script = new StringBuilder();
				script.append("#!/bin/bash\n");
				script.append("rm -f audiobooks/"+fileName+".mp3\n");
				script.append("touch audiobooks/"+fileName+".mp3\n");
				script.append("for i in {" + files + "}\n");
				script.append("do\n");
				script.append("cat $i >> audiobooks/"+fileName+".mp3\n");
				script.append("done\n");
				
				Writer output = new BufferedWriter(new FileWriter(xmlAudioBookConfig.getBashscriptPath()+"collectAudioBook.sh"));
				output.write(script.toString());
				output.close();
				
				Runtime.getRuntime().exec("chmod u+x "+xmlAudioBookConfig.getBashscriptPath()+"collectAudioBook.sh");
				
				processBuilder.start();

				System.out.println("finished mp3");
			}
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void makeWAV(List<String> pathList, String fileName) {
		try {
			if (pathList != null) {
				if(pathList.size() == 1) {
					AudioInputStream appendedFiles = AudioSystem.getAudioInputStream(new File(pathList.get(0)));
					AudioSystem.write(appendedFiles, 
	                        AudioFileFormat.Type.WAVE, 
	                        new File(xmlAudioBookConfig.getAudiobooksPath()+fileName+".wav"));
				}
				else if(pathList.size() > 1){
					AudioInputStream appendedFiles = AudioSystem.getAudioInputStream(new File(pathList.get(0)));
					AudioInputStream clip;
					
					for (int i = 1; i < pathList.size(); i++) {
						clip = AudioSystem.getAudioInputStream(new File(pathList.get(i)));
						appendedFiles = new AudioInputStream(
								new SequenceInputStream(appendedFiles, clip),
								appendedFiles.getFormat(), appendedFiles.getFrameLength()
										+ clip.getFrameLength());
					}
	                AudioSystem.write(appendedFiles, 
	                        AudioFileFormat.Type.WAVE, 
	                        new File(xmlAudioBookConfig.getAudiobooksPath()+fileName+".wav"));
				}
				
				System.out.println("finished wav");
			}
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
}

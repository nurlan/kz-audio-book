package kz.edu.sdu.kab;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import kz.edu.sdu.kab.audio.AudioMaker;
import kz.edu.sdu.kab.audio.AudioPlayer;
import kz.edu.sdu.kab.divisor.Divisor;
import kz.edu.sdu.kab.divisor.domain.Syllable;
import kz.edu.sdu.kab.divisor.domain.Text;
import kz.edu.sdu.kab.divisor.domain.Word;

public class AudioBook {

	private Divisor divisor;
	private AudioMaker audioMaker;
	private AudioPlayer audioPlayer;
	
	private String audioFileName;
	
	public AudioBook() {
		divisor = new Divisor();
		audioMaker = new AudioMaker();
	}
	
	public List<Syllable> getSyllableList(String inputString) {
		List<Syllable> syllableList = new ArrayList<Syllable>();
		
		try {
			Text itext = new Text(inputString);
			
			divisor.setText(itext);
			divisor.yReplacement();
			divisor.divideBySyllables();
			
			for(Word w : divisor.getText().getWordList()) {
				syllableList.addAll(w.getSyllableList());
			}
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		
		return syllableList;
	}
	
	public String make(String text, String extension) {
		try {
			List<Syllable> syllableList = getSyllableList(text);
			audioFileName = ""+syllableList.hashCode();
			Map<String, String> syllableMap = audioMaker.getSyllableFileMap(extension);
			List<String> pathList = new ArrayList<String>();
			for(Syllable syllable : syllableList) {
				if( syllable.getPosition().equals(Syllable.Position.LAST) && syllableMap.containsKey(syllable.getChunk()+"_."+extension) ) {
					pathList.add(syllableMap.get(syllable.getChunk()+"_."+extension));
				}
				else if( syllableMap.containsKey(syllable.getChunk()+"."+extension) ) {
					pathList.add(syllableMap.get(syllable.getChunk()+"."+extension));
				}
				else {
					System.out.println("Syllable not found Exception.");
				}
			}
			if(extension.equals("mp3")) {
				audioMaker.makeMP3(pathList, audioFileName);
			}
			else if(extension.equals("wav")) {
				audioMaker.makeWAV(pathList, audioFileName);
			}
			
			return audioFileName;
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	public void playMP3() {
		playMP3(audioFileName);
	}
	
	public void playMP3(String fileName) {
		try {
			audioPlayer = new AudioPlayer();
			audioPlayer.play(fileName);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void playWAV() {
		playWAV(audioFileName);
	}
	
	public void playWAV(String fileName) {
		try {
			audioPlayer = new AudioPlayer();
			audioPlayer.playWAV(fileName);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public boolean isPlaying() {
		try {
			return audioPlayer.isPlaying();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		
		return false;
	}
	
	public void stop() {
		try {
			if(audioPlayer != null) {
				audioPlayer.stop();
			}
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
}

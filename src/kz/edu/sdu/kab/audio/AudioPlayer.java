package kz.edu.sdu.kab.audio;

import java.io.File;

import javax.media.Format;
import javax.media.Manager;
import javax.media.MediaLocator;
import javax.media.Player;
import javax.media.PlugInManager;
import javax.media.format.AudioFormat;

public class AudioPlayer {

	private Player player;
	
	public AudioPlayer() {
		
	}
	
	public void play(String fileName) {
		Format input1 = new AudioFormat(AudioFormat.MPEGLAYER3);
		Format input2 = new AudioFormat(AudioFormat.MPEG);
		Format output = new AudioFormat(AudioFormat.LINEAR);
		PlugInManager.addPlugIn(
			"com.sun.media.codec.audio.mp3.JavaDecoder",
			new Format[]{input1, input2},
			new Format[]{output},
			PlugInManager.CODEC
		);
		try{
			player = Manager.createPlayer(new MediaLocator(new File("/Users/nurlan/Dev/tests/folders/audiobooks/"+fileName+".mp3").toURI().toURL()));
			player.start();
			System.out.println(player.getStopTime().getNanoseconds());
		}
		catch(Exception ex){
			ex.printStackTrace();
		}
	}
	
	public void stop() {
		try {
			player.stop();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
}

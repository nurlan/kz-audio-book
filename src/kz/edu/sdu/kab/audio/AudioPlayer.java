package kz.edu.sdu.kab.audio;

import java.io.File;

import javax.media.Format;
import javax.media.Manager;
import javax.media.MediaLocator;
import javax.media.Player;
import javax.media.PlugInManager;
import javax.media.format.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

import kz.edu.sdu.kab.config.XmlAudioBookConfig;

public class AudioPlayer {

	private XmlAudioBookConfig xmlAudioBookConfig;
	
	private Player player;
	private Clip clip;
	
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
			player = Manager.createPlayer(new MediaLocator(new File(xmlAudioBookConfig.getAudiobooksPath()+fileName+".mp3").toURI().toURL()));
			player.start();
			System.out.println(player.getStopTime().getNanoseconds());
		}
		catch(Exception ex){
			ex.printStackTrace();
		}
	}
	
	public void playWAV(String fileName) {
		try {
			AudioInputStream wavFile = AudioSystem.getAudioInputStream(new File(xmlAudioBookConfig.getAudiobooksPath()+fileName+".wav"));
			clip = AudioSystem.getClip();
			clip.open(wavFile);
			clip.start();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}

	public void stop() {
		try {
			if ( player != null) {
				player.stop();
			}
			if (clip != null) {
				clip.stop();
			}
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
}

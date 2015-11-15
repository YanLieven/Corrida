package jogo;

import java.io.*;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

import sun.audio.*;

public class Sound {

	public void Play(AudioStream as) throws Exception {
		AudioPlayer.player.start(as);
	}
	
	public void Stop(AudioStream as) {
		AudioPlayer.player.stop(as);
	}
	
}

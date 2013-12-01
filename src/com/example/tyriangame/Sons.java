package com.example.tyriangame;

import android.app.Activity;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;

public class Sons extends Activity{
	private int MAX_STREAMS = 3;
	private int QUALITY = 0;
	
	MediaPlayer mMediaPlayer;
	
	private SoundPool soundp = new SoundPool(MAX_STREAMS,
			AudioManager.STREAM_MUSIC, QUALITY );
	
	private int somExplosao;
	private int somTiro;
	
	public Sons() {
		mMediaPlayer = new MediaPlayer();
		mMediaPlayer = MediaPlayer.create(this, R.raw.musica_jogo);
		mMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
		mMediaPlayer.setLooping(true);
		
		somExplosao = soundp.load(this, R.raw.explosao, 1);
		somTiro = soundp.load(this, R.raw.explosao, 1);
	}
	
	public void tocarMusicaDoJogo() {
			mMediaPlayer.start();
	}
	public void tocarExplosao() {
		soundp.play(somExplosao, 1, 1, 1, 0, 1);
	}
	public void tocarTiro() {
		soundp.play(somTiro, 1, 1, 1, 0, 1);
	}
}

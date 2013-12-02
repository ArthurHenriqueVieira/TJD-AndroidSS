package com.example.tyriangame;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;

public class Sons{
	private int MAX_STREAMS = 3;
	private int QUALITY = 0;
	
	MediaPlayer mMediaPlayer;
	MediaPlayer mMediaPlayer2;
	
	private SoundPool soundp = new SoundPool(MAX_STREAMS,
			AudioManager.STREAM_MUSIC, QUALITY );
	
	private int somExplosao;
	private int somTiro;
	
	public Sons(Context contexto) {
		mMediaPlayer = new MediaPlayer();
		mMediaPlayer = MediaPlayer.create(contexto, R.raw.musica_fundo);
		mMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
		mMediaPlayer.setLooping(true);
		
		mMediaPlayer2 = new MediaPlayer();
		mMediaPlayer2 = MediaPlayer.create(contexto, R.raw.musica_jogo);
		mMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
		mMediaPlayer.setLooping(true);
		
		somExplosao = soundp.load(contexto, R.raw.explosao, 1);
		somTiro = soundp.load(contexto, R.raw.pewpew, 1);
	}
	
	public void tocarMusicaDeFundo() {
			mMediaPlayer.start();
	}
	public void pausarMusicaDeFundo() {
		mMediaPlayer.pause();
	}
	
	public void tocarMusicaDoJogo() {
		mMediaPlayer2.start();
	}
	public void pausarMusicaDoJogo() {
		mMediaPlayer2.pause();
	}
	
	public void tocarExplosao() {
		soundp.play(somExplosao, 1, 1, 1, 0, 1);
	}
	
	public void tocarTiro() {
		soundp.play(somTiro, 1, 1, 1, 0, 1);
	}
}

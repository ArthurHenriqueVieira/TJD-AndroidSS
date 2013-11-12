package com.example.tyriangame;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.widget.Button;

public class MainMenu extends Activity {
	//Cria os botoes.
	Button btn_sair;
	Button btn_ajuda;
	Button btn_ranking;
	Button btn_configuracoes;
	Button btn_jogo;
	
	//Cria um media player
	MediaPlayer mMediaPlayer;
    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main_menu);
		btn_sair = (Button)findViewById(R.id.btn_sair);
		btn_ajuda = (Button)findViewById(R.id.btn_ajuda);
		btn_ranking = (Button)findViewById(R.id.btn_ranking);
		btn_configuracoes = (Button)findViewById(R.id.btn_configuracoes);
		btn_jogo = (Button)findViewById(R.id.btn_novoJogo);
		
		//Cria um media Player
		mMediaPlayer = new MediaPlayer();
		mMediaPlayer = MediaPlayer.create(this, R.raw.musica_fundo);
		mMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
		mMediaPlayer.setLooping(true);
		mMediaPlayer.start();
		
		if(btn_ajuda != null) {
			
			btn_ajuda.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View arg0) {
					Intent i = new Intent();
					i.setClass(getApplicationContext(), Ajuda.class);
        			startActivity(i);
				}
			});
		}
		
		if(btn_ranking != null) {
			
			btn_ranking.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View arg0) {
					Intent i = new Intent();
					i.setClass(getApplicationContext(), Ranking.class);
        			startActivity(i);
				}
			});
		}
		if(btn_configuracoes != null) {
			
			btn_configuracoes.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View arg0) {
					Intent i = new Intent();
					i.setClass(getApplicationContext(), Configuracao.class);
        			startActivity(i);
				}
			});
		}
		if(btn_jogo != null) {
			
			btn_jogo.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View arg0) {
					mMediaPlayer.pause();
					Intent i = new Intent();
					i.setClass(getApplicationContext(), Jogo.class);
        			startActivity(i);
				}
			});
		}
		
		//Botão que fecha o jogo
		if(btn_sair != null) {
			
			btn_sair.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View arg0) {
					finish();
					System.exit(0);
				}
			});
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main_menu, menu);
		return true;
	}

}

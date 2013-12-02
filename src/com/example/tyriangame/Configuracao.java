package com.example.tyriangame;

import android.app.Activity;
import android.content.Context;
import android.media.AudioManager;
import android.os.Bundle;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;

public class Configuracao extends Activity {
	//cria os seeks bar
	SeekBar seekBarSom;
	static SeekBar seekBarAcele;
	
	private AudioManager audioManager;
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_configuracoes);
		
		seekBarAcele = (SeekBar) findViewById(R.id.seek_BarAcele);
		seekBarSom = (SeekBar) findViewById(R.id.seek_BarSom);
		
		audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
		
		seekBarSom.setMax(audioManager
                .getStreamMaxVolume(AudioManager.STREAM_MUSIC));
		seekBarSom.setProgress(audioManager
                .getStreamVolume(AudioManager.STREAM_MUSIC));
		
		seekBarSom.setOnSeekBarChangeListener(new OnSeekBarChangeListener() 
        {
            @Override
            public void onStopTrackingTouch(SeekBar arg0) 
            {
            }

            @Override
            public void onStartTrackingTouch(SeekBar arg0) 
            {
            }

            @Override
            public void onProgressChanged(SeekBar arg0, int progress, boolean arg2) 
            {
                audioManager.setStreamVolume(AudioManager.STREAM_MUSIC,
                        progress, 0);
            }
        });
	}
}

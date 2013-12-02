package com.example.tyriangame;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Window;
import android.widget.EditText;

public class Jogo extends Activity implements SensorEventListener{
	private SurfacePanel panel;
	
	//cria o sensor acelerometro
    private SensorManager mSensorManager;
    private Sensor mAccelerometer;
    
    public Context contexto;
    
    static int pontos = 0;
    
    static boolean gameOver = false;
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		
		contexto = this;
		
		mSensorManager = (SensorManager)getSystemService(SENSOR_SERVICE);
        mAccelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        SharedPreferences preferencias = PreferenceManager.getDefaultSharedPreferences(contexto);
		int sensibilidade = Integer.parseInt(preferencias.getString("sencibilidade", ""));
		
        MainMenu.som.tocarMusicaDoJogo();
		
        panel = new SurfacePanel(this, sensibilidade);
		setContentView(panel);
	}
	@Override
	public void onAccuracyChanged(Sensor sensor, int accuracy) {
		// TODO Auto-generated method stub
		
	}
	
	protected void onResume() {
        super.onResume();
        MainMenu.som.tocarMusicaDoJogo();
        mSensorManager.registerListener(this, mAccelerometer,
        		SensorManager.SENSOR_DELAY_GAME);
    }
	
	protected void onPause() {
        super.onPause();
        MainMenu.som.pausarMusicaDoJogo();
        mSensorManager.unregisterListener(this);
        finish();
    }

	@Override
	public void onSensorChanged(SensorEvent event) {
		//Faz a nave mover usando o acelerometro
		if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER){
				panel.setMovimentoX(event.values[0] * -1);
                panel.setMovimentoY(event.values[1]);
		}
		if(gameOver) {
			gameOver = false;
			panel._thread.setRunning(false);
			adicionaNaListaScore();
		}
	}
	
	public void adicionaNaListaScore() {
		AlertDialog.Builder fimJogo = new AlertDialog.Builder(contexto);
		
		fimJogo.setTitle("Você Morreu");
		fimJogo.setMessage("Digite seu nome");
		
		final EditText nome = new EditText(contexto);
		fimJogo.setView(nome);
		
		fimJogo.setPositiveButton("Submit", new DialogInterface.OnClickListener() {
	    	public void onClick(DialogInterface dialog, int whichButton) {
	    		String player = new String(nome.getText().toString());
	    		DBAdapter adapter = new DBAdapter(contexto);
	    		adapter.open();
	    		if(MainMenu.dificuldade == 1) {
	    			adapter.insertTitle("Jogador: " + player, "Nível: Fácil", "Pontos: " + pontos);
	    		}else if(MainMenu.dificuldade == 2) {
	    			adapter.insertTitle("Jogador: " + player, "Nível: Médio", "Pontos: " + pontos);
	    		}else if(MainMenu.dificuldade == 6) {
	    			adapter.insertTitle("Jogador: " + player, "Nível: Difícil", "Pontos: " + pontos);
	    		}
	    		adapter.close();
	    		Intent i = new Intent(contexto, Ranking.class);
				startActivity(i);
	    	}
    	});
    	
    	AlertDialog build = fimJogo.create();
    	build.show();
	}
}

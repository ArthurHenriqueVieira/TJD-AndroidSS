package com.example.tyriangame;

import android.app.Activity;
import android.graphics.BitmapFactory;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.Window;

public class Jogo extends Activity implements SensorEventListener{
	NaveJogador jogador; //Cria a nave do jogador
	Tiro tiro; //Cria a nave Tiro
	
	//cria o sensor acelerometro
    private SensorManager mSensorManager;
    private Sensor mAccelerometer;
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		
		
		//desenha a nave do jogador
		jogador = new NaveJogador(BitmapFactory.decodeResource(getResources(),
				R.drawable.nave));
		
		mSensorManager = (SensorManager)getSystemService(SENSOR_SERVICE);
        mAccelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
		
        //chama a classe que desenha na tela
		SurfacePanel.setJogador(jogador);
		
		setContentView(new SurfacePanel(this));
	}
	@Override
	public void onAccuracyChanged(Sensor sensor, int accuracy) {
		// TODO Auto-generated method stub
		
	}
	
	protected void onResume() {
        super.onResume();
        mSensorManager.registerListener(this, mAccelerometer, SensorManager.SENSOR_DELAY_NORMAL);
    }
	
	protected void onPause() {
        super.onPause();
        mSensorManager.unregisterListener(this);
    }

	@Override
	public void onSensorChanged(SensorEvent event) {
		//Faz a nave mover usando o acelerometro
		if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER){
			synchronized(jogador) {
				float x = jogador.getCoordinates().getX() + (event.values[0]*-1);
                float y = jogador.getCoordinates().getY() + (event.values[1]);
                jogador.getCoordinates().setX(x);
                jogador.getCoordinates().setY(y);
			}
		}
	}
}

package com.example.tyriangame;

import android.app.Activity;
import android.graphics.BitmapFactory;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.Window;

public class Jogo extends Activity implements SensorEventListener {
	NaveJogador jogador;

    private SensorManager mSensorManager;
    private Sensor mAccelerometer;
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		
		jogador = new NaveJogador(BitmapFactory.decodeResource(getResources(),
				R.drawable.nave));
		
		mSensorManager = (SensorManager)getSystemService(SENSOR_SERVICE);
        mAccelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
		
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
		if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER){
			synchronized(jogador) {
				float x = event.values[0];
				float y = event.values[1];
				jogador.getCoordinates().setX(x += x);
				jogador.getCoordinates().setY(y += y);
			}
		}
	}
}

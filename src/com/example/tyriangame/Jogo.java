package com.example.tyriangame;

import android.app.Activity;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.Window;

public class Jogo extends Activity implements SensorEventListener{
	private SurfacePanel panel;
	
	//cria o sensor acelerometro
    private SensorManager mSensorManager;
    private Sensor mAccelerometer;
	
    static Sons sons;
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		
		mSensorManager = (SensorManager)getSystemService(SENSOR_SERVICE);
        mAccelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        
        sons = new Sons(this);
        sons.tocarMusicaDoJogo();
		
        panel = new SurfacePanel(this);
		setContentView(panel);
	}
	@Override
	public void onAccuracyChanged(Sensor sensor, int accuracy) {
		// TODO Auto-generated method stub
		
	}
	
	protected void onResume() {
        super.onResume();
        sons.tocarMusicaDoJogo();
        mSensorManager.registerListener(this, mAccelerometer, SensorManager.SENSOR_DELAY_NORMAL);
    }
	
	protected void onPause() {
        super.onPause();
        sons.pausarMusicaDoJogo();
        mSensorManager.unregisterListener(this);
    }

	@Override
	public void onSensorChanged(SensorEvent event) {
		//Faz a nave mover usando o acelerometro
		if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER){
				panel.setMovimentoX(event.values[0] * -1);
                panel.setMovimentoY(event.values[1]);
		}
	}
}

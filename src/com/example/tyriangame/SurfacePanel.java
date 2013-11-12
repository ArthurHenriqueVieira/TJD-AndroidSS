package com.example.tyriangame;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class SurfacePanel extends SurfaceView implements SurfaceHolder.Callback {
	private static NaveJogador _jogador;
	private SurfaceThread _thread;
	private ArrayList<Tiro> _tiro = new ArrayList<Tiro>();

	public SurfacePanel(Context context) {
		super(context);
		getHolder().addCallback(this);
		_thread = new SurfaceThread(getHolder(), this);
		setFocusable(true);
	}
	
	@Override
	public void onDraw(Canvas canvas) {
		canvas.drawColor(Color.BLACK);		
		
		//Desenha a nave jogador
		synchronized(_jogador) {
			canvas.drawBitmap(_jogador.getGraphic(), _jogador.getCoordinates().getX(), _jogador.getCoordinates().getY(), null);
		}
		
		//Desenha os Tiros
		Bitmap bitmap;
		Coordinates coordinates;
		for (Tiro tiro : _tiro) {
			bitmap = tiro.getGraphic();
			coordinates = tiro.getCoordinates();
			canvas.drawBitmap(bitmap, coordinates.getX(), coordinates.getY(), null);
		}
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {

		if (event.getAction() == MotionEvent.ACTION_UP) {
			
			synchronized (_thread.getSurfaceHolder()) {
				Tiro tiro = new Tiro(BitmapFactory.decodeResource(getResources(),
								R.drawable.nave), _jogador);
				tiro.getCoordinates().setX(_jogador.getCoordinates().getX());
				tiro.getCoordinates().setY(_jogador.getCoordinates().getY());
				tiro.getSpeed().setY(5);
				_tiro.add(tiro);
			}
		}
		return true;
	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {
		// TODO Auto-generated method stub

	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		_thread.setRunning(true);
		_thread.start();
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		boolean retry = true;
		_thread.setRunning(false);
		while (retry) {
			try {
				_thread.join();
				retry = false;
			} catch (InterruptedException e) {
				// ignoramos a exceção e tentamos de novo
			}
		}
	}
	public void updatePhysics() {
		
		for (Tiro tiro : _tiro) {
			
			tiro.getCoordinates().setY(tiro.getCoordinates().getY() - tiro.getSpeed().getY());
		}
		for(int i = 0; i < _tiro.size(); i++) {
			if (_tiro.get(i).getCoordinates().getY() < 0) {
				_tiro.remove(i);
			}
		}
	}

	public static void setJogador(NaveJogador jogador) {
		_jogador = jogador;
	}
}

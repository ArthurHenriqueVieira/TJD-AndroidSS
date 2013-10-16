package com.example.tyriangame;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class SurfacePanel extends SurfaceView implements SurfaceHolder.Callback {
	private SurfaceThread _thread;
	private NaveJogador jogador = new NaveJogador(BitmapFactory.decodeResource(getResources(),
			R.drawable.nave));

	public SurfacePanel(Context context) {
		super(context);
		getHolder().addCallback(this);
		_thread = new SurfaceThread(getHolder(), this);
		setFocusable(true);
	}

	@Override
	public void onDraw(Canvas canvas) {
		canvas.drawColor(Color.BLACK);
		Bitmap bitmap;
		Coordinates coords;
		
		
		//Desenha a nave jogador
		bitmap = jogador.getGraphic();
		coords = jogador.getCoordinates();
		canvas.drawBitmap(bitmap, coords.getX(), coords.getY(), null);
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
	
	public void fimTela() {
		
	}
}

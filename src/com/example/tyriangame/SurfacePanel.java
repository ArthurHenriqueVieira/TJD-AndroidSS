package com.example.tyriangame;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class SurfacePanel extends SurfaceView implements SurfaceHolder.Callback {
	public SurfaceThread _thread;
	private Personagens personagens = new Personagens();
	
	private float _movimentoX = 200;
	private float _movimentoY = 600;
	
	private Bitmap _tiroBitmap, _inimigoBitmap, _campo, _gameOver;
	
	private Colisao colisao = new Colisao();
	
	private int _sensibilidade;
	
	private Paint paint;
	
	public SurfacePanel(Context context , int sesibilidade) {
		super(context);
		
		_sensibilidade = sesibilidade;
		
		getHolder().addCallback(this);
		_thread = new SurfaceThread(getHolder(), this);
		setFocusable(true);
		
		paint = new Paint();
	    paint.setAntiAlias(true);
	    paint.setDither(true);
	    paint.setColor(Color.BLACK);
	    paint.setTextSize(30);
		
		personagens.setNaveJogador(new NaveJogador(BitmapFactory.decodeResource(
				getResources(), R.drawable.nave)));
		
		_tiroBitmap = BitmapFactory.decodeResource(getResources(),
				R.drawable.tiro);
		
		_inimigoBitmap = BitmapFactory.decodeResource(getResources(),
				R.drawable.inimigo1);
		
		_campo = BitmapFactory.decodeResource(getResources(),
				R.drawable.agua);
		
		_gameOver = BitmapFactory.decodeResource(getResources(),
				R.drawable.gameover);
	}
	
	@SuppressLint("DrawAllocation")
	@Override
	public void onDraw(Canvas canvas) {
		canvas.drawColor(Color.BLACK);
		
		//Desenha o campo
		canvas.drawBitmap(_campo, 0, 0, null);
		
		//Desenha os Tiros
		Bitmap bitmap;
		Coordinates coordinates;
		
		for (Inimigo inimigo : personagens.getInimigo()) {
			bitmap = inimigo.getGraphic();
			coordinates = inimigo.getCoordinates();
			canvas.drawBitmap(bitmap, coordinates.getX(), coordinates.getY(), null);
		}
		
		for (Tiro tiro : personagens.getTiros()) {
			bitmap = tiro.getGraphic();
			coordinates = tiro.getCoordinates();
			canvas.drawBitmap(bitmap, coordinates.getX(), coordinates.getY(), null);
		}
		canvas.drawBitmap(personagens.getNaveJogador().getGraphic(),
				personagens.getNaveJogador().getCoordinates().getX(),
				personagens.getNaveJogador().getCoordinates().getY(),
				null);
		
		_gameOver = Bitmap.createScaledBitmap(_gameOver, getWidth(), getHeight(), true);
		
		canvas.drawText("Pontos :" + Jogo.pontos, 0, 25, paint);
		canvas.drawText("Vidas :" + personagens.getNaveJogador()
				.getVida(), 0, 50, paint);
		
		if(personagens.getNaveJogador().getVida() <= 0) {
			canvas.drawBitmap(_gameOver, 0, 0, null);
			Jogo.gameOver = true;
		}
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {

		if (event.getAction() == MotionEvent.ACTION_UP) {
			
			synchronized (_thread.getSurfaceHolder()) {
				Tiro tiro = new Tiro(_tiroBitmap, personagens.getNaveJogador());
				tiro.getCoordinates().setX(personagens.getNaveJogador().getCoordinates().getX()
						+ personagens.getNaveJogador().getGraphic().getWidth()/2);
				tiro.getCoordinates().setY(personagens.getNaveJogador().getCoordinates().getY()
						+ personagens.getNaveJogador().getGraphic().getHeight()/2);
				tiro.getSpeed().setY(5);
				personagens.getTiros().add(tiro);
				MainMenu.som.tocarTiro();
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
		
		if(personagens.getInimigo().size() <= 0) {
			for(int i = 0; i < 10; i++) {
				personagens.addInimigos(_inimigoBitmap, i * 50);
			}
		}
		
		personagens.getNaveJogador().getCoordinates().setX(personagens.getNaveJogador().getCoordinates().getX() 
				+ (_movimentoX * (float) (_sensibilidade + 1)));
		personagens.getNaveJogador().getCoordinates().setY(personagens.getNaveJogador().getCoordinates().getY() 
				+ (_movimentoY * (float) (_sensibilidade + 1)));
		
		for (Tiro tiro : personagens.getTiros()) {
			tiro.getCoordinates().setY(tiro.getCoordinates().getY() - tiro.getSpeed().getY());
		}
		for(int i = 0; i < personagens.getTiros().size(); i++) {
			if (personagens.getTiros().get(i).getCoordinates().getY() < 0) {
				personagens.getTiros().remove(i);
			}
		}
		
		Coordinates coord;
		Speed speed;
		for(Inimigo inimigos : personagens.getInimigo()) {
			coord = inimigos.getCoordinates();
			speed = inimigos.getSpeed();
			
			// Direction
			if (speed.getYDirection() == Speed.Y_DIRECTION_DOWN) {
				coord.setY((int) (coord.getY() + speed.getY() * MainMenu.dificuldade));
			}
		}
		for(int i = 0; i < personagens.getInimigo().size(); i++) {
			if (personagens.getInimigo().get(i).getCoordinates().getY() > getHeight()) {
				personagens.getInimigo().remove(i);
			}
		}
		colisao.checaColisaoDoPlayerComInimigos(personagens.getNaveJogador(), personagens.getInimigo());
		colisao.checaColisaoDosTirosComInimigos(personagens.getInimigo(), personagens.getTiros());
	}
	public void setMovimentoX(float movimentoX){
		_movimentoX = movimentoX;
	}
	
	public void setMovimentoY(float movimentoY){
		_movimentoY = movimentoY;
	}
}

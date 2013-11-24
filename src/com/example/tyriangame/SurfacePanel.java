package com.example.tyriangame;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class SurfacePanel extends SurfaceView implements SurfaceHolder.Callback {
	private SurfaceThread _thread;
	private Personagens personagens = new Personagens();
	
	private float _movimentoX = 115;
	private float _movimentoY = 250;
	
	private Bitmap _tiroBitmap, _inimigoBitmap;

	public SurfacePanel(Context context) {
		super(context);
		getHolder().addCallback(this);
		_thread = new SurfaceThread(getHolder(), this);
		setFocusable(true);
		
		personagens.setNaveJogador(new NaveJogador(BitmapFactory.decodeResource(
				getResources(), R.drawable.nave)));
		
		_tiroBitmap = BitmapFactory.decodeResource(getResources(),
				R.drawable.nave);
		
		_inimigoBitmap = BitmapFactory.decodeResource(getResources(),
				R.drawable.nave);
	}
	
	@Override
	public void onDraw(Canvas canvas) {
		canvas.drawColor(Color.BLACK);
		
		//Desenha os Tiros
		Bitmap bitmap;
		Coordinates coordinates;
		
		Colisao.checaColisaoDoPlayerComInimigos(personagens.getNaveJogador(), personagens.getInimigo());
		Colisao.checaColisaoDosTirosComInimigos(personagens.getInimigo(), personagens.getTiros());
		
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
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {

		if (event.getAction() == MotionEvent.ACTION_UP) {
			
			synchronized (_thread.getSurfaceHolder()) {
				Tiro tiro = new Tiro(_tiroBitmap, personagens.getNaveJogador());
				tiro.getCoordinates().setX(personagens.getNaveJogador().getCoordinates().getX());
				tiro.getCoordinates().setY(personagens.getNaveJogador().getCoordinates().getY());
				tiro.getSpeed().setY(5);
				personagens.getTiros().add(tiro);
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
		
		personagens.addInimigos(_inimigoBitmap);
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
				// ignoramos a exce��o e tentamos de novo
			}
		}
	}
	public void updatePhysics() {
		
		personagens.getNaveJogador().getCoordinates().setX(_movimentoX);
		personagens.getNaveJogador().getCoordinates().setY(_movimentoY);
		
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
			if (speed.getXDirection() == Speed.X_DIRECTION_RIGHT) {
				coord.setX((int) (coord.getX() + speed.getX() * 1));
			} else {
				coord.setX((int) (coord.getX() - speed.getX() * 1));
			}
			if (speed.getYDirection() == Speed.Y_DIRECTION_DOWN) {
				coord.setY((int) (coord.getY() + speed.getY() * 1));
			} else {
				coord.setY((int) (coord.getY() - speed.getY() * 1));
			}
			
			// borders for x...
			if (coord.getX() < 0) {
				speed.toggleXDirection();
				coord.setX(-coord.getX());
			} else if (coord.getX() + (inimigos.getGraphic().getWidth()) > getWidth()) {
				speed.toggleXDirection();
				coord.setX(coord.getX() + getWidth() + -(coord.getX() + inimigos.getGraphic().getWidth()));
			}

			// borders for y...
			if (coord.getY() < 0) {
				speed.toggleYDirection();
				coord.setY(-coord.getY());
			} else if (coord.getY() + inimigos.getGraphic().getHeight() > getHeight()) {
				speed.toggleYDirection();
				coord.setY(coord.getY()
						+ getHeight()
						+ -(coord.getY() + inimigos.getGraphic()
								.getHeight()));
			}
		}
	}
	public float setMovimentoX(float movimentoX){
		return _movimentoX += movimentoX;
	}
	
	public float setMovimentoY(float movimentoY){
		return _movimentoY += movimentoY;
	}
}

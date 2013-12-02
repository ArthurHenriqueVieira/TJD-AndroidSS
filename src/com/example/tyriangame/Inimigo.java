package com.example.tyriangame;

import android.graphics.Bitmap;

public class Inimigo implements GameObject {
	private Bitmap _bitmap;
	private Coordinates _coordinates;
	private NaveJogador _jogador;
	private Speed _speed;
	private int _vida;
	
	Inimigo(Bitmap bitmap) {
		_bitmap = bitmap;
	    _coordinates = new Coordinates();
	    _coordinates.setX(0);
	    _coordinates.setY(0);
	    _speed = new Speed();
	    _vida = 3;
	}
	public int getVida() {
		return _vida;
	}
	public void diminuirVida() {
		_vida -= 1;
	}
	
	public Coordinates getCoordinates() {
		return _coordinates;
	}
	
	public Bitmap getGraphic() {
		return _bitmap;
	}
	
	public NaveJogador getJogador() {
		return _jogador;
	}
	
	public Speed getSpeed() {
		return _speed;
	}
}

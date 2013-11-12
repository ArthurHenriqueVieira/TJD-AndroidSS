package com.example.tyriangame;

import android.graphics.Bitmap;

public class Inimigo {
	private Bitmap _bitmap;
	private Coordinates _coordinates;
	private NaveJogador _jogador;
	private Speed _speed;
	
	Inimigo(Bitmap bitmap) {
		_bitmap = bitmap;
	    _coordinates = new Coordinates();
	    _coordinates.setX(0);
	    _coordinates.setY(0);
	    _speed = new Speed();
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

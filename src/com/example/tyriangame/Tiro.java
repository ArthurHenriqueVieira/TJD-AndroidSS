package com.example.tyriangame;

import android.graphics.Bitmap;

public class Tiro implements GameObject {
	private Bitmap _bitmap;
	private Coordinates _coordinates;
	private NaveJogador _jogador;
	private Speed _speed;
	
	Tiro(Bitmap bitmap, NaveJogador jogador) {
		_bitmap = bitmap;
	    _coordinates = new Coordinates();
	    _coordinates.setX(jogador.getCoordinates().getX());
	    _coordinates.setY(jogador.getCoordinates().getY());
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

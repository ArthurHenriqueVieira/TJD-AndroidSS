package com.example.tyriangame;

import android.graphics.Bitmap;

public class NaveJogador {
	private Bitmap _bitmap;
	private Coordinates _coordinates;
	
	private Speed _speed;
	
	private int _energia;
	 
	public NaveJogador(Bitmap bitmap) {
	    _bitmap = bitmap;
	    _coordinates = new Coordinates();
	    _coordinates.setX(115);
	    _coordinates.setY(250);
	    _speed = new Speed();
	    _energia = 100;
	}
	
	public int getEnergia() {
		return _energia;
	}
	 
	public Speed getSpeed() {
	    return _speed;
	}

	public Bitmap getGraphic() {
		return _bitmap;
	}

	public Coordinates getCoordinates() {
		return _coordinates;
	}
}

package com.example.tyriangame;

import android.graphics.Bitmap;

public class NaveJogador implements GameObject {
	private Bitmap _bitmap;
	private Coordinates _coordinates;
	
	private int _vida;
	 
	public NaveJogador(Bitmap bitmap) {
	    _bitmap = bitmap;
	    _coordinates = new Coordinates();
	    _vida = 3;
	}
	
	public int getVida() {
		return _vida;
	}
	public void diminuirVida() {
		_vida -= 1;
	}

	public Bitmap getGraphic() {
		return _bitmap;
	}

	public Coordinates getCoordinates() {
		return _coordinates;
	}
}

package com.example.tyriangame;

import java.util.ArrayList;

import android.graphics.Bitmap;

public class Personagens {
	private NaveJogador _nave;
	private ArrayList<Tiro> _tiro = new ArrayList<Tiro>();
	private ArrayList<Inimigo> _inimigo = new ArrayList<Inimigo>();
	
	public NaveJogador getNaveJogador() {
		return _nave;
	}
	public void setNaveJogador(NaveJogador nave) {
		_nave = nave;
	}
	
	public ArrayList<Tiro> getTiros() {
		return _tiro;
	}
	
	public synchronized ArrayList<Inimigo> getInimigo() {
		return _inimigo;
	}
	
	public void addInimigos(Bitmap bitmap) {
		
		for(int i = 0; i < 10; i++){
			Inimigo inimigo = new Inimigo(bitmap);
			
			int j = i;
			inimigo.getCoordinates().setX(j);
			inimigo.getCoordinates().setY(0);
			
			j += 50;
			_inimigo.add(inimigo);
		}
	}
}

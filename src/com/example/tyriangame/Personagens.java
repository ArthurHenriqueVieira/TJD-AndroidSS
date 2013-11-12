package com.example.tyriangame;

import java.util.ArrayList;

public class Personagens {
	private NaveJogador _nave;
	private ArrayList<Tiro> _tiro = new ArrayList<Tiro>();
	
	public NaveJogador getNaveJogador() {
		return _nave;
	}
	public void setNaveJogador(NaveJogador nave) {
		_nave = nave;
	}
	
	public ArrayList<Tiro> getTiros() {
		return _tiro;
	}
}

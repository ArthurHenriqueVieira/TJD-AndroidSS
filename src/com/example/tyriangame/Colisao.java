package com.example.tyriangame;

import java.util.ArrayList;

public class Colisao {
	static public void checaColisaoDoPlayerComInimigos(NaveJogador player, ArrayList<Inimigo> inimigos)
	{
		for(Inimigo inimigo : inimigos)
		{
			if(isColidindoDoisCirculos(player, inimigo))
			{
				// A��es de Game Over
			}
		}
	}
	
	static public void checaColisaoDosTirosComInimigos(ArrayList<Inimigo> inimigos, ArrayList<Tiro> tiros)
	{
		for(Inimigo inimigo : inimigos)
		{
			for(Tiro tiro : tiros)
			{
				if(isColidindoDoisCirculos(inimigo, tiro))
				{
					inimigos.remove(inimigo);
					tiros.remove(tiro);
					break;
				}
			}
		}
	}
	
	static public Boolean isColidindoDoisCirculos(GameObject a, GameObject b)
	{
		// Calcula a hipotenusa
		float distX = Math.abs(a.getCoordinates().getX() - b.getCoordinates().getX());
		float distY = Math.abs(a.getCoordinates().getY() - b.getCoordinates().getY());
		double distHip = Math.sqrt(Math.pow(distX, 2) + Math.pow(distY, 2));
		
		// Pega a menor dimens�o das imagens
		double raioA = verificaMenorTamanho(a.getGraphic().getHeight(), a.getGraphic().getWidth());
		double raioB = verificaMenorTamanho(b.getGraphic().getHeight(), b.getGraphic().getWidth());
		
		// Pega a maior dimens�o, dentre a menor dimens�o das imagens, como o raio de colis�o
		double raio = raioA >= raioB ? raioA : raioB;
		
		// Verifica a colis�o
		if(distHip >= raio)
			return true;
		else
			return false;
	}
	
	private static double verificaMenorTamanho(float height, float width)
	{
		return height <= width ? height : width;
	}
}

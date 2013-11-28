package com.example.tyriangame;

import java.util.ArrayList;
import java.util.Iterator;

public class Colisao{
	
	//private Sons som = new Sons();
	
	public void checaColisaoDoPlayerComInimigos(NaveJogador player, ArrayList<Inimigo> inimigos)
	{
		Iterator<Inimigo> iInimigos = inimigos.iterator();
		while(iInimigos.hasNext())
		{
			Inimigo inimigo = iInimigos.next();

			if(isColidindoDoisCirculos(player, inimigo))
			{
				iInimigos.remove(); // Remove o inimigo que colidiu
				player.diminuirVida();
				//som.tocarExplosao();
			}
		}
	}
	
	public void checaColisaoDosTirosComInimigos(ArrayList<Inimigo> inimigos, ArrayList<Tiro> tiros)
	{
		Iterator<Inimigo> iInimigos = inimigos.iterator();
		while(iInimigos.hasNext())
		{
			Inimigo inimigo = iInimigos.next();
			
			Iterator<Tiro> iTiros = tiros.iterator();
			while(iTiros.hasNext())
			{
				if(isColidindoDoisCirculos(inimigo, iTiros.next()))
				{
					//som.tocarExplosao();
					iInimigos.remove();
					iTiros.remove();
					break;
				}
			}
		}
	}
	
	static public Boolean isColidindoDoisCirculos(GameObject a, GameObject b)
	{
		// Calcula a hipotenusa
		float distX = a.getCoordinates().getX() - b.getCoordinates().getX();
		float distY = a.getCoordinates().getY() - b.getCoordinates().getY();
		double distHip = Math.sqrt(Math.pow(distX, 2) + Math.pow(distY, 2));
		
		// Pega a menor dimensão das imagens
		double raioA = verificaMenorTamanho(a.getGraphic().getHeight(), a.getGraphic().getWidth());
		double raioB = verificaMenorTamanho(b.getGraphic().getHeight(), b.getGraphic().getWidth());
		
		// Pega a maior dimensão, dentre a menor dimensão das imagens, como o raio de colisão
		double raio = raioA >= raioB ? raioA : raioB;
		
		// Verifica a colisão
		if(distHip <= raio)
			return true;
		else
			return false;
	}
	
	private static double verificaMenorTamanho(float height, float width)
	{
		return height <= width ? height : width;
	}
}

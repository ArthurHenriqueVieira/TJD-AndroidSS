package com.example.tyriangame;

public class Colisao {
	static public Boolean checaColisaoDeDoisCirculos(GameObject a, GameObject b) {
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

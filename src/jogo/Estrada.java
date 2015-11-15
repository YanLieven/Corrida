package jogo;

import java.awt.Image;

public class Estrada {

	public int x, y; // posição na tela
	Image imagem; // imagem da estrada
	
	public Estrada(int x, int y) { // construtor da estrada
		
		this.x = x;
		this.y = y;
	}

	public void move() { // movimenta a estrada
		
		x -= 3;
		
		if(x < -600)
			x = 601;
	}

}

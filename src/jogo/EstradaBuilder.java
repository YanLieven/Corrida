package jogo;

import java.awt.Image;

public class EstradaBuilder {
	
	private Estrada estrada;
	
	public EstradaBuilder(int x, int y) {
		estrada = new Estrada(x, y);
	}
	
	public void mover() {
		estrada.move();
	}
	
	public int getX() {
		return estrada.x;
	}

	public int getY() {
		return estrada.y;
	}

	public Image getImagem() {
		return estrada.imagem;
	}

	public void setImagem(Image imagem) {
		estrada.imagem = imagem;
	}
	
	public Estrada returnEstrada() {
		return estrada;
	}
	
}

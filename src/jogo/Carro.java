package jogo;

import java.awt.Image;
import java.awt.Rectangle;
import javax.swing.ImageIcon;

public abstract class Carro { // classe dos carros

	protected int x, y, alt, lar, velocidade; // posição(x, y), altura e largura do rectangle e velocidade
	protected Image imagem; // imagem de cada carro
	protected boolean isAlive; // diz se o carro está vivo
	protected ImageIcon ref; // referência usada para a imagem
	protected String icone; // caminho da imagem
	
	public Carro(int x, int y, int velocidade) { // construtor dos carros
		
		this.x = x;
		this.y = y;
		this.velocidade = velocidade;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public Image getImagem() {
		return imagem;
	}
	
	public void setImagem(Image imagem) {
		this.imagem = imagem;
	}
	
	public Rectangle ret() { // cria o rectangle de cada carro
		return new Rectangle(x, y, lar, alt);
	}
	
	public abstract void move(); // método de se mover

}

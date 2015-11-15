package jogo;

import java.awt.event.KeyEvent;

import javax.swing.ImageIcon;

public class Heroi extends Carro { // classe do her�i(herda de carro)

	private static Heroi heroi = null;
	
	private Heroi() { // construtor do her�i
		super(75, 100, 0);
		
		isAlive = true;
		
		icone = "res/carro.gif";
		ref = new ImageIcon(icone);
		imagem = ref.getImage();
		
		this.lar = imagem.getWidth(null);
		this.alt = imagem.getHeight(null);
	}
	
	public static Heroi getHeroi() {
		if(heroi == null)
			heroi = new Heroi();
		return heroi;
	}
	
	public void keyPressed(KeyEvent tecla) { // m�todo do evento de clicar na tecla
		
		int cod = tecla.getKeyCode(); // verifica a tecla apertada
		
		if(cod == KeyEvent.VK_UP) // checa se foi um "up"
			velocidade = -3;
		
		else if(cod == KeyEvent.VK_DOWN)  // checa se foi um "down"
			velocidade = 3;
	}
	
	public void keyReleased(KeyEvent tecla) { // m�todo de liberar a tecla
		
		int cod = tecla.getKeyCode(); // checa a tecla
		
		if(cod == KeyEvent.VK_UP) 
			velocidade = 0;
		else if(cod == KeyEvent.VK_DOWN)
			velocidade = 0;
	}
	
	public void move(){ // implementa o movimento
		
		y += velocidade;
		if(y < 23)
			y = 23;
		else if(y > 178)
			y = 178;
	}

}

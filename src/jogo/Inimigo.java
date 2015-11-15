package jogo;

import java.util.Random;

import javax.swing.ImageIcon;

public class Inimigo extends Carro implements Prototype { // classe dos inimigos(herda de carro)
	
	public int cont = 0; // marca quantas vezes o inimigo chegou ao fim da tela
	
	private Random random = new Random();
	
	int aux = 1;
	
	public Inimigo(int x, int y, int velocidade) { // construtor do inimigo
		
		super(x, y, velocidade);
		
		isAlive = true;
		
		imagemAux(random.nextInt(3) + 1);
		
		this.lar = imagem.getWidth(null);
		this.alt = imagem.getHeight(null);
	}
	
	public void move() { // movimenta o inimigo
		
		if(y < 23)
			y = 23;
		else if(y > 178)
			y = 178;
		
		if(x < 0) { // checa se chegou ao fim da tela
			
			imagemAux(aux);
			++aux;
			if(aux > 4)
				aux = 1;
			
			++cont; // soma +1 ao contador
			
			x = 700;
			y = random.nextInt(178) + 23;
		}
		else { // muda a velocidade do inimigo com relação a "x"
			x -= (aux);
		}
	}
	
	private void imagemAux(int auxx) { // troca o inimigo de imagem
		
		if(auxx == 1) {
			ref = new ImageIcon("res/carro3.gif");
			imagem = ref.getImage();
		}
		else if (auxx == 2) {
			ref = new ImageIcon("res/carro4.gif");
			imagem = ref.getImage();
		}
		else if(auxx == 3) {
			ref = new ImageIcon("res/carro5.gif");
			imagem = ref.getImage();
		}
		else {
			ref = new ImageIcon("res/carro2.gif");
			imagem = ref.getImage();
		}
	}
	
	public Inimigo doClone(int x, int y) {
		return new Inimigo(x, y, velocidade);
	}

}

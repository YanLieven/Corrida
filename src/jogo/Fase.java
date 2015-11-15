package jogo;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.*;
import java.util.*;

import sun.audio.*;

import javax.swing.*;
import javax.swing.Timer;

public class Fase extends JPanel implements ActionListener { // Classe da fase - Herda de JPanel e implementa um ActionListener
	
	int maiorPontuação;
	
	String Recorde;
	
	Fase nextFase;
	
	private EstradaBuilder estrada1, estrada2; // Dois tipos estrada usados no movimento do cenário
	private Image imagem; // Imagem usada no cenário
	private Heroi heroi; // Nosso personagem
	private Timer time; // Timer para chamar as ações de movimentação
	
	boolean play; // Usado para saber qual estado o game está
	
	Sound sound;
	AudioStream as;
	
	private List<Inimigo> inimigos; // Lista de inimigos para o jogo
	private int[][] pos = { // posições de cada inimigo
			{1000,130},{3000,178},{600,23},
			{5000, 90}, {9000, 60},{7000, 145}, {5000, 43}
	};
	
	public Fase(String Recorde) throws Exception{ // construtor da fase
		
		play = true;
		
		this.Recorde = Recorde; // define o recorde inicial
		
		sound = new Sound();
		as = new AudioStream(new FileInputStream("res/clashbbmid.wav"));
		sound.Play(as);
		
		ImageIcon ref = new ImageIcon("res/road.jpg"); // inicia a imagem do cenário
		imagem = ref.getImage(); // atribui a imagem ao cenário
		
		/*a = new Estrada(0, 0); // inicia uma das estradas
		b = new Estrada(601, 0); // inicia a outra
		
		a.setImagem(imagem); // atribui a imagem de cenário à estrada "a"
		b.setImagem(imagem);*/ // atribui o mesmo à "b"
		
		estrada1 = new EstradaBuilder(0, 0);
		estrada2 = new EstradaBuilder(601, 0);
		
		estrada1.setImagem(imagem); // atribui a imagem de cenário à estrada "1"
		estrada2.setImagem(imagem); // atribui o mesmo à estrada "2"
		
		heroi = Heroi.getHeroi(); // instancia o herói
		criaini();
		
		addKeyListener(new Tecla()); // adiciona o perceptor de cada tecla apertada
		
		setDoubleBuffered(true); // tira as imperfeições gráficas
		setFocusable(true);
		
		time = new Timer(5, this); // instancia o timer
		time.start(); // inicia o timer
		
	}

	private void criaini() { // método de criar os inimigos
		
		inimigos = new ArrayList<Inimigo>(); // instancia a lista dos inimigos
		
		Random random = new Random();
		
		Inimigo inimigo = new Inimigo(random.nextInt(2000) + 600, random.nextInt(178) + 23, 1);
		for(int i = 1; i < 5; ++i)
			inimigos.add(inimigo.doClone(random.nextInt(2000) + 600, random.nextInt(178) + 23));
	}
	
	private void colide() { // checa as colisões
		
		Rectangle fher = heroi.ret(), fini; // cria um rectangle do her�i e um dos inimigos
		
		for(int i = 0; i < inimigos.size(); ++i){ //for de comparação
			
			Inimigo aux = inimigos.get(i); // cria um inimigo auxiliar e o liga a um inimigo real 
			fini = aux.ret(); // liga o rectangle do inimigo ao do auxiliar
			
			if(fher.intersects(fini)) { // checa se houve a colisão
				
				heroi.isAlive = false; // "Mata o personagem"
				aux.isAlive = false; // "Mata" o inimigo
				play = false; // acaba a partida
				
				verificarRecorde();
			
			}
		}
	}
	
	public void paint(Graphics g) { // M�todo dos gr�ficos do jogo
		
		Graphics2D grafic = (Graphics2D) g; // cria um graphics2d
		
		if(play) { // se a partida estiver acontecendo executa esse escopo
			
			/*grafic.drawImage(a.getImagem(), a.getX(), a.getY(), this); // chama a estrada "a"
			grafic.drawImage(b.getImagem(), b.getX(), b.getY(), this);*/ // chama a estrada "b"
			grafic.drawImage(estrada1.getImagem(), estrada1.getX(), estrada1.getY(), this);
			grafic.drawImage(estrada2.getImagem(), estrada2.getX(), estrada2.getY(), this);
			
			estrada1.mover();
			estrada2.mover();
			
			int pontuação = 0; // cria a variável que mede a pontuação ao longo da partida
			
			grafic.drawImage(heroi.getImagem(), heroi.getX(), heroi.getY(), this); //faz o herói aparecer
			
			for(int i = 0; i < inimigos.size(); ++i) { // percorre a lista de inimigos
			Inimigo j = inimigos.get(i); // inimigo auxiliar
			j.move(); // move o inimigo
			colide(); // chama o m�todo da colis�o dos inimigos
			pontuação += j.cont; // atualiza a pontua��o
			grafic.drawImage(j.getImagem(), j.getX(), j.getY(), this); // faz cada inimigo aparecer
			}
			
			grafic.drawString("Pontos Ganhos: " + pontuação, 5, 15); // mostra a pontuação atualizada
			
			setMaiorPontuação(pontuação); // define a maior pontuação a cada nova pontuação aumentada
				
		}
		else { // caso a partida seja encerrada
			
			sound.Stop(as);
			
			ImageIcon acabou = new ImageIcon("res/gameover.jpg"); // tela de game over
			
			grafic.drawImage(acabou.getImage(), 0, 0, this); // faz a tela aparecer
			
			grafic.drawString("Sua Pontuação foi: " + maiorPontuação, 5, 15); // mostra a pontuação conseguida
			grafic.drawString("'Espaço' para ver recorde", 5, 30);
			grafic.drawString("'Enter' para tentar de novo", 400, 15); // mostra como resetar
			grafic.drawString("'Esc' para sair do jogo", 400, 30);
			

		}
		g.dispose(); // "limpa" os graficos
	}
	
	public void actionPerformed(ActionEvent arg0) { // usado para o keyadapter
		
		heroi.move(); // move o her�i
		repaint(); // atualiza os gr�ficos do paint
	}
	
	private class Tecla extends KeyAdapter { // innerclass do keyadapter

		public void keyPressed(KeyEvent arg0) { // m�todo para verificar bot�es apertados
			
			if(!play) {
				
				if(arg0.getKeyCode() == KeyEvent.VK_ENTER) { // caso o enter seja apertado
					play = true; // o jogo volta a acontecer
					try {
						as = new AudioStream(new FileInputStream("res/clashbbmid.wav"));
						sound.Play(as);
					} catch (Exception e) {
						System.out.println("Áudio não encontrado.");
						e.printStackTrace();
					}
					heroi = Heroi.getHeroi(); // recria o her�i
					criaini(); // recria os inimigos
					}
				
				else if(arg0.getKeyCode() == KeyEvent.VK_ESCAPE)
					System.exit(0);
				
				else if(arg0.getKeyCode() == KeyEvent.VK_SPACE)
					JOptionPane.showMessageDialog(null, Recorde);
				
				}
			
			heroi.keyPressed(arg0); // chama o m�todo de novo
		}

		public void keyReleased(KeyEvent arg0) { // m�todo que libera a tecla
			heroi.keyReleased(arg0); // a��o para liberar a classe
		}
		
	}
	
	private void Record() {
		
		FileReader filereader;
		
		try {
			filereader = new FileReader(new File("res/recorde.txt")); // usado para ler o arquivo
			BufferedReader reader = new BufferedReader(filereader); // usado para ler o arquivo por linhas
			Recorde = reader.readLine(); // lê o arquivo por linhas
			reader.close(); // fecha o BufferedReader
		} catch (FileNotFoundException e) { // Exception caso o arquivo n�o seja aberto com sucesso
			System.out.println("O arquivo: " + (new File("res/recorde.txt")).getName() + " Não pôde ser aberto.");
			e.printStackTrace();
		} catch (Exception e) { // Exception para caso o reader dê problema
			System.out.println("erro no recorde.");
			e.printStackTrace();
		}
		
	}
	
	private void verificarRecorde() {
		
		String newRecorde = "Recorde: " + maiorPontuação;
		
		if(newRecorde.length() == Recorde.length()) {
			if(newRecorde.compareTo(Recorde) > 0) {
				saveFile(newRecorde);
				Recorde = newRecorde;
			}
		}
		else if(newRecorde.length() > Recorde.length()) {
			saveFile(newRecorde);
			Recorde = newRecorde;
		}
	}
	
	private void saveFile(String recorde) {
		
		FileWriter filewriter; // cria um filewriter
		
		try {
			
			filewriter = new FileWriter("res/recorde.txt");
			filewriter.write(recorde); // escreve o novo recorde no arquivo de texto
		    filewriter.close(); // fecha o filewriter
		
		} catch (IOException e1) { // exception para caso n�o consiga salvar o recorde
			
			System.out.println("Ocorreu um erro ao tentar salvar o recorde.");
			e1.printStackTrace();
		}
		
	}
	
	private void inserirNovaFase() throws Exception {
		nextFase = new Fase(Recorde);
		nextFase.as = new AudioStream(new FileInputStream("res/clashbbmid.wav"));
	}
	
	private void setMaiorPontuação(int recorde) { // muda a maior pontua��o
		maiorPontuação = recorde;
	}
	
}

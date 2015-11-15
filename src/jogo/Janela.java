package jogo;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.image.ImageObserver;
import java.io.*;

import javax.imageio.ImageIO;
import javax.swing.*;

public class Janela extends JFrame { // Classe da minha Janela
	
	File file = new File("res/recorde.txt");
	
	String record;
	
	JPanel painel;
	
	JButton jogar, recorde, sair;
	
	Container container;
	
	CardLayout cl;
	
	Fase fase;
	
	public static void main(String[] args) throws Exception {
		
		new Janela(); // Instancia a janela
	
	}
	
	public Janela() throws Exception { // Construtor da classe
		
		cl = new CardLayout();
		
		container = getContentPane();
		
		container.setLayout(cl);
		
		painel = new JPanel() {
			
			ImageIcon icon = new ImageIcon("res/telaini.jpg");
			Image im = icon.getImage();
			
			public void paint(Graphics g) {
				Graphics2D grafic = (Graphics2D) g;
				grafic.drawImage(im, 0, 0, this);
			}
		};
		
		jogar = new JButton("Jogar");
		recorde = new JButton("Recorde");
		sair = new JButton("Sair");
		
		painel.add(jogar);
		painel.add(recorde);
		painel.add(sair);
		
		Jogar();
		Recorde();
		Sair();
		
		container.add(painel, "1");
		
		setName("Corrida Infinita"); // Nome da janela
		setSize(600, 300); // tamamnho da janela
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE); // Configuração que só permite a você sair do jogo ao clicar no bot�o "Sair"
		setLocationRelativeTo(null);
		setResizable(false); // Não permite que a janela seja redimensionada
		setVisible(true); // Faz a janela ficar visível
		
	}
	
	public void Jogar() {
		
		jogar.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				
				try {
					
					lerRecorde();
					
					criarFase();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			
			
		});
	}
	
	public void Recorde() { // Implementa obotão Recorde
		recorde.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent arg0) {
				
				lerRecorde();
				JOptionPane.showMessageDialog(null, record);
			}
		});
	}
	
	public void Sair() { // implementa o botão Sair
		
		sair.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				System.exit(0);
			}
		});
	}
	
	public void lerRecorde() {
		
		FileReader filereader;
		
		try {
			filereader = new FileReader(file); // usado para ler o arquivo
			BufferedReader reader = new BufferedReader(filereader); // usado para ler o arquivo por linhas
			record = reader.readLine(); // lê o arquivo por linhas
			reader.close(); // fecha o BufferedReader
		} catch (FileNotFoundException e) { // Exception caso o arquivo n�o seja aberto com sucesso
			System.out.println("O arquivo: " + file.getName() + " Não pôde ser aberto.");
			record = "Recorde: 0";
			e.printStackTrace();
		} catch (Exception e) { // Exception para caso o reader dê problema
			System.out.println("erro no recorde.");
			record = "Recorde: 0";
			e.printStackTrace();
		}
	}
	
	public void criarFase() throws Exception {
		
		fase = new Fase(record);
		
		container.add(fase, "2");
		
		changeFocus(fase, jogar);
		
		cl.show(container, "2");
	    
	}
	
	private void changeFocus(final Component source,
		      final Component target) {
		    SwingUtilities.invokeLater(new Runnable() {
		      public void run() {
		        target.dispatchEvent(
		          new FocusEvent(source, FocusEvent.FOCUS_GAINED));
		      }
		    });
		  }
	
}

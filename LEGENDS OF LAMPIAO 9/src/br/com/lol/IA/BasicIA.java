package br.com.lol.IA;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import br.com.lol.core.Game;
import br.com.lol.entidade.Corvo;
import br.com.lol.entidade.Inimigo;
import br.com.lol.entidade.InimigoForte;
import br.com.lol.entidade.InimigoFraco;
import br.com.lol.gerenciadores.ImageManager;

public class BasicIA{
	
	private Game stage;
	private int telaX;
	private int telaY;
	
	private BufferedImage zumbi_forte_normal;
	private BufferedImage zumbi_forte_invertido;
	private BufferedImage zumbi_fraco_normal;
	private BufferedImage zumbi_fraco_invertido;
	
	public BasicIA(int x, int y){
		this.telaX = x;
		this.telaY = y;
		inicializarImagensInimigos();
	}
	
	private void inicializarImagensInimigos(){
		try{
		this.zumbi_forte_normal = ImageManager.getInstance()
				.loadImage("br/com/lol/imagens/zumbis/sprite_zumbi_verde_normal1.png");
		this.zumbi_forte_invertido = ImageManager.getInstance()
				.loadImage("br/com/lol/imagens/zumbis/sprite_zumbi_verde_invertido1.png");
		this.zumbi_fraco_normal = ImageManager.getInstance()
				.loadImage("br/com/lol/imagens/zumbis/sprite_zumbi_roxo_normal1.png");
		this.zumbi_fraco_invertido = ImageManager.getInstance()
				.loadImage("br/com/lol/imagens/zumbis/sprite_zumbi_roxo_invertido1.png");
		}catch(IOException e){
			e.getMessage();
		}
	}
	
	public void adicionarInimigosLimit(List<Inimigo> inimigos){
		int contador = 0;
		Random rnd = new Random();
		while(contador != 25){
			if(rnd.nextBoolean()){
				if(rnd.nextBoolean()){
					inimigos.add(new InimigoForte(720, this.telaY - 100,
							zumbi_forte_invertido, -1));
				}else{
					inimigos.add(new InimigoForte(10 , this.telaY - 100,
							zumbi_forte_normal, 1));
				}
			}else{
				if(rnd.nextBoolean()){
					inimigos.add(new InimigoFraco(720, this.telaY - 100, 
							zumbi_fraco_invertido, -1));
				}else{
					inimigos.add(new InimigoFraco(10, this.telaY - 100,
							zumbi_fraco_normal, 1));
				}
			}
			contador++;
		}
	}

	
	public void adicionarCorvos(List<Corvo> corvos){
		Random rnd = new Random();
		if(rnd.nextBoolean()){
			corvos.add(new Corvo(0, 0, 1));
		}else{
			corvos.add(new Corvo(800, 0, -1));
		}
	}
	
	public void rodar(Inimigo i, int currentTick){
		i.seMexer(currentTick);
	}

}

package br.com.lol.entidade;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import br.com.lol.gerenciadores.ImageManager;

public class InimigoFraco extends Inimigo{

	private BufferedImage imagemNormal1;
	private BufferedImage imagemNormal2;
	private BufferedImage imagemInvertida1;
	private BufferedImage imagemInvertida2;
	private List<BufferedImage> imagensDireita;
	private List<BufferedImage> imagensEsquerda;
	
	public InimigoFraco(int x, int y, BufferedImage imagem, int direcao){
		super(x,y, direcao);
		this.identificador = "fraco";
		this.energia = 1;
		this.speed = 1;
		try {
			this.imagemNormal1 = ImageManager.getInstance().loadImage(
					"br/com/lol/imagens/zumbis/sprite_zumbi_roxo_normal1.png");
			this.imagemNormal2 = ImageManager.getInstance().loadImage(
					"br/com/lol/imagens/zumbis/sprite_zumbi_roxo_normal2.png");
			this.imagemInvertida1 = ImageManager.getInstance().loadImage(
					"br/com/lol/imagens/zumbis/sprite_zumbi_roxo_invertido1.png");
			this.imagemInvertida2 = ImageManager.getInstance().loadImage(
					"br/com/lol/imagens/zumbis/sprite_zumbi_roxo_invertido2.png");
			
			this.imagensDireita = new ArrayList<BufferedImage>();
			this.imagensEsquerda = new ArrayList<BufferedImage>();
			
			this.imagensDireita.add(imagemNormal1);
			this.imagensDireita.add(imagemNormal2);
			this.imagensEsquerda.add(imagemInvertida1);
			this.imagensEsquerda.add(imagemInvertida2);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void andarDireita(int currentTick){
		if(currentTick % 15 == 0){
			this.imagem = imagemNormal1;
		}else if(currentTick % 21 == 0)
			this.imagem = imagemNormal2;
	}
	
	private void andarEsquerda(int currentTick){
		if(currentTick % 15 == 0){
			this.imagem = imagemInvertida1;
		}else if(currentTick % 21 == 0)
			this.imagem = imagemInvertida2;
	}
	
	public void seMexer(int currentTick){
		if(this.direcao > 0){
			this.x += this.speed;
			andarDireita(currentTick);
			if(this.x > 720){
				this.direcao *= -1;
				try {
					this.imagem = ImageManager.getInstance().loadImage("br/com/lol/imagens/zumbis/"
							+ "sprite_zumbi_roxo_invertido1.png");
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}else{
			this.x -= this.speed;
			andarEsquerda(currentTick);
			if(this.x < 10){
				this.direcao *= -1;
				try {
					this.imagem = ImageManager.getInstance().loadImage("br/com/lol/imagens/zumbis/"
							+ "sprite_zumbi_roxo_normal1.png");
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
}

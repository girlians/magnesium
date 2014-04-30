package br.com.lol.entidade;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import br.com.lol.gerenciadores.ImageManager;
import br.com.lol.gerenciadores.SpriteAnimation;

public class Jogador extends Personagem{
	
	private List<Tiro> tiros;
	private BufferedImage imagem;
	private Pulo pulo;
	
	public Jogador(int x, int y){
		this.energia = 5;
		this.x = x;
		this.y = y;
		this.speed = 5;
		this.dir = 0;
		this.tiros = new ArrayList<Tiro>();
		this.spritesDireita = new SpriteAnimation();
		this.spritesEsquerda = new SpriteAnimation();
		try {
			this.spritesDireita = ImageManager.getInstance().loadSpriteAnimation(
					"br/com/lol/imagens/spriteDireita.png", 9);
			this.spritesEsquerda = ImageManager.getInstance().loadSpriteAnimation(
					"br/com/lol/imagens/spriteEsquerda.png", 9);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public Rectangle getBounds(int direcao){
		if(direcao > 0){
		return new Rectangle(x + 20 , y, 20, 60);
		}else
			return new Rectangle(x + 20 , y, 20, 60);
	}
	
	public void atirar(int dir, int currentTick){
		if(currentTick % 12 == 0){
		if(dir>0){
			tiros.add(new Tiro(this.x+78, this.y+28, dir));
		} else {
			tiros.add(new Tiro(this.x-6, this.y+28, dir));
		}
	}
	}
	
	public void pular(int chao){
		if(this.y > chao - Pulo.getAlturaMax()){
			pulo.start(y);
		}else
			pulo.decrementar(y);
	}
	
	public BufferedImage getImagem(){
		return this.imagem;
	}
	
	public void andar(int direcao){
		if(direcao > 0){
			spritesDireita.setLoop(true);
			this.imagem = spritesDireita.getImage();
		} else {
			spritesEsquerda.setLoop(true);
			this.imagem = spritesEsquerda.getImage();
		}
	}

	public List<Tiro> getTiros() {
		return tiros;
	}

	public void setTiros(List<Tiro> tiros) {
		this.tiros = tiros;
	}
}

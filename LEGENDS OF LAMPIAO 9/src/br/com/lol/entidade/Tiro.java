package br.com.lol.entidade;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;

import br.com.lol.gerenciadores.ImageManager;

public class Tiro extends Entidade{

	private BufferedImage tiro;
	private boolean visible;
	private int direcao;
	
	public Tiro(int x, int y, int direcao){
		this.x = x;
		this.y = y;
		this.energia = -1;
		this.speed = 10;
		this.setVisible(true);
		this.direcao = direcao;
		try {
			if(direcao>0){
				tiro = ImageManager.getInstance().loadImage("br/com/lol/imagens/bala_invertida.png");
			}else{
				tiro = ImageManager.getInstance().loadImage("br/com/lol/imagens/bala.png");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public Rectangle getBounds(){
		return new Rectangle(x, y, 9, 5);
	}
	public void mover(){
		if(direcao>0){
			this.x += speed;
			if(this.x>795)
				this.setVisible(false);
		}
		else{
			this.x -= speed;
			if(this.x<5){
				this.setVisible(false);				
			}
		}
	}

	public boolean isVisible() {
		return visible;
	}

	public void setVisible(boolean visible) {
		this.visible = visible;
	}

	public BufferedImage getImagem() {
		return this.tiro;
	}
}

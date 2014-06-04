package br.com.lol.entidade;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;

public class Projetil extends Entidade{

	private boolean visible;
	private int direcao;
	private BufferedImage imagem;
	
	public Projetil(int x, int y, int direcao, BufferedImage imagem){
		this.imagem = imagem;
		this.x = x +10;
		this.y = y + 125;
		this.energia = -1;
		this.speed = 7;
		this.setVisible(true);
		this.direcao = direcao;
	}
	
	public int getDirecao(){
		return this.direcao;
	}
	
	public void setImagem(BufferedImage img){
		this.imagem = img;
	}
	
	public BufferedImage getImagem(){
		return this.imagem;
	}
	
	public Rectangle getBounds(){
		return new Rectangle(x, y, 9, 5);
	}
	
	public void posicao(){
		if(direcao>0){
			if(this.x>795)
				this.setVisible(false);
		}
		else{
			if(this.x <5){
				this.setVisible(false);				
			}
		}
	}
	
	public void mover(){
		if(direcao>0){
			this.x += speed;
			if(this.x>795)
				this.setVisible(false);
		}
		else{
			this.x -= speed;
			if(this.x <5){
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
	
	public Rectangle getBounds1() {
		if(direcao>0)
			return new Rectangle(this.x,this.y-10,400, 100);
		else
			return new Rectangle(this.x-400,this.y-10,400, 100);
	}

}

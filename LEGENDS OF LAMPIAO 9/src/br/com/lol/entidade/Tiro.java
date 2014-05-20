package br.com.lol.entidade;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import br.com.lol.gerenciadores.ImageManager;
import br.com.lol.observador.Observer;
import br.com.lol.observador.Sujeito;

public class Tiro extends Entidade{

	private boolean visible;
	private boolean status;
	private int direcao;
	private BufferedImage imagem;
	
	public Tiro(int x, int y, int direcao, BufferedImage imagem){
		this.imagem = imagem;
		this.x = x +10;
		this.y = y + 125;
		this.energia = -1;
		this.speed = 2;
		this.setVisible(true);
		this.direcao = direcao;
	}
	
	public void setImagem(BufferedImage img){
		this.imagem = img;
	}
	
	public BufferedImage getImagem(){
		return this.imagem;
	}
	
	@Override
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
	
	private void setStatus(){
		this.status = true;
	}
	

}

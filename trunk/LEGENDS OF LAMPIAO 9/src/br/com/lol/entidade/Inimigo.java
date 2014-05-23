package br.com.lol.entidade;

import java.awt.image.BufferedImage;

public class Inimigo extends Personagem{
	
	protected String identificador;
	
	public Inimigo(int x, int y, int direcao){
		this.x = x;
		this.y = y;
		this.direcao = direcao;
		this.speed = 3;
		this.imagem = imagem;
	}
	
	public void seMexer(int currenTick){
		
	}
	
	protected BufferedImage imagem;
	
	public BufferedImage getImagem(){
		return this.imagem;
	}
	
	public String getIdentificador(){
		return this.identificador;
	}
	
	public void update(int currentTick){
		
	}
	
}

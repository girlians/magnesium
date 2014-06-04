package br.com.lol.entidade;

import java.awt.image.BufferedImage;

import br.com.lol.gerenciadores.SpriteAnimation;

public class Inimigo extends Personagem{
	
	protected String identificador;
	
	protected int marco0X;
	
	protected SpriteAnimation spriteDireita;
	protected SpriteAnimation spriteEsquerda;
	
	protected boolean visible;
	
	protected BufferedImage imagem;
	
	protected boolean radarLampiao;
	
	public Inimigo(int x, int y, int direcao){
		this.visible = true;
		this.radarLampiao = false;
		this.x = x;
		this.y = y;
		this.direcao = direcao;
		this.speed = 3;
	}
	
	public void seMexer(){
		
	}
	
	public void ativarRadar(){
		this.radarLampiao = true;
	}
	
	public BufferedImage getImagem(){
		return this.imagem;
	}
	
	public String getIdentificador(){
		return this.identificador;
	}
	
	public void update(int currentTick){
		
	}

	public SpriteAnimation getSpriteDireita() {
		return spriteDireita;
	}

	public void setSpriteDireita(SpriteAnimation spriteDireita) {
		this.spriteDireita = spriteDireita;
	}

	public SpriteAnimation getSpriteEsquerda() {
		return spriteEsquerda;
	}

	public void setSpriteEsquerda(SpriteAnimation spriteEsquerda) {
		this.spriteEsquerda = spriteEsquerda;
	}
	
	public boolean isVisible(){
		return this.visible;
	}

	public int getMarco0X() {
		return marco0X;
	}

	public void setMarco0X(int marco0x) {
		marco0X = marco0x;
	}
	
	
	
}

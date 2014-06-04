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
	protected static int CAINDO = 1;
	protected static int REPOUSO = 0;
	private int estadoNoAr;
	
	public Inimigo(int x, int y, int direcao){
		this.visible = true;
		this.radarLampiao = false;
		this.x = x;
		this.y = y;
		this.direcao = direcao;
		this.speed = 3;
		this.estadoNoAr = CAINDO;
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
	
	public void caindo(){
		if(getEstadoNoAr() == CAINDO){
			this.y += 5;
		}
	}

	public int getEstadoNoAr() {
		return estadoNoAr;
	}

	public void setEstadoNoAr(int estadoNoAr) {
		this.estadoNoAr = estadoNoAr;
	}
}

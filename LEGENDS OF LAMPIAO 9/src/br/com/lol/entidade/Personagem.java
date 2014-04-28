package br.com.lol.entidade;

import br.com.lol.gerenciadores.SpriteAnimation;

public class Personagem extends Entidade{

	protected int dir;
	protected SpriteAnimation spritesDireita;
	protected SpriteAnimation spritesEsquerda;

	public SpriteAnimation getSpritesEsquerda() {
		return spritesEsquerda;
	}

	public void setSpritesEsquerda(SpriteAnimation spritesEsquerda) {
		this.spritesEsquerda = spritesEsquerda;
	}

	public SpriteAnimation getSpritesDireita() {
		return spritesDireita;
	}

	public void setSpritesDireita(SpriteAnimation spritesDireita) {
		this.spritesDireita = spritesDireita;
	}

	public int getDir() {
		return dir;
	}

	public void setDir(int dir) {
		this.dir = dir;
	}
	
	public void andar(){
		
	}
	
}

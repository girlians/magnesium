package br.com.lol.entidade;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import br.com.lol.armas.Arma;
import br.com.lol.armas.Espingarda;
import br.com.lol.armas.UsaArma;
import br.com.lol.gerenciadores.ImageManager;
import br.com.lol.gerenciadores.SpriteAnimation;

public class Jogador extends Personagem{
	
	private List<Tiro> tiros;
	private BufferedImage imagem;
	private Pulo pulo;
	protected SpriteAnimation spritesDireita;
	protected SpriteAnimation spritesEsquerda;
	protected BufferedImage paradoDireita;
	protected BufferedImage paradoEsquerda;
	protected BufferedImage pulandoDireita;
	protected BufferedImage pulandoEsquerda;
	protected BufferedImage abaixado;
	private SpriteAnimation spritesVoandoDireita;
	private SpriteAnimation spritesVoandoEsquerda;
	private BufferedImage imgVoando;
	private boolean modoVoador;
	
	
	public boolean isModoVoador() {
		return modoVoador;
	}

	public SpriteAnimation getSpritesVoandoDireita() {
		return spritesVoandoDireita;
	}

	public void setSpritesVoandoDireita(SpriteAnimation spritesVoandoDireita) {
		this.spritesVoandoDireita = spritesVoandoDireita;
	}

	public SpriteAnimation getSpritesVoandoEsquerda() {
		return spritesVoandoEsquerda;
	}

	public void setSpritesVoandoEsquerda(SpriteAnimation spritesVoandoEsquerda) {
		this.spritesVoandoEsquerda = spritesVoandoEsquerda;
	}

	public Jogador(int x, int y) {
		this.energia = 5;
		this.x = x;
		this.y = y;
		this.speed = 5;

		this.estado = 4;
		this.estadoDoSalto = 0;
		this.direcao = 1;
		this.modoVoador = false;
		altura = 80;
		largura = 90;

		this.arma = new Espingarda(this);

		this.tiros = new ArrayList<Tiro>();
		this.spritesDireita = new SpriteAnimation();
		this.spritesEsquerda = new SpriteAnimation();
		this.spritesVoandoDireita = new SpriteAnimation();
		this.spritesVoandoEsquerda = new SpriteAnimation();
		try {
			this.spritesDireita = ImageManager.getInstance()
					.loadSpriteAnimation(
							"br/com/lol/imagens/spriteDireita1.png", 8);
			this.spritesEsquerda = ImageManager.getInstance()
					.loadSpriteAnimation(
							"br/com/lol/imagens/spriteEsquerda1.png", 8);
			this.pulandoDireita = ImageManager.getInstance().loadImage(
					"br/com/lol/imagens/lampia_pulando_normal.png");
			this.pulandoEsquerda = ImageManager.getInstance().loadImage(
					"br/com/lol/imagens/lampia_pulando_invertido.png");
			this.paradoEsquerda = ImageManager.getInstance().loadImage(
					"br/com/lol/imagens/lampiao_invertida.png");
			this.paradoDireita = ImageManager.getInstance().loadImage(
					"br/com/lol/imagens/lampiao_normal.png");
			this.spritesVoandoEsquerda = ImageManager
					.getInstance()
					.loadSpriteAnimation(
							"br/com/lol/imagens/spriteCarcaraAtt.png",
							8);
			this.spritesVoandoDireita = ImageManager
					.getInstance()
					.loadSpriteAnimation(
							"br/com/lol/imagens/spriteCompletoCarcaraDireita1.png",
							8);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/*public void atirar(int dir, int currentTick, AudioClip somTiro) {
		if (currentTick % 12 == 0) {
			somTiro.play();
			if (dir > 0) {
				tiros.add(new Tiro(this.x + 78, this.y + 28, dir));
			} else {
				tiros.add(new Tiro(this.x - 6, this.y + 28, dir));
			}
		}
	}*/

	public void pular(int chao){
		if(this.y > chao - Pulo.getAlturaMax()){
			pulo.start(y);
		}else
			pulo.decrementar(y);
	}
	
	public BufferedImage getImagem(){
		if(modoVoador==true)
			return this.imgVoando;
		if((this.estadoDoSalto==1)||this.estadoDoSalto==2)
			if(this.direcao == 1)
				return this.pulandoDireita;
			else
				return this.pulandoEsquerda;
		 else
			if(this.estado == 3)
				return this.imagem;
			else
				if(direcao == 1)
					return this.paradoDireita;
				else
					return this.paradoEsquerda;
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
	
	public void updateFly() {
		if (isModoVoador()) {
			if(direcao > 0){
				spritesVoandoDireita.setLoop(true);
				this.imgVoando = spritesVoandoDireita.getImage();
			} else {
				spritesVoandoEsquerda.setLoop(true);
				this.imgVoando = spritesVoandoEsquerda.getImage();
			}
		}
	}
	
	public void atirarTest(){
		arma.usar(this.direcao);
		new Thread(arma).start();
	}
	
	public void setArma(UsaArma arma){
		this.arma = arma;
	}

	public Arma getArma(){
		return (Arma)this.arma;
	}
	
	public List<Tiro> getTiros() {
		return tiros;
	}

	public void setTiros(List<Tiro> tiros) {
		this.tiros = tiros;
	}
	
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

	public void ativarModoVoador() {
		y = y - 35;
		this.altura = 125;
		this.largura = 135;
		this.modoVoador = true;
	}
	public void desativarModoVoador(){
		
		this.altura = 80;
		this.largura = 90;
		this.modoVoador = false;
	}
	
	public Rectangle getBounds() {
		return new Rectangle(this.x , this.y, this.largura, this.largura);
	}
}

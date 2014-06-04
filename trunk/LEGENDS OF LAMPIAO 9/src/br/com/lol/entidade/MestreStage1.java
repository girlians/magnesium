package br.com.lol.entidade;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import br.com.lol.IA.PuloIA;
import br.com.lol.armas.Arma;
import br.com.lol.armas.Faca;
import br.com.lol.gerenciadores.ImageManager;
import br.com.lol.gerenciadores.SpriteAnimation;

public class MestreStage1 extends Mestre {

	private Faca arma;
	private BufferedImage normal;
	private BufferedImage invertido;
	private boolean executando;
	private PuloIA pulo;
	private SpriteAnimation spriteDireita;
	private SpriteAnimation spriteEsquerda;
	
	public MestreStage1(int x, int y,int direcao,Jogador j) {
		super(x, y,direcao, j);
		this.executando = false;
		this.energia = 8;
		this.speed = 15;
		this.imagem = imagem;
		this.pulo = new PuloIA();
		this.pulo.setY(y);
		this.pulo.setX(x);
		this.pulo.setMestre(this);
		this.arma = new Faca(500, j, this.pulo.getX(), this.pulo.getY());
		
		try {
			this.spriteDireita = ImageManager.getInstance().loadSpriteAnimation(
					"br/com/lol/imagens/sprite_chefao_direita.png", 8);
			this.spriteEsquerda = ImageManager.getInstance().loadSpriteAnimation(
					"br/com/lol/imagens/sprite_chefao_esquerda.png", 8);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void atualizarImagem(){
		if(this.direcao >0){
			this.imagem = normal;
		}else{
			this.imagem = invertido;
		}
	}
	
	public BufferedImage getImagem(int dir){
		atualizarImagem();
		return this.imagem;
	}

	public void andar(int speedX) {
		this.pulo.setSpeedX(speedX);
		this.pulo.setStatus(true);
		new Thread(pulo).start();
		atualizarPosicao();
	}

	public void jump(int speedX, int speedY) {
		this.pulo.setSpeedX(speedX);
		this.pulo.setSpeedY(speedY);
		this.pulo.setStatusAndar(true);
		new Thread(pulo).start();
		atualizarPosicao();
	}

	public void jogarFacas() {
		this.arma.usar(this.direcao);
		//new Thread(arma).start();
	}
	
	public void atualizarPosicao(){
		if(getPulo().getX() >= 740){
			this.direcao = -1;
		}else if(getPulo().getX() <= 0){
			this.direcao = 1;
		}
	}

	public void runModeNormal() {
		int x = new Aleatorio(0, 5).sorteio();
		int y = new Aleatorio(0, 5).sorteio();
		//int numFacas = new Aleatorio(1, 1).sorteio();
		Random rnd = new Random();
		if (rnd.nextBoolean()) {
			andar(x);
			atualizarPosicao();
			System.out.println("ANDEI");
		} else {
			jump(x, y);
			System.out.println("PULEI");
			atualizarPosicao();
		}
		
		//if (rnd.nextBoolean()) {
			//for(int i = 0; i< numFacas; i++){
			//jogarFacas();
			//System.out.println("JOGUEI FACA");
			//}
		//}
		
	}

	private void runModeArretado() {
		int x = new Aleatorio(5, 10).sorteio();
		int y = new Aleatorio(5, 10).sorteio();
		int numFacas = new Aleatorio(1, 3).sorteio();
		Random rnd = new Random();
		if (rnd.nextBoolean()) {
			andar(x);
		} else {
			jump(x, y);
		}
		if (rnd.nextBoolean()) {
			for(int i = 0; i< numFacas; i++){
			jogarFacas();
			}
		}
		this.executando = false;
	}
	
	public void runIA(){
		if(!executando){
		this.executando = true;
		if(executando){
		if(this.energia > 0){
			if(this.energia > 5){
				runModeNormal();
			}else if(this.energia > 2){
				runModeArretado();
			}else if(this.energia >= 1){
				runModeCaoNosCoro();
			}
		}
		this.executando = false;
		atualizarPosicao();
		}
		}
	}

	private void runModeCaoNosCoro() {
		int x = new Aleatorio(10, 15).sorteio();
		int y = new Aleatorio(10, 15).sorteio();
		int numFacas = new Aleatorio(1, 5).sorteio();
		Random rnd = new Random();
		if (rnd.nextBoolean()) {
			andar(x);
		} else {
			jump(x, y);
		}
		if (rnd.nextBoolean()) {
			for(int i = 0; i< numFacas; i++){
			jogarFacas();
			}
		}
		this.executando = false;
	}

	public Faca getArma() {
		return this.arma;
	}

	public void setArma(Faca arma) {
		this.arma = arma;
	}

	public boolean isExecutando() {
		return executando;
	}

	public void setExecutando(boolean executando) {
		this.executando = executando;
	}

	public PuloIA getPulo() {
		return pulo;
	}

	public void setPulo(PuloIA pulo) {
		this.pulo = pulo;
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
	public void setImagem(BufferedImage imagem){
		this.imagem = imagem;
	}
}

class Aleatorio {

	private Random rnd;
	private int min;

	public Aleatorio(int min, int max) {
		this.rnd = new Random(max);
		this.min = min;
	}

	public int sorteio() {
		int i = rnd.nextInt();
		while (i > this.min) {
			return i;
		}
		return i;
	}

}

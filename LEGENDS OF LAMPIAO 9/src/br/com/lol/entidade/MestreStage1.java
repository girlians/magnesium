package br.com.lol.entidade;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import br.com.lol.armas.Arma;
import br.com.lol.armas.Faca;
import br.com.lol.gerenciadores.ImageManager;

public class MestreStage1 extends Mestre {

	private Faca arma;
	private BufferedImage normal;
	private BufferedImage invertido;
	private boolean executando;
	
	public MestreStage1(int x, int y, BufferedImage imagem, int direcao,
			Jogador j) {
		super(x, y, imagem, direcao, j);
		this.executando = false;
		this.energia = 8;
		this.speed = 5;
		this.arma = new Faca(500, j);
		this.imagem = imagem;
		try{
		this.normal =  ImageManager.getInstance().loadImage("br/com/lol/imagens/chefe1.png");
		this.invertido =  ImageManager.getInstance().loadImage("br/com/lol/imagens/chefe1_invertido.png");
		}catch(IOException e){
			e.getMessage();
		}
	}
	
	private void atualizarImagem(){
		if(this.direcao >0){
			this.imagem = normal;
		}else{
			this.imagem = invertido;
		}
	}
	
	public BufferedImage getImagem(){
		atualizarImagem();
		return this.imagem;
	}

	public void andar() {
		if (this.direcao > 0) {
			this.x += this.speed;
		} else {
			this.x -= this.speed;
		}
	}

	private void jump(int speedX, int speedY) {
		if (this.direcao > 0) {
			for (int i = 0; i < 10; i++) {
				this.x += speedX;
				this.y -= speedY;
			}
			for (int i = 0; i < 10; i++) {
				this.x += speedX;
				this.y += speedY;
			}
		} else {
			for (int i = 0; i < 10; i++) {
				this.x -= speedX;
				this.y -= speedY;
			}
			for (int i = 0; i < 10; i++) {
				this.x -= speedX;
				this.y += speedY;
			}
		}
	}

	private void jogarFacas() {
		this.arma.usar(this.direcao);
		new Thread(arma).start();
	}
	
	private void atualizarPosicao(){
		if(this.x >= 7500){
			this.direcao *= -1;
		}
		if(this.x <= 6000){
			this.direcao *= -1;
		}
	}

	private void runModeNormal() {
		int x = new Aleatorio(0, 5).sorteio();
		int y = new Aleatorio(0, 5).sorteio();
		//int numFacas = new Aleatorio(1, 1).sorteio();
		Random rnd = new Random();
		if (rnd.nextBoolean()) {
			andar();
			atualizarPosicao();
		} else {
			jump(x, y);
			atualizarPosicao();
		}
		if (rnd.nextBoolean()) {
			//for(int i = 0; i< numFacas; i++){
			jogarFacas();
			//}
		}
		this.executando = false;
	}
	
	

	private void runModeArretado() {
		int x = new Aleatorio(5, 10).sorteio();
		int y = new Aleatorio(5, 10).sorteio();
		int numFacas = new Aleatorio(1, 3).sorteio();
		Random rnd = new Random();
		if (rnd.nextBoolean()) {
			andar();
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
		}
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
		}
	}

	private void runModeCaoNosCoro() {
		int x = new Aleatorio(10, 15).sorteio();
		int y = new Aleatorio(10, 15).sorteio();
		int numFacas = new Aleatorio(1, 5).sorteio();
		Random rnd = new Random();
		if (rnd.nextBoolean()) {
			andar();
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
		return arma;
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

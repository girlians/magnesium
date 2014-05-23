package br.com.lol.entidade;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import br.com.lol.armas.Arma;
import br.com.lol.armas.Faca;

public class MestreStage1 extends Mestre {

	private List<Arma> facas;

	public MestreStage1(int x, int y, BufferedImage imagem, int direcao,
			Jogador j) {
		super(x, y, imagem, direcao, j);
		this.energia = 8;
		this.facas = new ArrayList<Arma>();
		this.speed = 5;
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
		} else {
			for (int i = 0; i < 10; i++) {
				this.x -= speedX;
				this.y -= speedY;
			}
		}
	}

	private void jogarFacas() {
		this.facas.add(new Faca(0, this.jogador));
	}

	private void runModeNormal() {
		int x = new Aleatorio(0, 5).sorteio();
		int y = new Aleatorio(0, 5).sorteio();
		//int numFacas = new Aleatorio(1, 1).sorteio();
		Random rnd = new Random();
		if (rnd.nextBoolean()) {
			andar();
		} else {
			jump(x, y);
		}
		if (rnd.nextBoolean()) {
			//for(int i = 0; i< numFacas; i++){
			jogarFacas();
			//}
		}
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

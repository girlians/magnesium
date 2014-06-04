package br.com.lol.entidade;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import br.com.lol.IA.Temporizador;
import br.com.lol.gerenciadores.ImageManager;

public class InimigoFraco extends Inimigo{
	
	private Thread threadIa;
	private Temporizador timer;
	
	public InimigoFraco(int x, int y, int direcao){
		super(x,y, direcao);
		this.marco0X = x;
		this.timer = new Temporizador(1000);
		this.threadIa = new Thread(timer);
		this.identificador = "fraco";
		this.energia = 1;
		this.speed = 1;
	}
	
	public void seMexer(){
		if(this.direcao > 0){
			this.x += this.speed;
			this.spriteDireita.setLoop(true);
			this.imagem = this.spriteDireita.getImage();
			if(this.threadIa.getState() == Thread.State.NEW){
				this.threadIa.start();
			}else if(this.threadIa.getState() == Thread.State.TERMINATED){
				this.threadIa = new Thread(timer);
				this.threadIa.start();
			}
			if(this.x > this.marco0X + 500){
				this.direcao = -1;
			}
		}else{
			this.x -= this.speed;
			this.spriteEsquerda.setLoop(true);
			this.imagem = this.spriteEsquerda.getImage();
			if(this.threadIa.getState() == Thread.State.NEW){
				this.threadIa.start();
			}else if(this.threadIa.getState() == Thread.State.TERMINATED){
				this.threadIa = new Thread(timer);
				this.threadIa.start();
			}
			if(this.x < this.marco0X + 10){
				this.direcao = 1;
			}
		}
	}
}

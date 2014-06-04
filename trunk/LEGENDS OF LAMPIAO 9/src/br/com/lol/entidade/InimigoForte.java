package br.com.lol.entidade;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.List;

import br.com.lol.IA.Temporizador;
import br.com.lol.gerenciadores.ImageManager;

public class InimigoForte extends Inimigo{
	
	private Thread threadIa;
	private Temporizador timer;
	
	public InimigoForte(int x, int y, int direcao){
		super(x, y, direcao);
		this.marco0X = x;
		this.x = x;
		this.y = y;
		this.timer = new Temporizador(500);
		this.threadIa = new Thread(timer);
		this.identificador = "forte";
		this.energia = 3;
		this.speed = 3;
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
			if(this.x > this.marco0X + 400){
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
	
	public Rectangle getBounds() {
		return new Rectangle(this.x , this.y+20, 60, 60);
	}
}

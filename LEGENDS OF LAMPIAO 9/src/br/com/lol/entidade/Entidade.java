package br.com.lol.entidade;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;

public class Entidade {

	protected int energia;
	protected int x;
	protected int y;
	protected int speed;
	public Rectangle pos;
	
	public Entidade(){
		//Não faz nada, mas precisa ter
	}
	
	public Entidade(int x, int y){
		pos = new Rectangle(x, y, 1, 1);
	}
	
	public int getX() {
		return this.x;
	}


	public void setX(int x) {
		this.x = x;
	}


	public int getY() {
		return this.y;
	}


	public void setY(int y) {
		this.y = y;
	}


	public int getSpeed() {
		return speed;
	}


	public void setSpeed(int speed) {
		this.speed = speed;
	}


	public Rectangle getPos() {
		return pos;
	}


	public void setPos(Rectangle pos) {
		this.pos = pos;
	}	
	public int getEnergia() {
		return energia;
	}
	public void setEnergia(int energia) {
		this.energia = energia;
	}

	public Rectangle getBounds() {
		return new Rectangle(this.x , this.y, 80, 80);
	}
	
	
}

package br.com.lol.entidade;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import br.com.lol.gerenciadores.ImageManager;
import br.com.lol.observador.Observer;
import br.com.lol.observador.Sujeito;

public class Tiro extends Entidade implements Sujeito{

	private BufferedImage tiro;
	private boolean visible;
	private boolean status;
	private int direcao;
	
	//INTEIRO PARA INTEREGIR COM OS OBSERVADORES
	private int iterator;
	private List<Observer> observers;
	
	public Tiro(int x, int y, int direcao){
		this.x = x;
		this.y = y;
		this.energia = -1;
		this.speed = 10;
		this.setVisible(true);
		this.direcao = direcao;;
		
		this.observers = new ArrayList<Observer>();
		try {
			if(direcao>0){
				tiro = ImageManager.getInstance().loadImage("br/com/lol/imagens/bala_invertida.png");
			}else{
				tiro = ImageManager.getInstance().loadImage("br/com/lol/imagens/bala.png");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public Rectangle getBounds(){
		return new Rectangle(x, y, 9, 5);
	}
	
	public void mover(){
		if(direcao>0){
			this.x += speed;
			if(this.x>795)
				this.setVisible(false);
		}
		else{
			this.x -= speed;
			if(this.x<5){
				this.setVisible(false);				
			}
		}
	}
	
	public void setChanges(int iterator){
		this.iterator = iterator;
		makeChanges();
	}

	public boolean isVisible() {
		return visible;
	}

	public void setVisible(boolean visible) {
		this.visible = visible;
	}

	public BufferedImage getImagem() {
		return this.tiro;
	}

	@Override
	public void addObserver(Observer obs) {
		this.observers.add(obs);
	}

	@Override
	public void removeObserver(Observer obs) {
		this.observers.remove(obs);
	}
	
	private void setStatus(){
		this.status = true;
	}
	
	@Override
	public void makeChanges(){
		setStatus();
		notifyObserver();
	}

	@Override
	public void notifyObserver() {
		if(status){
			for(Observer obs: observers){
				obs.update(this.iterator);
			}
			this.status = false;
		}
	}
}

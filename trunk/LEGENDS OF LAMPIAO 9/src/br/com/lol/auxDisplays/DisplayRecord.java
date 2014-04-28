package br.com.lol.auxDisplays;

import java.awt.Graphics2D;

import br.com.lol.entidade.Display;
import br.com.lol.observador.Observer;
import br.com.lol.observador.Sujeito;

public class DisplayRecord implements Observer, Display{

	private int score;
	private Sujeito sujeito;
	private boolean status;
	
	public DisplayRecord(Sujeito s, int recorde){
		this.score = recorde;
		this.sujeito = s;
		this.sujeito.addObserver(this);
		this.status = false;
	}
	
	@Override
	public void display(Graphics2D g) {
		g.drawString("SCORE "+this.score, 30, 40);
	}

	@Override
	public void update(boolean status, int iterator) {
		this.status = status;
		setChanges(iterator);
	}

	public void setChanges(int iterator) {
		if(status){
			this.score = iterator;
		}
	}
	
	
	public void setChanges(){
		
	}

}

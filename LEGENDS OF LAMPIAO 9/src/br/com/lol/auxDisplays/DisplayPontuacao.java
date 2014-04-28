package br.com.lol.auxDisplays;

import java.awt.Color;
import java.awt.Graphics2D;

import br.com.lol.entidade.Display;
import br.com.lol.observador.Observer;
import br.com.lol.observador.Sujeito;

public class DisplayPontuacao implements Observer, Display{

	private Sujeito sujeito;
	private int pontos;
	private int contador;
	private boolean status;
	
	public DisplayPontuacao(Sujeito s, int pontos){
		this.sujeito = s;
		this.pontos = pontos;
		this.status = false;
		this.sujeito.addObserver(this);
	}
	
	public void setChanges(){
		if(status){
			this.pontos += contador;
			status = false;
		}
	}
	
	@Override
	public void update(boolean status, int contador) {
		this.status = status;
		this.contador = contador;
		setChanges();
	}

	@Override
	public void display(Graphics2D g) {
		g.setColor(Color.RED );
		g.drawString("PONTOS", 650, 40);
		g.drawString("" + this.pontos, 705, 40);
	}
	
}

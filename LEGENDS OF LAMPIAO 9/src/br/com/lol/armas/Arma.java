package br.com.lol.armas;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import br.com.lol.entidade.Entidade;
import br.com.lol.entidade.Jogador;
import br.com.lol.entidade.Tiro;

public class Arma extends Entidade implements Runnable{

	protected int codigo;
	protected int tempo;
	private boolean acesso;
	BufferedImage imagemTiro;
	protected List<Tiro> balas;

	public Arma(int tempo, Jogador j){
		this.x = j.getX();
		this.y = j.getY();
		this.balas = new ArrayList<Tiro>();
		acesso = true;
		this.tempo = tempo;
	}
	
	public int getCodigo(){
		return this.codigo;
	}

	@Override
	public void run() {
		if(isAcesso()){
			setAcesso(false);
			try {
				Thread.sleep(tempo);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			setAcesso(true);
		}
	}

	public boolean isAcesso() {
		return acesso;
	}

	public void setAcesso(boolean acesso) {
		this.acesso = acesso;
	}
	
	public List<Tiro> getBalas(){
		return this.balas;
	}

	
}

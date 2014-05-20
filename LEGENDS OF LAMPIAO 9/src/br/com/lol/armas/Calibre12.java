package br.com.lol.armas;

import br.com.lol.entidade.Jogador;

public class Calibre12 extends Arma implements UsaArma{
	
	
	public Calibre12(Jogador j){
		super(1500, j);
		this.codigo = 2;
	}

	@Override
	public void usar(int direcao) {
		// TODO Auto-generated method stub
		
	}
	
}

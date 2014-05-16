package br.com.lol.armas;

public class Espingarda extends Arma implements UsaArma{

	@Override
	public void usar() {
		System.out.println("POW");
	}
	
	public Espingarda(){
		this.codigo = 0;
	}
	
}

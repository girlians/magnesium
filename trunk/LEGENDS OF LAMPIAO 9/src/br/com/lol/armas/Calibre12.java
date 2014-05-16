package br.com.lol.armas;

public class Calibre12 extends Arma implements UsaArma{

	@Override
	public void usar() {
		System.out.println("POW, POW, POW, POW");
	}
	
	public Calibre12(){
		this.codigo = 2;
	}
	
}

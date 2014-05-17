package br.com.lol.armas;

public class Calibre12 extends Arma implements UsaArma{
	
	@Override
	public void usar() {
		if(isAcesso()){
			System.out.println("POW, POW, POW, POW!");
		}   
	}
	
	public Calibre12(){
		super(1500);
		this.codigo = 2;
	}
	
}

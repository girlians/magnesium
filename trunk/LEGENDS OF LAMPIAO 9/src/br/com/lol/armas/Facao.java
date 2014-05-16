package br.com.lol.armas;

public class Facao extends Arma implements UsaArma{

	@Override
	public void usar() {
		System.out.println("SLASH");
	}
	
	public Facao(){
		this.codigo = 1;
	}

}

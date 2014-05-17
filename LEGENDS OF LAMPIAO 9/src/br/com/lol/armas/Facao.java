package br.com.lol.armas;

public class Facao extends Arma implements UsaArma{

	@Override
	public void usar() {
		System.out.println("SLASH");
	}
	
	public Facao(){
		super(1);
		this.codigo = 1;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		
	}

}

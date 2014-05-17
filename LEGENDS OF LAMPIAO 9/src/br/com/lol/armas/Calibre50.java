package br.com.lol.armas;

public class Calibre50 extends Arma implements UsaArma {

	@Override
	public void usar() {
		if(isAcesso())
			System.out.println("POW CALIBRE 50");		
	}
	
	public Calibre50(){
		super(1500);
		this.codigo = 3;
	}

}

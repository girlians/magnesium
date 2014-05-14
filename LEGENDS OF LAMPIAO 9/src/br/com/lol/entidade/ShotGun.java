package br.com.lol.entidade;

public class ShotGun implements UsaArma{

	private int codigo = 1;
	
	@Override
	public void usar() {
		System.out.println("POW, POW, POW, POW");
	}
	
	public int getCodigo(){
		return this.codigo;
	}
	
}

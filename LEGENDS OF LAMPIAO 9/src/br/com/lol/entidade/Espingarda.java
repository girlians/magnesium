package br.com.lol.entidade;

import br.com.lol.armas.UsaArma;

public class Espingarda implements UsaArma {

	@Override
	public void usar() {
		System.out.println("POW");
	}

}

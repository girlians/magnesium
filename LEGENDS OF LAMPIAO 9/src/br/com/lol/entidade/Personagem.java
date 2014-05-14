package br.com.lol.entidade;

public class Personagem extends Entidade{
	
	protected int estadoDoSalto;

	protected int direcao;

	protected UsaArma arma;
	
	public Personagem(){
		this.arma = new Espingarda();
	}
	
	public void atirarTest(){
		arma.usar();
	}
	
	public void setArma(UsaArma arma){
		this.arma = arma;
	}

	public int getDirecao() {
		return direcao;
	}

	public void setDirecao(int direcao) {
		this.direcao = direcao;
	}
	
	public void andar(){
		
	}

	public int getEstadoDoSalto() {
		return estadoDoSalto;
	}

	public void setEstadoDoSalto(int estadoDoSalto) {
		this.estadoDoSalto = estadoDoSalto;
	}
	
}

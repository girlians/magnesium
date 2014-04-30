package br.com.lol.entidade;

public class Personagem extends Entidade{
	
	protected int estadoDoSalto;

	protected int direcao;

	

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

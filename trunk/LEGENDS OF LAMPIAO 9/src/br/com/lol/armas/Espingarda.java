	package br.com.lol.armas;

import java.io.IOException;
import java.util.List;

import br.com.lol.entidade.Jogador;
import br.com.lol.entidade.Projetil;
import br.com.lol.gerenciadores.ImageManager;

public class Espingarda extends Arma implements UsaArma {

	//private boolean acesso;

	public void usar(int direcao) {
		if(isAcesso()){
			verificaImagemBala(direcao);
			if(direcao > 0){
			this.balas.add(new Projetil(this.x + 65, this.y - 95, direcao, this.imagemTiro));
			}else{
				this.balas.add(new Projetil(this.x - 6, this.y - 95, direcao, this.imagemTiro));
			}
		}
			
	}
	
	private void verificaImagemBala(int direcao){
		try{
		if(direcao > 0){
			this.imagemTiro = ImageManager.getInstance().loadImage("br/com/lol/imagens/bala_invertida.png");
		}else{
			this.imagemTiro = ImageManager.getInstance().loadImage("br/com/lol/imagens/bala.png");
		}
		}catch(IOException e){
			e.getMessage();
		}
	}
	
	public List<Projetil> getBalas(){
		return this.balas;
	}

	public Espingarda(Jogador j) {
		super(1000, j);
		this.codigo = 0;
	}

}

package br.com.lol.armas;

import java.io.IOException;
import java.util.List;

import br.com.lol.entidade.Jogador;
import br.com.lol.entidade.Projetil;
import br.com.lol.gerenciadores.ImageManager;

public class Faca extends Arma{
	
	public Faca(int tempo, Jogador j) {
		super(tempo, j);
	}
	
	public void usar(int direcao) {
		if(isAcesso()){
			verificaImagemBala(direcao);
			if(direcao > 0){
			this.balas.add(new Projetil(this.x + 65, this.y, direcao, this.imagemTiro));
			}else{
				this.balas.add(new Projetil(this.x - 6, this.y, direcao, this.imagemTiro));
			}
		}
			
	}
	
	private void verificaImagemBala(int direcao){
		try{
		if(direcao > 0){
			this.imagemTiro = ImageManager.getInstance().loadImage("br/com/lol/imagens/facaInvertida.png");
		}else{
			this.imagemTiro = ImageManager.getInstance().loadImage("br/com/lol/imagens/faca.png");
		}
		}catch(IOException e){
			e.getMessage();
		}
	}

}

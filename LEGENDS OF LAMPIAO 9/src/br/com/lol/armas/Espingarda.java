package br.com.lol.armas;

import java.io.IOException;
import java.util.List;

import br.com.lol.entidade.Jogador;
import br.com.lol.entidade.Tiro;
import br.com.lol.gerenciadores.ImageManager;

public class Espingarda extends Arma implements UsaArma {

	//private boolean acesso;

	public void usar(int direcao) {
		if(isAcesso()){
			verificaImagemBala(direcao);
			if(direcao > 0){
			this.balas.add(new Tiro(this.x + 78, this.y + 28, direcao, this.imagemTiro));
			}else{
				this.balas.add(new Tiro(this.x - 6, this.y + 28, direcao, this.imagemTiro));
			}
		}
			
	}
	
	private void verificaImagemBala(int direcao){
		try{
		if(direcao > 0){
			this.imagemTiro = ImageManager.getInstance().loadImage("br/com/lol/imagens/bala.png");
		}else{
			this.imagemTiro = ImageManager.getInstance().loadImage("br/com/lol/imagens/bala_invertida.png");
		}
		}catch(IOException e){
			e.getMessage();
		}
	}
	
	public List<Tiro> getBalas(){
		return this.balas;
	}

	public Espingarda(Jogador j) {
		super(1000, j);
		this.codigo = 0;
	}

	/*public boolean isAcesso() {
		return acesso;
	}
	public void setAcesso(boolean acesso){
		this.acesso = acesso;
	}

	@Override
	public void run() {
		if(isAcesso()){
			setAcesso(false);
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			setAcesso(true);
		}
	}*/

}

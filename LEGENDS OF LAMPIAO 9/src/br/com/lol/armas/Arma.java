package br.com.lol.armas;

public class Arma implements Runnable{

	protected int codigo;
	protected int tempo;
	private boolean acesso;

	public Arma(int tempo){
		acesso = true;
		this.tempo = tempo;
	}
	
	public int getCodigo(){
		return this.codigo;
	}

	@Override
	public void run() {
		if(isAcesso()){
			setAcesso(false);
			try {
				Thread.sleep(tempo);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			setAcesso(true);
		}
	}

	public boolean isAcesso() {
		return acesso;
	}

	public void setAcesso(boolean acesso) {
		this.acesso = acesso;
	}

	
}

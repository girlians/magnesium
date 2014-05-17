package br.com.lol.armas;

public class Espingarda extends Arma implements UsaArma {

	//private boolean acesso;

	public void usar() {
		if(isAcesso())
			System.out.println("POW");
	}

	public Espingarda() {
		super(1000);
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

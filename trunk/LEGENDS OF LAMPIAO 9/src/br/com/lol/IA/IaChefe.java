package br.com.lol.IA;

import java.util.Random;

import br.com.lol.entidade.MestreStage1;

public class IaChefe implements Runnable{
	
	private PuloIA pulo;
	private MestreStage1 mestre;
	private boolean status;
	
	public IaChefe(MestreStage1 mestre){
		this.mestre = mestre;
	}
	
	@Override
	public void run() {
		Random rnd = new Random();
		if(rnd.nextBoolean()){
			this.mestre.andar(10);
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		}else{
			this.mestre.jump(10, 10);
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		if(rnd.nextBoolean()){
			this.mestre.jogarFacas();
		}
	}

}


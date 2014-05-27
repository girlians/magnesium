package br.com.lol.IA;

import br.com.lol.entidade.Mestre;
import br.com.lol.entidade.MestreStage1;

public class PuloIA implements Runnable {
	
	private int x;
	private int y;
	private boolean status = false;
	private boolean statusAndar = false;
	private MestreStage1 mestre;
	private int speedX;
	private int speedY;
	
	public void run() {
		if (!this.isStatus()) {
			this.setStatus(true);
			if(this.mestre.getDirecao() > 0){
			for (int i = 0; i < 10; i++) {
				this.setY(this.getY() - this.speedY);
				this.setX(this.getX() + this.speedX);
				try {
					Thread.sleep(1000/30);
				} catch (InterruptedException e) {
					System.out.println("Erro na Thread");
					e.printStackTrace();
				}
			}
			for (int i = 0; i < 10; i++) {
				this.setY(this.getY() + this.speedY);
				this.setX(this.getX() + this.speedX);
				try {
					Thread.sleep(1000 / 30);
				} catch (InterruptedException e) {
					System.out.println("Erro na Thread");
					e.printStackTrace();
				}
			}
			this.setStatus(false);
			this.setStatusAndar(false);
		}else{
			for (int i = 0; i < 10; i++) {
				this.setY(this.getY() - this.speedY);
				this.setX(this.getX() - this.speedX);
				try {
					Thread.sleep(1000 / 30);
				} catch (InterruptedException e) {
					System.out.println("Erro na Thread");
					e.printStackTrace();
				}
			}
			for (int i = 0; i < 10; i++) {
				this.setY(this.getY() + this.speedY);
				this.setX(this.getX() - this.speedX);
				try {
					Thread.sleep(1000 / 30);
				} catch (InterruptedException e) {
					System.out.println("Erro na Thread");
					e.printStackTrace();
				}
			}
			this.setStatus(false);
			this.setStatusAndar(false);
		}
		}
		if(!this.statusAndar){
			this.statusAndar = true;
			if(this.mestre.getDirecao() > 0){
				for(int i = 0; i < 10; i++){
				this.setX(getX() + this.speedX);
				try {
					Thread.sleep(1000/30);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				}
				this.status = false;
				this.statusAndar = false;
			}else{
				for(int i = 0; i < 10; i ++){
				this.setX(getX() - this.speedX);
				try {
					Thread.sleep(1000/30);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				}
				this.statusAndar = false;
				this.status = false;
			}
			
		}
	}

	public Mestre getMestre() {
		return mestre;
	}


	public void setMestre(MestreStage1 mestre) {
		this.mestre = mestre;
	}


	public int getX() {
		return x;
	}


	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}
	
	public void setX(int x){
		this.x = x;
	}
	
	public void setSpeedX(int x){
		this.speedX = x;
	}
	
	public void setSpeedY(int y){
		this.speedY = y;
	}

	public void setStatusAndar(boolean valor){
		this.statusAndar = valor;
	}
	
}
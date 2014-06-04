package br.com.lol.sounds;

import java.applet.AudioClip;
import java.io.IOException;

import br.com.lol.gerenciadores.AudioManager;

public class SoundBilbe {
	
	private AudioClip tiro_doze;
	private AudioClip dor_lampiao;
	private AudioClip carcara_ativar;
	private AudioClip musica_tema;
	private AudioClip corvos;
	private AudioClip jogar_facas;
	
	public SoundBilbe(){
		AudioManager am = new AudioManager();
		try {
			this.tiro_doze = AudioManager.getInstance().loadAudio("br/com/lol/sounds/tiro_doze.wav");
			this.dor_lampiao = AudioManager.getInstance().loadAudio("br/com/lol/sounds/dor_lampiao.wav");
			this.carcara_ativar = AudioManager.getInstance().loadAudio("br/com/lol/sounds/carcara_ativar.wav");
			this.musica_tema = AudioManager.getInstance().loadAudio("br/com/lol/sounds/musica_tema.wav");
			this.jogar_facas = AudioManager.getInstance().loadAudio("br/com/lol/sounds/jogar_facas.wav");
			this.corvos = AudioManager.getInstance().loadAudio("br/com/lol/sounds/corvos.wav");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void playCorvo(){
		this.corvos.play();
	}
	
	public void playTiro(){
		this.tiro_doze.play();
	}
	
	public void playTema(){
		this.musica_tema.play();
	}
	public void playJogarFacas(){
		this.jogar_facas.play();
	}
	public void playCarcara(){
		this.carcara_ativar.play();
	}
	
	public void playDor(){
		this.dor_lampiao.play();
	}
	
	public void stopCorvo(){
		this.corvos.stop();
	}
	
	public void stopTiro(){
		this.tiro_doze.stop();
	}
	
	public void stopTema(){
		this.musica_tema.stop();
	}
	public void stopJogarFacas(){
		this.jogar_facas.stop();
	}
	public void stopCarcara(){
		this.carcara_ativar.stop();
	}
	public void stopDor(){
		this.dor_lampiao.stop();
	}
}

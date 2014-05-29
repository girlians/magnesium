package br.com.lol.auxDisplays;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

import com.sun.org.apache.bcel.internal.generic.AALOAD;

import br.com.lol.entidade.EntidadePlataforma;
import br.com.lol.gerenciadores.ImageManager;

public class DesenharPlataformas {
	
	private List<EntidadePlataforma> listaDePlataformas;
	private BufferedImage plataformaMeio;
	private BufferedImage plataformaEsquerda;
	private BufferedImage plataformaDireita;
	private BufferedImage plataformaLado;
	private BufferedImage plataformaPontaLado;
	private BufferedImage rachaoGrande;
	private BufferedImage rachaoVertical;
	private BufferedImage rachaoCima;
	private List<Integer> valoresRandomicos;
	
	public DesenharPlataformas(List<EntidadePlataforma> listaDePlataformas) {
		this.listaDePlataformas = listaDePlataformas;
		try {
			this.rachaoCima = ImageManager.getInstance().loadImage("br/com/lol/imagens/rachãoCima.png");
			this.rachaoGrande = ImageManager.getInstance().loadImage("br/com/lol/imagens/rachãoGrande.png");
			this.rachaoVertical = ImageManager.getInstance().loadImage("br/com/lol/imagens/rachãoVertical.png");
			this.plataformaPontaLado = ImageManager.getInstance().loadImage("br/com/lol/imagens/plataformaPontaLado.png");
			this.plataformaLado = ImageManager.getInstance().loadImage("br/com/lol/imagens/plataformaLado.png");		
			this.plataformaMeio = ImageManager.getInstance().loadImage("br/com/lol/imagens/plataformaMeio.png");
			this.plataformaEsquerda = ImageManager.getInstance().loadImage("br/com/lol/imagens/pontaEsquerda.png");
			this.plataformaDireita = ImageManager.getInstance().loadImage("br/com/lol/imagens/pontaDireita.png");
			
			valoresRandomicos = new ArrayList<>();
			for(EntidadePlataforma p : listaDePlataformas){
				valoresRandomicos.add(new Random().nextInt(p.getLargura()-10));
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void desenhar(Graphics2D g){
		int i = 0;
		for (EntidadePlataforma plataforma : listaDePlataformas) {
			Color c = new Color(51, 23, 11);
			g.setColor(c);
			g.fillRect(plataforma.getX(), plataforma.getY()+10, plataforma.getLargura()+28,600-plataforma.getY());
			g.drawImage(plataformaEsquerda, plataforma.getX()-20, plataforma.getY()-12, 30,plataforma.getAltura()+12, null);
			g.drawImage(plataformaDireita, plataforma.getX()+plataforma.getLargura(), plataforma.getY()-12, 30,plataforma.getAltura()+12, null);
			g.drawImage(plataformaMeio, plataforma.getX()+10, plataforma.getY()-12, plataforma.getLargura()-10, plataforma.getAltura()+12, null);
			g.drawImage(plataformaLado, plataforma.getX()-20, plataforma.getY()+10, 22, 600-plataforma.getY(),null);
			g.drawImage(plataformaPontaLado, plataforma.getX()-20, plataforma.getY()-14, 21, 58,null);
			i++;
		}
	}
}

package br.com.lol.gerenciadores;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;

import br.com.lol.entidade.Bau;
import br.com.lol.entidade.EntidadePlataforma;
import br.com.lol.entidade.Jogador;

public class CollisionDetector {
	
	private List<EntidadePlataforma> listaDeEntidades;
	private List<EntidadePlataforma> listaVerticalDireita;
	private List<EntidadePlataforma> listaVerticalEsquerda;

	public CollisionDetector(List<EntidadePlataforma> listaDePlataformas) {
		listaDeEntidades = listaDePlataformas;

		listaVerticalDireita = new ArrayList<>();
		listaVerticalEsquerda = new ArrayList<>();
		for (EntidadePlataforma entidadePlataforma : listaDePlataformas) {
			getListaVerticalEsquerda().add(new EntidadePlataforma(entidadePlataforma.getX(), entidadePlataforma.getY()+10, 10, 600-entidadePlataforma.getY()));
			getListaVerticalDireita().add(new EntidadePlataforma(entidadePlataforma.getX()+entidadePlataforma.getLargura()-10, entidadePlataforma.getY()+10, 10, 600-entidadePlataforma.getY()));
		}
		for (EntidadePlataforma entidadePlataforma : getListaVerticalDireita()) {
			entidadePlataforma.init();
		}
		for (EntidadePlataforma entidadePlataforma : getListaVerticalEsquerda()) {
			entidadePlataforma.init();
		}
	}

	public boolean colisaoPlataforma(Jogador personagem) {
		boolean colisao = false;
		for (int i = 0; i < listaDeEntidades.size(); i++) {
			colisao = listaDeEntidades.get(i).getBounds()
					.intersects(personagem.getBounds());
			if (colisao) {
				return true;
			}
		}
		return false;
	}
	
	public boolean colisaoPlataformaVerticalDireira(Jogador personagem){
		boolean colisao = false;
		for(EntidadePlataforma plataforma : listaVerticalDireita){
			colisao = plataforma.getBounds().intersects(personagem.getBounds());			
			if(colisao){
				return colisao;
			}
		}
		return colisao;
	}
	
	public boolean colisaoPlataformaVerticalEsquerda(Jogador personagem){
		boolean colisao = false;
		for(EntidadePlataforma plataforma : listaVerticalEsquerda){
			colisao = plataforma.getBounds().intersects(personagem.getBounds());
			if(colisao){				
				return colisao;
			}
		}
		return colisao;
	}
	
	public void desenharVertical(Graphics2D g){
		g.setColor(Color.lightGray);
		for (EntidadePlataforma vertical : getListaVerticalDireita()) {
			g.drawImage(vertical.getSprite(), vertical.getX(), vertical.getY(), null);
		}
		for (EntidadePlataforma vertical : getListaVerticalEsquerda()) {
			g.drawImage(vertical.getSprite(), vertical.getX(), vertical.getY(), null);
		}
	}
	
	public void colisaoBaus(Jogador jogador, List<Bau> baus){
		for (Bau bau : baus) {
			for (EntidadePlataforma entidade : listaDeEntidades) {
				if(!bau.getBounds().intersects(entidade.getBounds())){
					bau.caindo();
				} else {
					bau.setEstadoNoAr(2);
				}
			}
		}
		for (Bau bau : baus) {
			if(jogador.getBounds().intersects(bau.getBounds())){
				bau.alterarEstado();
			}
		}
	}

	public List<EntidadePlataforma> getListaDeEntidades() {
		return listaDeEntidades;
	}

	public void setListaDeEntidades(List<EntidadePlataforma> listaDeEntidades) {
		this.listaDeEntidades = listaDeEntidades;
	}

	public List<EntidadePlataforma> getListaVerticalDireita() {
		return listaVerticalDireita;
	}

	public void setListaVerticalDireita(List<EntidadePlataforma> listaVerticalDireita) {
		this.listaVerticalDireita = listaVerticalDireita;
	}

	public List<EntidadePlataforma> getListaVerticalEsquerda() {
		return listaVerticalEsquerda;
	}

	public void setListaVerticalEsquerda(List<EntidadePlataforma> listaVerticalEsquerda) {
		this.listaVerticalEsquerda = listaVerticalEsquerda;
	}
}

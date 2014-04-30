package br.com.lol.gerenciadores;

import java.util.ArrayList;
import java.util.List;

import br.com.lol.entidade.Entidade;
import br.com.lol.entidade.EntidadePlataforma;
import br.com.lol.entidade.Jogador;
import br.com.lol.entidade.Personagem;

public class CollisionDetector {
	
	private List<EntidadePlataforma> listaDeEntidades;

	public CollisionDetector(List<EntidadePlataforma> listaDePlataformas) {
		listaDeEntidades = listaDePlataformas;
	}

	public boolean colisaoPlataforma(Jogador personagem) {
		boolean colisao = false;
		for (int i = 0; i < listaDeEntidades.size(); i++) {
			colisao = listaDeEntidades.get(i).getBounds()
					.intersects(personagem.getBounds())
					&& ((personagem.getY()+80) == listaDeEntidades.get(i).getY());
			if (colisao) {
				return true;
			}
		}
		return false;
	}

	public List<EntidadePlataforma> getListaDeEntidades() {
		return listaDeEntidades;
	}

	public void setListaDeEntidades(List<EntidadePlataforma> listaDeEntidades) {
		this.listaDeEntidades = listaDeEntidades;
	}
}

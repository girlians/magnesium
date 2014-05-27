package br.com.lol.entidade;

import java.awt.image.BufferedImage;

public class Mestre extends InimigoForte {
	
	protected Jogador jogador;

	public Mestre(int x, int y, BufferedImage imagem, int direcao, Jogador j) {
		super(x, y, imagem, direcao);
		this.jogador = j;
	}
	
	public void atualizarPosicao(int x, int y, int direcao){
		this.x = x;
		this.y = y;
		this.direcao = direcao;
	}
	
	
}

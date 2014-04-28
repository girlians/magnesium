package br.com.lol.dao;

import java.io.IOException;

import br.com.lol.gerenciadores.DataManager;

public class DaoGame{
	
	private DataManager dm;
	
	public DaoGame(){
		try {
			this.dm = new DataManager("br/com/lol/files/save.txt");
		} catch (IOException e) {
			e.getMessage();
		}
	}
	
	public void salvar(int jogadorY, int jogadorX, int pontos, int tentativas){
		dm.write("jogadorX", jogadorX);
		dm.write("jogadorY", jogadorY);
		dm.write("pontos", pontos);
		dm.write("tentativas", tentativas);
	}
	
	public void load(int jogadorY, int jogadorX, int pontos, int tentativas){
		jogadorX = dm.read("jogadorX", jogadorX);
		jogadorY = dm.read("jogadorY", jogadorY);
		pontos = dm.read("pontos", pontos);
		tentativas = dm.read("tentativas", tentativas);
	}
	
}

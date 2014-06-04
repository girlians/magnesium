package br.com.lol.entidade;

import java.io.IOException;

import br.com.lol.gerenciadores.ImageManager;

public class CarecaZumbi extends InimigoFraco{

	public CarecaZumbi(int x, int y, int direcao) {
		super(x, y, direcao);
		try {
			this.spriteDireita = ImageManager.getInstance().loadSpriteAnimation(
					"br/com/lol/imagens/zombi_careca.png", 4);
			this.spriteEsquerda = ImageManager.getInstance().loadSpriteAnimation(
					"br/com/lol/imagens/zombi_careca_invertido.png", 4);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}

package br.com.lol.entidade;

import java.io.IOException;

import br.com.lol.gerenciadores.ImageManager;

public class VelhaZumbi extends InimigoForte{

	public VelhaZumbi(int x, int y, int direcao) {
		super(x, y, direcao);
		try {
			this.spriteDireita = ImageManager.getInstance().loadSpriteAnimation(
					"br/com/lol/imagens/zombi_arrastado.png", 4);
			this.spriteEsquerda = ImageManager.getInstance().loadSpriteAnimation(
					"br/com/lol/imagens/zombi_arrastado_invertido.png", 4);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}

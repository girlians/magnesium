package br.com.lol.auxDisplays;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Paint;
import java.awt.event.KeyEvent;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import br.com.lol.armas.Bazuca666;
import br.com.lol.armas.Calibre12;
import br.com.lol.armas.Calibre50;
import br.com.lol.armas.Espingarda;
import br.com.lol.armas.Facao;
import br.com.lol.armas.Revolver38;
import br.com.lol.armas.UsaArma;
import br.com.lol.entidade.Display;
import br.com.lol.entidade.Jogador;
import br.com.lol.gerenciadores.ImageManager;
import br.com.lol.gerenciadores.InputManager;
import br.com.lol.gerenciadores.SpriteAnimation;

public class Inventario implements Display{
	
	private boolean selecionarArmas;
	private boolean selecionarOpcoes;
	private boolean selecionarInventario;
	private boolean selecionandoUmaOpcao;
	private boolean active;
	
	private int[][] locaisArmas;
	
	private int x, y;
	private BufferedImage inventario;
	private BufferedImage opcoes;
	private BufferedImage selecionadorVermelho;
	private BufferedImage selecionadorArma;
	private SpriteAnimation escolhasOpcoes;
	private BufferedImage inventarioArmas;
	
	private int indiceImagemOpcoes;
	private int selecionadorY;
	
	private int localX;
	private int localY;
	
	private int proxArmaY;
	
	private Jogador jogador;
	
	private List<UsaArma> armas;
	private List<Boolean> armasDesenhar;
	private List<Integer> armasPegas;
	
	public Inventario(int x, int y, Jogador j){
	
		this.jogador = j;
		
		this.indiceImagemOpcoes = 0;
		this.selecionadorY = 128;
		
		this.selecionarArmas = false;
		this.selecionarOpcoes = false;
		this.selecionandoUmaOpcao = false;
		this.active = false;
		
		this.selecionarInventario = false;
		
		this.x = 300;
		this.y = 225;
		
		this.proxArmaY = y + 100; // pode ir até 450
		
		this.localY = 0;
		
		this.armasDesenhar = new ArrayList<Boolean>();
		this.armasPegas = new ArrayList<Integer>();
		this.armas = new ArrayList<UsaArma>();
		this.locaisArmas = new int[3][3];
		inicializarArmas();
		inicializaLocaisArmas();
		try {
			this.escolhasOpcoes = ImageManager.getInstance().loadSpriteAnimationVertical(
					"br/com/lol/imagens/inventario/escolha_opcoes.png", 5);
		
			
			this.inventario = ImageManager.getInstance().loadImage("br/com/lol/imagens/inventario/inventario.png");
			this.opcoes = ImageManager.getInstance().loadImage("br/com/lol/imagens/inventario/opções.png");
			this.selecionadorVermelho = ImageManager.getInstance().loadImage("br/com/lol/imagens/inventario/selecionador_vermelho.png");
			this.selecionadorArma = ImageManager.getInstance().loadImage("br/com/lol/imagens/inventario/selecionador_Amarelo.png");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void inicializaLocaisArmas(){
		for(int i = 0; i < 3; i++){
			for(int j = 0; j < 3; j++){
				this.locaisArmas[i][j] = -1;
			}
		}
		this.locaisArmas[0][0] = 0; // SÓ INICIALIZANDO A PRIMAIRA POSIÇÃO DA MATRIZ COM A ESPINGARDA
	}
	
	private void inicializarArmas(){
		
		for(int i = 0; i< 5; i++){
			this.armasDesenhar.add(false);
		}
		
		for(int i =0; i< 5; i++){
			this.armasPegas.add(-1);
		}
		
		this.armas.add(new Espingarda(this.jogador));
		this.armas.add(new Facao(this.jogador));
		this.armas.add(new Calibre12(this.jogador));
		this.armas.add(new Calibre50(this.jogador));
		this.armas.add(new Revolver38(this.jogador));
		this.armas.add(new Bazuca666(this.jogador));
	}
	
	public void addArmaAoInventario(int cod){
		boolean contem = false;
	
		this.armasDesenhar.set(cod, true);
		
		for(int i = 0; i < 3; i++){
			for(int j = 0; j<3 ; j++){
				if(this.locaisArmas[i][j] == cod)
					contem = true;
			}
		}
		if(!contem){
		this.locaisArmas[localY][localX] = cod;
		if(this.localX < 2){
			this.localX++;
		}else{
			this.localX = 0;
			if(this.localY < 2){
				this.localY++;
			}
		}
		}
	}
	
	private void runControleEscolhaArma(int currentTick, Graphics2D g){
		
	}
	
	private void runControleEscolhaOpcoes(int currentTick, Graphics2D g){
		int selecionadorX = 235;
		g.drawImage(this.escolhasOpcoes.getImagens().get(this.indiceImagemOpcoes), selecionadorX, this.selecionadorY, null);
		
		if(InputManager.getInstance().isPressed(KeyEvent.VK_DOWN)){
			if(currentTick % 10 == 0){
				if(this.selecionadorY < 300){
					this.selecionadorY += 82;
					this.indiceImagemOpcoes++;
				}
			}
		}
		if(InputManager.getInstance().isPressed(KeyEvent.VK_UP)){
			if(currentTick % 10 == 0){
				if(this.selecionadorY > 128){
					this.selecionadorY -= 82;
					this.indiceImagemOpcoes --;
				}
			}
		}
		if(InputManager.getInstance().isPressed(KeyEvent.VK_ESCAPE)){
			this.selecionandoUmaOpcao = false;
			this.selecionarOpcoes = true;
			this.indiceImagemOpcoes = 0;
		}
		}
	
	private void runControleInventario(int currentTick, Graphics2D g){
		if(InputManager.getInstance().isPressed(KeyEvent.VK_ENTER)){
			if(currentTick % 5 == 0){
				runControleEscolhaArma(currentTick, g);
			}
		}
		if(InputManager.getInstance().isPressed(KeyEvent.VK_ESCAPE)){
			if(currentTick % 5 == 0){
				this.selecionarInventario = false;
				this.active = false;
			}
		}
		if(InputManager.getInstance().isPressed(KeyEvent.VK_RIGHT)){
			this.selecionarInventario = false;
			this.selecionarOpcoes = true;
		}
	}

	
	public BufferedImage selecionarArma(int cod){
			return this.escolhasOpcoes.getImagens().get(cod);
	}
	
	
		/*if(InputManager.getInstance().isPressed(KeyEvent.VK_ENTER)){
			int placeY = (this.selecionador2X - 400) / 100;
			int placeX = (this.selecionador2Y - 300) / 25;
			int arma = this.locaisArmas[placeY][placeX];
			if(arma >= 0){
			jogador.setArma(this.armas.get(arma));
			}*/
	
	public void runControleOpcoes(int currentTick,Graphics2D g){
		if(InputManager.getInstance().isPressed(KeyEvent.VK_ENTER)){
			if(currentTick % 5 == 0){
				this.selecionandoUmaOpcao = true;
			}
		}
		if(InputManager.getInstance().isPressed(KeyEvent.VK_ESCAPE)){
			if(currentTick % 5 == 0){
				this.selecionarOpcoes = false;
				this.active = false;
			}
		}
		if(InputManager.getInstance().isPressed(KeyEvent.VK_LEFT)){
			if(currentTick % 5 == 0){
				this.selecionarOpcoes = false;
				this.selecionarInventario = true;
			}
		}
	}
	
	private void runSair(){
		this.selecionarOpcoes = false;
	}
	
	public void displayInventarioArmas(Graphics2D g){
		
		/*for(int i = 0; i< armasPegas.size(); i++){
			if(armasPegas.get(i) >= 0){
				g.drawImage(selecionarArma(this.armasPegas.get(i)), proxArmaX, proxArmaY, null);
				if(this.proxArmaY < 450){
					this.proxArmaY += 25;
				}else{
					this.proxArmaY = 375;
					if(this.proxArmaX < 575){
						this.proxArmaX += 100;
					}
				}
			}
		}*/
		this.proxArmaY = 400;
	}

	public void displayInventario(Graphics2D g, int currentTick) {
		g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1));
		if(this.selecionarInventario){
			this.selecionarOpcoes = false;
		g.drawImage(this.inventario, 0, 0, null);
		runControleInventario(currentTick, g);
		}
		if(this.selecionarOpcoes){
			this.selecionarInventario = false;
			g.drawImage(this.opcoes, 0, 0, null);
			if(!selecionandoUmaOpcao){
			runControleOpcoes(currentTick, g);
			}else{
				runControleEscolhaOpcoes(currentTick, g);
			}
		}
	}
	
	
	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public boolean isSelecionarArmas() {
		return selecionarArmas;
	}

	public void setSelecionarArmas(boolean selecionarArmas) {
		this.selecionarArmas = selecionarArmas;
	}

	public boolean isSelecionarOpcoes() {
		return selecionarOpcoes;
	}

	public void setSelecionarOpcoes(boolean selecionarOpcoes) {
		this.selecionarOpcoes = selecionarOpcoes;
	}

	public int getProxArmaY() {
		return proxArmaY;
	}

	public void setProxArmaY(int proxArmaY) {
		this.proxArmaY = proxArmaY;
	}

	@Override
	public void display(Graphics2D g) {
		// TODO Auto-generated method stub
		
	}

	public boolean isSelecionarInventario() {
		return selecionarInventario;
	}

	public void setSelecionarInventario(boolean selecionarInventario) {
		this.selecionarInventario = selecionarInventario;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

}

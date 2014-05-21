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
	
	private int[][] locaisArmas;
	
	private int x, y;
	private BufferedImage imagem;
	private BufferedImage selecionadorVermelho;
	private BufferedImage selecionadorArma;
	private SpriteAnimation escolhasOpcoes;
	private BufferedImage inventarioArmas;
	
	private int selecionadorX;
	private int selecionadorY;
	private int selecionador2X;
	private int selecionador2Y;
	
	private int localX;
	private int localY;
	
	private int proxArmaX;
	private int proxArmaY;
	private int contador;
	
	private Jogador jogador;
	
	private List<UsaArma> armas;
	private List<Boolean> armasDesenhar;
	private List<Integer> armasPegas;
	
	public Inventario(int x, int y, Jogador j){
		
		this.contador = 0;
		this.jogador = j;
		
		this.selecionarArmas = false;
		this.selecionarOpcoes = false;
		
		this.selecionarInventario = false;
		
		this.x = 300;
		this.y = 225;
		this.selecionadorX = x;
		this.selecionadorY = y;
		
		this.selecionador2X = x;
		this.selecionador2Y = y;
		
		this.proxArmaX = x - 25; // pode ir até 575
		this.proxArmaY = y + 100; // pode ir até 450
		
		this.localX = 1;
		this.localY = 0;
		
		this.armasDesenhar = new ArrayList<Boolean>();
		this.armasPegas = new ArrayList<Integer>();
		this.armas = new ArrayList<UsaArma>();
		this.locaisArmas = new int[3][3];
		inicializarArmas();
		inicializaLocaisArmas();
		try {
			this.escolhasOpcoes = ImageManager.getInstance().loadSpriteAnimationVertical(
					"br/com/lol/imagens/inventario/escolha_opções.png", 5);
		
			
			this.imagem = ImageManager.getInstance().loadImage("br/com/lol/imagens/inventario/inventario.png");
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
	
		this.armasPegas.set(this.contador, cod);
		this.contador++;
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
	
	public void runControleInventario(int currentTick, Jogador jogador){
		
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
	
	public void controleSelecaoOpcao(int currentTick){
		if(!isSelecionarArmas()){
		if(InputManager.getInstance().isPressed(KeyEvent.VK_RIGHT)){
			
		}
		if(InputManager.getInstance().isPressed(KeyEvent.VK_LEFT)){
			
		}
		if(InputManager.getInstance().isPressed(KeyEvent.VK_U)){
			this.selecionarOpcoes = false;
		}
		if(InputManager.getInstance().isTyped(KeyEvent.VK_ENTER)){
			
		}
		}
	}
	private void controlePosicaoPonteiro(){
		if(this.selecionadorX == 640){
			runSair();
		}
		if(this.selecionadorX == 400){
			this.selecionarArmas = true;
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
		this.proxArmaX = 375;
		this.proxArmaY = 400;
	}

	public void displayInventario(Graphics2D g, int currentTick) {
		g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1));
		g.drawImage(this.imagem, 0, 0, null);
		runControleInventario(currentTick, this.jogador);
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

	public int getSelecionadorX() {
		return selecionadorX;
	}

	public void setSelecionadorX(int selecionadorX) {
		this.selecionadorX = selecionadorX;
	}

	public int getSelecionadorY() {
		return selecionadorY;
	}

	public void setSelecionadorY(int selecionadorY) {
		this.selecionadorY = selecionadorY;
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

	public int getProxArmaX() {
		return proxArmaX;
	}

	public void setProxArmaX(int proxArmaX) {
		this.proxArmaX = proxArmaX;
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

}

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

	static private final int ESPINGARDA = 0;
	static private final int FACAO = 1;
	static private final int CALIBRE12 = 2;
	static private final int CALIBRE50 = 3;
	static private final int REVOLVER38 = 4;
	static private final int BAZUCA666 = 5;
	
	private boolean desenharInventarioArmas;
	private boolean selecionarArmas;
	private boolean selecionarOpcoes;
	
	private int[][] locaisArmas;
	
	private int x, y;
	private BufferedImage imagem;
	private BufferedImage selecionador;
	private BufferedImage selecionador2;
	private SpriteAnimation armasInventario;
	private BufferedImage inventarioArmas;
	private int selecionadorX;
	private int selecionadorY;
	private int selecionador2X;
	private int selecionador2Y;
	
	private int localX;
	private int localY;
	
	private int proxArmaX;
	private int proxArmaY;
	
	private Jogador jogador;
	
	private ArrayList<UsaArma> armas;
	
	private ArrayList<Boolean> armasPegas;
	
	public Inventario(int x, int y, Jogador j){
		
		this.jogador = j;
		
		this.selecionarArmas = false;
		this.selecionarOpcoes = false;
		this.desenharInventarioArmas = false;
		
		this.x = 300;
		this.y = 225;
		this.selecionadorX = x;
		this.selecionadorY = y;
		
		this.selecionador2X = x;
		this.selecionador2Y = y;
		
		this.proxArmaX = x - 25; // pode ir até 270
		this.proxArmaY = y + 100; // pode ir até 215
		
		this.localX = 1;
		this.localY = 0;
		
		this.armasPegas = new ArrayList<Boolean>();
		this.armas = new ArrayList<UsaArma>();
		this.locaisArmas = new int[3][3];
		inicializarArmas();
		inicializaLocaisArmas();
		try {
			this.armasInventario = ImageManager.getInstance().loadSpriteAnimationVertical(
					"br/com/lol/imagens/Armas - inventario.png", 5);
		
			this.inventarioArmas = ImageManager.getInstance().loadImage("br/com/lol/imagens/inventario-armas.png");
			this.imagem = ImageManager.getInstance().loadImage("br/com/lol/imagens/inventário.png");
			this.selecionador = ImageManager.getInstance().loadImage("br/com/lol/imagens/selecionador.png");
			this.selecionador2 = ImageManager.getInstance().loadImage("br/com/lol/imagens/selecionador2.png");
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
		for(int i = 0; i < 6; i++){
			armasPegas.add(false);
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
		this.armasPegas.set(cod, true);
		
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

	
	public BufferedImage selecionarArma(int cod){
			return this.armasInventario.getImagens().get(cod);
	}
	
	public void andarSelecionador2Esquerda(int currentTick){
		if(currentTick % 5 == 0){
			if(this.selecionador2X > this.x + 170){
				this.selecionador2X -= 100;
			}
		}
	}
	
	public void andarSelecionador2Direita(int currentTick){
		if(currentTick % 5 == 0){
			if(this.selecionador2X < this.x + 270){
				this.selecionador2X += 100;
			}
		}
	}
	
	public void andarSelecionador2Baixo(int currentTick){
		if(currentTick % 5 == 0){
			if(this.selecionador2Y < this.y + 120){
				this.selecionador2Y += 25;
			}
		}
	}
	
	public void andarSelecionador2Cima(int currentTick){
		if(currentTick % 5 == 0){
			if(this.selecionador2Y > this.y + 95){
				this.selecionador2Y -= 25;
			}
		}
	}
	
	public void andarSelecionadorEsquerda(int currentTick){
		if(currentTick % 5 == 0){
			if(this.selecionadorX > this.x + 100){
				this.selecionadorX -= 80;
			}
		}
	}
	
	public void andarSelecionadorDireita(int currentTick){
		if(currentTick % 5 == 0){
			if(this.selecionadorX < this.x + 320){
				this.selecionadorX += 80;
			}
		}
	}
	
	public void controleSelecaoArmas(int currentTick, Jogador jogador){
		if(InputManager.getInstance().isPressed(KeyEvent.VK_RIGHT)){
			andarSelecionador2Direita(currentTick);
		}
		if(InputManager.getInstance().isPressed(KeyEvent.VK_DOWN)){
			andarSelecionador2Baixo(currentTick);
		}
		if(InputManager.getInstance().isPressed(KeyEvent.VK_UP)){
			andarSelecionador2Cima(currentTick);
		}
		if(InputManager.getInstance().isPressed(KeyEvent.VK_LEFT)){
			andarSelecionador2Esquerda(currentTick);
		}
		if(InputManager.getInstance().isPressed(KeyEvent.VK_ESCAPE)){
			this.selecionarArmas = false;
		}
		if(InputManager.getInstance().isPressed(KeyEvent.VK_ENTER)){
			int placeY = (this.selecionador2X - 400) / 100;
			int placeX = (this.selecionador2Y - 300) / 25;
			int arma = this.locaisArmas[placeY][placeX];
			if(arma >= 0){
			jogador.setArma(this.armas.get(arma));
			}
		}		
	}
	
	public void controleSelecaoOpcao(int currentTick){
		if(!isSelecionarArmas()){
		if(InputManager.getInstance().isPressed(KeyEvent.VK_RIGHT)){
			andarSelecionadorDireita(currentTick);
		}
		if(InputManager.getInstance().isPressed(KeyEvent.VK_LEFT)){
			andarSelecionadorEsquerda(currentTick);
		}
		if(InputManager.getInstance().isPressed(KeyEvent.VK_U)){
			this.selecionarOpcoes = false;
		}
		if(InputManager.getInstance().isTyped(KeyEvent.VK_ENTER)){
			controlePosicaoPonteiro();
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
		g.drawImage(this.inventarioArmas, this.x + 65, this.y + 140, null);
		g.drawImage(this.selecionador2, this.selecionador2X - 30, this.selecionador2Y + 70, null);
		g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1));
		for(int i = 0; i< armasPegas.size(); i++){
			if(armasPegas.get(i)){
				g.drawImage(selecionarArma(i), proxArmaX, proxArmaY, null);
			}
		}
	}
	
	@Override
	public void display(Graphics2D g) {
		g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1));
		g.drawImage(this.imagem, this.x, this.y, null);
		g.drawImage(this.selecionador, this.selecionadorX - 25 , this.selecionadorY - 30, null);
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

}

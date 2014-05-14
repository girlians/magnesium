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

import br.com.lol.entidade.Display;
import br.com.lol.gerenciadores.ImageManager;
import br.com.lol.gerenciadores.InputManager;
import br.com.lol.gerenciadores.SpriteAnimation;

public class Inventario implements Display{

	static private final int FACAO = 0;
	static private final int CALIBRE12 = 1;
	static private final int CALIBRE50 = 2;
	static private final int REVOLVER38 = 3;
	static private final int BAZUCA666 = 4;
	
	private boolean desenharInventarioArmas;
	private boolean selecionarArmas;
	private boolean selecionarOpcoes;
	
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
	
	private int proxArmaX;
	private int proxArmaY;
	
	private ArrayList<Boolean> armasPegas;
	
	public Inventario(int x, int y){
		
		this.selecionarArmas = false;
		this.selecionarOpcoes = false;
		this.desenharInventarioArmas = false;
		
		this.x = x;
		this.y = y;
		this.selecionadorX = x;
		this.selecionadorY = y;
		
		this.selecionador2X = x;
		this.selecionador2Y = y;
		
		this.proxArmaX = this.x - 10; // pode ir até 270
		this.proxArmaY = this.y + 95; // pode ir até 215
		
		this.armasPegas = new ArrayList<Boolean>();
		inicializarArmas();
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
	
	private void inicializarArmas(){
		for(int i = 0; i < 5; i++){
			armasPegas.add(false);
		}
	}
	
	public void addArmaAoInventario(int cod){
		this.armasPegas.set(cod, true);
	}

	public void update(int x, int y){
		this.x = x - 80;
		this.y = y - 80;
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
			if(this.selecionadorX > this.x + 80){
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
	
	public void controleSelecaoArmas(int currentTick){
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
		if(this.selecionadorX == this.x + 320){
			runSair();
		}
		if(this.selecionadorX == this.x + 80){
			this.selecionarArmas = true;
		}
	}
	
	private void runSair(){
		this.selecionarOpcoes = false;
	}
	
	public void displayInventarioArmas(Graphics2D g){
		g.drawImage(this.inventarioArmas, this.x + 65, this.y + 140, null);
		g.drawImage(this.selecionador2, this.selecionador2X - 10, this.selecionador2Y + 65, null);
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
		g.drawImage(this.selecionador, this.selecionadorX , this.selecionadorY - 35, null);
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

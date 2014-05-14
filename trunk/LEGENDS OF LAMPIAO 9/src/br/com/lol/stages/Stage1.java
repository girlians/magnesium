package br.com.lol.stages;

import java.applet.AudioClip;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import br.com.lol.auxDisplays.DisplayPontuacao;
import br.com.lol.core.Game;
import br.com.lol.dao.DaoGame;
import br.com.lol.entidade.EntidadePlataforma;
import br.com.lol.entidade.Inimigo;
import br.com.lol.entidade.InimigoForte;
import br.com.lol.entidade.InimigoFraco;
import br.com.lol.entidade.Jogador;
import br.com.lol.entidade.Tiro;
import br.com.lol.gerenciadores.AudioManager;
import br.com.lol.gerenciadores.ImageManager;
import br.com.lol.gerenciadores.InputManager;

public class Stage1 extends Game{
	
	private static final int EST_FINAL = -1;				
	private static final int EST_PARADO = 0;
	private static final int EST_CAINDO = 1;
	private static final int EST_PULANDO = 2;
	private static final int EST_ANDANDO_DIREITA = 3;
	private static final int EST_ANDANDO_ESQUERDA = 4;
	private static final int EST_REPOUSO = 6;
	private static final int ALT_MAX = 100;
	private static final int EST_ATIRANDO = 7;
	private static final int EST_NAO_ATIRANDO = 8;
	private static final int EST_ABAIXADO = 10;
	
	//OBSERVADOR E SUJEITO PARA PLACAR
	private DisplayPontuacao displayP;
	private Tiro balistica;
	
	private DaoGame dao;
	
	// declaração das imagens do stagio
	// Olá pião
	private BufferedImage cenario;
	private BufferedImage lampiao_normal;
	private BufferedImage lampiao_invertido;
	private BufferedImage lampiao_abaixado_normal;
	private BufferedImage lampiao_abaixado_invertido;
	private BufferedImage lampiao_pulando_normal;
	private BufferedImage lampiao_pulando_invertido;
	
	//declaração das imagens dos inimigos
	private BufferedImage zumbi_forte_normal;
	private BufferedImage zumbi_forte_invertido;
	private BufferedImage zumbi_fraco_normal;
	private BufferedImage zumbi_fraco_invertido;
	private BufferedImage gameOver;
	
	private AudioClip somTiro;
	private AudioClip somPasso;
	private AudioClip somZumbiMorto;
	private AudioClip somZumbiVivo;
	private AudioClip somGameOver;
	
	private Jogador jogador;
	private List<Inimigo> inimigos;
	private int estadoSalto;
	private int estadoAtirando;
	private int direcao;
	private int chao;
	private boolean estaVivo = true;
	
	//VARIAVEIS DE PLATAFORMAS
	private EntidadePlataforma p1;
	private EntidadePlataforma p2;
	private EntidadePlataforma p3;
	
	
	
	private void inicializarSons(){
		try{
			this.somTiro = AudioManager.getInstance().loadAudio("br/com/lol/sounds/FireGun.wav");
			this.somPasso = AudioManager.getInstance().loadAudio("br/com/lol/sounds/step.wav");
			this.somZumbiMorto = AudioManager.getInstance().loadAudio("br/com/lol/sounds/zombiePain.wav");
			this.somZumbiVivo = AudioManager.getInstance().loadAudio("br/com/lol/sounds/zombieAlive.wav");
			this.somGameOver = AudioManager.getInstance().loadAudio("br/com/lol/sounds/gameOver.wav");
		}catch(IOException e){
			System.out.println("Não foi encontrado o som!");
		}
	}
	
	private void inicializarImagens(){
		try{
			//instanciando as imagens do cenario e do personagem
			this.gameOver = ImageManager.getInstance().
					loadImage("br/com/lol/imagens/fim_de_jogo.png");
			this.cenario = ImageManager.getInstance().
					loadImage("br/com/lol/imagens/campo.png");
			this.lampiao_invertido = ImageManager.getInstance().
					loadImage("br/com/lol/imagens/lampiao_invertida.png");
			this.lampiao_normal = ImageManager.getInstance().
					loadImage("br/com/lol/imagens/lampiao_normal.png");
			this.lampiao_pulando_normal = ImageManager.getInstance().
					loadImage("br/com/lol/imagens/lampia_pulando_normal.png");
			this.lampiao_abaixado_normal = ImageManager.getInstance().
					loadImage("br/com/lol/imagens/lampião_abaixado_normal1.png");
			this.lampiao_pulando_invertido = ImageManager.getInstance().
					loadImage("br/com/lol/imagens/lampia_pulando_invertido.png");
			this.lampiao_abaixado_invertido = ImageManager.getInstance().
					loadImage("br/com/lol/imagens/lampião_abaixado_invertido1.png");
			this.direcao = 1;
			
			this.zumbi_forte_normal = ImageManager.getInstance()
					.loadImage("br/com/lol/imagens/zumbis/sprite_zumbi_verde_normal1.png");
			this.zumbi_forte_invertido = ImageManager.getInstance()
					.loadImage("br/com/lol/imagens/zumbis/sprite_zumbi_verde_invertido1.png");
			this.zumbi_fraco_normal = ImageManager.getInstance()
					.loadImage("br/com/lol/imagens/zumbis/sprite_zumbi_roxo_normal1.png");
			this.zumbi_fraco_invertido = ImageManager.getInstance()
					.loadImage("br/com/lol/imagens/zumbis/sprite_zumbi_roxo_invertido1.png");
			
			}catch(IOException e){
				System.err.println(e.getMessage());
			}
	}
	
	private void inicializarPlataformas(){
		this.p1 = new EntidadePlataforma(100, 450, 150, 25);
		this.p2 = new EntidadePlataforma(225, 520, 80, 25);
		this.p3 = new EntidadePlataforma(350, 450, 150, 25);
		
		p1.init();
		p2.init();
		p3.init();
	}

	public void onLoad() {
		
		this.dao = new DaoGame();
		this.estaVivo = true;        
		this.tentativas = 1;
		
		this.balistica = new Tiro(0, 0, 0);
		this.displayP = new DisplayPontuacao(balistica, 0);
		
		
		this.score = 23300;
		this.inimigos = new ArrayList<Inimigo>();
		jogador = new Jogador(100,this.getHeight() - 100);
		p1 = new EntidadePlataforma(700, this.getHeight() - 100, 80, 80);
		this.estado = EST_PARADO;
		this.estadoSalto = EST_REPOUSO;
		this.chao = 500;
		
		//INICIALIZANDO AS PLATAFORMAS
		inicializarPlataformas();
		//instanciando as imagens
		inicializarImagens();
		//INICIALIZANDO OS SONS
		inicializarSons();
		somZumbiVivo.loop();
	}
	
	protected void runControleJogo(){
		if(InputManager.getInstance().isPressed(KeyEvent.VK_ESCAPE)){
			somZumbiVivo.stop();
			terminate();
		}
		if(InputManager.getInstance().isPressed(KeyEvent.VK_UP)){
			if(!(estadoSalto == EST_CAINDO))
				this.estadoSalto = EST_PULANDO;
		}
		if(InputManager.getInstance().isPressed(KeyEvent.VK_RIGHT)){
			this.estado = EST_ANDANDO_DIREITA;
		}
		if(InputManager.getInstance().isPressed(KeyEvent.VK_LEFT)){
			this.estado = EST_ANDANDO_ESQUERDA;
		}
		if(InputManager.getInstance().isPressed(KeyEvent.VK_SPACE)){
			estadoAtirando = EST_ATIRANDO;
		} else {
			estadoAtirando = EST_NAO_ATIRANDO;
		}
		if(InputManager.getInstance().isPressed(KeyEvent.VK_DOWN)){
			estado = EST_ABAIXADO;
		}
		if(InputManager.getInstance().isPressed(KeyEvent.VK_S)){
			dao.salvar(jogador.getY(), jogador.getX(), pontos, tentativas);
		}
		if(InputManager.getInstance().isPressed(KeyEvent.VK_L)){
			dao.load(jogador.getY(), jogador.getX(), pontos, tentativas);
		}
	}
	
	private void runEstPulando(){
		if (jogador.getY() > chao - ALT_MAX) {
			jogador.setY((int)(jogador.getY() - jogador.getSpeed()));
			if (InputManager.getInstance().isReleased(KeyEvent.VK_UP)) {
				estadoSalto = EST_CAINDO;
			}
		} else {
			estadoSalto = EST_CAINDO;
		}
	}
	
	private void adicionarInimigos(){
		Random rnd = new Random();
		if(rnd.nextBoolean()){
			if(rnd.nextBoolean()){
				this.inimigos.add(new InimigoForte(720, this.getHeight() - 100,
						zumbi_forte_invertido, -1));
			}else{
				this.inimigos.add(new InimigoForte(10 , this.getHeight() - 100,
						zumbi_forte_normal, 1));
			}
		}else{
			if(rnd.nextBoolean()){
				this.inimigos.add(new InimigoFraco(720, this.getHeight() - 100, 
						zumbi_fraco_invertido, -1));
			}else{
				this.inimigos.add(new InimigoFraco(10, this.getHeight() - 100,
						zumbi_fraco_normal, 1));
			}
		}
	}
	
	private void runEstFinal(){
		somZumbiVivo.stop();
		if(InputManager.getInstance().isPressed(KeyEvent.VK_ENTER)){
			onLoad();
		}
		if(InputManager.getInstance().isPressed(KeyEvent.VK_ESCAPE)){
			terminate();
		}
	}

	private void runEstDescer() {
		controleAltura();
		if (jogador.getY() < chao) {
			jogador.setY((int) (jogador.getY() + (jogador.getSpeed())));
		} else {
			estadoSalto = EST_REPOUSO;
		}
	}
	
	private void controleAltura(){
		if(jogador.getY() == p2.getY() - 80){
			if(jogador.getX() >= p2.getX() - 50 && jogador.getX() <= p2.getX() + p2.getSprite().getWidth()){
				chao = p2.getY() - 80;
				jogador.setY(p2.getY() - 80);
				estadoSalto = EST_REPOUSO;
			}else{
				chao = 500;
				estadoSalto = EST_CAINDO;
			}
		}
		if(jogador.getY() == p1.getY() - 80){
			if(jogador.getX() >= p1.getX() - 50 && jogador.getX() <= p1.getX() + p1.getSprite().getWidth()){
				chao = p1.getY() - 80;
				jogador.setY(p1.getY() - 80);
				estadoSalto = EST_REPOUSO;
			}else if(jogador.getX() >= p3.getX() - 50 && jogador.getX() <= p3.getX() + p3.getSprite().getWidth()){
				chao = p3.getY() - 80;
				jogador.setY(p3.getY() - 80);
				estadoSalto = EST_REPOUSO;
			}else{
				chao = 500;
				estadoSalto = EST_CAINDO;
			}
		}	
		if(jogador.getY() == p3.getY() - 80){
			if(jogador.getX() >= p3.getX() - 50 && jogador.getX() <= p3.getX() + p3.getSprite().getWidth()){
				chao = p3.getY() - 80;
				jogador.setY(p3.getY() - 80);
				estadoSalto = EST_REPOUSO;
			}else{
				chao = 500;
				estadoSalto = EST_CAINDO;
			}
		}
	}
	
	private void runEstAndandoDireita(){
		controleAltura();
		if(jogador.getX()<720){
			jogador.setX(jogador.getX() + (int)jogador.getSpeed());
			direcao = 1;
			jogador.andar(direcao);
			somPasso.play();
		}
		if(InputManager.getInstance().isReleased(KeyEvent.VK_RIGHT)){
			estado = EST_PARADO;
			somPasso.stop();
		}
	}
	
	private void runEstAndandoEsquerda(){
		controleAltura();
		if(jogador.getX()> 5){
		jogador.setX(jogador.getX() - (int)jogador.getSpeed());
		direcao = -1;
		jogador.andar(direcao);
		somPasso.play();
		}
		if(InputManager.getInstance().isReleased(KeyEvent.VK_LEFT)){
			estado = EST_PARADO;
			somPasso.stop();
		}
	}
	
	private void runAtirando(int currentTick) {
		jogador.atirar(direcao, currentTick, somTiro);
		if(InputManager.getInstance().isReleased(KeyEvent.VK_SPACE)){
			somTiro.stop();
		}
	}
	
	private void verificandoBalas(){
		List<Tiro> balas = jogador.getTiros();
		for (int i = 0; i < balas.size(); i++) {
			Tiro tiro = balas.get(i);
			if(tiro.isVisible()){
				tiro.mover();
			} else {
				balas.remove(tiro);
			}
		}
	}
	
	private void runAbaixar(){
		if(InputManager.getInstance().isReleased(KeyEvent.VK_DOWN)){
			estado = EST_PARADO;
		}
	}
	
	private void runIA(int currentTick){
		for(Inimigo i: inimigos){
			i.seMexer(currentTick);
		}
	}
	
	private void renderHUD(Graphics2D g){
		displayP.display(g);
		g.drawString(this.tentativas + "  TENTATIVAS", 650, 60);
		g.drawString("SCORE "+this.score, 30, 40);
	}
	
	private boolean colisionDetector(){
		Rectangle rectLampiao = jogador.getBounds(direcao);
		
		List<Tiro> balas = jogador.getTiros();
		for (Tiro tiro : balas) {
				for(Inimigo i: inimigos){
					if(tiro.getBounds().intersects(i.getBounds())){
						tiro.setVisible(false);
						somZumbiMorto.play();
						this.inimigos.remove(i);
						if(i.getIdentificador().equals("fraco")){
						balistica.setChanges(10);
						}else if(i.getIdentificador().equals("forte")){
							balistica.setChanges(20);
						}
						return true;
					}
				}
		}
				for(Inimigo i: inimigos){
				if(i.getBounds().intersects(rectLampiao)){
						this.estaVivo = false;
						this.estado = EST_FINAL;
						return true;
					}
				}
			return false;
	}

	@Override
	public void onUpdate(int currentTick) {
		// Ouve o precionar dos teclados
		if(estado != EST_FINAL){
		runControleJogo();
		jogador.getSpritesDireita().update(currentTick);
		jogador.getSpritesEsquerda().update(currentTick);
		if(colisionDetector()){
		}
		if(currentTick % 50 == 0){
		adicionarInimigos();
		}
		runIA(currentTick);
		verificandoBalas();
		
		if (!(estado == EST_PARADO)) {
			if (this.estado == EST_ANDANDO_DIREITA) {
				runEstAndandoDireita();
			} else if(this.estado == EST_ANDANDO_ESQUERDA){
				runEstAndandoEsquerda();
			}
		}
		if(estadoSalto ==  EST_REPOUSO){
			chao = jogador.getY();
		} else if(estadoSalto == EST_PULANDO){
			runEstPulando();
		} else if(estadoSalto == EST_CAINDO) {
			runEstDescer();
		}
		if(estadoAtirando==EST_ATIRANDO){
			runAtirando(currentTick);
		}
		if(estado == EST_ABAIXADO){
			runAbaixar();
		}
		}else if(estado == EST_FINAL){
			runEstFinal();
		}
	}

	@Override
	public void onUnLoad() {
	}
	
	@Override
	public void onRender(Graphics2D g) {
		
		g.drawImage(cenario, 0, 0, null);
		
		g.drawImage(p1.getSprite(), p1.getX(), p1.getY(), null);
		g.drawImage(p2.getSprite(), p2.getX(), p2.getY(), null);
		g.drawImage(p3.getSprite(), p3.getX(), p3.getY(), null);
		
		for(int i = 0; i < this.inimigos.size(); i++){
			Inimigo tempInimigo = this.inimigos.get(i);
			g.drawImage(tempInimigo.getImagem(), tempInimigo.getX(), tempInimigo.getY(), null);
		}
		
		if(estado == EST_FINAL){
			somGameOver.play();
			g.drawImage(gameOver, 0, 0, null);
		}
		if(estaVivo){
			List<Tiro> balas = jogador.getTiros();
			for (Tiro tiro : balas) {
				if(tiro.isVisible()){
					g.drawImage(tiro.getImagem(), tiro.getX(), tiro.getY(), null);
				}
			}
		if(estadoSalto == EST_REPOUSO){
			if(direcao>0){
				if(estado == EST_ABAIXADO){
					g.drawImage(lampiao_abaixado_normal, jogador.getX(), jogador.getY(), 80, 80, null);
				} else if(estado != EST_PARADO){
					g.drawImage(jogador.getImagem(), jogador.getX(), jogador.getY(), 80, 80, null);
				} else{
					g.drawImage(lampiao_normal, jogador.getX(), jogador.getY(), 80, 80, null);
				}
			} else {
				if(estado == EST_ABAIXADO){
					g.drawImage(lampiao_abaixado_invertido, jogador.getX(), jogador.getY(), 80, 80, null);
				} else if(estado != EST_PARADO){
				g.drawImage(jogador.getImagem(), jogador.getX(), jogador.getY(), 80, 80, null);
				}else{
					g.drawImage(lampiao_invertido, jogador.getX(), jogador.getY(), 80, 80, null);
				}
			}
		} else if(estadoSalto == EST_PULANDO || estadoSalto == EST_CAINDO){
			if(direcao>0){
				g.drawImage(lampiao_pulando_normal, jogador.getX(), jogador.getY(), 80, 80, null);
			} else {
				g.drawImage(lampiao_pulando_invertido, jogador.getX(), jogador.getY(), 80, 80, null);
			}
		}
		}
		renderHUD(g);
	}
		
}

package br.com.lol.stages;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import br.com.lol.armas.Arma;
import br.com.lol.armas.Calibre12;
import br.com.lol.auxDisplays.Inventario;
import br.com.lol.core.Game;
import br.com.lol.entidade.Entidade;
import br.com.lol.entidade.EntidadePlataforma;
import br.com.lol.entidade.Jogador;
import br.com.lol.entidade.Tiro;
import br.com.lol.gerenciadores.CollisionDetector;
import br.com.lol.gerenciadores.GerenciadorDeTempo;
import br.com.lol.gerenciadores.ImageManager;
import br.com.lol.gerenciadores.InputManager;

public class TestStage extends Game {
	// Constantes do jogo.
	/*
	 * Aqui estão os estados do pulo, em Repouso, Pulando ou Descendo.
	 */
	final private int ESTADOREPOUSO = 0;
	final private int ESTADOPULANDO = 1;
	final private int ESTADODESCENDO = 2;
	/*
	 * Constantes que mostra a direção de lampiao.
	 */
	final private int DIREITA = 1;
	final private int ESQUERDA = -1;
	/*
	 * Aqui estão todos os estado dos movimentos de direita e esquerda.
	 */
	final private int ESTADOANDANDO = 3;
	final private int ESTADOPARADO = 4;
	/*
	 * Todas as imagens antes de serem inciadas
	 */
	private BufferedImage cenarioPlataforma;

	List<EntidadePlataforma> listaDePlataformas;

	/*
	 * Atributos do jogo.
	 */
	private int chao;
	private Dimension dim;
	private Point rolagem;

	private boolean aindaRolandoEsquerda;
	private boolean aindaRolandoDireita;

	private Arma armaAtual;

	// Jogador
	private Jogador jogador;
	// Plataformas
	private EntidadePlataforma plataforma1;
	private EntidadePlataforma plataforma2;
	private EntidadePlataforma plataforma3;

	// ESSAS AQUI SÃO DE TESTE
	
	private boolean colisaoArma;
	private boolean colisaoArma2;
	private Entidade arma;
	private Entidade arma2;
	private Inventario inventario;
	
	final private int PAUSANOJOGO = 99;
	// Atributo respinsavel por "DIZER" se a collisão.x
	private CollisionDetector colisao;

	/*
	 * Método que inicializa todas as imagens do jogo.
	 */
	private void inicializarImagens() {
		try {
			this.cenarioPlataforma = ImageManager.getInstance().loadImage(
					"br/com/lol/imagens/cenario-LOL.png");

		} catch (IOException e) {
			System.err.println(e.getMessage());
		}
	}

	/*
	 * Todas as atualizações das plataformas e outras coisas estão aqui
	 */
	private void updateDimensionDireita() {
		if (jogador.getX() > getWidth() * 0.55) {
			if(aindaRolandoDireita){
			rolagem.x += (int) jogador.getSpeed();
			listaDePlataformas = colisao.getListaDeEntidades();
			for (EntidadePlataforma i : listaDePlataformas) {
				i.setX(i.getX() - (int) jogador.getSpeed());
			}
			this.arma.setX(this.arma.getX() - (int) jogador.getSpeed());
			this.arma2.setX(this.arma2.getX() - (int) jogador.getSpeed());
		}
		}
	}
	

	private void updateDimensionEsquerda() {
		if ((jogador.getX() < getWidth() * 0.45)||rolagem.x < getWidth()*0.45) {
			if(aindaRolandoEsquerda){
			rolagem.x -= (int) jogador.getSpeed();
			listaDePlataformas = colisao.getListaDeEntidades();
			for (EntidadePlataforma i : listaDePlataformas) {
				i.setX(i.getX() + (int) jogador.getSpeed());
			}
			this.arma.setX(arma.getX() + (int) jogador.getSpeed());
			this.arma2.setX(arma2.getX() + (int) jogador.getSpeed());
		}
		}
	}
	
	public void verificaRolagem(){
		if(this.rolagem.x >= 6700){
			this.aindaRolandoDireita = false;
		}else{
			this.aindaRolandoDireita = true;
		}
		if(this.rolagem.x <= -100){
			this.aindaRolandoEsquerda = false;
		}else{
			this.aindaRolandoEsquerda = true;
		}
	}

	/*
	 * Inicializar dimension Aumenta o tamanho da dimension a partir da posição
	 * das plataformas
	 */
	private void inicializarDimension() {
		dim = new Dimension(getWidth(), getHeight());
		for (EntidadePlataforma e : listaDePlataformas) {
			if (dim.width < e.getX() + e.getLargura()) {
				dim.width = e.getX() + e.getLargura();
			}
			if (dim.height < e.getY() + e.getAltura()) {
				dim.height = e.getY() + e.getAltura();
			}
		}
	}

	/*
	 * Inicializa todas as plataformas
	 */
	private void inicializarPlataformas() {
		plataforma1 = new EntidadePlataforma(0, this.getHeight() - 50, 7500, 20);
		plataforma2 = new EntidadePlataforma(150, this.getHeight() - 100, 100,
				20);
		plataforma3 = new EntidadePlataforma(300, this.getHeight() - 150, 100,
				20);

		arma = new Entidade(1200, getHeight() - 100);
		arma2 = new Entidade(5000, getHeight() - 65);

		plataforma1.init();
		plataforma2.init();
		plataforma3.init();

	}

	/*
	 * Método que ouve todos os sons do teclado e apartir disso seta os estados
	 * no personagem.
	 */
	private void runControleJogo() {
		if(InputManager.getInstance().isTyped(KeyEvent.VK_U)){
			this.estado = PAUSANOJOGO;
			this.inventario.setSelecionarOpcoes(true);
		}
		if (InputManager.getInstance().isPressed(KeyEvent.VK_UP)
				&& (jogador.getEstadoDoSalto() != ESTADODESCENDO)) {
			jogador.setEstadoDoSalto(ESTADOPULANDO);
		}
		if (InputManager.getInstance().isPressed(KeyEvent.VK_RIGHT)) {
			jogador.setEstado(ESTADOANDANDO);
			jogador.setDirecao(DIREITA);
			jogador.andar(jogador.getDirecao());
		}
		if (InputManager.getInstance().isPressed(KeyEvent.VK_LEFT)) {
			jogador.setEstado(ESTADOANDANDO);
			jogador.setDirecao(ESQUERDA);
			jogador.andar(jogador.getDirecao());
		}
		if (InputManager.getInstance().isPressed(KeyEvent.VK_DOWN)) {

		}
		if (InputManager.getInstance().isPressed(KeyEvent.VK_ESCAPE)) {
			terminate();
		}
		if(InputManager.getInstance().isPressed(KeyEvent.VK_SPACE)){
			this.jogador.atirarTest();
		}
	}
	
	/*
	 * | Metodos dos estados de lampião estão aqui V
	 */
	private void runEstadoPulando() {
		if (chao - 100 < jogador.getY()
				&& InputManager.getInstance().isPressed(KeyEvent.VK_UP)) {
			jogador.setY(jogador.getY() - (int) jogador.getSpeed());
		} else {
			jogador.setEstadoDoSalto(ESTADODESCENDO);
		}
	}

	private void runEstadoDescendo() {
		jogador.setY(jogador.getY() + (int) jogador.getSpeed());
	}

	private void runEstadoMovendoDireita() {
		jogador.setX(jogador.getX() + ((int) jogador.getSpeed()));
	}

	private void runEstadoMovendoEsquerda() {
		jogador.setX(jogador.getX() - ((int) jogador.getSpeed()));
	}

	// ACABA AQUI OS METODOS DOS ESTADOS DE LAMPIÃO

	/*
	 * Roda antes do jogo iniciar o loop.
	 */

	private void colisaoArma() {
		if(colisaoArma == false){
		if (this.arma.getBounds().intersects(this.jogador.getBounds())) {
			this.inventario.addArmaAoInventario(1);
			this.colisaoArma = true;
		}
		}
		if(colisaoArma2 == false){
		if (this.arma2.getBounds().intersects(this.jogador.getBounds())) {
			this.inventario.addArmaAoInventario(0);
			this.colisaoArma2 = true;
		}
		}
	}

	public void onLoad() {
		
		this.colisaoArma = false;

		this.aindaRolandoEsquerda = true;
		this.aindaRolandoDireita = true;
		inicializarImagens();
		inicializarPlataformas();
		rolagem = new Point(0, 300);
		rolagem.x = 0;
		rolagem.y = 300;
		jogador = new Jogador(100, this.getHeight() - 300);
		this.armaAtual = this.jogador.getArma();
		this.inventario = new Inventario(getWidth() / 2, getHeight()/2, this.jogador);
		listaDePlataformas = new ArrayList<>();
		listaDePlataformas.add(plataforma1);
		listaDePlataformas.add(plataforma2);
		listaDePlataformas.add(plataforma3);

		colisao = new CollisionDetector(listaDePlataformas);
		inicializarDimension();
	}

	/*
	 * Método que roda a cada tick.
	 */
	public void onUpdate(int currentTick) {
		this.armaAtual.update(jogador.getX(), jogador.getY());
		verificaRolagem();
		jogador.getSpritesDireita().update(currentTick);
		jogador.getSpritesEsquerda().update(currentTick);
		colisaoArma();
		//inventario.update(getWidth()/2, getHeight()/ 2);
		if(this.estado != PAUSANOJOGO){
		runControleJogo();
		}else{
			this.inventario.controleSelecaoOpcao(currentTick);
			if(!this.inventario.isSelecionarOpcoes()){
				this.estado = ESTADOPARADO;
			}
			if(this.inventario.isSelecionarArmas()){
				
			}
		}
		if (jogador.getEstadoDoSalto() == ESTADOPULANDO) {
			runEstadoPulando();
		} else if (colisao.colisaoPlataforma(jogador)) {
			jogador.setEstadoDoSalto(ESTADOREPOUSO);
			chao = jogador.getY();
		} else {
			jogador.setEstadoDoSalto(ESTADODESCENDO);
			runEstadoDescendo();
		}
		if (jogador.getEstado() == ESTADOANDANDO) {
			if (jogador.getDirecao() == DIREITA) {
				if (jogador.getX() < getWidth() * 0.60) {
					runEstadoMovendoDireita();
				}
				updateDimensionDireita();
				if (InputManager.getInstance().isReleased(KeyEvent.VK_RIGHT)) {
					jogador.setEstado(ESTADOPARADO);
				}
			} else {
				if (jogador.getX() > getWidth() * 0.40) {
					runEstadoMovendoEsquerda();
				}
				updateDimensionEsquerda();
				if (InputManager.getInstance().isReleased(KeyEvent.VK_LEFT)) {
					jogador.setEstado(ESTADOPARADO);
				}
			}
		}
	}
	
	public void desenharArma(Graphics2D g){
		for(Tiro tiro: this.armaAtual.getBalas()){
			if(tiro.isVisible()){
				tiro.mover();
				g.drawImage(tiro.getImagem(), tiro.getX(), tiro.getY(), null);
			}
		}
	}

	public void onUnLoad() {

	}

	private void renderPlataformas(Graphics2D g) {
		for (EntidadePlataforma e : listaDePlataformas) {
			g.drawImage(e.getSprite(), e.getX(), e.getY(), null);
		}
	}

	public void onRender(Graphics2D g, int currentTick) {
		// Desenhando o cenario
		
		g.drawImage(cenarioPlataforma, -rolagem.x, -rolagem.y+300, null);
		
		if(this.estado == PAUSANOJOGO){
			this.inventario.display(g);
			if(this.inventario.isSelecionarArmas()){
				this.inventario.displayInventarioArmas(g);
			}
		}
		if (!colisaoArma) {
			g.setColor(Color.BLACK);
			g.fillRect(this.arma.getX() - 10, this.arma.getY()-30, 40 , 40);
			g.setColor(Color.RED);
			g.drawString("GUN", this.arma.getX(), this.arma.getY());
		}
		if (!colisaoArma2) {
			g.setColor(Color.WHITE);
			g.fillRect(this.arma2.getX() - 10, this.arma2.getY()-30, 40 , 40);
			g.setColor(Color.RED);
			g.drawString("FACÃO", this.arma2.getX(), this.arma2.getY());
		}
		// Desenhando as plataformas
		renderPlataformas(g);
		g.drawImage(jogador.getImagem(), jogador.getX(), jogador.getY(), 80,
				80, null);
		desenharArma(g);
	}

	public static void main(String[] args) {
		Game jogo = new TestStage();
		jogo.run();
	}

}

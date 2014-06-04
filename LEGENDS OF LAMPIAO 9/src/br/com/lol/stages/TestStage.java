package br.com.lol.stages;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import br.com.lol.IA.BasicIA;
import br.com.lol.IA.IaChefe;
import br.com.lol.IA.Temporizador;
import br.com.lol.armas.Arma;
import br.com.lol.auxDisplays.DesenharPlataformas;
import br.com.lol.auxDisplays.Inventario;
import br.com.lol.core.Game;
import br.com.lol.entidade.Bau;
import br.com.lol.entidade.Corvo;
import br.com.lol.entidade.Entidade;
import br.com.lol.entidade.EntidadePlataforma;
import br.com.lol.entidade.Inimigo;
import br.com.lol.entidade.Jogador;
import br.com.lol.entidade.MestreStage1;
import br.com.lol.entidade.Projetil;
import br.com.lol.gerenciadores.CollisionDetector;
import br.com.lol.gerenciadores.ImageManager;
import br.com.lol.gerenciadores.InputManager;
import br.com.lol.gerenciadores.SpriteAnimation;

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

	private int estadoAnterior;

	private boolean lapadaNoJogador = false;

	private Temporizador timer;
	private Temporizador timerCalibre12;
	private Temporizador timerLapada;

	Thread threadDoChefe;
	Thread threadTimer;
	Thread threadTimerCalibre12;
	Thread threadTimerLapada;
	/*
	 * Todas as imagens antes de serem inciadas
	 */
	private BufferedImage cenarioPlataforma;

	List<EntidadePlataforma> listaDePlataformas;
	DesenharPlataformas desenharPlataformas;

	private List<Corvo> corvos;
	private List<Inimigo> inimigos;
	private BasicIA ia;
	/*
	 * Atributos do jogo.
	 */
	private int chao;
	private Dimension dim;
	private Point rolagem;

	private boolean aindaRolandoEsquerda;
	private boolean aindaRolandoDireita;

	private boolean noChefe;

	private IaChefe iaChefe;

	private Arma armaAtual;

	// Jogador
	private Jogador jogador;
	private MestreStage1 mestre;
	// Plataformas
	private EntidadePlataforma plataforma0;
	private EntidadePlataforma plataforma1;
	private EntidadePlataforma plataforma2;
	private EntidadePlataforma plataforma3;
	private EntidadePlataforma plataforma4;
	private EntidadePlataforma plataforma5;

	private List<Bau> bausDoGame;

	// ESSAS AQUI SÃO DE TESTE

	private boolean colisaoArma;
	private boolean colisaoArma2;
	private Entidade arma;
	private Entidade arma2;
	private Inventario inventario;

	final private int PAUSANOJOGO = 99;
	// Atributo respinsavel por "DIZER" se a collisão.x
	private CollisionDetector colisao;
	private boolean pararDimensioEsquerda;
	private boolean pararDimensionDireita;
	private boolean controleUpdate;
	private Thread threadTransformacao;
	private Thread threadPancada;
	private Temporizador timeModoVoador;
	private Temporizador timePancada;

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

	public void onLoad() {
		this.colisaoArma = false;
		controleUpdate = true;

		this.aindaRolandoEsquerda = true;
		this.aindaRolandoDireita = true;
		this.noChefe = false;

		this.timer = new Temporizador(5000);
		this.timerCalibre12 = new Temporizador(500);
		this.timerLapada = new Temporizador(3000);
		this.timePancada = new Temporizador(1000);

		inicializarImagens();
		inicializarPlataformas();
		rolagem = new Point(0, 300);
		rolagem.x = 0;
		rolagem.y = 300;
		jogador = new Jogador(100, this.getHeight() - 300);
		this.ia = new BasicIA(this.jogador);
		this.inimigos = new ArrayList<Inimigo>();
		this.corvos = new ArrayList<Corvo>();
		this.mestre = new MestreStage1(1200, getHeight() - 140, -1, this.jogador);
		this.iaChefe = new IaChefe(this.mestre);
		this.threadDoChefe = new Thread(iaChefe);
		this.threadTimer = new Thread(timer);
		this.threadTimerCalibre12 = new Thread(this.timerCalibre12);
		this.threadTimerLapada = new Thread(this.timerLapada);

		this.armaAtual = this.jogador.getArma();
		this.inventario = new Inventario(getWidth() / 2, getHeight() / 2,
				this.jogador, this);
		listaDePlataformas = new ArrayList<>();

		listaDePlataformas.add(plataforma5);
		listaDePlataformas.add(plataforma4);
		listaDePlataformas.add(plataforma3);
		listaDePlataformas.add(plataforma2);
		listaDePlataformas.add(plataforma1);
		listaDePlataformas.add(plataforma0);

		bausDoGame = new ArrayList<>();
		bausDoGame.add(new Bau(500));

		desenharPlataformas = new DesenharPlataformas(listaDePlataformas);

		colisao = new CollisionDetector(listaDePlataformas);
		inicializarDimension();

		timeModoVoador = new Temporizador(1000);
		threadTransformacao = new Thread(timeModoVoador);
		threadPancada = new Thread(timePancada);
		adicionarInimigos();
	}

	/*
	 * Todas as atualizações das plataformas e outras coisas estão aqui
	 */

	private void updateDimensionDireita() {
		if (jogador.getX() > getWidth() * 0.59) {
			if (aindaRolandoDireita) {
				rolagem.x += (int) jogador.getSpeed();
				listaDePlataformas = colisao.getListaDeEntidades();
				ArrayList<EntidadePlataforma> listaEsquerda = (ArrayList<EntidadePlataforma>) colisao
						.getListaVerticalEsquerda();
				ArrayList<EntidadePlataforma> listaDireita = (ArrayList<EntidadePlataforma>) colisao
						.getListaVerticalDireita();

				for (EntidadePlataforma i : listaDePlataformas) {
					i.setX(i.getX() - (int) jogador.getSpeed());
				}
				for (EntidadePlataforma i : listaEsquerda) {
					i.setX(i.getX() - (int) jogador.getSpeed());
				}
				for (EntidadePlataforma i : listaDireita) {
					i.setX(i.getX() - (int) jogador.getSpeed());
				}
				for (Corvo c : this.corvos) {
					c.setX(c.getX() - (int) jogador.getSpeed());
				}
				for (Bau bau : bausDoGame) {
					bau.setX(bau.getX() - (int) (jogador.getSpeed()));
				}
				for(Inimigo i: this.inimigos){
					i.setX(i.getX() - (int)this.jogador.getSpeed());
					i.setMarco0X(i.getMarco0X() - (int)this.jogador.getSpeed());
				}
				this.arma.setX(this.arma.getX() - (int) jogador.getSpeed());
				this.arma2.setX(this.arma2.getX() - (int) jogador.getSpeed());
				this.mestre.setX(this.mestre.getX() - (int) jogador.getSpeed());
			}
		}
	}
	
	private void updateInimigos(int currentTick){
		for(Inimigo i: this.inimigos){
			i.getSpriteDireita().update(currentTick);
			i.getSpriteEsquerda().update(currentTick);
		}
	}

	private void pararUpdate() {
		if (controleUpdate) {
			if (jogador.getX() < 330 && rolagem.x <= 0) {
				pararDimensioEsquerda = true;
			} else
				pararDimensioEsquerda = false;
			if (jogador.getX() > 390 && rolagem.x >= 6700)
				pararDimensionDireita = true;
			else
				pararDimensionDireita = false;
		}
	}

	private void pararAqui() {
		if (!pararDimensioEsquerda && !pararDimensionDireita) {
			pararDimensioEsquerda = true;
			pararDimensionDireita = true;
		} else {
			pararDimensioEsquerda = false;
			pararDimensionDireita = false;
		}
	}

	private void updateDimensionEsquerda() {
		if (((jogador.getX() < getWidth() * 0.41) || rolagem.x < getWidth() * 0.41)
				&& rolagem.x > 0) {
			rolagem.x -= (int) jogador.getSpeed();
			listaDePlataformas = colisao.getListaDeEntidades();
			ArrayList<EntidadePlataforma> listaEsquerda = (ArrayList<EntidadePlataforma>) colisao
					.getListaVerticalEsquerda();
			ArrayList<EntidadePlataforma> listaDireita = (ArrayList<EntidadePlataforma>) colisao
					.getListaVerticalDireita();

			for (EntidadePlataforma i : listaDePlataformas) {
				i.setX(i.getX() + (int) jogador.getSpeed());
			}
			for (EntidadePlataforma i : listaEsquerda) {
				i.setX(i.getX() + (int) (jogador.getSpeed()));
			}
			for (EntidadePlataforma i : listaDireita) {
				i.setX(i.getX() + (int) (jogador.getSpeed()));
			}
			for (Corvo c : this.corvos) {
				c.setX(c.getX() + (int) jogador.getSpeed());
			}
			for (Bau bau : bausDoGame) {
				bau.setX(bau.getX() + (int) (jogador.getSpeed()));
			}
			for(Inimigo i: this.inimigos){
				i.setX(i.getX() + (int)this.jogador.getSpeed());
				i.setMarco0X(i.getMarco0X() + (int)this.jogador.getSpeed());
			}
			this.arma.setX(arma.getX() + (int) jogador.getSpeed());
			this.arma2.setX(arma2.getX() + (int) jogador.getSpeed());
			this.mestre.setX(this.mestre.getX() + (int) jogador.getSpeed());
		} else {

		}
	}

	public void verificaRolagem() {
		if (this.rolagem.x >= 6700) {
			this.aindaRolandoDireita = false;
		} else {
			this.aindaRolandoDireita = true;
		}
		if (this.rolagem.x <= -100) {
			this.aindaRolandoEsquerda = false;
		} else {
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
	
	private void adicionarInimigos(){
		for(int i = 1; i < 15; i++){
			this.ia.adicionarInimigosLimit(this.inimigos, 400 * i, 0);
		}
	}

	private void ativarRadar(){
		
	}
	
	/*
	 * Inicializa todas as plataformas
	 */
	private void inicializarPlataformas() {
		plataforma0 = new EntidadePlataforma(0, this.getHeight() - 50, 143, 10);
		plataforma1 = new EntidadePlataforma(162, this.getHeight() - 100, 100,
				10);
		plataforma2 = new EntidadePlataforma(280, this.getHeight() - 150, 100,
				10);
		plataforma3 = new EntidadePlataforma(399, this.getHeight() - 220, 200,
				10);
		plataforma4 = new EntidadePlataforma(528, this.getHeight() - 50, 142,
				10);
		plataforma5 = new EntidadePlataforma(690, this.getHeight() - 120, 7500,
				10);

		arma = new Entidade(1200, getHeight() - 100);
		arma2 = new Entidade(5000, getHeight() - 65);

		plataforma0.init();
		plataforma1.init();
		plataforma2.init();
		plataforma3.init();
		plataforma4.init();
		plataforma5.init();

	}

	private void verificaLocalChefe() {
		if (this.rolagem.x >= 1600) {
			pararAqui();
			this.noChefe = true;
		}
	}

	/*
	 * Método que ouve todos os sons do teclado e apartir disso seta os estados
	 * no personagem.
	 */
	private void runControleJogo(int currentTick) {
		if (jogador.getEnergia() > 0) {
			if (InputManager.getInstance().isPressed(KeyEvent.VK_UP)
					&& (jogador.isModoVoador())) {
				jogador.setEstadoDoSalto(ESTADOPULANDO);
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
				jogador.setEstaAbaixado(true);
			}
			if (InputManager.getInstance().isPressed(KeyEvent.VK_SPACE)) {
				this.jogador.atirarTest();
			}
			if (InputManager.getInstance().isPressed(KeyEvent.VK_V)) {
				if (threadTransformacao.getState() == Thread.State.NEW) {
					threadTransformacao = new Thread(timeModoVoador);
					threadTransformacao.start();
					if (jogador.isModoVoador()) {
						jogador.desativarModoVoador();
					} else {
						jogador.ativarModoVoador();
					}
				} else if (threadTransformacao.getState() == Thread.State.TERMINATED) {
					threadTransformacao = new Thread(timeModoVoador);
				}
			}
			if (InputManager.getInstance().isPressed(KeyEvent.VK_P)) {
				controleUpdate = false;
				pararAqui();
			}
			if (InputManager.getInstance().isReleased(KeyEvent.VK_DOWN)) {
				jogador.setEstaAbaixado(false);
			}

		}
		if (InputManager.getInstance().isPressed(KeyEvent.VK_ESCAPE)) {
			if (currentTick % 10 == 0) {
				terminate();
			}
		}
		if (InputManager.getInstance().isTyped(KeyEvent.VK_U)) {
			this.estadoAnterior = estado;
			this.estado = PAUSANOJOGO;
			this.inventario.setActive(true);
			this.inventario.setSelecionarInventario(true);
		}
	}

	/*
	 * | Metodos dos estados de lampião estão aqui V
	 */
	private void runEstadoPulando() {
		if (chao - 100 < jogador.getY()
				&& InputManager.getInstance().isPressed(KeyEvent.VK_UP)) {
			jogador.setY(jogador.getY() - (int) jogador.getSpeed());
		} else if (jogador.isModoVoador()
				&& InputManager.getInstance().isPressed(KeyEvent.VK_UP)
				&& jogador.getY() > 0) {
			jogador.setY(jogador.getY() - (int) jogador.getSpeed());
		} else {
			if (!jogador.isModoVoador())
				jogador.setEstadoDoSalto(ESTADODESCENDO);
			else if (InputManager.getInstance().isReleased(KeyEvent.VK_UP)) {
				jogador.setEstadoDoSalto(ESTADODESCENDO);
			}
		}
	}

	private void runEstadoDescendo() {
		jogador.setY(jogador.getY() + (int) jogador.getSpeed());
	}

	private void runEstadoMovendoDireita() {
		if (jogador.getX() < 710
				&& !colisao.colisaoPlataformaVerticalEsquerda(jogador))
			jogador.setX(jogador.getX() + ((int) jogador.getSpeed()));
	}

	private void runEstadoMovendoEsquerda() {
		if (jogador.getX() > 0
				&& !colisao.colisaoPlataformaVerticalDireira(jogador))
			jogador.setX(jogador.getX() - ((int) jogador.getSpeed()));
	}

	// ACABA AQUI OS METODOS DOS ESTADOS DE LAMPIÃO

	/*
	 * Roda antes do jogo iniciar o loop.
	 */

	private void colisaoArma() {
		if (colisaoArma == false) {
			if (this.arma.getBounds().intersects(this.jogador.getBounds())) {
				this.colisaoArma = true;
			}
		}
		if (colisaoArma2 == false) {
			if (this.arma2.getBounds().intersects(this.jogador.getBounds())) {
				this.colisaoArma2 = true;
			}
		}
	}

	private void lapada() {
		for (Corvo c : corvos) {
			if (c.isVisible()) {
				if (c.getBounds().intersects(this.jogador.getBounds())) {
					this.lapadaNoJogador = true;
				}
			}
		}
	}

	/*
	 * Método que roda a cada tick.
	 */
	public void onUpdate(int currentTick) {
		lapada();
		pararUpdate();
		verificaLocalChefe();
		updateInimigos(currentTick);
		colisao.colisaoBaus(jogador, bausDoGame);
		colisaoCorvo();
		if (noChefe) {
			if (this.threadDoChefe.getState() == Thread.State.NEW)
				this.threadDoChefe.start();
			if (this.threadDoChefe.getState() == Thread.State.TERMINATED)
				this.threadDoChefe = new Thread(this.iaChefe);
		}

		if (this.threadTimer.getState() == Thread.State.NEW) {
			this.threadTimer.start();
			this.ia.adicionarCorvos(corvos);
		}
		if (this.threadTimer.getState() == Thread.State.TERMINATED) {
			this.threadTimer = new Thread(timer);
			this.threadTimer.start();
			this.ia.adicionarCorvos(corvos);
		}

		jogador.updateFly();
		this.armaAtual.update(jogador.getX(), jogador.getY());
		verificaRolagem();
		mestre.getSpriteDireita().update(currentTick);
		mestre.getSpriteEsquerda().update(currentTick);
		jogador.getSpritesDireita().update(currentTick);
		jogador.getSpritesEsquerda().update(currentTick);
		jogador.getSpritesVoandoDireita().update(currentTick);
		jogador.getSpritesVoandoEsquerda().update(currentTick);
		jogador.getFumaçaSprite().update(currentTick);
		colisaoArma();
		if (this.estado != PAUSANOJOGO) {
			runControleJogo(currentTick);
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
					} else if (!pararDimensionDireita
							&& !colisao
									.colisaoPlataformaVerticalEsquerda(jogador))
						updateDimensionDireita();
					else
						runEstadoMovendoDireita();
					if (InputManager.getInstance()
							.isReleased(KeyEvent.VK_RIGHT)) {
						jogador.setEstado(ESTADOPARADO);
					}
				} else {
					if (jogador.getX() > getWidth() * 0.40) {
						runEstadoMovendoEsquerda();
					} else if (!pararDimensioEsquerda
							&& !colisao
									.colisaoPlataformaVerticalDireira(jogador))
						updateDimensionEsquerda();
					else
						runEstadoMovendoEsquerda();
					if (InputManager.getInstance().isReleased(KeyEvent.VK_LEFT)) {
						jogador.setEstado(ESTADOPARADO);
					}
				}
			}
		} else {
			if (!this.inventario.isActive()) {
				this.estado = this.estadoAnterior;
			}
		}
	}

	public void renderProjeteis(Graphics2D g) {
		for (Projetil tiro : this.armaAtual.getBalas()) {
			if (tiro.isVisible()) {
				if (this.armaAtual.getCodigo() == 2) {
					int x = tiro.getX();
					int y = tiro.getY();
					System.out.println("DIRECAO DO TIRO:");
					System.out.println(tiro.getDirecao());
					if (tiro.getDirecao() > 0) {
						if (this.threadTimerCalibre12.getState() == Thread.State.NEW) {
							this.threadTimerCalibre12.start();
							for (int i = 0; i < 6; i++) {
								g.drawImage(this.armaAtual.getImagem(), x,
										y - 50, 70, 100, null);
								x += 40;
							}
						} else if (this.threadTimerCalibre12.getState() == Thread.State.TERMINATED) {
							this.threadTimerCalibre12 = new Thread(
									this.timerCalibre12);
							this.threadTimerCalibre12.start();
							for (int i = 0; i < 6; i++) {
								g.drawImage(this.armaAtual.getImagem(), x,
										y - 50, 70, 100, null);
								x += 40;
							}
						}
					} else {
						if (this.threadTimerCalibre12.getState() == Thread.State.NEW) {
							this.threadTimerCalibre12.start();
							for (int i = 0; i < 6; i++) {
								g.drawImage(this.armaAtual.getImagem(), x - 60,
										y - 50, 70, 100, null);
								x -= 40;
							}
						} else if (this.threadTimerCalibre12.getState() == Thread.State.TERMINATED) {
							this.threadTimerCalibre12 = new Thread(
									this.timerCalibre12);
							this.threadTimerCalibre12.start();
							for (int i = 0; i < 6; i++) {
								g.drawImage(this.armaAtual.getImagem(), x - 60,
										y - 50, 70, 100, null);
								x -= 40;
							}
						}
					}
					tiro.setVisible(false);
				}
			} else {
				if (tiro.isVisible()) {
					tiro.mover();
					g.drawImage(tiro.getImagem(), tiro.getX(), tiro.getY(),
							null);
				}
			}
		}
		for (int i = 0; i < this.mestre.getArma().getBalas().size(); i++) {
			if (this.mestre.getArma().getBalas().get(i).isVisible()) {
				this.mestre.getArma().getBalas().get(i).mover();
				g.drawImage(
						this.mestre.getArma().getBalas().get(i).getImagem(),
						this.mestre.getArma().getBalas().get(i).getX(),
						this.mestre.getArma().getBalas().get(i).getY() - 50,
						30, 10, null);
			}
		}
	}

	public void renderInimigos(Graphics2D g) {
		for (int i = 0; i < this.corvos.size(); i++) {
			if (this.corvos.get(i).isVisible()) {
				this.corvos.get(i).seMexer();
				g.drawImage(this.corvos.get(i).getImagem(), this.corvos.get(i)
						.getX(), this.corvos.get(i).getY(), null);
			}
		}
		for(int i = 0; i < this.inimigos.size(); i++){
				this.inimigos.get(i).seMexer();
				g.drawImage(this.inimigos.get(i).getImagem(), this.inimigos.get(i).getX(), this.inimigos.get(i).getY(), 80, 80, null);
		}
	}

	public void onUnLoad() {

	}

	private void renderLapada(Graphics2D g) {
		g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER));
		g.setTransform(AffineTransform.getScaleInstance(1, 1));
		g.drawImage(jogador.getImagem(), jogador.getX(), jogador.getY(),
				jogador.getLargura(), jogador.getAltura(), null);
		g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_IN));
		g.setTransform(AffineTransform.getScaleInstance(1, 1));
		g.drawImage(jogador.getImagem(), jogador.getX(), jogador.getY(),
				jogador.getLargura(), jogador.getAltura(), null);
		g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OUT));
		g.setTransform(AffineTransform.getScaleInstance(1, 1));
		g.drawImage(jogador.getImagem(), jogador.getX(), jogador.getY(),
				jogador.getLargura(), jogador.getAltura(), null);
		g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER));
		g.setTransform(AffineTransform.getScaleInstance(1, 1));
		g.drawImage(jogador.getImagem(), jogador.getX(), jogador.getY(),
				jogador.getLargura(), jogador.getAltura(), null);
		g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_IN));
		g.setTransform(AffineTransform.getScaleInstance(1, 1));
		g.drawImage(jogador.getImagem(), jogador.getX(), jogador.getY(),
				jogador.getLargura(), jogador.getAltura(), null);
	}

	private void renderPlataformas(Graphics2D g) {
		for (EntidadePlataforma e : listaDePlataformas) {
			g.drawImage(e.getSprite(), e.getX(), e.getY(), null);
		}
	}

	private void colisaoCorvo() {
		if (threadPancada.getState() == Thread.State.NEW) {
			for (Corvo c : corvos) {
				if (c.getBounds().intersects(jogador.getBounds())) {
					threadPancada.start();
					this.jogador.decramentarEnergia();
				}
			}
		} else if (threadPancada.getState() == Thread.State.TERMINATED) {
			threadPancada = new Thread(timePancada);
		}
	}

	public void onRender(Graphics2D g, int currentTick) {
		// Desenhando o cenario

		g.drawImage(cenarioPlataforma, -rolagem.x, -rolagem.y + 300, null);

		if (this.estado == PAUSANOJOGO) {
			try {
				this.inventario.displayInventario(g, currentTick);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		} else {
			if (!colisaoArma) {
				g.setColor(Color.BLACK);
				g.fillRect(this.arma.getX() - 10, this.arma.getY() - 30, 40, 40);
				g.setColor(Color.RED);
				g.drawString("GUN", this.arma.getX(), this.arma.getY());
			}
			renderInimigos(g);
			if (!colisaoArma2) {
				g.setColor(Color.WHITE);
				g.fillRect(this.arma2.getX() - 10, this.arma2.getY() - 30, 40,
						40);
				g.setColor(Color.RED);
				g.drawString("FACÃO", this.arma2.getX(), this.arma2.getY());
			}
			// Desenhando as plataformas
			renderPlataformas(g);
			desenharPlataformas.desenhar(g);
			for (Bau bau : this.bausDoGame) {
				bau.desenharEventos(g);
			}
			if (this.lapadaNoJogador) {
				if (this.threadTimerLapada.getState() == Thread.State.NEW) {
					this.threadTimerLapada.start();
					while (this.threadTimerLapada.getState() == Thread.State.TIMED_WAITING) {
						renderLapada(g);
					}
				} else if (this.threadTimerLapada.getState() == Thread.State.TERMINATED) {
					this.threadTimerLapada = new Thread(this.timerLapada);
					this.threadTimerLapada.start();
					while (this.threadTimerLapada.getState() == Thread.State.TIMED_WAITING) {
						renderLapada(g);
					}
				}
				this.lapadaNoJogador = false;
			} else {
				jogador.render(g);
			}
			g.drawImage(this.mestre.getImagem(), this.mestre.getPulo().getX(),
					this.mestre.getPulo().getY() - 100, 100, 120, null);
			renderProjeteis(g);
		}
		// colisao.desenharVertical(g);
	}

	public static void main(String[] args) {
		Game jogo = new TestStage();
		jogo.run();
	}
}

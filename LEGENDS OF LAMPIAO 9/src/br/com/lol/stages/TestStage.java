package br.com.lol.stages;

import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import br.com.lol.core.Game;
import br.com.lol.entidade.EntidadePlataforma;
import br.com.lol.entidade.Jogador;
import br.com.lol.gerenciadores.CollisionDetector;
import br.com.lol.gerenciadores.ImageManager;
import br.com.lol.gerenciadores.InputManager;

public class TestStage extends Game {
	// Constantes do jogo.
	/*
	 *	Aqui estão os estados do pulo, em Repouso, Pulando ou Descendo.
	 * */
	final private int ESTADOREPOUSO = 0;
	final private int ESTADOPULANDO = 1;
	final private int ESTADODESCENDO = 2;
	/*
	 *	Constantes que mostra a direção de lampiao.
	 * */
	final private int DIREITA = 1;
	final private int ESQUERDA = -1;
	/*
	 *	Aqui estão todos os estado dos movimentos de direita e esquerda.
	 * */
	final private int ESTADOANDANDO = 3;
	final private int ESTADOPARADO = 4;
	/*
	 *	Todas as imagens antes de serem inciadas
	 * */
	private BufferedImage cenarioPlataforma;
	private BufferedImage lampiaoDireita;
	private BufferedImage lampiaoEsquerda;
	private BufferedImage lampiaoPulandoDireita;
	private BufferedImage lampiaoPulandoEsquerda;
	List<EntidadePlataforma> listaDePlataformas;
	
	/*
	 *	Atributos do jogo.
	 * */
	private int chao;
	private Dimension dim;
	private Point rolagem;
	//	Jogador
	private Jogador jogador;
	//	Plataformas
	private EntidadePlataforma plataforma1;
	private EntidadePlataforma plataforma2;
	private EntidadePlataforma plataforma3;
	//	Atributo respinsavel por "DIZER" se a collisão.x
	private CollisionDetector colisao;
	/*
	 *	Método que inicializa todas as imagens do jogo.
	 * */
	private void inicializarImagens(){
		try{
			this.cenarioPlataforma = ImageManager.getInstance().
					loadImage("br/com/lol/imagens/campoPlataformado1600x900.png");
			this.lampiaoEsquerda = ImageManager.getInstance().
					loadImage("br/com/lol/imagens/lampiao_invertida.png");
			this.lampiaoDireita = ImageManager.getInstance().
					loadImage("br/com/lol/imagens/lampiao_normal.png");
			this.lampiaoPulandoDireita = ImageManager.getInstance().
					loadImage("br/com/lol/imagens/lampia_pulando_normal.png");
			this.lampiaoPulandoEsquerda = ImageManager.getInstance().
					loadImage("br/com/lol/imagens/lampia_pulando_invertido.png");
			
			}catch(IOException e){
				System.err.println(e.getMessage());
			}
	}
	/*
	 * Todas as atualizações das plataformas e outras coisas estão aqui
	 * */
	private void updateDimensionDireita(){
		if(jogador.getX() > getWidth()*0.55 ){
			rolagem.x += (int)jogador.getSpeed();
			listaDePlataformas = colisao.getListaDeEntidades();
			for(EntidadePlataforma i : listaDePlataformas){
				i.setX(i.getX()-(int)jogador.getSpeed());
			}
		}
	}
	
	private void updateDimensionEsquerda(){
		if(jogador.getX() < getWidth()*0.45 ){
			rolagem.x -= (int)jogador.getSpeed();
			listaDePlataformas = colisao.getListaDeEntidades();
			for(EntidadePlataforma i : listaDePlataformas){
				i.setX(i.getX()+(int)jogador.getSpeed());
			}
		}
	}
	
	/*
	 *	Inicializar dimension
	 *	Aumenta o tamanho da dimension a partir da posição das plataformas
	 */
	private void inicializarDimension(){
		dim = new Dimension(getWidth(), getHeight());
		for(EntidadePlataforma e : listaDePlataformas){
			if(dim.width < e.getX() + e.getLargura()){
				dim.width = e.getX() + e.getLargura();
			}
			if(dim.height < e.getY() + e.getAltura()){
				dim.height = e.getY() + e.getAltura();
			}
		}
	}
	
	/*
	 *	Inicializa todas as plataformas
	 * */
	private void inicializarPlataformas(){
		plataforma1 = new EntidadePlataforma(0, this.getHeight()-50, 1600, 20);
		plataforma2 = new EntidadePlataforma(150, this.getHeight()-100, 100, 20);
		plataforma3 = new EntidadePlataforma(300, this.getHeight()-150, 100, 20);
		
		plataforma1.init();
		plataforma2.init();
		plataforma3.init();
	}
	
	/*
	 *	Método que ouve todos os sons do teclado e apartir disso seta os estados no personagem. 
	 * */
	private void runControleJogo(){
		if(InputManager.getInstance().isPressed(KeyEvent.VK_UP)&&(jogador.getEstadoDoSalto()!=ESTADODESCENDO)){
			jogador.setEstadoDoSalto(ESTADOPULANDO);
		}
		if(InputManager.getInstance().isPressed(KeyEvent.VK_RIGHT)){
			jogador.setEstado(ESTADOANDANDO);
			jogador.setDirecao(DIREITA);
			jogador.andar(jogador.getDirecao());
		}
		if(InputManager.getInstance().isPressed(KeyEvent.VK_LEFT)){
			jogador.setEstado(ESTADOANDANDO);
			jogador.setDirecao(ESQUERDA);
			jogador.andar(jogador.getDirecao());
		}
		if(InputManager.getInstance().isPressed(KeyEvent.VK_DOWN)){
			
		}
	}
	
	/*												|
	 *	Metodos dos estados de lampião estão aqui   V
	 * */
	private void runEstadoPulando(){
		if(chao-100<jogador.getY()&&InputManager.getInstance().isPressed(KeyEvent.VK_UP)){
			jogador.setY(jogador.getY()-(int)jogador.getSpeed());
		} else {
			jogador.setEstadoDoSalto(ESTADODESCENDO);
		}
	}
	private void runEstadoDescendo(){
		jogador.setY(jogador.getY()+(int)jogador.getSpeed());
	}
	private void runEstadoMovendoDireita(){
		jogador.setX(jogador.getX()+((int)jogador.getSpeed()));
	}
	private void runEstadoMovendoEsquerda(){
		jogador.setX(jogador.getX()-((int)jogador.getSpeed()));
	}
	// ACABA AQUI OS METODOS DOS ESTADOS DE LAMPIÃO

	/*
	 *	Roda antes do jogo iniciar o loop.
	 * */
	public void onLoad() {
		inicializarImagens();
		inicializarPlataformas();
		rolagem = new Point(0,300);
		rolagem.x = 0;
		rolagem.y = 300;
		jogador = new Jogador(100,this.getHeight() - 300);
		listaDePlataformas = new ArrayList<>();
		listaDePlataformas.add(plataforma1);
		listaDePlataformas.add(plataforma2);
		listaDePlataformas.add(plataforma3);
		colisao = new CollisionDetector(listaDePlataformas);
		inicializarDimension();
	}

	/*
	 *	Método que roda a cada tick.
	 * */
	public void onUpdate(int currentTick) {
		jogador.getSpritesDireita().update(currentTick);
		jogador.getSpritesEsquerda().update(currentTick);
		runControleJogo();
		if(jogador.getEstadoDoSalto()==ESTADOPULANDO){
			runEstadoPulando();
		} else if(colisao.colisaoPlataforma(jogador)){
			jogador.setEstadoDoSalto(ESTADOREPOUSO);
			chao = jogador.getY();
		} else {
			jogador.setEstadoDoSalto(ESTADODESCENDO);
			runEstadoDescendo();
		}
		if(jogador.getEstado()==ESTADOANDANDO){
			if(jogador.getDirecao()==DIREITA){
				if(jogador.getX()<getWidth()*0.60){
					runEstadoMovendoDireita();
				}
				updateDimensionDireita();
				if(InputManager.getInstance().isReleased(KeyEvent.VK_RIGHT)){
					jogador.setEstado(ESTADOPARADO);
				}
			} else {
				if(jogador.getX()>getWidth()*0.40){
					runEstadoMovendoEsquerda();
				}
				updateDimensionEsquerda();
				if(InputManager.getInstance().isReleased(KeyEvent.VK_LEFT)){
					jogador.setEstado(ESTADOPARADO);
				}
			}
		} 
	}


	public void onUnLoad() {
		
	}


	public void onRender(Graphics2D g) {
		//	Desenhando o cenario
		g.drawImage(cenarioPlataforma, -rolagem.x,-rolagem.y,  null);
		//	Desenhando as plataformas
		g.drawImage(plataforma1.getSprite(), plataforma1.getX(), plataforma1.getY(), null);
		g.drawImage(plataforma2.getSprite(), plataforma2.getX(), plataforma2.getY(), null);
		g.drawImage(plataforma3.getSprite(), plataforma3.getX(), plataforma3.getY(), null);
		g.drawImage(jogador.getImagem(), jogador.getX(), jogador.getY(),80, 80, null);
	}
	
	public static void main(String[] args) {
		Game jogo = new TestStage();
		jogo.run();
	}

}

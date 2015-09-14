package br.grupointegrado.SpaceInvaders;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.FillViewport;

import javax.print.DocFlavor;

import sun.print.SunPrinterJobService;


/**
 * Created by ACÁCIO on 03/08/2015.
 */
public class TelaJogo extends TelaBase {

        private OrthographicCamera camera;
        private SpriteBatch batch;
        private Stage palco;
        private Stage palcoinformacoes;
        private BitmapFont fonte;
        private Label lbPontuacao;
        private Label lbGameOver;
        private Image jogador;
        private Texture TextureJogadorp;
        private Texture TextureJogadorr;
        private Texture TextureJogadorl;
        private boolean indor;
        private boolean indol;
        private boolean atirando;
        private Array<Image> tiros = new Array<Image>();
        private Texture textureTiro;

        private Sound somTiro;
        private Sound somExplosao;
        private Sound somGameOver;
        private Music musicaFundo;

        private Array<Texture> texturasExplosao = new Array<Texture>();
        private Array<Explosao> explosoes = new Array<Explosao>();

        private Texture textureMeteoro1;
        private Texture textureMeteoro2;
        private Array<Image> meteoros1 = new Array<Image>();
        private Array<Image> meteoros2 = new Array<Image>();


    /**
     * contrutor padrão da tela de jogos
     * @aram game Referencia para a classe principal
     */

    public TelaJogo(mainSpaceInvaders game){
        super(game);

    }

    /**
     * chamado quando a tela é exibida
     */
    @Override
    public void show() {
        camera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        batch  = new SpriteBatch();
        palco  = new Stage(new FillViewport(camera.viewportWidth, camera.viewportHeight, camera));
        palcoinformacoes  = new Stage(new FillViewport(camera.viewportWidth, camera.viewportHeight, camera));

        initFont();
        initInformacoes();
        initJogador();
        initTexturas();
        initSons();


    }

    private void initSons(){
        somTiro     = Gdx.audio.newSound(Gdx.files.internal("sounds/shoot.mp3"));
        somExplosao = Gdx.audio.newSound(Gdx.files.internal("sounds/explosion.mp3"));
        somGameOver = Gdx.audio.newSound(Gdx.files.internal("sounds/gameover.mp3"));
        musicaFundo = Gdx.audio.newMusic(Gdx.files.internal("sounds/backgraund.mp3"));
        musicaFundo.setLooping(true);
    }

    private void initTexturas() {
        textureTiro = new Texture("sprites/shot.png");

        textureMeteoro1 = new Texture("sprites/enemie-1.png");
        textureMeteoro2 = new Texture("sprites/enemie-2.png");

        for (int i = 1; i <= 17; i++){
            Texture text = new Texture("sprites/explosion-" + i + ".png");
            texturasExplosao.add(text);
        }





    }


    /**
     * instacia os objetos do jogador e adiciona o palco
     */
    private void initJogador(){
        TextureJogadorp = new Texture ("sprites/player.png");
        TextureJogadorr = new Texture ("sprites/player-right.png");
        TextureJogadorl = new Texture("sprites/player-left.png");
        jogador = new Image(TextureJogadorp);
        float x = camera.viewportWidth / 2 - jogador.getWidth() /2;
        float y = 0;
        jogador.setPosition(x, y);
        palco.addActor(jogador);

    }

    /**
     * intacia as informações dos labels,  ou gameover
     */
    private void initInformacoes() {
        Label.LabelStyle lbEstilo = new Label.LabelStyle();
        lbEstilo.font = fonte;
        lbEstilo.fontColor = Color.WHITE;


        lbPontuacao = new Label("0 pontos",lbEstilo);
        palcoinformacoes.addActor(lbPontuacao);

        lbGameOver = new Label("game Over", lbEstilo);
        lbGameOver.setVisible(false);
        palcoinformacoes.addActor(lbGameOver);


    }


    /**
     * medoto intancia os objetos de fonte
     */
    private void initFont() {// { fonte = new BitmapFont();

        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/roboto.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter param = new FreeTypeFontGenerator.FreeTypeFontParameter();
        param.color = Color.WHITE;
        param.size = 24;
        param.shadowOffsetY = 1;
        param.shadowOffsetX = 1;
        param.shadowColor = Color.BLUE;

        fonte = generator.generateFont(param);
        generator.dispose();
    }
    /**
     * chamado todo quadro de atualizacao ou fps
     * @param delta   tempo entre um quadro e outro em segundo
     */
    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(.15f, .15f, .25f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        lbPontuacao.setPosition(10, camera.viewportHeight - lbPontuacao.getPrefHeight() - 20);
        lbPontuacao.setText(pontuacao + " pontos ");

        lbGameOver.setPosition(camera.viewportWidth / 2 - lbGameOver.getPrefWidth() / 2, camera.viewportHeight / 2);
        lbGameOver.setVisible(gameOver == true);


        atualizarExplososes(delta);



        if (gameOver == false) {
            if (!musicaFundo.isPlaying()) {
                musicaFundo.play();
            }
                capturaTeclas();
                atualizaJogador(delta);
                atualizartiros(delta);
                //autalizar meteoros
                atualizarMeteoros(delta);
                detectarColizoes(meteoros1, pontuacao += 5);
                detectarColizoes(meteoros2, pontuacao += 15);
            }else{
                if (musicaFundo.isPlaying()){
                    musicaFundo.stop();
                }
        }



        // atualia e desenha o palco na tela
        palco.act(delta);
        palco.draw();
        palcoinformacoes.act(delta);
        palcoinformacoes.draw();
    }

    private void atualizarExplososes(float delta){
        for (Explosao explosao : explosoes) {
            //verefica se a explosao chegou o fim
            if (explosao.getEstagio() <= 16) {
                explosoes.removeValue(explosao, true); // remove a explosap
                explosao.getAtor().remove(); //remove ator do paulo
            } else
                explosao.atualizar(delta);
        }
    }
    //declaracao do objetos
    private Rectangle recJogador = new Rectangle();
    private Rectangle recTiro = new Rectangle();
    private Rectangle recMeteoro = new Rectangle();
    private int pontuacao = 0;
    private boolean gameOver;

    private void detectarColizoes(Array<Image> meteoros, int valePonto) {
        recJogador.set(jogador.getX(), jogador.getY(), jogador.getImageWidth(),jogador.getImageHeight());

        for (Image meteoro : meteoros){
            recMeteoro.set(meteoro.getX(), meteoro.getY(), meteoro.getImageWidth(),meteoro.getImageHeight());
            //detecta colisoes com os tiros
            for (Image tiro : tiros){
                recTiro.set(tiro.getX(), tiro.getY(), tiro.getImageWidth(),tiro.getImageHeight());
                if (recMeteoro.overlaps(recTiro)){
                    //ocorre uma colisão do tiro com o meteoro1
                    pontuacao += 5;
                    tiro.remove();
                    tiros.removeValue(tiro, true);
                    meteoro.remove();
                    meteoros.removeValue(meteoro, true);
                    criarExplosao(meteoro.getX()+ meteoro.getWidth()/2, meteoro.getY()+ meteoro.getHeight()/2);
                }

            }
            //detecta colisoes com os tiros
            for (Image tiro : tiros){
                recTiro.set(tiro.getX(), tiro.getY(), tiro.getImageWidth(),tiro.getImageHeight());
                if (recMeteoro.overlaps(recTiro)){
                    //ocorre uma colisão do tiro com o meteoro1
                    pontuacao += 5;
                    tiro.remove();
                    tiros.removeValue(tiro, true);
                    meteoro.remove();
                    meteoros2.removeValue(meteoro, true);
                }

            }

            if (recJogador.overlaps(recMeteoro)) {
                //ocorre colisao entre
                gameOver = true;
                somGameOver.play();
            }
        }
    }

    /**
     * crea a explosao na posicao
     * @param x
     * @param y
     */



    private void criarExplosao(float x, float y) {

        Image ator = new Image(texturasExplosao.get(0));
        ator.setPosition(x - ator.getWidth()/2, y - ator.getHeight()/2);
        palco.addActor(ator);

        Explosao explosao = new Explosao(ator, texturasExplosao);
        explosoes.add(explosao);
        somExplosao.play();
    }

    private void atualizarMeteoros(float delta) {

        int gtdmeteoros = meteoros1.size + meteoros2.size;  //retorna a quantidade de meteoros

        if (gtdmeteoros < 15) {
           // int tipo = MathUtils.random(1, 3);  //tipo de meteoro que vai cair sempre no caso o 3 não aparece se fosse 1 a 5 5 tambem não
            int tipo = MathUtils.random(1, 5);  //tipo de meteoro que vai cair sempre no caso o 3 não aparece se fosse 1 a 5 5 tambem não
            if (tipo == 1) {
                //creia meteoro 1
                Image meteoro = new Image(textureMeteoro1);
                float x = MathUtils.random(0, camera.viewportWidth - meteoro.getWidth());
                float y = MathUtils.random(camera.viewportHeight, camera.viewportHeight * 2);
                meteoro.setPosition(x, y);
                meteoros1.add(meteoro);
                palco.addActor(meteoro);
            } else if (tipo == 2){
                Image meteoro = new Image(textureMeteoro2);
                float x = MathUtils.random(0, camera.viewportWidth - meteoro.getWidth());
                float y = MathUtils.random(camera.viewportHeight, camera.viewportHeight * 2);
                meteoro.setPosition(x, y);
                meteoros2.add(meteoro);
                palco.addActor(meteoro);
            }

        }


        float velocidade1 = 100;
        for (Image meteoro : meteoros1) {
            float x = meteoro.getX();
            float y = meteoro.getY() - velocidade1 * delta;
            meteoro.setPosition(x, y);
            if (meteoro.getY() + meteoro.getHeight() < 0) {
                meteoro.remove(); //remove do palco;
                meteoros1.removeValue(meteoro, true); //remove da lista
            }
        }
        float velocidade2 = 250;
            for (Image meteoro : meteoros2) {
                float x = meteoro.getX();
                float y = meteoro.getY() - velocidade2 * delta;
                meteoro.setPosition(x, y);
                if (meteoro.getY() + meteoro.getHeight() < 0) {
                    meteoro.remove(); //remove do palco;
                    meteoros2.removeValue(meteoro, true); //remove da lista
                }
            }
        }

    //tempo do tiro em segundeos
    private float intevaloTiro = 0;
   // minimo de tempo para novo tiro
    private final float min_intervalo_tiros = 0.4f;


    private void atualizartiros(float delta) {
        float velocidade = 300;  //velocidado de movimentacao do tiro
        intevaloTiro = intevaloTiro + delta; //aculula o tempo do tiro
        if (atirando){
            //verefica se to tempo do tiro esta permetido
           if (intevaloTiro >= min_intervalo_tiros) {

                Image tiro = new Image(textureTiro);
                float x = jogador.getX() + jogador.getWidth() / 2 - tiro.getWidth() / 2;
                float y = jogador.getY() + jogador.getHeight();


                tiro.setPosition(x, y);
                tiros.add(tiro);
                palco.addActor(tiro);
                intevaloTiro = 0;
                somTiro.play();
            }
        }
        //percorre todos os tiros existentes
        for (Image tiro : tiros){
            float x = tiro.getX();
            float y = tiro.getY() + velocidade * delta;
            tiro.setPosition(x,y);
            // remove os tiros da tela
            if (tiro.getY() > camera.viewportHeight){
                tiros.removeValue(tiro,true); //remove da lista
                tiro.remove(); //remove do palco;

            }

        }





    }

    /**
     * atualiza a posicao do jogador;
     * @param delta
     */
    private void atualizaJogador(float delta) {
        float velocidade = 200;
        if (indor){
            //verefica se o jogador esta dentro da tela
            if (jogador.getX() < camera.viewportWidth - jogador.getWidth()) {
                float X = jogador.getX() + velocidade * delta;
                float y = jogador.getY();
                jogador.setPosition(X, y);

            }
        }
        if (indol){
            //verefica se o jogador esta dentro da tela
            if (jogador.getX() > 0) {
                float X = jogador.getX() - velocidade * delta;
                float y = jogador.getY();
                jogador.setPosition(X, y);
            }
        }

        if (indor){
            jogador.setDrawable(new SpriteDrawable(new Sprite(TextureJogadorr)));
             }else if (indol){
            jogador.setDrawable(new SpriteDrawable(new Sprite(TextureJogadorl)));
        }else{
            jogador.setDrawable(new SpriteDrawable(new Sprite(TextureJogadorp)));
        }

    }


    /**
     * Verificando qual tecla esta precionada
     */

    private void capturaTeclas() {
        indol = false;
        indor = false;
        atirando = false;

        if(Gdx.input.isKeyPressed(Input.Keys.LEFT)){
        indol = true;
        }
        if(Gdx.input.isKeyPressed(Input.Keys.RIGHT)){
            indor = true;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.SPACE)){
            atirando = true;
        }

    }

    /**
     * É chanmado sempre que é alterado o tamanho da tela s
     * @param width novo valor de largura da tela
     * @param height novo valor da altura da tela
     */

    @Override
    public void resize(int width, int height) {

        camera.setToOrtho(false, width, height);
        camera.update();
    }







    /**
     * sempre é chamado quando for minimizado
     */

    @Override
    public void pause() {

    }


    /**
     * é chamado sempre que o jogo é chamado para o primeiro plano
     */
    @Override
    public void resume() {

    }

    /**
     * é chamado quando a tela for destruida
     */
    @Override
    public void dispose() {

        batch.dispose();
        palco.dispose();
        palcoinformacoes.dispose();
        fonte.dispose();
        TextureJogadorl.dispose();
        TextureJogadorp.dispose();
        TextureJogadorr.dispose();
        textureTiro.dispose();
        textureMeteoro1.dispose();
        textureMeteoro2.dispose();
        somTiro.dispose();
        somExplosao.dispose();
        somGameOver.dispose();
        musicaFundo.dispose();

        for(Texture text : texturasExplosao){
            text.dispose();
        }

    }
}

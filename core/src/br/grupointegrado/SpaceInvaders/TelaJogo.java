package br.grupointegrado.SpaceInvaders;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
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
        private BitmapFont fonte;
        private Label lbPontuacao;
        private Image jogador;
        private Texture TextureJogadorp;
        private Texture TextureJogadorr;
        private Texture TextureJogadorl;
        private boolean indor;
        private boolean indol;
    /**
     * contrutor padrão da tela de jogos
     * @aram game Referencia para a classe principal
     */

    public TelaJogo(mainSpaceInvaders game){
        super (game);

    }

    /**
     * chamado quando a tela é exibida
     */
    @Override
    public void show() {
        camera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        batch  = new SpriteBatch();
        palco  = new Stage(new FillViewport(camera.viewportWidth, camera.viewportHeight, camera));


        initFont();
        initInformacoes();
        initJogador();


    }





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


    private void initInformacoes() {
        Label.LabelStyle lbEstilo = new Label.LabelStyle();
        lbEstilo.font = fonte;
        lbEstilo.fontColor = Color.WHITE;

        lbPontuacao = new Label("0 pontos",lbEstilo);
        palco.addActor(lbPontuacao);

    }

    private void initFont() {
        fonte = new BitmapFont();
    }


    /**
     * chamado todo quadro de atualizacao ou fps
     * @param delta   tempo entre um quadro e outro em segundo
     */
    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(.15f, .15f, .25f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        lbPontuacao.setPosition(10, camera.viewportHeight - 20);

        capturaTeclas();
        atualizaJogador(delta);

        palco.act(delta);
        palco.draw();
    }

    /**
     * atualiza a posicao do jogador;
     * @param delta
     */
    private void atualizaJogador(float delta) {
        float velocidade = 200;
        if (indor){
            if (jogador.getX() < camera.viewportWidth - jogador.getWidth()) {
                float X = jogador.getX() + velocidade * delta;
                float y = jogador.getY();
                jogador.setPosition(X, y);

            }
        }
        if (indol){
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

        if(Gdx.input.isKeyPressed(Input.Keys.LEFT)){
        indol = true;
      }
        if(Gdx.input.isKeyPressed(Input.Keys.RIGHT)){
            indor = true;
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
        fonte.dispose();
        TextureJogadorl.dispose();
        TextureJogadorp.dispose();
        TextureJogadorr.dispose();
    }
}

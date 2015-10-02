package br.grupointegrado.SpaceInvaders;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageTextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
import com.badlogic.gdx.utils.viewport.FillViewport;

/**
 * Created by AC�CIO on 14/09/2015.
 */
    public class TelaMenu extends TelaBase {

    private OrthographicCamera camera;
    private Stage palco;
    private ImageTextButton btnIniciar;
    private Label lbTitulo;
    private Label lbpontuacao;

    private BitmapFont fonteTitulo;
    private BitmapFont fonteBotoes;

    private Texture texturaBotao;               //textura normal do bot�o
    private Texture texturabotaoPressionado;    //textura prissionado

    public TelaMenu(mainSpaceInvaders game) {
        super(game);
    }

    @Override
    public void show() {
        camera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        palco  = new Stage(new FillViewport(camera.viewportWidth, camera.viewportHeight, camera));
        Gdx.input.setInputProcessor(palco); // defineo palco como processador de entrada


        initFonts();
        initLabels();
        intBotoes();

    }

    private void intBotoes() {
        texturaBotao = new Texture("buttons/button.png");
        texturabotaoPressionado = new Texture("buttons/button-down.png");


        ImageTextButton.ImageTextButtonStyle estilo = new ImageTextButton.ImageTextButtonStyle();
        estilo.font = fonteBotoes;
        estilo.up = new SpriteDrawable( new Sprite(texturaBotao));
        estilo.down = new SpriteDrawable(new Sprite(texturabotaoPressionado));

        btnIniciar = new ImageTextButton(" inicar jogo ",estilo);
        palco.addActor(btnIniciar);

        btnIniciar.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                //evento do click do bot�o
                game.setScreen(new TelaJogo(game));
            }
        });



    }

    private void initLabels() {
        Label.LabelStyle estilo = new Label.LabelStyle();
        estilo.font = fonteTitulo;

        lbTitulo = new Label("Space Invaders", estilo);
        palco.addActor(lbTitulo);

        Preferences preferencias = Gdx.app.getPreferences("SpaceInvaders");
        int pontuacaoMaxima = preferencias.getInteger("pontuacao_maxima",0);

        estilo = new Label.LabelStyle();
        estilo.font = fonteBotoes;

        lbpontuacao = new Label("Pontuacao_maxima: " + pontuacaoMaxima + " pontos", estilo);
        palco.addActor(lbpontuacao);


    }

    private void initFonts() {
        FreeTypeFontGenerator gerador = new FreeTypeFontGenerator(Gdx.files.internal("fonts/roboto.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter params = new FreeTypeFontGenerator.FreeTypeFontParameter();
        params.size = 48;
        params.color = new Color(.25f, .25f, .25f, 1);  // ton azul
        params.shadowOffsetX = 2;
        params.shadowOffsetY = 2;
        params.shadowColor = Color.BLACK;
        fonteTitulo = gerador.generateFont(params);



        params = new FreeTypeFontGenerator.FreeTypeFontParameter();
        params.size = 32;
        params.color = Color.BLACK;

        fonteBotoes = gerador.generateFont(params);






        gerador.dispose();;
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1f, 1f, 1f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        atualizarLabel();
        atualizarBotoes();

        palco.act(delta);
        palco.draw();

    }

    private void atualizarBotoes() {
        float x = camera.viewportWidth /2 - btnIniciar.getPrefWidth()/2;
        float y = camera.viewportHeight /2 - btnIniciar.getPrefHeight() / 2;

        btnIniciar.setPosition(x,y);
    }

    private void atualizarLabel() {

        float x = camera.viewportWidth/2- lbTitulo.getPrefWidth()/2;
        float y = camera.viewportHeight-100;

        lbTitulo.setPosition(x, y);

        x = camera.viewportWidth /2 - lbpontuacao.getPrefWidth()/2;
        y = 100;

        lbpontuacao.setPosition(x, y);


    }

    @Override
    public void resize(int width, int height) {
        camera.setToOrtho(false, width, height);
        camera.update();
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void dispose() {

        palco.dispose();
        fonteTitulo.dispose();
        texturaBotao.dispose();
        texturabotaoPressionado.dispose();
        fonteBotoes.dispose();

    }
}

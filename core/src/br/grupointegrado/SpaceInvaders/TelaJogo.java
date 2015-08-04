package br.grupointegrado.SpaceInvaders;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.viewport.FillViewport;

/**
 * Created by ACÁCIO on 03/08/2015.
 */
public class TelaJogo extends TelaBase {

        private OrthographicCamera camera;
        private SpriteBatch batch;
        private Stage palco;
        private BitmapFont fonte;
        private Label lbPontuacao;

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

        lbPontuacao.setPosition(10,camera.viewportHeight-20);

        palco.act(delta);
        palco.draw();



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
    }
}

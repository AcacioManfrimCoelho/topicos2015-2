package br.grupointegrado.SpaceInvaders;

import com.badlogic.gdx.Screen;

import java.awt.font.TextLayout;

/**
 * Created by AC�CIO on 03/08/2015
 */
public abstract class TelaBase implements Screen{

    protected mainSpaceInvaders game ;

    public TelaBase(mainSpaceInvaders game){
        this.game = game;

    }

    @Override
    public void hide() {
        dispose();
    }
}

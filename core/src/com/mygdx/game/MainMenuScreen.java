package com.mygdx.game;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.utils.ScreenUtils;

public class MainMenuScreen implements Screen {

    final Galaxilize game;

    OrthographicCamera camera;

    public MainMenuScreen(final Galaxilize game){
        this.game = game;

        camera = new OrthographicCamera();

        camera.setToOrtho(false,800,800);
    }

    public void render(float delta) {
        ScreenUtils.clear(0,0,0,1);

        camera.update();
        game.batch.setProjectionMatrix(camera.combined);

        game.batch.begin();
        game.font.draw(game.batch, "GALAXILZE", 100,100);

    }

}

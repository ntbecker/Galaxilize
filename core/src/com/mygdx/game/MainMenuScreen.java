package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.utils.ScreenUtils;

public class MainMenuScreen implements Screen {


    final Galaxilize game;
    OrthographicCamera camera;

    /**
     * Constructor for main menu
     * @param game - The main game object, Galaxilize.java in this case
     */
    public MainMenuScreen(final Galaxilize game){
        this.game = game;
        camera = new OrthographicCamera();
        camera.setToOrtho(false,800,800);
    }

    /**
     * Render method, called every frame (60 fps)
     * @param delta The time in seconds since the last render.
     */
    public void render(float delta) {
        // Fill screen with black
        ScreenUtils.clear(0,0,0,1);

        // Update the camera
        camera.update();
        game.batch.setProjectionMatrix(camera.combined);

        // Draw sprites here
        game.batch.begin();
        game.titleFont.draw(game.batch, "GALAXILZE", 0,800,800,1,false);
        game.subTitleFont.draw(game.batch, "Click to start!", 0,650,800,1,false);
        game.batch.end();

        if (Gdx.input.isTouched()) {
            game.setScreen(new GameScreen(game));
            dispose();
        }
    }


    // These methods must be implemented as part of the Screen interface
    // libGDX tutorial shows that best practice is to leave them blank like so
    @Override
    public void show() {

    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }

}

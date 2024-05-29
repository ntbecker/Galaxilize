package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.ScreenUtils;

public class GameScreen implements Screen {

    private final Galaxilize game;
    private OrthographicCamera camera;

    private Texture dropImage;
    private Texture bucketImage;
    private Player p;

    public GameScreen(final Galaxilize game){
        this.game = game;

        // Create camera
        camera = new OrthographicCamera();
        camera.setToOrtho(false,800,800);

        p = new Player(200,200,0,0,3,10);
    }

    public void render(float delta) {
        ScreenUtils.clear(0,0,0,1);

        camera.update();
        game.batch.setProjectionMatrix(camera.combined);

        game.batch.begin();

        p.
        p.updatePos();
        p.draw(game.batch);
        // To draw PhysicsObjects, call the draw method and pass game variable (Reminder: game variable contains Galaxilize object, as in the instance of the program)

        game.batch.end();
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

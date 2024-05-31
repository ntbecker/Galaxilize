package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.ScreenUtils;

import java.util.ArrayList;

public class GameScreen implements Screen {

    private final Galaxilize game;
    private OrthographicCamera camera;

    private Player player;
    private Asteroid a;
    private Texture background;

    private int camX;
    private int camY;

    private ArrayList<PhysicsObject> physicsObjectsList;

    public GameScreen(final Galaxilize game){
        this.game = game;

        // Create camera
        camera = new OrthographicCamera();
        camera.setToOrtho(false,800,800);
        camX = 0;
        camY = 0;

        background = new Texture("background.png");
        background.setWrap(Texture.TextureWrap.Repeat, Texture.TextureWrap.Repeat);

        physicsObjectsList = new ArrayList<PhysicsObject>();

        player = new Player(200,200,0,5,1,10);
        player.setIsHooked(true);
        physicsObjectsList.add(player);
        physicsObjectsList.add(new Asteroid(300,220,0,2,100,10));
        player.setHookedAsteroid((Asteroid)physicsObjectsList.get(1));
    }

    public void render(float delta) {
        ScreenUtils.clear(0,0,0,1);

        camera.update();
        game.batch.setProjectionMatrix(camera.combined);

        game.batch.begin();
        // Background (800 by 800 scrolling texture always drawn to the camera)
        game.batch.draw(background,camX,camY,camX,-camY,800,800);

        // Player objects for testing, potentially obsolete if the physicsObjectsList works without issue
//        p.checkCollision(a);
//        a.checkCollision(p);
//        p.updateHook();
//        p.updatePos();
//        a.updatePos();
//        p.draw(game.batch);
//        a.draw(game.batch);

        for(int i = 0; i < physicsObjectsList.size(); i++){
            for(int j = 0; j < physicsObjectsList.size(); j++){
                if(j != i){
                    physicsObjectsList.get(i).checkCollision(physicsObjectsList.get(j));
                }
            }
        }
        player.updateHook();
        for(int i = 0; i < physicsObjectsList.size(); i++){
            physicsObjectsList.get(i).updatePos();
            physicsObjectsList.get(i).draw(game.batch);
        }
        // To draw PhysicsObjects, call the draw method and pass game variable (Reminder: game variable contains Galaxilize object, as in the instance of the program)

        game.batch.end();

        // Code for moving the camera and scrolling the background
        if (Gdx.input.isTouched()) {
            int changeX = (Gdx.input.getX()-400)/100;
            // Input uses y-down coords, so it needs to be inverted
            int changeY = (Gdx.input.getY()-400)/-100;

            camera.translate((float)(changeX),(float)(changeY));
            camX += changeX;
            camY += changeY;
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

package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.ScreenUtils;

import java.util.ArrayList;

public class GameScreen implements Screen {

    private final Galaxilize game;
    private OrthographicCamera camera;

    private Player player;
    private Asteroid a;
    private Texture background;

    private int counter;
    private int slow;


    private ArrayList<PhysicsObject> physicsObjectsList;

    public GameScreen(final Galaxilize game){
        // Game object to draw to
        this.game = game;

        // Counters used for slow motion
        counter = 0;
        slow = 1;

        // Create camera
        camera = new OrthographicCamera();
        camera.setToOrtho(false,800,800);

        // Initialize background texture
        background = new Texture("background.png");
        background.setWrap(Texture.TextureWrap.Repeat, Texture.TextureWrap.Repeat);

        // List where all physics affected objects are stored
        physicsObjectsList = new ArrayList<PhysicsObject>();

        // Initialize player
        player = new Player(200,200,0,0,10,10);

        // Add all objects to list for rendering and colliding
        physicsObjectsList.add(player);
        physicsObjectsList.add(new Asteroid(300,200,0,10,1,10));
        physicsObjectsList.add(new Asteroid(350,220,-1,1,10,10));
        physicsObjectsList.add(new Asteroid(300,280,0,-1,10,10));
        physicsObjectsList.add(new Asteroid(400,420,-1,-1,10,10));
        physicsObjectsList.add(new Asteroid(350,290,0,0,10,10));


        // Attach the player to the first Asteroid in the list
        player.setIsHooked(true);
        player.setHookedAsteroid((Asteroid)physicsObjectsList.get(1));
    }

    public void render(float delta) {
        ScreenUtils.clear(0,0,0,1);

        // Slow motion code cause slow motion is cool
        counter++;
        slow = 1;
        if(Gdx.input.isKeyPressed(Input.Keys.E)){
            slow = 4;
        }

        if(counter%slow == 0) {
            for (int i = 0; i < physicsObjectsList.size(); i++) {
                for (int j = 0; j < physicsObjectsList.size(); j++) {
                    if (j != i) {
                        physicsObjectsList.get(i).checkCollision(physicsObjectsList.get(j));
                    }
                }
            }
            player.updateHook();
            for (int i = 0; i < physicsObjectsList.size(); i++) {
                physicsObjectsList.get(i).updatePos();
            }
        }

        // Update camera position
        camera.position.set((float)player.getPosX(),(float)player.getPosY(),0);
        camera.update();
        game.batch.setProjectionMatrix(camera.combined);

        // Drawing code starts
        game.batch.begin();
        // Background (800 by 800 scrolling texture always drawn to the camera)
        game.batch.draw(background,camera.position.x-400,camera.position.y-400,(int)camera.position.x,(int)(-camera.position.y),800,800);

        // Draw all physics objects
        for(int i = 0; i < physicsObjectsList.size(); i++){
            // To draw PhysicsObjects, call the draw method and pass game variable (Reminder: game variable contains Galaxilize object, as in the instance of the program)
            physicsObjectsList.get(i).draw(game.batch);
        }

        // Draw hook between player and asteroid
        if(player.getHookedAsteroid() != null){
            game.shapeDrawer.line((float)player.getPosX(),(float)player.getPosY(),(float)player.getHookedAsteroid().getPosX(),(float)player.getHookedAsteroid().getPosY());
        }

        // Drawing code ends
        game.batch.end();

        if(Gdx.input.isKeyPressed(Input.Keys.SPACE)){
            player.setHookedAsteroid(null);
            player.setIsHooked(false);
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

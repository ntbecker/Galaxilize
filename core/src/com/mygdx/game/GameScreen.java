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
    private Texture slowEffect;

    private double speedFactor;


    private ArrayList<PhysicsObject> physicsObjectsList;

    public GameScreen(final Galaxilize game){
        // Game object to draw to
        this.game = game;

        // Create camera
        camera = new OrthographicCamera();
        camera.setToOrtho(false,800,800);

        // Initialize background texture
        background = new Texture("background.png");
        background.setWrap(Texture.TextureWrap.Repeat, Texture.TextureWrap.Repeat);

        // Initialize screen effect textures
        slowEffect = new Texture("slowmotion_effect.png");


        // List where all physics affected objects are stored
        physicsObjectsList = new ArrayList<PhysicsObject>();

        // Initialize player
        player = new Player(200,200,0,0,10,10);

        // Add all objects to list for rendering and colliding
        physicsObjectsList.add(player);
        physicsObjectsList.add(new Asteroid(300,200,0,10,100,10));
        physicsObjectsList.add(new Asteroid(350,220,-1,1,10,10));
        physicsObjectsList.add(new Asteroid(300,280,0,-1,10,10));
        physicsObjectsList.add(new Asteroid(400,420,-1,-1,10,10));
        physicsObjectsList.add(new Asteroid(350,290,0,0,10,10));

        // Attach the player to the first Asteroid in the list
        player.setIsHooked(true);
        player.setHookedAsteroid((Asteroid)physicsObjectsList.get(1));

        // Speed factor used in all physics methods for slow motion effect, pass into all update and collision methods
        speedFactor = 1;
    }

    public void render(float delta) {
        ScreenUtils.clear(0,0,0,1);
        // Slows the game's physics down to 1/5th speed
        if(Gdx.input.isKeyPressed(Input.Keys.E) && speedFactor > 0.2){
            speedFactor -= 0.08;
        }else if(speedFactor < 0.2){
            speedFactor = 0.2;
        }

        if(!Gdx.input.isKeyPressed(Input.Keys.E) && speedFactor < 1){
            speedFactor += 0.08;
        }else if(speedFactor > 1){
            speedFactor = 1;
        }


        //AsteroidSpawning.update(physicsObjectsList);
        for (int i = 0; i < physicsObjectsList.size(); i++) {
            for (int j = 0; j < physicsObjectsList.size(); j++) {
                if (j != i) {
                    physicsObjectsList.get(i).checkCollision(physicsObjectsList.get(j),speedFactor);
                }
            }
        }
        player.updateHook(speedFactor);
        for (int i = 0; i < physicsObjectsList.size(); i++) {
            physicsObjectsList.get(i).updatePos(speedFactor);
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

        // Draws slow motion screen effects
        if(speedFactor < 1){
            game.batch.setColor(1,1,1,(float)(-1.25*(speedFactor-1)));

            // Aiming line for orbiting asteroid or player, depending on which is bigger
            if(player.getHookedAsteroid() != null && player.getMass() >= player.getHookedAsteroid().getMass()){
                double velDir = Math.atan2(player.getHookedAsteroid().getVelY(),player.getHookedAsteroid().getVelX());
                float posX = (float)player.getHookedAsteroid().getPosX();
                float posY = (float)player.getHookedAsteroid().getPosY();
                game.shapeDrawer.line(posX,posY,posX+50*(float)Math.cos(velDir),posY+50*(float)Math.sin(velDir), 3f);
            }else if(player.getHookedAsteroid() != null && player.getMass() < player.getHookedAsteroid().getMass()){
                double velDir = Math.atan2(player.getVelY(),player.getVelX());
                float posX = (float)player.getPosX();
                float posY = (float)player.getPosY();
                game.shapeDrawer.line(posX,posY,posX+50*(float)Math.cos(velDir),posY+50*(float)Math.sin(velDir), 3f);
            }

            // Gradient around edges of screen
            game.batch.draw(slowEffect,camera.position.x-400,camera.position.y-400);
            game.batch.setColor(1,1,1,1);
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

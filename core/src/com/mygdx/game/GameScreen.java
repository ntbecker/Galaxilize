package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.graphics.g2d.freetype.*;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;
import com.badlogic.gdx.utils.viewport.ExtendViewport;

import javax.swing.*;
import java.text.DecimalFormat;
import java.util.ArrayList;

public class GameScreen implements Screen {

    private final Galaxilize game;
    private OrthographicCamera camera;
    private FreeTypeFontGenerator generator;
    private FreeTypeFontParameter parameter;
    private BitmapFont font;
    private Player player;
    private Asteroid a;
    private Texture background;
    private Texture slowEffect;
    private DecimalFormat velForm;
    private double speedFactor;
    private Border border;
    private double timer;
    private ArrayList<PlayerTrail> trail;
    private Scoring scores;
    private ExtendViewport viewport;

    private ArrayList<PhysicsObject> physicsObjectsList;

    public GameScreen(final Galaxilize game){
        // Game object to draw to
        this.game = game;

        // Create camera
        camera = new OrthographicCamera();
        //camera.setToOrtho(false,800,800);
        viewport = new ExtendViewport(800,800, 1920,800,camera);

        // Initialize background texture
        background = new Texture("Background_Elements/Background.png");
        background.setWrap(Texture.TextureWrap.Repeat, Texture.TextureWrap.Repeat);

        // Initialize screen effect textures
        slowEffect = new Texture("Screen_Effects/Slowmotion_Effect.png");


        // Reset the positions stored in this class in case the player has started another game after losing or returning to the main menu
        AsteroidSpawning.reset();

        // List where all physics affected objects are stored
        physicsObjectsList = new ArrayList<PhysicsObject>();
        // List to store all the trail of the player
        trail = new ArrayList<PlayerTrail>();
        // Create an object that will later be used to upload scores.
        scores = new Scoring();

        // Initialize player
        player = new Player(200,200,0,0,10,10);

        player.setName(JOptionPane.showInputDialog("What is your name? (scores will be saved under this name)"));
        border = new Border(-200,1);

        // Add all objects to list for rendering and colliding
        physicsObjectsList.add(player);
//        physicsObjectsList.add(new Asteroid(300,200,0,0,30,10));
//        physicsObjectsList.add(new Asteroid(330,200,-0.1,0,10,10));
//        physicsObjectsList.add(new Asteroid(300,280,0,-1,10,10));
//        physicsObjectsList.add(new Asteroid(400,420,-1,-1,10,10));
//        physicsObjectsList.add(new Asteroid(350,290,0,0,10,10));

        // Attach the player to the first Asteroid in the list
        //player.setIsHooked(true);
        //player.setHookedAsteroid((Asteroid)physicsObjectsList.get(1));
        //Creates a font for use in the GUI
        generator = new FreeTypeFontGenerator(Gdx.files.internal("comic.ttf"));
        parameter = new FreeTypeFontParameter();
        parameter.size = 20;
        font = generator.generateFont(parameter);
        generator.dispose();
        font.setUseIntegerPositions(false);

        //Creates formating for velocity text.
        velForm = new DecimalFormat("#,##0.0");

        // Speed factor used in all physics methods for slow motion effect, pass into all update and collision methods
        speedFactor = 1;

        // A timer that counts up every 60th of an in game second (accounting for slowmotion effect)
        double timer = 0;
    }

    public void render(float delta) {
        ScreenUtils.clear(0, 0, 0, 1);
        // Slows the game's physics down to 1/5th speed

        if (Gdx.input.isKeyPressed(Input.Keys.E) && speedFactor > 0.2) {
            speedFactor -= 0.08;
        } else if (speedFactor < 0.2) {
            speedFactor = 0.2;
        }
        if (!Gdx.input.isKeyPressed(Input.Keys.E) && speedFactor < 1) {
            speedFactor += 0.08;
        } else if (speedFactor > 1) {
            speedFactor = 1;
        }

        if(Gdx.input.isKeyJustPressed(Input.Keys.SPACE)){
            player.setHookedAsteroid(null);
            player.setIsHooked(false);
        }

        if(Gdx.input.isKeyJustPressed(Input.Keys.F)){
            if(!Gdx.graphics.isFullscreen()) {
                Gdx.graphics.setFullscreenMode(Gdx.graphics.getDisplayMode());
            }else{
                Gdx.graphics.setWindowedMode(800,800);
            }
        }

        if(Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)){
            scores.addScore(player.getScore(), player.getName());
            game.setScreen(new MainMenuScreen(game));
            dispose();
        }

        // Check if the player is clicking on an asteroid
        if (Gdx.input.justTouched()){
            // Get input relative to game world, not screen
            Vector3 input = new Vector3(Gdx.input.getX(),Gdx.input.getY(),0);
            camera.unproject(input);

            // Closest to wherever the player is clicking
            double bestDist = 400;
            int finalIndex = 0;

            for(int i = 1; i < physicsObjectsList.size(); i++){
                double checkDist = ((Asteroid)physicsObjectsList.get(i)).mouseDist(input.x,input.y);
                if(checkDist < bestDist){
                    bestDist = checkDist;
                    finalIndex = i;
                }
            }

            if(finalIndex != 0){
                player.setIsHooked(true);
                player.setHookedAsteroid((Asteroid) physicsObjectsList.get(finalIndex));
            }
        }

        AsteroidSpawning.update(physicsObjectsList);

        // Update border position
        border.update(speedFactor);

        // Check all collisions
        for (int i = 0; i < physicsObjectsList.size(); i++) {
            for (int j = 0; j < physicsObjectsList.size(); j++) {
                if (j != i) {
                    physicsObjectsList.get(i).checkCollision(physicsObjectsList.get(j),speedFactor);
                }
            }
        }
        // Update the player and grappled asteroid
        player.updateHook(speedFactor);
        // Update all physics object's positions
        for (int i = 0; i < physicsObjectsList.size(); i++) {
            physicsObjectsList.get(i).updatePos(speedFactor);
        }

        if(border.getPosY() > player.getPosY() - player.getRadius()){
            player.dealDamage(speedFactor);
        }
        //Adds a new part of the trail to the player.
        trail.add(new PlayerTrail(player,120));
        // Update camera position
        camera.position.set((float)player.getPosX(),(float)player.getPosY(),0);
        camera.update();
        game.batch.setProjectionMatrix(camera.combined);

        // Drawing code starts
        game.batch.begin();
        // Background (800 by 800 scrolling texture always drawn to the camera)
        game.batch.draw(background,camera.position.x-400,camera.position.y-400,(int)camera.position.x,(int)(-camera.position.y),800,1600);
        //Draws trail behind player.
        double dist;
        for(int i = 0; i < trail.size(); i++){
            if(trail.get(i).getTimeLeft() > 0){
                for(int j = 1; j < physicsObjectsList.size(); j++){
                    dist = Math.sqrt(Math.pow(physicsObjectsList.get(j).getPosX() - trail.get(i).getPosX(),2) + Math.pow(physicsObjectsList.get(j).getPosY() - trail.get(i).getPosY(),2));
                    if(dist - 4 < physicsObjectsList.get(j).getRadius()){
                        trail.remove(i);
                        j = physicsObjectsList.size();
                        if(i > 0){
                            i--;
                        }
                    }
                }
                if(trail.size() > 0) {
                    trail.get(i).draw(game.batch, game.shapeDrawer);
                    trail.get(i).setTimeLeft(trail.get(i).getTimeLeft() - speedFactor);
                }
            }
            else{
                trail.remove(i);
            }
        }
        // Draw all physics objects
        for(int i = 0; i < physicsObjectsList.size(); i++){
            // To draw PhysicsObjects, call the draw method and pass game variable (Reminder: game variable contains Galaxilize object, as in the instance of the program)
            physicsObjectsList.get(i).draw(game.batch, game.shapeDrawer);
        }
        // Draw hook between player and asteroid
        if(player.getHookedAsteroid() != null){
            game.shapeDrawer.line((float)player.getPosX(),(float)player.getPosY(),(float)player.getHookedAsteroid().getPosX(),(float)player.getHookedAsteroid().getPosY());
        }

        // Draw the border
        border.draw(game.shapeDrawer, camera.position);

        // UI

        //Draws text for UI on the screen.
        font.draw(game.batch,"Score: " + player.getScore(),(float)camera.position.x - 380, camera.position.y + 350);
        game.shapeDrawer.circle(camera.position.x - 325,camera.position.y - 275,50);
        game.shapeDrawer.line(camera.position.x - 325, camera.position.y - 275, camera.position.x - 325 + (float)(50*player.getVelX()/Math.sqrt(player.getVelX()*player.getVelX() + player.getVelY()*player.getVelY())), camera.position.y - 275 +(float)(50*player.getVelY()/Math.sqrt(player.getVelX()*player.getVelX() + player.getVelY()*player.getVelY())) );
        font.draw(game.batch,"Velocity: " + velForm.format(Math.sqrt(player.getVelX()*player.getVelX() + player.getVelY()*player.getVelY())), camera.position.x - 380, camera.position.y - 350);

        // Draws Healthbar
        game.shapeDrawer.setColor(0,1,0,1);
        game.shapeDrawer.filledRectangle(camera.position.x-400,camera.position.y+300, (float) player.getHealth(),30);
        game.shapeDrawer.setColor(1,1,1,1);


        // Screen Effects

        // Draws slow motion screen effects
        if(speedFactor < 1){
            game.batch.setColor(1,1,1,(float)(-1.25*(speedFactor-1)));
            game.shapeDrawer.setColor(1,1,1,(float)(-1.25*(speedFactor-1)));

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
            game.shapeDrawer.setColor(1,1,1,1);
            game.batch.setColor(1,1,1,1);
        }

        // Drawing code ends
        game.batch.end();
    }


    // These methods must be implemented as part of the Screen interface
    // libGDX tutorial shows that best practice is to leave them blank like so
    @Override
    public void show() {

    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width,height);
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

/*
 Nathan Becker, Muhammad Umar, Matthew Witherspoon
 June 11th 2024
 The screen that displays the game world.
 */
package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.graphics.g2d.freetype.*;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;
import com.badlogic.gdx.audio.Sound;
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
    private Texture background;
    private Texture stars;
    private Texture slowEffect;
    private DecimalFormat velForm;
    private double speedFactor;
    private Border border;
    private ArrayList<PlayerTrail> trail;
    private Scoring scores;
    private int deadTime;
    private static ArrayList<Music> musicTracks;
    private int trackNum;
    private Texture dashboard;
    private Texture healthBar;
    private Texture fuelBar;
    private Texture scoreDisplay;

    private Sound playerDeath;
    private Sound shootGrapple;


    private ArrayList<PhysicsObject> physicsObjectsList;

    /**
     * A constructor for the game screen object.
     * @param game the object holding data about the game.
     */
    public GameScreen(final Galaxilize game){
        // Game object to draw to
        this.game = game;

        //Sets the time the player has been dead for to zero
        deadTime = 0;

        // Create camera
        camera = new OrthographicCamera();
        camera.setToOrtho(false,800,800);

        // Initialize background texture
        background = new Texture("Background_Elements/Background.png");
        background.setWrap(Texture.TextureWrap.Repeat, Texture.TextureWrap.Repeat);

        stars = new Texture("Background_Elements/Stars.png");
        stars.setWrap(Texture.TextureWrap.Repeat, Texture.TextureWrap.Repeat);

        // Initialize screen effect textures
        slowEffect = new Texture("Screen_Effects/Slowmotion_Effect.png");

        // UI Elements
        dashboard = new Texture("UI/Dashboard.png");
        healthBar = new Texture("UI/Health_Bar.png");
        fuelBar = new Texture("UI/Fuel_Bar.png");
        scoreDisplay = new Texture("UI/Score_Bar.png");

        // Reset the positions stored in this class in case the player has started another game after losing or returning to the main menu
        Spawning.reset();

        // List where all physics affected objects are stored
        physicsObjectsList = new ArrayList<PhysicsObject>();
        // List to store all the trail of the player
        trail = new ArrayList<PlayerTrail>();
        // Create an object that will later be used to upload scores.
        scores = new Scoring();

        //Creates music array list.
        musicTracks = new ArrayList<>();
        musicTracks.add(Gdx.audio.newMusic(Gdx.files.internal("Sound/backgroundRadiation.mp3")));
        musicTracks.add(Gdx.audio.newMusic(Gdx.files.internal("Sound/solarFlare.mp3")));
        musicTracks.get(0).setVolume(0.5f);
        musicTracks.get(1).setVolume(0.3f);
        //Randomizes what track the music starts on.
        trackNum = (int)(Math.random()*((double)musicTracks.size()));
        // Create Sound Effects
        playerDeath = Gdx.audio.newSound(Gdx.files.internal("Sound/Player_Death.ogg"));
        shootGrapple = Gdx.audio.newSound(Gdx.files.internal("Sound/Shoot_Grapple.ogg"));
        // Initialize player
        player = new Player(200,200,0,0,10,10, 100, 100,"");

        player.setName(JOptionPane.showInputDialog("What is your name? (scores will be saved under this name)"));
        border = new Border(-200,1);

        // Add all starting objects to list for rendering and colliding
        physicsObjectsList.add(player);

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
    }

    public void render(float delta) {
        ScreenUtils.clear(0, 0, 0, 1);
        if(!musicTracks.get(trackNum).isPlaying()){ //Checks if the music has stopped.
            trackNum++; //Moves to the next track.
            if(trackNum >= musicTracks.size()){ //If the last track has been reached.
                trackNum = 0; //Reset to the first track.
            }
            musicTracks.get(trackNum).play(); //Start a new song.
        }
        // Slows the game's physics down to 1/5th speed
        if (Gdx.input.isKeyPressed(Input.Keys.E) && speedFactor > 0.2) { //Slows down time to 1/5x speed if the player is holding E down.
            speedFactor -= 0.08;
        } else if (speedFactor < 0.2) {
            speedFactor = 0.2;
        }
        if (!Gdx.input.isKeyPressed(Input.Keys.E) && speedFactor < 1) { //Speeds up to normal time if the player releases the E key.
            speedFactor += 0.08;
        } else if (speedFactor > 1) {
            speedFactor = 1;
        }

        if(Gdx.input.isKeyJustPressed(Input.Keys.SPACE)){
            player.setHookedObject(null);
            player.setIsHooked(false);
        }

        // Check if the player is clicking on an asteroid
        if (Gdx.input.justTouched()){
            // Get input relative to game world, not screen
            Vector3 input = new Vector3(Gdx.input.getX(),Gdx.input.getY(),0);
            camera.unproject(input);

            // Closest to wherever the player is clicking
            double bestDist = 400;
            int finalIndex = 0;

            //Loops through all physics objects checking which ones are closest to where the player clicks.
            for(int i = 1; i < physicsObjectsList.size(); i++){
                double checkDist = ((physicsObjectsList.get(i)).mouseDist(input.x,input.y));
                if(checkDist < bestDist){
                    bestDist = checkDist;
                    finalIndex = i;
                }
            }
            //If an object was found that the player can grapple to grapple to it and play a sound effect.
            if(finalIndex != 0){
                player.setIsHooked(true);
                player.setHookedObject(physicsObjectsList.get(finalIndex));
                shootGrapple.play(0.25f, (float)Math.random()*0.5f + 0.75f,0);
            }
        }
        //Checks if the user wants to return to the menu.
        if(Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)){
            scores.addScore((int)player.getScore(), player.getName());
            musicTracks.get(trackNum).stop();
            game.setScreen(new MainMenuScreen(game));
            dispose();
            return;
        }

        //Checks if the player is dead
        if(player.getHealth() < 0){
            speedFactor = 0;
            if(deadTime == 0){
                playerDeath.play(0.5f);
            }
            if(deadTime < 150) {
                deadTime++;
            }
        }

        //Updates the spawning of the asteroids, spawning more if necessary.
        Spawning.update(physicsObjectsList);

        // Update border position
        border.update(speedFactor);

        // If the border is too far away, speed it up
        if(border.getPosY()+800 < player.getPosY()){
            border.setPosY(player.getPosY()-800);
        }else{
            border.setSpeed(3);
        }


        // Check all collisions
        for (int i = 0; i < physicsObjectsList.size(); i++) {
            for (int j = 0; j < physicsObjectsList.size(); j++) {
                if (j != i) {
                    if(physicsObjectsList.get(i).checkCollision(physicsObjectsList.get(j),speedFactor)){
                        if(player.getHookedObject() instanceof Item && ((Item) player.getHookedObject()).equals((Item) physicsObjectsList.get(j))){
                            player.setHookedObject(null);
                        }
                        physicsObjectsList.remove(j);
                    }
                }
            }
        }
        // Update the player and grappled asteroid
        player.updateHook(speedFactor);
        // Update all physics object's positions
        player.updatePos(speedFactor);
        for (int i = 1; i < physicsObjectsList.size(); i++) {
            physicsObjectsList.get(i).updatePos(speedFactor);
        }
        //Deals damage to the player if they are inside the border.
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
        float hueShiftR = (float)(Math.sin(2*Math.PI/110000.0*(player.getPosY()-40000)+1.5));
        float hueShiftG = (float)(Math.cos(2*Math.PI/100000.0*(player.getPosY())+1.5));
        float hueShiftB = (float)(Math.cos(2*Math.PI/120000.0*(player.getPosY()-150000)+1.5));
        game.batch.setColor(hueShiftR,hueShiftG,hueShiftB,1);
        game.batch.draw(background,camera.position.x-400,camera.position.y-400,(int)camera.position.x,(int)(-camera.position.y),1600,1600);
        game.batch.setColor(1,1,1,1);
        game.batch.draw(stars,camera.position.x-400,camera.position.y-400,(int)camera.position.x,(int)(-camera.position.y),1600,1600);
        //Draws trail behind player.
        double dist;
        for(int i = 0; i < trail.size(); i++){ //Loops through every PlayerTrail object and draws each on the screen.
            if(trail.get(i).getTimeLeft() > 0){ //Checks if the trail is out of time.
                for(int j = 1; j < physicsObjectsList.size(); j++){
                    //Checks the distance between the trail and every physics object other than the player.
                    dist = Math.sqrt(Math.pow(physicsObjectsList.get(j).getPosX() - trail.get(i).getPosX(),2) + Math.pow(physicsObjectsList.get(j).getPosY() - trail.get(i).getPosY(),2));
                    if(dist - 4 < physicsObjectsList.get(j).getRadius()){ //If the trail is inside the physics object, delete it.
                        trail.remove(i);
                        j = physicsObjectsList.size();
                        if(i > 0){ //Moves back one to ensure every trail is drawn.
                            i--;
                        }
                    }
                }
                if(!trail.isEmpty()) { //If any trail exists draw it.
                    trail.get(i).draw(game.batch, game.shapeDrawer);
                    trail.get(i).setTimeLeft(trail.get(i).getTimeLeft() - speedFactor);
                }
            }
            else{ //Delete the trail if it is out of time.
                trail.remove(i);
            }
        }
        // Draw all physics objects
        for(int i = 0; i < physicsObjectsList.size(); i++){
            // To draw PhysicsObjects, call the draw method and pass game variable (Reminder: game variable contains Galaxilize object, as in the instance of the program)
            physicsObjectsList.get(i).draw(game.batch, game.shapeDrawer);
        }
        // Draw hook between player and asteroid
        if(player.getHookedObject() != null){
            game.shapeDrawer.line((float)player.getPosX(),(float)player.getPosY(),(float)player.getHookedObject().getPosX(),(float)player.getHookedObject().getPosY());
        }

        // Draw the border
        border.draw(game.shapeDrawer, camera.position);

        // UI

        // Draws Score
        game.batch.draw(scoreDisplay,camera.position.x+205,camera.position.y+291);
        font.setColor(0,1,0,1);
        font.draw(game.batch,"Score: " + (int)player.getScore(),(float)camera.position.x+225, camera.position.y + 325);
        font.setColor(1,1,1,1);

        // Draws Dashboard (Direction and Velocity)
        game.batch.draw(dashboard,camera.position.x-400,camera.position.y-386);
        game.shapeDrawer.setColor(0.8f,0.8f,0.9f,1);
        game.shapeDrawer.line(camera.position.x - 325, camera.position.y - 275, camera.position.x - 325 + (float)(50*player.getVelX()/Math.sqrt(player.getVelX()*player.getVelX() + player.getVelY()*player.getVelY())), camera.position.y - 275 +(float)(50*player.getVelY()/Math.sqrt(player.getVelX()*player.getVelX() + player.getVelY()*player.getVelY())),8 );

        // Velocity bar
        float velDisplay = (float)Math.sqrt(player.getVelX()*player.getVelX() + player.getVelY()*player.getVelY());
        game.shapeDrawer.setColor(0.35f,0.71f,0.93f,1);
        game.shapeDrawer.filledRectangle(camera.position.x-400,camera.position.y -377,Math.min(velDisplay*5, 50),30);
        game.shapeDrawer.setColor(0.96f,0.89f,0.41f,1);
        game.shapeDrawer.filledRectangle(camera.position.x-350,camera.position.y -377,Math.min(Math.max((velDisplay-10)*5, 0),50),30);
        game.shapeDrawer.setColor(0.93f,0.43f,0.35f,1);
        game.shapeDrawer.filledRectangle(camera.position.x-300,camera.position.y -377,Math.min(Math.max((velDisplay-20)*5, 0),50),30);
        game.shapeDrawer.setColor(1,1,1,1);

        font.setColor(0,0,0,1);
        font.draw(game.batch,"Velocity: " + velForm.format(velDisplay), camera.position.x - 380, camera.position.y - 350);
        font.setColor(1,1,1,1);

        // Draws Healthbar
        game.batch.draw(healthBar,camera.position.x-400,camera.position.y+291);
        game.shapeDrawer.setColor(0,1,0,1);
        game.shapeDrawer.filledRectangle(camera.position.x-400,camera.position.y+300, (float) (player.getHealth()*1.5),30);
        game.shapeDrawer.setColor(1,1,1,1);

        // Draws Fuel bar
        game.batch.draw(fuelBar,camera.position.x-400,camera.position.y + 243);
        game.shapeDrawer.setColor(1,0.5f,0,1);
        game.shapeDrawer.filledRectangle(camera.position.x-400,camera.position.y+252,(float)(player.getFuel()*1.5),30);

        // Screen Effects

        // Draws slow motion screen effects
        if(speedFactor < 1 && speedFactor != 0){
            game.batch.setColor(1,1,1,(float)(-1.25*(speedFactor-1)));
            game.shapeDrawer.setColor(1,1,1,(float)(-1.25*(speedFactor-1)));

            // Aiming line for orbiting asteroid or player, depending on which is bigger
            if(player.getHookedObject() != null && player.getMass() >= player.getHookedObject().getMass()){
                double velDir = Math.atan2(player.getHookedObject().getVelY(),player.getHookedObject().getVelX());
                float posX = (float)player.getHookedObject().getPosX();
                float posY = (float)player.getHookedObject().getPosY();
                game.shapeDrawer.line(posX,posY,posX+50*(float)Math.cos(velDir),posY+50*(float)Math.sin(velDir), 3f);
            }else if(player.getHookedObject() != null && player.getMass() < player.getHookedObject().getMass()){
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
        if(deadTime > 0) {
            game.shapeDrawer.setColor(0,0,0,((float)deadTime)/150f);
            game.shapeDrawer.filledRectangle(camera.position.x-400,camera.position.y-400, 800,800);
            game.shapeDrawer.setColor(1,1,1,1);
            if(deadTime >= 150){
                game.batch.setColor(1,1,1,1);
                font.draw(game.batch,"You died. . .\nFinal Score: " + (int)player.getScore() + "\nPress Escape to return to menu.",camera.position.x-150, camera.position.y+50);
            }
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
        font.dispose();
        background.dispose();
        stars.dispose();
        slowEffect.dispose();
        dashboard.dispose();
        healthBar.dispose();
        scoreDisplay.dispose();
        musicTracks.get(0).dispose();
        musicTracks.get(1).dispose();
        dashboard.dispose();
        healthBar.dispose();
        fuelBar.dispose();
        scoreDisplay.dispose();
        playerDeath.dispose();
        shootGrapple.dispose();
    }
}

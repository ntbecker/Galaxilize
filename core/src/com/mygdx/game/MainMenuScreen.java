package com.mygdx.game;
// imports
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import java.awt.*;

// Main class for the menu screen which is branched to the screen
public class MainMenuScreen implements Screen {
// my variables for the main menu screen
    private Stage stage;
    private Skin skin;
    final Galaxilize game;
    private Texture background;
    private Texture title;
    private OrthographicCamera camera;
    private static Music menuMusic;
    private int scroll;

    /**
     * Constructor for main menu
     */
    public MainMenuScreen(final Galaxilize game) {
        this.game = game;
        // Skin file from https://github.com/czyzby/gdx-skins/tree/master/default
        skin = new Skin(Gdx.files.internal("UI/uiskin.json")); // Ensure you have a skin JSON file
        camera = new OrthographicCamera();
        stage = new Stage(new ScreenViewport(camera));
        Gdx.input.setInputProcessor(stage);
        // Plays menu music.
        if(menuMusic == null){
            menuMusic = Gdx.audio.newMusic(Gdx.files.internal("menuTrack.mp3"));
        }
        if(!menuMusic.isPlaying()){
            menuMusic.play();
            menuMusic.setVolume(0.5f);
            menuMusic.setLooping(true);
        }
        // Create scrolling background
        background = new Texture(Gdx.files.internal("Background_Elements/BackgroundMenu.png"));
        background.setWrap(Texture.TextureWrap.Repeat, Texture.TextureWrap.Repeat);
        scroll = 0;
        title = new Texture(Gdx.files.internal("UI/logo.png"));

    }


    /**
     * Render method, called every frame (60 fps)
     * @param delta The time in seconds since the last render.
     */
    @Override
    public void render(float delta) {
        // Fill screen with black
        ScreenUtils.clear(0, 0, 0, 1);
        camera.update();
        game.batch.setProjectionMatrix(camera.combined);
//        Old menu code, not sure if anyone needs this // still not sure
//        // Update the camera
//        camera.update();
//        game.batch.setProjectionMatrix(camera.combined);
//
//        // Draw sprites here
//        game.batch.begin();
//        game.titleFont.draw(game.batch, "GALAXILIZE", 0, 800, 800, 1, false);
//        game.subTitleFont.draw(game.batch, "Click to start!", 0, 650, 800, 1, false);
//        game.batch.end();
//
//        if (Gdx.input.isTouched()) {
//            game.setScreen(new GameScreen(game));
//            dispose();
//        }

        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        // Draw background and logo

        game.batch.begin();
        scroll++;
        game.batch.draw(background,0,0,scroll,scroll,1600,1600);
        //s.draw(playerTexture,(float)(posX-10),(float)(posY-10),10,10,20,20,2,2,(float)(180*Math.atan2(velY,velX)/Math.PI-90),0,0,20,20,false,false);
         // Adjust the logo position and scaling
        float logoX = (Gdx.graphics.getWidth() - title.getWidth() * 0.5f) / 2;
        float logoY = Gdx.graphics.getHeight() - title.getHeight() * 0.5f - 50; // Adjust this value as needed
        game.batch.draw(title, logoX, logoY, title.getWidth() * 0.5f, title.getHeight() * 0.5f);


        game.batch.end();

        // Render the stage

        stage.act(delta);
        stage.draw();
    }

    /**
     * Show Method which displays the screen and the buttons for the user to choose from
     */
    @Override
    public void show() {
        Table table = new Table();
        table.setFillParent(true);
        stage.addActor(table);

        TextButton playButton = new TextButton("Play!", skin);
        TextButton tutorialButton = new TextButton("Tutorial", skin);
        TextButton quitButton = new TextButton("Quit", skin);
        TextButton scoresButton = new TextButton("High Scores", skin);
        TextButton creditsButton = new TextButton("Credits", skin);


        playButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                menuMusic.stop();
                menuMusic.dispose();
                game.setScreen(new GameScreen(game));
                dispose();
            }
        });

        tutorialButton.addListener(new ClickListener() {
            /**
             * Method used when the tutorial button and to show its features
             * @param event - the input event when clicked by the button
             * @param x - the x-coordinate of the click
             * @param y - the y-coordinate of the click
             */
            @Override
            public void clicked(InputEvent event, float x, float y) {
                // Navigate to the TutorialScreen when the tutorial button is clicked
                game.setScreen(new Tutorial(game));
                dispose();
            }
        });

        quitButton.addListener(new ClickListener() {
            @Override

            public void clicked(InputEvent event, float x, float y) {
                System.out.println("Quit button clicked");
                Gdx.app.exit(); // exits the system
            }
        });

        scoresButton.addListener(new ClickListener(){
            public void clicked(InputEvent event, float x, float y){
                game.setScreen(new ScoreScreen(game));
            }
        });
        // Add listener for the credits button
        creditsButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new Credits(game));
                dispose();
            }
        });
// add the play button to the table
        table.top().padTop(450);
        // Adjust this value as needed to move the buttons lower
        table.add(playButton).fillX().uniformX();
        table.row().pad(10, 0, 10, 0); // add a row
        table.add(tutorialButton).fillX().uniformX(); // add a tutorial button
        table.row().pad(0, 0, 10, 0); // add a row
        table.add(scoresButton).fillX().uniformX();
        table.row().pad(0, 0, 10, 0); // add a row
        table.add(creditsButton).fillX().uniformX();
        table.row().pad(0, 0, 10, 0);
        table.add(quitButton).fillX().uniformX(); // add the quit button to the table
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {
        // Method called when the application is paused
    }

    @Override
    public void resume() {
        // Method called when the application is resumed
    }

    @Override
    public void hide() {
        // Method called when the application is hidden
    }

    @Override
    public void dispose() {
        stage.dispose();
        skin.dispose();
        background.dispose();
        title.dispose();
    }
}
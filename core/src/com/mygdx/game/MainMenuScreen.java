package com.mygdx.game;
// imports
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

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
    private Player player;

    public static float colourR = 1f;
    public static float colourG = 0f;
    public static float colourB = 1f;

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

        //Create background player and asteroid.
        player = new Player((int)(Math.random()*2)*800,(int)(Math.random()*2)*800,Math.random()*3 - 1,Math.random()*3-1,10,10);
        player.setMoveLocked(true);
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
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        player.updatePos(1);
        if(player.getPosY() > 810){
            player.setPosY(player.getPosY()-820);
        }
        else if(player.getPosY() < -10){
            player.setPosY(player.getPosY()+820);
        }
        if (player.getPosX() < -10){
            player.setPosX(player.getPosX() + 820);
        }
        else if(player.getPosX() > 810){
            player.setPosX(player.getPosX() - 820);
        }
        // Draw background and logo
        game.batch.begin();
        scroll++;
        game.batch.draw(background,0,0,scroll,scroll,1600,1600);
        player.draw(game.batch,game.shapeDrawer);
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
        TextButton colourButton = new TextButton("Randomize Player", skin);


        // Listeners
        playButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                // Stop menu music when game starts
                menuMusic.stop();
                // Dispose of menu music to remove it from memory
                menuMusic.dispose();
                // Set screen to game screen
                game.setScreen(new GameScreen(game));
                // Dispose of other menu assets
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
                // Dispose of other menu assets
                dispose();
            }
        });

        quitButton.addListener(new ClickListener() {
            @Override

            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.exit(); // exits the game
            }
        });

        scoresButton.addListener(new ClickListener(){
            public void clicked(InputEvent event, float x, float y){
                // Navigates to the leaderboards
                game.setScreen(new ScoreScreen(game));
            }
        });
        // Add listener for the credits button
        creditsButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                // Navigates to the credits
                game.setScreen(new Credits(game));
                dispose();
            }
        });
        colourButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                // Randomize player colour
                colourR = (float)Math.random();
                colourG = (float)Math.random();
                colourB = (float)Math.random();
            }
        });

        // Adjust this value as needed to move the buttons lower
        table.top().padTop(450);
        // add the play button to the table
        table.add(playButton).fillX().uniformX();
        table.row().pad(10, 0, 10, 0); // add a row
        table.add(tutorialButton).fillX().uniformX(); // add a tutorial button
        table.row().pad(0, 0, 10, 0); // add a row
        table.add(scoresButton).fillX().uniformX();
        table.row().pad(0, 0, 10, 0); // add a row
        table.add(creditsButton).fillX().uniformX();
        table.row().pad(0, 0, 10, 0);
        table.add(colourButton).fillX().uniformX(); // add the quit button to the table
        table.row().pad(60, 0, 10, 0);
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
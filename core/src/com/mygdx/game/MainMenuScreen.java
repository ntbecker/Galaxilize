package com.mygdx.game;
// imports
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
// main class for the meny screen which is branched to the screen
public class MainMenuScreen implements Screen {
// my variables for the main menu screen
    private Stage stage;
    private Skin skin;
    final Galaxilize game;
    OrthographicCamera camera;

    /**
     * Constructor for main menu
     */
    public MainMenuScreen(final Galaxilize game) {
        this.game = game;
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 800, 800);
    }


    /**
     * Render method, called every frame (60 fps)
     * @param delta The time in seconds since the last render.
     */
    @Override
    public void render(float delta) {
        // Fill screen with black
        ScreenUtils.clear(0, 0, 0, 1);

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

        // Render the stage
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act(delta);
        stage.draw();
    }

    /**
     * Show Method which displays the screen and the buttons for the user to choose from
     */
    @Override
    public void show() {
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

        // Skin file from https://github.com/czyzby/gdx-skins/tree/master/default
        skin = new Skin(Gdx.files.internal("UI/uiskin.json")); // Ensure you have a skin JSON file

        Table table = new Table();
        table.setFillParent(true);
        stage.addActor(table);

        TextButton playButton = new TextButton("Play!", skin);
        TextButton tutorialButton = new TextButton("Tutorial", skin);
        TextButton quitButton = new TextButton("Quit", skin);

        playButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
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
                System.out.println("Tutorial button clicked");

            }
        });

        quitButton.addListener(new ClickListener() {
            @Override

            public void clicked(InputEvent event, float x, float y) {
                System.out.println("Quit button clicked");
                Gdx.app.exit(); // exits the system
            }
        });
// add the play button to the table
        table.add(playButton).fillX().uniformX();
        table.row().pad(10, 0, 10, 0); // add a row
        table.add(tutorialButton).fillX().uniformX(); // add a tutorial button
        table.row(); // add a row
        table.add(quitButton).fillX().uniformX(); // add the quit button to the table
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
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
    }
}
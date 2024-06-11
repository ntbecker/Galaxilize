package com.mygdx.game;
// the imports
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.graphics.Texture;
public class Credits implements Screen {
    // Reference to the main game class
    final Galaxilize game;
    // Camera to view the game world
    private OrthographicCamera camera;
    // Stage for managing the UI
    private Stage stage;
    // Skin for the UI elements
    private Skin skin;
    // Texture for the background image
    private Texture background;
    // Constructor to initialize the tutorial screen
    public Credits (final Galaxilize game) {
        // Set the game reference
        this.game = game;
        // Create a new camera
        camera = new OrthographicCamera();

        // Create a new stage with a screen viewport
        stage = new Stage(new ScreenViewport());
        // Set the input processor to the stage
        Gdx.input.setInputProcessor(stage);

        // Load the skin for the UI elements
        skin = new Skin(Gdx.files.internal("UI/uiskin.json"));

        // Create a table to organize UI elements
        Table table = new Table();
        // Make the table fill the entire stage
        table.setFillParent(true);
        // Add the table to the stage
        stage.addActor(table);

        // Create a label for the title "Credits" using the skin
        Label titleLabel = new Label(" Credits\n", skin );
        // Create a label for the credits using the skin
        Label instructionsLabel = new Label(
                "Created by MNM Productions\n\n" +
                        "Special Thanks to: Mr.Cutten\n\n" +
                        " - Team Members: \n\n" +
                        "   - Matthew Wittherspoon\n" +
                        "   - Nathan Becker\n" +
                        "   - Muhammad Umar\n\n" +
                        " - Supporters: Mr.Wallace\n",

                skin
        );

        // Add the title label to the table with some padding at the bottom
        table.add(titleLabel).padBottom(20);
        // Move to the next row in the table
        table.row();
        // Add the instructions label to the table
        table.add(instructionsLabel);
    }

    // Method called when the screen is shown
    @Override
    public void show() {
    }

    // Method called every frame to render the screen
    @Override
    public void render(float num) {
        // Clear the screen with a black color
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);


        stage.act(num);
        // Draw the stage
        stage.draw();
        // Check if the screen was clicked or touched
        if (Gdx.input.justTouched()) {
            game.setScreen(new MainMenuScreen(game));
            dispose();
        }

    }

    // Method called when the screen is resized
    @Override
    public void resize(int width, int height) {

    }

    // Method called when the game is paused
    @Override
    public void pause() {
    }

    // Method called when the game is resumed
    @Override
    public void resume() {
    }

    // Method called when the screen is hidden
    @Override
    public void hide() {
    }

    // Method called to dispose of resources
    @Override
    public void dispose() {
        // Dispose of the stage
        stage.dispose();
        // Dispose of the skin
        skin.dispose();
    }
}



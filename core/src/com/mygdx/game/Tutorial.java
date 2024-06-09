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
/*
Tutorial Class
 */
public class Tutorial implements Screen{
    // Reference to the main game class
    final Galaxilize game;
    // Camera to view the game world
    private OrthographicCamera camera;
    // Stage for managing the UI
    private Stage stage;
    // Skin for the UI elements
    private Skin skin;

    // Constructor to initialize the tutorial screen
    public Tutorial(final Galaxilize game) {
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

        // Create a label for the title "How to Play" using the skin
        Label titleLabel = new Label("How to Play", skin);
        // Create a label for the instructions using the skin
        Label instructionsLabel = new Label(
                "1. Use arrow keys to move.\n" +
                        "2. Press 'E' for slow motion.\n" +
                        "3. Press 'Space' to release from an asteroid.\n" +
                        "4. Avoid asteroids and survive as long as possible.\n" +
                        "5. Click anywhere on the screen to go back to Main Menu",
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
        // Update the stage with the time since the last render
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

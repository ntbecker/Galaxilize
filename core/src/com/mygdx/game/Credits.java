/*
 Nathan Becker, Muhammad Umar, Matthew Witherspoon
 June 11th 2024
 Displays the credits for the game.
 */
package com.mygdx.game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
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
    private Texture logo;
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
        logo = new Texture(Gdx.files.internal("UI/mnm_logo.png"));



        // Create a table to organize UI elements
        Table table = new Table();
        // Make the table fill the entire stage
        table.setFillParent(true);
        // Add the table to the stage
        stage.addActor(table);


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
        table.add(instructionsLabel).padBottom(70);
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
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        game.batch.begin();
        float logoWidth = logo.getWidth() * 0.4f; // Adjust the scale as needed
        float logoHeight = logo.getHeight() * 0.4f; // Adjust the scale as needed
        game.batch.draw(logo, (Gdx.graphics.getWidth() - logoWidth) / 2, Gdx.graphics.getHeight() - logoHeight - 100, logoWidth, logoHeight); // Center the logo at the top
        game.batch.end();



        stage.act(num);
        // Draw the stage
        stage.draw();
        // Checks if escape is pressed.
        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
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
        logo.dispose();
    }
}



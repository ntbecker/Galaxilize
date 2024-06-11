/*
 Nathan Becker, Muhammad Umar, Matthew Witherspoon
 6/11/2024
 A screen that will display all the existing scores stored in the file and allow the user to search for scores.
 */
package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import javax.swing.*;


public class ScoreScreen implements Screen{
    final Galaxilize game;
    private String output;
    private Scoring scores;
    OrthographicCamera camera;
    private Stage stage;
    private Skin skin;
    private FreeTypeFontGenerator generator;
    private FreeTypeFontGenerator.FreeTypeFontParameter parameter;
    private BitmapFont font;
    /**
     * A constructor for the score screen.
     */
    public ScoreScreen(final Galaxilize game){
        scores = new Scoring();
        output = scores.displayHighScores();
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 800, 800);
        this.game = game;

        //Creates a font for use on the screen.
        generator = new FreeTypeFontGenerator(Gdx.files.internal("comic.ttf"));
        parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 20;
        font = generator.generateFont(parameter);
        generator.dispose();
        font.setUseIntegerPositions(false);

        //Creates the stage
        stage = new Stage(new ScreenViewport());

        Gdx.input.setInputProcessor(stage);

        //Creates the skin for the buttons.
        skin = new Skin(Gdx.files.internal("UI/uiskin.json"));
    }

    @Override
    public void show() {
        //Creates a table
        Table table = new Table();
        table.setFillParent(true);
        stage.addActor(table);

        //Creates button to display the high scores and search for scores under a user's name.
        TextButton searchButton = new TextButton("Search", skin);
        TextButton topScoreButton = new TextButton("View Leaderboard", skin);
        table.add(searchButton).fillX().uniformX();
        table.add(topScoreButton).fillX().uniformX();



        topScoreButton.addListener(new ClickListener(){
            public void clicked(InputEvent event, float x, float y) {
                output = scores.displayHighScores();
            }
        });
        searchButton.addListener(new ClickListener(){
            public void clicked(InputEvent event, float x, float y){
                output = scores.searchScore(JOptionPane.showInputDialog("What name would you like to search for scores under?"));
            }
        });
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 0, 0, 1);

        if(Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)){
            game.setScreen(new MainMenuScreen(game));
            dispose();
        }

        camera.update();
        game.batch.setProjectionMatrix(camera.combined);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        game.batch.begin();
        game.batch.setColor(new Color(1,1,1,1));
        font.draw(game.batch,output,camera.position.x - 100,camera.position.y - 30);
        game.batch.end();
        stage.act(delta);
        stage.draw();

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

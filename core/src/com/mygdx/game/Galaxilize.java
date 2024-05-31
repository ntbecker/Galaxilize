package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import space.earlygrey.shapedrawer.ShapeDrawer;

public class Galaxilize extends Game {
	public SpriteBatch batch;
	public Texture img;
	public BitmapFont titleFont;
	public BitmapFont subTitleFont;
	public ShapeDrawer shapeDrawer;

	/**
	 * Runs when the application is started, instantiates SpriteBatch and fonts
	 */
	@Override
	public void create () {
		batch = new SpriteBatch();
		titleFont = new BitmapFont();
		subTitleFont = new BitmapFont();
		titleFont.getData().setScale(10);
		subTitleFont.getData().setScale(5);
		img = new Texture("badlogic.jpg");

		// Texture region specifies a single white pixel for
		shapeDrawer = new ShapeDrawer(batch, new TextureRegion(img,8,8,9,9));
		shapeDrawer.setColor(1,1,1,1);

		// Sets the screen to the Main Menu
		this.setScreen(new MainMenuScreen());
	}

	/**
	 * Renders the game
	 */
	@Override
	public void render () {
		// Calls the render method in the Game class, renders whichever screen is active
		super.render();
	}

	/**
	 * Disposes the batch and loaded textures
	 */
	@Override
	public void dispose () {
		batch.dispose();
		img.dispose();
	}
	public void create() {
		this.setScreen(new MainMenuScreen());
	}
}


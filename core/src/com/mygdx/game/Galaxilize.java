package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import space.earlygrey.shapedrawer.ShapeDrawer;

public class Galaxilize extends Game {
	public SpriteBatch batch;
	public Texture whitePixel;
	public BitmapFont titleFont;
	public BitmapFont subTitleFont;
	public ShapeDrawer shapeDrawer;
	public double thrustMulti;
	public int bonusHealth;
	public double bonusMass;
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

		whitePixel = new Texture("White_Pixel.png");
		// Texture region specifies a single white pixel
		shapeDrawer = new ShapeDrawer(batch, new TextureRegion(whitePixel,0,0,1,1));
		shapeDrawer.setColor(Color.WHITE);
		thrustMulti = 1;
		bonusHealth = 0;
		bonusMass = 0;
		// Sets the screen to the Main Menu
		this.setScreen(new MainMenuScreen(this));
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
		whitePixel.dispose();
		subTitleFont.dispose();
		titleFont.dispose();
	}

}


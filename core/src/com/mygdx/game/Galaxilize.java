package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import jdk.javadoc.internal.doclets.formats.html.markup.HtmlStyle;

public class Galaxilize extends Game {
	public SpriteBatch batch;
	public Texture img;
	public BitmapFont titleFont;
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		titleFont = new BitmapFont();
		titleFont.getData().setScale(10);
		this.setScreen(new MainMenuScreen(this));
	}

	@Override
	public void render () {
		super.render();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		img.dispose();
	}
}

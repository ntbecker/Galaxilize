package com.mygdx.game;
// imports for this class
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;


// Please note that on macOS your application needs to be started with the -XstartOnFirstThread JVM argument
public class DesktopLauncher {
	public static void main (String[] arg) {
		Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
		config.setTitle("Galaxilize"); // set the title of the game
		config.setWindowedMode(800, 800); // set the title of the game
		config.useVsync(true);// Enable vertical synchronization to reduce tearing of the screen
		config.setForegroundFPS(60); // set the foreground to 60 fps
		config.setResizable(false);
		new Lwjgl3Application(new Galaxilize(), config);// create a new instance application within your application
	}
	}

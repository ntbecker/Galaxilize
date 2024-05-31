package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Asteroid extends PhysicsObject{
    private static Texture asteroidTexture = new Texture("circleRadius10.png");
    boolean containsUpgrade;
    Asteroid(double posX, double posY, double velX, double velY, double mass, double radius){
        super(posX,posY,velX,velY,mass,radius);
        containsUpgrade = false;
    }
    Asteroid(double posX, double posY, double velX, double velY, double mass, double radius, boolean containsUpgrade){
        this(posX,posY,velX,velY,mass,radius);
        this.containsUpgrade = containsUpgrade;
    }

    /**
     * Returns if the asteroid contains an upgrade.
     * @return if the asteroid contains an upgrade.
     */
    public boolean getContainsUpgrade(){ return(containsUpgrade); }

    /**
     * Sets the asteroid to contain or not contain an upgrade based on input.
     * @param containsUpgrade if the asteroid contains an upgrade.
     */
    public void setContainsUpgrade(boolean containsUpgrade){ this.containsUpgrade = containsUpgrade; }
    public void draw(SpriteBatch s){
        s.draw(asteroidTexture, (float) (posX-radius), (float) (posY-radius));
    }
}

/*
 Nathan Becker, Muhammad Umar, Matthew Witherspoon
 June 11th 2024
 A class to create objects for the trail that follows the player.
 */
package com.mygdx.game;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import space.earlygrey.shapedrawer.ShapeDrawer;

public class PlayerTrail {
    private double posX;
    private double posY;
    private double timeLeft;
    private double startTime;
    public PlayerTrail(Player player, double decayTime){
        startTime = decayTime;
        timeLeft = decayTime;
        posX = player.getPosX();
        posY = player.getPosY();
    }

    /**
     * Returns how much time the trail has left before it is deleted.
     * @return how much time the trail has left before it is deleted.
     */
    public double getTimeLeft(){return(timeLeft);}

    /**
     * Sets how much time the trail has left before it is deleted.
     * @param timeLeft how much time the trail has left before it is deleted.
     */
    public void setTimeLeft(double timeLeft){this.timeLeft = timeLeft;}

    /**
     * Returns the amount of time the trail started with in frames.
     * @return the amount of time the trail started with in frames.
     */
    public double getStartTime(){return(startTime);}

    /**
     * Sets how much time the trail started with in frames.
     * @param startTime how much time the trail started with in frames.
     */
    public void setStartTime(double startTime){this.startTime = startTime;}

    /**
     * Returns the X position of the particle.
     * @return the X position of the particle.
     */
    public double getPosX(){return(posX);}

    /**
     * Sets the X position of the particle.
     * @param posX the X position of the particle.
     */
    public void setPosX(double posX){this.posX = posX;}

    /**
     * Returns the Y position of the particle.
     * @return the Y position of the particle.
     */
    public double getPosY(){return(posY);}

    /**
     * Sets the Y position of the particle.
     * @param posY the Y position of the particle.
     */
    public void setPosY(double posY){this.posY = posY;}

    /**
     * Draws the particle at its position.
     * @param s the spritebatch used to draw the particle.
     * @param shape the shape drawer used to draw the particle.
     */
    public void draw(SpriteBatch s, ShapeDrawer shape){
        shape.setColor(new Color(0f,0f,1f,((float)timeLeft)/((float)startTime)));
        shape.circle((float)posX,(float)posY,4);
        shape.setColor(new Color(0f,0.3f,1f, ((float)timeLeft)/((float)startTime)));
        shape.filledCircle((float)posX,(float)posY,3);
        shape.setColor(new Color(1f,1f,1f,1f));
    }
}

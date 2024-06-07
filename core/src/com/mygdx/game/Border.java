/* Nathan Becker, Muhammad Umar, Matthew Witherspoon
 * 6/11/2024
 * Border that the player has to outrun, player can check if it is within the border and take damage accordingly
 */
package com.mygdx.game;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import space.earlygrey.shapedrawer.ShapeDrawer;
public class Border {

    private double posY;
    private double speed;

    /**
     * Creates a border that travels up
     * @param startY The starting position of the border
     * @param speed The starting speed of the border
     */
    public Border(double startY, double speed){
        this.posY = startY;
        this.speed = speed;
    }

    /**
     * Updates the border's position
     * @param speedFactor Speed at which the game is running at
     */
    public void update(double speedFactor){
        posY += speed*speedFactor;
    }

    /**
     * Draws the border relative to the camera assuming the camera is 800 px wide
     * @param shapeDrawer Shape drawer from main game screen
     * @param camPos Position of the camera
     */
    public void draw(ShapeDrawer shapeDrawer, Vector3 camPos){
        shapeDrawer.setColor(1,0,0,0.5f);

        // Draws the border across the X axis of the screen and from the bottom of the camera to the position it needs to be at in the Y axis
        shapeDrawer.filledRectangle((camPos.x-400),camPos.y-400, (float)(800), (float) posY-camPos.y+400);
        shapeDrawer.setColor(1,1,1,1);
    }

    /**
     * Accessor for speed of the border
     * @return The speed of the border
     */
    public double getSpeed() {
        return speed;
    }

    /**
     * Accessor for position of the border
     * @return The position of the border
     */
    public double getPosY() {
        return posY;
    }

    /**
     * Mutator for position of the border
     * @param posY The new position of the border
     */
    public void setPosY(double posY) {
        this.posY = posY;
    }

    /**
     * Mutator for speed of the border
     * @param speed The new speed of the border
     */
    public void setSpeed(double speed) {
        this.speed = speed;
    }
}

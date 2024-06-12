/* Nathan Becker, Muhammad Umar, Matthew Witherspoon
 * 6/11/2024
 * Subclass of PhysicsObject, acts as an obstacle for the player
 */
package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import space.earlygrey.shapedrawer.ShapeDrawer;

public class Asteroid extends PhysicsObject{

    /**
     * Primary constructor
     * @param posX Start position x coordinate
     * @param posY Start position y coordinate
     * @param velX Start x velocity
     * @param velY Start y velocity
     * @param mass Mass of the asteroid
     * @param radius Radius of the asteroid
     */
    Asteroid(double posX, double posY, double velX, double velY, double mass, double radius){
        super(posX,posY,velX,velY,mass,radius);
    }
    /**
     * Secondary constructor, for an asteroid with an item
     * @param posX Start position x coordinate
     * @param posY Start position y coordinate
     * @param velX Start x velocity
     * @param velY Start y velocity
     * @param mass Mass of the asteroid
     * @param radius Radius of the asteroid
     * @param containsUpgrade If the asteroid has an item
     */
    Asteroid(double posX, double posY, double velX, double velY, double mass, double radius, boolean containsUpgrade){
        this(posX,posY,velX,velY,mass,radius);
    }

    /**
     * Draws the asteroid to the Galaxilize window
     * @param s The spritebatch defined in Galaxilize class
     */
    public void draw(SpriteBatch s, ShapeDrawer shape){
        shape.circle((float)posX,(float)posY,(float)radius, 3);
    }

    /**
     * Checks if two asteroid objects are equal to each other.
     * @param asteroid the asteroid being checked for equality.
     * @return
     */
    public boolean equals(Asteroid asteroid){
        return(super.equals(asteroid));
    }

    /**
     * Returns a string containing all the data of the asteroid.
     * @return a string containing all the data of the asteroid.
     */
    public String toString(){
        return(super.toString());
    }
}

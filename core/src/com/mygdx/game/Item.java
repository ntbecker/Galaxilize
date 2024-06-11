package com.mygdx.game;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import space.earlygrey.shapedrawer.ShapeDrawer;

public class Item {

    private double posX;
    private double posY;
    private boolean delete;
    private static final Texture itemTexture = new Texture("Object_Textures/Circle_Radius_10.png");

    /**
     * Constructor for Item, initializes with a position
     * @param posX X position of the item
     * @param posY Y position of the item
     */
    public Item(double posX, double posY){
        this.posX = posX;
        this.posY = posY;
        delete = false;
    }

    /**
     * Draws this item to the screen
     * @param s The open sprite batch to draw to
     */
    public void draw(SpriteBatch s){
        s.draw(itemTexture, (float) (posX-10), (float) (posY-10));
    }

    /**
     * Heals the player and flags self for deletion if successful
     * @param player The player object
     */
    public void isCollected(Player player){
        double dist = Math.pow(player.getPosX()-posX,2.0) + Math.pow(player.getPosY()-posY,2.0);

        if(Math.pow((player.getRadius() + 10),2.0)+100 > dist){
            player.addHealth(25);
            delete = true;
        }
    }

    /**
     * Accessor for X position
     * @return The X position of this Item
     */
    public double getPosX() {
        return posX;
    }

    /**
     * Mutator for X position
     * @param posX The new value for X position
     */
    public void setPosX(double posX) {
        this.posX = posX;
    }

    /**
     * Accessor for Y position
     * @return The Y position of this Item
     */
    public double getPosY() {
        return posY;
    }

    /**
     * Mutator for Y position
     * @param posY The new value for Y position
     */
    public void setPosY(double posY) {
        this.posY = posY;
    }
}

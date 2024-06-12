package com.mygdx.game;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import space.earlygrey.shapedrawer.ShapeDrawer;

public class Item extends PhysicsObject{
    private static final Texture healthTexture = new Texture("Object_Textures/Health_Item.png");
    private static final Texture fuelTexture = new Texture("Object_Textures/Fuel_Item.png");
    private boolean isHealth;
    /**
     * Constructor for Item, initializes with a position
     * @param posX X position of the item
     * @param posY Y position of the item
     */
    public Item(double posX, double posY, double velX, double velY, double mass, double radius, boolean isHealth){
        super(posX,posY,velX,velY,mass,radius);
        this.isHealth = isHealth;
    }

    /**
     * Draws this item to the screen
     * @param s The open sprite batch to draw to
     */
    public void draw(SpriteBatch s, ShapeDrawer shape){
        if(isHealth) {
            s.draw(healthTexture, (float) (posX - 10), (float) (posY - 10));
        }
        else{
            s.draw(fuelTexture, (float) (posX - 10), (float) (posY) - 10);
        }
    }

    /**
     * Returns if the item is health or fuel.
     * @return if the item is health or fuel.
     */
    public boolean isHealth(){return(isHealth);}

    /**
     * Sets if the item is health or fuel.
     * @param isHealth is the item health or fuel.
     */
    public void setIsHealth(boolean isHealth){this.isHealth = isHealth;}

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



    /**
     * Compare 2 items for equivalency
     * @param item The item object to compare this to
     * @return true if the item is the same
     */
    public boolean equals(Item item){
        return(super.equals(item) && isHealth == item.isHealth);
    }

    /**
     * Creates a table with all the item's information
     * @return A string containing item information
     */
    @Override
    public String toString() {
        return "Type: Item\n" +
                super.toString() +
                "\nIs a health pack: " + isHealth;
    }
}

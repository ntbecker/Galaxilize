package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

abstract public class PhysicsObject {

    protected double accX;
    protected double accY;
    protected double velX;
    protected double velY;
    protected double posX;
    protected double posY;
    protected double mass;
    protected double radius;

    // Temporary velocity for collision functions, If these have a different value when update is called, velX and Y are set to these values
    protected double nextVelX;
    protected double nextVelY;

    public PhysicsObject() {
        accX = 0;
        accY = 0;
        velX = 0;
        nextVelX = 0;
        velY = 0;
        nextVelY = 0;
        posX = 0;
        posY = 0;
        mass = 1;
        radius = 1;
    }

    public PhysicsObject(double posX, double posY, double velX, double velY, double mass, double radius) {
        this();
        this.velX = velX;
        nextVelX = velX;
        this.velY = velY;
        nextVelY = velY;
        this.posX = posX;
        this.posY = posY;
        this.mass = mass;
        this.radius = radius;
    }

    abstract public void draw(SpriteBatch game);

    public void updatePos(){
        // If a collision happened before this update, these values will be different
        if(nextVelX != velX){
            velX = nextVelX;
            nextVelX = velX;
        }
        if(nextVelY != velY){
            velY = nextVelY;
            nextVelY = velY;
        }

        velX += accX;
        velY += accY;

        posX += velX;
        posY += velY;
    }

    public void checkCollision(PhysicsObject other){
        if(isColliding(other)){
            // Call these here so that we don't repeat method calls in the next two formulas
            double otherMass = other.getMass();
            double otherVelX = other.getVelX();
            double otherVelY = other.getVelY();

            // Variable to simplify formula
            double massSum = mass + otherMass;

            // Velocity is changed next update
            nextVelX = ((mass - otherMass)/massSum)*velX + ((2*otherMass)/massSum)*otherVelX;
            nextVelY = ((mass - otherMass)/massSum)*velY + ((2*otherMass)/massSum)*otherVelY;
        }
    }

    protected boolean isColliding(PhysicsObject other){
        double distSquared = Math.pow((other.getPosX()-posX),2.0) + Math.pow((other.getPosY()-posY),2.0);
        double radiiSquared = Math.pow((other.getRadius()+radius),2.0);

        // If the objects are closer than the length of the two radii, return true because they are colliding
        return distSquared < radiiSquared;
    }

    /**
     * Accessor for Acceleration in the X direction
     * @return The double value of accX
     */
    public double getAccX() {
        return accX;
    }

    /**
     * Accessor for Acceleration in the Y direction
     * @return The double value of accY
     */
    public double getAccY() {
        return accY;
    }

    /**
     * Accessor for Velocity in the X direction
     * @return The double value of velX
     */
    public double getVelX() {
        return velX;
    }

    /**
     * Accessor for Velocity in the Y direction
     * @return The double value of velY
     */
    public double getVelY() {
        return velY;
    }

    /**
     * Accessor for Position in the X direction
     * @return The double value of posX
     */
    public double getPosX() {
        return posX;
    }

    /**
     * Accessor for Position in the Y direction
     * @return The double value of posY
     */
    public double getPosY() {
        return posY;
    }

    /**
     * Accessor for Mass of the object
     * @return The double value of mass
     */
    public double getMass() {
        return mass;
    }

    /**
     * Accessor for Radius of the object's collision
     * @return The double value of radius
     */
    public double getRadius() {
        return radius;
    }

    /**
     * Mutator for Acceleration in the X direction
     * @param accX The new double value of accX
     */
    public void setAccX(double accX) {
        this.accX = accX;
    }

    /**
     * Mutator for Acceleration in the Y direction
     * @param accY The new double value of accY
     */
    public void setAccY(double accY) {
        this.accY = accY;
    }

    /**
     * Mutator for Velocity in the X direction
     * @param velX The new double value of velX
     */
    public void setVelX(double velX) {
        this.velX = velX;
    }

    /**
     * Mutator for Velocity in the Y direction
     * @param velY The new double value of velY
     */
    public void setVelY(double velY) {
        this.velY = velY;
    }

    /**
     * Mutator for Position in the X direction
     * @param posX The new double value of posX. Make sure a collision won't occur if this value is changed, otherwise objects will become stuck inside each other
     */
    public void setPosX(double posX) {
        this.posX = posX;
    }

    /**
     * Mutator for Position in the Y direction
     * @param posY The new double value of posY. Make sure a collision won't occur if this value is changed, otherwise objects will become stuck inside each other
     */
    public void setPosY(double posY) {
        this.posY = posY;
    }

    /**
     * Mutator for Mass of the object
     * @param mass The new double value of mass
     */
    public void setMass(double mass) {
        this.mass = mass;
    }

    /**
     * Mutator for Radius of the object's collision. Make sure a collision won't occur if this value is changed, otherwise objects will become stuck inside each other
     * @param radius The new double value of radius
     */
    public void setRadius(double radius) {
        this.radius = radius;
    }
}

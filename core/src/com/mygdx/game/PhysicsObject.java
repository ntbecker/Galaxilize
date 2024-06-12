/* Nathan Becker, Muhammad Umar, Matthew Witherspoon
 * 6/11/2024
 * Abstract class for physics-affected objects, handles movement and collisions
 */

package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import space.earlygrey.shapedrawer.ShapeDrawer;

abstract public class PhysicsObject {

    // Attributes
    protected double accX;
    protected double accY;
    protected double velX;
    protected double velY;
    protected double posX;
    protected double posY;
    protected double mass;
    protected double radius;

    // Temporary velocity for collision functions. If hasCollided is true, then vel = nextVel
    protected double nextVelX;
    protected double nextVelY;
    protected boolean hasCollided;


    /**
     * Primary constructor, sets all position related values to 0, mass and radius to 1
     */
    public PhysicsObject() {
        accX = 0;
        accY = 0;
        velX = 0;
        velY = 0;
        posX = 0;
        posY = 0;
        mass = 1;
        radius = 1;


        // Assume no collisions are happening when the object is created
        hasCollided = false;
        nextVelX = 0;
        nextVelY = 0;
    }

    /**
     * Secondary constructor. Sets all values
     * @param posX Initial x coord
     * @param posY Initial y coord
     * @param velX Initial x velocity
     * @param velY Initial y velocity
     * @param mass Mass of the object
     * @param radius Radius of the object
     */
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

    /**
     * Draws the object to the Galaxilize window
     *
     * @param game The spritebatch defined in Galaxilize class
     * @param shapeDrawer The shapeDrawer defined in Galaxilize class
     */
    abstract public void draw(SpriteBatch game, ShapeDrawer shapeDrawer);

    public void updatePos(double speedFactor){

        // If this object has collided, nextVel is the new velocity
        if(hasCollided){
            velX = nextVelX;
            velY = nextVelY;
            // Move once to avoid colliding next frame
            posX += nextVelX*speedFactor;
            posY += nextVelY*speedFactor;

            hasCollided = false;
        }

        // Update velocity based on acceleration
        velX += accX*speedFactor;
        velY += accY*speedFactor;

        // Update position based on velocity
        posX += velX*speedFactor;
        posY += velY*speedFactor;
    }

    /**
     * Checks the collision between this object and another, and changes the velocity accordingly
     * @param other The other object that is being checked with
     * @param speedFactor The speed that the game is running at
     * @return if the collided object should be deleted on impact.
     */
    public boolean checkCollision(PhysicsObject other, double speedFactor){
        if(isColliding(other, speedFactor)){
            // Call these here so that we don't repeat method calls in the next two formulas
            double otherMass = other.getMass();
            double otherVelX = other.getVelX();
            double otherVelY = other.getVelY();

            // Magnitude of this object's velocity
            double vel = Math.sqrt(velX*velX + velY*velY);
            // Magnitude of the other object's velocity
            double otherVel = Math.sqrt(otherVelX*otherVelX + otherVelY*otherVelY);


            // Angle which points towards the other object
            double contactAngle = Math.atan2(other.getPosY()-posY,other.getPosX()-posX);
            // Angle of motion of this object
            double velAngle = Math.atan2(velY,velX);
            // Angle of motion of the other object
            double otherVelAngle = Math.atan2(otherVelY,otherVelX);

            // Two-dimensional collision with two moving objects formula
            // Velocity is changed next update, hence nextVel variables
            nextVelX = ((vel*Math.cos(velAngle - contactAngle)*(mass-otherMass) + 2*otherMass*otherVel*Math.cos(otherVelAngle-contactAngle))/(mass+otherMass))*Math.cos(contactAngle)+vel*Math.sin(velAngle-contactAngle)*Math.cos(contactAngle+Math.PI/2.0);
            nextVelY = ((vel*Math.cos(velAngle - contactAngle)*(mass-otherMass) + 2*otherMass*otherVel*Math.cos(otherVelAngle-contactAngle))/(mass+otherMass))*Math.sin(contactAngle)+vel*Math.sin(velAngle-contactAngle)*Math.sin(contactAngle+Math.PI/2.0);
            if(this instanceof Player){
                if(other instanceof Asteroid) {
                    double changeVel = Math.abs(this.velX - nextVelX) + Math.abs(this.velY - nextVelX);
                    if (changeVel > 10) {
                        ((Player) this).dealDamage((int) (changeVel / 5));
                    }
                }
                else{
                    if(((Item)other).isHealth()){
                        ((Player)this).addHealth(25);
                    }
                    else{
                        ((Player)this).addFuel(25);
                    }
                    return(true);
                }
            }
            // When hasCollided is true, next update the velocity is set to nextVel
            hasCollided = true;
        }
        return(false);
    }

    /**
     * Finds the distance from this asteroid to the mouse
     * @return The distance from the asteroid to the mouse
     */
    public double mouseDist(double mouseX, double mouseY){
        return Math.sqrt(Math.pow((posX-mouseX),2.0) + Math.pow((posY-mouseY),2.0));
    }

    /**
     * Checks if two objects are colliding
     * @param other The other object
     * @return True if the objects are overlapping
     */
    protected boolean isColliding(PhysicsObject other, double speedFactor){
        double distSquared;
        double radiiSquared = Math.pow((other.getRadius()+radius),2.0);

        // If the objects are closer than the length of the two radii, return true because they are colliding
        // Move slowly to check if the object will collide during the next update method call
        for(int i = 0; i < 10; i++) {
            distSquared = Math.pow((other.getPosX()+(other.getVelX()*speedFactor)*i/10.0-posX-(velX*speedFactor)*i/10.0),2.0) + Math.pow((other.getPosY()+(other.getVelY()*speedFactor)*i/10.0-posY-(velY*speedFactor)*i/10.0),2.0);
            if (distSquared < radiiSquared) {
                return true;
            }
        }
        // If all else fails, they aren't colliding
        return false;
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

    public boolean equals(PhysicsObject object){
        return(posX == object.getPosX() && posY == object.getPosY() && velX == object.getVelX() && velY == object.getVelY() && accX == object.getAccX() && accY == object.getAccY() && mass == object.getMass() && radius == object.getRadius());
    }

    /**
     * Returns a string containing all the data of the physics object.
     * @return a string containing all the data of the physics object.
     */
    public String toString(){
        return("X position: " + posX + "px\n" +
                "Y position: " + posY + "px\n" +
                "X velocity: " + velX + "px/frame\n" +
                "Y velocity: " + velY + "px/frame\n" +
                "X acceleration: " + accX + "px/frame^2\n" +
                "Y acceleration: " + accY + "px/frame^2\n" +
                "Mass: " + mass + "kg\n" +
                "Radius: " + radius + "px");
    }
}

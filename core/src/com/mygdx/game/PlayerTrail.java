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
    public double getTimeLeft(){return(timeLeft);}
    public void setTimeLeft(double timeLeft){this.timeLeft = timeLeft;}
    public double getStartTime(){return(startTime);}
    public void setStartTime(double startTime){this.startTime = startTime;}
    public double getPosX(){return(posX);}
    public void setPosX(double posX){this.posX = posX;}
    public double getPosY(){return(posY);}
    public void setPosY(double posY){this.posY = posY;}
    public void draw(SpriteBatch s, ShapeDrawer shape){
        shape.setColor(new Color(0f,0f,1f,((float)timeLeft)/((float)startTime)));
        shape.circle((float)posX,(float)posY,4);
        shape.setColor(new Color(0f,0.3f,1f, ((float)timeLeft)/((float)startTime)));
        shape.filledCircle((float)posX,(float)posY,3);
        shape.setColor(new Color(1f,1f,1f,1f));
    }
}

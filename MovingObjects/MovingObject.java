package MovingObjects;

import GenerateLevel.GameObjects;
import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.GraphicsContext;

public abstract class MovingObject extends GameObjects {

    private boolean colliding;

    private double groundheight;

    private boolean isJumping;
    private boolean moving;
    private boolean onGround;
    private boolean onBox;

    private double leftspeed;
    private double rightspeed;
    private double yspeed;

    private GraphicsContext gc;

    public abstract Rectangle2D getBottom();

    public abstract Rectangle2D getLeft();

    public abstract Rectangle2D getRight();

    public abstract Rectangle2D getTop();

    public abstract void redraw();

    public double getGroundheight() {
        return groundheight;
    }

    public boolean isJumping() {
        return isJumping;
    }

    public void setJumping(boolean jumping) {
        isJumping = jumping;
    }

    public boolean isMoving() {
        return moving;
    }

    public void setMoving(boolean moving) {
        this.moving = moving;
    }

    public boolean isOnGround() {
        return onGround;
    }

    public boolean isOnBox() {
        return onBox;
    }

    public void setOnBox(boolean onBox) {
        this.onBox = onBox;
    }

    public double getLeftspeed() {
        return leftspeed;
    }

    public void setLeftspeed(double leftspeed) {
        this.leftspeed = leftspeed;
    }

    public double getRightspeed() {
        return rightspeed;
    }

    public void setRightspeed(double rightspeed) {
        this.rightspeed = rightspeed;
    }

    public double getYspeed() {
        return yspeed;
    }

    public void setYspeed(double yspeed) {
        this.yspeed = yspeed;
    }

    public GraphicsContext getGc() {
        return gc;
    }

    public void setColliding(boolean colliding) {
        this.colliding = colliding;
    }

}

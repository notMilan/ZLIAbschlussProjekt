package obstacles;

import GenerateLevel.GameObjects;
import MovingObjects.MovingObject;
import javafx.geometry.Rectangle2D;

public abstract class Obstacles extends GameObjects {

    //Gibt zurück ob ein MovingObject mit einem Obstacle collided

    public boolean collisionTop(MovingObject object) {
        if (new Rectangle2D(getObjectX(), getObjectY(), getWidth(), 1).intersects(object.getBottom())) {
            return true;
        }
        if (new Rectangle2D(getObjectX(), getObjectY() -object.getYspeed(), getWidth(), getHeight()).intersects(object.getBottom())) {
            object.setObjectY(getObjectY() -object.getHeight());
            object.getGc().clearRect(object.getObjectX(), object.getObjectY()-object.getHeight(), object.getWidth(), object.getHeight());
            return false;
        }
        return false;
    }

    public boolean collisionBottom(MovingObject object) {
        return new Rectangle2D(getObjectX(), getObjectY(), getWidth(), getHeight()-object.getYspeed()).intersects(object.getTop());
    }

    public boolean collisionRight(MovingObject object) {
        return new Rectangle2D(getObjectX() + getWidth(), getObjectY(), 1, getHeight()).intersects(object.getLeft()) || new Rectangle2D(getObjectX(), getObjectY(), getWidth(), getHeight()).intersects(object.getLeft());
    }

    public boolean collisionLeft(MovingObject object) {
        return new Rectangle2D(getObjectX(), getObjectY(), 1, getHeight()).intersects(object.getRight()) || new Rectangle2D(getObjectX(), getObjectY(), getWidth(), getHeight()).intersects(object.getRight());
    }

    //testet alle Collision
    public void collision(MovingObject object) {
        //Collision detection left side of Box
        if (collisionLeft(object) && object.getRightspeed() != 0) {
            object.setObjectX(object.getObjectX()-object.getRightspeed());
            object.setColliding(true);
        }

        //Collision detection right side of Box
        if (collisionRight(object) && object.getLeftspeed() != 0) {
            object.setObjectX(object.getObjectX()-object.getLeftspeed());
            object.setColliding(true);
        }

        //Collision detection top of Box
        if (collisionTop(object) && object.getYspeed() > 0) {
            object.setJumping(false);
            object.setOnBox(true);
            object.setYspeed(0);
        }

        if (collisionBottom(object) && object.getYspeed() < 0) {
            object.setJumping(true);
            object.setOnBox(false);
            object.setObjectY(getObjectY() + getHeight());
            object.setYspeed(0);
            object.getGc().clearRect(object.getObjectX(), object.getObjectY()+object.getHeight(), object.getWidth(), object.getHeight());
        }
    }
}

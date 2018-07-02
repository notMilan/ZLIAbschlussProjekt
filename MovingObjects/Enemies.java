package MovingObjects;

import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;

public abstract class Enemies extends MovingObject{

    private double damage = 1;
    private boolean colliding;
    private boolean isDead;

    private Image slimeright = new Image(getClass().getResource("/resources/SlimeLvl0/slimeright.gif").toExternalForm());

    private Image slimeleft = new Image(getClass().getResource("/resources/SlimeLvl0/slimeleft.gif").toExternalForm());
    private Image leftdeath = new Image(getClass().getResource("/resources/SlimeLvl0/leftdeath.gif").toExternalForm());
    private Image rightdeath = new Image(getClass().getResource("/resources/SlimeLvl0/rightdeath.gif").toExternalForm());
    private Image fallingright = new Image(getClass().getResource("/resources/SlimeLvl0/slimerightfalling.gif").toExternalForm());
    private Image fallingleft = new Image(getClass().getResource("/resources/SlimeLvl0/slimeleftfalling.gif").toExternalForm());
    private Image currentimage;


    public boolean enemyCollidesWithCharLeft(MainChar mainChar) {
        return hitbox().intersects(mainChar.getLeft());
    }

    public boolean enemyCollidesWithCharRight(MainChar mainChar) {
        return hitbox().intersects(mainChar.getRight());
    }

    public boolean enemyCollidesWithCharTop(MainChar mainChar) {
        return hitbox().intersects(mainChar.getTop());
    }

    public boolean enemyCollidesWithCharBottom(MainChar mainChar) {
        return hitbox().intersects(mainChar.getBottom());
    }

    public boolean collisionTop(MovingObject object) {
        return new Rectangle2D(getObjectX(), getObjectY(), getWidth(), 1).intersects(object.getBottom()) || new Rectangle2D(getObjectX(), getObjectY() - object.getYspeed(), getWidth(), getHeight()).intersects(object.getBottom());
    }

    public boolean collisionRight(Enemies enemy) {
        return new Rectangle2D(getObjectX() + getWidth(), getObjectY(), 1, getHeight()).intersects(enemy.getLeft()) || new Rectangle2D(getObjectX(), getObjectY(), getWidth(), getHeight()).intersects(enemy.getLeft());
    }

    public boolean collisionLeft(Enemies enemy) {
        return new Rectangle2D(getObjectX() , getObjectY(), 1, getHeight()).intersects(enemy.getRight()) || new Rectangle2D(getObjectX(), getObjectY(), getWidth(), getHeight()).intersects(enemy.getRight());
    }

    public void collidesWithOtherEnemies(Enemies enemy) {
        //Collision detection left side of Enemy
        if (collisionLeft(enemy) && enemy.getRightspeed() != 0) {
            enemy.setColliding(true);
        }

        //Collision detection right side of Enemy
        if (collisionRight(enemy) && enemy.getLeftspeed() != 0) {
            enemy.setColliding(true);
        }

        //Collision detection top of Enemy
        if (collisionTop(enemy) && !enemy.isDead() && !isDead()) {
            enemy.setYspeed(-5);
            enemy.setJumping(true);
        }
    }

    //Methode um Slimes zusammenzufügen
    public Slime mergeSlimes(Slime enemy) {
        if (collisionTop(enemy) && (enemy.isDead() || isDead())) {
            if (this instanceof Slime) {
                this.setCurrentimage(null);
                ((Slime) this).mergeWithEnemy(enemy);
                return enemy;
            }
        }
        return null;
    }


    public Rectangle2D hitbox() {
        return new Rectangle2D(getObjectX(), getObjectY(), getWidth(), getHeight());
    }

    public boolean isDead() {
        return isDead;
    }

    public void setDead(boolean dead) {
        isDead = dead;
    }

    public Image getCurrentimage() {
        return currentimage;
    }

    public void setCurrentimage(Image currentimage) {
        this.currentimage = currentimage;
    }

    public Image getSlimeright() {
        return slimeright;
    }

    public void setSlimeright(Image slimeright) {
        this.slimeright = slimeright;
    }

    public Image getSlimeleft() {
        return slimeleft;
    }

    public void setSlimeleft(Image slimeleft) {
        this.slimeleft = slimeleft;
    }

    public Image getLeftdeath() {
        return leftdeath;
    }

    public void setLeftdeath(Image leftdeath) {
        this.leftdeath = leftdeath;
    }

    public Image getRightdeath() {
        return rightdeath;
    }

    public void setRightdeath(Image rightdeath) {
        this.rightdeath = rightdeath;
    }

    public Image getFallingright() {
        return fallingright;
    }

    public void setFallingright(Image fallingright) {
        this.fallingright = fallingright;
    }

    public Image getFallingleft() {
        return fallingleft;
    }

    public void setFallingleft(Image fallingleft) {
        this.fallingleft = fallingleft;
    }

    public double getDamage() {
        return damage;
    }

    public void setDamage(double damage) {
        this.damage = damage;
    }

}

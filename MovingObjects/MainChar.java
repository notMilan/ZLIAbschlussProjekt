package MovingObjects;

import Game.GameController;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;

public class MainChar extends MovingObject {

    private double objectX;
    private double objectY;
    private double width;
    private double height;

    private double groundheight;

    private double health;

    private boolean isJumping;
    private boolean moving;
    private boolean onGround;
    private boolean onBox;
    private boolean colliding;
    private boolean tookdmg;
    private boolean blink;

    private GraphicsContext gc;

    private Image standingright = new Image(getClass().getResource("/resources/charstandingstill1.gif").toExternalForm());
    private Image standingleft = new Image(getClass().getResource("/resources/charstandingstill3.gif").toExternalForm());
    private Image walkingright = new Image(getClass().getResource("/resources/charwalkingright.gif").toExternalForm());
    private Image walkingleft = new Image(getClass().getResource("/resources/charwalkingleft.gif").toExternalForm());
    private Image jumpingright = new Image(getClass().getResource("/resources/jumpingright.png").toExternalForm());
    private Image jumpingleft = new Image(getClass().getResource("/resources/jumpingleft.png").toExternalForm());

    private Image currentimage;

    private String lastdirection;

    private double leftspeed;
    private double rightspeed;
    private double yspeed;
    private double dmgnoticecounter;

    public MainChar(double width, double height, double charX, double charY, double leftspeed, double rightspeed, double yspeed, GraphicsContext gc, double groundheight, double health) {
        this.width = width;
        this.height = height;

        this.groundheight = groundheight;

        this.objectX = charX;
        this.objectY = charY;

        this.rightspeed = rightspeed;
        this.leftspeed = leftspeed;
        this.yspeed = yspeed;

        this.gc = gc;

        this.currentimage = standingright;
        this.lastdirection = "RIGHT";

        this.isJumping = false;
        this.onGround = true;
        this.onBox = false;
        this.moving = false;

        this.health = health;
        this.tookdmg = false;
        this.blink = false;
    }

    public void moveRight() {
        rightspeed = 2;
        lastdirection = "RIGHT";
        moving = true;
    }

    public void moveLeft() {
        leftspeed = -2;
        lastdirection = "LEFT";
        moving = true;
    }

    public void jump() {
        yspeed = -5.8;
        isJumping = true;
    }

    public void stopleft() {
        leftspeed = 0;
        moving = false;
    }

    public void stopright() {
        rightspeed = 0;
        moving = false;
    }

    //Ändert das momentan zu zeichnende Bild je nach gedrücktem Button
    public void changeImage() {
        //Hier wird geprüft ob die Figur sich auf dem Boden bewegt
        if ((onGround || onBox) && leftspeed + rightspeed != 0) {
            if (rightspeed != 0) {
                currentimage = walkingright;
            }
            else if (leftspeed != 0) {
                currentimage = walkingleft;
            }
        //Hier wird geprüft ob die Figur steht
        } else if ((onGround || onBox)) {
            if (lastdirection.equals("RIGHT")) {
                currentimage = standingright;
            } else if (lastdirection.equals("LEFT")) {
                currentimage = standingleft;
            }
        }
        //Hier wird geprüft ob die Figur gerade am springen ist
        else if (yspeed != 0) {
            if (lastdirection.equals("RIGHT")) {
                currentimage = jumpingright;
            } else if (lastdirection.equals("LEFT")) {
                currentimage = jumpingleft;
            }
        }

    }

    //Object springt oder fällt wenn Gewisse Foraussetzungen erfüllt sind
    public void jumpAndFall() {
        //Stopt das Objekt wenn es den Boden berührt
        if (groundheight <= objectY + yspeed) {
            isJumping = false;
            onGround = true;
            yspeed = 0;
        }

        //lässt das Objekt springen oder fallen je nach Höhe
        if ((groundheight > objectY && !onBox)) {
            yspeed += 0.2;
            yspeed = round(yspeed, 2);
            onGround = false;
            onBox = false;
        }
    }

    //Zeichnet das Momentane Bild je nach Zustand
    public void redraw() {
        //Jumping wird gesetzt
        if (onBox || onGround) {
            isJumping = false;
        } else {
            isJumping = true;
        }
        //Löscht vorheriges Bild
        gc.clearRect(gc.getCanvas().getLayoutX(), gc.getCanvas().getLayoutY(), gc.getCanvas().getWidth(), gc.getCanvas().getHeight());

        //Bewegt die Figur je nach speed
        objectX += rightspeed + leftspeed;
        objectY += yspeed;

        //rundet ObjectY da der yspeed mit kommazahlen gemacht ist und das rechnen mit denen sehr ungenau ist
        objectY = Math.round(objectY);

        jumpAndFall();
        changeImage();

        //lässt den Main Char blinken wenn er Schaden genommen hat
        damageNotice();
        if (health > 0 && !blink) {
            gc.drawImage(currentimage, objectX, objectY, width, height);
        }
    }

    //Skipped das zeichnen des Bildes wenn der Gegner Schaden genommen hat solange er Unverwundbar ist
    public void damageNotice() {
        if (this.tookdmg) {
            if (dmgnoticecounter % 5 == 0) {
                blink = true;
            } else {
                blink = false;
            }
            dmgnoticecounter++;
        } else {
            dmgnoticecounter = 0;
        }
    }

    //rundet eine Zahl auf die angegebene Stelle (Kopiert)
    public static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        BigDecimal bd = new BigDecimal(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }


    //Getters & Setters
    public Rectangle2D getBottom() {
        return new Rectangle2D(objectX + 8, objectY +height, width-16, 1);
    }

    public Rectangle2D getLeft() {
        return new Rectangle2D(objectX + 8, objectY, 1, height);
    }

    public Rectangle2D getRight() {
        return new Rectangle2D(objectX + width - 8, objectY, 1, height);
    }

    public Rectangle2D getTop() {
        return new Rectangle2D(objectX + 8, objectY, width-16, 1);
    }

    public double getObjectX() {
        return objectX;
    }

    public void setObjectX(double objectX) {
        this.objectX = objectX;
    }

    public double getObjectY() {
        return objectY;
    }

    public void setObjectY(double objectY) {
        this.objectY = objectY;
    }

    public double getWidth() {
        return width;
    }

    public void setWidth(double width) {
        this.width = width;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
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

    public void setOnGround(boolean onGround) {
        this.onGround = onGround;
    }

    public String getLastdirection() {
        return lastdirection;
    }

    public void setLastdirection(String lastdirection) {
        this.lastdirection = lastdirection;
    }

    public double getYspeed() {
        return yspeed;
    }

    public void setYspeed(double yspeed) {
        this.yspeed = yspeed;
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

    public boolean isOnBox() {
        return onBox;
    }

    public void setOnBox(boolean onBox) {
        this.onBox = onBox;
    }

    public GraphicsContext getGc() {
        return gc;
    }

    public void setGc(GraphicsContext gc) {
        this.gc = gc;
    }

    public boolean isColliding() {
        return colliding;
    }

    public void setColliding(boolean colliding) {
        this.colliding = colliding;
    }

    public double getHealth() {
        return health;
    }

    public void setHealth(double health) {
        this.health = health;
    }

    public boolean isTookdmg() {
        return tookdmg;
    }

    public void setTookdmg(boolean tookdmg) {
        this.tookdmg = tookdmg;
    }

}

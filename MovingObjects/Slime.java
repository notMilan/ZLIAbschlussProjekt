package MovingObjects;

import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

import java.math.BigDecimal;
import java.math.RoundingMode;


public class Slime extends Enemies {

    private double groundheight;

    private boolean moving;
    private boolean onGround;
    private boolean onBox;
    private boolean colliding;
    private boolean isDead;

    private GraphicsContext gc;

    private Image currentimage;

    private String lastdirection;

    private double leftspeed;
    private double rightspeed;
    private double yspeed;

    private double objectX;
    private double objectY;

    private double width;
    private double height;

    private double merged;

    private String slimeImageDirectory = "/resources/SlimeLvl0/";

    private Image slimeright = new Image(getClass().getResource(slimeImageDirectory + "slimeright.gif").toExternalForm());
    private Image slimeleft = new Image(getClass().getResource(slimeImageDirectory + "slimeleft.gif").toExternalForm());

    private Image leftdeath = new Image(getClass().getResource(slimeImageDirectory + "leftdeath.gif").toExternalForm());
    private Image rightdeath = new Image(getClass().getResource(slimeImageDirectory + "rightdeath.gif").toExternalForm());

    private Image fallingright = new Image(getClass().getResource(slimeImageDirectory + "slimerightfalling.gif").toExternalForm());
    private Image fallingleft = new Image(getClass().getResource(slimeImageDirectory + "slimeleftfalling.gif").toExternalForm());

    private Image bouncingright = new Image(getClass().getResource(slimeImageDirectory + "slimerightup.gif").toExternalForm());
    private Image bouncingleft = new Image(getClass().getResource(slimeImageDirectory + "slimeleftup.gif").toExternalForm());

    private int counter;

    public Slime(double width, double height, double objectX, double objectY, double leftspeed, double rightspeed, double yspeed, GraphicsContext gc, double groundheight, boolean onBox, boolean onGround, double merged) {
        this.objectX = objectX;
        this.objectY = objectY;

        this.width = width;
        this.height = height;
        this.groundheight = groundheight;

        this.merged = merged;

        this.moving = false;
        this.onGround = onGround;
        this.onBox = onBox;

        this.gc = gc;

        this.colliding = false;
        this.isDead = false;

        this.lastdirection = lastdirection;

        this.leftspeed = leftspeed;
        this.rightspeed = 2;
        this.yspeed = yspeed;

        this.currentimage = slimeright;

        this.counter = 0;

        changeLooks();
    }

    //Object springt oder fällt wenn Gewisse Foraussetzungen erfüllt sind
    public void jumpAndFall() {
        //Stopt das Objekt wenn es den Boden berührt
        if (groundheight <= objectY + yspeed) {
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

    //Bewegt den Slime je nach Bild
    public void moveSlime() {
        if (counter >= 50 && counter <= 60 && yspeed == 0 ) {
            if (!colliding) {
                if (currentimage == slimeright || currentimage == fallingright || currentimage == bouncingright) {
                    rightspeed = 2;
                    leftspeed = 0;
                } else if (currentimage == slimeleft || currentimage == fallingleft || currentimage == bouncingleft){
                    leftspeed = -2;
                    rightspeed = 0;
                }
            }
            if (colliding) {
                if (rightspeed != 0) {
                    rightspeed = 0;
                    leftspeed -= 2;
                }
                else if (leftspeed != 0) {
                    leftspeed = 0;
                    rightspeed += 2;
                }
                colliding = false;
            }
            moving = true;
        } else {
            rightspeed = 0;
            leftspeed = 0;
        }
    }

    public void changeImage() {
        //Wenn der Slime Sich auf dem Boden oder auf der Box befinden dann wechselt er das Bild
        if (onBox || onGround) {
            if (currentimage == fallingright) {
                currentimage = slimeright;
            } else if (currentimage == fallingleft){
                currentimage = slimeleft;
            }
        }
        //Wenn der Slime Sich nach Rechts oder Links bewegt dann wechselt er das Bild
        if (leftspeed + rightspeed != 0 && yspeed == 0) {
            if (rightspeed != 0) {
                currentimage = slimeright;
            }
            else if (leftspeed != 0) {
                currentimage = slimeleft;
            }
        //Wenn der Slime sich in der Luft befindet dann wechselt er das Bild
        } else if (yspeed != 0) {
            if (currentimage == slimeright || currentimage == fallingright || currentimage == bouncingright) {
                if (yspeed > 0) {
                    currentimage = fallingright;
                } else if (yspeed < 0) {
                    currentimage = bouncingright;
                }
            } else if (currentimage == slimeleft || currentimage == fallingleft || currentimage == bouncingleft) {
                if (yspeed > 0) {
                    currentimage = fallingleft;
                } else if (yspeed < 0) {
                    currentimage = bouncingleft;
                }
            }
        }
        //Wenn der Slime "tot" ist
        if (isDead) {
            if (currentimage == slimeright || currentimage == fallingright || currentimage == bouncingright) {
                currentimage = rightdeath;
                rightspeed = 0;
                leftspeed = 0;
            }
            else if (currentimage == slimeleft || currentimage == fallingleft || currentimage == bouncingleft) {
                currentimage = leftdeath;
                leftspeed = 0;
                rightspeed = 0;
            }
        }
    }

    //Zeichnet das Momentane Bild je nach Zustand
    public void redraw() {
        //löscht den letzten Frame
        gc.clearRect(objectX, objectY, width, height);

        //lässt den Slime auch wenn er Flach ist fallen
        if (isDead && objectY < groundheight && !onBox) {
            yspeed += 0.2;
            yspeed = round(yspeed, 2);
            objectY += yspeed;
        }

        //Wenn der Slime nicht "tot" ist dann soll er Sich bewegen
        if (!isDead) {
            objectY += yspeed;
            objectX += rightspeed + leftspeed;
            moveSlime();
            jumpAndFall();
        }
        //ändert Bilder und zeichnet es
        changeImage();
        gc.drawImage(currentimage, objectX, objectY, width, height);

        death();

        //counter zählt und reseted nach einer Sekunde falls der SLime nicht tot ist
        if (counter == 60 && !isDead) {
            counter = 0;
        }
        counter++;

    }

    //Drückt den Slime für 3 Sekunden flach
    public void death() {
        if (isDead && counter == 180) {
            isDead = false;
            counter = 0;
            if (currentimage == rightdeath) {
                currentimage = slimeright;
                rightspeed = 2;
                leftspeed = 0;
            }
            else if (currentimage == leftdeath) {
                currentimage = slimeleft;
                leftspeed = -2;
                rightspeed = 0;
            }
        }
    }

    //verändert das Aussehen des SLimes je nach merged Level
    public void changeLooks() {
        if (merged == 0) {
            slimeImageDirectory = "/resources/SlimeLvl0/";
            updateImages();
            setDamage(1);
        }
        else if (merged >= 1) {
            slimeImageDirectory = "/resources/SlimeLvl1/";
            updateImages();
            setDamage(merged + 1);
        }
    }

    //Lädt alle Images
    public void updateImages() {
        setSlimeleft(new Image(getClass().getResource(slimeImageDirectory + "slimeleft.gif").toExternalForm()));
        setSlimeright(new Image(getClass().getResource(slimeImageDirectory + "slimeright.gif").toExternalForm()));

        setLeftdeath(new Image(getClass().getResource(slimeImageDirectory + "leftdeath.gif").toExternalForm()));
        setRightdeath(new Image(getClass().getResource(slimeImageDirectory + "rightdeath.gif").toExternalForm()));

        setFallingleft(new Image(getClass().getResource(slimeImageDirectory + "slimeleftfalling.gif").toExternalForm()));
        setFallingright(new Image(getClass().getResource(slimeImageDirectory + "slimerightfalling.gif").toExternalForm()));

        setBouncingleft(new Image(getClass().getResource(slimeImageDirectory + "slimeleftup.gif").toExternalForm()));
        setBouncingright(new Image(getClass().getResource(slimeImageDirectory + "slimerightup.gif").toExternalForm()));

        currentimage = slimeright;
    }

    //Setzt die Attribute des Slimes neu und ändert dessen aussehen
    public void mergeWithEnemy(Slime enemy) {
        if (collisionTop(enemy) && (enemy.isDead() || isDead())) {
            this.setMerged(this.getMerged() + enemy.getMerged());
            this.setDamage(merged + enemy.getDamage());
            changeLooks();
        }
    }

    //rundet eine Zahl auf die angegebene Stelle (Kopiert)
    public static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        BigDecimal bd = new BigDecimal(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }

    public Rectangle2D hitbox() {
        return new Rectangle2D(getObjectX()+8, getObjectY(), getWidth()-16, getHeight());
    }

    public Rectangle2D getBottom() {
        return new Rectangle2D(objectX, objectY + height, width, 1);
    }

    public Rectangle2D getLeft() {
        return new Rectangle2D(objectX, objectY, 1, height);
    }

    public Rectangle2D getRight() {
        return new Rectangle2D(objectX + width, objectY, 1, height);
    }

    public Rectangle2D getTop() {
        return new Rectangle2D(objectX + 8, objectY, width-16, 1);
    }

    @Override
    public double getGroundheight() {
        return groundheight;
    }

    @Override
    public boolean isMoving() {
        return moving;
    }

    @Override
    public void setMoving(boolean moving) {
        this.moving = moving;
    }

    @Override
    public boolean isOnGround() {
        return onGround;
    }

    @Override
    public boolean isOnBox() {
        return onBox;
    }

    @Override
    public void setOnBox(boolean onBox) {
        this.onBox = onBox;
    }

    public GraphicsContext getGc() {
        return gc;
    }

    public void setGc(GraphicsContext gc) {
        this.gc = gc;
    }

    public Image getCurrentimage() {
        return currentimage;
    }

    public void setCurrentimage(Image currentimage) {
        this.currentimage = currentimage;
    }

    public String getLastdirection() {
        return lastdirection;
    }

    public void setLastdirection(String lastdirection) {
        this.lastdirection = lastdirection;
    }

    @Override
    public double getLeftspeed() {
        return leftspeed;
    }

    @Override
    public void setLeftspeed(double leftspeed) {
        this.leftspeed = leftspeed;
    }

    @Override
    public double getRightspeed() {
        return rightspeed;
    }

    @Override
    public void setRightspeed(double rightspeed) {
        this.rightspeed = rightspeed;
    }

    @Override
    public double getYspeed() {
        return yspeed;
    }

    @Override
    public void setYspeed(double yspeed) {
        this.yspeed = yspeed;
    }

    @Override
    public double getObjectX() {
        return objectX;
    }

    @Override
    public void setObjectX(double objectX) {
        this.objectX = objectX;
    }

    @Override
    public double getObjectY() {
        return objectY;
    }

    @Override
    public void setObjectY(double objectY) {
        this.objectY = objectY;
    }

    @Override
    public double getWidth() {
        return width;
    }

    @Override
    public void setWidth(double width) {
        this.width = width;
    }

    @Override
    public double getHeight() {
        return height;
    }

    @Override
    public void setHeight(double height) {
        this.height = height;
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

    public int getCounter() {
        return counter;
    }

    public void setCounter(int counter) {
        this.counter = counter;
    }

    public boolean isColliding() {
        return colliding;
    }

    public void setColliding(boolean colliding) {
        this.colliding = colliding;
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

    public boolean isDead() {
        return isDead;
    }

    public void setDead(boolean dead) {
        isDead = dead;
    }

    public double getMerged() {
        return merged;
    }

    public void setMerged(double merged) {
        this.merged = merged;
    }

    public String getSlimeImageDirectory() {
        return slimeImageDirectory;
    }

    public void setSlimeImageDirectory(String slimeImageDirectory) {
        this.slimeImageDirectory = slimeImageDirectory;
    }

    @Override
    public Image getFallingright() {
        return fallingright;
    }

    @Override
    public void setFallingright(Image fallingright) {
        this.fallingright = fallingright;
    }

    @Override
    public Image getFallingleft() {
        return fallingleft;
    }

    @Override
    public void setFallingleft(Image fallingleft) {
        this.fallingleft = fallingleft;
    }

    public Image getBouncingright() {
        return bouncingright;
    }

    public void setBouncingright(Image bouncingright) {
        this.bouncingright = bouncingright;
    }

    public Image getBouncingleft() {
        return bouncingleft;
    }

    public void setBouncingleft(Image bouncingleft) {
        this.bouncingleft = bouncingleft;
    }

}


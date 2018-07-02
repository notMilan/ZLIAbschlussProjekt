package HUD;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class Health {

    private double objectX;
    private double objectY;
    private double width;
    private double height;

    private double health;

    private Image heart = new Image(getClass().getResource("/resources/Heart.gif").toExternalForm());
    private Image emptyheart = new Image(getClass().getResource("/resources/EmptyHeart.png").toExternalForm());
    private Image currentimage;

    private GraphicsContext gc;

    public Health(GraphicsContext gc, double objectX, double objectY, double width, double height, double health) {
        this.gc = gc;

        this.width = width;
        this.height = height;
        this.objectX = objectX;
        this.objectY = objectY;

        this.health = health;

        currentimage = heart;
    }

    //Methode um ein Herz zu zeichnen
    public void drawHeart(double spacing, boolean isEmpty) {
        //Bild je nach übriger Health des Mainchars wechseln
        if (isEmpty) {
            currentimage = emptyheart;
        } else {
            currentimage = heart;
        }
        //Worheriges Herz clearen und einen neuen Frame zeichnen
        gc.clearRect(objectX + spacing, objectY, width, height);
        gc.drawImage(currentimage, objectX + spacing, objectY, width, height);
    }

    public double getHealth() {
        return health;
    }

    public void setHealth(double health) {
        this.health = health;
    }

    public double getObjectX() {
        return objectX;
    }

    public double getObjectY() {
        return objectY;
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

    public GraphicsContext getGc() {
        return gc;
    }

}

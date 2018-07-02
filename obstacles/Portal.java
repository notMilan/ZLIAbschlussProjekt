package obstacles;

import GenerateLevel.GameObjects;
import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class Portal extends GameObjects {
    private Image portal;

    private double objectX;
    private double objectY;

    private double width;
    private double height;

    private GraphicsContext gc;

    public Portal(double objectX, double objectY, double width, double height, GraphicsContext gc, Image portal) {
        this.width = width;
        this.height = height;
        this.objectX = objectX;
        this.objectY = objectY;

        this.gc = gc;

        this.portal = portal;
    }

    public void drawPortal() {
        gc.clearRect(objectX, objectY, width, height);
        gc.drawImage(portal , objectX, objectY, width, height);
    }

    public Rectangle2D hitBox() {
        return new Rectangle2D(objectX + width/4, objectY + height/4, width/2, height/2);
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
}

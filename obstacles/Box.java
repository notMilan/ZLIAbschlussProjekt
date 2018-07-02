package obstacles;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class Box extends Obstacles {

    private Image box;

    private double objectX;
    private double objectY;

    private double width;
    private double height;

    private GraphicsContext gc2;

    public Box(double boxX, double boxY, double width, double height, GraphicsContext gc2, Image box) {
        this.width = width;
        this.height = height;
        this.objectX = boxX;
        this.objectY = boxY;

        this.gc2 = gc2;

        this.box = box;
    }

    public void drawbox() {
        gc2.clearRect(objectX, objectY, width, height);
        gc2.drawImage(box, objectX, objectY, width, height);
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

    public GraphicsContext getGc2() {
        return gc2;
    }

    public void setGc2(GraphicsContext gc2) {
        this.gc2 = gc2;
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

}

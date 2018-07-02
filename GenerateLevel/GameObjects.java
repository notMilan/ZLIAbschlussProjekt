package GenerateLevel;

public abstract class GameObjects {

    //Basic GameObject (Obstacle, MainCHar, Slime, usw.)

    private double objectX;
    private double objectY;
    private double width;
    private double height;


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

}

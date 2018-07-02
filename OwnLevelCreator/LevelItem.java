package OwnLevelCreator;

import javafx.scene.image.Image;

public class LevelItem {
    public String name;
    public Image image;
    public int limit;
    public double height;
    public double width;
    public int usedCount;

    public LevelItem(String name, Image image, int limit, double height, double width, int usedCount){
        this.name = name;
        this.image = image;
        this.limit = limit;
        this.height = height;
        this.width = width;
        this.usedCount = usedCount;
    }
}

package GenerateLevel;


import MovingObjects.Enemies;
import MovingObjects.Slime;
import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import MovingObjects.MainChar;
import obstacles.Box;
import obstacles.Obstacles;
import obstacles.Portal;

import java.util.*;

public class Level {

    private MainChar mainChar;

    private ArrayList<Obstacles> allObstacles;
    private ArrayList<Enemies> allEnemies;
    private ArrayList<Portal> allPortals;

    private Image box = new Image(getClass().getResource("/resources/box.png").toExternalForm());
    private Image portal = new Image(getClass().getResource("/resources/portalV2.gif").toExternalForm());

    private double boxX;
    private double boxY;

    private int level;

    private int[][] ownLevel;

    private double width;
    private double height;

    private GraphicsContext gc2;
    private GraphicsContext gc3;
    private GraphicsContext gc4;

    public Level(double boxX, double boxY, double width, double height, GraphicsContext gc2, GraphicsContext gc3, GraphicsContext gc5, MainChar mainChar, int[][] ownLevel) {
        this.allObstacles = new ArrayList<>();
        this.allEnemies = new ArrayList<>();
        this.allPortals = new ArrayList<>();

        this.ownLevel = ownLevel;

        this.mainChar = mainChar;

        this.width = width;
        this.height = height;
        this.boxX = boxX;
        this.boxY = boxY;
        this.gc2 = gc2;
        this.gc3 = gc3;
        this.gc4 = gc5;
        this.level = 1;

        generateLevelLayout(level);

    }

    public boolean nextLevel() {
        for (Portal portal : allPortals) {
            if (mainChar.getRight().intersects(portal.hitBox()) || mainChar.getLeft().intersects(portal.hitBox()) || mainChar.getBottom().intersects(portal.hitBox()) || mainChar.getTop().intersects(portal.hitBox())) {
                level++;
                gc3.clearRect(gc3.getCanvas().getLayoutX(), gc3.getCanvas().getLayoutY(), gc3.getCanvas().getWidth(), gc3.getCanvas().getHeight());
                gc4.clearRect(gc4.getCanvas().getLayoutX(), gc4.getCanvas().getLayoutY(), gc4.getCanvas().getWidth(), gc4.getCanvas().getHeight());
                mainChar.getGc().clearRect(mainChar.getObjectX(), mainChar.getObjectY(), mainChar.getWidth(), mainChar.getHeight());
                generateLevelLayout(level);
                mainChar.setYspeed(0);
                return true;
            }
        }
            return false;

    }

    public void generateLevelLayout(int level) {
        //Hier werden die von mir bestimmenten Level geladen
        if (ownLevel == null) {
            switch (level) {
                case 1:
                    generateLevel(0, new int[][]{
                            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 4},
                            {0, 0, 0, 1, 1, 0, 0, 0, 0, 0, 1, 0, 0, 1},
                            {5, 0, 1, 1, 1, 3, 0, 3, 0, 2, 1, 0, 2, 0}
                    });
                    break;

                case 2:
                    generateLevel(0, new int[][]{
                            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 3, 0},
                            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                            {4, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                            {1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 5},
                            {0, 0, 0, 1, 1, 0, 0, 1, 0, 0, 0, 0, 0, 1},
                            {0, 0, 0, 0, 0, 0, 1, 1, 2, 0, 0, 1, 1, 1}
                    });
                    break;

                case 3:
                    generateLevel(0, new int[][]{
                            {4, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                            {1, 1, 1, 0, 0, 1, 1, 0, 0, 1, 1, 0, 0, 0},
                            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
                            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1},
                            {5, 0, 0, 0, 1, 0, 1, 0, 0, 0, 1, 1, 1, 1},
                            {1, 1, 2, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0},
                            {1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                            {1, 1, 1, 0, 2, 0, 0, 3, 0, 0, 0, 3, 0, 2}
                    });
                    break;

                case 4:
                    generateLevel(0, new int[][]{
                            {5, 2, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 3, 3},
                            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 4},
                            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 2, 1},
                            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1},
                            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 1},
                            {1, 1, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0},
                            {1, 1, 1, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                            {1, 1, 1, 0, 0, 0, 3, 0, 0, 2, 0, 0, 0, 3}
                    });
                    break;

                default:
                    generateLevel(0, new int[][]{
                            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                            {0, 1, 1, 0, 1, 0, 0, 1, 0, 0, 1, 0, 0, 5},
                            {0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
                            {0, 1, 2, 0, 0, 3, 0, 0, 3, 0, 2, 0, 0, 3},
                            {0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
                            {0, 0, 0, 3, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                            {0, 0, 1, 0, 1, 1, 0, 0, 1, 1, 0, 0, 1, 0},
                            {1, 1, 1, 2, 1, 1, 1, 0, 1, 1, 1, 0, 1, 4},
                    });
                    break;

            }
            //Falls man sein eigenes Level spielen will wird dies hier geladen
        } else {
            generateLevel(0, this.ownLevel);
        }
    }

    public void generateLevel(double startingX, int[][] layout) {

        //Hier werden die GameObjects je nach Kategorie in neue Listen eingeteilt

        ArrayList<GameObjects> allGameObjects = generateField(startingX, layout);
        ArrayList<Obstacles> allBoxes = new ArrayList<>();
        ArrayList<Enemies> allSlimes = new ArrayList<>();
        ArrayList<Portal> allPortals = new ArrayList<>();

        for (GameObjects gameObjects : allGameObjects) {
            if (gameObjects instanceof Obstacles) {
                allBoxes.add((Box)gameObjects);
            }
        }

        for (GameObjects gameObjects : allGameObjects) {
            if (gameObjects instanceof Enemies) {
                allSlimes.add((Slime)gameObjects);
            }
        }

        for (GameObjects gameObjects : allGameObjects) {
            if (gameObjects instanceof Portal) {
                allPortals.add((Portal)gameObjects);
            }
        }

        this.allEnemies = allSlimes;
        this.allObstacles = allBoxes;
        this.allPortals = allPortals;
    }

    //Generiert das Feld anhand von den Zahlen von 1-5 in Arrays
    public ArrayList<GameObjects> generateField(double startingX, int[][] objects) {

        //Alle Gameobjects werden hier gespeichert
        ArrayList<GameObjects> gameObjects = new ArrayList<>();

        double maxY = boxY + height - objects.length * height;
        double addheight = 0;
        double spacing;
        int amountobjects = 0;

        //Looped durch den ersten Teil des Arrays
        for (int i = 0; i < objects.length; i++) {
            spacing = 0;
            //Looped durch den zweiten Teil des Arrays
            for (int j = 0; j < objects[i].length; j++) {
                //Falls es kein Object eintragen soll
                if (objects[i][j] == 0) {
                    spacing += width;
                }
                //Falls es ein Objekt intragen soll
                if (objects[i][j] != 0) {
                    //Falls es die erste Reihe generieren muss
                    if (i == 0) {
                        //Falls es das erste Objekt in der ersten Reihe generieren muss
                        if (j == 0) {
                            addGameObjects(objects, i, j, gameObjects, startingX ,maxY);
                            amountobjects++;
                        } else {
                            //wenn das erste Feld nichts gespeichert hat
                            if (amountobjects > 0) {
                                addGameObjects(objects, i, j, gameObjects, gameObjects.get(amountobjects - 1).getObjectX() + width + spacing, maxY);
                                amountobjects++;
                            } else {
                                addGameObjects(objects, i, j, gameObjects, startingX + spacing, maxY);
                                amountobjects++;
                            }
                        }
                    } else {
                        //Falls es das erste Objekt in der ersten Reihe generieren muss
                        if (j == 0) {
                            addGameObjects(objects, i, j, gameObjects, startingX + spacing, maxY + addheight);
                            amountobjects++;
                        } else {
                            //wenn das erste Feld etwas gespeichert hat
                            if (amountobjects > 0) {
                                //wenn das jetzige objekt das erste ist
                                if (gameObjects.get(amountobjects - 1).getObjectY() < maxY + addheight) {
                                    addGameObjects(objects, i, j, gameObjects, startingX + spacing, maxY + addheight);
                                    amountobjects++;
                                } else {
                                    addGameObjects(objects, i, j, gameObjects, gameObjects.get(amountobjects - 1).getObjectX() + width + spacing, maxY + addheight);
                                    amountobjects++;
                                }
                            } else {
                                addGameObjects(objects, i, j, gameObjects, startingX + spacing, maxY + addheight);
                                amountobjects++;
                            }
                        }
                    }
                    //spacing wird nach jeder Reihe immer wieder zurückgesetzt
                    spacing = 0;
                }
            }
            //Höhe immer wieder auf die nächste Reihe pro Reihe
            addheight += height;
        }

        return gameObjects;
    }

    //Funktion um die jeweilig verlangten GameObjects hinzuzufügen
    public void addGameObjects(int[][] objects, int i, int j, ArrayList<GameObjects> gameObjects, double objX, double objY) {
        if (objects[i][j] == 1) {
            //Fügt Box hinzu
            gameObjects.add(new Box(objX, objY, width, height, gc2, box));
        } else if (objects[i][j] == 2) {
            //Fügt SlimeLvl1 hinzu
            gameObjects.add(new Slime(50, 34, objX, objY, 0, 0, 0, gc3, 470, false, true, 0));
        } else if (objects[i][j] == 3) {
            //Fügt SlimeLvl2 hinzu
            gameObjects.add(new Slime(50, 34, objX, objY, 0, 0, 0, gc3, 470, false, true, 1));
        } else if (objects[i][j] == 4) {
            //Fügt Portal hinzu
            gameObjects.add(new Portal(objX, objY, width, height, gc4, portal));
        }
        else if (objects[i][j] == 5) {
            //Fügt MainChar Starting Position hinzu
            mainChar.setObjectX(objX);
            mainChar.setObjectY(objY);
            mainChar.setYspeed(0);
            gameObjects.add(mainChar);
        }
    }

    //Zeichnet das Portal
    public void setFinish() {
        for (Portal portal : allPortals) {
            portal.drawPortal();
        }
    }

    //Getters and Setters
    public Rectangle2D getLeftBorder() {
        return new Rectangle2D(mainChar.getGc().getCanvas().getLayoutX() - width, mainChar.getGroundheight(), width, mainChar.getGc().getCanvas().getHeight());
    }

    public Rectangle2D getRightBorder() {
        return new Rectangle2D(mainChar.getGc().getCanvas().getLayoutX()+mainChar.getGc().getCanvas().getWidth(), mainChar.getGroundheight(), width, mainChar.getGc().getCanvas().getHeight());
    }

    public ArrayList<Obstacles> getAllObstacles() {
        return allObstacles;
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

    public ArrayList<Enemies> getAllEnemies() {
        return allEnemies;
    }

    public void setAllEnemies(ArrayList<Enemies> allEnemies) {
        this.allEnemies = allEnemies;
    }

    public void setOwnLevel(int[][] ownLevel) {
        this.ownLevel = ownLevel;
    }

}

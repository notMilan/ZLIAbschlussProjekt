package Collisions;

import GenerateLevel.Level;
import MovingObjects.Enemies;
import MovingObjects.MainChar;
import MovingObjects.MovingObject;
import MovingObjects.Slime;
import obstacles.Box;
import obstacles.Obstacles;

import java.util.ArrayList;

public class Collisions {

    private double iframe = 120;
    private boolean iframecounter = false;

    //Hier werden alle Collision Detection Methoden ausgeführt
    public void testAllCollisions(ArrayList<Obstacles> obstacles, ArrayList<Enemies> enemies, MainChar mainChar, Level level) {
        movingObjectWithObstacleCollision(obstacles, enemies, mainChar);
        charWithMovingObjectCollision(enemies, mainChar);
        borderCollision(enemies, mainChar, level);
        enemyCollidingWithEnemy(enemies, level);
    }

    //Hier wird geprüft ob der Char mit einem MovingObject Kollidiert das ein Gegner ist
    public void charWithMovingObjectCollision(ArrayList<Enemies> movingObjects, MainChar mainChar) {
        for (MovingObject movingObject : movingObjects) {
            if (movingObject instanceof Enemies) {
                if (((Enemies) movingObject).enemyCollidesWithCharBottom(mainChar) && mainChar.getYspeed() > 0 && !((Enemies)movingObject).isDead() && mainChar.getHealth() != 0) {
                    movingObject.getGc().clearRect(movingObject.getObjectX(), movingObject.getObjectY(), movingObject.getWidth(), movingObject.getHeight());
                    ((Enemies)movingObject).setDead(true);
                    movingObject.setRightspeed(0);
                    movingObject.setLeftspeed(0);
                    movingObject.setMoving(false);
                    ((Slime)movingObject).setCounter(0);
                    mainChar.setYspeed(-5.8);
                }
                //Hier wird der Unverwundbarkeits Timer gestartet
                if (iframe == 120) {
                    mainChar.setTookdmg(false);
                    //Hier wird getestet ob der Char schaden nimmt
                    if ((((Enemies) movingObject).enemyCollidesWithCharTop(mainChar) || ((Enemies) movingObject).enemyCollidesWithCharRight(mainChar) || ((Enemies) movingObject).enemyCollidesWithCharLeft(mainChar)) && mainChar.getHealth() > 0 && !((Enemies)movingObject).isDead() && mainChar.getYspeed() <= 0) {
                        //Wenn MainChar Schaden nimmt dann soll er so viel Leben verlieren wie der Slime Schaden machen kann und für 2 Sekunden unverwundbar sein
                        iframe = 0;
                        mainChar.setHealth(mainChar.getHealth() - ((Enemies) movingObject).getDamage());
                        mainChar.setTookdmg(true);
                        iframecounter = true;
                    } else {
                        iframecounter = false;
                    }
                }
            }
        }
        //Falls schaden genommen worden ist zählt der Timer 2 sekunden lang hoch
        if (iframecounter) {
            iframe++;
        }
    }

    //Falls ein runtergedrückter Slime auf einen normalen Slime oder umgekehrt fällt sollen diese "mergen"
    public void enemyCollidingWithEnemy(ArrayList<Enemies> enemies, Level level) {
        ArrayList<Enemies> enemiescopy = new ArrayList<>();
        //Kopie von den Enemies da der das löschen während dem Iterieren nicht optimal ist
        enemiescopy.addAll(enemies);
        for (Enemies enemy : enemies) {
            for (Enemies allenemies : enemies) {
                //Prüfen ob Gegner mit einem anderen übereinstimmt
                if (!enemy.equals(allenemies)) {
                    enemy.getGc().clearRect(enemy.getGc().getCanvas().getLayoutX(), enemy.getGc().getCanvas().getLayoutY(), enemy.getGc().getCanvas().getWidth(), enemy.getGc().getCanvas().getHeight());
                    enemy.collidesWithOtherEnemies(allenemies);
                    if (allenemies instanceof Slime) {
                        //Der Slime wird gelöscht
                        enemiescopy.remove(enemy.mergeSlimes((Slime) allenemies));
                    }
                }
            }
        }
        //Die Gegnerliste wird ge-updated
        level.setAllEnemies(enemiescopy);
    }

    //HIer wird überprüft ob ein MovingObject mit einem Obstacle Collided
    public void movingObjectWithObstacleCollision(ArrayList<Obstacles> obstacles, ArrayList<Enemies> enemies, MainChar mainChar) {
        for (Obstacles obstacle : obstacles) {
            for (MovingObject movingObject : enemies) {
                movingObject.setOnBox(multipleCollisionsTest(movingObject, obstacles));
                movingObject.setJumping(!multipleCollisionsTest(movingObject, obstacles));
                obstacle.collision(movingObject);
            }
            mainChar.setOnBox(multipleCollisionsTest(mainChar, obstacles));
            obstacle.collision(mainChar);
            ((Box)obstacle).drawbox();
        }
    }

    public boolean multipleCollisionsTest(MovingObject object, ArrayList<Obstacles> obstacles) {
        for (Obstacles obstacle : obstacles) {
            if (obstacle.collisionTop(object)) {
                return true;
            }
        }
        return false;
    }

    //Hier wird die Map eingeschränk sodass MovingObjects nicht durch das zu sehende Level gehen können
    public void borderCollision(ArrayList<Enemies> enemies, MainChar mainChar, Level level) {
        for (MovingObject object : enemies) {
            if (object.getLeft().intersects(level.getLeftBorder()) && object.getLeftspeed() != 0) {
                if (object instanceof Enemies) {
                    object.setColliding(true);
                }
            } else if (object.getRight().intersects(level.getRightBorder()) && object.getRightspeed() != 0) {
                if (object instanceof Enemies) {
                    object.setColliding(true);
                } else if (object instanceof MainChar) {
                    object.setObjectX(object.getObjectX() - object.getRightspeed());
                }
            }
        }
        if (mainChar.getLeft().intersects(level.getLeftBorder()) && mainChar.getLeftspeed() != 0) {
            mainChar.setObjectX(mainChar.getObjectX() - mainChar.getLeftspeed());
        } else if (mainChar.getRight().intersects(level.getRightBorder()) && mainChar.getRightspeed() != 0) {
            mainChar.setObjectX(mainChar.getObjectX() - mainChar.getRightspeed());
        }
    }

}

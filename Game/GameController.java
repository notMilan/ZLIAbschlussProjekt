package Game;

import Collisions.Collisions;
import HUD.Health;
import HUD.Taskbar;
import GenerateLevel.Level;
import MovingObjects.MainChar;
import MovingObjects.MovingObject;
import javafx.animation.AnimationTimer;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class GameController {

    @FXML
    public Canvas canvas;

    @FXML
    public Canvas canvas2;

    @FXML
    public Canvas canvas3;

    @FXML
    public Canvas canvas4;

    @FXML
    public Label lable;


    private double charX;
    private double charY;
    private double charWidth;
    private double charHeight;

    private double groundheight;
    private double boxheight;

    private double boxWidth;
    private double boxHeight;
    double boxX;
    double boxY;

    private GraphicsContext gc;
    private GraphicsContext gc2;
    private GraphicsContext gc3;
    private GraphicsContext gc4;

    private double leftspeed;
    private double rightspeed;

    private double yspeed;
    private double lastY;

    boolean onObstacles;

    private MainChar mainChar;
    private Level level;
    private Health health;
    private Taskbar taskbar;
    private Collisions colli;

    private int[][] ownLevel;

    //Allen Variablen beim Laden des fxml files Werte zuweisen
    public void initialize() {
        this.gc = canvas.getGraphicsContext2D();
        this.gc2 = canvas2.getGraphicsContext2D();
        this.gc3 = canvas3.getGraphicsContext2D();
        this.gc4 = canvas4.getGraphicsContext2D();

        this.groundheight = 450;
        this.boxheight = 450;

        this.charX = 0;
        this.charY = groundheight;
        this.charWidth = 48;
        this.charHeight = 52;
        this.leftspeed = 0;
        this.rightspeed = 0;
        this.yspeed = 0;
        this.lastY = charY;

        this.boxHeight = 56;
        this.boxWidth = 56;
        this.boxX = 250;
        this.boxY = groundheight;

        onObstacles = false;

        mainChar = new MainChar(charWidth, charHeight, charX, charY, leftspeed, rightspeed, yspeed, gc, groundheight, 5);
        health = new Health(gc3, 40, 525, 40, 40, mainChar.getHealth());
        level = new Level(boxX, boxY, boxWidth, boxHeight, gc2, gc3, gc4, mainChar, ownLevel);
        colli = new Collisions();
        taskbar = new Taskbar(health);
        gameLoop();
    }

    public GameController(int[][] ownLevel) {
        if (ownLevel != null) {
            this.ownLevel = ownLevel;
        } else {
            this.ownLevel = null;
        }
    }

    //Hier werden die in dem Animation Timer geschriebenen Methoden 60 mal in der Sekunde wiederholt ausgeführt
    public void gameLoop() {
        AnimationTimer animationTimer = new AnimationTimer(){
            @Override
            public void handle(long now) {
                //Ändert das Level wenn man ein eigenes Level spielt
                level.setOwnLevel(ownLevel);

                //Added Controls für das Bewegen des Main Chars
                addKeyInput(canvas.getScene(), this);

                //Überprüft ob man in das Portal ist, und wenn ja dann wechselt es das Level
                if (level.nextLevel()) {
                    gc2.clearRect(gc2.getCanvas().getLayoutX(), gc2.getCanvas().getLayoutY(), gc2.getCanvas().getWidth(), gc2.getCanvas().getHeight());
                }

                //Tested alle Kollisionen
                colli.testAllCollisions(level.getAllObstacles(), level.getAllEnemies(), mainChar, level);

                //Zeichnet alle MovingObjects
                for (MovingObject movingObject : level.getAllEnemies()) {
                    movingObject.redraw();
                }

                level.setFinish();
                health.setHealth(mainChar.getHealth());
                taskbar.drawHealth();
                mainChar.redraw();
//                updateoverview();
                if (mainChar.getHealth() <= 0) {
                    this.stop();
                    try {
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Mainmenu/MainMenu.fxml"));
                        Stage stage = (Stage) lable.getScene().getWindow();
                        Scene newScene = new Scene(loader.load());
                        newScene.getStylesheets().add(getClass().getResource("/resources/css/styles.css").toExternalForm());
                        stage.setScene(newScene);
                    }catch (Exception ex){
                        ex.printStackTrace();
                    }
                }
            }
        };
        animationTimer.start();
    }

    public void updateoverview() {
        lable.setText(
                "IsJumping: " + mainChar.isJumping() + "\n"
                        + "IsMoving: " + mainChar.isMoving() + "\n"
                        + "OnGround: " + mainChar.isOnGround() + "\n"
                        + "OnBox: " + mainChar.isOnBox() + "\n"
                        + "Yspeed: " + mainChar.getYspeed() + "\n"
                        + "LeftSpeed: " + mainChar.getLeftspeed() + "\n"
                        + "RightSpeed: " + mainChar.getRightspeed() + "\n"
                        + "CharX: " + mainChar.getObjectX() + "\n"
                        + "CharY: " + mainChar.getObjectY()
        );
    }

    //Hier werden die Keyevents hinzugefügt
    public void addKeyInput(Scene scene, AnimationTimer animationTimer) {
        scene.setOnKeyPressed((event) -> {
            //Nach Rechts bewegen
            if (event.getCode() == KeyCode.RIGHT || event.getCode() == KeyCode.D) {
                mainChar.moveRight();
            }
            //Nach Links bewegen
            if (event.getCode() == KeyCode.LEFT || event.getCode() == KeyCode.A) {
                mainChar.moveLeft();
            }
            //Springen
            if ((event.getCode() == KeyCode.SPACE || event.getCode() == KeyCode.W || event.getCode() == KeyCode.UP) && mainChar.getYspeed() == 0 && !mainChar.isJumping()) {
                mainChar.jump();
            }
            if (event.getCode() == KeyCode.ESCAPE) {
                try {
                    animationTimer.stop();
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/Mainmenu/MainMenu.fxml"));
                    Stage stage = (Stage) scene.getWindow();
                    Scene newScene = new Scene(loader.load());
                    newScene.getStylesheets().add(getClass().getResource("/resources/css/styles.css").toExternalForm());
                    stage.setScene(newScene);
                }catch (Exception ex){
                    ex.printStackTrace();
                }
            }
        });

        //Stehen bleiben
        scene.setOnKeyReleased(event -> {
            if (event.getCode() == KeyCode.RIGHT || event.getCode() == KeyCode.D) {
                mainChar.stopright();
            }
            if (event.getCode() == KeyCode.LEFT || event.getCode() == KeyCode.A) {
                mainChar.stopleft();
            }
        });
    }
}

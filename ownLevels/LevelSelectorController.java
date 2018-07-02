package ownLevels;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import Game.GameController;

import java.io.*;
import java.net.URL;
import java.security.CodeSource;
import java.util.Objects;
import java.util.Scanner;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class LevelSelectorController {

    @FXML
    private Pane pane;

    //Lädt alle Buttons
    public void initialize() {
        accessFile();
        addBackToMenu();
    }

    //ESC Button befürdert einen zurück zum Menu
    public void addBackToMenu() {
        pane.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ESCAPE) {
                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/Mainmenu/MainMenu.fxml"));
                    Stage stage = (Stage) pane.getScene().getWindow();
                    Scene newScene = new Scene(loader.load());
                    newScene.getStylesheets().add(getClass().getResource("/resources/css/styles.css").toExternalForm());
                    stage.setScene(newScene);
                }catch (Exception ex){
                    ex.printStackTrace();
                }
            }
        });
    }

    //Holt alle Files und erstellt einen Button für diese
    public void accessFile() {
        Button btn;
        double xspacing = 20;
        double yspacing = 100;
        try {
            CodeSource src = LevelSelectorController.class.getProtectionDomain().getCodeSource();
            if (src != null) {
                URL jar = src.getLocation();
                ZipInputStream zip = new ZipInputStream(jar.openStream());
                if (zip.getNextEntry() != null) {
                    //Wenn ausgeführt im Jar
                    ZipEntry e = zip.getNextEntry();
                    File source = new File(LevelSelectorController.class.getProtectionDomain().getCodeSource().getLocation().toURI());
                    File dir = new File(source.getParentFile() + "/allLevel/");
                    for (String lvlfilename : Objects.requireNonNull(dir.list())) {
                        if (xspacing >= 800) {
                            yspacing += 50;
                            xspacing = 20;
                        }
                        btn = new Button(lvlfilename.replaceAll("allLevel/", "").replaceAll(".txt", ""));
                        btn.setLayoutX(xspacing);
                        btn.setLayoutY(yspacing);
                        btn.setPrefWidth(150);
                        addButtonFunction(btn, readLvl(new File(dir + "/" + lvlfilename)));
                        pane.getChildren().add(btn);
                        xspacing += 200;
                    }
                } else {
                    //wenn ausgeführt in editor
                    File dir = new File("out/artifacts/Produkt_jar/allLevel/");
                    for (String lvlfilename : Objects.requireNonNull(dir.list())) {
                        if (xspacing >= 800) {
                            yspacing += 50;
                            xspacing = 20;
                        }
                        btn = new Button(lvlfilename.replaceAll("allLevel/", "").replaceAll(".txt", ""));
                        btn.setLayoutX(xspacing);
                        btn.setLayoutY(yspacing);
                        btn.setPrefWidth(150);
                        addButtonFunction(btn, readLvl(new File(dir +  "/" + lvlfilename)));
                        pane.getChildren().add(btn);
                        xspacing += 200;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    //Added eine Methode die auf klick das Level mitgibt
    public void addButtonFunction(Button button, int[][] ownLevel) {
        button.setOnAction(event -> {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/Game/game.fxml"));
                Stage stage = (Stage) pane.getScene().getWindow();
                //Gibt dem Konstruktor des GameControllers das eigene Level mit
                GameController controller = new GameController(ownLevel);
                loader.setController(controller);
                Scene scene = new Scene(loader.load());
                scene.getStylesheets().add(getClass().getResource("/resources/css/styles.css").toExternalForm());
                stage.setScene(scene);
            } catch (IOException io) {
                io.printStackTrace();
            }
        });
    }

    //Liest das eigene level aus dem File
    public int[][] readLvl(File file) {
        int[][] ownLevel = new int[8][14];
        Scanner s;
        try {
            s = new Scanner(file);
            for (int i = 0; i < ownLevel.length; i++) {
                for (int j = 0; j < ownLevel[i].length; j++) {
                    ownLevel[i][j] = s.nextInt();
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return ownLevel;
    }

}
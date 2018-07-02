package Mainmenu;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import Game.GameController;

import java.io.IOException;

public class MenuController {

    @FXML
    private Button startGame;

    @FXML
    private Button openLevelCreator;

    @FXML
    private Button startOwnLevel;

    public void switchToGame(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/Game/game.fxml"));
            Stage stage = (Stage)this.startGame.getScene().getWindow();
            GameController controller = new GameController(null);
            loader.setController(controller);
            Scene scene = new Scene(loader.load());
            stage.setScene(scene);
        } catch (IOException var6) {
            var6.printStackTrace();
        }

    }

    public void switchToLevelCreator(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/OwnLevelCreator/LevelCreator.fxml"));
            Stage stage = (Stage)this.openLevelCreator.getScene().getWindow();
            Scene scene = new Scene(loader.load());
            scene.getStylesheets().add(this.getClass().getResource("/resources/css/styles.css").toExternalForm());
            stage.setScene(scene);
        } catch (IOException var5) {
            var5.printStackTrace();
        }

    }

    public void switchToOwnLevel(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/ownLevels/LevelSelector.fxml"));
            Stage stage = (Stage)this.startOwnLevel.getScene().getWindow();
            Scene scene = new Scene(loader.load());
            scene.getStylesheets().add(this.getClass().getResource("/resources/css/styles.css").toExternalForm());
            stage.setScene(scene);
        } catch (IOException var5) {
            var5.printStackTrace();
        }

    }

}

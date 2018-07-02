
package Game;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class Main extends Application {



    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("/Mainmenu/MainMenu.fxml"));
        primaryStage.setTitle("Jump and Run");
        primaryStage.resizableProperty().setValue(false);
        Scene scene = new Scene(root, 785, 575);
        primaryStage.getIcons().add(new Image(getClass().getResource("/resources/charstandingstill1.gif").toExternalForm()));
        scene.getStylesheets().add(getClass().getResource("/resources/css/styles.css").toExternalForm());
        primaryStage.setScene(scene);
//        primaryStage.setMaximized(true);
        primaryStage.show();

    }


    public static void main(String[] args) {
        launch(args);
    }
}

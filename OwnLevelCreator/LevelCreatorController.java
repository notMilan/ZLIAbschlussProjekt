package OwnLevelCreator;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import ownLevels.LevelSelectorController;

import java.io.*;
import java.net.URL;
import java.security.CodeSource;
import java.util.ArrayList;
import java.util.Objects;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class LevelCreatorController {


    @FXML
    public Label errormessage;

    @FXML
    private AnchorPane anchorpane;

    @FXML
    private GridPane itemsGridpane;

    @FXML
    private GridPane layoutGridpane;

    @FXML
    private TextField levelName;

    private Image cursorImage;

    private Image delimage;
    private Image charStart;
    private Image slimelvl0;
    private Image slimelvl1;
    private Image box;
    private Image portal;

    private ArrayList<LevelItem> allItems;

    private LevelItem chosenItem;

    private int[][] generatedField;

    public LevelCreatorController() {
        delimage = new Image(getClass().getResource("/resources/ImageReplacement.png").toExternalForm());
        charStart = new Image(getClass().getResource("/resources/charstandingstill1.gif").toExternalForm());
        slimelvl0 = new Image(getClass().getResource("/resources/SlimeLvl0/slimeright.gif").toExternalForm());
        slimelvl1 = new Image(getClass().getResource("/resources/SlimeLvl1/slimeright.gif").toExternalForm());
        box = new Image(getClass().getResource("/resources/box.png").toExternalForm());
        portal = new Image(getClass().getResource("/resources/portalV2.gif").toExternalForm());

        generatedField = new int[][] {
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
        };
        allItems = new ArrayList<>();

        allItems.add(new LevelItem("Box", box, 112, 56, 56, 0));


        allItems.add(new LevelItem("SlimeLvl0", slimelvl0, 5, 34, 50, 0));
        allItems.add(new LevelItem("SlimeLvl1", slimelvl1, 5, 34, 50, 0));

        allItems.add(new LevelItem("Portal", portal, 1, 56, 56, 0));

        allItems.add(new LevelItem("Starting Position", charStart, 1, 56, 48, 0));

    }

    //Generiert alles für das Level erstellen
    public void initialize() {
        addItems();
        addBackToMenu();
        addImageViewToLayout();
        generateField();
    }

    //Generiert das Level
    public void generateField() {
        int fieldnbr = 1;
        for (int i = 0; i < layoutGridpane.getRowCount(); i++) {
            for (int j = 0; j < layoutGridpane.getColumnCount(); j++) {
                if (layoutGridpane.getChildren().get(fieldnbr) instanceof ImageView) {
                    if (((ImageView) layoutGridpane.getChildren().get(fieldnbr)).getImage() == delimage) {
                        generatedField[i][j] = 0;
                    } else if (((ImageView) layoutGridpane.getChildren().get(fieldnbr)).getImage() == box) {
                        generatedField[i][j] = 1;
                    } else if (((ImageView) layoutGridpane.getChildren().get(fieldnbr)).getImage() == slimelvl0) {
                        generatedField[i][j] = 2;
                    } else if (((ImageView) layoutGridpane.getChildren().get(fieldnbr)).getImage() == slimelvl1) {
                        generatedField[i][j] = 3;
                    } else if (((ImageView) layoutGridpane.getChildren().get(fieldnbr)).getImage() == portal) {
                        generatedField[i][j] = 4;
                    } else if (((ImageView) layoutGridpane.getChildren().get(fieldnbr)).getImage() == charStart) {
                        generatedField[i][j] = 5;
                    }
                }
                fieldnbr++;
            }
        }
    }

    //ESC Button befürdert einen zurück zum Menu
    public void addBackToMenu() {
        anchorpane.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ESCAPE) {
                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/Mainmenu/MainMenu.fxml"));
                    Stage stage = (Stage) anchorpane.getScene().getWindow();
                    Scene newScene = new Scene(loader.load());
                    newScene.getStylesheets().add(getClass().getResource("/resources/css/styles.css").toExternalForm());
                    stage.setScene(newScene);
                }catch (Exception ex){
                    ex.printStackTrace();
                }
            }
        });
    }

    //Fügt alle Items die Ich habe hinzu
    public void addItems() {
        int columnindex = 0;
        ImageView item;
        for (LevelItem levelItem : allItems) {

            item = new ImageView(levelItem.image);
            item.setFitHeight(levelItem.height);
            item.setFitWidth(levelItem.width);

            itemsGridpane.setPrefWidth(itemsGridpane.getPrefWidth() + 56);
            itemsGridpane.getColumnConstraints().add(new ColumnConstraints(56));
            itemsGridpane.add(item, columnindex, 0);

            addSelection(item, levelItem);

            columnindex++;
        }
    }

    //Wenn ImageView angeclickt wird dann wird das Item ausgewählt und der Cursor verändert
    public void addSelection(ImageView item, LevelItem levelItem) {
        item.setOnMouseClicked(event -> {
            if (levelItem.limit > 0) {
                item.getScene().setCursor(Cursor.HAND);
                this.cursorImage = levelItem.image;
                this.chosenItem = levelItem;
            }
        });

    }

    //Added alle ImageViews zum Layout Gridpane
    public void addImageViewToLayout() {
        ImageView imageView;
        for (int i = 0; i < layoutGridpane.getRowCount(); i++) {
            for (int j = 0; j < layoutGridpane.getColumnCount(); j++) {
                imageView = new ImageView(delimage);
                imageView.setOpacity(0);
                imageView.setFitWidth(56);
                imageView.setFitHeight(56);
                layoutGridpane.add(imageView, j, i);
                for (Node node : layoutGridpane.getChildren()) {
                    if (node instanceof ImageView) {
                        addImageToImageView((ImageView) node);
                    }
                }
            }
        }
    }

    //Wenn ein Item ausgewählt ist added man dessen Bild auf Click zum ImageView
    public void addImageToImageView(ImageView imageView) {
        imageView.setOnMouseClicked(event -> {
            if (event.getButton() == MouseButton.PRIMARY) {
                if (chosenItem.usedCount < chosenItem.limit) {
                    itemUsageLimit(1, imageView);
                    itemUsageLimit(2, imageView);
                    imageView.setImage(chosenItem.image);
                    imageView.setFitHeight(chosenItem.height);
                    imageView.setFitWidth(chosenItem.width);
                    imageView.setOpacity(1);
                }
                //Wenn das ItemLimit erreicht ist dann setzt es die Opacity des Items auf 0.5
                if (chosenItem.usedCount >= chosenItem.limit) {
                    for (Node node : itemsGridpane.getChildren()) {
                        if (node instanceof ImageView && ((ImageView) node).getImage() == chosenItem.image) {
                            node.setOpacity(0.5);
                        }
                    }
                }
            }
            //Mit Rechtklick kann man ein Bild entfernen
            else if (event.getButton() == MouseButton.SECONDARY) {
                itemUsageLimit(2, imageView);
                imageView.setImage(delimage);
                imageView.setOpacity(0);
            }
            generateField();
        });
    }

    //Überprüft die Anzahl von Items die übrig bleiben
    public void itemUsageLimit(int option, ImageView imageView) {
        switch (option) {
            case 1:
                //Solange das Feld auf das man drückt nicht das gleiche Image enthält ersetzt es dies und zählt den Counter hoch
                if (imageView.getImage() != chosenItem.image && chosenItem.usedCount < chosenItem.limit) {
                    chosenItem.usedCount++;
                }
                break;
            case 2:
                //wenn das Image gleicht ist wie das gewählte Item und man dies löscht dann setzt es dessen usedCOunt eines zurück
                for (LevelItem item : allItems) {
                    if (item.image == imageView.getImage()) {
                        item.usedCount--;
                        for (Node node : itemsGridpane.getChildren()) {
                            if (node instanceof ImageView && ((ImageView) node).getImage() == item.image) {
                                //Wenn das Item wieder genügend vorhanden ist dann setzt es seine Opacity auf 1
                                node.setOpacity(1);
                            }
                        }
                    }
                }
                break;
            default:
                break;
        }

    }

    //Schreibt die Daten des erstellten Levels in ein Textfile
    public void writeInFile() {
        try {
            PrintStream dir = null;
            CodeSource src = LevelSelectorController.class.getProtectionDomain().getCodeSource();
            if (src != null) {
                URL jar = src.getLocation();
                ZipInputStream zip = new ZipInputStream(jar.openStream());

                //Wenn es im Jar ausgeführt wird
                if (zip.getNextEntry() != null) {
                    while (true) {
                        ZipEntry e = zip.getNextEntry();
                        if (e == null) {
                            break;
                        }
                        //erstellt ein neues TextFile
                        File source = new File(LevelSelectorController.class.getProtectionDomain().getCodeSource().getLocation().toURI());
                        dir = new PrintStream(new FileOutputStream(source.getParentFile() + "/allLevel/" + levelName.getText() + ".txt"));
                    }
                }
                //wenn man es im Editor ausführt
                else {
                    //erstellt ein neues TextFile
                    dir = new PrintStream(new FileOutputStream("out/artifacts/Produkt_jar/allLevel/" + levelName.getText() + ".txt"));
                }
                //Iteriert durch das generierte Level und schreibt dies in ein Textfile
                for (int i = 0; i < generatedField.length; i++) {
                    for (int j = 0; j < generatedField[i].length; j++) {
                        Objects.requireNonNull(dir).print(generatedField[i][j]);
                        dir.print("\n");
                    }
                }
                Objects.requireNonNull(dir).close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    //Tested ob das Generierte Level eine Starting Position und ein Portal hat
    public boolean testHasPortalAndChar() {
        boolean hasPortal = false;
        boolean hasChar = false;
        for (int i = 0; i < generatedField.length; i++) {
            for (int j = 0; j < generatedField[i].length; j++) {
                switch (generatedField[i][j]) {
                    case 4:
                        hasPortal = true;
                        break;
                    case 5:
                        hasChar = true;
                        break;
                    default:
                        break;
                }
            }
        }
        return hasChar && hasPortal;
    }

    public void backAndSave() {
        //wenn das Generierte Level eine Starting Position, ein Portal und einen Namen der kleiner als 10 Zeichen ist
        if (!levelName.getText().isEmpty() && levelName.getText().length() < 10 && testHasPortalAndChar()) {
            //erstellt neues File schreibt rein und switched zum Menu
            writeInFile();
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/Mainmenu/MainMenu.fxml"));
                Stage stage = (Stage) anchorpane.getScene().getWindow();
                Scene scene = new Scene(loader.load());
                stage.setScene(scene);
                scene.getStylesheets().add(getClass().getResource("/resources/css/styles.css").toExternalForm());
            } catch (IOException io) {
                io.printStackTrace();
            }
        }
        //Gibt eine ErrorMessage aus
        else {
            if (levelName.getText().length() > 10) {
                errormessage.setText("Levelname too long!");
            }
            if (levelName.getText().isEmpty()) {
                errormessage.setText("Please enter a Name!");
            }
            if (!testHasPortalAndChar()) {
                errormessage.setText("Set Char and Portal!");
            }
        }
    }
}

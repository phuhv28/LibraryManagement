package librarymanagement.gui.controllers;


import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import java.io.IOException;



public class SearchController {

    @FXML
    private AnchorPane SearchScene;
    @FXML
    private Button btSearchDocument;
    @FXML
    private TextField tfInformation;
    @FXML
    private MenuButton mbFilter;
    @FXML
    private MenuButton mbSearch;
    @FXML
    private MenuItem mbSearchISBN;
    @FXML
    private MenuItem mbSearchAuthor;
    @FXML
    private MenuItem mbSearchCategory;
    @FXML
    private MenuItem mbSearchName;
    @FXML
    private Label lbError;

    @FXML
    public void initialize() {
    }

    private void loadScene(String fxmlFile) {
        try {
            AnchorPane newPane = FXMLLoader.load(getClass().getResource("/FXML/" + fxmlFile));
            SearchScene.getChildren().setAll(newPane);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadError() {
        lbError.setVisible(true);
    }
}


package librarymanagement.gui.controllers;


import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;


public class AddController {

    @FXML
    private AnchorPane AddScene;
    @FXML
    private Button btAddDocument;
    @FXML
    private TextField tfDocumentID;
    @FXML
    private TextField tfTitle;
    @FXML
    private TextField tfAuthor;
    @FXML
    private TextField tfPublisher;
    @FXML
    private TextField tfPublicationDate;
    @FXML
    private TextField tfCategory;
    @FXML
    private TextField tfAvailableQuantity;
    @FXML
    private TextField tfISBN;
    @FXML
    private TextField tfTotalQuantity;
    @FXML
    private TextField tfRatingCount;
    @FXML
    private TextField tfAverageRating;
    @FXML
    private TextField tfDescription;
    @FXML
    private Label lbError;

    @FXML
    public void initialize() {
    }

    private void loadScene(String fxmlFile) {
        try {
            AnchorPane newPane = FXMLLoader.load(getClass().getResource("/FXML/" + fxmlFile));
            AddScene.getChildren().setAll(newPane);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadError() {
        lbError.setVisible(true);
    }

}


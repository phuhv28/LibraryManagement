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
    private TextField tfISBN;
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
    private TextField tfAvailableCopies;
    @FXML
    private TextField tfDescription;
    @FXML
    private Label lbError;
    @FXML
    private TextField tfPageCount;

    @FXML
    public void initialize() {
        btAddDocument.setOnAction(e -> {handleAddDocument();});
    }

    private void loadScene(String fxmlFile) {
        try {
            AnchorPane newPane = FXMLLoader.load(getClass().getResource("/FXML/" + fxmlFile));
            AddScene.getChildren().setAll(newPane);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void handleAddDocument() {
        tfISBN.requestFocus();
    }

    private void loadError() {
        lbError.setVisible(true);
    }

    private void Clear() {
        tfISBN.clear();
        tfTitle.clear();
        tfAuthor.clear();
        tfPublisher.clear();
        tfPublicationDate.clear();
        tfCategory.clear();
        tfAvailableCopies.clear();
        tfDescription.clear();
        tfPageCount.clear();
    }
}


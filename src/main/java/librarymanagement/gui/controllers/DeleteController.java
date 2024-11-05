package librarymanagement.gui.controllers;


import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;


public class DeleteController {

    @FXML
    private AnchorPane DeleteScene;
    @FXML
    private Button btDeleteDocument;
    @FXML
    private TextField tfDocumentID;
    @FXML
    private Label lbError;


    @FXML
    public void initialize() {
    }

    private void loadScene(String fxmlFile) {
        try {
            AnchorPane newPane = FXMLLoader.load(getClass().getResource("/FXML/" + fxmlFile));
            DeleteScene.getChildren().setAll(newPane);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadError() {
        lbError.setVisible(true);
    }
}


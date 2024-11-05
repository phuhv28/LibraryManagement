package librarymanagement.gui.controllers;


import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;


public class BorrowBookController {

    @FXML
    private AnchorPane BorrowBookScene;
    @FXML
    private Button btBorrowDocument;
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
            BorrowBookScene.getChildren().setAll(newPane);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadError() {
        lbError.setVisible(true);
    }

}


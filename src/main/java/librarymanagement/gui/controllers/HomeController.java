package librarymanagement.gui.controllers;


import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import javafx.scene.control.Button;

import java.io.IOException;

public class HomeController {
    @FXML
    private AnchorPane HomeScene;
    @FXML
    private Button btSetting;
    @FXML
    private Button btUser;
    @FXML
    private Button btHome;

    @FXML
    public void initialize() {
    }

    private void loadScene(String fxmlFile) {
        try {
            AnchorPane newPane = FXMLLoader.load(getClass().getResource("/FXML/" + fxmlFile));
            HomeScene.getChildren().setAll(newPane);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

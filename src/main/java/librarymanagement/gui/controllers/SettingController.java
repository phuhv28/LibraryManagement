package librarymanagement.gui.controllers;


import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;


public class SettingController {
    @FXML
    private AnchorPane SettingScene;

    @FXML
    public void initialize() {
    }

    private void loadScene(String fxmlFile)  {
        try{
            AnchorPane newPane = FXMLLoader.load(getClass().getResource("/FXML/" + fxmlFile));
            SettingScene.getChildren().setAll(newPane);
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}

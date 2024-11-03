package librarymanagement.gui.controllers;


import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import javafx.scene.control.Button;

import java.io.IOException;


public class SettingController {
    @FXML
    private AnchorPane SettingScene;
    @FXML
    private Button btHome;
    @FXML
    private Button btUser;
    @FXML
    private Button btSetting;

    @FXML
    public void initialize() {
        btUser.setOnAction(event -> {loadUser();});
        btHome.setOnAction(event -> {loadHome();});
        btSetting.setOnAction(event -> {loadSetting();});
    }

    private void loadScene(String fxmlFile)  {
        try{
            AnchorPane newPane = FXMLLoader.load(getClass().getResource("/FXML/" + fxmlFile));
            SettingScene.getChildren().setAll(newPane);
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    private void loadHome(){
        previousSceneToReturn.previousFxmlFIle1=previousSceneToReturn.previousFxmlFile2;
        loadScene("HomeScene.fxml");
        previousSceneToReturn.previousFxmlFile2="HomeScene.fxml";}
    private void loadSetting(){
        previousSceneToReturn.previousFxmlFIle1=previousSceneToReturn.previousFxmlFile2;
        loadScene("SettingScene.fxml");
        previousSceneToReturn.previousFxmlFile2="SettingScene.fxml";}
    private void loadUser(){
        previousSceneToReturn.previousFxmlFIle1=previousSceneToReturn.previousFxmlFile2;
        loadScene("UserScene.fxml");
        previousSceneToReturn.previousFxmlFile2="UserScene.fxml";}

}

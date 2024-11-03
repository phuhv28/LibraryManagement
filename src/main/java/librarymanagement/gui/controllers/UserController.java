package librarymanagement.gui.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.io.IOException;


import java.awt.*;
import java.io.IOException;

public class UserController {
    @FXML
    private AnchorPane UserScene;
    @FXML
    private Button btSetting;
    @FXML
    private Button btHome;
    @FXML
    private Button btUser;
    @FXML
    private Label lbFullName;
    @FXML
    private Label lbNumberOfBooksBorrowed;
    @FXML
    private Label lbUserName;
    @FXML
    private Label lbEmail;

    @FXML
    public void initialize() {
        lbFullName.setText("Full Name");
        lbNumberOfBooksBorrowed.setText("Number of Books Borrowed");
        lbUserName.setText("User Name");
        lbEmail.setText("Email");
        btHome.setOnAction(event -> {loadSceneHome();});
        btSetting.setOnAction(event -> {loadSceneSetting();});
        btUser.setOnAction(event -> {loadSceneUser();});
    }

    private void loadScene(String fxmlFile)  {
        try{
            AnchorPane newPane = FXMLLoader.load(getClass().getResource("/FXML/" + fxmlFile));
            UserScene.getChildren().setAll(newPane);
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    private void loadSceneHome(){
        previousSceneToReturn.previousFxmlFIle1=previousSceneToReturn.previousFxmlFile2;
        loadScene("HomeScene.fxml");
        previousSceneToReturn.previousFxmlFile2="HomeScene.fxml";}
    private void loadSceneSetting(){
        previousSceneToReturn.previousFxmlFIle1=previousSceneToReturn.previousFxmlFile2;
        loadScene("SettingScene.fxml");
        previousSceneToReturn.previousFxmlFile2="SettingScene.fxml";}
    private void loadSceneUser(){
        previousSceneToReturn.previousFxmlFIle1=previousSceneToReturn.previousFxmlFile2;
        loadScene("UserScene.fxml");
        previousSceneToReturn.previousFxmlFile2="UserScene.fxml";}

}

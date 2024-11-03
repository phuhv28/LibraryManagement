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
        btHome.setOnAction(event -> loadHome());
        btSetting.setOnAction(event -> loadSetting());
        btUser.setOnAction(event -> loadUser());
    }

    private void loadScene(String fxmlFile)  {
        try{
            AnchorPane newPane = FXMLLoader.load(getClass().getResource("/FXML/" + fxmlFile));
            HomeScene.getChildren().setAll(newPane);
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

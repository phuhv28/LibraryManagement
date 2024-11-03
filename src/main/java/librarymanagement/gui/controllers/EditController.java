package librarymanagement.gui.controllers;


import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import java.io.IOException;



public class EditController {

    @FXML
    private AnchorPane EditScene;
    @FXML
    private Button btHome;
    @FXML
    private Button btSetting;
    @FXML
    private Button btUser;
    @FXML
    private Button btEditDocument;
    @FXML
    private MenuButton mbFilter;

    @FXML
    private MenuItem mbEditTitle;

    @FXML
    private MenuItem mbEditAuthor;

    @FXML
    private MenuItem mbEditPublisher;

    @FXML
    private MenuItem mbEditPublicationYear;

    @FXML
    private MenuItem mbEditTotalQuantity;

    @FXML
    private TextField tfDocumentID;

    @FXML
    private TextField tfInformation;



    @FXML
    public void initialize() {
        btHome.setOnAction(event -> loadHome());
        btSetting.setOnAction(event -> loadSetting());
        btUser.setOnAction(event -> loadUser());
        clear();
    }




    private void loadScene(String fxmlFile) {
        try {
            AnchorPane newPane = FXMLLoader.load(getClass().getResource("/FXML/" + fxmlFile));
            EditScene.getChildren().setAll(newPane);

        } catch (IOException e) {
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
    private void clear(){
        tfDocumentID.clear();
        tfInformation.clear();
        mbFilter.setText("Filter");
    }


}


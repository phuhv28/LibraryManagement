package librarymanagement.gui.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class MainSceneController {


    @FXML
    private AnchorPane MainScene;
    @FXML
    private AnchorPane rightMainScene;
    @FXML
    private Button btSearch;
    @FXML
    private Button btBorrow;
    @FXML
    private Button btReturn;
    @FXML
    private Button btFix;
    @FXML
    private Button btDelete;
    @FXML
    private Button btAdd;
    @FXML
    private TextField tfSearch;
    @FXML
    private Button btLogOut;
    @FXML
    private Button btAddUser;
    @FXML
    private Button btHome;
    @FXML
    private Button btSetting;
    @FXML
    private Button btUser;

    private String Words_find;


    @FXML
    public void initialize() {
        btSearch.setOnAction(event -> loadSceneSearch());
        btBorrow.setOnAction(event -> loadSceneBorrowBook());
        btReturn.setOnAction(event -> loadSceneReturn());
        btFix.setOnAction(event -> loadSceneFix());
        btDelete.setOnAction(event -> loadSceneDelete());
        btAdd.setOnAction(event -> loadSceneAdd());
        tfSearch.setOnAction(event -> searchInformation());
        btLogOut.setOnAction(actionEvent -> loadSceneLoginAndSignUp());
        btAddUser.setOnAction(actionEvent -> loadSceneAddAccount());
        btHome.setOnAction(event -> loadHome());
        btSetting.setOnAction(event -> loadSetting());
        btUser.setOnAction(event -> loadUser());
    }

    private void loadScene(String fxmlFile) {
        try {
            AnchorPane newPane = FXMLLoader.load(getClass().getResource("/FXML/" + fxmlFile));
            rightMainScene.getChildren().setAll(newPane);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadSceneSearch() {
        previousSceneToReturn.previousFxmlFIle1 = previousSceneToReturn.previousFxmlFile2;
        loadScene("SearchScene.fxml");
        previousSceneToReturn.previousFxmlFile2 = "SearchScene.fxml";
    }

    private void loadSceneAdd() {
        previousSceneToReturn.previousFxmlFIle1 = previousSceneToReturn.previousFxmlFile2;
        loadScene("AddScene.fxml");
        previousSceneToReturn.previousFxmlFile2 = "AddScene.fxml";
    }

    private void loadSceneDelete() {
        previousSceneToReturn.previousFxmlFIle1 = previousSceneToReturn.previousFxmlFile2;
        loadScene("DeleteScene.fxml");
        previousSceneToReturn.previousFxmlFile2 = "DeleteScene.fxml";
    }

    private void loadSceneFix() {
        previousSceneToReturn.previousFxmlFIle1 = previousSceneToReturn.previousFxmlFile2;
        loadScene("EditScene.fxml");
        previousSceneToReturn.previousFxmlFile2 = "EditScene.fxml";
    }

    private void loadSceneBorrowBook() {
        previousSceneToReturn.previousFxmlFIle1 = previousSceneToReturn.previousFxmlFile2;
        loadScene("ListBorrowBookScene.fxml");
        previousSceneToReturn.previousFxmlFile2 = "ListBorrowBookScene.fxml";
    }

    private void loadSceneReturn() {
        try {
            AnchorPane newPane = FXMLLoader.load(getClass().getResource("/FXML/" + previousSceneToReturn.previousFxmlFIle1));
            rightMainScene.getChildren().setAll(newPane);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadSceneLoginAndSignUp() {
        try {
            AnchorPane newPane = FXMLLoader.load(getClass().getResource("/FXML/StartScreen.fxml"));
            MainScene.setPrefWidth(800);
            MainScene.setPrefHeight(600);
            MainScene.getChildren().setAll(newPane);


        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadSceneAddAccount() {
        previousSceneToReturn.previousFxmlFIle1 = previousSceneToReturn.previousFxmlFile2;
        loadScene("AddUserScene.fxml");
        previousSceneToReturn.previousFxmlFile2 = "AddUserScene.fxml";
    }

    private void loadHome() {
        previousSceneToReturn.previousFxmlFIle1 = previousSceneToReturn.previousFxmlFile2;
        loadScene("HomeScene.fxml");
        previousSceneToReturn.previousFxmlFile2 = "HomeScene.fxml";
    }

    private void loadSetting() {
        previousSceneToReturn.previousFxmlFIle1 = previousSceneToReturn.previousFxmlFile2;
        loadScene("SettingScene.fxml");
        previousSceneToReturn.previousFxmlFile2 = "SettingScene.fxml";
    }

    private void loadUser() {
        previousSceneToReturn.previousFxmlFIle1 = previousSceneToReturn.previousFxmlFile2;
        loadScene("UserScene.fxml");
        previousSceneToReturn.previousFxmlFile2 = "UserScene.fxml";
    }

    private void searchInformation() {
        Words_find = tfSearch.getText();
        tfSearch.clear();
    }

}

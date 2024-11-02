package librarymanagement.gui.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;

import java.io.IOException;

public class MainSceneController {

    @FXML
    public HBox hBoxMain;
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

    public String Words_find;

    private void loadTextField(){
        Words_find = tfSearch.getText();
    }

    @FXML
    public void initialize() {
        btSearch.setOnAction(event -> loadSceneSearch());
        btBorrow.setOnAction(event -> loadSceneBorrowBook());
        btReturn.setOnAction(event -> loadSceneHome());
        btFix.setOnAction(event -> loadSceneFix());
        btDelete.setOnAction(event -> loadSceneDelete());
        btAdd.setOnAction(event -> loadSceneAdd());
        tfSearch.setOnAction(event -> loadTextField());
    }

    private void loadScene(String fxmlFile) {
        try {
            AnchorPane newPane = FXMLLoader.load(getClass().getResource("/FXML/" + fxmlFile));
            rightMainScene.getChildren().setAll(newPane);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadSceneSearch(){
        loadScene("SearchScene.fxml");
    }

    private void loadSceneAdd(){
        loadScene("AddScene.fxml");
    }
    private void loadSceneDelete(){
        loadScene("DeleteScene.fxml");
    }
    private void loadSceneFix(){
        loadScene("FixScene.fxml");
    }
    private void loadSceneUser(){
        loadScene("UserScene.fxml");
    }
    private void loadSceneSetting(){
        loadScene("SettingScene.fxml");
    }
    private void loadSceneBorrowBook(){
        loadScene("BorrowBookScene.fxml");
    }
    private void loadSceneHome(){
        loadScene("HomeScene.fxml");
    }

}

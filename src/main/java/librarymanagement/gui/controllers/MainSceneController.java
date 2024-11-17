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
    private Button btMyDocument;

    @FXML
    private Button btReturn;

    @FXML
    private Button btFix;

    @FXML
    private Button btDelete;

    @FXML
    private Button btAdd;

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

    @FXML
    public void initialize() {
        btSearch.setOnAction(event -> loadSceneSearch());
        btMyDocument.setOnAction(event -> loadSceneMyDocument());
        btReturn.setOnAction(event -> loadSceneReturn());
        btFix.setOnAction(event -> loadSceneFix());
        btDelete.setOnAction(event -> loadSceneDelete());
        btAdd.setOnAction(event -> loadSceneAdd());
        btLogOut.setOnAction(actionEvent -> handleLogOut());
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
        previousSceneToReturn.listPreviousFxmlFile.push(previousSceneToReturn.previousFxmlFile);
        loadScene("SearchDocument.fxml");
        previousSceneToReturn.previousFxmlFile = "SearchDocument.fxml";
    }

    private void loadSceneAdd() {
        previousSceneToReturn.listPreviousFxmlFile.push(previousSceneToReturn.previousFxmlFile);
        loadScene("AddDocument.fxml");
        previousSceneToReturn.previousFxmlFile = "AddDocument.fxml";
    }

    private void loadSceneDelete() {
        previousSceneToReturn.listPreviousFxmlFile.push(previousSceneToReturn.previousFxmlFile);
        loadScene("DeleteDocument.fxml");
        previousSceneToReturn.previousFxmlFile = "DeleteDocument.fxml";
    }

    private void loadSceneFix() {
        previousSceneToReturn.listPreviousFxmlFile.push(previousSceneToReturn.previousFxmlFile);
        loadScene("EditDocument.fxml");
        previousSceneToReturn.previousFxmlFile = "EditDocument.fxml";
    }

    private void loadSceneMyDocument() {
        previousSceneToReturn.listPreviousFxmlFile.push(previousSceneToReturn.previousFxmlFile);
        loadScene("MyDocument.fxml");
        previousSceneToReturn.previousFxmlFile = "MyDocument.fxml";
    }

    private void loadSceneReturn() {
        try {
            String fxmlFile;
            if(previousSceneToReturn.listPreviousFxmlFile.isEmpty()) {
                  fxmlFile = "HomeScene.fxml";
            } else {
                fxmlFile = previousSceneToReturn.listPreviousFxmlFile.pop();
            }

            AnchorPane newPane = FXMLLoader.load(getClass().getResource("/FXML/" + fxmlFile));
            rightMainScene.getChildren().setAll(newPane);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void handleLogOut() {
        UIController.showScene(StartScreenController.getStartScreen());
    }

    private void loadSceneAddAccount() {
        previousSceneToReturn.listPreviousFxmlFile.push(previousSceneToReturn.previousFxmlFile);
        loadScene("AddUserScene.fxml");
        previousSceneToReturn.previousFxmlFile = "AddUserScene.fxml";
    }

    private void loadHome() {
        previousSceneToReturn.listPreviousFxmlFile.push(previousSceneToReturn.previousFxmlFile);
        loadScene("HomeScene.fxml");
        previousSceneToReturn.previousFxmlFile = "HomeScene.fxml";
    }

    private void loadSetting() {
        previousSceneToReturn.listPreviousFxmlFile.push(previousSceneToReturn.previousFxmlFile);
        loadScene("SettingScene.fxml");
        previousSceneToReturn.previousFxmlFile = "SettingScene.fxml";
    }

    private void loadUser() {
        previousSceneToReturn.listPreviousFxmlFile.push(previousSceneToReturn.previousFxmlFile);
        loadScene("UserInfo.fxml");
        previousSceneToReturn.previousFxmlFile = "UserInfo.fxml";
    }

}

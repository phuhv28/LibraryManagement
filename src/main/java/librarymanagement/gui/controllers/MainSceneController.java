package librarymanagement.gui.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import librarymanagement.UserAuth.AccountService;
import librarymanagement.UserAuth.AccountType;
import librarymanagement.gui.utils.SceneHistoryStack;

import java.io.IOException;

public class MainSceneController {

    @FXML
    private AnchorPane rightMainScene;

    @FXML
    private Button btSearch;

    @FXML
    private Button btMyDocument;

    @FXML
    private Button btReturn;

    @FXML
    private Button btEditDocument;

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
        btEditDocument.setOnAction(event -> loadSceneFix());
        btDelete.setOnAction(event -> loadSceneDelete());
        btAdd.setOnAction(event -> loadSceneAdd());
        btLogOut.setOnAction(actionEvent -> handleLogOut());
        btAddUser.setOnAction(actionEvent -> loadSceneAddAccount());
        btHome.setOnAction(event -> loadHome());
        btSetting.setOnAction(event -> loadSetting());
        btUser.setOnAction(event -> loadUser());

        loadHome();

        if (AccountService.getInstance().getCurrentAccount().getAccountType() != AccountType.ADMIN) {
            btAddUser.setVisible(false);
            btDelete.setVisible(false);
            btAdd.setVisible(false);
            btEditDocument.setVisible(false);

        }
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
        SceneHistoryStack.listPreviousFxmlFile.push(SceneHistoryStack.previousFxmlFile);
        loadScene("SearchDocument.fxml");
        SceneHistoryStack.previousFxmlFile = "SearchDocument.fxml";
    }

    private void loadSceneAdd() {
        SceneHistoryStack.listPreviousFxmlFile.push(SceneHistoryStack.previousFxmlFile);
        loadScene("AddDocument.fxml");
        SceneHistoryStack.previousFxmlFile = "AddDocument.fxml";
    }

    private void loadSceneDelete() {
        SceneHistoryStack.listPreviousFxmlFile.push(SceneHistoryStack.previousFxmlFile);
        loadScene("DeleteDocument.fxml");
        SceneHistoryStack.previousFxmlFile = "DeleteDocument.fxml";
    }

    private void loadSceneFix() {
        SceneHistoryStack.listPreviousFxmlFile.push(SceneHistoryStack.previousFxmlFile);
        loadScene("EditDocument.fxml");
        SceneHistoryStack.previousFxmlFile = "EditDocument.fxml";
    }

    private void loadSceneMyDocument() {
        SceneHistoryStack.listPreviousFxmlFile.push(SceneHistoryStack.previousFxmlFile);
        loadScene("MyDocument.fxml");
        SceneHistoryStack.previousFxmlFile = "MyDocument.fxml";
    }

    private void loadSceneReturn() {
        try {
            String fxmlFile;
            if(SceneHistoryStack.listPreviousFxmlFile.isEmpty()) {
                  fxmlFile = "Home.fxml";
            } else {
                fxmlFile = SceneHistoryStack.listPreviousFxmlFile.pop();
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
        SceneHistoryStack.listPreviousFxmlFile.push(SceneHistoryStack.previousFxmlFile);
        loadScene("AddUser.fxml");
        SceneHistoryStack.previousFxmlFile = "AddUser.fxml";
    }

    private void loadHome() {
        SceneHistoryStack.listPreviousFxmlFile.push(SceneHistoryStack.previousFxmlFile);
        loadScene("Home.fxml");
        SceneHistoryStack.previousFxmlFile = "Home.fxml";
    }

    private void loadSetting() {
        SceneHistoryStack.listPreviousFxmlFile.push(SceneHistoryStack.previousFxmlFile);
        loadScene("Setting.fxml");
        SceneHistoryStack.previousFxmlFile = "Setting.fxml";
    }

    private void loadUser() {
        SceneHistoryStack.listPreviousFxmlFile.push(SceneHistoryStack.previousFxmlFile);
        loadScene("UserInfo.fxml");
        SceneHistoryStack.previousFxmlFile = "UserInfo.fxml";
    }

}

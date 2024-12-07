package librarymanagement.gui.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import librarymanagement.entity.Admin;
import librarymanagement.gui.models.AccountService;
import librarymanagement.utils.SceneHistoryStack;

import java.io.IOException;

/**
 * Controller class for the main scene of the application.
 * This class handles user interactions with the main scene, including navigating between different views
 * and handling visibility of buttons based on user account type.
 */
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
    private Button btMangeUser;

    @FXML
    private Button btHome;

    @FXML
    private Button btInformationApp;

    @FXML
    private Button btUser;

    /**
     * Initializes the main scene by setting up button actions and handling visibility
     * of buttons based on the current account type. Also loads the home scene by default.
     */
    @FXML
    public void initialize() {
        btSearch.setOnAction(event -> loadSceneSearch());
        btMyDocument.setOnAction(event -> loadSceneMyDocument());
        btReturn.setOnAction(event -> loadSceneReturn());
        btEditDocument.setOnAction(event -> loadSceneFix());
        btDelete.setOnAction(event -> loadSceneDelete());
        btAdd.setOnAction(event -> loadSceneAdd());
        btMangeUser.setOnAction(actionEvent -> loadManageUser());
        btHome.setOnAction(event -> loadHome());
        btInformationApp.setOnAction(event -> loadInformationApp());
        btUser.setOnAction(event -> loadUser());

        loadHome();

        // Check the account type and hide buttons accordingly.
        if (!(AccountService.getInstance().getCurrentAccount() instanceof Admin)) {
            btMangeUser.setVisible(false);
            btDelete.setVisible(false);
            btAdd.setVisible(false);
            btEditDocument.setVisible(false);
        }
    }

    /**
     * Loads a new scene into the main area by replacing the current content.
     *
     * @param fxmlFile the name of the FXML file for the new scene to be loaded.
     */
    private void loadScene(String fxmlFile) {
        try {
            AnchorPane newPane = FXMLLoader.load(getClass().getResource("/FXML/" + fxmlFile));
            rightMainScene.getChildren().setAll(newPane);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadManageUser() {
        SceneHistoryStack.listPreviousFxmlFile.push(SceneHistoryStack.previousFxmlFile);
        loadScene("ManageUser.fxml");
        SceneHistoryStack.previousFxmlFile = "ManageUser.fxml";
    }

    /**
     * Loads the search scene.
     * Saves the current scene into the history stack before loading the new scene.
     */
    private void loadSceneSearch() {
        SceneHistoryStack.listPreviousFxmlFile.push(SceneHistoryStack.previousFxmlFile);
        loadScene("SearchDocument.fxml");
        SceneHistoryStack.previousFxmlFile = "SearchDocument.fxml";
    }

    /**
     * Loads the add document scene.
     * Saves the current scene into the history stack before loading the new scene.
     */
    private void loadSceneAdd() {
        SceneHistoryStack.listPreviousFxmlFile.push(SceneHistoryStack.previousFxmlFile);
        loadScene("AddDocument.fxml");
        SceneHistoryStack.previousFxmlFile = "AddDocument.fxml";
    }

    /**
     * Loads the delete document scene.
     * Saves the current scene into the history stack before loading the new scene.
     */
    private void loadSceneDelete() {
        SceneHistoryStack.listPreviousFxmlFile.push(SceneHistoryStack.previousFxmlFile);
        loadScene("DeleteDocument.fxml");
        SceneHistoryStack.previousFxmlFile = "DeleteDocument.fxml";
    }

    /**
     * Loads the edit document scene.
     * Saves the current scene into the history stack before loading the new scene.
     */
    private void loadSceneFix() {
        SceneHistoryStack.listPreviousFxmlFile.push(SceneHistoryStack.previousFxmlFile);
        loadScene("EditDocument.fxml");
        SceneHistoryStack.previousFxmlFile = "EditDocument.fxml";
    }

    /**
     * Loads the user's document scene.
     * Saves the current scene into the history stack before loading the new scene.
     */
    private void loadSceneMyDocument() {
        SceneHistoryStack.listPreviousFxmlFile.push(SceneHistoryStack.previousFxmlFile);
        loadScene("MyDocument.fxml");
        SceneHistoryStack.previousFxmlFile = "MyDocument.fxml";
    }

    /**
     * Loads the return scene based on the scene history stack.
     * If the stack is empty, it loads the home scene. Otherwise, it pops the last scene from the stack.
     */
    private void loadSceneReturn() {
        try {
            String fxmlFile;
            if (SceneHistoryStack.listPreviousFxmlFile.isEmpty()) {
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

    /**
     * Loads the home scene.
     * Saves the current scene into the history stack before loading the new scene.
     */
    private void loadHome() {
        SceneHistoryStack.listPreviousFxmlFile.push(SceneHistoryStack.previousFxmlFile);
        loadScene("Home.fxml");
        SceneHistoryStack.previousFxmlFile = "Home.fxml";
    }

    /**
     * Loads the application information scene.
     * Saves the current scene into the history stack before loading the new scene.
     */
    private void loadInformationApp() {
        SceneHistoryStack.listPreviousFxmlFile.push(SceneHistoryStack.previousFxmlFile);
        loadScene("InformationApp.fxml");
        SceneHistoryStack.previousFxmlFile = "InformationApp.fxml";
    }

    /**
     * Loads the user information scene.
     * Saves the current scene into the history stack before loading the new scene.
     */
    private void loadUser() {
        SceneHistoryStack.listPreviousFxmlFile.push(SceneHistoryStack.previousFxmlFile);
        loadScene("UserInfo.fxml");
        SceneHistoryStack.previousFxmlFile = "UserInfo.fxml";
    }
}

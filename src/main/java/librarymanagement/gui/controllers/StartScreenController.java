package librarymanagement.gui.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;

public class StartScreenController {
    private static Scene scene;
    private static StartScreenController startScreenController;

    @FXML
    private AnchorPane apLogin;

    @FXML
    private AnchorPane apRegister;

    public static Scene getStartScreen() {
        if (scene == null) {
            FXMLLoader loader = new FXMLLoader(LoginController.class.getResource("/FXML/StartScreen.fxml"));
            try {
                Parent parent = loader.load();
                scene = new Scene(parent);
                startScreenController = loader.getController();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return scene;
    }

    public static StartScreenController getStartScreenController() {
        return startScreenController;
    }

    public void showLogin() {
        apRegister.setVisible(false);
        apRegister.setManaged(false);
        apLogin.setVisible(true);
        apLogin.setManaged(true);
    }

    public void showRegister() {
        apLogin.setVisible(false);
        apLogin.setManaged(false);
        apRegister.setVisible(true);
        apRegister.setManaged(true);
    }
}

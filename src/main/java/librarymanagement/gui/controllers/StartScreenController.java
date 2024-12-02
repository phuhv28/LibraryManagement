package librarymanagement.gui.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;

/**
 * Controller class for the Start Screen of the library management application.
 * Manages the initial screen where users can choose to log in or register.
 * Provides functionality to switch between the login and register panes.
 */
public class StartScreenController {

    private static Scene scene;
    private static StartScreenController startScreenController;

    @FXML
    private AnchorPane apLogin;

    @FXML
    private AnchorPane apRegister;

    /**
     * Retrieves the start screen scene. If the scene is not initialized,
     * it loads the StartScreen.fxml layout and initializes the controller.
     *
     * @return the start screen scene.
     */
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

    /**
     * Retrieves the instance of the StartScreenController.
     *
     * @return the StartScreenController instance.
     */
    public static StartScreenController getStartScreenController() {
        return startScreenController;
    }

    /**
     * Displays the login pane and hides the register pane.
     * This method is used to switch to the login view.
     */
    public void showLogin() {
        apRegister.setVisible(false);
        apRegister.setManaged(false);
        apLogin.setVisible(true);
        apLogin.setManaged(true);
    }

    /**
     * Displays the register pane and hides the login pane.
     * This method is used to switch to the registration view.
     */
    public void showRegister() {
        apLogin.setVisible(false);
        apLogin.setManaged(false);
        apRegister.setVisible(true);
        apRegister.setManaged(true);
    }
}

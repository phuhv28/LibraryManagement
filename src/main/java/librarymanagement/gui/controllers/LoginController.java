package librarymanagement.gui.controllers;

import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import librarymanagement.gui.viewmodels.LoginViewModel;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Controller class for the login screen.
 * This class handles user interactions on the login page, including validating login credentials,
 * displaying error messages, and navigating to the main application scene.
 */
public class LoginController implements Initializable {

    @FXML
    public Label errorLabel;

    @FXML
    public AnchorPane apLogin;

    @FXML
    private PasswordField pfPassword;

    @FXML
    private TextField tfUsername;

    private final LoginViewModel viewModel = new LoginViewModel();

    /**
     * Returns an instance of the LoginController.
     * This method loads the FXML file for the login screen and returns the controller instance.
     *
     * @return the LoginController instance.
     */
    public static LoginController getInstance() {
        LoginController controller = null;
        FXMLLoader loader = new FXMLLoader(LoginController.class.getResource("/FXML/Login.fxml"));
        try {
            controller = loader.getController();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return controller;
    }

    /**
     * Initializes the login screen controller.
     * Binds the text properties of the username, password fields, and error label to the corresponding properties
     * in the view model. Also, sets up key press event listeners to handle login attempts when the ENTER key is pressed.
     *
     * @param url the location used to resolve relative paths for the root object, or null if the location is not known.
     * @param resourceBundle the resources used to localize the root object, or null if the root object does not require localization.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        tfUsername.textProperty().bindBidirectional(viewModel.usernameProperty());
        pfPassword.textProperty().bindBidirectional(viewModel.passwordProperty());
        errorLabel.textProperty().bindBidirectional(viewModel.errorLabelTextProperty());

        tfUsername.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                handleLogin();
            }
        });

        pfPassword.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                handleLogin();
            }
        });
    }

    /**
     * Handles the login process.
     * Displays a loading popup while verifying the login credentials in the background.
     * If the login is successful, the main application scene is shown; otherwise, an error message is displayed.
     */
    @FXML
    private void handleLogin() {
        LoadingPopupController loadingPopup = LoadingPopupController.newInstance("Login");
        loadingPopup.initOwnerStage(UIController.getPrimaryStage());
        loadingPopup.show();

        Task<Boolean> loginTask = new Task<>() {
            @Override
            protected Boolean call() {
                return viewModel.handleLogin();
            }
        };

        loginTask.setOnSucceeded(event -> {
            loadingPopup.close();
            if (loginTask.getValue()) {
                UIController.showMainScene();
            } else {
                errorLabel.setVisible(true);
            }
        });

        new Thread(loginTask).start();
    }

    /**
     * Handles the register action.
     * Navigates to the registration screen.
     */
    @FXML
    public void handleRegister() {
        StartScreenController.getStartScreenController().showRegister();
    }
}

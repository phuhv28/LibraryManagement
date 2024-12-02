package librarymanagement.gui.controllers;

import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import librarymanagement.gui.viewmodels.RegisterViewModel;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Controller class for the registration scene where new users can register an account.
 * This class handles user inputs, validations, and the registration process.
 */
public class RegisterController implements Initializable {
    private Scene scene;

    @FXML
    public Label errorLabel;

    @FXML
    private TextField tfUsername;

    @FXML
    private PasswordField pfPassword;

    @FXML
    private PasswordField pfConfirmPassword;

    @FXML
    private Button btRegister;

    @FXML
    private TextField tfEmail;

    @FXML
    private TextField tfFullName;

    @FXML
    private Hyperlink lkLogin;

    private final RegisterViewModel viewModel = new RegisterViewModel();

    /**
     * Singleton method to get an instance of the RegisterController.
     * It loads the FXML for the registration scene and returns the controller.
     *
     * @return the instance of RegisterController.
     */
    public static RegisterController getInstance() {
        FXMLLoader loader = new FXMLLoader(RegisterController.class.getResource("/FXML/Register.fxml"));
        try {
            Parent root = loader.load();
            RegisterController controller = loader.getController();
            Scene scene = new Scene(root);
            controller.setScene(scene);
            return controller;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private void setScene(Scene scene) {
        this.scene = scene;
    }

    /**
     * Initializes the RegisterController by binding UI fields to the view model,
     * setting up listeners for the Enter key to trigger the registration process.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        tfUsername.textProperty().bindBidirectional(viewModel.usernameProperty());
        pfPassword.textProperty().bindBidirectional(viewModel.passwordProperty());
        pfConfirmPassword.textProperty().bindBidirectional(viewModel.confirmPasswordProperty());
        tfEmail.textProperty().bindBidirectional(viewModel.emailProperty());
        tfFullName.textProperty().bindBidirectional(viewModel.fullNameProperty());
        errorLabel.textProperty().bindBidirectional(viewModel.errorLabelProperty());

        // Trigger registration on pressing Enter
        tfUsername.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                register();
            }
        });
        tfEmail.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                register();
            }
        });
        tfFullName.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                register();
            }
        });
        pfPassword.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                register();
            }
        });
        pfConfirmPassword.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                register();
            }
        });
    }

    /**
     * Navigates the user to the login scene when the login hyperlink is clicked.
     */
    @FXML
    public void goToLogin() {
        StartScreenController.getStartScreenController().showLogin();
    }

    /**
     * Handles the registration process by calling the view model's register method.
     * Shows a loading popup during registration and updates the error label if registration fails.
     */
    @FXML
    public void register() {
        LoadingPopupController loadingPopup = LoadingPopupController.newInstance("Registering");
        loadingPopup.initOwnerStage(UIController.getPrimaryStage());
        loadingPopup.show();

        Task<Boolean> registerTask = new Task<>() {
            @Override
            protected Boolean call() {
                return viewModel.handleRegister();
            }
        };

        registerTask.setOnSucceeded(event -> {
            loadingPopup.close();
            if (registerTask.getValue()) {
                goToLogin();  // If registration is successful, navigate to login
            } else {
                errorLabel.setVisible(true);  // Show error if registration fails
            }
        });

        new Thread(registerTask).start();
    }

    /**
     * Sets the registration scene on the given primary stage.
     *
     * @param primaryStage the primary stage to display the registration scene.
     */
    public void showScene(Stage primaryStage) {
        primaryStage.setScene(scene);
        primaryStage.setTitle("Register");
    }
}

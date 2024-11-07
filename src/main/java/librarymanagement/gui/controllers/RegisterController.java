package librarymanagement.gui.controllers;

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


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        tfUsername.textProperty().bindBidirectional(viewModel.usernameProperty());
        pfPassword.textProperty().bindBidirectional(viewModel.passwordProperty());
        pfConfirmPassword.textProperty().bindBidirectional(viewModel.confirmPasswordProperty());
        errorLabel.textProperty().bindBidirectional(viewModel.errorLabelProperty());

        tfUsername.setOnKeyPressed(event -> {
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
        tfEmail.textProperty().bindBidirectional(viewModel.emailPropertyProperty());
        tfFullName.textProperty().bindBidirectional(viewModel.fullNamePropertyProperty());
    }

    @FXML
    public void goToLogin() {
        StartScreenController.getStartScreenController().showLogin();
    }

    @FXML
    public void register() {
        boolean succes = viewModel.handleRegister();
        errorLabel.setVisible(true);
        if (succes) {
            goToLogin();
        }
    }

    public void showScene(Stage primaryStage) {
        primaryStage.setScene(scene);
        primaryStage.setTitle("Register");
    }
}

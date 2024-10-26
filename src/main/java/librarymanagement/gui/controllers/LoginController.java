package librarymanagement.gui.controllers;

import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import librarymanagement.gui.viewmodels.LoginViewModel;

import java.net.URL;
import java.util.ResourceBundle;

public class LoginController implements Initializable {

    @FXML
    public Label errorLabel;

    @FXML
    public CheckBox cbRemeberMe;

    @FXML
    private PasswordField pfPassword;

    @FXML
    private TextField tfUsername;

    @FXML
    private Button btLogin;

    @FXML
    private Button btRegister;

    private LoginViewModel viewModel = new LoginViewModel();

    private static Scene scene;

    private static LoginController controller;

    public static Scene getLoginScene() {
        if (scene == null) {
            FXMLLoader loader = new FXMLLoader(LoginController.class.getResource("/FXML/Login.fxml"));
            try {
                Parent parent = loader.load();
                scene = new Scene(parent);
                controller = loader.getController();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return scene;
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        tfUsername.textProperty().bindBidirectional(viewModel.usernameProperty());
        pfPassword.textProperty().bindBidirectional(viewModel.passwordProperty());
        errorLabel.textProperty().bindBidirectional(viewModel.errorLabelTextProperty());
    }

    @FXML
    private void handleLogin() {
        System.out.println("Da bam dang nhap");
        errorLabel.setVisible(true);
        viewModel.handleLogin();
    }


    @FXML
    public void handleRegister() {
        RegisterController controller = RegisterController.getInstance();
        if (controller != null) {
            controller.showScene(UIController.getPrimaryStage());
        }
    }
}
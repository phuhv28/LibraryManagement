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

    @FXML
    public void handleRegister() {
        StartScreenController.getStartScreenController().showRegister();
    }
}
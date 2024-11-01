package librarymanagement.gui.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import librarymanagement.gui.viewmodels.LoginViewModel;

import java.net.URL;
import java.util.ResourceBundle;

public class LoginController implements Initializable {

    @FXML
    public Label errorLabel;

    @FXML
    public CheckBox cbRemeberMe;

    @FXML
    public AnchorPane apLogin;

    @FXML
    private PasswordField pfPassword;

    @FXML
    private TextField tfUsername;

    @FXML
    private Button btLogin;

    @FXML
    private Button btRegister;

    private final LoginViewModel viewModel = new LoginViewModel();

    private static LoginController controller;

    public static LoginController getInstance() {
        if (controller == null) {
            FXMLLoader loader = new FXMLLoader(LoginController.class.getResource("/FXML/Login.fxml"));
            try {
                controller = loader.getController();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return controller;
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
        if (viewModel.handleLogin()) {
            UIController.showMainScene();
        }
    }

    @FXML
    public void handleRegister() {
        StartScreenController.getStartScreenController().showRegister();
    }
}
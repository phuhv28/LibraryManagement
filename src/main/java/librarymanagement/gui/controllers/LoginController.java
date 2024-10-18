package librarymanagement.gui.controllers;

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
    private Button btSignUpInLogin;

    private static LoginController loginController = null;
    private static Scene loginScene;

    private LoginViewModel viewModel = new LoginViewModel();



    private static void prepare() {
        FXMLLoader loader = new FXMLLoader(LoginController.class.getResource("/FXML/Login.fxml"));
        try {
            Parent parent = loader.load();
            loginScene = new Scene(parent);
            loginController = loader.getController();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Scene getLoginScene() {
        if (loginScene == null) {
            prepare();
        }
        return loginScene;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        tfUsername.textProperty().bindBidirectional(viewModel.usernameProperty());
        pfPassword.textProperty().bindBidirectional(viewModel.passwordProperty());
//        errorLabel.textProperty().bindBidirectional(viewModel.errorLabelTextProperty());
//
//        errorLabel.visibleProperty().bind(Bindings.createBooleanBinding(
//                () -> !(viewModel.errorLabelTextProperty().get() == null),
//                viewModel.errorLabelTextProperty()
//        ));
    }

    @FXML
    private void handleLogin() {
        viewModel.handleLogin();
    }


    @FXML
    public void handleSignUp() {
        SignUpController signUpController = SignUpController.getInstance();

        if (signUpController != null) {
            signUpController.showStage(UIController.getPrimaryStage());
        }
    }
}
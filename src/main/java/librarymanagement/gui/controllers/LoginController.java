package librarymanagement.gui.controllers;

import javafx.event.ActionEvent;
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

    private static LoginController loginController = null;
    private static Scene loginScene;

    @FXML
    public CheckBox cbRemeberMe;

    @FXML
    public Button btnLogin;

    @FXML
    private TextField tfUserName;

    @FXML
    private PasswordField tfPassWord;

    private LoginViewModel viewModel = new LoginViewModel();



    private static void prepare() {
        FXMLLoader loader = new FXMLLoader(LoginController.class.getResource("/librarymanagement/gui/controllers/Login.fxml"));
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
        tfUserName.textProperty().bindBidirectional(viewModel.userNameProperty());
        tfPassWord.textProperty().bindBidirectional(viewModel.passWordProperty());
    }

    @FXML
    private void handleLogin(ActionEvent actionEvent) {
    }


    @FXML
    public void handleSignUp() {
        SignUpController signUpController = SignUpController.getInstance();

        if (signUpController != null) {
            signUpController.showStage(UIController.getPrimaryStage());
        }
    }
}
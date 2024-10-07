package librarymanagement.gui.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class SignUpController implements Initializable {
    private static SignUpController controller = null;

    private static Stage stage;

    @FXML
    private TextField tfUserName;

    @FXML
    private PasswordField pfPassWord;

    @FXML
    private TextField tfEmail;

    @FXML
    private Button btnRegister;

    @FXML
    private Hyperlink lkLogin;

    private SignUpController() {

    }

    public static SignUpController getInstance() {
        if (controller == null) {
            FXMLLoader loader = new FXMLLoader(SignUpController.class.getResource("FXML/SignUp.fxml"));
            try {
                Parent parent = loader.load();
                Scene scene = new Scene(parent);
                stage = new Stage();
                stage.setTitle("Sign Up");
                stage.setScene(scene);
                controller = loader.getController();

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return controller;
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    @FXML
    public void goToLogin(ActionEvent actionEvent) {
    }

    @FXML
    public void register(ActionEvent actionEvent) {
    }

    public void showStage(Stage ownerStage) {
        if (stage != null) {
            stage.setResizable(false);
            stage.initModality(Modality.WINDOW_MODAL);
            stage.initOwner(ownerStage);
            stage.show();
        }
    }
}

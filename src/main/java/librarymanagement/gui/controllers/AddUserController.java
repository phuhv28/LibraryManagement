package librarymanagement.gui.controllers;


import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import librarymanagement.gui.viewmodels.AddUserViewModel;

import java.io.IOException;


public class AddUserController {

    @FXML
    private AnchorPane AddUserScene;

    @FXML
    private Button btAddAccount;

    @FXML
    private TextField tfUserName;

    @FXML
    private MenuButton mbFunction;

    @FXML
    private MenuItem mbCreat;

    @FXML
    private MenuItem mbAdd;

    @FXML
    private MenuButton mbAccount;

    @FXML
    private MenuItem mbUser;

    @FXML
    private MenuItem mbAdmin;

    @FXML
    private TextField tfEmail;

    @FXML
    private TextField tfPassword;

    @FXML
    private TextField tfConfirmPassword;

    @FXML
    private TextField tfFullName;

    @FXML
    private Label lbSelectFunction;

    @FXML
    private Label lbEnterFullName;

    @FXML
    private Label lbEnterEmail;

    @FXML
    private Label lbEnterPassword;

    @FXML
    private Label lbConfirmPassword;

    @FXML
    private Label lbEnterUserName;

    @FXML
    private Label lbErrorSignUp;

    @FXML
    private Label lbErrorAddAdmin;

    @FXML
    private Label lbErrorPassword;


    private final AddUserViewModel viewModel = new AddUserViewModel();


    @FXML
    public void initialize() {
        mbUser.setOnAction(event -> {
            loadGraphicWithUserCreat();
        });
        mbAdmin.setOnAction(actionEvent -> {
            loadGraphicAdmin();
        });
        mbCreat.setOnAction(actionEvent -> {
            loadGraphicWithAdminCreat();
        });
        mbAdd.setOnAction(actionEvent -> {
            loadGraphicWithAdminAdd();
        });
        btAddAccount.setOnAction(e -> {
            handleAddUSerOrAdmin();
        });
        tfFullName.textProperty().bindBidirectional(viewModel.fullNamePropertyProperty());
        tfEmail.textProperty().bindBidirectional(viewModel.emailPropertyProperty());
        tfPassword.textProperty().bindBidirectional(viewModel.passwordPropertyProperty());
        tfUserName.textProperty().bindBidirectional(viewModel.userNamePropertyProperty());
        tfConfirmPassword.textProperty().bindBidirectional(viewModel.confirmPasswordPropertyProperty());
    }

    private void loadAtStart() {
        mbFunction.setVisible(false);
        mbFunction.setDisable(true);
        lbSelectFunction.setVisible(false);
        btAddAccount.setVisible(false);
        btAddAccount.setDisable(true);
        tfEmail.setVisible(false);
        tfEmail.setDisable(true);
        lbEnterEmail.setVisible(false);
        tfPassword.setVisible(false);
        tfPassword.setDisable(true);
        lbEnterPassword.setVisible(false);
        tfConfirmPassword.setVisible(false);
        lbConfirmPassword.setVisible(false);
        tfConfirmPassword.setDisable(true);
        tfFullName.setVisible(false);
        lbEnterFullName.setVisible(false);
        tfFullName.setDisable(true);
        lbEnterUserName.setVisible(false);
        tfUserName.setDisable(true);
        tfUserName.setVisible(false);
        loadNotError();
    }

    private void loadGraphicWithUserCreat() {
        mbFunction.setVisible(false);
        mbFunction.setDisable(true);
        lbSelectFunction.setVisible(false);
        btAddAccount.setVisible(true);
        btAddAccount.setDisable(false);
        tfEmail.setVisible(true);
        tfEmail.setDisable(false);
        lbEnterEmail.setVisible(true);
        tfPassword.setVisible(true);
        tfPassword.setDisable(false);
        lbEnterPassword.setVisible(true);
        tfConfirmPassword.setVisible(true);
        lbConfirmPassword.setVisible(true);
        tfConfirmPassword.setDisable(false);
        tfFullName.setVisible(true);
        lbEnterFullName.setVisible(true);
        tfFullName.setDisable(false);
        lbEnterUserName.setVisible(true);
        tfUserName.setDisable(false);
        tfUserName.setVisible(true);
        mbAccount.setText("User");
        loadNotError();
    }

    private void loadGraphicWithAdminCreat() {
        mbFunction.setVisible(true);
        mbFunction.setDisable(false);
        lbSelectFunction.setVisible(true);
        btAddAccount.setVisible(true);
        btAddAccount.setDisable(false);
        tfEmail.setVisible(true);
        tfEmail.setDisable(false);
        lbEnterEmail.setVisible(true);
        tfPassword.setVisible(true);
        tfPassword.setDisable(false);
        lbEnterPassword.setVisible(true);
        tfConfirmPassword.setVisible(true);
        lbConfirmPassword.setVisible(true);
        tfConfirmPassword.setDisable(false);
        tfFullName.setVisible(true);
        lbEnterFullName.setVisible(true);
        tfFullName.setDisable(false);
        lbEnterUserName.setVisible(true);
        tfUserName.setDisable(false);
        tfUserName.setVisible(true);
        mbAccount.setText("Admin");
        mbFunction.setText("Creat");
        loadNotError();
    }

    private void loadGraphicWithAdminAdd() {
        mbFunction.setVisible(true);
        mbFunction.setDisable(false);
        lbSelectFunction.setVisible(true);
        btAddAccount.setVisible(true);
        btAddAccount.setDisable(false);
        tfEmail.setVisible(true);
        tfEmail.setDisable(false);
        lbEnterEmail.setVisible(true);
        tfPassword.setVisible(true);
        tfPassword.setDisable(false);
        lbEnterPassword.setVisible(true);
        tfConfirmPassword.setVisible(false);
        lbConfirmPassword.setVisible(false);
        tfConfirmPassword.setDisable(true);
        tfFullName.setVisible(false);
        lbEnterFullName.setVisible(false);
        tfFullName.setDisable(true);
        lbEnterUserName.setVisible(true);
        tfUserName.setDisable(false);
        tfUserName.setVisible(true);
        mbAccount.setText("Admin");
        mbFunction.setText("Add");
        loadNotError();
    }

    private void loadGraphicAdmin() {
        mbFunction.setVisible(true);
        mbFunction.setDisable(false);
        lbSelectFunction.setVisible(true);
        btAddAccount.setVisible(false);
        btAddAccount.setDisable(true);
        tfEmail.setVisible(false);
        tfEmail.setDisable(true);
        lbEnterEmail.setVisible(false);
        tfPassword.setVisible(false);
        tfPassword.setDisable(true);
        lbEnterPassword.setVisible(false);
        tfConfirmPassword.setVisible(false);
        lbConfirmPassword.setVisible(false);
        tfConfirmPassword.setDisable(true);
        tfFullName.setVisible(false);
        lbEnterFullName.setVisible(false);
        tfFullName.setDisable(true);
        lbEnterUserName.setVisible(false);
        tfUserName.setDisable(true);
        tfUserName.setVisible(false);
        mbAccount.setText("Admin");
        mbFunction.setText("Function");
        loadNotError();
    }

    private void clear() {
        tfUserName.clear();
        tfPassword.clear();
        tfConfirmPassword.clear();
        tfFullName.clear();
        tfEmail.clear();
        mbFunction.setText("function");
        mbAccount.setText("account");
    }

    private void handleAddUSerOrAdmin() {
        if (mbAccount.getText().equals("User")) {
            if(viewModel.getPassword().equals(viewModel.getConfirmPassword())) {
                loadErrorPassword();
                clear();
                return;
            }
            if(viewModel.checkIfAccountExists()) {
                loadErrorSignUp();
                clear();
                return;
            }
            viewModel.AddAccountUser();
            loadAtStart();
            clear();
        } else if (mbUser.getText().equals("Admin")) {
            if (mbFunction.getText().equals("Creat")) {
                if(viewModel.getPassword().equals(viewModel.getConfirmPassword())) {
                    loadErrorPassword();
                    clear();
                    return;
                }
                if(viewModel.checkIfAccountExists()) {
                    loadErrorSignUp();
                    clear();
                    return;
                }
                viewModel.CreatAccountAdmin();
                loadAtStart();
                clear();
            } else if (mbFunction.getText().equals("Add")) {
                if(!viewModel.checkIfAccountExists()) {
                    loadErrorAddAdmin();
                    clear();
                    return;
                }
                viewModel.AddAccountAdmin();
                loadAtStart();
                clear();
            } else {
                return;
            }
        } else {
            return;
        }
    }

    private void loadErrorSignUp() {
        lbErrorSignUp.setVisible(true);
        lbErrorAddAdmin.setVisible(false);
        lbErrorPassword.setVisible(false);
    }

    private void loadErrorAddAdmin() {
        lbErrorAddAdmin.setVisible(true);
        lbErrorSignUp.setVisible(false);
        lbErrorPassword.setVisible(false);
    }

    private void loadErrorPassword(){
        lbErrorAddAdmin.setVisible(false);
        lbErrorSignUp.setVisible(false);
        lbErrorPassword.setVisible(true);
    }

    private void loadNotError()
    {
        lbErrorAddAdmin.setVisible(false);
        lbErrorSignUp.setVisible(false);
        lbErrorPassword.setVisible(false);
    }
}


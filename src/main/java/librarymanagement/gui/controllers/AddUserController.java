package librarymanagement.gui.controllers;


import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
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
    private MenuItem miCreateAdminAccount;

    @FXML
    private MenuItem miSetAsAdmin;

    @FXML
    private MenuButton mbAccount;

    @FXML
    private MenuItem miUser;

    @FXML
    private MenuItem miAdmin;

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
        miUser.setOnAction(event -> {
            loadGraphicWithUserCreat();
        });
        miAdmin.setOnAction(actionEvent -> {
            loadGraphicAdmin();
        });
        miCreateAdminAccount.setOnAction(actionEvent -> {
            loadGraphicWithCreatAdmin();
        });
        miSetAsAdmin.setOnAction(actionEvent -> {
            loadGraphicWithSetAdmin();
        });
        btAddAccount.setOnAction(e -> {
            handleAddUSerOrAdmin();
        });
        tfFullName.textProperty().bindBidirectional(viewModel.fullnameProperty());
        tfEmail.textProperty().bindBidirectional(viewModel.emailProperty());
        tfPassword.textProperty().bindBidirectional(viewModel.passwordProperty());
        tfUserName.textProperty().bindBidirectional(viewModel.usernameProperty());
        tfConfirmPassword.textProperty().bindBidirectional(viewModel.confirmPasswordProperty());
        tfUserName.setOnKeyPressed(actionEvent -> {
            if (actionEvent.getCode() == KeyCode.TAB || actionEvent.getCode() == KeyCode.ENTER) {
                tfEmail.requestFocus();
            }
        });
        tfEmail.setOnKeyPressed(actionEvent -> {
            if (actionEvent.getCode() == KeyCode.TAB || actionEvent.getCode() == KeyCode.ENTER) {
                if (mbAccount.getText().equals("User") || mbFunction.getText().equals("Create Admin Account")) {
                    tfFullName.requestFocus();
                } else if (mbFunction.getText().equals("Set As Admin")) {
                    tfPassword.requestFocus();
                } else {
                }
            }
        });
        tfFullName.setOnKeyPressed(actionEvent -> {
            if (actionEvent.getCode() == KeyCode.TAB || actionEvent.getCode() == KeyCode.ENTER) {
                tfPassword.requestFocus();
            }
        });
        tfPassword.setOnKeyPressed(actionEvent -> {
            if (actionEvent.getCode() == KeyCode.TAB || actionEvent.getCode() == KeyCode.ENTER) {
                tfConfirmPassword.requestFocus();
            }
        });
        tfConfirmPassword.setOnKeyPressed(actionEvent -> {
            if (actionEvent.getCode() == KeyCode.TAB || actionEvent.getCode() == KeyCode.ENTER) {
                btAddAccount.fire();
            }
        });
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
        tfPassword.setLayoutX(318);
        tfPassword.setLayoutY(348);
        lbEnterPassword.setLayoutX(93);
        lbEnterPassword.setLayoutY(348);
        tfConfirmPassword.setVisible(true);
        lbConfirmPassword.setVisible(true);
        tfConfirmPassword.setDisable(false);
        tfFullName.setVisible(true);
        lbEnterFullName.setVisible(true);
        tfFullName.setDisable(false);
        tfFullName.setLayoutX(318);
        tfFullName.setLayoutY(310);
        lbEnterFullName.setLayoutX(93);
        lbEnterFullName.setLayoutY(310);
        lbEnterUserName.setVisible(true);
        tfUserName.setDisable(false);
        tfUserName.setVisible(true);
        mbAccount.setText("User");
        loadNotError();
        clear();
    }

    private void loadGraphicWithCreatAdmin() {
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
        tfPassword.setLayoutX(318);
        tfPassword.setLayoutY(348);
        lbEnterPassword.setLayoutX(93);
        lbEnterPassword.setLayoutY(348);
        tfConfirmPassword.setVisible(true);
        lbConfirmPassword.setVisible(true);
        tfConfirmPassword.setDisable(false);
        tfFullName.setVisible(true);
        lbEnterFullName.setVisible(true);
        tfFullName.setDisable(false);
        tfFullName.setLayoutX(318);
        tfFullName.setLayoutY(310);
        lbEnterFullName.setLayoutX(93);
        lbEnterFullName.setLayoutY(310);
        lbEnterUserName.setVisible(true);
        tfUserName.setDisable(false);
        tfUserName.setVisible(true);
        mbAccount.setText("Admin");
        mbFunction.setText("Creat Admin Account");
        loadNotError();
        clear();
    }

    private void loadGraphicWithSetAdmin() {
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
        tfPassword.setLayoutX(318);
        tfPassword.setLayoutY(310);
        lbEnterPassword.setLayoutX(93);
        lbEnterPassword.setLayoutY(310);
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
        mbFunction.setText("Se dmin");
        loadNotError();
        clear();
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
            if (viewModel.getPassword().equals(viewModel.getConfirmPassword())) {
                loadErrorPassword();
                clear();
                return;
            }
            if (viewModel.checkIfAccountExists()) {
                loadErrorSignUp();
                clear();
                return;
            }
            viewModel.AddAccountUser();
            loadAtStart();
            clear();
        } else if (mbAccount.getText().equals("Admin")) {
            if (mbFunction.getText().equals("Creat Admin Account")) {
                if (viewModel.getPassword().equals(viewModel.getConfirmPassword())) {
                    loadErrorPassword();
                    clear();
                    return;
                }
                if (viewModel.checkIfAccountExists()) {
                    loadErrorSignUp();
                    clear();
                    return;
                }
                viewModel.CreatAccountAdmin();
                loadAtStart();
                clear();
            } else if (mbFunction.getText().equals("Set As Admin")) {
                if (!viewModel.checkIfAccountExists()) {
                    loadErrorAddAdmin();
                    clear();
                    return;
                }
                viewModel.AddAccountAdminFromUser();
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

    private void loadErrorPassword() {
        lbErrorAddAdmin.setVisible(false);
        lbErrorSignUp.setVisible(false);
        lbErrorPassword.setVisible(true);
    }

    private void loadNotError() {
        lbErrorAddAdmin.setVisible(false);
        lbErrorSignUp.setVisible(false);
        lbErrorPassword.setVisible(false);
    }
}


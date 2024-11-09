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
    private TextField tfUsername;

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
    private TextField tfFullname;

    @FXML
    private Label lbSelectFunction;

    @FXML
    private Label lbEnterFullname;

    @FXML
    private Label lbEnterEmail;

    @FXML
    private Label lbEnterPassword;

    @FXML
    private Label lbConfirmPassword;

    @FXML
    private Label lbEnterUsername;

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
            loadGraphicWithUserCreate();
        });
        miAdmin.setOnAction(actionEvent -> {
            loadGraphicAdmin();
        });
        miCreateAdminAccount.setOnAction(actionEvent -> {
            loadGraphicWithCreateAdmin();
        });
        miSetAsAdmin.setOnAction(actionEvent -> {
            loadGraphicWithSetAdmin();
        });
        btAddAccount.setOnAction(e -> {
            handleAddUSerOrAdmin();
        });
        tfFullname.textProperty().bindBidirectional(viewModel.fullnameProperty());
        tfEmail.textProperty().bindBidirectional(viewModel.emailProperty());
        tfPassword.textProperty().bindBidirectional(viewModel.passwordProperty());
        tfUsername.textProperty().bindBidirectional(viewModel.usernameProperty());
        tfConfirmPassword.textProperty().bindBidirectional(viewModel.confirmPasswordProperty());
        tfUsername.setOnKeyPressed(actionEvent -> {
            if (actionEvent.getCode() == KeyCode.ENTER) {
                handleAddUSerOrAdmin();
            }
        });
        tfEmail.setOnKeyPressed(actionEvent -> {
            if (actionEvent.getCode() == KeyCode.ENTER) {
                handleAddUSerOrAdmin();
            }
        });
        tfFullname.setOnKeyPressed(actionEvent -> {
            if (actionEvent.getCode() == KeyCode.ENTER) {
                handleAddUSerOrAdmin();
            }
        });
        tfPassword.setOnKeyPressed(actionEvent -> {
            if (actionEvent.getCode() == KeyCode.ENTER) {
                handleAddUSerOrAdmin();
            }
        });
        tfConfirmPassword.setOnKeyPressed(actionEvent -> {
            if (actionEvent.getCode() == KeyCode.ENTER) {
                handleAddUSerOrAdmin();
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
        tfFullname.setVisible(false);
        lbEnterFullname.setVisible(false);
        tfFullname.setDisable(true);
        lbEnterUsername.setVisible(false);
        tfUsername.setDisable(true);
        tfUsername.setVisible(false);
        mbAccount.setText("Account");
        loadNotError();
    }

    private void loadGraphicWithUserCreate() {
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
        tfFullname.setVisible(true);
        lbEnterFullname.setVisible(true);
        tfFullname.setDisable(false);
        tfFullname.setLayoutX(318);
        tfFullname.setLayoutY(310);
        lbEnterFullname.setLayoutX(93);
        lbEnterFullname.setLayoutY(310);
        lbEnterUsername.setVisible(true);
        tfUsername.setDisable(false);
        tfUsername.setVisible(true);
        mbAccount.setText("User");
        loadNotError();
        clear();
    }

    private void loadGraphicWithCreateAdmin() {
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
        tfFullname.setVisible(true);
        lbEnterFullname.setVisible(true);
        tfFullname.setDisable(false);
        tfFullname.setLayoutX(318);
        tfFullname.setLayoutY(310);
        lbEnterFullname.setLayoutX(93);
        lbEnterFullname.setLayoutY(310);
        lbEnterUsername.setVisible(true);
        tfUsername.setDisable(false);
        tfUsername.setVisible(true);
        mbAccount.setText("Admin");
        mbFunction.setText("Create Admin Account");
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
        tfFullname.setVisible(false);
        lbEnterFullname.setVisible(false);
        tfFullname.setDisable(true);
        lbEnterUsername.setVisible(true);
        tfUsername.setDisable(false);
        tfUsername.setVisible(true);
        mbAccount.setText("Admin");
        mbFunction.setText("Set As Admin");
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
        tfFullname.setVisible(false);
        lbEnterFullname.setVisible(false);
        tfFullname.setDisable(true);
        lbEnterUsername.setVisible(false);
        tfUsername.setDisable(true);
        tfUsername.setVisible(false);
        mbAccount.setText("Admin");
        mbFunction.setText("Function");
        loadNotError();
    }

    private void clear() {
        tfUsername.clear();
        tfPassword.clear();
        tfConfirmPassword.clear();
        tfFullname.clear();
        tfEmail.clear();
    }

    private void handleAddUSerOrAdmin() {
        if (mbAccount.getText().equals("User")) {
            if (!viewModel.getPassword().equals(viewModel.getConfirmPassword())) {
                loadErrorPassword();
                clear();
                return;
            }
            if (viewModel.checkIfAccountExists()) {
                loadErrorSignUp();
                clear();
                return;
            }
            viewModel.addUserAccount();
            loadAtStart();
            clear();
        } else if (mbAccount.getText().equals("Admin")) {
            if (mbFunction.getText().equals("Create Admin Account")) {
                if (!viewModel.getPassword().equals(viewModel.getConfirmPassword())) {
                    loadErrorPassword();
                    clear();
                    return;
                }
                if (viewModel.checkIfAccountExists()) {
                    loadErrorSignUp();
                    clear();
                    return;
                }
                viewModel.createAdminAccount();
                loadAtStart();
                clear();
            } else if (mbFunction.getText().equals("Set As Admin")) {
                if (!viewModel.checkIfAccountExists()) {
                    loadErrorAddAdmin();
                    clear();
                    return;
                }
                viewModel.setAsAdmin();
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


package librarymanagement.gui.controllers;


import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import librarymanagement.gui.viewmodels.AddUserViewModel;


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
    private Label lbEnterUsername;

    @FXML
    private Label lbError;

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
        tfFullName.textProperty().bindBidirectional(viewModel.fullNameProperty());
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
        tfFullName.setOnKeyPressed(actionEvent -> {
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
        tfFullName.setVisible(false);
        lbEnterFullName.setVisible(false);
        tfFullName.setDisable(true);
        lbEnterUsername.setVisible(false);
        tfUsername.setDisable(true);
        tfUsername.setVisible(false);
        mbAccount.setText("Account");
        lbError.setVisible(false);
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
        tfFullName.setVisible(true);
        lbEnterFullName.setVisible(true);
        tfFullName.setDisable(false);
        tfFullName.setLayoutX(318);
        tfFullName.setLayoutY(310);
        lbEnterFullName.setLayoutX(93);
        lbEnterFullName.setLayoutY(310);
        lbEnterUsername.setVisible(true);
        tfUsername.setDisable(false);
        tfUsername.setVisible(true);
        mbAccount.setText("User");
        lbError.setVisible(false);
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
        tfFullName.setVisible(true);
        lbEnterFullName.setVisible(true);
        tfFullName.setDisable(false);
        tfFullName.setLayoutX(318);
        tfFullName.setLayoutY(310);
        lbEnterFullName.setLayoutX(93);
        lbEnterFullName.setLayoutY(310);
        lbEnterUsername.setVisible(true);
        tfUsername.setDisable(false);
        tfUsername.setVisible(true);
        mbAccount.setText("Admin");
        mbFunction.setText("Create Admin Account");
        lbError.setVisible(false);
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
        lbEnterUsername.setVisible(true);
        tfUsername.setDisable(false);
        tfUsername.setVisible(true);
        mbAccount.setText("Admin");
        mbFunction.setText("Set As Admin");
        lbError.setVisible(false);
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
        lbEnterUsername.setVisible(false);
        tfUsername.setDisable(true);
        tfUsername.setVisible(false);
        mbAccount.setText("Admin");
        mbFunction.setText("Function");
        lbError.setVisible(false);
    }

    private void clear() {
        tfUsername.clear();
        tfPassword.clear();
        tfConfirmPassword.clear();
        tfFullName.clear();
        tfEmail.clear();
    }

    private void handleAddUSerOrAdmin() {
        String result = viewModel.addUserOrAdmin();

        switch (result) {
            case "error_password_not_match":
                lbError.setText("The password and confirmation password do not match.");
                lbError.setVisible(true);
                break;
            case "error_account_exist":
                lbError.setText("This account already exists. Please choose a different username.");
                lbError.setVisible(true);
                break;
            case "error_add_admin":
                lbError.setText("Only admin users are allowed to perform this action.");
                lbError.setVisible(true);
                break;
            case "error_not_admin":
                lbError.setText("The account is not an admin account.");
                lbError.setVisible(true);
                break;
            case "success_user":
                loadAtStart();
                lbError.setText("A user account has been successfully created.");
                lbError.setVisible(true);
                break;
            case "success_admin_created":
                loadAtStart();
                lbError.setText("An admin account has been successfully created.");
                lbError.setVisible(true);
                break;
            case "success_admin_set":
                loadAtStart();
                lbError.setText("The user account has been successfully promoted to admin.");
                lbError.setVisible(true);
                break;
            default:
                break;
        }
        clear();
    }

}


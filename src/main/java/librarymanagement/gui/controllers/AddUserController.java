package librarymanagement.gui.controllers;


import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import librarymanagement.gui.viewmodels.AddUserViewModel;


public class AddUserController {
    @FXML
    private Button btAddAccount;

    @FXML
    private TextField tfUsername;

    @FXML
    private MenuButton mbAccountType;

    @FXML
    private TextField tfEmail;

    @FXML
    private TextField tfPassword;

    @FXML
    private TextField tfConfirmPassword;

    @FXML
    private TextField tfFullname;

    @FXML
    private Label lbResult;

    private final AddUserViewModel viewModel = new AddUserViewModel();

    @FXML
    public void initialize() {
        viewModel.menuAccountProperty().setValue("Account type");
        btAddAccount.setOnAction(e -> {
            handleAddAccount();
        });
        tfFullname.textProperty().bindBidirectional(viewModel.fullNameProperty());
        tfEmail.textProperty().bindBidirectional(viewModel.emailProperty());
        tfPassword.textProperty().bindBidirectional(viewModel.passwordProperty());
        tfUsername.textProperty().bindBidirectional(viewModel.usernameProperty());
        tfConfirmPassword.textProperty().bindBidirectional(viewModel.confirmPasswordProperty());
        lbResult.textProperty().bindBidirectional(viewModel.resultProperty());
        mbAccountType.textProperty().bindBidirectional(viewModel.menuAccountProperty());
        for (MenuItem item : mbAccountType.getItems()) {
            item.setOnAction(e -> {
                mbAccountType.setText(item.getText());
            });
        }
        tfUsername.setOnKeyPressed(actionEvent -> {
            if (actionEvent.getCode() == KeyCode.ENTER) {
                handleAddAccount();
            }
        });
        tfEmail.setOnKeyPressed(actionEvent -> {
            if (actionEvent.getCode() == KeyCode.ENTER) {
                handleAddAccount();
            }
        });
        tfFullname.setOnKeyPressed(actionEvent -> {
            if (actionEvent.getCode() == KeyCode.ENTER) {
                handleAddAccount();
            }
        });
        tfPassword.setOnKeyPressed(actionEvent -> {
            if (actionEvent.getCode() == KeyCode.ENTER) {
                handleAddAccount();
            }
        });
        tfConfirmPassword.setOnKeyPressed(actionEvent -> {
            if (actionEvent.getCode() == KeyCode.ENTER) {
                handleAddAccount();
            }
        });
    }

    private void clear() {
        tfUsername.clear();
        tfPassword.clear();
        tfConfirmPassword.clear();
        tfFullname.clear();
        tfEmail.clear();
    }

    private void handleAddAccount() {
        LoadingPopupController loadingPopup = LoadingPopupController.newInstance("Creating Account");
        loadingPopup.initOwnerStage(UIController.getPrimaryStage());
        loadingPopup.show();

        Task<Void> addAccountTask = new Task<>() {
            @Override
            protected Void call() throws Exception {
                viewModel.addAccount();
                return null;
            }
        };

        addAccountTask.setOnSucceeded(event -> {
            loadingPopup.close();
            lbResult.setVisible(true);
            clear();
        });

        addAccountTask.setOnFailed(event -> {

            loadingPopup.close();
            lbResult.setText("Error!");
            lbResult.setVisible(true);
        });


        new Thread(addAccountTask).start();

    }

}


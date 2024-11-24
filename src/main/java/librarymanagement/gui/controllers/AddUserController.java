package librarymanagement.gui.controllers;


import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import librarymanagement.UserAuth.RegistrationResult;
import librarymanagement.gui.viewmodels.AddUserViewModel;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;


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
    private TextField tfFullName;

    @FXML
    private Label lbResult;

    private final AddUserViewModel viewModel = new AddUserViewModel();

    @FXML
    public void initialize() {
        viewModel.menuAccountProperty().setValue("Account type");
        btAddAccount.setOnAction(_ -> handleAddAccount());
        tfFullName.textProperty().bindBidirectional(viewModel.fullNameProperty());
        tfEmail.textProperty().bindBidirectional(viewModel.emailProperty());
        tfPassword.textProperty().bindBidirectional(viewModel.passwordProperty());
        tfUsername.textProperty().bindBidirectional(viewModel.usernameProperty());
        tfConfirmPassword.textProperty().bindBidirectional(viewModel.confirmPasswordProperty());
        lbResult.textProperty().bindBidirectional(viewModel.resultProperty());
        mbAccountType.textProperty().bindBidirectional(viewModel.menuAccountProperty());
        for (MenuItem item : mbAccountType.getItems()) {
            item.setOnAction(_ -> mbAccountType.setText(item.getText()));
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
        tfFullName.setOnKeyPressed(actionEvent -> {
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
        tfFullName.clear();
        tfEmail.clear();
    }

    private void handleAddAccount() {
        if (Objects.equals(mbAccountType.getText(), "Account type")
                || tfUsername.getText() == null || tfUsername.getText().isEmpty()
                || tfPassword.getText() == null || tfPassword.getText().isEmpty()
                || tfConfirmPassword.getText() == null || tfConfirmPassword.getText().isEmpty()
                || tfFullName.getText() == null || tfFullName.getText().isEmpty()
                || tfEmail.getText() == null || tfEmail.getText().isEmpty()) {
            lbResult.setText("You need to fill all fields!");
            lbResult.setVisible(true);
            return;
        }

        LoadingPopupController loadingPopup = LoadingPopupController.newInstance("Creating Account");
        loadingPopup.initOwnerStage(UIController.getPrimaryStage());
        loadingPopup.show();

        Task<RegistrationResult> addAccountTask = getVoidTask(loadingPopup);


        new Thread(addAccountTask).start();

    }

    @NotNull
    private Task<RegistrationResult> getVoidTask(LoadingPopupController loadingPopup) {
        Task<RegistrationResult> addAccountTask = new Task<>() {
            @Override
            protected RegistrationResult call() {
                return viewModel.addAccount();
            }
        };

        addAccountTask.setOnSucceeded(_ -> {
            loadingPopup.close();
            lbResult.setVisible(true);
            lbResult.setText(addAccountTask.getValue().getMessage());
            clear();
        });

        return addAccountTask;
    }

}


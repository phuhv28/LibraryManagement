package librarymanagement.gui.controllers;

import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import librarymanagement.entity.RegistrationResult;
import librarymanagement.gui.viewmodels.AddUserViewModel;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

/**
 * The `AddUserController` class manages the UI logic for adding a new user account.
 * It connects the view layer (FXML components) with the underlying `AddUserViewModel`
 * and handles user interactions like form submission and input validation.
 */
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

    /**
     * Initializes the controller.
     * <p>
     * Sets up bindings between UI components and the view model, assigns event handlers,
     * and configures user interaction behavior, including form validation and submission on Enter key press.
     * </p>
     */
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

    /**
     * Clears all text fields in the user form.
     */
    private void clear() {
        tfUsername.clear();
        tfPassword.clear();
        tfConfirmPassword.clear();
        tfFullName.clear();
        tfEmail.clear();
    }

    /**
     * Handles the event of adding a new account.
     * <p>
     * Validates the form input, displays a loading popup, and submits the form asynchronously.
     * If the form is incomplete, shows an error message in the result label.
     * </p>
     */
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

    /**
     * Creates a background task to handle the account creation process.
     * <p>
     * Executes the account registration logic in the `AddUserViewModel`, updates the UI on success,
     * and closes the loading popup.
     * </p>
     *
     * @param loadingPopup the popup displayed during the account creation process.
     * @return a `Task` object that performs the account creation operation.
     */
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

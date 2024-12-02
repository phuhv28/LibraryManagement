package librarymanagement.gui.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.input.KeyCode;
import librarymanagement.entity.Account;
import librarymanagement.gui.models.AccountService;

/**
 * The `ChangePasswordController` class handles the user interface for changing passwords.
 * It manages user input, interacts with the `AccountService`, and provides feedback on the success or failure of the operation.
 */
public class ChangePasswordController {

    @FXML
    private Button btChange;

    @FXML
    private PasswordField pfCurrentPassword;

    @FXML
    private PasswordField pfNewPassword;

    @FXML
    private PasswordField pfConfirmNewPassword;

    @FXML
    private Label lbError;

    private final AccountService accountService = AccountService.getInstance();

    /**
     * Initializes the controller.
     * <p>
     * Sets up event handlers for the "Change Password" button and allows
     * pressing Enter to submit the form when the "Confirm New Password" field is focused.
     * </p>
     */
    @FXML
    public void initialize() {
        // Set an event handler for pressing Enter in the "Confirm New Password" field
        pfConfirmNewPassword.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                handleChange();
            }
        });

        // Set an event handler for the "Change Password" button
        btChange.setOnAction(actionEvent -> handleChange());
    }

    /**
     * Handles the password change process.
     * <p>
     * Validates the current password and ensures the new password matches the confirmation password.
     * Updates the password if the validation succeeds and displays an appropriate message.
     * </p>
     */
    public void handleChange() {
        // Retrieve user input
        String currentPassword = pfCurrentPassword.getText();
        String newPassword = pfNewPassword.getText();
        String confirmNewPassword = pfConfirmNewPassword.getText();

        // Call the changePassword method and display the corresponding message (error or success) on the label.
        lbError.setText(accountService.changePassword(currentPassword, newPassword, confirmNewPassword).getMessage());

        // Clear the password fields
        clear();
    }

    /**
     * Clears the password fields.
     * <p>
     * Resets the text fields for the current, new, and confirm password inputs.
     * </p>
     */
    public void clear() {
        pfCurrentPassword.clear();
        pfNewPassword.clear();
        pfConfirmNewPassword.clear();
    }
}

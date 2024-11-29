package librarymanagement.gui.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.input.KeyCode;
import librarymanagement.UserAuth.Account;
import librarymanagement.UserAuth.AccountService;

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

    @FXML
    public void initialize() {
        pfConfirmNewPassword.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                handleChange();
            }
        });
        btChange.setOnAction(actionEvent -> handleChange());
    }

    public void handleChange() {
        String currentPassword = pfCurrentPassword.getText();
        String newPassword = pfNewPassword.getText();
        String confirmNewPassword = pfConfirmNewPassword.getText();

        Account account = accountService.getCurrentAccount();

        if (account.getPassword().equals(currentPassword)) {
            if (newPassword.equals(confirmNewPassword)) {
                account.setPassword(newPassword);
                lbError.setText("Your password has been changed successfully");
            } else {
                lbError.setText("Password and confirm password do not match");
            }
        } else {
            lbError.setText("Incorrect password");
        }
        clear();
    }

    public void clear() {
        pfCurrentPassword.clear();
        pfNewPassword.clear();
        pfConfirmNewPassword.clear();
    }
}

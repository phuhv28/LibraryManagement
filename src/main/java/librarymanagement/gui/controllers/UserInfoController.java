package librarymanagement.gui.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import librarymanagement.UserAuth.AccountService;

public class UserInfoController {

    @FXML
    private Label lbFullName;

    @FXML
    private Label lbNumberOfBooksBorrowed;

    @FXML
    private Label lbUserName;

    @FXML
    private Label lbEmail;


    @FXML
    public void initialize() {
        lbFullName.setText(AccountService.getInstance().getCurrentAccount().getFullName());
        lbNumberOfBooksBorrowed.setText("0");
        lbUserName.setText(AccountService.getInstance().getCurrentAccount().getUsername());
        lbEmail.setText(AccountService.getInstance().getCurrentAccount().getEmail());
    }
}

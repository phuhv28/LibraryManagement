package librarymanagement.gui.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

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
        lbFullName.setText("Full Name");
        lbNumberOfBooksBorrowed.setText("Number of Books Borrowed");
        lbUserName.setText("User Name");
        lbEmail.setText("Email");
    }
}

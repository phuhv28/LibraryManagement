package librarymanagement.gui.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import librarymanagement.UserAuth.AccountService;
import librarymanagement.data.BorrowingService;
import librarymanagement.data.DocumentServiceFactory;
import librarymanagement.data.DocumentType;

public class UserInfoController {

    @FXML
    private Label lbFullName;

    @FXML
    private Label lbNumberOfBooksBorrowed;

    @FXML
    private Label lbUserName;

    @FXML
    private Label lbEmail;

    private final AccountService accountService = AccountService.getInstance();
    private final BorrowingService borrowingService = new BorrowingService(DocumentServiceFactory.getDocumentService(DocumentType.BOOK));

    @FXML
    public void initialize() {
        lbFullName.setText(accountService.getCurrentAccount().getFullName());
        lbNumberOfBooksBorrowed.setText(String.valueOf(borrowingService.getBorrowRecordsOfCurrentAccount().size()));
        lbUserName.setText(accountService.getCurrentAccount().getUsername());
        lbEmail.setText(accountService.getCurrentAccount().getEmail());
    }
}

package librarymanagement.gui.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import librarymanagement.UserAuth.AccountService;
import librarymanagement.data.BorrowRecord;
import librarymanagement.data.BorrowingService;
import librarymanagement.data.DocumentServiceFactory;
import librarymanagement.data.DocumentType;

import java.util.List;

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
        lbUserName.setText(accountService.getCurrentAccount().getUsername());
        lbEmail.setText(accountService.getCurrentAccount().getEmail());
        List<BorrowRecord> borrowRecords = borrowingService.getBorrowRecordsOfCurrentAccount();
        if (borrowRecords == null) {
            lbNumberOfBooksBorrowed.setText("0");
        } else {
            lbNumberOfBooksBorrowed.setText(String.valueOf(borrowRecords.size()));
        }
    }
}

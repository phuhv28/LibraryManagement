package librarymanagement.gui.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import librarymanagement.gui.models.AccountService;
import librarymanagement.entity.BorrowRecord;
import librarymanagement.gui.models.BorrowingService;
import librarymanagement.gui.models.DocumentServiceFactory;
import librarymanagement.entity.DocumentType;
import librarymanagement.utils.SceneHistoryStack;

import java.io.IOException;
import java.util.List;

public class UserInfoController {

    @FXML
    private AnchorPane apInformationUser;

    @FXML
    private Label lbFullName;

    @FXML
    private Label lbNumberOfBooksBorrowed;

    @FXML
    private Label lbUserName;

    @FXML
    private Label lbEmail;

    @FXML
    private Button btLogOut;

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
        btLogOut.setOnAction(actionEvent -> handleLogOut());
    }
    private void handleLogOut() {
        UIController.showScene(StartScreenController.getStartScreen());
    }

    public void gotoChangePassword() {
        try {
            SceneHistoryStack.listPreviousFxmlFile.push(SceneHistoryStack.previousFxmlFile);
            SceneHistoryStack.previousFxmlFile = "ChangePasswordScene.fxml";
            AnchorPane newPane = FXMLLoader.load(getClass().getResource("/FXML/ChangePasswordScene.fxml"));
            apInformationUser.getChildren().setAll(newPane);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

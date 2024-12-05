package librarymanagement.gui.controllers;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import librarymanagement.entity.User;
import librarymanagement.entity.BorrowRecord;
import librarymanagement.entity.DocumentType;
import librarymanagement.gui.models.BorrowingService;
import librarymanagement.gui.models.DocumentServiceFactory;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class UserStatusController {
    @FXML
    private Label lbFullName;

    @FXML
    private Label lbNumberOfBooksBorrowed;

    @FXML
    private Label lbEmail;

    @FXML
    private Label lbUserName;

    @FXML
    private TableView<BorrowRecord> tableView;

    @FXML
    private TableColumn<BorrowRecord, Integer> recordIdCol;

    @FXML
    private TableColumn<BorrowRecord, String> documentIdCol;

    @FXML
    private TableColumn<BorrowRecord, String> documentTitleCol;

    @FXML
    private TableColumn<BorrowRecord, LocalDate> borrowDateCol;

    @FXML
    private TableColumn<BorrowRecord, LocalDate> dueDateCol;

    private static final BorrowingService borrowingService = new BorrowingService(DocumentServiceFactory.getDocumentService(DocumentType.BOOK));

    private static UserStatusController instance;
    private static Stage stage;
    private static User user;


    public void initialize() {
        lbFullName.setText(user.getFullName());
        lbUserName.setText(user.getUsername());
        lbEmail.setText(user.getEmail());
        lbNumberOfBooksBorrowed.setText(String.valueOf(user.getNumberOfBooksIsBorrowing()));

        List<BorrowRecord> records = borrowingService.getBorrowRecordsOfUser(user.getUserID());
        if (records != null) {
            tableView.setItems(FXCollections.observableArrayList(records));
            recordIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
            documentIdCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getDocument().getId()));
            documentTitleCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getDocument().getTitle()));
            borrowDateCol.setCellValueFactory(new PropertyValueFactory<>("borrowDate"));
            dueDateCol.setCellValueFactory(new PropertyValueFactory<>("dueDate"));

            setCellFactoryForDate(borrowDateCol);
            setCellFactoryForDate(dueDateCol);
        }
    }

    public static void newInstance(User user) {
        UserStatusController.user = user;
        FXMLLoader loader = new FXMLLoader(LoadingPopupController.class.getResource("/FXML/UserStatus.fxml"));
        try {
            Parent parent = loader.load();
            Scene scene = new Scene(parent);
            Stage stage = new Stage();
            stage.setTitle("User Status");
            stage.setScene(scene);
            stage.setResizable(false);
            stage.initOwner(UIController.getPrimaryStage());
            stage.initModality(Modality.WINDOW_MODAL);

            UserStatusController.stage = stage;
        } catch (Exception e) {
            e.printStackTrace();
        }
        UserStatusController.stage.show();
    }

    private void setCellFactoryForDate(TableColumn<BorrowRecord, LocalDate> col) {
        col.setCellFactory(_ -> new TableCell<>() {
            @Override
            protected void updateItem(LocalDate item, boolean empty) {
                super.updateItem(item, empty);
                if (item != null && !empty) {
                    setText(item.format(DateTimeFormatter.ofPattern("dd-MM-yyyy")));
                } else {
                    setText(null);
                }
            }
        });
    }
}

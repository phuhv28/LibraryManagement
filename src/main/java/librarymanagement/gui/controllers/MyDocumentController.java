package librarymanagement.gui.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.TableCell;
import javafx.scene.layout.AnchorPane;
import librarymanagement.data.BorrowRecord;
import librarymanagement.gui.utils.SceneHistoryStack;

import java.io.IOException;
import java.util.List;
import java.util.ArrayList;


public class MyDocumentController {

    @FXML
    private AnchorPane MyDocument;

    @FXML
    private TableView<BorrowRecord> tableView;

    @FXML
    private TableColumn<BorrowRecord, Integer> sttColumn;

    @FXML
    private TableColumn<BorrowRecord, Integer> documentIdColumn;

    @FXML
    private TableColumn<BorrowRecord, String> documentTitleColumn;

    @FXML
    private TableColumn<BorrowRecord, String> borrowDateColumn;

    @FXML
    private TableColumn<BorrowRecord, String> dueDateColumn;

    @FXML
    private TableColumn<BorrowRecord, Void> returnCol;

    @FXML
    private Label lbCheckEmpty;

    @FXML
    private Button btBorrowBooks;

    private ObservableList<BorrowRecord> borrowBookList;

    @FXML
    public void initialize() {
        List<BorrowRecord> listBook = new ArrayList<>();

        ObservableList<BorrowRecord> observableListBook = FXCollections.observableArrayList(listBook);
        tableView.setItems(observableListBook);

        if (listBook.isEmpty()) {
            tableView.setVisible(false);
            lbCheckEmpty.setVisible(true);
        } else {
            tableView.setVisible(true);
            lbCheckEmpty.setVisible(false);
        }

        sttColumn.setCellValueFactory(new PropertyValueFactory<>("stt"));
        documentIdColumn.setCellValueFactory(new PropertyValueFactory<>("documentId"));
        documentTitleColumn.setCellValueFactory(new PropertyValueFactory<>("documentTitle"));
        borrowDateColumn.setCellValueFactory(new PropertyValueFactory<>("borrowDate"));
        dueDateColumn.setCellValueFactory(new PropertyValueFactory<>("dueDate"));

        addReturnButtonToTable();

        btBorrowBooks.setOnAction(event -> loadBorrowBooks());
    }

    private void addReturnButtonToTable() {
        returnCol.setCellFactory(column -> new TableCell<>() {
            private final Button button = new Button("RETURN");

            {
                button.setOnAction(event -> {
                    BorrowRecord book = getTableView().getItems().get(getIndex());
                    handleFunction2(book);
                });
            }

            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    setGraphic(button);
                }
            }
        });
    }

    // function: read book
    private void handleFunction1(BorrowRecord book) {
    }

    // function: return book
    private void handleFunction2(BorrowRecord book) {
    }

    private void loadScene(String fxmlFile) {
        try {
            AnchorPane newPane = FXMLLoader.load(getClass().getResource("/FXML/" + fxmlFile));
            MyDocument.getChildren().setAll(newPane);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private void loadBorrowBooks() {
        SceneHistoryStack.listPreviousFxmlFile.push(SceneHistoryStack.previousFxmlFile);
        loadScene("BorrowDocument.fxml");
        SceneHistoryStack.previousFxmlFile="BorrowDocument.fxml";
    }

}


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
import librarymanagement.data.BorrowBook;

import java.io.IOException;
import java.util.List;
import java.util.ArrayList;


public class ListBorrowBookController {

    @FXML
    private AnchorPane ListBorrowBooks;

    @FXML
    private TableView<BorrowBook> tableView;

    @FXML
    private TableColumn<BorrowBook, Integer> sttColumn;

    @FXML
    private TableColumn<BorrowBook, Integer> documentIdColumn;

    @FXML
    private TableColumn<BorrowBook, String> documentTitleColumn;

    @FXML
    private TableColumn<BorrowBook, String> borrowDateColumn;

    @FXML
    private TableColumn<BorrowBook, String> dueDateColumn;

    @FXML
    private TableColumn<BorrowBook, Void> readColumn;

    @FXML
    private TableColumn<BorrowBook, Void> returnColumn;

    @FXML
    private Label lbCheckEmpty;

    @FXML
    private Button btBorrowBooks;

    private ObservableList<BorrowBook> borrowBookList;

    @FXML
    public void initialize() {
        // Example list books to print scenes
        List<BorrowBook> listBook = new ArrayList<>();
        listBook.add(new BorrowBook(134, "helo", "2024-10-01", "2024-11-03"));
        listBook.add(new BorrowBook(146, "hi", "2024-10-02", "2024-12-4"));
        listBook.add(new BorrowBook(180, "xin chao", "2024-10-03", "2025-02-04"));

        ObservableList<BorrowBook> observableListBook = FXCollections.observableArrayList(listBook);
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

        addReadButtonToTable();
        addReturnButtonToTable();

        btBorrowBooks.setOnAction(event -> loadBorrowBooks());
    }


    private void addReadButtonToTable() {
        readColumn.setCellFactory(column -> new TableCell<>() {
            private final Button button = new Button("  READ  ");

            {
                button.setOnAction(event -> {
                    BorrowBook book = getTableView().getItems().get(getIndex());
                    handleFunction1(book);
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

    private void addReturnButtonToTable() {
        returnColumn.setCellFactory(column -> new TableCell<>() {
            private final Button button = new Button("RETURN");

            {
                button.setOnAction(event -> {
                    BorrowBook book = getTableView().getItems().get(getIndex());
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
    private void handleFunction1(BorrowBook book) {
    }

    // function: return book
    private void handleFunction2(BorrowBook book) {
    }

    private void loadScene(String fxmlFile) {
        try {
            AnchorPane newPane = FXMLLoader.load(getClass().getResource("/FXML/" + fxmlFile));
            ListBorrowBooks.getChildren().setAll(newPane);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private void loadBorrowBooks() {
        previousSceneToReturn.previousFxmlFIle1 = previousSceneToReturn.previousFxmlFile2;
        loadScene("BorrowBook.fxml");
        previousSceneToReturn.previousFxmlFile2 = "BorrowBook.fxml";
    }

}


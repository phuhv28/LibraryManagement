package librarymanagement.gui.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.TableCell;
import librarymanagement.data.BorrowBook;

import java.util.List;
import java.util.ArrayList;


public class BorrowBookController {

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
    private TableColumn<BorrowBook, Void> readColumn;
    @FXML
    private TableColumn<BorrowBook, Void> returnColumn;
    @FXML
    private Label lbCheckEmpty;

    private ObservableList<BorrowBook> borrowBookList;

    @FXML
    public void initialize() {
        // Example list books to print scenes
        List<BorrowBook> listBook = new ArrayList<>();
        listBook.add(new BorrowBook(134, "helo", "2024-10-01"));
        listBook.add(new BorrowBook(146, "hi", "2024-10-02"));
        listBook.add(new BorrowBook(180, "xin chao", "2024-10-03"));

        ObservableList<BorrowBook> observableListBook = FXCollections.observableArrayList(listBook);
        tableView.setItems(observableListBook);

        if(listBook.isEmpty()) {
            tableView.setVisible(false);
            lbCheckEmpty.setVisible(true);
        }
        else{
            tableView.setVisible(true);
            lbCheckEmpty.setVisible(false);
        }

        sttColumn.setCellValueFactory(new PropertyValueFactory<>("stt"));
        documentIdColumn.setCellValueFactory(new PropertyValueFactory<>("documentId"));
        documentTitleColumn.setCellValueFactory(new PropertyValueFactory<>("documentTitle"));
        borrowDateColumn.setCellValueFactory(new PropertyValueFactory<>("borrowDate"));

        addReadButtonToTable();
        addReturnButtonToTable();

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

    private void handleFunction1(BorrowBook book) {
    }

    private void handleFunction2(BorrowBook book) {
    }

}


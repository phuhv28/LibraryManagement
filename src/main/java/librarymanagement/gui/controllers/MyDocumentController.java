package librarymanagement.gui.controllers;

import javafx.beans.property.SimpleStringProperty;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.TableCell;
import javafx.scene.layout.AnchorPane;
import librarymanagement.entity.BorrowRecord;
import librarymanagement.utils.SceneHistoryStack;
import librarymanagement.gui.viewmodels.MyDocumentViewModel;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Controller class for the "My Documents" scene, where users can view and manage their borrowed books.
 * This class provides functionality to return borrowed books, view document information, and navigate to other scenes.
 */
public class MyDocumentController {

    @FXML
    private AnchorPane MyDocument;

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

    @FXML
    private TableColumn<BorrowRecord, Void> returnCol;

    @FXML
    private Label lbNoti;

    @FXML
    private Button btBorrowBooks;

    private final MyDocumentViewModel viewModel = new MyDocumentViewModel();

    /**
     * Initializes the "My Documents" scene by binding the table view to the borrowed books data,
     * setting up cell factories for dates, adding a return button to the table, and handling empty state.
     */
    @FXML
    public void initialize() {
        tableView.setItems(viewModel.borrowedBooksProperty());

        recordIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        documentIdCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getDocument().getId()));
        documentTitleCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getDocument().getTitle()));
        borrowDateCol.setCellValueFactory(new PropertyValueFactory<>("borrowDate"));
        dueDateCol.setCellValueFactory(new PropertyValueFactory<>("dueDate"));

        setCellFactoryForDate(borrowDateCol);
        setCellFactoryForDate(dueDateCol);

        addReturnButtonToTable();

        btBorrowBooks.setOnAction(event -> loadBorrowBooks());

        // Handle no borrowed books state
        if (viewModel.borrowedBooksProperty() == null || viewModel.borrowedBooksProperty().isEmpty()) {
            lbNoti.setText("There are no books in your borrowed list at the moment.");
            lbNoti.setVisible(true);
            tableView.setVisible(false);
        }

        // Handle double-click event to view document info
        tableView.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2) {
                BorrowRecord selectedBook = tableView.getSelectionModel().getSelectedItem();

                if (selectedBook != null) {
                    UIController.showDocumentInfo(selectedBook.getDocument());
                }
            }
        });
    }

    /**
     * Adds a return button to the table for each borrowed book record.
     * When clicked, the corresponding book will be returned.
     */
    private void addReturnButtonToTable() {
        returnCol.setCellFactory(column -> new TableCell<>() {
            private final Button button = new Button("RETURN");

            {
                button.setOnAction(event -> {
                    BorrowRecord record = getTableView().getItems().get(getIndex());
                    handleReturn(record);
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

    /**
     * Sets a custom cell factory to format the date columns in the table view.
     * The date is formatted as "dd-MM-yyyy".
     *
     * @param col the column to apply the custom date format to.
     */
    private void setCellFactoryForDate(TableColumn<BorrowRecord, LocalDate> col) {
        col.setCellFactory(column -> new TableCell<>() {
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

    /**
     * Handles the return of a borrowed book.
     * It shows a loading popup while the return process is ongoing and updates the notification label upon success or failure.
     *
     * @param record the borrow record of the book to be returned.
     */
    private void handleReturn(BorrowRecord record) {
        LoadingPopupController loadingPopup = LoadingPopupController.newInstance("Returning document");
        loadingPopup.initOwnerStage(UIController.getPrimaryStage());
        loadingPopup.show();

        Task<Boolean> returnTask = new Task<>() {
            @Override
            protected Boolean call() {
                return viewModel.handleReturn(record);
            }
        };

        returnTask.setOnSucceeded(event -> {
            loadingPopup.close();
            lbNoti.setText("Returned successfully!");
            lbNoti.setVisible(true);
        });

        returnTask.setOnFailed(event -> {
            loadingPopup.close();
            lbNoti.setText("Error!");
            lbNoti.setVisible(true);
        });

        new Thread(returnTask).start();
    }

    /**
     * Loads the BorrowDocument scene by replacing the current content with the new scene.
     */
    private void loadScene() {
        try {
            AnchorPane newPane = FXMLLoader.load(getClass().getResource("/FXML/BorrowDocument.fxml"));
            MyDocument.getChildren().setAll(newPane);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Navigates to the BorrowDocument scene, saving the current scene into the history stack.
     */
    private void loadBorrowBooks() {
        SceneHistoryStack.listPreviousFxmlFile.push(SceneHistoryStack.previousFxmlFile);
        loadScene();
        SceneHistoryStack.previousFxmlFile = "BorrowDocument.fxml";
    }
}

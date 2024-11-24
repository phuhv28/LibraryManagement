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
import librarymanagement.data.BorrowRecord;
import librarymanagement.gui.utils.SceneHistoryStack;
import librarymanagement.gui.viewmodels.MyDocumentViewModel;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;


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
        if (viewModel.borrowedBooksProperty() == null || viewModel.borrowedBooksProperty().isEmpty()) {
            lbNoti.setText("There are no books in your borrowed list at the moment.");
            lbNoti.setVisible(true);
            tableView.setVisible(false);
        }
    }

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

    private void handleReturn(BorrowRecord record) {
        LoadingPopupController loadingPopup = LoadingPopupController.newInstance("Adding document");
        loadingPopup.initOwnerStage(UIController.getPrimaryStage());
        loadingPopup.show();

        Task<Boolean> returnTask = new Task<>() {
            @Override
            protected Boolean call() throws Exception {
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
        SceneHistoryStack.previousFxmlFile = "BorrowDocument.fxml";
    }

}


package librarymanagement.gui.controllers;


import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import librarymanagement.gui.viewmodels.BorrowDocumentViewModel;


public class BorrowDocumentController {

    @FXML
    private Button btBorrowDocument;

    @FXML
    private TextField tfDocumentID;

    @FXML
    private Label lbResult;

    private final BorrowDocumentViewModel viewModel = new BorrowDocumentViewModel();

    @FXML
    public void initialize() {
        tfDocumentID.textProperty().bindBidirectional(viewModel.idProperty());

        btBorrowDocument.setOnAction(event -> {
            handleBorrowDocument();
        });
    }

    private void handleBorrowDocument() {
        LoadingPopupController loadingPopup = LoadingPopupController.newInstance("Borrow Document");
        loadingPopup.initOwnerStage(UIController.getPrimaryStage());
        loadingPopup.show();

        Task<BorrowResult> borrowDocumentTask = new Task<>() {
            @Override
            protected BorrowResult call() {
                return viewModel.borrowDocument();
            }
        };

        borrowDocumentTask.setOnSucceeded(event -> {
            loadingPopup.close();
            lbResult.setText(borrowDocumentTask.getValue().getMessage());
            lbResult.setVisible(true);
        });

        new Thread(borrowDocumentTask).start();
    }
}


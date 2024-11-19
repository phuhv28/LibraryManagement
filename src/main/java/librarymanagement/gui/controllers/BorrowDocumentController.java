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

        Task<Boolean> borrowDocumentTask = new Task<>() {
            @Override
            protected Boolean call() throws Exception {
                return viewModel.borrowDocument();
            }
        };

        borrowDocumentTask.setOnSucceeded(event -> {
            loadingPopup.close();
            if (borrowDocumentTask.getValue()) {
                lbResult.setText("The document has been successfully borrowed!");
            } else {
                lbResult.setText("Sorry, this book is currently out of stock!");
            }

            lbResult.setVisible(true);
        });

        borrowDocumentTask.setOnFailed(event -> {
            loadingPopup.close();
            lbResult.setText("The document is not available!");
            lbResult.setVisible(true);
        });

        new Thread(borrowDocumentTask).start();
    }

    private void loadError() {
        lbResult.setVisible(true);
    }

}


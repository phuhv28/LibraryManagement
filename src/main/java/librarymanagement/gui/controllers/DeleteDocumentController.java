package librarymanagement.gui.controllers;

import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import librarymanagement.gui.viewmodels.DeleteDocumentViewModel;

public class DeleteDocumentController {

    @FXML
    private Button btDeleteDocument;

    @FXML
    private TextField tfDocumentID;

    @FXML
    private Label lbError;

    private final DeleteDocumentViewModel viewModel = new DeleteDocumentViewModel();

    @FXML
    public void initialize() {
        btDeleteDocument.setOnAction(event -> {
            handleDeleteDocument();
        });
        tfDocumentID.textProperty().bindBidirectional(viewModel.IDProperty());
        tfDocumentID.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                handleDeleteDocument();
            }
        });
    }

    private void handleDeleteDocument() {
        LoadingPopupController loadingPopup = LoadingPopupController.newInstance("Delete Document");
        loadingPopup.initOwnerStage(UIController.getPrimaryStage());
        loadingPopup.show();

        Task<Boolean> registerTask = new Task<>() {
            @Override
            protected Boolean call() {
                return viewModel.deleteDocument();
            }
        };

        registerTask.setOnSucceeded(event -> {
            loadingPopup.close();
            if (!registerTask.getValue()) {
                lbError.setText("Document not found!");
            } else {
                lbError.setText("Delete successfully!");
            }
            lbError.setVisible(true);
        });

        new Thread(registerTask).start();
    }
}


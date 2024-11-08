package librarymanagement.gui.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import librarymanagement.gui.viewmodels.DeleteDocumentViewModel;

public class DeleteController {

    @FXML
    private Button btDeleteDocument;

    @FXML
    private TextField tfDocumentID;

    @FXML
    private Label lbError;

    private final DeleteDocumentViewModel viewModel = new DeleteDocumentViewModel();

    @FXML
    public void initialize() {
        btDeleteDocument.setOnAction(event -> {handleDeleteDocument();});
        tfDocumentID.textProperty().bindBidirectional(viewModel.IDProperty());
    }

    private void handleDeleteDocument() {
        if (viewModel.checkIdDocument()) {
            lbError.setVisible(false);
            viewModel.deleteDocument();
            tfDocumentID.clear();
        }
        else {
            lbError.setVisible(true);
            tfDocumentID.clear();
        }
    }
}


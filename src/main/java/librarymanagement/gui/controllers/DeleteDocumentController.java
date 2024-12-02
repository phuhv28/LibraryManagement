package librarymanagement.gui.controllers;

import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import librarymanagement.gui.viewmodels.DeleteDocumentViewModel;

/**
 * The `DeleteDocumentController` class manages the user interface for deleting documents.
 * It interacts with the `DeleteDocumentViewModel` to handle user input and perform the deletion operation.
 */
public class DeleteDocumentController {

    @FXML
    private Button btDeleteDocument;

    @FXML
    private TextField tfDocumentID;

    @FXML
    private Label lbError;

    private final DeleteDocumentViewModel viewModel = new DeleteDocumentViewModel();

    /**
     * Initializes the controller.
     * <p>
     * Binds the `TextField` for document ID to the view model and sets up event handlers
     * for the "Delete Document" button and pressing Enter in the text field.
     * </p>
     */
    @FXML
    public void initialize() {
        // Bind the text field to the view model property
        tfDocumentID.textProperty().bindBidirectional(viewModel.IDProperty());

        // Set an event handler for the "Delete Document" button
        btDeleteDocument.setOnAction(_ -> handleDeleteDocument());

        // Allow pressing Enter in the text field to trigger the delete operation
        tfDocumentID.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                handleDeleteDocument();
            }
        });
    }

    /**
     * Handles the document deletion process.
     * <p>
     * Displays a loading popup while the deletion is being processed in the background.
     * Updates the result label with a success or failure message once the task completes.
     * </p>
     */
    private void handleDeleteDocument() {
        // Show a loading popup
        LoadingPopupController loadingPopup = LoadingPopupController.newInstance("Delete Document");
        loadingPopup.initOwnerStage(UIController.getPrimaryStage());
        loadingPopup.show();

        // Create a background task to perform the delete operation
        Task<Boolean> deleteTask = new Task<>() {
            @Override
            protected Boolean call() {
                return viewModel.deleteDocument();
            }
        };

        // Handle the completion of the delete task
        deleteTask.setOnSucceeded(_ -> {
            loadingPopup.close();
            if (!deleteTask.getValue()) {
                lbError.setText("Document not found!");
            } else {
                lbError.setText("Delete successfully!");
            }
            lbError.setVisible(true);
        });

        // Start the task in a new thread
        new Thread(deleteTask).start();
    }
}

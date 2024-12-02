package librarymanagement.gui.controllers;

import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import librarymanagement.gui.viewmodels.BorrowDocumentViewModel;

/**
 * The `BorrowDocumentController` class manages the user interface for borrowing a document.
 * It connects the view (FXML components) with the `BorrowDocumentViewModel`,
 * handles user actions, and updates the UI based on the borrow result.
 */
public class BorrowDocumentController {

    @FXML
    private Button btBorrowDocument;

    @FXML
    private TextField tfDocumentID;

    @FXML
    private Label lbResult;

    private final BorrowDocumentViewModel viewModel = new BorrowDocumentViewModel();

    /**
     * Initializes the controller.
     * <p>
     * Sets up bindings between the UI components and the `BorrowDocumentViewModel`.
     * Assigns event handlers to manage borrowing actions.
     * </p>
     */
    @FXML
    public void initialize() {
        // Bind the text property of the document ID text field to the view model's property
        tfDocumentID.textProperty().bindBidirectional(viewModel.idProperty());

        // Set up the borrow document button action
        btBorrowDocument.setOnAction(_ -> handleBorrowDocument());
    }

    /**
     * Handles the document borrowing process.
     * <p>
     * Displays a loading popup while the borrowing operation is performed asynchronously.
     * Upon completion, updates the result label to show the outcome of the operation.
     * </p>
     */
    private void handleBorrowDocument() {
        // Display a loading popup
        LoadingPopupController loadingPopup = LoadingPopupController.newInstance("Borrow Document");
        loadingPopup.initOwnerStage(UIController.getPrimaryStage());
        loadingPopup.show();

        // Create a background task to perform the borrowing operation
        Task<BorrowResult> borrowDocumentTask = new Task<>() {
            @Override
            protected BorrowResult call() {
                // Perform the borrowing operation using the view model
                return viewModel.borrowDocument();
            }
        };

        // Define behavior when the task completes successfully
        borrowDocumentTask.setOnSucceeded(_ -> {
            // Close the loading popup
            loadingPopup.close();

            // Display the result message in the label
            lbResult.setText(borrowDocumentTask.getValue().getMessage());
            lbResult.setVisible(true);
        });

        // Start the task in a new thread
        new Thread(borrowDocumentTask).start();
    }
}

package librarymanagement.gui.controllers;

import javafx.beans.binding.Bindings;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.util.converter.NumberStringConverter;
import librarymanagement.gui.viewmodels.EditDocumentViewModel;

/**
 * Controller class for editing document information in the library management system.
 * This class manages user interactions for editing document details such as ISBN, title,
 * author, publisher, publication date, category, description, available copies, and page count.
 */
public class EditDocumentController {

    @FXML
    private Label lbISBN;
    @FXML
    private Label lbTitle;
    @FXML
    private Label lbPublisher;
    @FXML
    private Label lbAuthor;
    @FXML
    private Label lbPublicationDate;
    @FXML
    private Label lbCategory;
    @FXML
    private Label lbDescription;
    @FXML
    private Label lbAvailableCopies;
    @FXML
    private Label lbPageCount;
    @FXML
    private TextField tfISBN;
    @FXML
    private TextField tfTitle;
    @FXML
    private TextField tfPublisher;
    @FXML
    private TextField tfDescription;
    @FXML
    private TextField tfAvailableCopies;
    @FXML
    private TextField tfAuthor;
    @FXML
    private TextField tfCategory;
    @FXML
    private TextField tfPageCount;
    @FXML
    private DatePicker dpPublicationDate;
    @FXML
    private Button btEnter;
    @FXML
    private Label lbResult;
    @FXML
    private TextField tfDocumentID;
    @FXML
    private Button btSave;

    private final EditDocumentViewModel viewModel = new EditDocumentViewModel();

    /**
     * Initializes the controller by setting up bindings and event handlers.
     * Binds the fields in the UI to properties in the `viewModel` and sets up actions
     * for the buttons to search and save document details.
     */
    public void initialize() {
        btEnter.setOnAction(_ -> showFieldsToEdit());

        tfDocumentID.textProperty().bindBidirectional(viewModel.idProperty());
        tfISBN.textProperty().bindBidirectional(viewModel.isbnProperty());
        tfTitle.textProperty().bindBidirectional(viewModel.titleProperty());
        tfPublisher.textProperty().bindBidirectional(viewModel.publisherProperty());
        tfAuthor.textProperty().bindBidirectional(viewModel.authorProperty());
        dpPublicationDate.valueProperty().bindBidirectional(viewModel.publishDateProperty());
        tfCategory.textProperty().bindBidirectional(viewModel.categoryProperty());
        tfDescription.textProperty().bindBidirectional(viewModel.descriptionProperty());
        Bindings.bindBidirectional(
                tfAvailableCopies.textProperty(),
                viewModel.availableCopiesProperty(),
                new NumberStringConverter()
        );
        Bindings.bindBidirectional(
                tfPageCount.textProperty(),
                viewModel.pageCountProperty(),
                new NumberStringConverter()
        );

        tfDocumentID.textProperty().addListener(_ -> clear());

        btSave.setOnAction(_ -> save());
    }

    /**
     * Clears all input fields in the form.
     * Resets the text fields, date picker, and result label.
     */
    private void clear() {
        tfISBN.clear();
        tfTitle.clear();
        tfPublisher.clear();
        tfAuthor.clear();
        dpPublicationDate.setValue(null);
        tfCategory.clear();
        tfDescription.clear();
        tfAvailableCopies.clear();
        tfPageCount.clear();
    }

    /**
     * Makes the fields visible for user input when editing a document.
     * This includes text fields for ISBN, title, publisher, author, and others.
     */
    private void showFields() {
        tfISBN.setVisible(true);
        tfTitle.setVisible(true);
        tfPublisher.setVisible(true);
        tfAuthor.setVisible(true);
        dpPublicationDate.setVisible(true);
        tfCategory.setVisible(true);
        tfDescription.setVisible(true);
        tfAvailableCopies.setVisible(true);
        tfPageCount.setVisible(true);
        lbISBN.setVisible(true);
        lbTitle.setVisible(true);
        lbAuthor.setVisible(true);
        lbPublisher.setVisible(true);
        lbPublicationDate.setVisible(true);
        lbCategory.setVisible(true);
        lbDescription.setVisible(true);
        lbAvailableCopies.setVisible(true);
        lbPageCount.setVisible(true);
        btSave.setVisible(true);
    }

    /**
     * Searches for a document using the provided document ID and populates the fields
     * with the document's existing details if found. Shows a loading popup while
     * performing the search.
     */
    private void showFieldsToEdit() {
        LoadingPopupController loadingPopup = LoadingPopupController.newInstance("Search for document");
        loadingPopup.initOwnerStage(UIController.getPrimaryStage());
        loadingPopup.show();

        Task<Boolean> searchDocumentToEditTask = new Task<>() {
            @Override
            protected Boolean call() {
                return viewModel.getDocumentFromId();
            }
        };

        searchDocumentToEditTask.setOnSucceeded(event -> {
            loadingPopup.close();
            if (!searchDocumentToEditTask.getValue()) {
                lbResult.setVisible(true);
                lbResult.setText("Document not found!");
            } else {
                if (lbResult.isVisible()) {
                    lbResult.setVisible(false);
                }
                showFields();
            }
        });

        new Thread(searchDocumentToEditTask).start();
    }

    /**
     * Saves the updated document details to the database. If the required fields
     * (title and author) are not filled, a message is shown. A loading popup is displayed
     * during the saving process.
     */
    private void save() {
        if (tfTitle.getText() == null || tfTitle.getText().isEmpty()
                || tfAuthor.getText() == null || tfAuthor.getText().isEmpty()) {
            lbResult.setText("Title and Author are required!");
            lbResult.setVisible(true);
            return;
        }

        LoadingPopupController loadingPopup = LoadingPopupController.newInstance("Edit Document");
        loadingPopup.initOwnerStage(UIController.getPrimaryStage());
        loadingPopup.show();

        Task<Void> registerTask = new Task<>() {
            @Override
            protected Void call() {
                viewModel.save();
                return null;
            }
        };

        registerTask.setOnSucceeded(event -> {
            loadingPopup.close();
            lbResult.setText("Document saved!");
            lbResult.setVisible(true);
        });

        new Thread(registerTask).start();
    }
}

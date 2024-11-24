package librarymanagement.gui.controllers;


import javafx.beans.binding.Bindings;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.util.converter.NumberStringConverter;
import librarymanagement.gui.viewmodels.AddDocumentViewModel;
import org.jetbrains.annotations.NotNull;


public class AddDocumentController {
    @FXML
    private Button btFillUsingISBN;

    @FXML
    private Button btAddDocument;

    @FXML
    private TextField tfISBN;

    @FXML
    private TextField tfTitle;

    @FXML
    private TextField tfAuthor;

    @FXML
    private TextField tfPublisher;

    @FXML
    private DatePicker dpPublicationDate;

    @FXML
    private TextField tfCategory;

    @FXML
    private TextField tfAvailableCopies;

    @FXML
    private TextField tfDescription;

    @FXML
    private Label lbResult;

    @FXML
    private TextField tfPageCount;

    private final AddDocumentViewModel viewModel = new AddDocumentViewModel();

    @FXML
    public void initialize() {
        btAddDocument.setOnAction(_ -> handleAddDocument());

        btFillUsingISBN.setOnAction(_ -> fillUsingISBN());

        tfISBN.textProperty().bindBidirectional(viewModel.ISBNProperty());
        tfTitle.textProperty().bindBidirectional(viewModel.titleProperty());
        tfPublisher.textProperty().bindBidirectional(viewModel.publisherProperty());
        tfAuthor.textProperty().bindBidirectional(viewModel.authorProperty());
        tfCategory.textProperty().bindBidirectional(viewModel.categoryProperty());
        dpPublicationDate.valueProperty().bindBidirectional(viewModel.publicationDateProperty());
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
    }

    private void fillUsingISBN() {
        LoadingPopupController loadingPopup = LoadingPopupController.newInstance("Searching for Document");
        loadingPopup.initOwnerStage(UIController.getPrimaryStage());
        loadingPopup.show();

        Task<Boolean> fillDocumentTask = new Task<>() {
            @Override
            protected Boolean call() {
                return viewModel.fillUsingISBN();
            }
        };

        fillDocumentTask.setOnSucceeded(_ -> {
            loadingPopup.close();
            if (lbResult.isVisible()) {
                lbResult.setVisible(false);
            }
        });

        fillDocumentTask.setOnFailed(_ -> {
            loadingPopup.close();
            lbResult.setText("The ISBN is invalid!");
            lbResult.setVisible(true);
        });



        new Thread(fillDocumentTask).start();
    }

    private void handleAddDocument() {
        if (tfTitle.getText() == null || tfTitle.getText().isEmpty()
        || tfAuthor.getText() == null || tfAuthor.getText().isEmpty()) {
            lbResult.setText("Title and Author are required!");
            lbResult.setVisible(true);
            return;
        }

        LoadingPopupController loadingPopup = LoadingPopupController.newInstance("Adding document");
        loadingPopup.initOwnerStage(UIController.getPrimaryStage());
        loadingPopup.show();

        Task<Boolean> addDocumentTask = getBooleanTask(loadingPopup);

        new Thread(addDocumentTask).start();
    }

    @NotNull
    private Task<Boolean> getBooleanTask(LoadingPopupController loadingPopup) {
        Task<Boolean> addDocumentTask = new Task<>() {
            @Override
            protected Boolean call() {
                return viewModel.addDocument();
            }
        };

        addDocumentTask.setOnSucceeded(_ -> {
            loadingPopup.close();
            lbResult.setVisible(true);
            if (addDocumentTask.getValue()) {
                lbResult.setText("Document added successfully!");
            } else {
                lbResult.setText("The document already exists!");
            }
        });
        return addDocumentTask;
    }
}


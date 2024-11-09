package librarymanagement.gui.controllers;


import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.util.converter.NumberStringConverter;
import librarymanagement.gui.viewmodels.AddDocumentViewModel;


public class AddDocumentController {
    @FXML
    private AnchorPane addDocumentPane;

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
    private Label lbError;

    @FXML
    private TextField tfPageCount;

    private final AddDocumentViewModel viewModel = new AddDocumentViewModel();

    @FXML
    public void initialize() {
        btAddDocument.setOnAction(e -> {
            handleAddDocument();
        });

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

    private void handleAddDocument() {
        viewModel.addDocument();
    }

    private void loadError() {
        lbError.setVisible(true);
    }

    private void clear() {
        tfISBN.clear();
        tfTitle.clear();
        tfAuthor.clear();
        tfPublisher.clear();
        dpPublicationDate.setValue(null);
        tfCategory.clear();
        tfAvailableCopies.clear();
        tfDescription.clear();
        tfPageCount.clear();
    }
}


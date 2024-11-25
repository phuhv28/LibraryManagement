package librarymanagement.gui.controllers;


import javafx.beans.binding.Bindings;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.util.converter.NumberStringConverter;
import librarymanagement.gui.viewmodels.EditDocumentViewModel;

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


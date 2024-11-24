package librarymanagement.gui.controllers;


import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import librarymanagement.gui.viewmodels.EditDocumentViewModel;

public class EditDocumentController {
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
    private Label lbError;

    @FXML
    private TextField tfDocumentID;

    @FXML
    private TextField tfEditedField;

    @FXML
    private Button btSave;

    private final EditDocumentViewModel viewModel = new EditDocumentViewModel();

    public void initialize() {
    }

    private void handleEditDocument() {

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
        });

        new Thread(registerTask).start();
    }
}


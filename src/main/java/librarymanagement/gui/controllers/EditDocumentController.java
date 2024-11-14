package librarymanagement.gui.controllers;


import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import librarymanagement.gui.viewmodels.EditDocumentViewModel;

import java.io.IOException;


public class EditDocumentController {

    @FXML
    private MenuItem miCategory;

    @FXML
    private MenuItem miPublicationDate;

    @FXML
    private MenuItem miDescription;

    @FXML
    private MenuItem miAvailableCopies;

    @FXML
    private MenuItem miPageCount;

    @FXML
    private MenuItem miISBN;

    @FXML
    private MenuItem miTitle;

    @FXML
    private MenuItem miPublisher;

    @FXML
    private MenuItem miAuthor;

    @FXML
    private TextField tfDocumentID;

    @FXML
    private TextField tfEditedField;

    @FXML
    private Label lbFieldToEdit;

    @FXML
    private Button btSave;

    @FXML
    private MenuButton mbSelectedField;

    private final EditDocumentViewModel viewModel = new EditDocumentViewModel();

    public void initialize() {
        btSave.setOnAction(event -> {
            handleEditDocument();
        });

        tfDocumentID.textProperty().bindBidirectional(viewModel.documentIDProperty());
        tfEditedField.textProperty().bindBidirectional(viewModel.editedFieldProperty());


        for (MenuItem menuItem : mbSelectedField.getItems()) {
            setupMenuItemAction(menuItem);
        }

        tfEditedField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (oldValue != null) {
                btSave.setVisible(true);
            }
        });
    }

    private void setupMenuItemAction(MenuItem menuItem) {
        menuItem.setOnAction(event -> {
            lbFieldToEdit.setText(menuItem.getText() + " :");
            tfEditedField.setVisible(true);
            mbSelectedField.setText(menuItem.getText());
            lbFieldToEdit.setVisible(true);
            viewModel.selectedAttributeProperty().set(menuItem.getText());
        });
    }



    private void handleEditDocument() {

        LoadingPopupController loadingPopup = LoadingPopupController.newInstance("Edit Document");
        loadingPopup.initOwnerStage(UIController.getPrimaryStage());
        loadingPopup.show();

        Task<Void> registerTask = new Task<>() {
            @Override
            protected Void call() throws Exception {
                viewModel.save();
                return null;
            }
        };

        registerTask.setOnSucceeded(event -> {
            loadingPopup.close();
        });

        new Thread(registerTask).start();
    }

    private void loadError() {
//        lbError.setVisible(true);
    }
}


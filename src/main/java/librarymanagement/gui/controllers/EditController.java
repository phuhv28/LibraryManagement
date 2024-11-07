package librarymanagement.gui.controllers;


import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import java.io.IOException;



public class EditController {

    @FXML
    private AnchorPane EditScene;
    @FXML
    private Button btEditDocument;
    @FXML
    private MenuButton mbFilter;

    @FXML
    private MenuItem mbEditTitle;

    @FXML
    private MenuItem mbEditAuthor;

    @FXML
    private MenuItem mbEditPublisher;

    @FXML
    private MenuItem mbEditPublicationYear;

    @FXML
    private MenuItem mbEditTotalQuantity;

    @FXML
    private TextField tfDocumentID;

    @FXML
    private TextField tfInformation;

    @FXML
    private Label lbError;



    @FXML
    public void initialize() {
        btEditDocument.setOnAction(event -> {handleEditDocument();});
    }




    private void loadScene(String fxmlFile) {
        try {
            AnchorPane newPane = FXMLLoader.load(getClass().getResource("/FXML/" + fxmlFile));
            EditScene.getChildren().setAll(newPane);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void handleEditDocument() {
        tfDocumentID.requestFocus();
    }

    private void loadError(){
        lbError.setVisible(true);
    }
}


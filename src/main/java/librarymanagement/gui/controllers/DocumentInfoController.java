package librarymanagement.gui.controllers;

import javafx.beans.Observable;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;

import javafx.scene.image.ImageView;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import librarymanagement.data.Document;

public class DocumentInfoController {
    @FXML
    private AnchorPane informationDocument;

    @FXML
    private Label lbID;

    @FXML
    private Label lbTitle;

    @FXML
    private Label lbAuthor;

    @FXML
    private Label lbPublisher;

    @FXML
    private Label lbPublicationDate;

    @FXML
    private Label lbISBN;

    @FXML
    private Label lbCategories;

    @FXML
    private Label lbPageCount;

    @FXML
    private Label lbAverageRating;

    @FXML
    private Label lbRatingCount;

    @FXML
    private Label lbDescription;

    @FXML
    private TextArea taReview;

    @FXML
    private ListView<String> lvComments;

    @FXML
    private Button btOneStar;

    @FXML
    private Button bt2Star;

    @FXML
    private Button bt3Star;

    @FXML
    private Button bt4Star;

    @FXML
    private Button btFullStar;

    @FXML
    private ImageView image_OneStar;

    @FXML
    private ImageView image_Star2;

    @FXML
    private ImageView image_Star3;

    @FXML
    private ImageView image_Star4;

    @FXML
    private ImageView image_FullStar;

    private final Image imageLast = new Image(getClass().getResource("/images/image_starLast.png").toExternalForm());

    private final Image imageFirst = new Image(getClass().getResource("/images/image_starFirst.png").toExternalForm());

    private static Stage stage;

    @FXML
    public void initialize() {
        btOneStar.setOnAction(event -> handleRating1Star());
        bt2Star.setOnAction(event -> handleRating2Star());
        bt3Star.setOnAction(event -> handleRating3Star());
        bt4Star.setOnAction(event -> handleRating4Star());
        btFullStar.setOnAction(event -> handleRating5Star());
    }

    public static void showDocumentInfo(Document _document) {
        FXMLLoader loader = new FXMLLoader(DocumentInfoController.class.getResource("/FXML/DocumentInfo.fxml"));
        try {
            Parent root = loader.load();
            DocumentInfoController controller = loader.getController();
            Scene scene = new Scene(root);
            stage = new Stage();
            stage.setScene(scene);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void handleRating1Star() {
        image_OneStar.setImage(imageLast);
        image_Star2.setImage(imageFirst);
        image_Star3.setImage(imageFirst);
        image_Star4.setImage(imageFirst);
        image_FullStar.setImage(imageFirst);

    }

    private void handleRating2Star() {
        image_OneStar.setImage(imageLast);
        image_Star2.setImage(imageLast);
        image_Star3.setImage(imageFirst);
        image_Star4.setImage(imageFirst);
        image_FullStar.setImage(imageFirst);
    }

    private void handleRating3Star() {
        image_OneStar.setImage(imageLast);
        image_Star2.setImage(imageLast);
        image_Star3.setImage(imageLast);
        image_Star4.setImage(imageFirst);
        image_FullStar.setImage(imageFirst);
    }

    private void handleRating4Star() {
        image_OneStar.setImage(imageLast);
        image_Star2.setImage(imageLast);
        image_Star3.setImage(imageLast);
        image_Star4.setImage(imageLast);
        image_FullStar.setImage(imageFirst);
    }

    private void handleRating5Star() {
        image_OneStar.setImage(imageLast);
        image_Star2.setImage(imageLast);
        image_Star3.setImage(imageLast);
        image_Star4.setImage(imageLast);
        image_FullStar.setImage(imageLast);
    }

}

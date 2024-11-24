package librarymanagement.gui.controllers;

import javafx.application.Platform;
import javafx.beans.Observable;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;

import javafx.scene.image.ImageView;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;
import librarymanagement.data.Book;
import librarymanagement.data.Document;

import javafx.scene.input.MouseEvent;
import java.awt.font.ImageGraphicAttribute;

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
    private ScrollPane spScroll;

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
    private ImageView ivImageBook;

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

    private final Image imagePublic = new Image(getClass().getResource("/images/image_bookPublic.png").toExternalForm());

    private Document document;

    private Stage stage;

    private static DocumentInfoController instance;

    @FXML
    public void initialize() {
        btOneStar.setOnAction(event -> handleRating1Star());
        bt2Star.setOnAction(event -> handleRating2Star());
        bt3Star.setOnAction(event -> handleRating3Star());
        bt4Star.setOnAction(event -> handleRating4Star());
        btFullStar.setOnAction(event -> handleRating5Star());
        spScroll.setOnMouseClicked(MouseEvent -> {informationDocument.requestFocus();});
    }

    public static DocumentInfoController newInstance(Document document) {
        FXMLLoader loader = new FXMLLoader(LoadingPopupController.class.getResource("/FXML/DocumentInfo.fxml"));
        try {
            Parent parent = loader.load();
            Scene scene = new Scene(parent);
            Stage stage = new Stage();
            stage.setTitle("Document Information");
            stage.setScene(scene);
            stage.setResizable(false);
            stage.initModality(Modality.WINDOW_MODAL);

            instance = loader.getController();
            instance.setStage(stage);


        } catch (Exception e) {
            e.printStackTrace();
            instance = null;
        }

        instance.setDocument(document);
        return instance;
    }

    private void setDocument(Document document) {
        this.document = document;
    }

    public void show() {
        if (document != null) {
            if (document instanceof Book book) {
                if(book.getThumbnailImage() == null){
                    ivImageBook.setImage(imagePublic);
                }
                else {
                    ivImageBook.setImage(new Image(book.getThumbnailImage()));
                }
                lbID.setText((book).getId());
                lbTitle.setText(book.getTitle());
                lbAuthor.setText(book.getAuthor());
                lbPublisher.setText(book.getPublisher());
                lbPublicationDate.setText(book.getPublishedDate().toString());
                lbISBN.setText(book.getISBN());
                lbCategories.setText(book.getCategories());
                lbPageCount.setText(String.valueOf(book.getPageCount()));
                lbAverageRating.setText(String.valueOf(book.getAverageRating()));
                lbRatingCount.setText(String.valueOf(book.getRatingsCount()));
                lbDescription.setText(book.getDescription());
            }
        }
        stage.show();
    }

    public void close() {
        if (stage != null) {
            stage.close();
        }
    }

    private void setStage(Stage stage) {
        this.stage = stage;
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

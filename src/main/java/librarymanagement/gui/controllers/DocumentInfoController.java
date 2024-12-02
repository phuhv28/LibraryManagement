package librarymanagement.gui.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;

import javafx.scene.image.ImageView;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;
import librarymanagement.entity.*;
import librarymanagement.gui.viewmodels.DocumentInfoViewModel;

import java.util.List;

/**
 * Controller for displaying detailed information about a document, including reviews, ratings,
 * and functionality to borrow or return the document.
 */
public class DocumentInfoController {

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
    private Label lbRatingCount;

    @FXML
    private Label lbDescription;

    @FXML
    private TextArea taReview;

    @FXML
    private VBox vbReviewDocument;

    @FXML
    private AnchorPane apScroll;

    @FXML
    private Button btReview;

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

    @FXML
    private ImageView imageRatingDocument1;

    @FXML
    private ImageView imageRatingDocument2;

    @FXML
    private ImageView imageRatingDocument3;

    @FXML
    private ImageView imageRatingDocument4;

    @FXML
    private ImageView imageRatingDocument5;

    @FXML
    private Button btFunction;

    @FXML
    private Label textAuthor;

    @FXML
    private Label textISBN;

    @FXML
    private Label lbTypeDocument;

    @FXML
    private AnchorPane apScrollDescription;

    private final Image imageLast = new Image(getClass().getResource("/images/image_starLast.png").toExternalForm());

    private final Image imageFirst = new Image(getClass().getResource("/images/image_starFirst.png").toExternalForm());

    private final Image imagePublic = new Image(getClass().getResource("/images/image_bookPublic.png").toExternalForm());

    private final Image imageUser1 = new Image(getClass().getResource("/images/image1.png").toExternalForm());

    private final Image imageUser2 = new Image(getClass().getResource("/images/image2.png").toExternalForm());

    private  DocumentInfoViewModel documentInfoViewModel;


    private Document document;

    private Stage stage;

    private int countStarRating = 0;

    /**
     * Initializes the controller, setting up event handlers for rating buttons, review submission,
     * and borrowing/returning the document.
     */
    @FXML
    public void initialize() {
        btOneStar.setOnAction(event -> {
            setRatingImage(1, image_OneStar, image_Star2, image_Star3, image_Star4, image_FullStar);
            countStarRating = 1;
        });
        bt2Star.setOnAction(event -> {
            setRatingImage(2, image_OneStar, image_Star2, image_Star3, image_Star4, image_FullStar);
            countStarRating = 2;
        });
        bt3Star.setOnAction(event -> {
            setRatingImage(3, image_OneStar, image_Star2, image_Star3, image_Star4, image_FullStar);
            countStarRating = 3;
        });
        bt4Star.setOnAction(event -> {
            setRatingImage(4, image_OneStar, image_Star2, image_Star3, image_Star4, image_FullStar);
            countStarRating = 4;
        });
        btFullStar.setOnAction(event -> {
            setRatingImage(5, image_OneStar, image_Star2, image_Star3, image_Star4, image_FullStar);
            countStarRating = 5;
        });
        btFunction.setOnAction(actionEvent -> handleFunction());
        taReview.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                {
                    handleReview();
                }
            }
        });
        btReview.setOnAction(actionEvent -> handleReview());
    }

    /**
     * Handles the review submission process, including saving the review to the database,
     * resetting the review input field, and updating the review list.
     */
    private void handleReview() {
        String inputText = taReview.getText();

        documentInfoViewModel.getReviewService().addReview(documentInfoViewModel.getAccount().getUsername(), document.getId(), countStarRating, inputText);

        setRatingImage(0, image_OneStar, image_Star2, image_Star3, image_Star4, image_FullStar);
        countStarRating = 0;
        taReview.clear();

        show();
    }

    /**
     * Handles the borrowing or returning of the document, toggling the function button text
     * between "BORROW" and "RETURN".
     */
    private void handleFunction() {
        if (btFunction.getText().equals("RETURN")) {
            btFunction.setText("BORROW");
            documentInfoViewModel.functionReturn(documentInfoViewModel.getRecordIdOfBorrowedDocument(document.getId()));
        } else if (btFunction.getText().equals("BORROW")) {
            documentInfoViewModel.functionBorrow(document.getId());
            btFunction.setText("RETURN");
        }
    }

    /**
     * Creates a new instance of the DocumentInfoController with the provided document details.
     * @param document The document to display.
     * @return A new instance of DocumentInfoController.
     */
    public static DocumentInfoController newInstance(Document document) {
        FXMLLoader loader = new FXMLLoader(LoadingPopupController.class.getResource("/FXML/DocumentInfo.fxml"));
        DocumentInfoController instance;
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

    /**
     * Displays the document information in the UI and updates the stage.
     */
    public void show() {
        if (document != null) {
            setRatingImage((int) document.getAverageRating(), imageRatingDocument1, imageRatingDocument2, imageRatingDocument3, imageRatingDocument4, imageRatingDocument5);
            lbID.setText((document).getId());
            lbTitle.setText(document.getTitle());
            lbPageCount.setText(String.valueOf(document.getPageCount()));
            lbRatingCount.setText(String.valueOf(document.getRatingsCount()));
            lbPublisher.setText(document.getPublisher());
            if (document.getPublishedDate() == null) {
                lbPublicationDate.setText(" ");
            } else {
                lbPublicationDate.setText(document.getPublishedDate().toString());
            }

            if (document instanceof Book book) {
                documentInfoViewModel = new DocumentInfoViewModel(DocumentType.BOOK);
                setListReview(vbReviewDocument, documentInfoViewModel.getReviewService().getAllReviewsInDocument(document.getId()));
                if (documentInfoViewModel.getBorrowingService().checkIfUserHasBorrowedDocument(documentInfoViewModel.getAccount().getId(), document.getId())) {
                    btFunction.setText("RETURN");
                } else {
                    btFunction.setText("BORROW");
                }
                lbTypeDocument.setText(" BOOK");
                if (book.getThumbnailImage() == null) {
                    ivImageBook.setImage(imagePublic);
                } else {
                    ivImageBook.setImage(new Image(book.getThumbnailImage()));
                }
                lbAuthor.setText(book.getAuthor());
                lbAuthor.setVisible(true);
                textAuthor.setVisible(true);
                lbDescription.setText(book.getDescription());
                lbDescription.setVisible(true);
                resizeAnPane();
                lbISBN.setText(book.getISBN());
                textISBN.setText("ISBN :");
                lbCategories.setText(book.getCategories());
            }
            if (document instanceof Magazine magazine) {
                documentInfoViewModel = new DocumentInfoViewModel(DocumentType.MAGAZINE);
                setListReview(vbReviewDocument, documentInfoViewModel.getReviewService().getAllReviewsInDocument(document.getId()));
                if (documentInfoViewModel.getBorrowingService().checkIfUserHasBorrowedDocument(documentInfoViewModel.getAccount().getId(), document.getId())) {
                    btFunction.setText("RETURN");
                } else {
                    btFunction.setText("BORROW");
                }
                lbTypeDocument.setText(" MAGAZINE");
                if (magazine.getThumbnailImage() == null) {
                    ivImageBook.setImage(imagePublic);
                } else {
                    ivImageBook.setImage(new Image(magazine.getThumbnailImage()));
                }
                lbAuthor.setVisible(false);
                textAuthor.setVisible(false);
                lbDescription.setVisible(false);
                textISBN.setText("ISSN :");
                lbISBN.setText(magazine.getISSN());
                lbCategories.setText(magazine.getCategories());
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

    /**
     * Updates the star rating images based on the given rating value.
     * @param Rating The rating value (1-5).
     * @param image1 First star image.
     * @param image2 Second star image.
     * @param image3 Third star image.
     * @param image4 Fourth star image.
     * @param image5 Fifth star image.
     */
    public void setRatingImage(int Rating, ImageView image1, ImageView image2, ImageView image3, ImageView image4, ImageView image5) {
        if (Rating == 0) {
            image1.setImage(imageFirst);
            image2.setImage(imageFirst);
            image3.setImage(imageFirst);
            image4.setImage(imageFirst);
            image5.setImage(imageFirst);
        }
        if (Rating == 1) {
            image1.setImage(imageLast);
            image2.setImage(imageFirst);
            image3.setImage(imageFirst);
            image4.setImage(imageFirst);
            image5.setImage(imageFirst);

        }
        if (Rating == 2) {
            image1.setImage(imageLast);
            image2.setImage(imageLast);
            image3.setImage(imageFirst);
            image4.setImage(imageFirst);
            image5.setImage(imageFirst);
        }
        if (Rating == 3) {
            image1.setImage(imageLast);
            image2.setImage(imageLast);
            image3.setImage(imageLast);
            image4.setImage(imageFirst);
            image5.setImage(imageFirst);
        }
        if (Rating == 4) {
            image1.setImage(imageLast);
            image2.setImage(imageLast);
            image3.setImage(imageLast);
            image4.setImage(imageLast);
            image5.setImage(imageFirst);
        }
        if (Rating == 5) {
            image1.setImage(imageLast);
            image2.setImage(imageLast);
            image3.setImage(imageLast);
            image4.setImage(imageLast);
            image5.setImage(imageLast);
        }
    }

    /**
     * Creates and returns a UI component representing an individual review.
     *
     * This method constructs an HBox that contains the reviewer's profile image, their name,
     * their star rating (as ImageViews), and the review text in a non-editable TextArea.
     * The stars are displayed based on the rating provided (1 to 5 stars), and the layout
     * alternates the profile image for every other review based on the index provided.
     *
     * @param name The name of the reviewer.
     * @param review The text of the review written by the reviewer.
     * @param rating The rating given by the reviewer, from 1 to 5 stars.
     * @param index The index of the review, used to alternate profile images between two options.
     *
     * @return HBox A layout containing the profile image, name, star ratings, and review text.
     */
    public HBox setEachReview(String name, String review, int rating, int index) {
        HBox currentHbox = new HBox(20);

        ImageView imageAccount;
        if (index % 2 == 0) {
            imageAccount = new ImageView(imageUser1);
        } else {
            imageAccount = new ImageView(imageUser2);
        }
        imageAccount.setFitWidth(50);
        imageAccount.setFitHeight(50);

        VBox rightHbox = new VBox(5);

        HBox topVbox = new HBox(5);

        Label nameOfAccount = new Label(name);
        nameOfAccount.setTextFill(Color.web("#030202"));
        nameOfAccount.setFont(Font.font("Arial", 18));

        ImageView imageView1 = new ImageView();
        imageView1.setFitHeight(20);
        imageView1.setFitWidth(20);
        ImageView imageView2 = new ImageView();
        imageView2.setFitHeight(20);
        imageView2.setFitWidth(20);
        ImageView imageView3 = new ImageView();
        imageView3.setFitHeight(20);
        imageView3.setFitWidth(20);
        ImageView imageView4 = new ImageView();
        imageView4.setFitHeight(20);
        imageView4.setFitWidth(20);
        ImageView imageView5 = new ImageView();
        imageView5.setFitHeight(20);
        imageView5.setFitWidth(20);

        topVbox.getChildren().addAll(nameOfAccount, imageView1, imageView2, imageView3, imageView4, imageView5);
        setRatingImage(rating, imageView1, imageView2, imageView3, imageView4, imageView5);

        TextArea reviewDocument = new TextArea(review);
        reviewDocument.setPrefWidth(402);
        reviewDocument.setPrefHeight(100);
        reviewDocument.setStyle("-fx-font-family: 'Arial'; -fx-font-size: 18px;");
        reviewDocument.setEditable(false);
        reviewDocument.setFocusTraversable(false);

        rightHbox.getChildren().addAll(topVbox, reviewDocument);

        currentHbox.getChildren().addAll(imageAccount, rightHbox);
        return currentHbox;
    }

    /**
     * Sets the review display area with the list of reviews.
     * @param currentVbox The VBox to populate with reviews.
     * @param reviewList The list of reviews to display.
     */
    public void setListReview(VBox currentVbox, List<Review> reviewList) {
        if (reviewList.isEmpty()) {
            System.out.println("empty");
            return;
        }
        currentVbox.getChildren().clear();
        int size = reviewList.size();
        for (int i = size - 1; i >= 0; i--) {
            String username = reviewList.get(i).getUsername();
            String comment = reviewList.get(i).getComment();
            int rating = reviewList.get(i).getRating();
            HBox vboxContent = setEachReview(username, comment, rating, i);
            currentVbox.getChildren().add(vboxContent);
        }
        currentVbox.setPrefHeight((size * 200) + (currentVbox.getSpacing() * (size - 1)));
        apScroll.setPrefHeight(1050 + (size * 200) + (currentVbox.getSpacing() * (size - 1)));
    }

    /**
     * Adjusts the height of the description pane to fit the content.
     * This method checks the height of the description text in the `lbDescription` label.
     * If the height of the content exceeds the current height of the description label,
     * the height of the associated scrollable description pane (`apScrollDescription`) is
     * adjusted to accommodate the entire content, ensuring it is fully visible.
     * The height of the scroll pane is increased by a small buffer (10.0 pixels).
     */
    public void resizeAnPane() {
        Label copiesDescription = new Label(lbDescription.getText());
        copiesDescription.setFont(lbDescription.getFont());
        double textHeight = copiesDescription.getBoundsInLocal().getHeight();
        if (textHeight > lbDescription.getHeight()) {
            apScrollDescription.setPrefHeight(textHeight + 10.0);
        }
    }
}

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
import librarymanagement.data.*;
import librarymanagement.gui.viewmodels.DocumentInfoViewModel;

import java.util.ArrayList;
import java.util.List;

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
    private AnchorPane apScrollDescription;

    private final Image imageLast = new Image(getClass().getResource("/images/image_starLast.png").toExternalForm());

    private final Image imageFirst = new Image(getClass().getResource("/images/image_starFirst.png").toExternalForm());

    private final Image imagePublic = new Image(getClass().getResource("/images/image_bookPublic.png").toExternalForm());

    private final Image imageUser1 = new Image(getClass().getResource("/images/image1.png").toExternalForm());

    private final Image imageUser2 = new Image(getClass().getResource("/images/image2.png").toExternalForm());

    private DocumentInfoViewModel documentInfoViewModel = new DocumentInfoViewModel();


    private Document document;

    private Stage stage;

    private static DocumentInfoController instance;

    private int countStarRating = 0;

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
                    String inputText = taReview.getText();
                    System.out.println(inputText);
                    documentInfoViewModel.getReviewService().addReview(documentInfoViewModel.getAccount().getUsername(), document.getId(), countStarRating, inputText);
                    setRatingImage(0, image_OneStar, image_Star2, image_Star3, image_Star4, image_FullStar);
                    countStarRating = 0;
                    taReview.clear();
                    event.consume();
                }
            }
        });
    }

    private void handleFunction() {
        if (btFunction.getText().equals("RETURN")) {
            btFunction.setText("BORROW");
            documentInfoViewModel.functionReturn(document.getId());
        } else if (btFunction.getText().equals("BORROW")) {
            documentInfoViewModel.functionBorrow(documentInfoViewModel.getAccount().getId(), document.getId());
            btFunction.setText("RETURN");
        }
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
                setRatingImage((int) book.getAverageRating(), imageRatingDocument1, imageRatingDocument2, imageRatingDocument3, imageRatingDocument4, imageRatingDocument5);
                if (book.getThumbnailImage() == null) {
                    ivImageBook.setImage(imagePublic);
                } else {
                    ivImageBook.setImage(new Image(book.getThumbnailImage()));
                }
                if (false) {
                    btFunction.setText("RETURN");
                } else {
                    btFunction.setText("BORROW");
                }
                lbID.setText((book).getId());
                lbTitle.setText(book.getTitle());
                lbAuthor.setText(book.getAuthor());
                lbPublisher.setText(book.getPublisher());
                lbPublicationDate.setText(book.getPublishedDate().toString());
                lbISBN.setText(book.getISBN());
                lbCategories.setText(book.getCategories());
                lbPageCount.setText(String.valueOf(book.getPageCount()));
                lbDescription.setText(book.getDescription());
                resizeAnPane();
                lbRatingCount.setText(String.valueOf(book.getRatingsCount()));
                setListReview(vbReviewDocument, getReviewList());
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

    public void setListReview(VBox currentVbox, List<Review> reviewList) {
        if (reviewList.isEmpty()) {
            return;
        }
        int size = reviewList.size();
        for (int i = size - 1; i > 0; i--) {
            String username = reviewList.get(i).getUsername();
            String comment = reviewList.get(i).getComment();
            int rating = reviewList.get(i).getRating();
            HBox vboxContent = setEachReview(username, comment, rating, i);
            currentVbox.getChildren().add(vboxContent);
        }
        currentVbox.setPrefHeight((size * 200) + (currentVbox.getSpacing() * (size - 1)));
        apScroll.setPrefHeight(1150 + (size * 200) + (currentVbox.getSpacing() * (size - 1)));
    }

    //demo(test)
    public static List<Review> getReviewList() {
        List<Review> reviews = new ArrayList<>();

        reviews.add(new Review("user1", "doc123", "hello pro", 5));
        reviews.add(new Review("user2", "doc124", "i like it", 3));
        reviews.add(new Review("user3", "doc125", "I didn't like it much.", 2));
        reviews.add(new Review("user4", "doc126", "oke good", 4));
        reviews.add(new Review("user5", "doc127", "Fantastic read! Highly recommended.", 5));

        return reviews;
    }

    public void resizeAnPane() {
        Label copiesDescription = new Label(lbDescription.getText());
        copiesDescription.setFont(lbDescription.getFont());
        double textHeight = copiesDescription.getBoundsInLocal().getHeight();
        if (textHeight > lbDescription.getHeight()) {
            apScrollDescription.setPrefHeight(textHeight + 10.0);
        }
    }
}

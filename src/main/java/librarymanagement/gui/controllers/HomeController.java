package librarymanagement.gui.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import librarymanagement.data.AbstractDocument;
import librarymanagement.data.Book;
import librarymanagement.data.Document;
import librarymanagement.gui.viewmodels.HomeViewModels;

import java.io.IOException;
import java.util.List;

public class HomeController {
    @FXML
    private AnchorPane HomeScene;

    @FXML
    private Button btLeftListLibrary;

    @FXML
    private Button btRightListLibrary;

    @FXML
    private Button btLeftListNewest;

    @FXML
    private Button btRightListNewest;

    @FXML
    private Button btLeftListBorrow;

    @FXML
    private Button btRightListBorrowed;

    @FXML
    private HBox DocumentOfLibrary;

    @FXML
    private HBox DocumentNewest;

    @FXML
    private HBox DocumentBorrowed;

    @FXML
    private ScrollPane scrollLibrary;

    @FXML
    private ScrollPane scrollDocumentNewest;

    @FXML
    private ScrollPane scrollDocumentBorrowed;

    @FXML
    private AnchorPane apLibrary;

    @FXML
    private AnchorPane apDocumentNewest;

    @FXML
    private AnchorPane apDocumentBorrowed;

    private final Image imagePublic = new Image(getClass().getResource("/images/image_bookPublic.png").toExternalForm());

    private HomeViewModels viewModels = new HomeViewModels();

    @FXML
    public void initialize() {
        btLeftListBorrow.setOnAction(actionEvent -> ScrollLeft(scrollDocumentBorrowed));
        btRightListBorrowed.setOnAction(actionEvent -> ScrollRight(scrollDocumentBorrowed));
        btLeftListLibrary.setOnAction(actionEvent -> ScrollLeft(scrollLibrary));
        btRightListLibrary.setOnAction(actionEvent -> ScrollRight(scrollLibrary));
        btLeftListNewest.setOnAction(actionEvent -> ScrollLeft(scrollDocumentNewest));
        btRightListNewest.setOnAction(actionEvent -> ScrollRight(scrollDocumentNewest));
        setImageHbox(DocumentOfLibrary, apLibrary, viewModels.getListAllBook());
        setImageHbox(DocumentNewest, apDocumentNewest, viewModels.getListNewestBooks());
        setImageHbox(DocumentBorrowed, apDocumentBorrowed, viewModels.getListMostBorrowedBooks());
    }

    private void loadScene(String fxmlFile) {
        try {
            AnchorPane newPane = FXMLLoader.load(getClass().getResource("/FXML/" + fxmlFile));
            HomeScene.getChildren().setAll(newPane);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void ScrollRight(ScrollPane currentScrollPane) {
        double currentHval = currentScrollPane.getHvalue();
        double newHaval = currentHval + 0.05;

        if (newHaval > 1) {
            newHaval = 1;
        }
        currentScrollPane.setHvalue(newHaval);
    }

    public void ScrollLeft(ScrollPane currentScrollPane) {
        double currentHval = currentScrollPane.getHvalue();
        double newHaval = currentHval - 0.05;
        if (newHaval < 0) {
            newHaval = 0;
        }
        currentScrollPane.setHvalue(newHaval);
    }

    public void setImageHbox(HBox currentHBox, AnchorPane currentAnchorpane, List<Book> listBook) {
        if (listBook.isEmpty()) {
            return;
        }
        int size = listBook.size();
        for (int i = 0; i < size; i++) {
            final int index = i;
            VBox buttonContent = new VBox(5);
            buttonContent.setAlignment(Pos.CENTER);
            Image image = imagePublic;

            if (listBook.get(i).getThumbnailImage() != null) {
                image = new Image(listBook.get(i).getThumbnailImage());
            }

            ImageView imageView = new ImageView(image);
            imageView.setFitWidth(120);
            imageView.setFitHeight(160);
            buttonContent.getChildren().add(imageView);

            Label label = new Label(listBook.get(i).getTitle());
            label.setTextFill(Color.web("#FFFFFF"));
            label.setFont(Font.font("Arial", FontWeight.BOLD, 16));
            buttonContent.getChildren().add(label);

            Button button = new Button();
            button.setPrefSize(150, 170);
            button.setGraphic(buttonContent);
            button.setStyle("-fx-background-color: #232f33;");

            button.setOnAction(actionEvent -> {
                DocumentInfoController.newInstance((Document) ((AbstractDocument) listBook.get(index)));
                System.out.println("click Button :" + index);
            });

            currentHBox.getChildren().add(button);
        }
        currentAnchorpane.setPrefWidth((size * 150) + (currentHBox.getSpacing() * (size - 1)));
        currentHBox.setPrefWidth((size * 150) + (currentHBox.getSpacing() * (size - 1)));
    }

}

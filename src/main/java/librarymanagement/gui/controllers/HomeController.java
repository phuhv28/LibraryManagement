package librarymanagement.gui.controllers;

import javafx.fxml.FXML;
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
import librarymanagement.entity.Book;
import librarymanagement.gui.viewmodels.HomeViewModel;

import java.util.List;

/**
 * Controller class for managing the home page UI of the library system.
 * This class handles displaying lists of books (all, newest, borrowed), and enables scrolling and
 * interaction with the books displayed on the screen.
 */
public class HomeController {

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

    private final HomeViewModel viewModel = new HomeViewModel();

    /**
     * Initializes the controller. This method is automatically called by the FXML loader.
     * Sets up button actions for scrolling and populates the lists with books.
     */
    @FXML
    public void initialize() {
        btLeftListBorrow.setOnAction(_ -> scrollLeft(scrollDocumentBorrowed));
        btRightListBorrowed.setOnAction(_ -> scrollRight(scrollDocumentBorrowed));
        btLeftListLibrary.setOnAction(_ -> scrollLeft(scrollLibrary));
        btRightListLibrary.setOnAction(_ -> scrollRight(scrollLibrary));
        btLeftListNewest.setOnAction(_ -> scrollLeft(scrollDocumentNewest));
        btRightListNewest.setOnAction(_ -> scrollRight(scrollDocumentNewest));
        setImageHBox(DocumentOfLibrary, apLibrary, viewModel.getListAllBook());
        setImageHBox(DocumentNewest, apDocumentNewest, viewModel.getListNewestBooks());
        setImageHBox(DocumentBorrowed, apDocumentBorrowed, viewModel.getListMostBorrowedBooks());
    }

    /**
     * Scrolls the specified scroll pane to the right by a small increment.
     *
     * @param currentScrollPane the scroll pane to scroll.
     */
    public void scrollRight(ScrollPane currentScrollPane) {
        double currentHval = currentScrollPane.getHvalue();
        double newHaval = currentHval + 0.05;

        if (newHaval > 1) {
            newHaval = 1;
        }
        currentScrollPane.setHvalue(newHaval);
    }

    /**
     * Scrolls the specified scroll pane to the left by a small increment.
     *
     * @param currentScrollPane the scroll pane to scroll.
     */
    public void scrollLeft(ScrollPane currentScrollPane) {
        double currentHval = currentScrollPane.getHvalue();
        double newHaval = currentHval - 0.05;
        if (newHaval < 0) {
            newHaval = 0;
        }
        currentScrollPane.setHvalue(newHaval);
    }

    /**
     * Populates the specified HBox and AnchorPane with book images and titles.
     * Each book is displayed in a button that shows the book's thumbnail (or a default image)
     * and title. When clicked, it displays the book's details.
     *
     * @param currentHBox the HBox to add book buttons to.
     * @param currentAnchorpane the AnchorPane to adjust the width based on the number of books.
     * @param listBook the list of books to display.
     */
    public void setImageHBox(HBox currentHBox, AnchorPane currentAnchorpane, List<Book> listBook) {
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

            button.setOnAction(_ -> {
                UIController.showDocumentInfo(listBook.get(index));
                System.out.println("click Button :" + index);
            });

            currentHBox.getChildren().add(button);
        }
        currentAnchorpane.setPrefWidth((size * 150) + (currentHBox.getSpacing() * (size - 1)));
        currentHBox.setPrefWidth((size * 150) + (currentHBox.getSpacing() * (size - 1)));
    }
}

package librarymanagement.gui.controllers;


import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import librarymanagement.data.Book;
import librarymanagement.gui.viewmodels.SearchDocumentViewModel;


public class SearchDocumentController {

    @FXML
    private TableView<Book> tbResults;

    @FXML
    private TableColumn<Book, String> tcID;

    @FXML
    private TableColumn<Book, String> tcISBN;

    @FXML
    private TableColumn<Book, String> tcAuthor;

    @FXML
    private TableColumn<Book, String> tcTitle;


    @FXML
    private MenuItem miID;

    @FXML
    private MenuButton mbSearchBy;

    @FXML
    private Label lbAttributeSearch;

    @FXML
    private TextField tfValueSearch;

    @FXML
    private Button btSearchDocument;

    @FXML
    private MenuItem miSearchISBN;

    @FXML
    private MenuItem miSearchAuthor;

    @FXML
    private MenuItem miSearchCategory;

    @FXML
    private MenuItem miSearchTitle;

    @FXML
    private Label lbError;

    private SearchDocumentViewModel viewModel = new SearchDocumentViewModel();

    public void initialize() {
        btSearchDocument.setOnAction(event -> {
            searchDocument();
        });

        for (MenuItem item : mbSearchBy.getItems()) {
            setupMenuItemAction(item);
        }

        tfValueSearch.textProperty().bindBidirectional(viewModel.valueSearchProperty());
        tbResults.setItems(viewModel.searchResultProperty());
        tcID.setCellValueFactory(new PropertyValueFactory<>("id"));
        tcISBN.setCellValueFactory(new PropertyValueFactory<>("ISBN"));
        tcTitle.setCellValueFactory(new PropertyValueFactory<>("title"));
        tcAuthor.setCellValueFactory(new PropertyValueFactory<>("author"));
    }

    private void setupMenuItemAction(MenuItem menuItem) {
        menuItem.setOnAction(event -> {
            mbSearchBy.setText(menuItem.getText());
            viewModel.selectedAttributeProperty().set(menuItem.getText());
        });
    }


    private void searchDocument() {
        LoadingPopupController loadingPopup = LoadingPopupController.newInstance("Searching Document");
        loadingPopup.initOwnerStage(UIController.getPrimaryStage());
        loadingPopup.show();

        Task<Void> searchTask = new Task<>() {
            @Override
            protected Void call() throws Exception {
                loadingPopup.setText("Searching for " + tfValueSearch.getText() + " ...");
                viewModel.searchDocument();
                return null;
            }
        };

        searchTask.setOnSucceeded(event -> {
            loadingPopup.close();
            tbResults.setVisible(true);
        });

        new Thread(searchTask).start();
    }

    private void loadError() {
        lbError.setVisible(true);
    }
}


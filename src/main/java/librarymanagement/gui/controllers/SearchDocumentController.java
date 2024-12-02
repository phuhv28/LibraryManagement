package librarymanagement.gui.controllers;

import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import librarymanagement.entity.Book;
import librarymanagement.gui.viewmodels.SearchDocumentViewModel;

/**
 * Controller class for the search document feature where users can search for documents
 * such as books based on different attributes like ID, ISBN, title, author, or category.
 * This class handles user input, search logic, and displays search results.
 */
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

    private final SearchDocumentViewModel viewModel = new SearchDocumentViewModel();

    /**
     * Initializes the SearchDocumentController by setting up the table columns,
     * binding UI components to the view model, and setting up event handlers
     * for search actions and menu item selections.
     */
    public void initialize() {
        btSearchDocument.setOnAction(event -> {
            searchDocument();
        });

        // Set up menu items for selecting search attribute
        for (MenuItem item : mbSearchBy.getItems()) {
            setupMenuItemAction(item);
        }

        tfValueSearch.textProperty().bindBidirectional(viewModel.valueSearchProperty());
        tbResults.setItems(viewModel.searchResultProperty());
        tcID.setCellValueFactory(new PropertyValueFactory<>("id"));
        tcISBN.setCellValueFactory(new PropertyValueFactory<>("ISBN"));
        tcTitle.setCellValueFactory(new PropertyValueFactory<>("title"));
        tcAuthor.setCellValueFactory(new PropertyValueFactory<>("author"));

        // Handle double-click on a search result to show document info
        tbResults.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2) {
                Book selectedBook = tbResults.getSelectionModel().getSelectedItem();

                if (selectedBook != null) {
                    UIController.showDocumentInfo(selectedBook);
                }
            }
        });
    }

    /**
     * Sets up the action for each menu item in the search by menu.
     * When a menu item is selected, it updates the search criteria.
     *
     * @param menuItem the menu item to be configured.
     */
    private void setupMenuItemAction(MenuItem menuItem) {
        menuItem.setOnAction(event -> {
            mbSearchBy.setText(menuItem.getText());
            viewModel.selectedAttributeProperty().set(menuItem.getText());
        });
    }

    /**
     * Performs the document search by invoking the view model's search method.
     * Displays a loading popup during the search process, and updates the UI
     * with the search results or error messages.
     */
    private void searchDocument() {
        LoadingPopupController loadingPopup = LoadingPopupController.newInstance("Searching Document");
        loadingPopup.initOwnerStage(UIController.getPrimaryStage());
        loadingPopup.show();

        Task<Boolean> searchTask = new Task<>() {
            @Override
            protected Boolean call() {
                loadingPopup.setText("Searching for " + tfValueSearch.getText() + " ...");
                return viewModel.searchDocument();
            }
        };

        searchTask.setOnSucceeded(event -> {
            loadingPopup.close();
            if (searchTask.getValue()) {
                tbResults.setVisible(true);
                if (lbError.isVisible()) {
                    lbError.setVisible(false);
                }
            } else {
                lbError.setVisible(true);
                lbError.setText("Error!");
            }
        });

        new Thread(searchTask).start();
    }
}

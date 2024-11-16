package librarymanagement.gui.viewmodels;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import librarymanagement.data.Book;
import librarymanagement.data.BookService;

public class SearchDocumentViewModel {
    private StringProperty valueSearchProperty = new SimpleStringProperty();
    private StringProperty selectedAttributeProperty = new SimpleStringProperty();
    private ObservableList<Book> searchResultProperty = FXCollections.observableArrayList();

    public StringProperty selectedAttributeProperty() {
        return selectedAttributeProperty;
    }

    public StringProperty valueSearchProperty() {
        return valueSearchProperty;
    }

    public ObservableList<Book> searchResultProperty() {
        return searchResultProperty;
    }

    public void searchDocument() {
        if (valueSearchProperty.get() == null) {
            return;
        }

        if (!searchResultProperty.isEmpty()) {
            searchResultProperty.clear();
        }

        switch (selectedAttributeProperty.get()) {
            case "ID":
                searchResultProperty.add(BookService.getInstance().findDocumentById(valueSearchProperty.get()));
                break;
            case "ISBN":
                searchResultProperty.add(BookService.getInstance().searchBookByISBN(valueSearchProperty.get()));
                break;
            case "Title":
                searchResultProperty.addAll(BookService.getInstance().searchBookByTitle(valueSearchProperty.get()));
                break;
            case "Author":
                searchResultProperty.addAll(BookService.getInstance().searchBookByAuthor(valueSearchProperty.get()));
                break;
            case "Category":
                searchResultProperty.addAll(BookService.getInstance().searchBookByCategory(valueSearchProperty.get()));
                break;
        }
    }
}

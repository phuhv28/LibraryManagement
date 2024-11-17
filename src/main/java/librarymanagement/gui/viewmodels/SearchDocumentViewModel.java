package librarymanagement.gui.viewmodels;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import librarymanagement.data.*;

public class SearchDocumentViewModel {
    private final StringProperty valueSearchProperty = new SimpleStringProperty();
    private final StringProperty selectedAttributeProperty = new SimpleStringProperty();
    private final ObservableList<Book> searchResultProperty = FXCollections.observableArrayList();
    private final DocumentService<Book> bookService = DocumentServiceFactory.getDocumentService(DocumentType.BOOK);


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
                searchResultProperty.add(bookService.findDocumentById(valueSearchProperty.get()));
                break;
            case "ISBN":
                searchResultProperty.add(((BookService) bookService).searchBookByISBN(valueSearchProperty.get()));
                break;
            case "Title":
                searchResultProperty.addAll(((BookService) bookService).searchBookByTitle(valueSearchProperty.get()));
                break;
            case "Author":
                searchResultProperty.addAll(((BookService) bookService).searchBookByAuthor(valueSearchProperty.get()));
                break;
            case "Category":
                searchResultProperty.addAll(((BookService) bookService).searchBookByCategory(valueSearchProperty.get()));
                break;
        }
    }
}

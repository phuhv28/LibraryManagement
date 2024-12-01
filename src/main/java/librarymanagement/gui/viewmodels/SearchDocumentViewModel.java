package librarymanagement.gui.viewmodels;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import librarymanagement.entity.*;
import librarymanagement.gui.models.BookService;
import librarymanagement.gui.models.DocumentService;
import librarymanagement.gui.models.DocumentServiceFactory;

import java.util.List;

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

    public boolean searchDocument() {
        if (valueSearchProperty.get() == null || selectedAttributeProperty.get() == null) {
            return false;
        }

        if (!searchResultProperty.isEmpty()) {
            searchResultProperty.clear();
        }

        switch (selectedAttributeProperty.get()) {
            case "ID": {
                Book book = bookService.findDocumentById(valueSearchProperty.get());
                if (book != null) {
                    searchResultProperty.add(book);
                }
                break;
            }
            case "ISBN": {
                Book book = ((BookService) bookService).searchBookByISBN(valueSearchProperty.get());
                if (book != null) {
                    searchResultProperty.add(book);
                }
                break;
            }
            case "Title": {
                List<Book> books = ((BookService) bookService).searchBookByTitle(valueSearchProperty.get());
                if (books != null) {
                    searchResultProperty.addAll(books);
                }
                break;
            }
            case "Author": {
                List<Book> books = ((BookService) bookService).searchBookByAuthor(valueSearchProperty.get());
                if (books != null) {
                    searchResultProperty.addAll(books);
                }
                break;
            }
            case "Category": {
                List<Book> books = ((BookService) bookService).searchBookByCategory(valueSearchProperty.get());
                if (books != null) {
                    searchResultProperty.addAll(books);
                }
                break;
            }
        }

        return true;
    }
}

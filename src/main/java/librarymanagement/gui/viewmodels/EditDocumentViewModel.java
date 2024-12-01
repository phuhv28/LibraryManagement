package librarymanagement.gui.viewmodels;

import javafx.beans.property.*;
import librarymanagement.entity.*;
import librarymanagement.gui.models.DocumentService;
import librarymanagement.gui.models.DocumentServiceFactory;

import java.time.LocalDate;

public class EditDocumentViewModel {
    private final ObjectProperty<Book> bookProperty = new SimpleObjectProperty<>();
    private final StringProperty idProperty = new SimpleStringProperty();
    private final StringProperty isbnProperty = new SimpleStringProperty();
    private final StringProperty titleProperty = new SimpleStringProperty();
    private final StringProperty publisherProperty = new SimpleStringProperty();
    private final StringProperty authorProperty = new SimpleStringProperty();
    private final ObjectProperty<LocalDate> publishDateProperty = new SimpleObjectProperty<>();
    private final StringProperty categoryProperty = new SimpleStringProperty();
    private final StringProperty descriptionProperty = new SimpleStringProperty();
    private final IntegerProperty availableCopiesProperty = new SimpleIntegerProperty();
    private final IntegerProperty pageCountProperty = new SimpleIntegerProperty();

    private final DocumentService<Book> bookService = DocumentServiceFactory.getDocumentService(DocumentType.BOOK);

    public EditDocumentViewModel() {
        bookProperty.addListener((_, _, newValue) -> {
            if (newValue != null) {
                isbnProperty.set(newValue.getISBN());
                titleProperty.set(newValue.getTitle());
                publisherProperty.set(newValue.getPublisher());
                authorProperty.set(newValue.getAuthor());
                publishDateProperty.set(newValue.getPublishedDate());
                categoryProperty.set(newValue.getCategories());
                descriptionProperty.set(newValue.getDescription());
                availableCopiesProperty.set(newValue.getAvailableCopies());
                pageCountProperty.set(newValue.getPageCount());
            }
        });
    }

    public StringProperty idProperty() {
        return idProperty;
    }

    public StringProperty isbnProperty() {
        return isbnProperty;
    }

    public StringProperty titleProperty() {
        return titleProperty;
    }

    public StringProperty publisherProperty() {
        return publisherProperty;
    }

    public StringProperty authorProperty() {
        return authorProperty;
    }

    public ObjectProperty<LocalDate> publishDateProperty() {
        return publishDateProperty;
    }

    public StringProperty categoryProperty() {
        return categoryProperty;
    }

    public StringProperty descriptionProperty() {
        return descriptionProperty;
    }

    public IntegerProperty availableCopiesProperty() {
        return availableCopiesProperty;
    }

    public IntegerProperty pageCountProperty() {
        return pageCountProperty;
    }

    public boolean getDocumentFromId() {
        Book book = bookService.findDocumentById(idProperty.get());
        if (book == null) {
            return false;
        }
        bookProperty.set(book);
        return true;
    }

    public void save() {
        bookService.updateDocument(bookProperty.get());
    }
}

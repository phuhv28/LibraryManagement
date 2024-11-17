package librarymanagement.gui.viewmodels;

import javafx.beans.property.*;
import java.time.LocalDate;
import librarymanagement.data.*;

public class AddDocumentViewModel {
    private final StringProperty ISBNProperty = new SimpleStringProperty();
    private final StringProperty titleProperty = new SimpleStringProperty();
    private final StringProperty publisherProperty = new SimpleStringProperty();
    private final StringProperty authorProperty = new SimpleStringProperty();
    private final StringProperty categoryProperty = new SimpleStringProperty();
    private final ObjectProperty<LocalDate> publicationDateProperty = new SimpleObjectProperty<>();
    private final StringProperty descriptionProperty = new SimpleStringProperty();
    private final IntegerProperty availableCopiesProperty = new SimpleIntegerProperty();
    private final IntegerProperty pageCountProperty = new SimpleIntegerProperty();
    private final DocumentService<Book> bookService = DocumentServiceFactory.getDocumentService(DocumentType.BOOK);

    public StringProperty ISBNProperty() {
        return ISBNProperty;
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

    public StringProperty categoryProperty() {
        return categoryProperty;
    }

    public ObjectProperty<LocalDate> publicationDateProperty() {
        return publicationDateProperty;
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

    public void setPublicationDateProperty(LocalDate publicationDate) {
        this.publicationDateProperty.set(publicationDate);
    }

    public String getISBN() {
        return ISBNProperty.get();
    }

    public String getTitle() {
        return titleProperty.get();
    }

    public String getPublisher() {
        return publisherProperty.get();
    }

    public LocalDate getPublicationDate() {
        return publicationDateProperty.get();
    }

    public String getAuthor() {
        return authorProperty.get();
    }

    public int getPageCount() {
        return pageCountProperty.get();
    }

    public String getCategory() {
        return categoryProperty.get();
    }

    public int getAvailableCopies() {
        return availableCopiesProperty.get();
    }

    public String getDescription() {
        return descriptionProperty.get();
    }

    public boolean addDocument() {
        Book newBook = new Book(null, getTitle(), getPublisher(),
                getPublicationDate().toString(), getPageCount(), getAvailableCopies(),
                0, 0, getISBN(), getCategory(), getAuthor(), getDescription());
        bookService.addDocument(newBook);
        return true;
    }
}

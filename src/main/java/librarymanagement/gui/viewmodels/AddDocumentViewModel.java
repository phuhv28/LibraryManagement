package librarymanagement.gui.viewmodels;

import javafx.beans.property.*;

import java.time.LocalDate;

import librarymanagement.data.*;

public class AddDocumentViewModel {
    private final ObjectProperty<Book> bookProperty = new SimpleObjectProperty<>(new Book());
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

    public AddDocumentViewModel() {
        bookProperty.addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                ISBNProperty.set(newValue.getISBN());
                titleProperty.set(newValue.getTitle());
                publisherProperty.set(newValue.getPublisher());
                authorProperty.set(newValue.getAuthor());
                categoryProperty.set(newValue.getCategories());
                descriptionProperty.set(newValue.getDescription());
                availableCopiesProperty.set(newValue.getAvailableCopies());
                pageCountProperty.set(newValue.getPageCount());
                publicationDateProperty.set(newValue.getPublishedDate());
            }
        });

        ISBNProperty.addListener((observable, oldValue, newValue) -> {
            if (bookProperty.get() != null) {
                bookProperty.get().setISBN(newValue);
            }
        });

        titleProperty.addListener((observable, oldValue, newValue) -> {
            if (bookProperty.get() != null) {
                bookProperty.get().setTitle(newValue);
            }
        });

        publisherProperty.addListener((observable, oldValue, newValue) -> {
            if (bookProperty.get() != null) {
                bookProperty.get().setPublisher(newValue);
            }
        });

        authorProperty.addListener((observable, oldValue, newValue) -> {
            if (bookProperty.get() != null) {
                bookProperty.get().setAuthor(newValue);
            }
        });

        categoryProperty.addListener((observable, oldValue, newValue) -> {
            if (bookProperty.get() != null) {
                bookProperty.get().setCategories(newValue);
            }
        });

        publicationDateProperty.addListener((observable, oldValue, newValue) -> {
            if (bookProperty.get() != null) {
                bookProperty.get().setPublishedDate(newValue);
            }
        });

        descriptionProperty.addListener((observable, oldValue, newValue) -> {
            if (bookProperty.get() != null) {
                bookProperty.get().setDescription(newValue);
            }
        });

        availableCopiesProperty.addListener((observable, oldValue, newValue) -> {
            if (bookProperty.get() != null) {
                bookProperty.get().setAvailableCopies(newValue.intValue());
            }
        });

        pageCountProperty.addListener((observable, oldValue, newValue) -> {
            if (bookProperty.get() != null) {
                bookProperty.get().setPageCount(newValue.intValue());
            }
        });


    }

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

    public ObjectProperty<Book> bookProperty() {
        return bookProperty;
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
        return bookService.addDocument(bookProperty.get());
    }

    public Boolean fillUsingISBN() {
        Book book = GoogleBooksAPI.searchBookByISBN(ISBNProperty.get());
        if (book != null) {
            bookProperty.set(book);
            return true;
        }

        return false;
    }
}

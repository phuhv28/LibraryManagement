package librarymanagement.gui.viewmodels;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import librarymanagement.data.*;

import java.time.LocalDate;

public class EditDocumentViewModel {
    private final StringProperty documentIDProperty = new SimpleStringProperty();
    private final StringProperty editedFieldProperty = new SimpleStringProperty();
    private final ObjectProperty<Book> selectedDocumentProperty = new SimpleObjectProperty<>();
    private final StringProperty selectedAttributeProperty = new SimpleStringProperty();
    private final StringProperty resultProperty = new SimpleStringProperty();

    private final DocumentService<Book> bookService = DocumentServiceFactory.getDocumentService(DocumentType.BOOK);

    public EditDocumentViewModel() {
        documentIDProperty.addListener((observable, oldValue, newValue) -> {
            selectedDocumentProperty.set(bookService.findDocumentById(documentIDProperty.get()));
            if (selectedDocumentProperty.get() == null) {
                resultProperty.set("Invalid document id!");
            } else {
                if (selectedAttributeProperty.get() != null) {
                    loadEditedFieldProperty(selectedAttributeProperty.get());
                }
            }

        });

        selectedAttributeProperty().addListener((observable, oldValue, newValue) -> {
            loadEditedFieldProperty(newValue);
        });

        editedFieldProperty.addListener((observable, oldValue, newValue) -> {
            updateEditedFieldProperty();
        });
    }

    private void updateEditedFieldProperty() {
        switch (selectedAttributeProperty.get()) {
            case "ISBN":
                selectedDocumentProperty.get().setISBN(editedFieldProperty.get());
                break;
            case "Title":
                selectedDocumentProperty.get().setTitle(editedFieldProperty.get());
                break;
            case "Publisher":
                selectedDocumentProperty.get().setPublisher(editedFieldProperty.get());
                break;
            case "Author":
                selectedDocumentProperty.get().setAuthor(editedFieldProperty.get());
                break;
            case "Category":
                selectedDocumentProperty.get().setCategories(editedFieldProperty.get());
                break;
            case "Publication Date":
                selectedDocumentProperty.get().setPublishedDate(LocalDate.parse(editedFieldProperty.get()));
                break;
            case "Description":
                selectedDocumentProperty.get().setDescription(editedFieldProperty.get());
                break;
            case "Available copies":
                selectedDocumentProperty.get().setAvailableCopies(Integer.parseInt(editedFieldProperty.get()));
                break;
            case "Page count":
                selectedDocumentProperty.get().setPageCount(Integer.parseInt(editedFieldProperty.get()));
                break;
        }
    }

    public StringProperty documentIDProperty() {
        return documentIDProperty;
    }

    public StringProperty editedFieldProperty() {
        return editedFieldProperty;
    }

    public StringProperty selectedAttributeProperty() {
        return selectedAttributeProperty;
    }

    public StringProperty resultProperty() {
        return resultProperty;
    }

    public void save() {
        bookService.updateDocument(selectedDocumentProperty.get());
    }

    private void loadEditedFieldProperty(String selectedAttribute) {
        switch (selectedAttribute) {
            case "ISBN":
                editedFieldProperty.set(selectedDocumentProperty.get().getISBN());
                break;
            case "Title":
                editedFieldProperty.set(selectedDocumentProperty.get().getTitle());
                break;
            case "Publisher":
                editedFieldProperty.set(selectedDocumentProperty.get().getPublisher());
                break;
            case "Author":
                editedFieldProperty.set(selectedDocumentProperty.get().getAuthor());
                break;
            case "Category":
                editedFieldProperty.set(selectedDocumentProperty.get().getCategories());
                break;
            case "Publication Date":
                editedFieldProperty.set(selectedDocumentProperty.get().getPublishedDate().toString());
                break;
            case "Description":
                editedFieldProperty.set(selectedDocumentProperty.get().getDescription());
                break;
            case "Available copies":
                editedFieldProperty.set(Integer.toString(selectedDocumentProperty.get().getAvailableCopies()));
                break;
            case "Page count":
                editedFieldProperty.set(Integer.toString(selectedDocumentProperty.get().getPageCount()));
                break;
        }
    }
}

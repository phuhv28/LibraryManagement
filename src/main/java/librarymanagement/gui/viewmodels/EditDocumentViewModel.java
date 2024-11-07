package librarymanagement.gui.viewmodels;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import librarymanagement.data.Book;
import librarymanagement.data.DocumentManagement;

public class EditDocumentViewModel {
    private final StringProperty documentIDProperty = new SimpleStringProperty();
    private final StringProperty editedFieldProperty = new SimpleStringProperty();
    private final ObjectProperty<Book> selectedDocumentProperty = new SimpleObjectProperty<>();
    private final StringProperty selectedAttributeProperty = new SimpleStringProperty();

    public EditDocumentViewModel() {
        documentIDProperty.addListener((observable, oldValue, newValue) -> {
            selectedDocumentProperty.set(DocumentManagement.getInstance().searchBookByID(documentIDProperty.get()));
            if (selectedAttributeProperty.get() != null) {
                updateEditedFieldProperty(selectedAttributeProperty.get());
            }
        });

        selectedAttributeProperty().addListener((observable, oldValue, newValue) -> {
            updateEditedFieldProperty(newValue);
        });
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

    public void save() {
        DocumentManagement.getInstance().editBook(selectedDocumentProperty.get());
    }

    private void updateEditedFieldProperty(String selectedAttribute) {
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
                editedFieldProperty.set(selectedDocumentProperty.get().getPublishedDate());
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

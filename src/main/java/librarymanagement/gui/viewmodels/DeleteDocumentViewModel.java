package librarymanagement.gui.viewmodels;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import librarymanagement.data.*;

public class DeleteDocumentViewModel {
    private final StringProperty IDProperty = new SimpleStringProperty();
    private final DocumentService<Book> bookService = DocumentServiceFactory.getDocumentService(DocumentType.BOOK);

    public String getID() {
        return IDProperty.get();
    }

    public StringProperty IDProperty() {
        return IDProperty;
    }

    public boolean deleteDocument() {
        return bookService.deleteDocument(getID());
    }
}

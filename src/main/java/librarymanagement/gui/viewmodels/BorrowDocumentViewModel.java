package librarymanagement.gui.viewmodels;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import librarymanagement.UserAuth.AccountService;
import librarymanagement.data.BorrowingService;
import librarymanagement.data.DocumentServiceFactory;
import librarymanagement.data.DocumentType;
import librarymanagement.gui.controllers.BorrowResult;

public class BorrowDocumentViewModel {
    private final StringProperty idProperty = new SimpleStringProperty();

    private final BorrowingService borrowingService = new BorrowingService(DocumentServiceFactory.getDocumentService(DocumentType.BOOK));

    public StringProperty idProperty() {
        return idProperty;
    }

    public BorrowResult borrowDocument() {
        return borrowingService.borrowDocumentForCurrentAccount(idProperty.get());
    }
}

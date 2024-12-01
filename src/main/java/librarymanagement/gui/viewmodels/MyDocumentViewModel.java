package librarymanagement.gui.viewmodels;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import librarymanagement.entity.*;
import librarymanagement.gui.models.BorrowingService;
import librarymanagement.gui.models.DocumentServiceFactory;

public class MyDocumentViewModel {
    private final BorrowingService borrowingService = new BorrowingService(DocumentServiceFactory.getDocumentService(DocumentType.BOOK));
    private ObservableList<BorrowRecord> borrowedBooksProperty = null;

    public MyDocumentViewModel() {
        if (borrowingService.getBorrowRecordsOfCurrentAccount() != null) {
            borrowedBooksProperty = FXCollections.observableArrayList(borrowingService.getBorrowRecordsOfCurrentAccount());
        }

    }

    public ObservableList<BorrowRecord> borrowedBooksProperty() {
        return borrowedBooksProperty;
    }

    public boolean handleReturn(BorrowRecord record) {
        boolean result = borrowingService.returnDocument(record.getId());
        if (result) {
            borrowedBooksProperty.remove(record);
        }
        return result;
    }
}

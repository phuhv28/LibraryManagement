package librarymanagement.gui.viewmodels;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import librarymanagement.data.*;

import java.util.ArrayList;
import java.util.List;

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

    public void handleReturn(BorrowRecord record) {
        borrowingService.returnDocument(record.getId());
        borrowedBooksProperty.remove(record);
    }
}

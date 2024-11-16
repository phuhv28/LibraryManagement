package librarymanagement.gui.viewmodels;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import librarymanagement.data.BorrowBook;

public class MyDocumentViewModel {
    private final ObservableList<BorrowBook> borrowedBooksProperty = FXCollections.observableArrayList();


    public ObservableList<BorrowBook> borrowedBooksProperty() {
        return borrowedBooksProperty;
    }

    public Boolean handleReturn() {
        return true;
    }
}

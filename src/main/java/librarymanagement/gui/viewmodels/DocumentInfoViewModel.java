package librarymanagement.gui.viewmodels;

import librarymanagement.entity.User;
import librarymanagement.gui.models.AccountService;
import librarymanagement.entity.*;
import librarymanagement.gui.models.BorrowingService;
import librarymanagement.gui.models.DocumentServiceFactory;
import librarymanagement.gui.models.ReviewService;

public class DocumentInfoViewModel {
    private final ReviewService reviewService = new ReviewService();
    private final AccountService accountService = AccountService.getInstance();
    private final BorrowingService borrowingService;


    public DocumentInfoViewModel(DocumentType documentType) {
        borrowingService = new BorrowingService(DocumentServiceFactory.getDocumentService(documentType));
    }

    public ReviewService getReviewService() {
        return reviewService;
    }

    public User getAccount() {
        return accountService.getCurrentAccount();
    }

    public BorrowingService getBorrowingService() {
        return borrowingService;
    }

    public void functionReturn(String recordID) {
        borrowingService.returnDocument(recordID);
    }

    public void functionBorrow(String documentID) {
        borrowingService.borrowDocumentForCurrentAccount(documentID);
    }

    public String getRecordIdOfBorrowedDocument(String documentID) {
        return borrowingService.getRecordIdOfBorrowedDocument(documentID);
    }
}

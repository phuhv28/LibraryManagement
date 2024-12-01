package librarymanagement.gui.viewmodels;

import librarymanagement.entity.Account;
import librarymanagement.gui.models.AccountService;
import librarymanagement.entity.*;
import librarymanagement.gui.models.BorrowingService;
import librarymanagement.gui.models.DocumentServiceFactory;
import librarymanagement.gui.models.ReviewService;

public class DocumentInfoViewModel {
    private final ReviewService reviewService = new ReviewService();
    private final AccountService accountService = AccountService.getInstance();
    private final BorrowingService borrowingService = new BorrowingService(DocumentServiceFactory.getDocumentService(DocumentType.BOOK));


    public DocumentInfoViewModel(){
    }

    public ReviewService getReviewService() {
        return reviewService;
    }

    public Account getAccount() {
        return accountService.getCurrentAccount();
    }

    public BorrowingService getBorrowingService() {
        return borrowingService;
    }

    public void functionReturn(String id) {
        borrowingService.returnDocument(id);
    }

    public void functionBorrow(String userId, String id) {
        borrowingService.borrowDocument(userId, id);
    }
}

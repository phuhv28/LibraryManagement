package librarymanagement.data;

import librarymanagement.UserAuth.AccountService;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class BorrowingService {
    private static final BorrowingService INSTANCE = new BorrowingService();

    SQLiteInstance sqLiteInstance = new SQLiteInstance();

    public BorrowingService getInstance() {
        return INSTANCE;
    }


    public String generateTransactionID() {
        String newId;

        List<List<Object>> result = sqLiteInstance.findNotCondition("Transaction", "Max(transactionId)");
        if (result.get(0).get(0) == null) {
            newId = "T101";
        } else {
            String temp = result.get(0).get(0).toString().substring(1);
            newId = "T" + (Integer.parseInt(temp) + 1);
        }

        return newId;
    }

    public BorrowBook getBorrowedBookByID(String transactionID) {
        return (BorrowBook) sqLiteInstance.find("Transaction", "transactionID", transactionID, "*").getFirst();
    }

    public boolean issueBook(String bookID) {
        Book book = BookService.getInstance().findDocumentById(bookID);
        if (book == null) {
            return false;
        } else {
            if (book.getAvailableCopies() == 0) {
                return false;
            } else {
                book.setAvailableCopies(book.getAvailableCopies() - 1);
            }
            BookService.getInstance().updateDocument(book);

            LocalDate today = LocalDate.now();
            DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

            sqLiteInstance.insertRow("Transaction", generateTransactionID(),
                    AccountService.getInstance().getCurrentAccount().getId(), bookID,
                    book.getTitle(), today.format(dateFormatter), null);
        }
        return true;
    }

    public void returnBook(String transactionID) {
        BorrowBook transaction = getBorrowedBookByID(transactionID);
        Book book = BookService.getInstance().findDocumentById(transaction.getDocID());
        book.setAvailableCopies(book.getAvailableCopies() + 1);
        BookService.getInstance().updateDocument(book);

        LocalDate today = LocalDate.now();
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        sqLiteInstance.updateRow("Transaction", "returnDate", today.format(dateFormatter), "transactionID", transactionID);
    }

    public List<BorrowBook> listBooksBorrowedByUser(String userID) {
        List<BorrowBook> list = new ArrayList<>();
        sqLiteInstance.find("bookTransaction", "userId", userID, "*");
        return list;
    }

    // TODO
    public void borrowDocument(String usernameOfAccount, String documentId, DocumentType documentType) {
    }

    // TODO
    public void returnDocument(String borrowId) {
    }
}

import librarymanagement.gui.models.AccountService;
import librarymanagement.entity.AccountType;
import librarymanagement.entity.*;
import librarymanagement.gui.controllers.BorrowResult;
import librarymanagement.gui.models.BookService;
import librarymanagement.gui.models.BorrowingService;
import librarymanagement.utils.SQLiteInstance;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assumptions.*;
import static org.junit.jupiter.api.Assertions.*;

class BorrowingServiceTest {
    List<Book> bookList = new ArrayList<>();
    BookService bookService = new BookService();
    BorrowingService borrowingService = new BorrowingService(bookService);

    @BeforeEach
    void setUp() {
        AccountService.getInstance().addAccount("temp", "123", "123",
                "Superman", "example@gmail.com", AccountType.ADMIN);
        AccountService.getInstance().checkLogin("temp", "123");
        bookList = bookService.getRecentlyAddedBooks();
        for (Book book : bookList) {
            borrowingService.borrowDocumentForCurrentAccount(book.getId());
        }
    }

    @AfterEach
    void tearDown() {
        for (Book book : bookList) {
            String sql = "SELECT recordID FROM BorrowRecord WHERE userID = ? AND docID = ?";
            List<List<Object>> list = SQLiteInstance.getInstance().findWithSQL(sql,
                    new Object[]{AccountService.getInstance().getCurrentAccount().getId(), book.getId()},
                    "recordID");
            if (list.isEmpty() || list.getFirst().isEmpty()) {
                continue;
            }
            String recordID = (String) list.getFirst().getFirst();
            borrowingService.returnDocument(recordID);
        }
        SQLiteInstance.getInstance().deleteRow("BorrowRecord",
                "userID = '" + AccountService.getInstance().getCurrentAccount().getId() + "'");
        SQLiteInstance.getInstance().deleteRow("Admin", "username = 'temp'");
    }

    @ParameterizedTest
    @CsvSource({"0", "1", "2", "3", "4", "5", "6", "7", "8", "9"})
    void borrowDocumentAndReturnDocument(int index) {
        String userID = AccountService.getInstance().getCurrentAccount().getId();
        assumeTrue(borrowingService.borrowDocument(userID, bookList.get(index).getId()) == BorrowResult.SUCCESS,
                "Failed to borrow document " + index + " for user '" + userID + "'");
        /// Get recordID, docID, returnDate
        String sql = "SELECT recordID, docID, returnDate FROM BorrowRecord WHERE userID = ? AND docID = ?";
        List<List<Object>> list = SQLiteInstance.getInstance().findWithSQL(sql,
                new Object[]{AccountService.getInstance().getCurrentAccount().getId(), bookList.get(index).getId()},
                "recordID", "docID", "returnDate");
        String recordID = (String) list.getFirst().getFirst();
        String docID = (String) list.getFirst().get(1);
        Book book = bookService.findDocumentById(docID);
        /// get availableCopies before return
        int copies1 = book.getAvailableCopies();
        /// Check returnDate when borrow need be null
        assumeTrue(list.getFirst().get(2) == null, "returnDate need be null");
        assumeTrue(borrowingService.returnDocument(recordID), "Failed to return document "
                + index + " for user '" + userID + "'");
        book = bookService.findDocumentById(docID);
        /// get availableCopies after return
        int copies2 = book.getAvailableCopies();
        /// Check availableCopies
        assertEquals(copies1, copies2 - 1, "Failed available copies.");
    }

    @Test
    void getBorrowRecordsOfUser() {
        List<BorrowRecord> docs =
                borrowingService.getBorrowRecordsOfUser(AccountService.getInstance().getCurrentAccount().getId());
        assertEquals(docs.size(), bookList.size(), "Number of docs need be 10");
    }

    @ParameterizedTest
    @CsvSource({"0", "1", "2", "3", "4", "5", "6", "7", "8", "9"})
    void checkIfUserHasBorrowedDocument(int index) {
        String userID = AccountService.getInstance().getCurrentAccount().getId();
        String docID = bookList.get(index).getId();
        assumeTrue(borrowingService.checkIfUserHasBorrowedDocument(userID, docID),
                "Check if document " + index + " has borrowed document");
    }

    @ParameterizedTest
    @CsvSource({"U1001, 1",
                "U1001, 2",
                "U101, 3"})
    void checkIfUserHasBorrowedDocumentFalse(String userID, int index) {
        String docID = bookList.get(index).getId();
        assumeFalse(borrowingService.checkIfUserHasBorrowedDocument(userID, docID),
                "Check if document " + index + " has not borrowed document");
    }
}
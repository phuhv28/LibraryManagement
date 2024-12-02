package librarymanagement.gui.models;

import librarymanagement.entity.*;
import librarymanagement.utils.SQLiteInstance;
import librarymanagement.gui.controllers.BorrowResult;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;


/** Class handles handle borrowing operations.*/
public class BorrowingService {
    private final SQLiteInstance sqLiteInstance = new SQLiteInstance();
    private final DocumentService<Book> bookService;

    public BorrowingService(DocumentService<Book> bookService) {
        this.bookService = bookService;
    }

    /**
     * Generates a new unique record ID for a borrow record.
     *
     * <p>This method generates a new borrow record ID by checking the maximum existing record ID in the
     * "BorrowRecord" table and incrementing it by 1. If no records exist, it starts the ID from "R101".</p>
     *
     * @return the newly generated borrow record ID as a {@link String}.
     */
    private String generateBorrowRecordID() {
        String newId;

        List<List<Object>> result = sqLiteInstance.findNotCondition("BorrowRecord", "Max(recordID)");
        if (result.getFirst().getFirst() == null) {
            newId = "R101";
        } else {
            String temp = result.getFirst().getFirst().toString().substring(1);
            newId = "R" + (Integer.parseInt(temp) + 1);
        }

        return newId;
    }

    /**
     * Retrieves a borrow record by its ID from the database.
     *
     * <p>This method queries the "BorrowRecord" table using the provided record ID and retrieves
     * information related to the borrow record, including the associated user account, document,
     * borrow date, due date, and return date.</p>
     *
     * <p>It then creates and returns a {@link BorrowRecord} object populated with the retrieved
     * data.</p>
     *
     * @param recordID the ID of the borrow record to be retrieved.
     * @return the {@link BorrowRecord} object containing the borrow record details.
     * @throws IllegalArgumentException if the record ID is not found or invalid.
     */
    public BorrowRecord getBorrowRecordByID(String recordID) {
        List<List<Object>> list = sqLiteInstance.find("BorrowRecord", "recordID", recordID,
                "userID", "docID", "borrowDate", "dueDate", "returnDate");
        Account account = AccountService.getInstance().getAccountByUserID((String) list.getFirst().getFirst());
        Document document = bookService.findDocumentById((String) list.getFirst().get(1));
        // TODO add type MAGAZINE and THESIS
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate borrowDate = LocalDate.parse((String) list.getFirst().get(2), formatter);
        LocalDate dueDate = LocalDate.parse((String) list.getFirst().get(3), formatter);
        LocalDate returnDate;
        if (list.getFirst().get(4) == null) {
            returnDate = null;
        } else {
            returnDate = LocalDate.parse((String) list.getFirst().get(4), formatter);
        }
        return new BorrowRecord(recordID, account, document, borrowDate, dueDate, returnDate);
    }

    /**
     * Borrows a document (book, magazine, thesis) for a given user.
     *
     * <p>This method checks if the document is a book, magazine, or thesis by inspecting the first
     * character of the document ID. It then performs the following operations:
     * <ul>
     *   <li>If the document is a book, it checks if the book is available. If available, it updates
     *       the book's available copies and records the borrow action in the database.</li>
     *   <li>If the document is a magazine or thesis, the functionality is yet to be implemented.</li>
     *   <li>If the document ID does not match any known type, it returns a "not found" result.</li>
     * </ul>
     * After successful borrowing, a new borrow record is created and inserted into the database.</p>
     *
     * @param userID the ID of the user borrowing the document.
     * @param documentId the ID of the document being borrowed.
     * @return the result of the borrow operation, which can be one of the following:
     *         <ul>
     *           <li>{@link BorrowResult#NOT_FOUND} if the document does not exist or is invalid.</li>
     *           <li>{@link BorrowResult#OUT_OF_STOCK} if the document (book) is out of stock.</li>
     *           <li>{@link BorrowResult#SUCCESS} if the borrow operation is successful.</li>
     *         </ul>
     */
    public BorrowResult borrowDocument(String userID, String documentId) {
        if (documentId.charAt(0) == 'B') {
            Book book = bookService.findDocumentById(documentId);
            if (book == null) {
                return BorrowResult.NOT_FOUND;
            }
            if (book.getAvailableCopies() == 0) {
                return BorrowResult.OUT_OF_STOCK;
            }
            book.setAvailableCopies(book.getAvailableCopies() - 1);
            bookService.updateDocument(book);
        } else if (documentId.charAt(0) == 'M') {
            MagazineService magazineService = new MagazineService();
            Magazine magazine = magazineService.findDocumentById(documentId);
            if (magazine == null) {
                return BorrowResult.NOT_FOUND;
            }
            if (magazine.getAvailableCopies() == 0) {
                return BorrowResult.OUT_OF_STOCK;
            }
            magazine.setAvailableCopies(magazine.getAvailableCopies() - 1);
            magazineService.updateDocument(magazine);
        } else {
            return BorrowResult.NOT_FOUND;
        }
        Account account = AccountService.getInstance().getAccountByUserID(userID);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate borrowDate = sqLiteInstance.getToday();
        LocalDate dueDate = borrowDate.plusDays(10);
        sqLiteInstance.insertRow("BorrowRecord", generateBorrowRecordID(), account.getId(), documentId,
                borrowDate.format(formatter), null, dueDate.format(formatter));
        return BorrowResult.SUCCESS;
    }

    /**
     * Borrows a document for the currently logged-in user.
     *
     * <p>This method acts as a shortcut for borrowing a document, using the ID of the currently
     * logged-in user (retrieved from the current account). It calls the {@link #borrowDocument(String, String)}
     * method to perform the borrowing operation.</p>
     *
     * @param documentId the ID of the document being borrowed.
     * @return the result of the borrow operation, which can be one of the following:
     *         <ul>
     *           <li>{@link BorrowResult#NOT_FOUND} if the document does not exist or is invalid.</li>
     *           <li>{@link BorrowResult#OUT_OF_STOCK} if the document (book) is out of stock.</li>
     *           <li>{@link BorrowResult#SUCCESS} if the borrow operation is successful.</li>
     *         </ul>
     */
    public BorrowResult borrowDocumentForCurrentAccount(String documentId) {
        return borrowDocument(AccountService.getInstance().getCurrentAccount().getId(), documentId);
    }

    /**
     * Returns a borrowed document and updates the document's available copies in the system.
     *
     * <p>This method updates the status of the borrowed document by setting the return date in the
     * borrow record and increasing the available copies of the document. If the document is a book,
     * it will be updated using the book service. If the document is of other types (e.g., magazine or thesis),
     * the implementation for those types still needs to be completed.</p>
     *
     * @param borrowId the ID of the borrow record for the document being returned.
     * @return {@code true} if the document is successfully returned and the borrow record is updated,
     *         {@code false} if the borrow record does not exist.
     */
    public boolean returnDocument(String borrowId) {
        List<List<Object>> list = sqLiteInstance.find("BorrowRecord", "recordID", borrowId,
                "userID", "docID", "borrowDate", "dueDate", "returnDate");
        if (list.isEmpty()) {
            return false;
        }
        Document document = bookService.findDocumentById((String) list.getFirst().get(1));
        document.setAvailableCopies(document.getAvailableCopies() + 1);
        String documentID = document.getId();
        if (documentID.charAt(0) == 'B') {
            bookService.updateDocument((Book) document);
        } else if (documentID.charAt(0) == 'M') {
            MagazineService magazineService = new MagazineService();
            magazineService.updateDocument((Magazine) document);
        }
        String returnDate = sqLiteInstance.getToday().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        sqLiteInstance.updateRow("BorrowRecord", "returnDate", returnDate, "recordID", borrowId);
        return true;
    }

    /**
     * Retrieves a list of borrow records for a specific user.
     *
     * <p>This method queries the borrow records of the user from the database and returns a list of
     * borrow records where the return date is not set. It handles different document types (Book,
     * Magazine, Thesis) and constructs a `BorrowRecord` for each matching entry.</p>
     *
     * @param userID the ID of the user whose borrow records are being retrieved.
     * @return a list of `BorrowRecord` objects for the user with the return date not set, or {@code null}
     *         if no such records exist for the given user.
     */
    public List<BorrowRecord> getBorrowRecordsOfUser(String userID) {
        List<List<Object>> list = sqLiteInstance.find("BorrowRecord", "userID", userID,
                "recordID", "docID", "borrowDate", "dueDate", "returnDate");
        if (list.isEmpty()) {
            return null;
        }
        List<BorrowRecord> borrowRecords = new ArrayList<>();
        for (List<Object> row : list) {
            if (row.get(4) != null) {
                continue;
            }
            String recordID = (String) row.getFirst();
            Account account = AccountService.getInstance().getAccountByUserID(userID);
            String documentID = (String) row.get(1);
            Document document = null;
            if (documentID.charAt(0) == 'B') {
                document = bookService.findDocumentById(documentID);
            } else if (documentID.charAt(0) == 'M') {
                MagazineService magazineService = new MagazineService();
                document = magazineService.findDocumentById(documentID);
            }
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate borrowDate = LocalDate.parse((String) row.get(2), formatter);
            LocalDate dueDate = LocalDate.parse((String) row.get(3), formatter);
            borrowRecords.add(new BorrowRecord(recordID, account, document, borrowDate, dueDate, null));
        }

        if (borrowRecords.isEmpty()) {
            return null;
        }

        return borrowRecords;
    }

    /**
     * Retrieves a list of borrow records for the currently logged-in user.
     *
     * <p>This method calls {@link #getBorrowRecordsOfUser(String)} to fetch the borrow records
     * for the currently authenticated user. It automatically uses the current user's ID
     * to retrieve the borrow records where the return date is not set.</p>
     *
     * @return a list of `BorrowRecord` objects for the currently logged-in user, with the return
     *         date not set, or {@code null} if no such records exist.
     */
    public List<BorrowRecord> getBorrowRecordsOfCurrentAccount() {
        return getBorrowRecordsOfUser(AccountService.getInstance().getCurrentAccount().getId());
    }

    /**
     * Checks if a user has borrowed a specific document.
     *
     * <p>This method queries the "BorrowRecord" table to check if a record exists where the
     * given userID and documentID match. If such a record is found, it indicates that the user
     * has borrowed the specified document.</p>
     *
     * @param userID the ID of the user
     * @param documentID the ID of the document
     * @return {@code true} if the user has borrowed the specified document, {@code false} otherwise
     */
    public boolean checkIfUserHasBorrowedDocument(String userID, String documentID) {
        String sql = "SELECT userID FROM BorrowRecord WHERE userID = ? AND docID = ? AND returnDate ISNULL";
        List<List<Object>> lists = sqLiteInstance.findWithSQL(sql, new Object[]{userID, documentID}, "userID");
        return !lists.isEmpty() && !lists.getFirst().isEmpty();
    }
}

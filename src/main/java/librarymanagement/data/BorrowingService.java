package librarymanagement.data;

import librarymanagement.UserAuth.Account;
import librarymanagement.UserAuth.AccountService;
import librarymanagement.gui.controllers.BorrowResult;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class BorrowingService {
    private final SQLiteInstance sqLiteInstance = new SQLiteInstance();
    private final DocumentService<Book> bookService;

    public BorrowingService(DocumentService<Book> bookService) {
        this.bookService = bookService;
    }

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
            // TODO
        } else if (documentId.charAt(0) == 'T') {
            //TODO
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

    public BorrowResult borrowDocumentForCurrentAccount(String documentId) {
        return borrowDocument(AccountService.getInstance().getCurrentAccount().getId(), documentId);
    }

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
            //TODO
        } else if (documentID.charAt(0) == 'T') {
            //TODO
        }
        String returnDate = sqLiteInstance.getToday().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        sqLiteInstance.updateRow("BorrowRecord", "returnDate", returnDate, "recordID", borrowId);
        return true;
    }

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
                //TODO
            } else if (documentID.charAt(0) == 'T') {
                //TODO
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

    public List<BorrowRecord> getBorrowRecordsOfCurrentAccount() {
        return getBorrowRecordsOfUser(AccountService.getInstance().getCurrentAccount().getId());
    }
}

package librarymanagement.data;

import librarymanagement.UserAuth.Account;
import librarymanagement.UserAuth.AccountService;

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
            String temp = result.get(0).get(0).toString().substring(1);
            newId = "R" + (Integer.parseInt(temp) + 1);
        }

        return newId;
    }

    public BorrowRecord getBorrowRecordByID(String recordID) {
        List<List<Object>> list = sqLiteInstance.find("BorrowRecord", "recordID", recordID,
                "userID", "docID", "borrowDate", "dueDate", "returnDate");
        Account account = AccountService.getInstance().getAccountByUserID((String) list.get(0).get(0));
        Document document = bookService.findDocumentById((String) list.get(0).get(1));
        // TODO add type MAGAZINE and THESIS
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate borrowDate = LocalDate.parse((String) list.get(0).get(2), formatter);
        LocalDate dueDate = LocalDate.parse((String) list.get(0).get(3), formatter);
        LocalDate returnDate;
        if (list.get(0).get(4) == null) {
            returnDate = null;
        } else {
            returnDate = LocalDate.parse((String) list.get(0).get(4), formatter);
        }
        return new BorrowRecord(recordID, account, document, borrowDate, dueDate, returnDate);
    }

    // TODO
    public boolean borrowDocument(String userID, String documentId) {
        Document document = bookService.findDocumentById(documentId);
        if (document.getAvailableCopies() == 0) {
            return false;
        }

        DocumentType documentType = document.getDocumentType();
        if (documentType == DocumentType.BOOK) {
            document.setAvailableCopies(document.getAvailableCopies() - 1);
            bookService.updateDocument((Book) document);
        } else if (documentType == DocumentType.MAGAZINE) {
            // TODO
            return false;
        } else if (documentType == DocumentType.THESIS) {
            // TODO
            return false;
        }
        Account account = AccountService.getInstance().getAccountByUserID(userID);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate borrowDate = sqLiteInstance.getToday();
        LocalDate dueDate = borrowDate.plusDays(10);
        sqLiteInstance.insertRow("BorrowRecord", generateBorrowRecordID(), account.getId(), document.getId(),
                borrowDate.format(formatter), null, dueDate.format(formatter));
        return true;
    }

    public boolean borrowDocumentForCurrentAccount(String documentId) {
        return borrowDocument(AccountService.getInstance().getCurrentAccount().getId(), documentId);
    }

    // TODO
    public boolean returnDocument(String borrowId) {
        List<List<Object>> list = sqLiteInstance.find("BorrowRecord", "recordID", borrowId,
                "userID", "docID", "borrowDate", "dueDate", "returnDate");
        if (list.isEmpty()) {
            return false;
        }
        Document document = bookService.findDocumentById((String) list.get(0).get(1));
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
            String recordID = (String) row.get(0);
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
        return borrowRecords;
    }

    public List<BorrowRecord> getBorrowRecordsOfCurrentAccount() {
        return getBorrowRecordsOfUser(AccountService.getInstance().getCurrentAccount().getId());
    }
}

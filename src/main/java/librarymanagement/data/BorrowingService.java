package librarymanagement.data;

import librarymanagement.UserAuth.Account;
import librarymanagement.UserAuth.AccountService;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class BorrowingService {
    private static final BorrowingService INSTANCE = new BorrowingService();
    SQLiteInstance sqLiteInstance = new SQLiteInstance();

    public static BorrowingService getInstance() {
        return INSTANCE;
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
        Document document = BookService.getInstance().findDocumentById((String) list.get(0).get(1));
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
    public boolean borrowDocument(String userID, String documentId, DocumentType documentType) {
        Document document = BookService.getInstance().findDocumentById(documentId);
        if (document.getAvailableCopies() == 0) {
            System.out.println("Document " + documentId + " is not available");
            return false;
        }
        if (documentType == DocumentType.BOOK) {
            document.setAvailableCopies(document.getAvailableCopies() - 1);
            BookService.getInstance().updateDocument((Book) document);
        } else if (documentType == DocumentType.MAGAZINE){
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

    // TODO
    public boolean returnDocument(String borrowId) {
        List<List<Object>> list = sqLiteInstance.find("BorrowRecord", "recordID", borrowId,
                "userID", "docID", "borrowDate", "dueDate", "returnDate");
        if (list.isEmpty()) {
            return false;
        }
        Document document = BookService.getInstance().findDocumentById((String) list.get(0).get(1));
        document.setAvailableCopies(document.getAvailableCopies() + 1);
        String documentID = document.getId();
        if (documentID.charAt(0) == 'B') {
            BookService.getInstance().updateDocument((Book) document);
        } else if (documentID.charAt(0) == 'M') {
            //TODO
        } else if (documentID.charAt(0) == 'T') {
            //TODO
        }
        String returnDate = sqLiteInstance.getToday().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        sqLiteInstance.updateRow("BorrowRecord", "returnDate", returnDate, "recordID", borrowId);
        return true;
    }
}

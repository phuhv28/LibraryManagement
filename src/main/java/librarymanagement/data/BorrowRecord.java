package librarymanagement.data;

import librarymanagement.UserAuth.Account;
import java.time.LocalDate;

/** Class represents a borrow record.*/
public class BorrowRecord {
    private String id;
    private Account account;
    private Document document;
    private LocalDate borrowDate;
    private LocalDate dueDate;
    private LocalDate returnDate;
    private Status status;

    public enum Status {
        BORROWING, OVERDUE, RETURNED
    }

    public BorrowRecord(String id, Account account, Document document, LocalDate borrowDate,
                        LocalDate dueDate, LocalDate returnDate) {
        this.id = id;
        this.account = account;
        this.document = document;
        this.borrowDate = borrowDate;
        this.dueDate = dueDate;
        this.returnDate = returnDate;
        if (returnDate != null) {
            this.status = Status.RETURNED;
        } else {
            if (LocalDate.now().isAfter(dueDate)) {
                this.status = Status.OVERDUE;
            } else {
                this.status = Status.BORROWING;
            }
        }
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public Document getDocument() {
        return document;
    }

    public void setDocument(Document document) {
        this.document = document;
    }

    public LocalDate getBorrowDate() {
        return borrowDate;
    }

    public void setBorrowDate(LocalDate borrowDate) {
        this.borrowDate = borrowDate;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    public LocalDate getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(LocalDate returnDate) {
        this.returnDate = returnDate;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
}

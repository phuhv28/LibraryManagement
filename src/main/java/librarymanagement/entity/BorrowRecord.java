package librarymanagement.entity;

import java.time.LocalDate;

/**
 * Class represents a borrow record.
 */
public class BorrowRecord {
    private String id;
    private User user;
    private Document document;
    private LocalDate borrowDate;
    private LocalDate dueDate;
    private LocalDate returnDate;
    private Status status;

    public BorrowRecord(String id, User user, Document document, LocalDate borrowDate,
                        LocalDate dueDate, LocalDate returnDate) {
        this.id = id;
        this.user = user;
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

    public User getAccount() {
        return user;
    }

    public void setAccount(User user) {
        this.user = user;
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

    public enum Status {
        BORROWING, OVERDUE, RETURNED
    }
}

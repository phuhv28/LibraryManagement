package librarymanagement.data;

import librarymanagement.UserAuth.Account;
import java.time.LocalDate;

public class BorrowRecord {
    private String id;
    private Account account;
    private Document document;
    private LocalDate borrowDate;
    private LocalDate dueDate;
    private LocalDate returnDate;
    private Status status;

    public enum Status {
        BORROWING, OVERDUE, RETURN
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
            this.status = Status.RETURN;
        } else {
            if (LocalDate.now().isAfter(dueDate)) {
                this.status = Status.OVERDUE;
            } else {
                this.status = Status.BORROWING;
            }
        }
    }
}

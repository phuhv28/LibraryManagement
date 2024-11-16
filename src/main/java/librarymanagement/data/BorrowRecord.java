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
    private String status;
}

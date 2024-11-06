package librarymanagement.data;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class TransactionManagement {
    private static final TransactionManagement INSTANCE = new TransactionManagement();

    SQLiteInstance sqLiteInstance = new SQLiteInstance();

    public TransactionManagement getInstance() {
        return INSTANCE;
    }

    public void issueBook(String ISBN, String userID) {
        List<List<Object>> lists = sqLiteInstance.find("Book", "ISBN", ISBN, "availableCopies");

        if (lists.isEmpty()) {
            System.out.println("ISBN is not found.");
            return;
        } else if ((int) lists.getFirst().getFirst() == 0) {
            System.out.println("No available copies found.");
            return;
        }

        String transactionID;
        lists = sqLiteInstance.findWithSQL("SELECT transactionID FROM bookTransaction ORDER BY transactionID DESC LIMIT 1", new Object[]{}, "transactionID");
        if (lists.isEmpty()) {
            transactionID = "T101";
        } else {
            String temp = lists.get(0).get(0).toString().substring(1);
            transactionID = "T" + (Integer.parseInt(temp) + 1);
        }
        String finalTransactionID = transactionID;

        LocalDate today = LocalDate.now();
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        String sql = "UPDATE book SET availableCopies = availableCopies - 1 WHERE ISBN = ?";
        sqLiteInstance.executeUpdate(sql, stmt -> {
            stmt.setString(1, ISBN);
        });

        sql = "INSERT INTO bookTransaction (userId, ISBN, borrowDate, returnDate, transactionID) VALUES (?, ?, ?, ?, ?)";
        sqLiteInstance.executeUpdate(sql, stmt -> {
            stmt.setString(1, userID);
            stmt.setString(2, ISBN);
            stmt.setString(3, today.format(dateFormatter));
            stmt.setString(4, null);
            stmt.setString(5, finalTransactionID);
        });
        System.out.println("Issue book successfully");
    }

    public void returnBook(String issueID) {
        List<List<Object>> lists = sqLiteInstance.find("Book", "transactionID", issueID, "ISBN");
        String finalISBN = lists.getFirst().getFirst().toString();

        String sql = "UPDATE book SET availableCopies = availableCopies + 1 WHERE ISBN = ?";
        sqLiteInstance.executeUpdate(sql, stmt -> {
            stmt.setString(1, finalISBN);
        });

        LocalDate today = LocalDate.now();
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        sql = "UPDATE bookTransaction SET returnDate = ? WHERE transactionID = ?";
        sqLiteInstance.executeUpdate(sql, stmt -> {
            stmt.setString(1, today.format(dateFormatter));
        });
        System.out.println("Return book successfully");
    }

    public List<Book> listBooksBorrowedByUser(String userID) {
        List<Book> books = new ArrayList<>();
        sqLiteInstance.find("bookTransaction", "userId", userID, "*");
        return books;
    }

    public static void main(String[] args) {
        TransactionManagement transactionManagement = new TransactionManagement();
        transactionManagement.issueBook("9781788299046", "U101");
    }
}

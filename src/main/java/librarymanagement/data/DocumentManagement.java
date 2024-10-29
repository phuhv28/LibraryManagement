package librarymanagement.data;

import java.io.File;
import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class DocumentManagement {

    public DocumentManagement(Connection mockConnection) {
        try (Connection con = DriverManager.getConnection(Constant.URL, Constant.USERNAME, Constant.PASSWORD)) {
            System.out.println("Database connection successful.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void addDocument(Book document) {
        String sql = "INSERT INTO books (ISBN, Book_title, Category, Rental_Price, Status, Author, Publisher, Total_quantity, Available_quantity) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection con = DriverManager.getConnection(Constant.URL, Constant.USERNAME, Constant.PASSWORD);
             PreparedStatement stmt = con.prepareStatement(sql)) {

            stmt.setString(1, document.getISBN());
            stmt.setString(2, document.getTitle());
            stmt.setString(3, document.getCategory());
            stmt.setDouble(4, 10);
            stmt.setString(5, "Yes");
            stmt.setString(6, document.getAuthor());
            stmt.setString(7, document.getPublisher());
            stmt.setInt(8, document.getTotalQuantity());
            stmt.setInt(9, document.getAvailableQuantity());

            stmt.executeUpdate();
            System.out.println("The book has been added to the database.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteDocument(String documentID) {
        String sql = "DELETE FROM books WHERE ISBN = ?";
        try (Connection con = DriverManager.getConnection(Constant.URL, Constant.USERNAME, Constant.PASSWORD);
             PreparedStatement stmt = con.prepareStatement(sql)) {

            stmt.setString(1, documentID);
            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Book has been deleted successfully.");
            } else {
                System.out.println("No book found with the given ID.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<String> searchDocumentByName(String documentName) {
        List<String> listName = new ArrayList<>();
        String sql = "SELECT * FROM books WHERE Book_title LIKE ?";

        try (Connection con = DriverManager.getConnection(Constant.URL, Constant.USERNAME, Constant.PASSWORD);
             PreparedStatement stmt = con.prepareStatement(sql)) {

            stmt.setString(1, "'%" + documentName + "%'");
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    listName.add(rs.getString("Book_title"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return listName;
    }

    public List<String> searchDocumentByISBN(String ISBN) {
        List<String> listName = new ArrayList<>();
        String sql = "SELECT * FROM books WHERE ISBN LIKE ?";

        try (Connection con = DriverManager.getConnection(Constant.URL, Constant.USERNAME, Constant.PASSWORD);
             PreparedStatement stmt = con.prepareStatement(sql)) {

            stmt.setString(1, ISBN);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    listName.add(rs.getString("ISBN") + " - " + rs.getString("Book_title"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return listName;
    }

    public List<String> searchDocumentByCategory(String category) {
        List<String> listName = new ArrayList<>();
        String sql = "SELECT * FROM books WHERE Category = ?";

        try (Connection con = DriverManager.getConnection(Constant.URL, Constant.USERNAME, Constant.PASSWORD);
             PreparedStatement stmt = con.prepareStatement(sql)) {

            stmt.setString(1, category);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    listName.add(rs.getString("Book_title"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return listName;
    }

    public List<String> searchDocumentByAuthor(String author) {
        List<String> listName = new ArrayList<>();
        String sql = "SELECT * FROM books WHERE Author = ?";

        try (Connection con = DriverManager.getConnection(Constant.URL, Constant.USERNAME, Constant.PASSWORD);
             PreparedStatement stmt = con.prepareStatement(sql)) {

            stmt.setString(1, author);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    listName.add(rs.getString("Book_title"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return listName;
    }

    public String issueBook(String ISBN, String customerID) {
        String sql = "SELECT * FROM books WHERE ISBN = ?";
        String status = "";

        // Check if the book is still available in the library
        try (Connection con = DriverManager.getConnection(Constant.URL, Constant.USERNAME, Constant.PASSWORD);
             PreparedStatement stmt = con.prepareStatement(sql)) {

            stmt.setString(1, ISBN);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    status = rs.getString("Status");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        if (status.equals("No")) {
            return "This book is no longer available in the library.";
        }

        // Create new IssueID
        String maxIssueID = "";
        sql = "SELECT MAX(Transaction_Id) AS max_id FROM BookTransactions";

        try (Connection con = DriverManager.getConnection(Constant.URL, Constant.USERNAME, Constant.PASSWORD);
             PreparedStatement stmt = con.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            if (rs.next()) {
                maxIssueID = rs.getString("max_id");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        int temp = (maxIssueID != null ? Integer.parseInt(maxIssueID.substring(2)) : 0) + 1;
        maxIssueID = "IS" + temp;

        // Find bookName
        String bookName = "";
        sql = "SELECT * FROM books WHERE ISBN = ?";

        try (Connection con = DriverManager.getConnection(Constant.URL, Constant.USERNAME, Constant.PASSWORD);
             PreparedStatement stmt = con.prepareStatement(sql)) {

            stmt.setString(1, ISBN);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    bookName = rs.getString("Book_title");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // Find borrow date
        LocalDate currentDate = LocalDate.now();
        String borrowDate = currentDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));

        // Update the quantity and status of the books.
        sql = "UPDATE books SET Available_quantity = Available_quantity - 1, " +
                "Status = CASE WHEN Available_quantity - 1 > 0 THEN 'Yes' ELSE 'No' END " +
                "WHERE ISBN = ?";

        try (Connection con = DriverManager.getConnection(Constant.URL, Constant.USERNAME, Constant.PASSWORD);
             PreparedStatement stmt = con.prepareStatement(sql)) {

            stmt.setString(1, ISBN);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // Import new issue to BookTransactions
        sql = "INSERT INTO BookTransactions (Transaction_Id, Customer_Id, Book_ISBN, Book_title, Issue_date, Return_Date, Status) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection con = DriverManager.getConnection(Constant.URL, Constant.USERNAME, Constant.PASSWORD);
             PreparedStatement stmt = con.prepareStatement(sql)) {

            stmt.setString(1, maxIssueID);
            stmt.setString(2, customerID);
            stmt.setString(3, ISBN);
            stmt.setString(4, bookName);
            stmt.setString(5, borrowDate);
            stmt.setString(6, null);
            stmt.setString(7, "Issued");

            stmt.executeUpdate();
            return "Book has been issued successfully.";
        } catch (SQLException e) {
            e.printStackTrace();
            return "Book is not issued";
        }
    }

    void returnBook(String issueID) {
        String sql = "SELECT * FROM BookTransactions WHERE Transaction_Id = ?";
        String ISBN = "";
        String returnDate = "";

        try (Connection con = DriverManager.getConnection(Constant.URL, Constant.USERNAME, Constant.PASSWORD);
             PreparedStatement stmt = con.prepareStatement(sql)) {

            stmt.setString(1, issueID);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    ISBN = rs.getString("Book_ISBN");
                    returnDate = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return;
        }

        sql = "UPDATE BookTransactions SET Return_Date = ?, Status = ? WHERE Transaction_Id = ?";
        try (Connection con = DriverManager.getConnection(Constant.URL, Constant.USERNAME, Constant.PASSWORD);
             PreparedStatement stmt = con.prepareStatement(sql)) {

            stmt.setString(1, returnDate);
            stmt.setString(2, "Returned");
            stmt.setString(3, issueID);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return;
        }

        sql = "UPDATE books SET Available_quantity = Available_quantity + 1, " +
                "Status = CASE WHEN Available_quantity + 1 > 0 THEN 'Yes' ELSE 'No' END " +
                "WHERE ISBN = ?";

        try (Connection con = DriverManager.getConnection(Constant.URL, Constant.USERNAME, Constant.PASSWORD);
             PreparedStatement stmt = con.prepareStatement(sql)) {

            stmt.setString(1, ISBN);
            stmt.executeUpdate();
            System.out.println("Book has been returned successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

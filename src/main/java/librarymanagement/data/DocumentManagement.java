package librarymanagement.data;

import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class DocumentManagement {
    private static final DocumentManagement INSTANCE = new DocumentManagement();

    private static final SQLiteInstance sqLiteInstance = SQLiteInstance.getInstance();

    private Connection connection;

    public DocumentManagement getInstance() {
        return INSTANCE;
    }

    private DocumentManagement() {
        try {
            connection = DriverManager.getConnection(Constant.URL);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void closeConnection() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FunctionalInterface
    interface PreparedStatementSetter {
        void setValues(PreparedStatement stmt) throws SQLException;
    }

    public String generateBookID() {
        String newId;

        List<List<Object>> result = sqLiteInstance.findNotCondition("Book", "Max(id)");
        if (result.get(0).get(0) == null) {
            newId = "B101";
        } else {
            String temp = result.get(0).get(0).toString().substring(1);
            newId = "B" + (Integer.parseInt(temp) + 1);
        }

        return newId;
    }

    //tam thoi co moi Book, con Magazine va Newspaper
    public void addBook(Book book) {
        book.setId(generateBookID());

        sqLiteInstance.insertRow("Book", book.getAll());

        System.out.println("Add book successfully");
    }

    public void deleteBook(String id) {
        String condition = "id = " + "'" + id + "'";
        sqLiteInstance.deleteRow("Book", condition);
    }

    private List<Book> createNewBookList(String condition, String sql) {
        List<Book> books = new ArrayList<>();
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, "%" + condition + "%");
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                String id = rs.getString("id");
                String title = rs.getString("title");
                String publisher = rs.getString("publisher");
                String publishedDate = rs.getString("publishedDate");
                int totalQuantity = rs.getInt("totalQuantity");
                int availableQuantity = rs.getInt("availableQuantity");
                double averageRating = rs.getDouble("averageRating");
                int ratingsCount = rs.getInt("ratingsCount");
                String ISBN = rs.getString("ISBN");
                String category = rs.getString("categories");
                String author = rs.getString("author");
                String description = rs.getString("description");

                books.add(new Book(id, title, publisher, publishedDate, totalQuantity, availableQuantity, averageRating, ratingsCount, ISBN, category, author, description));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return books;
    }

    public List<Book> searchBookByName(String bookName) {
        String sql = "SELECT * FROM Book WHERE title LIKE ?";
        return createNewBookList(bookName, sql);
    }

    public List<Book> searchBookByISBN(String ISBN) {
        String sql = "SELECT * FROM Book WHERE ISBN LIKE ?";
        return createNewBookList(ISBN, sql);
    }

    public List<Book> searchBookByCategory(String category) {
        String sql = "SELECT * FROM Book WHERE Categories = ?";
        return createNewBookList(category, sql);
    }

    public List<Book> searchDocumentByAuthor(String author) {
        String sql = "SELECT * FROM Book WHERE author = ?";
        return createNewBookList(author, sql);
    }

    public void issueBook(String ISBN, String userID) {
        List<List<Object>> lists = sqLiteInstance.find("Book", "ISBN", ISBN, "availableCopies");

        if(lists.isEmpty()) {
            System.out.println("ISBN is not found.");
            return;
        } else if ((int)lists.getFirst().getFirst() == 0) {
            System.out.println("No available copies found.");
            return;
        }

        lists = sqLiteInstance.findWithSQL("SELECT ? FROM bookTransaction", new Object[]{"MAX(transactionID) as Max"}, "Max");
        String temp = lists.get(0).get(0).toString().substring(1);
        String finalTransactionID = "T" + (Integer.parseInt(temp) + 1);

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

    void returnBook(String issueID) {
        String sql = "SELECT ISBN FROM bookTransaction WHERE transactionID = ?";
        List<List<Object>> lists = sqLiteInstance.find("Book", "transactionID", issueID, "ISBN");
        String finalISBN = lists.getFirst().getFirst().toString();

        sql = "UPDATE book SET availableCopies = availableCopies + 1 WHERE ISBN = ?";
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

    public static void main(String[] args) {
        Book book = new Book("B101", "Java", "LongNg", "2024-01-01", 100, 100, 0, 0, "1010-20202", "IT", "long", "long");
        DocumentManagement dm = new DocumentManagement();
        dm.addBook(book);
    }
}

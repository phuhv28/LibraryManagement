package librarymanagement.data;

import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class DocumentManagement {
    private static final DocumentManagement INSTANCE = new DocumentManagement();

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

    private void executeUpdate(String sql, DocumentManagement.PreparedStatementSetter setter) {
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            setter.setValues(stmt);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /*public String genarateDocID(documentType type) {
        String newId = startID[type.ordinal()];
        String sql = "SELECT MAX(" + nameType[type.ordinal()] + ".docID) FROM " + nameType[type.ordinal()];
        try (PreparedStatement stmt = connection.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            if (rs.next()) {
                String temp = rs.getString("Max");
                if (temp == null) {
                    newId += "101";
                } else {
                    temp = temp.substring(1);
                    newId += Integer.parseInt(temp) + 1;
                }
            } else {
                newId += "101";
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return newId;
    }*/

    //tam thoi co moi Book, con Magazine va Newspaper
    public void addBook(Book book) {
        String sql = "INSERT INTO Book (ISBN, title, author, publisher, categories, availableCopies, " +
                "pageCount, publishedDate, id, description, averageRating, ratingsCount) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        executeUpdate(sql, stmt -> {
            stmt.setString(1, book.getISBN());
            stmt.setString(2, book.getTitle());
            stmt.setString(3, book.getAuthor());
            stmt.setString(4, book.getPublisher());
            stmt.setString(5, book.getCategories());
            stmt.setInt(6, book.getAvailableCopies());
            stmt.setInt(7, book.getPageCount());
            stmt.setString(8, book.getPublishedDate());
            stmt.setString(9, book.getId());
            stmt.setString(10, book.getDescription());
            stmt.setDouble(11, book.getAverageRating());
            stmt.setInt(12, book.getRatingsCount());
        });

        System.out.println("Add book successfully");
    }

    public void deleteBook(String id) {
        String sql = "DELETE FROM " + id + " WHERE docID = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, id);
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

    private List<Book> createNewBookList(String condition, String sql) throws SQLException {
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

    public List<Book> searchBookByName(String bookName) throws SQLException {
        String sql = "SELECT * FROM Book WHERE title LIKE ?";
        return createNewBookList(bookName, sql);
    }

    public List<Book> searchBookByISBN(String ISBN) throws SQLException {
        String sql = "SELECT * FROM Book WHERE ISBN LIKE ?";
        return createNewBookList(ISBN, sql);
    }

    public List<Book> searchBookByCategory(String category) throws SQLException {
        String sql = "SELECT * FROM Book WHERE Categories = ?";
        return createNewBookList(category, sql);
    }

    public List<Book> searchDocumentByAuthor(String author) throws SQLException {
        String sql = "SELECT * FROM Book WHERE author = ?";
        return createNewBookList(author, sql);
    }

    public String issueBook(String ISBN, String userID) {
        String transactionID = "T";
        String sql = "SELECT MAX(transactionID) as Max FROM bookTransaction";
        try(PreparedStatement stmt = connection.prepareStatement(sql)) {
            ResultSet rs = stmt.executeQuery();
            if(rs.next()) {
                String temp = rs.getString("Max");
                if(temp == null) {
                    transactionID += "101";
                } else {
                    temp = temp.substring(1);
                    transactionID += Integer.parseInt(temp) + 1;
                }
            } else {
                transactionID += "101";
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        String finalTransactionID = transactionID;

        LocalDate today = LocalDate.now();
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        sql = "INSERT INTO bookTransaction (userId, ISBN, borrowDate, returnDate, transactionID) VALUES (?, ?, ?, ?, ?)";

        executeUpdate(sql, stmt -> {
            stmt.setString(1, userID);
            stmt.setString(2, ISBN);
            stmt.setString(3, today.format(dateFormatter));
            stmt.setString(4, null);
            stmt.setString(5, finalTransactionID);
        });
    }

    void returnBook(String issueID) {

    }
}

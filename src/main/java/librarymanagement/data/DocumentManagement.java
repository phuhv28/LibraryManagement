package librarymanagement.data;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DocumentManagement {
    private static final DocumentManagement INSTANCE = new DocumentManagement();

    private static final SQLiteInstance sqLiteInstance = SQLiteInstance.getInstance();

    private Connection connection;

    public static DocumentManagement getInstance() {
        return INSTANCE;
    }

    public DocumentManagement() {
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

    public boolean checkIfHasBookISBN(String isbn) {
        List<List<Object>> res = sqLiteInstance.find("Book", "ISBN", isbn, "ISBN");
        if (res.isEmpty() || res.get(0).isEmpty()) {
            return false;
        }
        return res.get(0).get(0) != null;
    }

    public boolean checkIfHasBookId(String id) {
        List<List<Object>> res = sqLiteInstance.find("Book", "id", id, "id");
        if (res.isEmpty() || res.get(0).isEmpty()) {
            return false;
        }
        return res.get(0).get(0) != null;
    }

    //tam thoi co moi Book, con Magazine va Newspaper
    public void addBook(Book book) {
        if (checkIfHasBookISBN(book.getISBN())) {
            System.out.println("Book already exists");
            return;
        }

        book.setId(generateBookID());

        sqLiteInstance.insertRow("Book", book.getAll());

        System.out.println("Add book successfully");
    }

    public void deleteBook(String id) {
        if (!checkIfHasBookId(id)) {
            System.out.println("Book does not exist");
            return;
        }
        String condition = "id = " + "'" + id + "'";
        sqLiteInstance.deleteRow("Book", condition);
    }

    private List<Book> createNewBookList(String condition, String sql) {
        List<Book> books = new ArrayList<>();
        try (PreparedStatement stmt = sqLiteInstance.connection.prepareStatement(sql)) {
            stmt.setString(1, "%" + condition + "%");
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                String isbn = rs.getString("ISBN");
                String id = rs.getString("id");
                String title = rs.getString("title");
                String author = rs.getString("author");
                String publisher = rs.getString("publisher");
                String publishedDate = rs.getString("publishedDate");
                String categories = rs.getString("categories");
                int pageCount = rs.getInt("pageCount");
                int availableCopies = rs.getInt("availableCopies");
                String description = rs.getString("description");
                float averageRating = rs.getFloat("averageRating");
                int ratingCount = rs.getInt("ratingsCount");

                books.add(new Book(id, title, publisher, publishedDate, pageCount, availableCopies, averageRating, ratingCount, isbn, categories, author, description));
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
        String sql = "SELECT * FROM Book WHERE Categories LIKE ?";
        return createNewBookList(category, sql);
    }

    public List<Book> searchBookByAuthor(String author) {
        String sql = "SELECT * FROM Book WHERE author LIKE ?";
        return createNewBookList(author, sql);
    }

    public List<Book> searchBookById(String id) {
        String sql = "SELECT * FROM Book WHERE id LIKE ?";
        return createNewBookList(id, sql);
    }

    public void fixBook(String id, String attribute, String value) {
        if (checkIfHasBookId(id)) {
            System.out.println("Book already exists");
            return;
        }
        String sql = "UPDATE Book SET ? = ? WHERE id = ?";
        sqLiteInstance.executeUpdate(sql, stmt -> {
            stmt.setString(1, attribute);
            stmt.setString(2, value);
            stmt.setString(3, id);
        });
        System.out.println("Fix book successfully");
    }
}

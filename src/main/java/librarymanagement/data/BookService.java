package librarymanagement.data;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BookService implements DocumentService<Book> {
    private static final BookService INSTANCE = new BookService();

    private static final SQLiteInstance sqLiteInstance = SQLiteInstance.getInstance();

    private Connection connection;

    public static BookService getInstance() {
        return INSTANCE;
    }

    public BookService() {
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

    @Override
    public void addDocument(Book book) {
        if (checkIfHasBookISBN(book.getISBN())) {
            System.out.println("Book already exists");
            return;
        }

        book.setId(generateBookID());

        sqLiteInstance.insertRow("Book", book.getAll());

        System.out.println("Add book successfully");
    }

    @Override
    public void deleteDocument(String id) {
        if (!checkIfHasBookId(id)) {
            System.out.println("Book does not exist");
            return;
        }
        String condition = "id = " + "'" + id + "'";
        sqLiteInstance.deleteRow("Book", condition);
    }

    @Override
    public void updateDocument(Book book) {
        deleteDocument(book.getId());

        sqLiteInstance.insertRow("Book", book.getAll());

        System.out.println("Edit book successfully");
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

    public List<Book> searchBookByTitle(String title) {
        String sql = "SELECT * FROM Book WHERE title LIKE ?";
        return createNewBookList(title, sql);
    }

    public Book searchBookByISBN(String ISBN) {
        String sql = "SELECT * FROM Book WHERE ISBN LIKE ?";
        return createNewBookList(ISBN, sql).getFirst();
    }

    public List<Book> searchBookByCategory(String category) {
        String sql = "SELECT * FROM Book WHERE Categories LIKE ?";
        return createNewBookList(category, sql);
    }

    public List<Book> searchBookByAuthor(String author) {
        String sql = "SELECT * FROM Book WHERE author LIKE ?";
        return createNewBookList(author, sql);
    }

    @Override
    public Book findDocumentById(String id) {
        String sql = "SELECT * FROM Book WHERE id LIKE ?";
        return createNewBookList(id, sql).getFirst();
    }
}

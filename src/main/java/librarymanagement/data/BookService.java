package librarymanagement.data;

import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class BookService implements DocumentService<Book> {
    private static final SQLiteInstance sqLiteInstance = SQLiteInstance.getInstance();

    private String generateNewID() {
        String newId;

        List<List<Object>> result = sqLiteInstance.findNotCondition("Book", "Max(id)");
        if (result.getFirst().getFirst() == null) {
            newId = "B101";
        } else {
            String temp = result.getFirst().getFirst().toString().substring(1);
            newId = "B" + (Integer.parseInt(temp) + 1);
        }

        return newId;
    }

    private boolean checkIfHasBookISBN(String isbn) {
        List<List<Object>> res = sqLiteInstance.find("Book", "ISBN", isbn, "ISBN");
        if (res.isEmpty() || res.getFirst().isEmpty()) {
            return false;
        }
        return res.getFirst().getFirst() != null;
    }

    private boolean checkIfHasBookId(String id) {
        List<List<Object>> res = sqLiteInstance.find("Book", "id", id, "id");
        if (res.isEmpty() || res.getFirst().isEmpty()) {
            return false;
        }
        return res.getFirst().getFirst() != null;
    }

    public boolean checkIfHasTitleAndAuthor(String title, String author) {
        String sql = "SELECT ISBN FROM Book WHERE title = ? AND author = ?";
        List<List<Object>> lists = sqLiteInstance.findWithSQL(sql, new Object[]{title, author}, "ISBN");
        return !lists.isEmpty();
    }

    @Override
    public boolean addDocument(Book book) {
        if ((book.getISBN() == null) && checkIfHasTitleAndAuthor(book.getTitle(), book.getAuthor())) {
            return false;
        } else if (book.getISBN().equals("N/A")) {
            if (checkIfHasTitleAndAuthor(book.getTitle(), book.getAuthor())) {
                return false;
            }
        } else if (checkIfHasBookISBN(book.getISBN())) {
            System.out.println("Book already exists");
            return false;
        }
        book.setId(generateNewID());
        sqLiteInstance.insertRow("Book", book.getAll());
        System.out.println("Add book successfully");
        return true;
    }

    @Override
    public boolean deleteDocument(String id) {
        if (!checkIfHasBookId(id)) {
            System.out.println("Book does not exist");
            return false;
        }

        String condition = "id = " + "'" + id + "'";
        sqLiteInstance.deleteRow("Book", condition);
        return true;
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
            if (condition != null && !condition.isEmpty()) {
                stmt.setString(1, "%" + condition + "%");
            }
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
                String linkToAPI = rs.getString("linkToAPI");
                byte[] thumbnailImage = rs.getBytes("thumbnailImage");

                LocalDate finalDate = null;
                if (publishedDate != null && !publishedDate.equals("N/A")) {
                    if (publishedDate.length() == 4) {
                        finalDate = LocalDate.of(Integer.parseInt(publishedDate), 1, 1);
                    } else if (publishedDate.length() == 7) {
                        DateTimeFormatter yearMonthFormatter = new DateTimeFormatterBuilder()
                                .appendPattern("yyyy-MM")
                                .parseDefaulting(java.time.temporal.ChronoField.DAY_OF_MONTH, 1)
                                .toFormatter(Locale.getDefault());
                        finalDate = LocalDate.parse(publishedDate, yearMonthFormatter);
                    } else {
                        finalDate = LocalDate.parse(publishedDate);
                    }
                }

                books.add(new Book(id, title, publisher, finalDate, pageCount, availableCopies,
                        averageRating, ratingCount, isbn, categories, author, description, linkToAPI, thumbnailImage));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        if (books.isEmpty()) {
            return null;
        }

        return books;
    }

    public List<Book> searchBookByTitle(String title) {
        String sql = "SELECT * FROM Book WHERE title LIKE ?";
        return createNewBookList(title, sql);
    }

    public Book searchBookByISBN(String ISBN) {
        String sql = "SELECT * FROM Book WHERE ISBN LIKE ?";
        List<Book> books = createNewBookList(ISBN, sql);
        if (books == null) {
            return null;
        }

        return books.getFirst();
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
        List<Book> books = createNewBookList(id, sql);
        if (books == null) {
            return null;
        }
        return books.getFirst();
    }

    public List<Book> getRecentlyAddedBooks() {
        String sql = "SELECT * FROM Book ORDER BY id DESC LIMIT 10";
        return createNewBookList(null, sql);
    }

    public List<Book> getAllDocument() {
        String sql = "SELECT * FROM Book ORDER BY id";
        return createNewBookList(null, sql);
    }
}

package librarymanagement.data;

import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/** Class handles handle operations related to booksoperations related to books (Add, edit, delete,...).*/
public class BookService implements DocumentService<Book> {
    private static final SQLiteInstance sqLiteInstance = SQLiteInstance.getInstance();

    /**
     * Generates a new ID for a book by retrieving the maximum current ID from the database
     * and incrementing it. If no books exist in the database (i.e., no IDs are found),
     * the method starts with "B101" as the new ID.
     *
     * <p>The method retrieves the highest existing ID from the "Book" table, extracts the
     * numeric part of the ID, increments it, and generates a new ID in the format "B" followed
     * by the incremented number.</p>
     *
     * @return a new unique ID for a book as a {@link String}. The ID will be in the format "B" followed by a number.
     */
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

    /**
     * Checks if a book with the given ISBN exists in the database.
     *
     * <p>This method queries the database to check if a book with the specified ISBN exists.
     * It searches for the ISBN in the "Book" table and returns {@code true} if a match is found,
     * otherwise it returns {@code false}.</p>
     *
     * @param isbn the ISBN of the book to be checked.
     * @return {@code true} if the book with the given ISBN exists, otherwise {@code false}.
     */
    private boolean checkIfHasBookISBN(String isbn) {
        List<List<Object>> res = sqLiteInstance.find("Book", "ISBN", isbn, "ISBN");
        if (res.isEmpty() || res.getFirst().isEmpty()) {
            return false;
        }
        return res.getFirst().getFirst() != null;
    }

    /**
     * Checks if a book with the given ID exists in the database.
     *
     * <p>This method queries the database to check if a book with the specified ID exists.
     * It searches for the ID in the "Book" table and returns {@code true} if a match is found,
     * otherwise it returns {@code false}.</p>
     *
     * @param id the ID of the book to be checked.
     * @return {@code true} if the book with the given ID exists, otherwise {@code false}.
     */
    private boolean checkIfHasBookId(String id) {
        List<List<Object>> res = sqLiteInstance.find("Book", "id", id, "id");
        if (res.isEmpty() || res.getFirst().isEmpty()) {
            return false;
        }
        return res.getFirst().getFirst() != null;
    }

    /**
     * Checks if a book with the given title and author exists in the database.
     *
     * <p>This method queries the database to check if a book with the specified title and author exists.
     * It returns {@code true} if a book with the given title and author is found, otherwise {@code false}.</p>
     *
     * @param title the title of the book to be checked.
     * @param author the author of the book to be checked.
     * @return {@code true} if a book with the given title and author exists, otherwise {@code false}.
     */
    public boolean checkIfHasTitleAndAuthor(String title, String author) {
        String sql = "SELECT ISBN FROM Book WHERE title = ? AND author = ?";
        List<List<Object>> lists = sqLiteInstance.findWithSQL(sql, new Object[]{title, author}, "ISBN");
        return !lists.isEmpty();
    }

    /**
     * Adds a new book to the database.
     *
     * <p>This method checks if the book can be added based on its ISBN, title, and author.
     * If the book already exists in the database (either by ISBN or by a matching title and author),
     * it will not be added.</p>
     *
     * @param book the {@link Book} object to be added to the database.
     * @return {@code true} if the book was successfully added, otherwise {@code false}.
     */
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

    /**
     * Deletes a book from the database by its ID.
     *
     * <p>This method first checks if the book with the specified ID exists in the database.
     * If the book exists, it is deleted; otherwise, a message is printed, and the book is not deleted.</p>
     *
     * @param id the ID of the book to be deleted.
     * @return {@code true} if the book was successfully deleted, {@code false} if the book does not exist.
     */
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

    /**
     * Updates a book's information in the database.
     *
     * <p>This method first deletes the existing book with the given ID, then inserts the updated book data into the database.</p>
     *
     * @param book the {@link Book} object containing the updated information to be saved in the database.
     */
    @Override
    public void updateDocument(Book book) {
        deleteDocument(book.getId());

        sqLiteInstance.insertRow("Book", book.getAll());

        System.out.println("Edit book successfully");
    }

    /**
     * Creates a list of books based on the provided condition and SQL query.
     *
     * <p>This method executes the provided SQL query with an optional condition, retrieves the book details from the result set,
     * and constructs a list of {@link Book} objects. It handles different date formats for the published date and returns the
     * list of books, or null if no books are found.</p>
     *
     * @param condition the condition to filter the books. It is used in the SQL query to filter by matching titles or authors.
     *                  If null or empty, the condition is not applied.
     * @param sql the SQL query string to execute, which should include a placeholder for the condition.
     * @return a list of {@link Book} objects created from the result of the SQL query, or null if no books are found.
     */
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

    /**
     * Searches for books by title.
     *
     * <p>This method executes an SQL query to find books whose titles contain the specified keyword.</p>
     *
     * @param title the title (or part of the title) of the book to search for.
     *              The search is case-insensitive and uses the SQL `LIKE` operator.
     * @return a list of {@link Book} objects that match the search criteria, or null if no books are found.
     */
    public List<Book> searchBookByTitle(String title) {
        String sql = "SELECT * FROM Book WHERE title LIKE ?";
        return createNewBookList(title, sql);
    }

    /**
     * Searches for a book by its ISBN.
     *
     * <p>This method executes an SQL query to find a book whose ISBN matches the specified value.</p>
     *
     * @param ISBN the ISBN of the book to search for.
     * @return a {@link Book} object that matches the given ISBN, or null if no book is found.
     */
    public Book searchBookByISBN(String ISBN) {
        String sql = "SELECT * FROM Book WHERE ISBN LIKE ?";
        List<Book> books = createNewBookList(ISBN, sql);
        if (books == null) {
            return null;
        }

        return books.getFirst();
    }

    /**
     * Searches for books by their category.
     *
     * <p>This method executes an SQL query to find books whose category matches the specified value.</p>
     *
     * @param category the category of the books to search for.
     * @return a list of {@link Book} objects that match the given category, or null if no books are found.
     */
    public List<Book> searchBookByCategory(String category) {
        String sql = "SELECT * FROM Book WHERE Categories LIKE ?";
        return createNewBookList(category, sql);
    }

    /**
     * Searches for books by their author.
     *
     * <p>This method executes an SQL query to find books whose author matches the specified value.</p>
     *
     * @param author the author of the books to search for.
     * @return a list of {@link Book} objects that match the given author, or null if no books are found.
     */
    public List<Book> searchBookByAuthor(String author) {
        String sql = "SELECT * FROM Book WHERE author LIKE ?";
        return createNewBookList(author, sql);
    }

    /**
     * Finds a book document by its ID.
     *
     * <p>This method executes an SQL query to retrieve a book whose ID matches the specified value.</p>
     *
     * @param id the ID of the book to search for.
     * @return the {@link Book} object with the matching ID, or {@code null} if no book is found.
     */
    @Override
    public Book findDocumentById(String id) {
        String sql = "SELECT * FROM Book WHERE id LIKE ?";
        List<Book> books = createNewBookList(id, sql);
        if (books == null) {
            return null;
        }
        return books.getFirst();
    }

    /**
     * Retrieves the 10 most recently added books.
     *
     * <p>This method retrieves the 10 most recently added books by querying the database and ordering
     * the results by the book ID in descending order. This ensures the most recently added books appear first.</p>
     *
     * @return a list of the 10 most recently added {@link Book} objects, or an empty list if no books are found.
     */
    public List<Book> getRecentlyAddedBooks() {
        String sql = "SELECT * FROM Book ORDER BY id DESC LIMIT 10";
        return createNewBookList(null, sql);
    }

    /**
     * Retrieves all books from the database, ordered by their ID.
     *
     * <p>This method retrieves all books stored in the database and orders them by their ID in ascending order.</p>
     *
     * @return a list of all {@link Book} objects, or an empty list if no books are found.
     */
    public List<Book> getAllDocument() {
        String sql = "SELECT * FROM Book ORDER BY id";
        return createNewBookList(null, sql);
    }
}

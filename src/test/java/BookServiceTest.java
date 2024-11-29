import librarymanagement.data.Book;
import librarymanagement.data.BookService;
import librarymanagement.data.SQLiteInstance;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assumptions.*;

class BookServiceTest {
    private static final BookService bookService = new BookService();
    private static final List<Book> testBooks = new ArrayList<>();
    @BeforeAll
    static void setUp() {
        testBooks.add(new Book(
                null, "Temp1", "TechWorld Publishing", LocalDate.of(2022, 6, 15), 300,
                15, 4.3, 120, "1234567890123", "Fantasy Programming", "Alice Codewright",
                "An adventurous journey into the mystical world of programming languages.",
                "https://example.com/temp1", null
        ));

        testBooks.add(new Book(
                null, "Temp2", "AI Chronicles Press", LocalDate.of(2020, 9, 10), 280,
                10, 4.6, 95, "9876543210987", "Algorithms", "Bob Algoson",
                "A collection of captivating tales about famous algorithms and their creators.",
                "https://example.com/temp2", null
        ));

        testBooks.add(new Book(
                null, "Temp3", "Cosmic Coders", LocalDate.of(2019, 11, 5), 450,
                20, 4.7, 200, "5678901234567", "Science Fiction", "Dr. Code Asteroid",
                "A thrilling story of a programmer debugging the simulation of the universe.",
                "https://example.com/temp3", null
        ));

        testBooks.add(new Book(
                null, "Temp4", "Pattern Masters Inc.", LocalDate.of(2021, 2, 18), 350,
                5, 4.5, 80, "2468101214168", "Software Design", "Claire Pattern",
                "Exploring futuristic design patterns for software engineering in 2050.",
                "https://example.com/temp4", null
        ));

        testBooks.add(new Book(
                null, "Temp5", "Epic Dev Press", LocalDate.of(2023, 8, 22), 500,
                8, 4.8, 150, "3141592653589", "Programming Fiction", "Luke Devwalker",
                "An epic tale of battle between programmers and rogue compilers in a galaxy far away.",
                "https://example.com/temp5", null
        ));

        for (Book book : testBooks) {
            bookService.addDocument(book);
        }
    }

    @AfterAll
    static void tearDown() {
        Book book1 = bookService.searchBookByISBN("1234567890123");
        if (book1 != null) bookService.deleteDocument(book1.getId());
        book1 = bookService.searchBookByISBN("9876543210987");
        if (book1 != null) bookService.deleteDocument(book1.getId());
        book1 = bookService.searchBookByISBN("5678901234567");
        bookService.deleteDocument(book1.getId());
        book1 = bookService.searchBookByISBN("2468101214168");
        bookService.deleteDocument(book1.getId());
        book1 = bookService.searchBookByISBN("3141592653589");
        bookService.deleteDocument(book1.getId());
    }

    @ParameterizedTest
    @CsvSource({"Temp1, Long",
                "Temp2, Alice",
                "Temp3, Dr. Strange"})
    void testCheckIfHasTitleAndAuthorTrue(String title, String author) {
        assumeTrue(bookService.checkIfHasTitleAndAuthor(title, author),
                "Result with " + title + " " + author  + "need be true.");
    }

    @ParameterizedTest
    @CsvSource({"Temp4, Capitan America",
            "Temp5, Capybara"})
    void testCheckIfHasTitleAndAuthorFalse(String title, String author) {
        assumeFalse(bookService.checkIfHasTitleAndAuthor(title, author),
                "Result with " + title + " " + author  + " need be false.");
    }

    @ParameterizedTest
    @CsvSource({"1234567890123, 0",
                "9876543210987, 1"})
    void testDeleteDocument(String ISBN, int index) {
        Book book1 = bookService.searchBookByISBN(ISBN);
        bookService.deleteDocument(book1.getId());
        assumeTrue(bookService.searchBookByISBN(ISBN) == null, "Document was not deleted.");
        bookService.addDocument(testBooks.get(index));
    }

    @Test
    void TestAddDocument() {
        bookService.addDocument(testBooks.getFirst());
        Book book = bookService.searchBookByISBN(testBooks.getFirst().getISBN());
        assumeTrue(testBooks.getFirst().getTitle().equals(book.getTitle()), "Document was not added.");
        bookService.addDocument(testBooks.get(1));
        book = bookService.searchBookByISBN(testBooks.get(1).getISBN());
        assumeTrue(testBooks.get(1).getTitle().equals(book.getTitle()), "Document was not added.");
        assumeFalse(bookService.addDocument(testBooks.get(1)), "Document already exists.");
    }

    @ParameterizedTest
    @CsvSource({"1234567890123, Long",
            "9876543210987, Alice",
            "5678901234567, Dr. Strange"})
    void TestUpdateDocument(String ISBN, String author) {
        Book book = bookService.searchBookByISBN(ISBN);
        book.setAuthor(author);
        bookService.updateDocument(book);
        book = bookService.searchBookByISBN(ISBN);
        assumeTrue(book.getAuthor().equals(author), "Document was not updated.");
    }

    @ParameterizedTest
    @CsvSource({"Temp1, 0", "Temp2, 1", "Temp3, 2", "Temp4, 3", "Temp5, 4"})
    void TestSearchBookByTitle(String title, int testBook) {
        List<Book> books = bookService.searchBookByTitle(title);
        assumeFalse(books.isEmpty(), "Result with " + title + " was not searched.");
        for (Book book : books) {
            if (book.getTitle().equals(title)) {
                assumeTrue(book.getCategories().equals(testBooks.get(testBook).getCategories()),
                        "Incorrect book information when search book by title.");
            }
        }
    }

    @ParameterizedTest
    @CsvSource({"1234567890123, 0",
            "9876543210987, 1",
            "5678901234567, 2",
            "2468101214168, 3"})
    void TestSearchBookByISBN(String ISBN, int testBook) {
        Book book = bookService.searchBookByISBN(ISBN);
        assumeFalse(book == null, "Result with " + ISBN + " was not searched.");
        assumeTrue(book.getCategories().equals(testBooks.get(testBook).getCategories()),
                "Incorrect book information when search book by ISBN.");

    }

    @ParameterizedTest
    @CsvSource({"0", "1", "2", "3", "4"})
    void TestSearchBookByCategory(int index) {
        List<Book> books = bookService.searchBookByCategory(testBooks.get(index).getCategories());
        assumeFalse(books.isEmpty(), "Result with " + index + " was not searched.");
        for (Book book : books) {
            if (book.getCategories().equals(testBooks.get(index).getCategories())) {
                assumeTrue(book.getTitle().equals(testBooks.get(index).getTitle()),
                        "Incorrect book information when search book by Category.");
            }
        }
    }

    @ParameterizedTest
    @CsvSource({"3", "4"})
    void TestSearchBookByAuthor(int index) {
        List<Book> books = bookService.searchBookByAuthor(testBooks.get(index).getAuthor());
        assumeFalse(books.isEmpty(), "Result with " + index + " was not searched.");
        for (Book book : books) {
            if (book.getCategories().equals(testBooks.get(index).getCategories())) {
                assumeTrue(book.getTitle().equals(testBooks.get(index).getTitle()),
                        "Incorrect book information when search book by Author.");
            }
        }
    }

    @ParameterizedTest
    @CsvSource({"0", "1", "2", "3", "4"})
    void TestFindDocumentById(int index) {
        Book book = bookService.searchBookByISBN(testBooks.get(index).getISBN());
        book = bookService.findDocumentById(book.getId());
        assumeFalse(book == null, "Result with " + index + " was not searched.");

    }

    @Test
    void TestGetRecentlyAddedBooks() {
        List<Book> books = bookService.getRecentlyAddedBooks();
        for (int i = 0; i < 5; i++) {
            String temp = books.get(i).getTitle().substring(0, 4);
            assumeTrue(temp.equals("Temp"), "Incorrect book information when searching recently-added books.");
        }

    }

    @Test
    void getAllDocument() {
        String sql = "SELECT COUNT(id) as total FROM Book";
        int total = (int) SQLiteInstance.getInstance().findWithSQL(sql, new Object[]{}, "total").getFirst().getFirst();
        List<Book> books = bookService.getAllDocument();
        assumeTrue(books.size() == total, "Incorrect number of books.");
    }
}
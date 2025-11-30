
import java.time.LocalDate;

import librarymanagement.entity.Book;
import librarymanagement.entity.DocumentType;
import librarymanagement.gui.models.BookService;
import librarymanagement.gui.models.DocumentService;
import librarymanagement.gui.models.DocumentServiceFactory;
import librarymanagement.gui.viewmodels.AddDocumentViewModel;
import librarymanagement.utils.GoogleBooksAPI;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.when;

class AddDocumentViewModelTest {

    private AddDocumentViewModel vm;
    private DocumentService<Book> mockService;

    @BeforeEach
    void setup() {
        mockService = mock(BookService.class);

        MockedStatic<DocumentServiceFactory> factoryMock =
                mockStatic(DocumentServiceFactory.class);

        factoryMock.when(() ->
                DocumentServiceFactory.getDocumentService(DocumentType.BOOK)
        ).thenReturn(mockService);

        vm = new AddDocumentViewModel();
        factoryMock.close();
    }

    @Test
    void testBookPropertySync_EP() {

        Book b = new Book();
        b.setISBN("1234567890");
        b.setTitle("Title");
        b.setPublisher("Publisher");
        b.setAuthor("Author");
        b.setCategories("Novel");
        b.setDescription("Desc");
        b.setAvailableCopies(10);
        b.setPageCount(150);
        b.setPublishedDate(LocalDate.of(2020, 1, 1));

        vm.bookProperty().set(b);

        assertEquals("1234567890", vm.getISBN());
        assertEquals("Title", vm.getTitle());
        assertEquals("Publisher", vm.getPublisher());
        assertEquals("Author", vm.getAuthor());
        assertEquals("Novel", vm.getCategory());
        assertEquals("Desc", vm.getDescription());
        assertEquals(10, vm.getAvailableCopies());
        assertEquals(150, vm.getPageCount());
        assertEquals(LocalDate.of(2020, 1, 1), vm.getPublicationDate());
    }

    @Test
    void testFieldSyncBackToBook_EP() {
        Book b = new Book();
        vm.bookProperty().set(b);

        vm.ISBNProperty().set("ABC");
        vm.titleProperty().set("NewTitle");
        vm.publisherProperty().set("NewPub");
        vm.authorProperty().set("NewAuthor");
        vm.categoryProperty().set("Sci-Fi");
        vm.descriptionProperty().set("NewDesc");
        vm.availableCopiesProperty().set(20);
        vm.pageCountProperty().set(200);
        vm.publicationDateProperty().set(LocalDate.of(2024, 1, 1));

        assertEquals("ABC", b.getISBN());
        assertEquals("NewTitle", b.getTitle());
        assertEquals("NewPub", b.getPublisher());
        assertEquals("NewAuthor", b.getAuthor());
        assertEquals("Sci-Fi", b.getCategories());
        assertEquals("NewDesc", b.getDescription());
        assertEquals(20, b.getAvailableCopies());
        assertEquals(200, b.getPageCount());
        assertEquals(LocalDate.of(2024, 1, 1), b.getPublishedDate());
    }

    @Test
    void testTitleNull_EP() {
        Book b = new Book();
        vm.bookProperty().set(b);

        vm.titleProperty().set(null);

        assertNull(b.getTitle());
    }

    @Test
    void testPublicationDateNull_EP() {
        Book b = new Book();
        vm.bookProperty().set(b);

        vm.publicationDateProperty().set(null);

        assertNull(b.getPublishedDate());
    }

    @Test
    void testPageCountNegative_BVA() {
        Book b = new Book();
        vm.bookProperty().set(b);

        vm.pageCountProperty().set(-1);

        assertEquals(-1, b.getPageCount());
    }

    @Test
    void testPageCountZero_BVA() {
        Book b = new Book();
        vm.bookProperty().set(b);

        vm.pageCountProperty().set(0);

        assertEquals(0, b.getPageCount());
    }

    @Test
    void testPageCountOne_BVA() {
        Book b = new Book();
        vm.bookProperty().set(b);

        vm.pageCountProperty().set(1);

        assertEquals(1, b.getPageCount());
    }

    @Test
    void testAvailableCopiesNegative_BVA() {
        Book b = new Book();
        vm.bookProperty().set(b);

        vm.availableCopiesProperty().set(-1);

        assertEquals(-1, b.getAvailableCopies());
    }

    @Test
    void testAddDocumentSuccess_EP() {
        Book b = new Book();
        vm.bookProperty().set(b);

        when(mockService.addDocument(b)).thenReturn(true);

        assertTrue(vm.addDocument());
    }

    @Test
    void testAddDocumentFail_EP() {
        Book b = new Book();
        vm.bookProperty().set(b);

        when(mockService.addDocument(b)).thenReturn(false);

        assertFalse(vm.addDocument());
    }

    @Test
    void testFillUsingISBN_Success_EP() {
        vm.ISBNProperty().set("123");

        Book apiBook = new Book();
        apiBook.setTitle("API Title");

        try (MockedStatic<GoogleBooksAPI> apiMock =
                     mockStatic(GoogleBooksAPI.class)) {

            apiMock.when(() -> GoogleBooksAPI.searchBookByISBN("123"))
                    .thenReturn(apiBook);

            boolean result = vm.fillUsingISBN();

            assertTrue(result);
            assertEquals(apiBook, vm.bookProperty().get());
            assertEquals("API Title", vm.getTitle());
        }
    }

    @Test
    void testFillUsingISBN_NotFound_EP() {
        vm.ISBNProperty().set("999");

        try (MockedStatic<GoogleBooksAPI> apiMock =
                     mockStatic(GoogleBooksAPI.class)) {

            apiMock.when(() -> GoogleBooksAPI.searchBookByISBN("999"))
                    .thenReturn(null);

            assertFalse(vm.fillUsingISBN());
        }
    }

    @Test
    void testISBNBoundary10_BVA() {
        String isbn = "1234567890";
        vm.ISBNProperty().set(isbn);

        Book b = new Book();
        b.setTitle("Book10");

        try (MockedStatic<GoogleBooksAPI> apiMock =
                     mockStatic(GoogleBooksAPI.class)) {

            apiMock.when(() -> GoogleBooksAPI.searchBookByISBN(isbn))
                    .thenReturn(b);

            assertTrue(vm.fillUsingISBN());
            assertEquals("Book10", vm.getTitle());
        }
    }

    @Test
    void testISBNBoundary13_BVA() {
        String isbn = "1234567890123";
        vm.ISBNProperty().set(isbn);

        Book b = new Book();
        b.setTitle("Book13");

        try (MockedStatic<GoogleBooksAPI> apiMock =
                     mockStatic(GoogleBooksAPI.class)) {

            apiMock.when(() -> GoogleBooksAPI.searchBookByISBN(isbn))
                    .thenReturn(b);

            assertTrue(vm.fillUsingISBN());
            assertEquals("Book13", vm.getTitle());
        }
    }

    @Test
    void testISBNBoundary9_Invalid_BVA() {
        String isbn = "123456789";
        vm.ISBNProperty().set(isbn);

        try (MockedStatic<GoogleBooksAPI> apiMock =
                     mockStatic(GoogleBooksAPI.class)) {

            apiMock.when(() -> GoogleBooksAPI.searchBookByISBN(isbn))
                    .thenReturn(null);

            assertFalse(vm.fillUsingISBN());
        }
    }

    @Test
    void testISBNBoundary14_Invalid_BVA() {
        String isbn = "12345678901234";
        vm.ISBNProperty().set(isbn);

        try (MockedStatic<GoogleBooksAPI> apiMock =
                     mockStatic(GoogleBooksAPI.class)) {

            apiMock.when(() -> GoogleBooksAPI.searchBookByISBN(isbn))
                    .thenReturn(null);

            assertFalse(vm.fillUsingISBN());
        }
    }
}

import librarymanagement.entity.Book;
import librarymanagement.gui.models.BookService;
import librarymanagement.utils.SQLiteInstance;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class BookServiceTest {

    private SQLiteInstance mockDB;
    private BookService service;

    @BeforeEach
    void setup() {
        mockDB = mock(SQLiteInstance.class);
        BookService.setSqLiteInstance(mockDB);
        service = BookService.getInstance();
    }

    @Test
    void testAddDocument_success() {
        Book b = new Book();
        b.setISBN("ABC123");
        b.setTitle("Java");
        b.setAuthor("Author");

        when(mockDB.find("Book", "ISBN", "ABC123", "ISBN"))
                .thenReturn(List.of());

        when(mockDB.findNotCondition("Book", "Max(id)"))
                .thenReturn(List.of());


        boolean ok = service.addDocument(b);

        assertTrue(ok);
        verify(mockDB).insertRow(eq("Book"), anyList());
    }

    @Test
    void testAddDocument_duplicateISBN() {
        Book b = new Book();
        b.setISBN("dup");
        b.setTitle("T");
        b.setAuthor("A");

        // ISBN đã tồn tại
        when(mockDB.find("Book", "ISBN", "dup", "ISBN"))
                .thenReturn(List.of(List.of("dup")));

        boolean ok = service.addDocument(b);

        assertFalse(ok);
        verify(mockDB, never()).insertRow(anyString(), (List<Object>) any());
    }

    @Test
    void testDeleteDocument_success() {
        when(mockDB.find("Book", "id", "B101", "id"))
                .thenReturn(List.of(List.of("B101")));

        boolean ok = service.deleteDocument("B101");

        assertTrue(ok);
        verify(mockDB).deleteRow("Book", "id = 'B101'");
    }

    @Test
    void testDeleteDocument_notFound() {
        when(mockDB.find("Book", "id", "X", "id"))
                .thenReturn(List.of());

        boolean ok = service.deleteDocument("X");
        assertFalse(ok);
        verify(mockDB, never()).deleteRow(anyString(), anyString());
    }

    @Test
    void testUpdateDocument_callsDeleteAndInsert() {
        Book book = new Book();
        book.setId("B999");

        when(mockDB.find("Book", "id", "B999", "id"))
                .thenReturn(List.of(List.of("B999")));

        service.updateDocument(book);

        verify(mockDB).deleteRow("Book", "id = 'B999'");
        verify(mockDB).insertRow(eq("Book"), anyList());
    }

    @Test
    void testSearchBookByTitle_callsCreateNewBookList() {
        Book fake = new Book("B1", "Java", "Pub", LocalDate.now(),
                100, 3, 4.5, 11, "isbn", "cat", "author", "desc");

        when(mockDB.createNewBookList("Java", "SELECT * FROM Book WHERE title LIKE ?"))
                .thenReturn(List.of(fake));

        List<Book> list = service.searchBookByTitle("Java");

        assertEquals(1, list.size());
        assertEquals("B1", list.getFirst().getId());
    }

    @Test
    void testSearchBookByAuthor() {
        Book fake = new Book("B7", "T", "P", LocalDate.now(),
                10, 2, 4, 3, "i", "cat", "John", "d");

        when(mockDB.createNewBookList("John", "SELECT * FROM Book WHERE author LIKE ?"))
                .thenReturn(List.of(fake));

        List<Book> list = service.searchBookByAuthor("John");

        assertEquals(1, list.size());
        assertEquals("B7", list.get(0).getId());
    }

    @Test
    void testFindDocumentById() {
        Book fake = new Book("B55", "T", "P", LocalDate.now(),
                10, 2, 4, 3, "i", "c", "a", "d");

        when(mockDB.createNewBookList("B55", "SELECT * FROM Book WHERE id LIKE ?"))
                .thenReturn(List.of(fake));

        Book b = service.findDocumentById("B55");

        assertNotNull(b);
        assertEquals("B55", b.getId());
    }

    @Test
    void testGetRecentlyAddedBooks() {
        Book fake = new Book("B200", "T", "P", LocalDate.now(),
                1, 1, 1, 1, "i", "c", "a", "d");

        when(mockDB.createNewBookList(null, "SELECT * FROM Book ORDER BY id DESC LIMIT 10"))
                .thenReturn(List.of(fake));

        List<Book> list = service.getRecentlyAddedBooks();

        assertEquals(1, list.size());
        assertEquals("B200", list.get(0).getId());
    }

    @Test
    void testGetMostBorrowedBooks() {

        when(mockDB.findWithSQL(anyString(), any(), eq("docID")))
                .thenReturn(List.of(
                        List.of("B1")
                ));

        Book fake = new Book("B1", "X", "P", LocalDate.now(),
                1, 1, 1, 1, "i", "c", "a", "d");

        when(mockDB.createNewBookList("B1", "SELECT * FROM Book WHERE id LIKE ?"))
                .thenReturn(List.of(fake));

        List<Book> list = service.getMostBorrowedBooks();

        assertEquals(1, list.size());
        assertEquals("B1", list.get(0).getId());
    }
}

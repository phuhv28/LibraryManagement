import javafx.collections.ObservableList;
import librarymanagement.entity.Book;
import librarymanagement.entity.DocumentType;
import librarymanagement.gui.models.BookService;
import librarymanagement.gui.models.DocumentServiceFactory;
import librarymanagement.gui.viewmodels.SearchDocumentViewModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SearchDocumentViewModelTest {

    @Mock
    private BookService bookService;

    @Mock
    private Book mockBook;

    private SearchDocumentViewModel viewModel;
    private ObservableList<Book> searchResultList;

    @BeforeEach
    void setUp() {
        try (MockedStatic<DocumentServiceFactory> factoryMock = mockStatic(DocumentServiceFactory.class)) {
            factoryMock.when(() -> DocumentServiceFactory.getDocumentService(DocumentType.BOOK))
                    .thenReturn(bookService);
            viewModel = new SearchDocumentViewModel();
        }

        searchResultList = viewModel.searchResultProperty();
    }

    @Test
    void searchDocument_NullValueAndAttribute_ReturnsFalse() {
        viewModel.valueSearchProperty().set(null);
        viewModel.selectedAttributeProperty().set(null);

        boolean result = viewModel.searchDocument();

        assertFalse(result);
        verifyNoInteractions(bookService);
        assertTrue(searchResultList.isEmpty());
    }

    @Test
    void searchDocument_NonNullInputs_ReturnsTrue() {
        viewModel.valueSearchProperty().set("test");
        viewModel.selectedAttributeProperty().set("ID");

        when(bookService.findDocumentById(anyString())).thenReturn(mockBook);

        boolean result = viewModel.searchDocument();

        assertTrue(result);
        verify(bookService).findDocumentById("test");
    }

    @Test
    void searchDocument_ID_Found_AddsBookToList() {
        viewModel.valueSearchProperty().set("123");
        viewModel.selectedAttributeProperty().set("ID");
        when(bookService.findDocumentById("123")).thenReturn(mockBook);

        searchResultList.add(mock(Book.class));

        boolean result = viewModel.searchDocument();

        assertTrue(result);
        assertEquals(1, searchResultList.size());
        assertEquals(mockBook, searchResultList.getFirst());
        verify(bookService).findDocumentById("123");
    }

    @Test
    void searchDocument_ID_NotFound_ClearsAndKeepsEmpty() {
        viewModel.valueSearchProperty().set("999");
        viewModel.selectedAttributeProperty().set("ID");
        when(bookService.findDocumentById("999")).thenReturn(null);

        searchResultList.add(mock(Book.class));

        boolean result = viewModel.searchDocument();

        assertTrue(result);
        assertTrue(searchResultList.isEmpty());
        verify(bookService).findDocumentById("999");
    }

    @Test
    void searchDocument_ISBN_Found_AddsBookToList() {
        viewModel.valueSearchProperty().set("isbn123");
        viewModel.selectedAttributeProperty().set("ISBN");
        when(bookService.searchBookByISBN("isbn123")).thenReturn(mockBook);

        searchResultList.add(mock(Book.class));

        boolean result = viewModel.searchDocument();

        assertTrue(result);
        assertEquals(1, searchResultList.size());
        assertEquals(mockBook, searchResultList.getFirst());
        verify(bookService).searchBookByISBN("isbn123");
    }

    @Test
    void searchDocument_ISBN_NotFound_ClearsAndKeepsEmpty() {
        viewModel.valueSearchProperty().set("isbn999");
        viewModel.selectedAttributeProperty().set("ISBN");
        when(bookService.searchBookByISBN("isbn999")).thenReturn(null);

        searchResultList.add(mock(Book.class));

        boolean result = viewModel.searchDocument();

        assertTrue(result);
        assertTrue(searchResultList.isEmpty());
        verify(bookService).searchBookByISBN("isbn999");
    }

    @Test
    void searchDocument_Title_Found_AddsAllBooks() {
        Book book1 = mock(Book.class);
        Book book2 = mock(Book.class);
        List<Book> foundBooks = Arrays.asList(book1, book2);

        viewModel.valueSearchProperty().set("title test");
        viewModel.selectedAttributeProperty().set("Title");
        when(bookService.searchBookByTitle("title test")).thenReturn(foundBooks);

        searchResultList.add(mock(Book.class));

        boolean result = viewModel.searchDocument();

        assertTrue(result);
        assertEquals(2, searchResultList.size());
        assertTrue(searchResultList.containsAll(foundBooks));
        verify(bookService).searchBookByTitle("title test");
    }

    @Test
    void searchDocument_Title_NotFound_ListEmpty() {
        viewModel.valueSearchProperty().set("no title");
        viewModel.selectedAttributeProperty().set("Title");
        when(bookService.searchBookByTitle("no title")).thenReturn(Collections.emptyList());

        searchResultList.add(mock(Book.class));

        boolean result = viewModel.searchDocument();

        assertTrue(result);
        assertTrue(searchResultList.isEmpty());
        verify(bookService).searchBookByTitle("no title");
    }

    @Test
    void searchDocument_Title_NullList_ClearsAndKeepsEmpty() {
        viewModel.valueSearchProperty().set("null title");
        viewModel.selectedAttributeProperty().set("Title");
        when(bookService.searchBookByTitle("null title")).thenReturn(null);

        searchResultList.add(mock(Book.class));

        boolean result = viewModel.searchDocument();

        assertTrue(result);
        assertTrue(searchResultList.isEmpty());
        verify(bookService).searchBookByTitle("null title");
    }

    @Test
    void searchDocument_Author_Found_AddsAllBooks() {
        Book book1 = mock(Book.class);
        List<Book> foundBooks = Collections.singletonList(book1);

        viewModel.valueSearchProperty().set("author test");
        viewModel.selectedAttributeProperty().set("Author");
        when(bookService.searchBookByAuthor("author test")).thenReturn(foundBooks);

        boolean result = viewModel.searchDocument();

        assertTrue(result);
        assertEquals(1, searchResultList.size());
        verify(bookService).searchBookByAuthor("author test");
    }

    @Test
    void searchDocument_Category_Found_AddsAllBooks() {
        Book book1 = mock(Book.class);
        List<Book> foundBooks = Collections.singletonList(book1);

        viewModel.valueSearchProperty().set("category test");
        viewModel.selectedAttributeProperty().set("Category");
        when(bookService.searchBookByCategory("category test")).thenReturn(foundBooks);

        boolean result = viewModel.searchDocument();

        assertTrue(result);
        assertEquals(1, searchResultList.size());
        verify(bookService).searchBookByCategory("category test");
    }
}
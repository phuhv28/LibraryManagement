import librarymanagement.entity.Book;
import librarymanagement.entity.DocumentType;
import librarymanagement.gui.models.BookService;
import librarymanagement.gui.models.DocumentService;
import librarymanagement.gui.models.DocumentServiceFactory;
import librarymanagement.gui.viewmodels.EditDocumentViewModel;
import org.junit.jupiter.api.*;
import org.mockito.MockedStatic;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class EditDocumentViewModelTest {

    MockedStatic<DocumentServiceFactory> factoryMock;
    DocumentService mockService;

    @BeforeEach
    void setup() {
        mockService = mock(BookService.class);

        factoryMock = mockStatic(DocumentServiceFactory.class);
        factoryMock.when(() ->
                DocumentServiceFactory.getDocumentService(DocumentType.BOOK)
        ).thenReturn(mockService);
    }

    @AfterEach
    void teardown() {
        factoryMock.close();
    }

    @Test
    void testGetDocumentFromId_success() {

        Book fake = new Book(
                "42",                     // id
                "Effective Java",         // title
                "Addison-Wesley",         // publisher
                LocalDate.of(2018, 5, 1), // published date
                416,                      // page count
                10,                       // available copies
                4.8,                      // average rating
                5000,                     // ratings count
                "ISBN-99999",             // ISBN
                "Programming",            // categories
                "Joshua Bloch",           // author
                "Best Java book"          // description
        );

        when(mockService.findDocumentById("42")).thenReturn(fake);

        EditDocumentViewModel vm = new EditDocumentViewModel();
        vm.idProperty().set("42");

        boolean ok = vm.getDocumentFromId();

        assertTrue(ok);

        assertEquals("ISBN-99999", vm.isbnProperty().get());
        assertEquals("Effective Java", vm.titleProperty().get());
        assertEquals("Addison-Wesley", vm.publisherProperty().get());
        assertEquals("Joshua Bloch", vm.authorProperty().get());
        assertEquals(LocalDate.of(2018, 5, 1), vm.publishDateProperty().get());
        assertEquals("Programming", vm.categoryProperty().get());
        assertEquals("Best Java book", vm.descriptionProperty().get());
        assertEquals(10, vm.availableCopiesProperty().get());
        assertEquals(416, vm.pageCountProperty().get());

        verify(mockService).findDocumentById("42");
    }

    @Test
    void testGetDocumentFromId_notFound() {
        when(mockService.findDocumentById("404")).thenReturn(null);

        EditDocumentViewModel vm = new EditDocumentViewModel();
        vm.idProperty().set("404");

        boolean ok = vm.getDocumentFromId();

        assertFalse(ok);
        verify(mockService).findDocumentById("404");
    }
}

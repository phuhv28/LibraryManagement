import librarymanagement.entity.Book;
import librarymanagement.entity.Document;
import librarymanagement.entity.DocumentType;
import librarymanagement.gui.models.BookService;
import librarymanagement.gui.models.DocumentService;
import librarymanagement.gui.models.DocumentServiceFactory;
import librarymanagement.gui.models.MagazineService;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DocumentServiceFactoryTest {

    @Test
    void testGetDocumentService_ReturnsBookService() {

        DocumentService<? extends Document> service =
                DocumentServiceFactory.getDocumentService(DocumentType.BOOK);

        assertNotNull(service);
        assertTrue(service instanceof BookService);
    }

    @Test
    void testBookServiceSingleton() {

        DocumentService<? extends Document> s1 =
                DocumentServiceFactory.getDocumentService(DocumentType.BOOK);

        DocumentService<? extends Document> s2 =
                DocumentServiceFactory.getDocumentService(DocumentType.BOOK);

        assertSame(s1, s2);
    }

    @Test
    void testGetDocumentService_ReturnsMagazineService() {

        DocumentService<? extends Document> service =
                DocumentServiceFactory.getDocumentService(DocumentType.MAGAZINE);

        assertNotNull(service);
        assertTrue(service instanceof MagazineService);
    }

    @Test
    void testMagazineServiceSingleton() {

        DocumentService<? extends Document> s1 =
                DocumentServiceFactory.getDocumentService(DocumentType.MAGAZINE);

        DocumentService<? extends Document> s2 =
                DocumentServiceFactory.getDocumentService(DocumentType.MAGAZINE);

        assertSame(s1, s2);
    }

    @Test
    void testUnknownType_ThrowsException() {
        assertThrows(IllegalArgumentException.class, () ->
                DocumentServiceFactory.getDocumentService(null)
        );
    }

    @Test
    void testGenericCastingDoesNotThrow() {
        DocumentService<Book> bookService =
                DocumentServiceFactory.getDocumentService(DocumentType.BOOK);

        assertNotNull(bookService);
        assertInstanceOf(BookService.class, bookService);
    }
}

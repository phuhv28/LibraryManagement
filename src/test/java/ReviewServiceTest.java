import librarymanagement.entity.Book;
import librarymanagement.entity.Document;
import librarymanagement.entity.DocumentType;
import librarymanagement.entity.Review;
import librarymanagement.gui.models.*;
import librarymanagement.utils.SQLiteInstance;
import org.junit.jupiter.api.*;
import org.mockito.MockedStatic;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ReviewServiceTest {

    private SQLiteInstance mockDB;
    private ReviewService service;

    private MockedStatic<DocumentServiceFactory> factoryMock;
    private MockedStatic<AccountService> accountMock;

    private DocumentService<Document> mockDocService;
    private AccountService mockAccountService;

    @BeforeEach
    void setup() {

        mockDB = mock(SQLiteInstance.class);
        ReviewService.setSqLiteInstance(mockDB);

        service = ReviewService.getInstance();

        factoryMock = mockStatic(DocumentServiceFactory.class);
        mockDocService = mock(DocumentService.class);

        factoryMock.when(() ->
                DocumentServiceFactory.getDocumentService(DocumentType.BOOK)
        ).thenReturn(mockDocService);

        accountMock = mockStatic(AccountService.class);
        mockAccountService = mock(AccountService.class);
        accountMock.when(AccountService::getInstance).thenReturn(mockAccountService);
    }

    @AfterEach
    void tearDown() {
        factoryMock.close();
        accountMock.close();
    }
    @Test
    void testCheckIfUserHasCommentToDocument_true() {
        when(mockDB.findWithSQL(anyString(), any(), eq("username")))
                .thenReturn(List.of(List.of("john")));

        boolean ok = service.checkIfUserHasCommentToDocument("john", "B1");
        assertTrue(ok);
    }

    @Test
    void testCheckIfUserHasCommentToDocument_false() {
        when(mockDB.findWithSQL(anyString(), any(), eq("username")))
                .thenReturn(List.of());

        boolean ok = service.checkIfUserHasCommentToDocument("john", "B1");
        assertFalse(ok);
    }

    @Test
    void testAddReview_updatesBookAndInsertReview() {

        Book book = new Book();
        book.setId("B1");
        book.setAverageRating(4.0);
        book.setRatingsCount(2);

        when(mockDocService.findDocumentById("B1"))
                .thenReturn(book);

        boolean ok = service.addReview("john", "B1", 5, "Great!");

        assertTrue(ok);

        verify(mockDocService).updateDocument(book);

        verify(mockDB).insertRow(eq("Review"), eq("john"), eq("B1"), eq("Great!"), eq(5));
    }

    @Test
    void testAddReviewForCurrentAccount() {

        var account = mock(librarymanagement.entity.User.class);
        when(account.getUsername()).thenReturn("currentUser");
        when(mockAccountService.getCurrentAccount()).thenReturn(account);

        Book book = new Book();
        book.setId("B1");
        book.setAverageRating(3.0);
        book.setRatingsCount(1);

        when(mockDocService.findDocumentById("B1"))
                .thenReturn(book);

        boolean ok = service.addReviewForCurrentAccount("B1", "Nice!", 4);

        assertTrue(ok);

        verify(mockDB).insertRow(eq("Review"), eq("currentUser"), eq("B1"), eq("Nice!"), eq(4));
    }

    @Test
    void testGetAllReviewsInDocument() {

        when(mockDB.find("Review", "docID", "B1",
                "username", "rating", "comment"))
                .thenReturn(List.of(
                        List.of("u1", 5, "Good"),
                        List.of("u2", 3, "Ok")
                ));

        List<Review> reviews = service.getAllReviewsInDocument("B1");

        assertEquals(2, reviews.size());
        assertEquals("u1", reviews.get(0).getUsername());
        assertEquals(5, reviews.get(0).getRating());
        assertEquals("Good", reviews.get(0).getComment());

        assertEquals("u2", reviews.get(1).getUsername());
        assertEquals(3, reviews.get(1).getRating());
        assertEquals("Ok", reviews.get(1).getComment());
    }
}

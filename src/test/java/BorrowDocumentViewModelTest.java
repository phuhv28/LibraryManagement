import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.when;

import librarymanagement.gui.viewmodels.BorrowDocumentViewModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;

import librarymanagement.gui.models.BorrowingService;
import librarymanagement.gui.controllers.BorrowResult;

class BorrowDocumentViewModelTest {

    private BorrowDocumentViewModel viewModel;
    private BorrowingService mockService;

    @BeforeEach
    void setUp() {
        mockService = mock(BorrowingService.class);
        try (MockedStatic<BorrowingService> mockedStatic = mockStatic(BorrowingService.class)) {
            mockedStatic.when(BorrowingService::getInstance).thenReturn(mockService);
            viewModel = new BorrowDocumentViewModel();
        }
    }

    @Test
    void testBorrowDocumentSuccess() {
        viewModel.idProperty().set("123");
        BorrowResult mockResult = BorrowResult.SUCCESS; // Assume default success
        when(mockService.borrowDocumentForCurrentAccount("123")).thenReturn(mockResult);

        assertEquals(mockResult, viewModel.borrowDocument());
    }

    @Test
    void testBorrowDocumentNullId() {
        viewModel.idProperty().set(null);
        BorrowResult mockResult = BorrowResult.NOT_FOUND; // Assume failure handling
        when(mockService.borrowDocumentForCurrentAccount(null)).thenReturn(mockResult);

        assertEquals(mockResult, viewModel.borrowDocument());
    }

    @Test
    void testBorrowDocumentEmptyId() {
        viewModel.idProperty().set("");
        BorrowResult mockResult = BorrowResult.NOT_FOUND; // Assume failure
        when(mockService.borrowDocumentForCurrentAccount("")).thenReturn(mockResult);

        assertEquals(mockResult, viewModel.borrowDocument());
    }
}
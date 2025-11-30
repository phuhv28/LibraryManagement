import javafx.application.Platform;
import librarymanagement.entity.RegistrationResult;
import librarymanagement.gui.models.AccountService;
import librarymanagement.gui.viewmodels.AddUserViewModel;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

class AddUserViewModelTest {

    AddUserViewModel vm;
    AccountService mockService;
    MockedStatic<AccountService> staticMock;

    @BeforeEach
    void setup() {
        try {
            Platform.startup(() -> {});
        } catch (IllegalStateException e) {
        }
        Platform.setImplicitExit(false);

        mockService = mock(AccountService.class);
        staticMock = mockStatic(AccountService.class);
        staticMock.when(AccountService::getInstance).thenReturn(mockService);

        vm = new AddUserViewModel();
    }

    @AfterEach
    void tearDown() {
        staticMock.close();
    }

    @Test
    void testAddAccount_Member() {
        // Arrange
        vm.usernameProperty().set("alice");
        vm.passwordProperty().set("123");
        vm.confirmPasswordProperty().set("123");
        vm.fullNameProperty().set("Alice Wonderland");
        vm.emailProperty().set("alice@gmail.com");
        vm.menuAccountProperty().set("Member");

        RegistrationResult fakeResult = RegistrationResult.SUCCESS;
        when(mockService.addMember(any(), any(), any(), any(), any()))
                .thenReturn(fakeResult);

        RegistrationResult result = vm.addAccount();

        verify(mockService).addMember(
                eq("alice"), eq("123"), eq("123"),
                eq("Alice Wonderland"), eq("alice@gmail.com")
        );

        assertEquals(RegistrationResult.SUCCESS.getMessage(), result.getMessage());

        assertEquals(RegistrationResult.SUCCESS.getMessage(), vm.getResult());
    }

    @Test
    void testAddAccount_Admin() {
        // Arrange
        vm.usernameProperty().set("bob");
        vm.passwordProperty().set("456");
        vm.confirmPasswordProperty().set("456");
        vm.fullNameProperty().set("Bob Builder");
        vm.emailProperty().set("bob@gmail.com");
        vm.menuAccountProperty().set("Admin");

        RegistrationResult fakeResult = RegistrationResult.SUCCESS;
        when(mockService.addAdmin(any(), any(), any(), any(), any()))
                .thenReturn(fakeResult);

        RegistrationResult result = vm.addAccount();

        verify(mockService).addAdmin(
                eq("bob"), eq("456"), eq("456"),
                eq("Bob Builder"), eq("bob@gmail.com")
        );

        assertEquals(RegistrationResult.SUCCESS.getMessage(), result.getMessage());

        assertEquals(RegistrationResult.SUCCESS.getMessage(), vm.getResult());
    }
}

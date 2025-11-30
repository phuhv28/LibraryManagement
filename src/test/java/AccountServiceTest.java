import librarymanagement.entity.*;
import librarymanagement.gui.models.AccountService;
import librarymanagement.utils.SQLiteInstance;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AccountServiceTest {

    private AccountService service;
    private SQLiteInstance mockDb;

    @BeforeEach
    void setup() {
        service = AccountService.getInstance();
        mockDb = mock(SQLiteInstance.class);

        // inject database mock
        AccountService.setSqLiteInstance(mockDb);
        service.setCurrentAccount(null);
    }


    @Test
    void testCheckLogin_usernameNotFound() {

        when(mockDb.find("Member", "username", "john",
                "password", "fullName", "email", "regDate", "memberID"))
                .thenReturn(List.of());

        LoginResult result = service.checkLogin("john", "123");

        assertEquals(LoginResult.USERNAME_NOT_FOUND, result);
    }

    @Test
    void testCheckLogin_incorrectPassword() {

        when(mockDb.find(anyString(), anyString(), anyString(),
                any(), any(), any(), any(), any()))
                .thenReturn(List.of(List.of("wrongPassword")));

        LoginResult result = service.checkLogin("john", "123");

        assertEquals(LoginResult.INCORRECT_PASSWORD, result);
    }

    @Test
    void testCheckLogin_success_member() {

        when(mockDb.find("Admin", "username", "john", "username"))
                .thenReturn(List.of());

        when(mockDb.find("Member", "username", "john",
                "password", "fullName", "email", "regDate", "memberID"))
                .thenReturn(List.of(List.of(
                        "123",       // password match
                        "John Doe",
                        "john@mail.com",
                        "2024-10-10",
                        "M101"
                )));

        LoginResult result = service.checkLogin("john", "123");

        assertEquals(LoginResult.SUCCESS, result);
        assertInstanceOf(Member.class, service.getCurrentAccount());
    }

    @Test
    void testCheckLogin_success_admin() {

        when(mockDb.find("Admin", "username", "root", "username"))
                .thenReturn(List.of(List.of("root")));

        when(mockDb.find("Admin", "username", "root",
                "password", "fullName", "email", "regDate", "adminID"))
                .thenReturn(List.of(List.of(
                        "pass",
                        "Admin Boss",
                        "admin@mail.com",
                        "2024-11-01",
                        "A200"
                )));

        when(mockDb.find("Admin", "username", "root", "adminID"))
                .thenReturn(List.of(List.of("A200")));
        LoginResult result = service.checkLogin("root", "pass");

        assertEquals(LoginResult.SUCCESS, result);
        assertInstanceOf(Admin.class, service.getCurrentAccount());
        assertEquals("A200", service.getCurrentAccount().getId());
    }

    @Test
    void testIsUsernameTaken_true() {

        when(mockDb.findWithSQL(anyString(), any(), any()))
                .thenReturn(List.of(List.of("john")));

        assertTrue(service.isUsernameTaken("john"));
    }

    @Test
    void testIsUsernameTaken_false() {

        when(mockDb.findWithSQL(anyString(), any(), any()))
                .thenReturn(List.of());

        assertFalse(service.isUsernameTaken("mike"));
    }

    @Test
    void testAddUser_usernameTaken() {
        when(mockDb.findWithSQL(anyString(), any(), any()))
                .thenReturn(List.of(List.of("john")));

        RegistrationResult res = service.addUser("john", "123", "123", "John", "mail", false);

        assertEquals(RegistrationResult.USERNAME_TAKEN, res);
    }

    @Test
    void testAddUser_passwordMismatch() {
        when(mockDb.findWithSQL(anyString(), any(), any()))
                .thenReturn(List.of());

        RegistrationResult res = service.addUser("john", "123", "999", "John", "mail", false);

        assertEquals(RegistrationResult.PASSWORD_NOT_MATCH, res);
    }

    @Test
    void testAddUser_successMember() {

        when(mockDb.findWithSQL(anyString(), any(), any()))
                .thenReturn(List.of()); // username not taken

        when(mockDb.findNotCondition(eq("Member"), anyString()))
                .thenReturn(List.of(List.of(null))); // first ID → M101

        RegistrationResult res = service.addUser("john", "123", "123", "John", "mail", false);

        assertEquals(RegistrationResult.SUCCESS, res);

        verify(mockDb).insertRow(eq("Member"), any(), eq("john"), eq("123"), anyString(), eq("John"), eq("mail"));
    }

    @Test
    void testAddUser_successAdmin() {

        when(mockDb.findWithSQL(anyString(), any(), any()))
                .thenReturn(List.of());

        when(mockDb.findNotCondition(eq("Admin"), anyString()))
                .thenReturn(List.of());

        RegistrationResult res = service.addUser("root", "123", "123", "Boss", "mail", true);

        assertEquals(RegistrationResult.SUCCESS, res);

        verify(mockDb).insertRow(eq("Admin"), any(), eq("root"), eq("123"), anyString(), eq("Boss"), eq("mail"));
    }

    @Test
    void testIsAdmin_true() {
        when(mockDb.find("Admin", "username", "root", "username"))
                .thenReturn(List.of(List.of("root")));

        assertTrue(service.isAdmin("root"));
    }

    @Test
    void testIsAdmin_false() {
        when(mockDb.find(any(), any(), any(), any()))
                .thenReturn(List.of());

        assertFalse(service.isAdmin("john"));
    }

    @Test
    void testChangePassword_wrongOldPassword() {

        User u = new Member("M123", "john", "realpass", "John", "mail", "2024-10-10");
        service.setCurrentAccount(u);

        ChangePasswordResult result = service.changePassword("wrong", "123", "123");

        assertEquals(ChangePasswordResult.WRONG_OLD_PASSWORD, result);
    }

    @Test
    void testChangePassword_wrongConfirm() {

        User u = new Member("M123", "john", "realpass", "John", "mail", "2024-10-10");
        service.setCurrentAccount(u);

        ChangePasswordResult result = service.changePassword("realpass", "123", "456");

        assertEquals(ChangePasswordResult.WRONG_CONFIRM_NEW_PASSWORD, result);
    }

    @Test
    void testChangePassword_success() {

        User u = new Member("M123", "john", "realpass", "John", "mail", "2024-10-10");
        service.setCurrentAccount(u);

        when(mockDb.find("Admin", "username", "john", "username"))
                .thenReturn(List.of()); // not admin → Member

        ChangePasswordResult result = service.changePassword("realpass", "newPass", "newPass");

        assertEquals(ChangePasswordResult.SUCCESS_CHANGE, result);

        verify(mockDb).updateRow(eq("Member"), eq("password"), eq("newPass"), eq("username"), eq("john"));
        assertEquals("newPass", u.getPassword());
    }
}

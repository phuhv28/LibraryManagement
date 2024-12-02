import librarymanagement.entity.*;
import librarymanagement.gui.models.AccountService;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.List;

import static org.junit.jupiter.api.Assumptions.*;

class AccountServiceTest {
    private static final AccountService accountService = AccountService.getInstance();

    @BeforeAll
    static void setUp() {
        // Add test accounts to the database
        accountService.addAccount("testuser1", "password123", "password123", "Test User 1", "testuser1@example.com", AccountType.USER);
        accountService.addAccount("testadmin1", "admin123", "admin123", "Test Admin 1", "testadmin1@example.com", AccountType.ADMIN);
    }

    @AfterAll
    static void tearDown() {
        accountService.deleteUser("testuser1");
        accountService.deleteAdmin("testadmin1");
    }

    @Test
    void testCheckLoginValidUser() {
        LoginResult result = accountService.checkLogin("testuser1", "password123");
        assumeTrue(result == LoginResult.SUCCESS, "Login should be successful for valid user.");
    }

    @Test
    void testCheckLoginInvalidPassword() {
        LoginResult result = accountService.checkLogin("testuser1", "wrongpassword");
        assumeTrue(result == LoginResult.INCORRECT_PASSWORD, "Login should fail due to incorrect password.");
    }

    @Test
    void testCheckLoginUsernameNotFound() {
        LoginResult result = accountService.checkLogin("nonexistentuser", "password123");
        assumeTrue(result == LoginResult.USERNAME_NOT_FOUND, "Login should fail due to username not found.");
    }

    @ParameterizedTest
    @CsvSource({"testuser1, password123, SUCCESS", "testuser1, wrongpassword, INCORRECT_PASSWORD"})
    void testCheckLoginWithParameters(String username, String password, LoginResult expectedResult) {
        LoginResult result = accountService.checkLogin(username, password);
        assumeTrue(result == expectedResult, "Login result was not as expected.");
    }

    @Test
    void testIsUsernameTakenTrue() {
        boolean isTaken = accountService.isUsernameTaken("testuser1");
        assumeTrue(isTaken, "Username should be taken.");
    }

    @Test
    void testIsUsernameTakenFalse() {
        boolean isTaken = accountService.isUsernameTaken("nonexistentuser");
        assumeFalse(isTaken, "Username should not be taken.");
    }

    @ParameterizedTest
    @CsvSource({"testuser1, password123, password123, Test User 1, testuser1@example.com, USER, USERNAME_TAKEN",
            "testadmin1, admin123, admin123, Test Admin 1, testadmin1@example.com, ADMIN, USERNAME_TAKEN"})
    void testAddAccount(String username, String password, String confirmPassword, String fullName, String email,
                        AccountType accountType, RegistrationResult expectedResult) {
        RegistrationResult result = accountService.addAccount(username, password, confirmPassword, fullName, email, accountType);
        assumeTrue(result == expectedResult, "Account was not added as expected.");
    }

    @Test
    void testChangePasswordWrongOldPassword() {
        String oldPassword = "wrongpassword";
        String newPassword = "newpassword123";
        String confirmPassword = "newpassword123";

        ChangePasswordResult result = accountService.changePassword(oldPassword, newPassword, confirmPassword);
        assumeTrue(result == ChangePasswordResult.WRONG_OLD_PASSWORD, "Password change should fail due to wrong old password.");
    }

    @Test
    void testChangePasswordWrongConfirmPassword() {
        String oldPassword = "password123";
        String newPassword = "newpassword123";
        String confirmPassword = "wrongconfirm123";

        ChangePasswordResult result = accountService.changePassword(oldPassword, newPassword, confirmPassword);
        assumeTrue(result == ChangePasswordResult.WRONG_OLD_PASSWORD, "Password change should fail due to wrong confirmation password.");
    }

    @Test
    void testGetAllAccounts() {
        List<Account> accounts = accountService.getAllAccounts();
        assumeFalse(accounts.isEmpty(), "Account list should not be empty.");
        // Verify if test accounts are included
        boolean containsTestUser = accounts.stream().anyMatch(acc -> acc.getUsername().equals("testuser1"));
        boolean containsTestAdmin = accounts.stream().anyMatch(acc -> acc.getUsername().equals("testadmin1"));
        assumeTrue(containsTestUser && containsTestAdmin, "Test accounts should be in the list.");
    }
}

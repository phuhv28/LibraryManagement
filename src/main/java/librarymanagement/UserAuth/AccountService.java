package librarymanagement.UserAuth;

import java.util.*;

public class AccountService {
    private static final HashMap<String, Account> accountData = new HashMap<>();

    /**
     * Verify user account.
     */
    public static LoginResult checkLogin(String username, String password) {
        if (accountData.containsKey(username)) {
            if (accountData.get(username).getUsername().equals(password)) {
                return LoginResult.SUCCESS;
            } else {
                return LoginResult.INCORRECT_PASSWORD;
            }
        }
        return LoginResult.USERNAME_NOT_FOUND;
    }

    /**
     * Check if a username is taken.
     */
    private boolean isUsernameTaken(String username) {
        if (accountData.containsKey(username)) {
            return true;
        }
        return false;
    }

    /**
     * Add a user.
     */
    public RegistrationResult addUser(String username, String password) {
        if (isUsernameTaken(username)) {
            return RegistrationResult.USERNAME_TAKEN;
        }

        Account registerAccount = new Account(username, password, AccountType.USER);
        accountData.put(username, registerAccount);

        return RegistrationResult.SUCCESS;
    }

    /**
     * Add an admin.
     */
    public RegistrationResult addAdmin(String username, String password) {
        if (isUsernameTaken(username)) {
            return RegistrationResult.USERNAME_TAKEN;
        }

        Account registerAccount = new Account(username, password, AccountType.ADMIN);
        accountData.put(username, registerAccount);

        return RegistrationResult.SUCCESS;
    }
}

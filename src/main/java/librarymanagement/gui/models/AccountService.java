package librarymanagement.gui.models;

import librarymanagement.entity.Account;
import librarymanagement.entity.AccountType;
import librarymanagement.entity.LoginResult;
import librarymanagement.entity.RegistrationResult;
import librarymanagement.utils.SQLiteInstance;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class AccountService {
    private static final AccountService INSTANCE = new AccountService();
    private static Account currentAccount = new Account();
    private static final SQLiteInstance sqLiteInstance = new SQLiteInstance();

    public AccountService() {
    }

    public Account getCurrentAccount() {
        return currentAccount;
    }

    /**
     * Returns the singleton instance of the AccountService class.
     *
     * <p>This method ensures that only one instance of AccountService is created and used throughout the application.
     * It follows the Singleton design pattern to provide a global point of access to the AccountService instance.</p>
     *
     * @return the singleton instance of {@link AccountService}
     */
    public static AccountService getInstance() {
        return INSTANCE;
    }

    public void setCurrentAccount(Account currentAccount) {
        AccountService.currentAccount = currentAccount;
    }

    /**
     * Checks the login credentials for a user or admin account.
     *
     * <p>This method checks the provided username and password against the records in the "Admin" or "User" table.
     * If the username is found, it compares the provided password with the stored password. If the credentials
     * are valid, it sets the current account information and returns a success result. Otherwise, it returns
     * an error indicating the issue (e.g., incorrect password, username not found).</p>
     *
     * @param username the username of the account to check
     * @param password the password of the account to check
     * @return a {@link LoginResult} indicating the result of the login attempt:
     *         - {@link LoginResult#SUCCESS} if the login is successful
     *         - {@link LoginResult#USERNAME_NOT_FOUND} if the username is not found in the database
     *         - {@link LoginResult#INCORRECT_PASSWORD} if the password is incorrect
     */
    public LoginResult checkLogin(String username, String password) {
        List<List<Object>> result =
                sqLiteInstance.find("Admin", "username", username, "username");
        boolean isAdmin = !result.isEmpty();
        String type = isAdmin ? "Admin" : "User";

        result = sqLiteInstance.find(type, "username", username,
                "password", "fullName", "email", "regDate");
        if (result.isEmpty()) {
            return LoginResult.USERNAME_NOT_FOUND;
        } else if (result.getFirst().get(0).equals(password)) {
            String fullName = (String) result.get(0).get(1);
            String email = (String) result.get(0).get(2);
            String regDate = (String) result.get(0).get(3);
            currentAccount = new Account(username, password, fullName,
                    email, regDate, isAdmin ? AccountType.ADMIN : AccountType.USER);
            return LoginResult.SUCCESS;
        }

        return LoginResult.INCORRECT_PASSWORD;
    }

    /**
     * Check if a username is taken.
     */
    public boolean isUsernameTaken(String username) {
        String sql = "SELECT * FROM User WHERE User.username = ? UNION SELECT * FROM Admin WHERE Admin.username = ?";
        List<List<Object>> result =
                sqLiteInstance.findWithSQL(sql, new Object[]{username, username}, "username");

        return !result.isEmpty();
    }

    /**
     * Generate new user ID based on the maximum user ID in the database.
     *
     * @param tableName name of table to generate new ID
     *                  return new ID
     */
    private String generateNewUserId(String tableName) {
        String newId = "";
        if (tableName.equals("User")) {
            List<List<Object>> result = sqLiteInstance.findNotCondition("User", "Max(userId)");
            if (result.get(0).get(0) == null) {
                newId = "U101";
            } else {
                String temp = result.get(0).get(0).toString().substring(1);
                newId = "U" + (Integer.parseInt(temp) + 1);
            }
        } else if (tableName.equals("Admin")) {
            List<List<Object>> result = sqLiteInstance.findNotCondition("Admin", "Max(adminId)");
            if (result.get(0).get(0) == null) {
                newId = "A101";
            } else {
                String temp = result.get(0).get(0).toString().substring(1);
                newId = "A" + (Integer.parseInt(temp) + 1);
            }
        }
        return newId;
    }

    /**
     * Adds a new account (either user or admin) to the database.
     *
     * <p>This method first checks if the provided username is already taken and ensures that the password
     * and confirm password match. If both checks pass, it proceeds to create the account by inserting
     * the necessary details into the appropriate database table based on the account type (either "User" or "Admin").</p>
     *
     * @param username the username of the new account
     * @param password the password of the new account
     * @param confirmPassword the confirmation password
     * @param fullName the full name of the new account holder
     * @param email the email address of the new account holder
     * @param accountType the type of account to create, either {@link AccountType#USER} or {@link AccountType#ADMIN}
     * @return a {@link RegistrationResult} indicating the result of the registration:
     *         - {@link RegistrationResult#USERNAME_TAKEN} if the username is already taken
     *         - {@link RegistrationResult#PASSWORD_NOT_MATCH} if the passwords do not match
     *         - {@link RegistrationResult#SUCCESS} if the account was successfully created
     */
    public RegistrationResult addAccount(String username, String password, String confirmPassword, String fullName, String email, AccountType accountType) {
        if (isUsernameTaken(username)) {
            return RegistrationResult.USERNAME_TAKEN;
        }

        if (!password.equals(confirmPassword)) {
            return RegistrationResult.PASSWORD_NOT_MATCH;
        }

        ///Create issueDay
        LocalDate today = LocalDate.now();
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        if (accountType == AccountType.USER) {
            String userId = generateNewUserId("User");
            ///Import new user to database
            sqLiteInstance.insertRow("User", userId, username, password, dateFormatter.format(today), fullName, email);
        } else if (accountType == AccountType.ADMIN) {
            String newAdminId = generateNewUserId("Admin");
            ///Import new admin to database
            sqLiteInstance.insertRow("Admin", newAdminId, username, password, dateFormatter.format(today), fullName, email);
        }


        return RegistrationResult.SUCCESS;
    }

    /**
     * Check if username of admin
     *
     * @param username username
     * @return isAdmin
     */
    public boolean isAdmin(String username) {
        List<List<Object>> list = sqLiteInstance.find("Admin", "username", username, "*");
        return !list.isEmpty();
    }

    /**
     * Retrieves an account by its user ID.
     *
     * <p>This method retrieves an account from the database based on the provided user ID. It determines
     * whether the account belongs to an admin or a regular user based on the first character of the
     * user ID. The account's details such as username, password, full name, email, and registration date
     * are then retrieved from the corresponding table (either "Admin" or "User").</p>
     *
     * @param userID the user ID of the account to be retrieved
     * @return an {@link Account} object containing the account details, or null if the account is not found
     */
    public Account getAccountByUserID(String userID) {
        AccountType type = userID.charAt(0) == 'A' ? AccountType.ADMIN : AccountType.USER;
        String tableName = userID.charAt(0) == 'A' ? "Admin" : "User";
        String column = userID.charAt(0) == 'A' ? "adminId" : "userId";
        List<List<Object>> list = sqLiteInstance.find(tableName, column, userID, "username", "password", "fullName", "email", "regDate");

        if (list.isEmpty()) {
            return null;
        }

        String username = (String) list.get(0).get(0);
        String password = (String) list.get(0).get(1);
        String fullName = (String) list.get(0).get(2);
        String email = (String) list.get(0).get(3);
        String regDate = (String) list.get(0).get(4);
        return new Account(userID, username, password, fullName, email, regDate, type);
    }
}

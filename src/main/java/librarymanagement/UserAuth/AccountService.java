package librarymanagement.UserAuth;

import librarymanagement.data.SQLiteInstance;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class AccountService {
    private static final AccountService INSTANCE = new AccountService();

    private AccountService() {
    }

    public static AccountService getInstance() {
        return INSTANCE;
    }

    private static final SQLiteInstance sqLiteInstance = new SQLiteInstance();

    /**
     * Verify user account.
     */
    public static LoginResult checkLogin(String username, String password) {
        List<List<Object>> result = sqLiteInstance.find("User", "username", username, "username", "password");
        if (result.isEmpty()) {
            return LoginResult.USERNAME_NOT_FOUND;
        } else if (result.getFirst().get(1).equals(password)) {
            return LoginResult.SUCCESS;
        }

        return LoginResult.INCORRECT_PASSWORD;
    }

    /**
     * Check if a username is taken.
     */
    private boolean isUsernameTaken(String username) {
        String sql = "SELECT * FROM User WHERE User.username = ? UNION SELECT * FROM Admin WHERE Admin.username = ?";
        List<List<Object>> result = sqLiteInstance.findWithSQL(sql, new Object[]{username, username}, "username");

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
     * Add a user.
     */
    public RegistrationResult addUser(String username, String password, String confirmPassword , String fullname , String email) {
        if (!password.equals(confirmPassword)) {
            return RegistrationResult.PASSWORD_NOT_MATCH;
        }

        if (isUsernameTaken(username)) {
            return RegistrationResult.USERNAME_TAKEN;
        }

        String userId = generateNewUserId("User");

        ///Create issueDay
        LocalDate today = LocalDate.now();
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        ///Import new user to database
        sqLiteInstance.insertRow("User", userId, username, password, dateFormatter.format(today), null, null);

        return RegistrationResult.SUCCESS;
    }

    /**
     * Add an admin.
     */
    public RegistrationResult addAdmin(String username, String password) {
        if (isUsernameTaken(username)) {
            return RegistrationResult.USERNAME_TAKEN;
        }

        String newAdminId = generateNewUserId("Admin");

        ///Create returnDate
        LocalDate today = LocalDate.now();
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        ///Import new admin to database
        sqLiteInstance.insertRow("Admin", newAdminId, username, null, null, password, today.format(dateFormatter));

        return RegistrationResult.SUCCESS;
    }
}

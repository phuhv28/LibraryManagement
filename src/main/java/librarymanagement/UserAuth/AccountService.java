package librarymanagement.UserAuth;

import librarymanagement.data.SQLiteInstance;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class AccountService {
    private static final AccountService INSTANCE = new AccountService();
    private static Account currentAccount = new Account();
    private static final SQLiteInstance sqLiteInstance = new SQLiteInstance();

    private AccountService() {
    }

    public Account getCurrentAccount() {
        return currentAccount;
    }

    public static AccountService getInstance() {
        return INSTANCE;
    }

    public void setCurrentAccount(Account currentAccount) {
        AccountService.currentAccount = currentAccount;
    }

    /**
     * Verify user account.
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
     * Add a account.
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

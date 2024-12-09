package librarymanagement.gui.models;

import librarymanagement.entity.*;
import librarymanagement.utils.SQLiteInstance;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * Service class for managing user accounts (Admin and Member).
 * Provides functionalities like login, registration, password change, and account management.
 */
public class AccountService {
    private static final AccountService INSTANCE = new AccountService();
    private static final SQLiteInstance sqLiteInstance = new SQLiteInstance();
    private static User currentUser = null;

    private AccountService() {
    }

    /**
     * Gets the singleton instance of the AccountService.
     *
     * @return the singleton instance of AccountService
     */
    public static AccountService getInstance() {
        return INSTANCE;
    }

    /**
     * Gets the currently logged-in user.
     *
     * @return the current user or null if no user is logged in
     */
    public User getCurrentAccount() {
        return currentUser;
    }

    /**
     * Sets the current user account.
     *
     * @param currentUser the user to set as the current user
     */
    public void setCurrentAccount(User currentUser) {
        AccountService.currentUser = currentUser;
    }

    /**
     * Checks login credentials and authenticates the user.
     *
     * @param username the username
     * @param password the password
     * @return a {@link LoginResult} indicating the login result
     */
    public LoginResult checkLogin(String username, String password) {

        boolean isAdmin = isAdmin(username);
        String type = isAdmin ? "Admin" : "Member";
        String id = isAdmin ? "adminID" : "memberID";

        List<List<Object>> result = sqLiteInstance.find(type, "username", username,
                "password", "fullName", "email", "regDate", id);
        if (result.isEmpty()) {
            return LoginResult.USERNAME_NOT_FOUND;
        } else if (result.getFirst().getFirst().equals(password)) {
            String fullName = (String) result.getFirst().get(1);
            String email = (String) result.getFirst().get(2);
            String regDate = (String) result.getFirst().get(3);
            String userID = (String) result.getFirst().get(4);
            if (isAdmin) {
                currentUser = new Admin(userID, username, password, fullName, email, regDate);
            } else {
                currentUser = new Member(userID, username, password, fullName, email, regDate);
            }
            return LoginResult.SUCCESS;
        }

        return LoginResult.INCORRECT_PASSWORD;
    }

    /**
     * Checks if a username is already taken.
     *
     * @param username the username to check
     * @return true if the username is taken, false otherwise
     */
    public boolean isUsernameTaken(String username) {
        String sql = "SELECT * FROM Member WHERE Member.username = ? UNION SELECT * FROM Admin WHERE Admin.username = ?";
        List<List<Object>> result =
                sqLiteInstance.findWithSQL(sql, new Object[]{username, username}, "username");

        return !result.isEmpty();
    }


    private String generateNewUserId(String tableName) {
        String newId = tableName.equals("Admin") ? "A" : "M";
        String columns = tableName.equals("Admin") ? "MAX(adminID)" : "MAX(memberID)";
        List<List<Object>> list = sqLiteInstance.findNotCondition(tableName, columns);
        if (list.getFirst().getFirst() == null) {
            newId += "101";
        } else {
            String temp = list.getFirst().getFirst().toString().substring(1);
            newId += (Integer.parseInt(temp) + 1);
        }
        return newId;
    }

    /**
     * Registers a new user (Admin or Member).
     *
     * @param username        the username
     * @param password        the password
     * @param confirmPassword the password confirmation
     * @param fullName        the user's full name
     * @param email           the user's email address
     * @param isAdmin         true if registering an admin, false for a member
     * @return a {@link RegistrationResult} indicating the result of the registration
     */
    public RegistrationResult addUser(String username, String password, String confirmPassword,
                                      String fullName, String email, boolean isAdmin) {
        if (isUsernameTaken(username)) {
            return RegistrationResult.USERNAME_TAKEN;
        }

        if (!password.equals(confirmPassword)) {
            return RegistrationResult.PASSWORD_NOT_MATCH;
        }

        ///Create issueDay
        LocalDate today = LocalDate.now();
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        if (!isAdmin) {
            String userId = generateNewUserId("Member");
            ///Import new member to database
            sqLiteInstance.insertRow("Member", userId, username, password, dateFormatter.format(today), fullName, email);
        } else {
            String newAdminId = generateNewUserId("Admin");
            ///Import new admin to database
            sqLiteInstance.insertRow("Admin", newAdminId, username, password, dateFormatter.format(today), fullName, email);
        }

        return RegistrationResult.SUCCESS;
    }

    /**
     * Registers a new member.
     *
     * @param username        the username
     * @param password        the password
     * @param confirmPassword the password confirmation
     * @param fullName        the member's full name
     * @param email           the member's email address
     * @return a {@link RegistrationResult} indicating the result of the registration
     */
    public RegistrationResult addMember(String username, String password, String confirmPassword,
                                        String fullName, String email) {
        return addUser(username, password, confirmPassword, fullName, email, false);
    }

    /**
     * Registers a new admin.
     *
     * @param username        the username
     * @param password        the password
     * @param confirmPassword the password confirmation
     * @param fullName        the admin's full name
     * @param email           the admin's email address
     * @return a {@link RegistrationResult} indicating the result of the registration
     */
    public RegistrationResult addAdmin(String username, String password, String confirmPassword,
                                       String fullName, String email) {
        return addUser(username, password, confirmPassword, fullName, email, true);
    }

    /**
     * Checks if the specified username belongs to an admin account.
     *
     * @param username the username to check
     * @return true if the username belongs to an admin, false otherwise
     */
    public boolean isAdmin(String username) {
        List<List<Object>> list = sqLiteInstance.find("Admin", "username", username, "username");
        return !list.isEmpty();
    }

    /**
     * Retrieves a user account by user ID.
     *
     * @param userID the user ID
     * @return the user account or null if no account matches the ID
     */
    public User getAccountByUserID(String userID) {
        String tableName = userID.charAt(0) == 'A' ? "Admin" : "Member";
        String column = userID.charAt(0) == 'A' ? "adminId" : "memberId";
        List<List<Object>> list = sqLiteInstance.find(tableName, column, userID, "username", "password", "fullName", "email", "regDate");

        if (list.isEmpty()) {
            return null;
        }

        String username = (String) list.getFirst().getFirst();
        String password = (String) list.getFirst().get(1);
        String fullName = (String) list.getFirst().get(2);
        String email = (String) list.getFirst().get(3);
        String regDate = (String) list.getFirst().get(4);
        if (userID.charAt(0) == 'A') {
            return new Admin(userID, username, password, fullName, email, regDate);
        } else {
            return new Member(userID, username, password, fullName, email, regDate);
        }
    }

    /**
     * Changes the password for the current user.
     *
     * @param oldPassword     the current password
     * @param newPassword     the new password
     * @param confirmPassword the password confirmation
     * @return a {@link ChangePasswordResult} indicating the result of the operation
     */
    public ChangePasswordResult changePassword(String oldPassword, String newPassword, String confirmPassword) {
        if (!oldPassword.equals(AccountService.getInstance().getCurrentAccount().getPassword())) {
            return ChangePasswordResult.WRONG_OLD_PASSWORD;
        }
        if (!newPassword.equals(confirmPassword)) {
            return ChangePasswordResult.WRONG_CONFIRM_NEW_PASSWORD;
        }
        String username = AccountService.getInstance().getCurrentAccount().getUsername();
        String table = isAdmin(AccountService.getInstance().getCurrentAccount().getUsername()) ? "Admin" : "Member";
        sqLiteInstance.updateRow(table, "password", newPassword, "username", username);
        currentUser.setPassword(newPassword);
        return ChangePasswordResult.SUCCESS_CHANGE;
    }

    /**
     * Retrieves all user accounts (Admins and Members).
     *
     * @return a list of all user accounts, or null if no accounts exist
     */
    public List<User> getAllAccounts() {
        List<User> users = new ArrayList<>();

        users.addAll(fetchAccounts(
                "SELECT * FROM Member ORDER BY regDate ASC",
                new String[]{"memberID", "username", "password", "fullName", "email", "regDate"},
                false
        ));

        users.addAll(fetchAccounts(
                "SELECT * FROM Admin ORDER BY regDate ASC",
                new String[]{"adminID", "username", "password", "fullName", "email", "regDate"},
                true
        ));

        if (users.isEmpty()) {
            return null;
        }

        return users;
    }

    /**
     * Helper method to fetch accounts from the database based on a SQL query.
     *
     * @param sql     the SQL query
     * @param columns the columns to fetch
     * @param isAdmin true if fetching admin accounts, false for member accounts
     * @return a list of user accounts matching the query
     */
    private List<User> fetchAccounts(String sql, String[] columns, boolean isAdmin) {
        List<User> users = new ArrayList<>();
        List<List<Object>> results = sqLiteInstance.findWithSQL(sql, new Object[]{}, columns);

        for (List<Object> row : results) {
            try {
                String userID = (String) row.get(0);
                String username = (String) row.get(1);
                String password = (String) row.get(2);
                String fullName = (String) row.get(3);
                String email = (String) row.get(4);
                String regDate = (String) row.get(5);

                if (isAdmin) {
                    users.add(new Admin(userID, username, password, fullName, email, regDate));
                } else {
                    users.add(new Member(userID, username, password, fullName, email, regDate));
                }
            } catch (ClassCastException | IndexOutOfBoundsException e) {
                e.printStackTrace();
            }
        }

        return users;
    }

    /**
     * Deletes a user account by username.
     *
     * @param username the username of the account to delete
     */
    public void deleteUser(String username) {
        String condition = "username = '" + username + "'";
        sqLiteInstance.deleteRow("User", condition);
    }

    /**
     * Deletes an admin account by username.
     *
     * @param username the username of the admin account to delete
     */
    public void deleteAdmin(String username) {
        String condition = "adminId = '" + username + "'";
        sqLiteInstance.deleteRow("Admin", condition);
    }
}

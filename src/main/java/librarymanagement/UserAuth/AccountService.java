package librarymanagement.UserAuth;

import librarymanagement.data.Constant;

import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class AccountService {
    private static final AccountService INSTANCE = new AccountService();

    private Connection connection;

    private AccountService() {
        try {
            connection = DriverManager.getConnection(Constant.URL);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static AccountService getInstance() {
        return INSTANCE;
    }

    public void closeConnection() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FunctionalInterface
    interface PreparedStatementSetter {
        void setValues(PreparedStatement stmt) throws SQLException;
    }

    private void executeUpdate(String sql, PreparedStatementSetter setter) {
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            setter.setValues(stmt);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Verify user account.
     */
    public static LoginResult checkLogin(String username, String password) {
        String sql = "SELECT * FROM User WHERE username = ?";
        try (Connection con = DriverManager.getConnection(Constant.URL);
             PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setString(1, username);
            try (ResultSet rs = stmt.executeQuery()) {
                if (!rs.next()) {
                    return LoginResult.USERNAME_NOT_FOUND;
                } else {
                    String truePassword = rs.getString("Password");
                    if (password.equals(truePassword)) {
                        return LoginResult.SUCCESS;
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return LoginResult.INCORRECT_PASSWORD;
    }

    /**
     * Check if a username is taken.
     */
    private boolean isUsernameTaken(String username) {
        String sql = "SELECT * FROM User WHERE username = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, username);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return true;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        sql = "SELECT * FROM Admin WHERE username = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, username);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return true;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Generate new user ID based on the maximum user ID in the database.
     */
    private String generateNewUserId(String tableName) {
        String newId = "";
        String sql = "SELECT MAX(userID) as Max FROM " + tableName;
        try (PreparedStatement stmt = connection.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            if (rs.next()) {
                String temp = rs.getString("Max");
                if (temp == null) {
                    newId = "U101";
                } else {
                    temp = temp.substring(1);
                    newId = "U" + (Integer.parseInt(temp) + 1);
                }
            } else {
                newId = "U101";
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return newId;
    }

    /**
     * Add a user.
     */
    public RegistrationResult addUser(String username, String password, String confirmPassword) {
        if (!password.equals(confirmPassword)) {
            return RegistrationResult.PASSWORD_NOT_MATCH;
        }

        if (isUsernameTaken(username)) {
            return RegistrationResult.USERNAME_TAKEN;
        }

        String userId = generateNewUserId("User");
        LocalDate today = LocalDate.now();
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String sql = "INSERT INTO User (userID, regDate, username, password) VALUES (?, ?, ?, ?)";

        executeUpdate(sql, stmt -> {
            stmt.setString(1, userId);
            stmt.setString(2, dateFormatter.format(today));
            stmt.setString(3, username);
            stmt.setString(4, password);
        });

        return RegistrationResult.SUCCESS;
    }

    /**
     * Add an admin.
     */
    public RegistrationResult addAdmin(String username, String password) {
        if (isUsernameTaken(username)) {
            return RegistrationResult.USERNAME_TAKEN;
        }

        String adminId = generateNewUserId("Admin");
        LocalDate today = LocalDate.now();
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String sql = "INSERT INTO Admin (adminID, regDate, username, password) VALUES (?, ?, ?, ?)";

        executeUpdate(sql, stmt -> {
            stmt.setString(1, adminId);
            stmt.setString(2, dateFormatter.format(today));
            stmt.setString(3, username);
            stmt.setString(4, password);
        });

        return RegistrationResult.SUCCESS;
    }
}

package librarymanagement.UserAuth;

import java.sql.*;
import java.util.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class AccountService {
    private static final HashMap<String, Account> accountData = new HashMap<>();

    private static final AccountService INSTANCE = new AccountService();

    private AccountService() {

    }

    public static AccountService getInstance() {
        return INSTANCE;
    }

    /**
     * Verify user account.
     */
    public static LoginResult checkLogin(String username, String password) {
        /*if (accountData.containsKey(username)) {
            if (accountData.get(username).getUsername().equals(password)) {
                return LoginResult.SUCCESS;
            } else {
                return LoginResult.INCORRECT_PASSWORD;
            }
        }
        return LoginResult.USERNAME_NOT_FOUND;*/
        String sql = "SELECT * FROM Customer WHERE User_name = ?";
        try (Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/library", "root", "longmixi0401");
             PreparedStatement stmt = con.prepareStatement(sql)) {

            stmt.setString(1, username);
            try (ResultSet rs = stmt.executeQuery()) {
                if (!rs.next()) {
                    return LoginResult.USERNAME_NOT_FOUND;
                } else {
                    String TruePassword = rs.getString("Password");
                    if (password.equals(TruePassword)) {
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
        /*if (accountData.containsKey(username)) {
            return true;
        }
        return false;*/
        String sql = "SELECT * FROM Customer WHERE User_name = ?";
        try (Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/library", "root", "longmixi0401");
             PreparedStatement stmt = con.prepareStatement(sql)) {

            stmt.setString(1, username);
            try (ResultSet rs = stmt.executeQuery()) {
                if (!rs.next()) {
                    return false;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return true;
    }

    /**
     * Add a user.
     */
    public RegistrationResult addUser(String username, String password, String confirmPassword/*, String name, String address*/) {
        if (!password.equals(confirmPassword)) {
            return  RegistrationResult.PASSWORD_NOT_MATCH;
        }

        if (isUsernameTaken(username)) {
            return RegistrationResult.USERNAME_TAKEN;
        }

        /*
         Create new user_id
        */
        String user_id = "";
        String sql = "SELECT MAX(Customer.Customer_Id) as 'Max' FROM Customer";
        try (Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/library", "root", "longmixi0401");
            PreparedStatement stmt = con.prepareStatement(sql)) {
            try (ResultSet rs = stmt.executeQuery()) {
                if (!rs.next()) {
                    user_id = "C101";
                } else {
                    String temp = rs.getString("Max");
                    temp = temp.substring(1);
                    user_id = "C" + (Integer.parseInt(temp) + 1);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        /*
         Insert into Database
        */
        LocalDate today = LocalDate.now();
        DateTimeFormatter Date_formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        sql = "INSERT INTO Customer (Customer_Id, Customer_name, Customer_address, Reg_date, User_name, Password) "
                + "VALUE (?, ?, ?, ?, ?, ?)";
        try (Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/library", "root", "longmixi0401");
             PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setString(1, user_id);
            stmt.setString(2, /*name*/ null);
            stmt.setString(3, /*address*/ null);
            stmt.setString(4, Date_formatter.format(today));
            stmt.setString(5, username);
            stmt.setString(6, password);

            stmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return RegistrationResult.SUCCESS;
    }

    /**
     * Add an admin.
     */
    public RegistrationResult addAdmin(String username, String password) {
        if (isUsernameTaken(username)) {
            return RegistrationResult.USERNAME_TAKEN;
        }

        /*Account registerAccount = new Account(username, password, AccountType.ADMIN);
        accountData.put(username, registerAccount);*/

        String employee_id = "";
        String sql = "SELECT MAX(admin.Emp_id) FROM admin";
        try (Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/library", "root", "longmixi0401");
             PreparedStatement stmt = con.prepareStatement(sql)){
            ResultSet rs = stmt.executeQuery();
            if(!rs.next()) {
                employee_id = "E101";
            } else {
                String temp = rs.getString("Emp_id");
                temp = temp.substring(1);
                employee_id = "E" + (Integer.parseInt(temp) + 1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        sql = "INSERT INTO admin (Emm_id, Admin_name, password) VALUES (?, ?, ?)";

        try (Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/library", "root", "longmixi0401");
             PreparedStatement stmt = con.prepareStatement(sql)){
            stmt.setString(1, employee_id);
            stmt.setString(2, username);
            stmt.setString(3, password);

            stmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return RegistrationResult.SUCCESS;
    }
}

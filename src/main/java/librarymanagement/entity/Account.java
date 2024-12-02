package librarymanagement.entity;

import librarymanagement.utils.SQLiteInstance;

import java.util.List;

public class Account {
    private String userID;
    private String username;
    private String password;
    private String fullName;
    private String email;
    private String regDate;
    private AccountType accountType;

    public Account(String username, String password, AccountType accountType) {
        this.username = username;
        this.password = password;
        this.accountType = accountType;
    }

    public Account(String username, String password, String fullName, String email, String regDate, AccountType accountType) {
        this.username = username;
        this.password = password;
        this.fullName = fullName;
        this.email = email;
        this.regDate = regDate;
        this.accountType = accountType;
        this.userID = getId();
    }

    public Account(String userID, String username, String password, String fullName, String email, String regDate, AccountType accountType) {
        this.userID = userID;
        this.username = username;
        this.password = password;
        this.fullName = fullName;
        this.email = email;
        this.regDate = regDate;
        this.accountType = accountType;
    }

    public Account() {

    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public AccountType getAccountType() {
        return accountType;
    }

    public void setAccountType(AccountType accountType) {
        this.accountType = accountType;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRegDate() {
        return regDate;
    }

    public void setRegDate(String regDate) {
        this.regDate = regDate;
    }

    public String getId() {
        String table = accountType == AccountType.USER ? "User" : "Admin";
        String column = accountType == AccountType.USER ? "userID" : "adminID";
        List<List<Object>> lists = SQLiteInstance.getInstance().find(table, "username", username, column);

        return lists.get(0).get(0).toString();
    }

    /**
     * Returns the number of books borrowed by the current user.
     *
     * <p>This method queries the "Transaction" table to count the number of borrow transactions for the user
     * identified by the current user's ID. If no borrow transactions are found, it returns 0.</p>
     *
     * @return the number of books borrowed by the user.
     */
    public int getNumberOfBooksBorrowed() {
        List<List<Object>> lists = SQLiteInstance.getInstance().find("BorrowRecord", "userID", getId(), "recordID");

        return lists.isEmpty() ? 0 : lists.size();
    }
}
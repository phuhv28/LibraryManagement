package librarymanagement.entity;

import librarymanagement.utils.SQLiteInstance;

import java.util.List;

public abstract class User {
    protected String userID;
    protected String username;
    protected String password;
    protected String fullName;
    protected String email;
    protected String regDate;

    public User(String username, String password, String fullName, String email, String regDate) {
        this.username = username;
        this.password = password;
        this.fullName = fullName;
        this.email = email;
        this.regDate = regDate;
        this.userID = getId();
    }

    public User(String userID, String username, String password, String fullName, String email, String regDate) {
        this.userID = userID;
        this.username = username;
        this.password = password;
        this.fullName = fullName;
        this.email = email;
        this.regDate = regDate;
    }

    public User() {

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

    public String getUserID() {
        return userID;
    }

    public abstract String getId();

    /**
     * Returns the number of books borrowed by the current user.
     *
     * <p>This method queries the "BorrowRecord" table to count the number of borrow transactions for the user
     * identified by the current user's ID. If no borrow transactions are found, it returns 0.</p>
     *
     * @return the number of books borrowed by the user.
     */
    public int getNumberOfBooksIsBorrowing() {
        String sql = "SELECT recordID FROM BorrowRecord WHERE userID = ? AND returnDate ISNULL";
        String userID = getId();
        List<List<Object>> lists = SQLiteInstance.getInstance().findWithSQL(sql, new Object[]{userID}, "recordID");

        return lists.isEmpty() ? 0 : lists.size();
    }
}
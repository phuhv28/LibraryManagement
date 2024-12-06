package librarymanagement.entity;

public class Admin extends User {
    public Admin(String userID, String username, String password, String fullName, String email, String regDate) {
        super(userID, username, password, fullName, email, regDate);
    }
}

package librarymanagement.entity;

public class Member extends User {
    public Member(String userID, String username, String password, String fullName, String email, String regDate) {
        super(userID, username, password, fullName, email, regDate);
    }
}

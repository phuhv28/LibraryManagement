package librarymanagement.entity;

import librarymanagement.utils.SQLiteInstance;

import java.util.List;

public class Member extends User {
    public Member(String userID, String username, String password, String fullName, String email, String regDate) {
        super(userID, username, password, fullName, email, regDate);
    }

    @Override
    public String getId() {
        List<List<Object>> lists = SQLiteInstance.getInstance().find("Member", "username", username, "memberID");
        return lists.getFirst().getFirst().toString();
    }
}

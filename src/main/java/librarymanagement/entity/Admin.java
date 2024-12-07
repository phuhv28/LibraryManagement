package librarymanagement.entity;

import librarymanagement.utils.SQLiteInstance;

import java.util.List;

public class Admin extends User {
    public Admin(String userID, String username, String password, String fullName, String email, String regDate) {
        super(userID, username, password, fullName, email, regDate);
    }

    @Override
    public String getId() {
        List<List<Object>> lists = SQLiteInstance.getInstance().find("Admin", "username", username, "adminID");
        return lists.getFirst().getFirst().toString();
    }
}

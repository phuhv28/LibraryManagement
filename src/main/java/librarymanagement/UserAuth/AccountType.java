package librarymanagement.UserAuth;

public enum AccountType {
    ADMIN("Admin"),
    USER("User");

    private String type;

    AccountType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }
}

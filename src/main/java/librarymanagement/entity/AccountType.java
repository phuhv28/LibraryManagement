package librarymanagement.entity;

public enum AccountType {
    ADMIN("Admin"),
    USER("User");

    private final String type;

    AccountType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }
}

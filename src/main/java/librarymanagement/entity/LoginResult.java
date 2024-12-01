package librarymanagement.entity;

public enum LoginResult {
    SUCCESS("Login successful!"),
    USERNAME_NOT_FOUND("Username not found."),
    INCORRECT_PASSWORD("Incorrect password.");

    private final String message;

    LoginResult(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}

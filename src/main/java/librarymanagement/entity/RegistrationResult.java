package librarymanagement.entity;

public enum RegistrationResult {
    SUCCESS("Registration successful!"),
    USERNAME_TAKEN("Username is already taken."),
    INVALID_USERNAME("Username is invalid."),
    PASSWORD_NOT_MATCH("Passwords do not match.");

    private final String message;

    RegistrationResult(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}


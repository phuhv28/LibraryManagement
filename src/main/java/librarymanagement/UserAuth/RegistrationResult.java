package librarymanagement.UserAuth;

public enum RegistrationResult {
    SUCCESS("Registration successful!"),
    USERNAME_TAKEN("Username is already taken."),
    INVALID_USERNAME("Username is invalid.");

    private String message;

    RegistrationResult(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}


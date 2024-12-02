package librarymanagement.entity;

public enum ChangePasswordResult {
    SUCCESS_CHANGE("Password changed successfully."),
    WRONG_OLD_PASSWORD("Old password is incorrect."),
    WRONG_CONFIRM_NEW_PASSWORD("New password and confirm password is not matched.");

    private final String message;

    ChangePasswordResult (String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}

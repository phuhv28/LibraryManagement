package librarymanagement.UserAuth;

public enum AddUserResult {
    SUCCESS_CREAT("An account has been successfully created."),
    ACCOUNT_EXIST("This account already exists.Please choose a different username."),
    PASSWORD_NOT_MATCH("The password do not match."),
    SUCCESS_ADMIN_SET("The user account has been successfully promoted to admin."),
    ACCOUNT_NOT_ADMIN("The account is not an admin account.");


    private final String message;

    AddUserResult(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}

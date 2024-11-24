package librarymanagement.gui.controllers;

public enum BorrowResult {
    OUT_OF_STOCK("Sorry, this book is currently out of stock!"),
    NOT_FOUND("The document is not available!"),
    SUCCESS("The book has been borrowed successfully!");

    private final String message;

    BorrowResult(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}

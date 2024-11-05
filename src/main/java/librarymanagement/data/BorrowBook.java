package librarymanagement.data;

public class BorrowBook {
    private int stt;
    private int documentId;
    private String documentTitle;
    private String borrowDate;
    private String dueDate;

    public static int count = 0;

    public BorrowBook(int documentId, String documentTitle, String borrowDate, String dueDate) {
        this.documentId = documentId;
        this.documentTitle = documentTitle;
        this.borrowDate = borrowDate;
        this.dueDate = dueDate;
        count++;
        this.stt = count;
    }

    public int getStt() {
        return stt;
    }

    public int getDocumentId() {
        return documentId;
    }

    public String getDocumentTitle() {
        return documentTitle;
    }

    public String getBorrowDate() {
        return borrowDate;
    }

    public String getDueDate() {
        return dueDate;
    }
}

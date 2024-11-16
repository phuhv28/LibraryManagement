package librarymanagement.data;

public class BorrowBook {
    private String transactionID;
    private String userID;
    private String docID;
    private String docTitle;
    private String issueDate;
    private String returnDate;

    public BorrowBook(String transactionID, String userID, String docID, String docTitle, String issueDate, String returnDate) {
        this.transactionID = transactionID;
        this.userID = userID;
        this.docID = docID;
        this.docTitle = docTitle;
        this.issueDate = issueDate;
        this.returnDate = returnDate;
    }

    public String getTransactionID() {
        return transactionID;
    }

    public void setTransactionID(String transactionID) {
        this.transactionID = transactionID;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getDocID() {
        return docID;
    }

    public void setDocID(String docID) {
        this.docID = docID;
    }

    public String getDocTitle() {
        return docTitle;
    }

    public void setDocTitle(String docTitle) {
        this.docTitle = docTitle;
    }

    public String getIssueDate() {
        return issueDate;
    }

    public void setIssueDate(String issueDate) {
        this.issueDate = issueDate;
    }

    public String getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(String returnDate) {
        this.returnDate = returnDate;
    }
}

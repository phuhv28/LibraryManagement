package librarymanagement.data;

class Magazine extends Document {
    private int issueNumber;
    private String month;

    public Magazine() {}

    public Magazine(String documentID, String title, String publisher, int publicationYear, int numberAll, int numberAvailable, int issueNumber, String month) {
        super(title, publisher, publicationYear, numberAll, numberAvailable);
        this.issueNumber = issueNumber;
        this.month = month;
    }

    public int getIssueNumber() {
        return issueNumber;
    }

    public void setIssueNumber(int issueNumber) {
        this.issueNumber = issueNumber;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    @Override
    public String getInfo() {
        return super.getInfo() + "\n" +
                "Issue Number: " + issueNumber + "\n" +
                "Month: " + month;
    }
}


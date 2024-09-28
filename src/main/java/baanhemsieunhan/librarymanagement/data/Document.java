package baanhemsieunhan.librarymanagement.data;

public class Document {
    private String documentID;
    private String title;
    private String publisher;
    private int publicationYear;
    private int numberAll;
    private int numberAvailable;

    public Document() {}

    public Document(String documentID, String title, String publisher, int publicationYear, int numberAll, int numberAvailable) {
        this.documentID = documentID;
        this.title = title;
        this.publisher = publisher;
        this.publicationYear = publicationYear;
        this.numberAll = numberAll;
        this.numberAvailable = numberAvailable;
    }

    public void setDocumentID(String documentID) {
        this.documentID = documentID;
    }

    public void setNumberAll(int numberAll) {
        this.numberAll = numberAll;
    }

    public void setNumberAvailable(int numberAvailable) {
        this.numberAvailable = numberAvailable;
    }

    public void setPublicationYear(int publicationYear) {
        this.publicationYear = publicationYear;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDocumentID() {
        return documentID;
    }

    public String getPublisher() {
        return publisher;
    }

    public String getTitle() {
        return title;
    }

    public int getNumberAll() {
        return numberAll;
    }

    public int getNumberAvailable() {
        return numberAvailable;
    }

    public int getPublicationYear() {
        return publicationYear;
    }

    public String getInfo() {
        return "ID: " + documentID + "\n" +
                "Title: " + title + "\n" +
                "Publisher: " + publisher + "\n" +
                "Publication year: " + publicationYear + "\n" +
                "Total book: " + numberAll + "\n" +
                "Remaining book: " + numberAvailable;
    }
}

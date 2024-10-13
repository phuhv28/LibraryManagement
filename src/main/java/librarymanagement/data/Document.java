package librarymanagement.data;

public class Document {
    private String ISBN;
    private String title;
    private String publisher;
    private int publicationYear;
    private int totalQuantity;
    private int availableQuantity;

    public Document() {}

    public Document(String documentID, String title, String publisher, int publicationYear, int totalQuantity, int availableQuantity) {
        this.ISBN = documentID;
        this.title = title;
        this.publisher = publisher;
        this.publicationYear = publicationYear;
        this.totalQuantity = totalQuantity;
        this.availableQuantity = availableQuantity;
    }

    public void setISBN(String ISBN) {
        this.ISBN = ISBN;
    }

    public void setTotalQuantity(int totalQuantity) {
        this.totalQuantity = totalQuantity;
    }

    public void setAvailableQuantity(int availableQuantity) {
        this.availableQuantity = availableQuantity;
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

    public String getISBN() {
        return ISBN;
    }

    public String getPublisher() {
        return publisher;
    }

    public String getTitle() {
        return title;
    }

    public int getTotalQuantity() {
        return totalQuantity;
    }

    public int getAvailableQuantity() {
        return availableQuantity;
    }

    public int getPublicationYear() {
        return publicationYear;
    }

    public String getInfo() {
        return "ID: " + ISBN + "\n" +
                "Title: " + title + "\n" +
                "Publisher: " + publisher + "\n" +
                "Publication year: " + publicationYear + "\n" +
                "Total book: " + totalQuantity + "\n" +
                "Remaining book: " + availableQuantity;
    }
}

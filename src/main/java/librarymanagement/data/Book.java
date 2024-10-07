package librarymanagement.data;

class Book extends Document {
    private Person author;
    private int numberOfPage;

    public Book() {}

    public Book(String documentID, String title, String publisher, int publicationYear, int numberAll, int numberAvailable, Person author, int numberOfPage) {
        super(documentID, title, publisher, publicationYear, numberAll, numberAvailable);
        this.author = author;
        this.numberOfPage = numberOfPage;
    }

    public void setAuthor(Person author) {
        this.author = author;
    }

    public Person getAuthor() {
        return author;
    }

    public void setNumberOfPage(int numberOfPage) {
        this.numberOfPage = numberOfPage;
    }

    public int getNumberOfPage() {
        return numberOfPage;
    }

    @Override
    public String getInfo() {
        return super.getInfo() + "\n" +
                "Number of page: " + numberOfPage + "\n" +
                "Author: " + author.getInfo();
    }
}


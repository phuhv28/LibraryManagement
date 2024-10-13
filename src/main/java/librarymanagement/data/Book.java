package librarymanagement.data;

class Book extends Document {
    private String category;
    private String author;

    public Book() {}

    public Book(String documentID, String title, String publisher, int publicationYear, int numberAll, int numberAvailable, String author, String category) {
        super(documentID, title, publisher, publicationYear, numberAll, numberAvailable);
        this.author = author;
        this.category = category;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    @Override
    public String getInfo() {
        return super.getInfo() + "\n"
                + "Author: " + this.author + "\n"
                + "Category: " + this.category;
    }
}


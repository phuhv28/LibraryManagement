package librarymanagement.data;

class Book extends Document {
    private String ISBN;
    private String categories;
    private String author;
    private String description;

    public Book() {}

    @Override
    public String getDocumentType() {
        return "Book";
    }

    public Book(String id, String title, String publisher, String publishedDate, int pageCount, int availableCopies, double averageRating, int ratingsCount, String ISBN, String categories, String author, String description) {
        super(id, title, publisher, publishedDate, pageCount, availableCopies, averageRating, ratingsCount);
        this.ISBN = ISBN;
        this.categories = categories;
        this.author = author;
        this.description = description;
    }

    public String getISBN() {
        return ISBN;
    }

    public void setISBN(String ISBN) {
        this.ISBN = ISBN;
    }

    public String getCategories() {
        return categories;
    }

    public void setCategories(String categories) {
        this.categories = categories;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String getInfo() {
        return super.getInfo() + "\n" +
                "ISBN: " + ISBN + "\n" +
                "Author: " + this.author + "\n" +
                "Category: " + this.categories + "\n" +
                "Description: " + this.description + "\n";
    }
}
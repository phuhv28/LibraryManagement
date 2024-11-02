package librarymanagement.data;

class Newspaper extends Document {
    private String author;

    public Newspaper() {}

    @Override
    public String getDocumentType() {
        return "";
    }

    public Newspaper(String title, String publisher, String publishedDate, int numberAll, int numberAvailable, String author) {
        super(title, publisher, publishedDate, numberAll, numberAvailable);
        this.author = author;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    @Override
    public String getInfo() {
        return super.getInfo() +
                "\nAuthor: " + author;
    }
}

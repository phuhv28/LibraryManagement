package librarymanagement.data;

import java.util.List;

abstract class Document {
    private String id;
    private String title;
    private String publisher;
    private String publishedDate;
    private int pageCount;
    private int availableCopies;
    private double averageRating;
    private int ratingsCount;

    public Document() {}

    public Document(String id, String title, String publisher, String publishedDate, int pageCount, int availableCopies, double averageRating, int ratingsCount) {
        this.id = id;
        this.title = title;
        this.publisher = publisher;
        this.publishedDate = publishedDate;
        this.pageCount = pageCount;
        this.availableCopies = availableCopies;
        this.averageRating = averageRating;
        this.ratingsCount = ratingsCount;
    }

    public String getId() { return id; }
    public String getTitle() { return title; }
    public String getPublisher() { return publisher; }
    public String getPublishedDate() { return publishedDate; }
    public int getPageCount() { return pageCount; }
    public int getAvailableCopies() { return availableCopies; }
    public double getAverageRating() { return averageRating; }
    public int getRatingsCount() { return ratingsCount; }

    public void setId(String id) { this.id = id; }
    public void setTitle(String title) { this.title = title; }
    public void setPublisher(String publisher) { this.publisher = publisher; }
    public void setPublishedDate(String publishedDate) { this.publishedDate = publishedDate; }
    public void setPageCount(int pageCount) { this.pageCount = pageCount; }
    public void setAvailableCopies(int availableCopies) { this.availableCopies = availableCopies; }
    public void setAverageRating(double averageRating) { this.averageRating = averageRating; }
    public void setRatingsCount(int ratingsCount) { this.ratingsCount = ratingsCount; }

    public abstract List<Object> getAll();

    public abstract String getDocumentType();

    public String getInfo() {
        return "ID: " + id + "\n" +
                "Title: " + title + "\n" +
                "Publisher: " + publisher + "\n" +
                "Published Date: " + publishedDate + "\n" +
                "Page Count: " + pageCount + "\n" +
                "Available Copies: " + availableCopies + "\n" +
                "Average Rating: " + averageRating + "\n" +
                "Ratings Count: " + ratingsCount;
    }
}

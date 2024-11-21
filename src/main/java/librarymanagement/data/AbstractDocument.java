package librarymanagement.data;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;

public abstract class AbstractDocument implements Document {
    protected String id;
    protected String title;
    protected String publisher;
    protected LocalDate publishedDate;
    protected int pageCount;
    protected int availableCopies;
    protected double averageRating;
    protected int ratingsCount;
    protected DocumentType documentType;
    protected String linkToAPI;
    protected byte[] thumbnailImage;

    public AbstractDocument() {
    }

    public AbstractDocument(String id, String title, String publisher, LocalDate publishedDate, int pageCount,
                            int availableCopies, double averageRating, int ratingsCount) {
        this.id = id;
        this.title = title;
        this.publisher = publisher;
        this.publishedDate = publishedDate;
        this.pageCount = pageCount;
        this.availableCopies = availableCopies;
        this.averageRating = averageRating;
        this.ratingsCount = ratingsCount;
    }

    public AbstractDocument(String id, String title, String publisher, LocalDate publishedDate, int pageCount,
                            int availableCopies, double averageRating, int ratingsCount,
                           String linkToAPI, byte[] thumbnailImage) {
        this.id = id;
        this.title = title;
        this.publisher = publisher;
        this.publishedDate = publishedDate;
        this.pageCount = pageCount;
        this.availableCopies = availableCopies;
        this.averageRating = averageRating;
        this.ratingsCount = ratingsCount;
        this.linkToAPI = linkToAPI;
        this.thumbnailImage = thumbnailImage;
    }

//        @Override
    public String getId() {
        return id;
    }

    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public String getPublisher() {
        return publisher;
    }

    @Override
    public LocalDate getPublishedDate() {
        return publishedDate;
    }

    @Override
    public int getPageCount() {
        return pageCount;
    }

    @Override
    public int getAvailableCopies() {
        return availableCopies;
    }

    @Override
    public double getAverageRating() {
        return averageRating;
    }

    @Override
    public int getRatingsCount() {
        return ratingsCount;
    }

    @Override
    public DocumentType getDocumentType() {
        return documentType;
    }

    @Override
    public void setId(String id) {
        this.id = id;
    }

    @Override
    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    @Override
    public void setPublishedDate(LocalDate publishedDate) {
        this.publishedDate = publishedDate;
    }

    @Override
    public void setPageCount(int pageCount) {
        this.pageCount = pageCount;
    }

    @Override
    public void setAvailableCopies(int availableCopies) {
        this.availableCopies = availableCopies;
    }

    @Override
    public void setAverageRating(double averageRating) {
        this.averageRating = averageRating;
    }

    @Override
    public void setRatingsCount(int ratingsCount) {
        this.ratingsCount = ratingsCount;
    }

    @Override
    public void setDocumentType(DocumentType documentType) {
        this.documentType = documentType;
    }

    public String getLinkToAPI() {
        return linkToAPI;
    }

    public void setLinkToAPI(String linkToAPI) {
        this.linkToAPI = linkToAPI;
    }

    public InputStream getThumbnailImage() {
        if(thumbnailImage == null ) {
            return null;
        }
        return new ByteArrayInputStream(thumbnailImage);
    }

    public void setThumbnailImage(InputStream thumbnailImage) throws IOException {
        this.thumbnailImage = GoogleBooksAPI.convertInputStreamToByteArray(thumbnailImage);
    }

    public void setThumbnailImage(byte[] thumbnailImage) {
        this.thumbnailImage = thumbnailImage;
    }

    //    public abstract List<Object> getAll();

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

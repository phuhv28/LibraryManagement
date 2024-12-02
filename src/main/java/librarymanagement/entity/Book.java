package librarymanagement.entity;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


/**
 * Class represent for Books.
 */
public class Book extends AbstractDocument {
    private String ISBN;
    private String categories;
    private String author;
    private String description;

    public Book() {
        this.documentType = DocumentType.BOOK;
    }

    public Book(String id, String title, String publisher, LocalDate publishedDate, int pageCount,
                int availableCopies, double averageRating, int ratingsCount, String ISBN,
                String categories, String author, String description) {
        super(id, title, publisher, publishedDate, pageCount, availableCopies, averageRating, ratingsCount);
        this.ISBN = ISBN;
        this.categories = categories;
        this.author = author;
        this.description = description;
        this.documentType = DocumentType.BOOK;
    }

    public Book(String id, String title, String publisher, LocalDate publishedDate, int pageCount, int availableCopies,
                double averageRating, int ratingsCount, String ISBN,
                String categories, String author, String description, String linkToAPI, byte[] thumbnailImage) {
        super(id, title, publisher, publishedDate, pageCount, availableCopies,
                averageRating, ratingsCount, linkToAPI, thumbnailImage);
        this.ISBN = ISBN;
        this.categories = categories;
        this.author = author;
        this.description = description;
        this.documentType = DocumentType.BOOK;
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

    //    @Override

    /**
     * Retrieves all the properties of the object as a list of {@link Object}.
     * The method returns a list containing various attributes of the object such as ISBN,
     * title, author, publisher, published date, categories, page count, available copies,
     * description, average rating, ratings count, link to API, and thumbnail image.
     *
     * <p><strong>Note:</strong> The published date is converted to a string if it's not null,
     * otherwise, it will be added as null to the list.</p>
     *
     * @return a list of {@link Object} containing all the attributes of the object.
     */
    public List<Object> getAll() {
        List<Object> res = new ArrayList<>();

        res.add(ISBN);
        res.add(getId());
        res.add(getTitle());
        res.add(author);
        res.add(getPublisher());
        if (getPublishedDate() == null) {
            res.add(null);
        } else {
            res.add(getPublishedDate().toString());
        }
        res.add(categories);
        res.add(getPageCount());
        res.add(getAvailableCopies());
        res.add(description);
        res.add(getAverageRating());
        res.add(getRatingsCount());
        res.add(linkToAPI);
        res.add(thumbnailImage);
        return res;
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
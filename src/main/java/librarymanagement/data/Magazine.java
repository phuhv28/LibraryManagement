package librarymanagement.data;

import java.time.LocalDate;

class Magazine extends AbstractDocument {
    private String ISBN;
    private String categories;

    public Magazine(String ISBN, String categories) {
        this.ISBN = ISBN;
        this.categories = categories;
    }

    public Magazine(String id, String title, String publisher, LocalDate publishedDate, int pageCount,
                    int availableCopies, double averageRating, int ratingsCount, String ISBN,
                    String categories, String linkToAPI, byte[] thumbnailImage) {
        super(id, title, publisher, publishedDate, pageCount, availableCopies, averageRating, ratingsCount,
                linkToAPI, thumbnailImage);
        this.ISBN = ISBN;
        this.categories = categories;
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
}


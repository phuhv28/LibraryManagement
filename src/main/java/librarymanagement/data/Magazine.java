package librarymanagement.data;

import java.time.LocalDate;

class Magazine extends AbstractDocument {
    private String ISSN;
    private String categories;

    public Magazine(String ISSN, String categories) {
        this.ISSN = ISSN;
        this.categories = categories;
    }

    public Magazine(String id, String title, String publisher, LocalDate publishedDate, int pageCount, int availableCopies, double averageRating, int ratingsCount, String ISSN, String categories) {
        super(id, title, publisher, publishedDate, pageCount, availableCopies, averageRating, ratingsCount);
        this.ISSN = ISSN;
        this.categories = categories;
    }

    public String getISSN() {
        return ISSN;
    }

    public void setISSN(String ISSN) {
        this.ISSN = ISSN;
    }

    public String getCategories() {
        return categories;
    }

    public void setCategories(String categories) {
        this.categories = categories;
    }
}


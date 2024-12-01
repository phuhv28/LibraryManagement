package librarymanagement.entity;

import java.time.LocalDate;

public interface Document {

    String getId();

    String getTitle();

    String getPublisher();

    LocalDate getPublishedDate();

    int getPageCount();

    int getAvailableCopies();

    double getAverageRating();

    int getRatingsCount();

    void setId(String id);

    void setTitle(String title);

    void setPublisher(String publisher);

    void setPublishedDate(LocalDate publishedDate);

    void setPageCount(int pageCount);

    void setAvailableCopies(int availableCopies);

    void setAverageRating(double averageRating);

    void setRatingsCount(int ratingsCount);

    DocumentType getDocumentType();

    void setDocumentType(DocumentType documentType);
}

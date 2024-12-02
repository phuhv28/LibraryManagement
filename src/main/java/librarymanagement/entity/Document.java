package librarymanagement.entity;

import java.time.LocalDate;

public interface Document {

    String getId();

    void setId(String id);

    String getTitle();

    void setTitle(String title);

    String getPublisher();

    void setPublisher(String publisher);

    LocalDate getPublishedDate();

    void setPublishedDate(LocalDate publishedDate);

    int getPageCount();

    void setPageCount(int pageCount);

    int getAvailableCopies();

    void setAvailableCopies(int availableCopies);

    double getAverageRating();

    void setAverageRating(double averageRating);

    int getRatingsCount();

    void setRatingsCount(int ratingsCount);

    DocumentType getDocumentType();

    void setDocumentType(DocumentType documentType);
}

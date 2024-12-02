package librarymanagement.entity;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Magazine extends AbstractDocument {
    private String ISSN;
    private String categories;

    public Magazine(String ISSN, String categories) {
        this.ISSN = ISSN;
        this.categories = categories;
    }

    public Magazine(String id, String title, String publisher, LocalDate publishedDate, int pageCount,
                    int availableCopies, double averageRating, int ratingsCount, String ISSN,
                    String categories, String linkToAPI, byte[] thumbnailImage) {
        super(id, title, publisher, publishedDate, pageCount, availableCopies, averageRating, ratingsCount,
                linkToAPI, thumbnailImage);
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

    /**
     * Retrieves all the attributes of the magazine as a list of objects.
     *
     * <p>This method collects all the fields of the `Magazine` object, including attributes inherited from the
     * `AbstractDocument` class, and returns them as a `List<Object>`. The list includes the following attributes in order:</p>
     * <ol>
     *     <li>id (String): The unique identifier for the magazine.</li>
     *     <li>ISSN (String): The International Standard Serial Number of the magazine.</li>
     *     <li>title (String): The title of the magazine.</li>
     *     <li>publisher (String): The publisher of the magazine.</li>
     *     <li>publishedDate (LocalDate): The publication date of the magazine.</li>
     *     <li>categories (String): The categories associated with the magazine.</li>
     *     <li>pageCount (int): The number of pages in the magazine.</li>
     *     <li>availableCopies (int): The number of available copies of the magazine.</li>
     *     <li>averageRating (double): The average rating of the magazine.</li>
     *     <li>ratingsCount (int): The total number of ratings for the magazine.</li>
     *     <li>linkToAPI (String): The API link for additional details about the magazine.</li>
     *     <li>thumbnailImage (byte[]): The thumbnail image of the magazine.</li>
     * </ol>
     *
     * @return a {@code List<Object>} containing all attributes of the magazine in the specified order.
     */
    public List<Object> getAll() {
        List<Object> list = new ArrayList<>();
        list.add(id);
        list.add(ISSN);
        list.add(title);
        list.add(publisher);
        list.add(publishedDate);
        list.add(categories);
        list.add(pageCount);
        list.add(availableCopies);
        list.add(averageRating);
        list.add(ratingsCount);
        list.add(linkToAPI);
        list.add(thumbnailImage);
        return list;
    }
}


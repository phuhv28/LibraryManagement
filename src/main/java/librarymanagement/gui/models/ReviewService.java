package librarymanagement.gui.models;

import librarymanagement.entity.Book;
import librarymanagement.entity.Review;
import librarymanagement.utils.SQLiteInstance;

import java.util.ArrayList;
import java.util.List;

public class ReviewService {
    SQLiteInstance sqLiteInstance = new SQLiteInstance();

    public ReviewService() {
    }

    /**
     * Checks if a user has commented on a specific document.
     *
     * <p>This method queries the "Review" table to check if a review exists where the given
     * username and documentID match. If such a record is found, it indicates that the user
     * has commented on the specified document.</p>
     *
     * @param username the username of the user
     * @param docID    the ID of the document
     * @return {@code true} if the user has commented on the document, {@code false} otherwise
     */
    public boolean checkIfUserHasCommentToDocument(String username, String docID) {
        String sql = "SELECT username FROM Review WHERE username = '" + username + "' AND docID = '" + docID + "'";
        List<List<Object>> lists = sqLiteInstance.findWithSQL(sql, new Object[]{}, "username");
        return !lists.isEmpty();
    }

    /**
     * Adds a review for a document if the user hasn't already commented on it.
     *
     * <p>This method allows a user to add a review (rating and comment) to a document. It checks
     * whether the user has already commented on the document. If not, it updates the document's
     * rating and rating count (for books) and inserts the review into the "Review" table.</p>
     *
     * <p>If the document is a book (indicated by a 'B' in the document ID), it updates the book's
     * average rating and the count of ratings. If the document is of type "Magazine" or "Thesis",
     * further handling is needed (denoted by TODO).</p>
     *
     * @param username the username of the user adding the review
     * @param docID    the ID of the document being reviewed
     * @param rating   the rating given to the document (assumed to be an integer)
     * @param comment  the comment provided by the user
     * @return {@code true} if the review was successfully added, {@code false} if the user has
     * already commented on the document
     */
    public boolean addReview(String username, String docID, int rating, String comment) {
        if (docID.charAt(0) == 'B') {
            BookService bookService = new BookService();
            Book book = bookService.findDocumentById(docID);
            book.setAverageRating((book.getAverageRating() * book.getRatingsCount() + (double) rating)
                    / (book.getRatingsCount() + 1));
            book.setRatingsCount(book.getRatingsCount() + 1);
            bookService.updateDocument(book);
        } else if (docID.charAt(0) == 'M') {
            //TODO
        } else if (docID.charAt(0) == 'T') {
            //TODO
        }
        sqLiteInstance.insertRow("Review", username, docID, comment, rating);
        return true;
    }

    /**
     * Adds a review for a document by the current logged-in user.
     *
     * <p>This method allows the current user (retrieved from the account service) to add a review
     * (rating and comment) for a specific document. It calls the {@link #addReview(String, String, int, String)}
     * method to handle the actual review process.</p>
     *
     * @param docID   the ID of the document being reviewed
     * @param comment the comment provided by the user
     * @param rating  the rating given to the document (assumed to be an integer)
     * @return {@code true} if the review was successfully added, {@code false} if the current user
     * has already commented on the document
     */
    public boolean addReviewForCurrentAccount(String docID, String comment, int rating) {
        String username = AccountService.getInstance().getCurrentAccount().getUsername();
        return addReview(username, docID, rating, comment);
    }

    /**
     * Retrieves all reviews for a specific document.
     *
     * <p>This method fetches all reviews associated with a given document by querying the "Review"
     * table in the database. Each review includes the username, rating, and comment provided by
     * the user.</p>
     *
     * @param docID the ID of the document for which reviews are being retrieved
     * @return a list of {@link Review} objects containing all reviews for the specified document,
     * or an empty list if no reviews are found
     */
    public List<Review> getAllReviewsInDocument(String docID) {
        List<Review> reviews = new ArrayList<>();
        List<List<Object>> lists = sqLiteInstance.find("Review", "docID", docID, "username", "rating", "comment");
        for (List<Object> list : lists) {
            String username = (String) list.get(0);
            int rating = (int) list.get(1);
            String comment = (String) list.get(2);
            reviews.add(new Review(username, docID, comment, rating));
        }
        return reviews;
    }
}

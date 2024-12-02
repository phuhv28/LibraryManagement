import librarymanagement.entity.Review;
import librarymanagement.gui.models.ReviewService;
import librarymanagement.utils.SQLiteInstance;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

class ReviewServiceTest {

    private ReviewService reviewService;
    private SQLiteInstance sqLiteInstance;

    @BeforeEach
    void setUp() {
        reviewService = new ReviewService();
        sqLiteInstance = new SQLiteInstance();
    }

    @Test
    void testCheckIfUserHasCommentToDocument_ExistingReview() {
        String username = "user1";
        String docID = "B101";
        boolean result = reviewService.checkIfUserHasCommentToDocument(username, docID);
        assertTrue(result, "User should have commented on the document.");
    }

    @Test
    void testCheckIfUserHasCommentToDocument_NoReview() {
        String username = "user2";
        String docID = "B101";
        boolean result = reviewService.checkIfUserHasCommentToDocument(username, docID);
        assertFalse(result, "User should not have commented on the document.");
    }

    @Test
    void testAddReview_NewReview() {
        String username = "user1";
        String docID = "B101";
        int rating = 5;
        String comment = "Excellent book!";

        boolean result = reviewService.addReview(username, docID, rating, comment);
        assertTrue(result, "Review should be added successfully.");

        // Optionally, verify if the review was actually added to the database
        List<Review> reviews = reviewService.getAllReviewsInDocument(docID);
        assertTrue(reviews.stream().anyMatch(r -> r.getUsername().equals(username) && r.getRating() == rating),
                "The added review should be present in the review list.");
        String condition = "username = 'user1'";
        sqLiteInstance.deleteRow("Review", condition);
    }

    @Test
    void testAddReview_UserAlreadyReviewed() {
        String username = "user1";
        String docID = "B101";
        int rating = 4;
        String comment = "Good book.";

        reviewService.addReview(username, docID, rating, comment);

        boolean result = reviewService.addReview(username, docID, 5, "Excellent!");
        assertFalse(result, "Review should not be added again for the same user and document.");
    }

    @Test
    void testGetAllReviewsInDocument() {
        String docID = "B101";
        List<Review> reviews = reviewService.getAllReviewsInDocument(docID);
        assertNotNull(reviews, "Reviews should not be null.");
        assertTrue(reviews.size() > 0, "There should be at least one review for the document.");
    }
}

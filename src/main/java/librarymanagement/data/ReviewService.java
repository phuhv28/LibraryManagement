package librarymanagement.data;

import librarymanagement.UserAuth.AccountService;

import java.util.ArrayList;
import java.util.List;

public class ReviewService {
    SQLiteInstance sqLiteInstance = new SQLiteInstance();

    public ReviewService() {
    }

    public boolean checkIfUserHasCommentToDocument(String username, String docID) {
        String sql = "SELECT username FROM Review WHERE username = '" + username + "' AND docID = '" + docID + "'";
        List<List<Object>> lists = sqLiteInstance.findWithSQL(sql, new Object[]{}, "username");
        return !lists.isEmpty();
    }

    public boolean addReview(String username, String docID, int rating, String comment) {
        if (checkIfUserHasCommentToDocument(username, docID)) {
            return false;
        }
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

    public boolean addReviewForCurrentAccount(String docID, String comment, int rating) {
        String username = AccountService.getInstance().getCurrentAccount().getUsername();
        return addReview(username, docID, rating, comment);
    }

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

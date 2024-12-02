package librarymanagement.gui.models;

import librarymanagement.entity.Magazine;
import librarymanagement.utils.SQLiteInstance;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/** Class handles handle operations related to magazines-operations related to magazine (Add, edit, delete,...).*/
public class MagazineService implements DocumentService<Magazine> {
    private static final SQLiteInstance sqLiteInstance = SQLiteInstance.getInstance();

    /**
     * Generates a new unique ID for a magazine by finding the highest ID in the database
     * and incrementing it.
     *
     * @return A new unique magazine ID.
     */
    private String generateNewID() {
        String newId;

        List<List<Object>> result = sqLiteInstance.findNotCondition("Book", "Max(id)");
        if (result.getFirst().getFirst() == null) {
            newId = "M101";
        } else {
            String temp = result.getFirst().getFirst().toString().substring(1);
            newId = "M" + (Integer.parseInt(temp) + 1);
        }

        return newId;
    }

    /**
     * Checks if a magazine with the given ISSN already exists in the database.
     *
     * @param issn The ISSN to check.
     * @return {@code true} if the ISSN exists, {@code false} otherwise.
     */
    private boolean checkIfHasMagazineISSN(String issn) {
        List<List<Object>> res = sqLiteInstance.find("Magazine", "ISSN", issn, "ISBN");
        if (res.isEmpty() || res.getFirst().isEmpty()) {
            return false;
        }
        return res.getFirst().getFirst() != null;
    }

    /**
     * Checks if a magazine with the given ID exists in the database.
     *
     * @param id The ID to check.
     * @return {@code true} if the ID exists, {@code false} otherwise.
     */
    private boolean checkIfHasMagazineId(String id) {
        List<List<Object>> res = sqLiteInstance.find("Magazine", "id", id, "id");
        if (res.isEmpty() || res.getFirst().isEmpty()) {
            return false;
        }
        return res.getFirst().getFirst() != null;
    }

    /**
     * Checks if a magazine with the given title already exists in the database.
     *
     * @param title The title to check.
     * @return {@code true} if the title exists, {@code false} otherwise.
     */
    private boolean checkIfHasMagazineTitle(String title) {
        List<List<Object>> res = sqLiteInstance.find("Magazine", "title", title, "title");
        if (res.isEmpty() || res.getFirst().isEmpty()) {
            return false;
        }
        return res.getFirst().getFirst() != null;
    }

    /**
     * Adds a new magazine to the database. Validates that the magazine ISSN or title
     * does not already exist before adding it.
     *
     * @param magazine The {@link Magazine} to be added.
     * @return {@code true} if the magazine was successfully added, {@code false} otherwise.
     */
    @Override
    public boolean addDocument(Magazine magazine) {
        if (magazine.getISSN() == null && checkIfHasMagazineTitle(magazine.getTitle())) {
            return false;
        } else if (magazine.getISSN().equals("N/A") && checkIfHasMagazineTitle(magazine.getTitle())) {
            return false;
        } else if (checkIfHasMagazineISSN(magazine.getISSN())) {
            return false;
        }
        magazine.setId(generateNewID());
        sqLiteInstance.insertRow("Magazine", magazine.getAll());
        return true;
    }

    /**
     * Updates the details of an existing magazine in the database.
     *
     * @param magazine The updated {@link Magazine} object.
     */
    @Override
    public void updateDocument(Magazine magazine) {
        deleteDocument(magazine.getId());
        sqLiteInstance.insertRow("Magazine", magazine.getAll());
    }

    /**
     * Deletes a magazine from the database by its ID.
     *
     * @param id The ID of the magazine to be deleted.
     * @return {@code true} if the magazine was successfully deleted, {@code false} otherwise.
     */
    @Override
    public boolean deleteDocument(String id) {
        if (!checkIfHasMagazineISSN(id)) {
            return false;
        }

        String condition = "id = " + "'" + id + "'";
        sqLiteInstance.deleteRow("Magazine", condition);
        return true;
    }

    /**
     * Creates a list of magazines based on the given SQL query and condition.
     *
     * @param condition The condition to filter the query.
     * @param sql       The SQL query to execute.
     * @return A list of {@link Magazine} objects matching the query, or {@code null} if none were found.
     */
    private List<Magazine> createNewMagazineList(String condition, String sql) {
        List<Magazine> magazines = new ArrayList<>();
        try (PreparedStatement stmt = sqLiteInstance.connection.prepareStatement(sql)) {
            if (condition != null && !condition.isEmpty()) {
                stmt.setString(1, "%" + condition + "%");
            }
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                String issn = rs.getString("ISSN");
                String id = rs.getString("id");
                String title = rs.getString("title");
                String publisher = rs.getString("publisher");
                String publishedDate = rs.getString("publishedDate");
                String categories = rs.getString("categories");
                int pageCount = rs.getInt("pageCount");
                int availableCopies = rs.getInt("availableCopies");
                float averageRating = rs.getFloat("averageRating");
                int ratingCount = rs.getInt("ratingsCount");
                String linkToAPI = rs.getString("linkToAPI");
                byte[] thumbnailImage = rs.getBytes("thumbnailImage");

                LocalDate finalDate = null;
                if (publishedDate != null && !publishedDate.equals("N/A")) {
                    if (publishedDate.length() == 4) {
                        finalDate = LocalDate.of(Integer.parseInt(publishedDate), 1, 1);
                    } else if (publishedDate.length() == 7) {
                        DateTimeFormatter yearMonthFormatter = new DateTimeFormatterBuilder()
                                .appendPattern("yyyy-MM")
                                .parseDefaulting(java.time.temporal.ChronoField.DAY_OF_MONTH, 1)
                                .toFormatter(Locale.getDefault());
                        finalDate = LocalDate.parse(publishedDate, yearMonthFormatter);
                    } else {
                        finalDate = LocalDate.parse(publishedDate);
                    }
                }

                magazines.add(new Magazine(id, title, publisher, finalDate, pageCount, availableCopies, averageRating,
                        ratingCount, issn, categories, linkToAPI, thumbnailImage));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        if (magazines.isEmpty()) {
            return null;
        }

        return magazines;
    }

    /**
     * Searches for magazines by title.
     *
     * @param title The title to search for.
     * @return A list of {@link Magazine} objects matching the title.
     */
    public List<Magazine> searchMagazineByTitle(String title) {
        String sql = "SELECT * FROM Magazine WHERE title LIKE ?";
        return createNewMagazineList(title, sql);
    }

    public Magazine searchMagazineByISSN(String issn) {
        String sql = "SELECT * FROM Magazine WHERE ISSN = ?";
        List<Magazine> magazines = createNewMagazineList(issn, sql);
        assert magazines != null;
        if (magazines.isEmpty()) {
            return null;
        }
        return magazines.getFirst();
    }

    public List<Magazine> searchMagazineByCategory(String category) {
        String sql = "SELECT * FROM Magazine WHERE category = ?";
        return createNewMagazineList(category, sql);
    }

    public List<Magazine> searchMagazineByPublisher(String publisher) {
        String sql = "SELECT * FROM Magazine WHERE publisher = ?";
        return createNewMagazineList(publisher, sql);
    }

    @Override
    public Magazine findDocumentById(String id) {
        String sql = "SELECT * FROM Magazine WHERE id = ?";
        List<Magazine> magazines = createNewMagazineList(sql, id);
        return null;
    }

    public List<Magazine> getRecentlyAddedMagazines() {
        String sql = "SELECT * FROM Magazine ORDER BY id DESC LIMIT 10";
        return createNewMagazineList(null, sql);
    }

    public List<Magazine> getAllMagazines() {
        String sql = "SELECT * FROM Magazine ORDER BY id";
        return createNewMagazineList(null, sql);
    }
}

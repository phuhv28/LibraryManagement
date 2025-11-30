import librarymanagement.utils.SQLiteInstance;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class SQLiteInstanceTest {

    private static SQLiteInstance db;

    @BeforeAll
    static void setUpAll() throws Exception {
        Field instanceField = SQLiteInstance.class.getDeclaredField("instance");
        instanceField.setAccessible(true);
        instanceField.set(null, null);

        SQLiteInstance tempInstance = new SQLiteInstance();

        Connection memConn = DriverManager.getConnection("jdbc:sqlite::memory:");

        Field connectionField = SQLiteInstance.class.getDeclaredField("connection");
        connectionField.setAccessible(true);
        connectionField.set(tempInstance, memConn);

        instanceField.set(null, tempInstance);
        db = tempInstance;
    }

    @BeforeEach
    void setUp() {
        db.createTable("test_table",
                "id INTEGER PRIMARY KEY",
                "name TEXT",
                "age INTEGER",
                "salary DOUBLE",
                "active BOOLEAN",
                "data BLOB"
        );
    }

    @Test
    void insertRow_ListOfSupportedTypes_InsertsSuccessfully() {
        List<Object> values = Arrays.asList(1, "John Doe", 30, 50000.0, true, new byte[]{1, 2, 3});
        db.insertRow("test_table", values);

        List<List<Object>> result = db.findNotCondition("test_table", "id", "name", "age", "salary", "active");
        assertEquals(1, result.size(), "Phải có 1 row được insert");
        List<Object> row = result.get(0);
        assertEquals(1, row.get(0), "ID phải là 1");
        assertEquals("John Doe", row.get(1), "Name phải khớp");
        assertEquals(30, row.get(2), "Age phải là 30");
        assertEquals(50000.0, row.get(3), "Salary phải là 50000.0");
        assertEquals(true, row.get(4), "Active phải true");
    }

    @Test
    void find_WithCondition_MatchesLikePattern() {
        db.insertRow("test_table", 1, "John Doe", 30, null, null, null);
        db.insertRow("test_table", 2, "Jane Smith", 25, null, null, null);

        List<List<Object>> result = db.find("test_table", "name", "J%n%", "id", "name");

        assertEquals(2, result.size(), "Phải match cả 2 row với pattern LIKE");
        assertEquals(1, result.get(0).get(0));
        assertEquals("John Doe", result.get(0).get(1));
    }

    @Test
    void find_WithCondition_NoMatches_ReturnsEmpty() {
        db.insertRow("test_table", 1, "Alice", 20, null, null, null);

        List<List<Object>> result = db.find("test_table", "name", "Bob%", "id", "name");

        assertTrue(result.isEmpty(), "Không match thì empty list");
    }

    @Test
    void find_EmptyColumns_ThrowsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> db.find("test_table", "name", "J%", new String[]{}));
    }

    @Test
    void findNotCondition_RetrievesAllRows() {
        db.insertRow("test_table", 1, "All Test", 35, null, null, null);
        db.insertRow("test_table", 2, "All Test 2", 40, null, null, null);

        List<List<Object>> result = db.findNotCondition("test_table", "id", "name", "age");
        assertEquals(2, result.size());
    }

    @Test
    void findNotCondition_EmptyColumns_ThrowsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> db.findNotCondition("test_table", new String[]{}));
    }

    @Test
    void findWithSQL_ValidQueryWithParams_ReturnsResults() {
        db.insertRow("test_table", 1, "Query Test", 30, null, null, null);

        List<List<Object>> result = db.findWithSQL("SELECT id, name FROM test_table WHERE age = ?", new Object[]{30}, "id", "name");
        assertEquals(1, result.size());
        assertEquals("Query Test", result.get(0).get(1));
    }

    @Test
    void findWithSQL_NoResults_ReturnsEmpty() {
        List<List<Object>> result = db.findWithSQL("SELECT id FROM test_table WHERE age = ?", new Object[]{999}, "id");
        assertTrue(result.isEmpty());
    }

    @Test
    void executeUpdate_WithSetter_UpdatesSuccessfully() {
        db.insertRow("test_table", 1, "Update Test", 30, null, null, null);

        db.executeUpdate("UPDATE test_table SET name = ? WHERE id = ?", stmt -> {
            try {
                stmt.setString(1, "Updated Name");
                stmt.setInt(2, 1);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });

        List<List<Object>> result = db.findNotCondition("test_table", "name");
        assertEquals("Updated Name", result.get(0).get(0));
    }

    @Test
    void query_ValidSQLWithParams_ReturnsResultSet() {
        db.insertRow("test_table", 1, "Query Test", 30, null, null, null);

        ResultSet rs = db.query("SELECT name FROM test_table WHERE id = ?", "1");
        assertNotNull(rs);
        try {
            assertTrue(rs.next());
            assertEquals("Query Test", rs.getString("name"));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    void deleteRow_WithCondition_DeletesRows() {
        db.insertRow("test_table", 1, "Delete Test", 30, null, null, null);
        db.insertRow("test_table", 2, "Keep Test", 25, null, null, null);

        db.deleteRow("test_table", "id = 1");

        List<List<Object>> result = db.findNotCondition("test_table", "id", "name");
        assertEquals(1, result.size());
        assertEquals(2, result.get(0).get(0));
    }

    @Test
    void updateRow_UpdatesColumnSuccessfully() {
        db.insertRow("test_table", 1, "Old Name", 30, null, null, null);

        db.updateRow("test_table", "name", "New Name", "id", 1);

        List<List<Object>> result = db.findNotCondition("test_table", "name");
        assertEquals("New Name", result.get(0).get(0));
    }

    @Test
    void getToday_ReturnsCurrentDate() {
        LocalDate today = db.getToday();
        assertEquals(LocalDate.now(), today);
    }

    @AfterAll
    static void tearDownAll() throws SQLException {
        if (db != null && db.connection != null) {
            db.connection.close();
        }
    }
}
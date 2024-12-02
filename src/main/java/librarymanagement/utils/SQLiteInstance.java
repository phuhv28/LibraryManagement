package librarymanagement.utils;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/** Utils for SQLite.*/
public class SQLiteInstance {
    private static final String CONNECTION_URL = "jdbc:sqlite:library.db";
    private static SQLiteInstance instance = null;
    public Connection connection;

    public SQLiteInstance() {
        try {
            Class.forName("org.sqlite.JDBC");
            this.connection = DriverManager.getConnection(CONNECTION_URL);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public synchronized static SQLiteInstance getInstance() {
        if (instance == null) {
            instance = new SQLiteInstance();
        }
        return instance;
    }

    @FunctionalInterface
    interface PreparedStatementSetter {
        void setValues(PreparedStatement stmt) throws SQLException;
    }

    /**
     * Create table
     * @param tableName name of table
     * @param columns columns of table
     */
    public void createTable(String tableName, String... columns) {
        try {
            StringBuilder sql = new StringBuilder("DROP TABLE IF EXISTS " + tableName + ";");
            try (PreparedStatement preparedStatement1 = connection.prepareStatement(sql.toString())) {
                preparedStatement1.executeUpdate();
                sql = new StringBuilder();
                sql.append("CREATE TABLE ").append(tableName).append(" (");
                for (int i = 0; i < columns.length; i++) {
                    sql.append(columns[i]);
                    if (i != columns.length - 1) {
                        sql.append(", ");
                    }
                }
                sql.append(");");
                try (PreparedStatement preparedStatement2 = connection.prepareStatement(sql.toString())) {
                    preparedStatement2.executeUpdate();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Inserts a row into the specified table with the given values.
     *
     * @param tableName the name of the table where the row will be inserted.
     * @param values    a list of values to be inserted into the table.
     *                  Each value corresponds to a column in the table.
     *                  Supported types include:
     *                  <ul>
     *                      <li>{@code String}</li>
     *                      <li>{@code Integer}</li>
     *                      <li>{@code Double}</li>
     *                      <li>{@code Float}</li>
     *                      <li>{@code Boolean}</li>
     *                      <li>{@code Long}</li>
     *                      <li>{@code java.sql.Date}</li>
     *                      <li>{@code byte[]}</li>
     *                      <li>{@code null} (sets the column to {@code NULL})</li>
     *                  </ul>
     * @throws IllegalArgumentException if a value's type is not supported.
     *
     * <p><strong>Example:</strong></p>
     * <pre>{@code
     * List<Object> values = List.of("John Doe", 30, 50000.0, true);
     * insertRow("employees", values);
     * }</pre>
     *
     * <p>This example inserts a new row into the "employees" table with the values:
     * "John Doe" (String), 30 (Integer), 50000.0 (Double), and true (Boolean).
     */
    public void insertRow(String tableName, List<Object> values) {
        try {
            StringBuilder insertSQL = new StringBuilder("INSERT INTO " + tableName + " VALUES (");
            for (int i = 0; i < values.size(); i++) {
                insertSQL.append("?");
                if (i < values.size() - 1) {
                    insertSQL.append(", ");
                }
            }
            insertSQL.append(")");

            try (PreparedStatement preparedStatement = connection.prepareStatement(insertSQL.toString())) {
                for (int i = 1; i <= values.size(); i++) {
                    Object value = values.get(i - 1);
                    switch (value) {
                        case String s -> preparedStatement.setString(i, s);
                        case Integer integer -> preparedStatement.setInt(i, integer);
                        case Double v -> preparedStatement.setDouble(i, v);
                        case Float v -> preparedStatement.setFloat(i, v);
                        case Boolean b -> preparedStatement.setBoolean(i, b);
                        case Long l -> preparedStatement.setLong(i, l);
                        case Date date -> preparedStatement.setDate(i, date);
                        case byte[] bytes -> preparedStatement.setBytes(i, bytes);
                        case null -> preparedStatement.setNull(i, Types.NULL);
                        default ->
                            throw new IllegalArgumentException("Unsupported data type: " + value.getClass().getName());
                    }
                }

                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Inserts a row into the specified table with the given values.
     *
     * @param tableName the name of the table where the row will be inserted.
     * @param values    a varargs array of values to be inserted into the table.
     *                  Each value corresponds to a column in the table.
     *                  Supported types include:
     *                  <ul>
     *                      <li>{@code String}</li>
     *                      <li>{@code Integer}</li>
     *                      <li>{@code Double}</li>
     *                      <li>{@code Float}</li>
     *                      <li>{@code Boolean}</li>
     *                      <li>{@code Long}</li>
     *                      <li>{@code java.sql.Date}</li>
     *                      <li>{@code byte[]}</li>
     *                      <li>{@code null} (sets the column to {@code NULL})</li>
     *                  </ul>
     *
     * <p>This method delegates to {@link #insertRow(String, List)} by converting the varargs into a {@code List}.</p>
     *
     * <p><strong>Example:</strong></p>
     * <pre>{@code
     * insertRow("employees", "Jane Doe", 25, 60000.0, false);
     * }</pre>
     *
     * <p>This example inserts a new row into the "employees" table with the values:
     * "Jane Doe" (String), 25 (Integer), 60000.0 (Double), and false (Boolean).</p>
     */
    public void insertRow(String tableName, Object... values) {
        insertRow(tableName, Arrays.asList(values));
    }

    /**
     * Retrieves rows from the specified table where the specified condition is met, returning only the specified columns.
     *
     * @param tableName           the name of the table to query.
     * @param columnCheckCondition the name of the column to apply the condition on.
     * @param condition           the value to match using a SQL {@code LIKE} condition.
     *                             This can include wildcards (e.g., {@code %} or {@code _}).
     * @param columns             the columns to retrieve from the table.
     *                             At least one column must be specified.
     * @return a list of rows matching the condition. Each row is represented as a list of objects,
     *         with values corresponding to the specified columns in the order they are provided.
     * @throws IllegalArgumentException if the {@code columns} array is empty.
     *
     * <p><strong>Example:</strong></p>
     * <pre>{@code
     * List<List<Object>> results = find("employees", "name", "Jane%", "id", "name", "salary");
     * }</pre>
     * <p>This example retrieves rows from the "employees" table where the "name" column starts with "Jane".
     * Each row will include the "id", "name", and "salary" columns.</p>
     */
    public List<List<Object>> find(String tableName, String columnCheckCondition, String condition, String... columns) {
        List<List<Object>> values = new ArrayList<>();
        List<String> columnsList = Arrays.asList(columns);

        if (columnsList.isEmpty()) {
            throw new IllegalArgumentException("Columns array must not be empty");
        }

        StringBuilder sql = new StringBuilder("SELECT " + columnsList.getFirst());
        for (int i = 1; i < columnsList.size(); i++) {
            sql.append(", ").append(columnsList.get(i));
        }
        sql.append(" FROM ").append(tableName).append(" WHERE ");
        sql.append(columnCheckCondition).append(" LIKE ?");

        try (PreparedStatement stmt = connection.prepareStatement(sql.toString())) {
            stmt.setString(1, condition);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    List<Object> row = new ArrayList<>();

                    for (String column : columnsList) {
                        row.add(rs.getObject(column));
                    }

                    values.add(row);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return values;
    }

    /**
     * Retrieves all rows from the specified table without applying any conditions,
     * returning only the specified columns.
     *
     * @param tableName the name of the table to query.
     * @param columns   the columns to retrieve from the table. At least one column must be specified.
     * @return a list of rows from the table. Each row is represented as a list of objects,
     *         with values corresponding to the specified columns in the order they are provided.
     * @throws IllegalArgumentException if the {@code columns} array is empty.
     *
     * <p><strong>Example:</strong></p>
     * <pre>{@code
     * // Example: Retrieve all rows with specified columns from the "users" table
     * List<List<Object>> results = findNotCondition("users", "id", "name", "email");
     * for (List<Object> row : results) {
     *     System.out.println("ID: " + row.get(0) + ", Name: " + row.get(1) + ", Email: " + row.get(2));
     * }
     * }</pre>
     * <p>This example retrieves all rows from the "users" table and returns the "id",
     * "name", and "email" columns for each row.</p>
     */
    public List<List<Object>> findNotCondition(String tableName, String... columns) {
        List<List<Object>> values = new ArrayList<>();
        List<String> columnsList = Arrays.asList(columns);

        StringBuilder sql = new StringBuilder("SELECT ").append(columnsList.get(0));
        for (int i = 1; i < columns.length; i++) {
            sql.append(", ").append(columns[i]);
        }
        sql.append(" FROM ").append(tableName);

        try (PreparedStatement stmt = connection.prepareStatement(sql.toString())) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                List<Object> row = new ArrayList<>();
                for (String column : columnsList) {
                    row.add(rs.getObject(column));
                }
                values.add(row);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return values;
    }


    /**
     * Executes a SQL query with parameters and retrieves the specified columns from the result set.
     *
     * @param sql     the SQL query string with placeholders (?).
     * @param params  an array of parameters to bind to the placeholders in the SQL query.
     * @param columns the column names to retrieve from the result set.
     * @return a list of rows, where each row is a list of column values.
     *         Returns an empty list if the query produces no results or an error occurs.
     *
     * <p><strong>Example:</strong></p>
     * <pre>{@code
     * String sql = "SELECT id, title FROM books WHERE author = ?";
     * Object[] params = {"John Doe"};
     * List<List<Object>> results = findWithSQL(sql, params, "id", "title");
     * for (List<Object> row : results) {
     *     System.out.println("ID: " + row.get(0));
     *     System.out.println("Title: " + row.get(1));
     * }
     * }</pre>
     */
    public List<List<Object>> findWithSQL(String sql, Object[] params, String... columns) {
        List<List<Object>> values = new ArrayList<>();
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            for (int i = 0; i < params.length; i++) {
                stmt.setObject(i + 1, params[i]);
            }

            ResultSet rs = stmt.executeQuery();
            if (rs == null) {
                return values;
            }
            while (rs.next()) {
                List<Object> row = new ArrayList<>();
                for (String column : columns) {
                    row.add(rs.getObject(column));
                }
                values.add(row);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return values;
    }

    /**
     * Executes a SQL update operation (e.g., INSERT, UPDATE, DELETE) with parameters.
     *
     * @param sql    the SQL query with placeholders (?).
     * @param setter a {@link PreparedStatementSetter} to set parameter values.
     *
     * <p><strong>Example:</strong></p>
     * <pre>{@code
     * String sql = "UPDATE books SET title = ? WHERE id = ?";
     * executeUpdate(sql, stmt -> {
     *     stmt.setString(1, "New Title");
     *     stmt.setInt(2, 1);
     * });
     * }</pre>
     */
    public void executeUpdate(String sql, PreparedStatementSetter setter) {
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            setter.setValues(stmt);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public ResultSet query(String sql, String... params) {
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql))
        {
            for (int i = 0; i < params.length; i++) {
                preparedStatement.setString(i + 1, params[i]);
            }
            return preparedStatement.executeQuery();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Deletes rows from the specified table that satisfy the given condition.
     *
     * @param tableName the name of the table from which rows will be deleted.
     * @param condition the SQL condition to determine which rows to delete.
     *                  For example, "id = 1" or "name = 'John'".
     *
     * <p><strong>Example:</strong></p>
     * <pre>{@code
     * // Example: Delete rows from the "users" table where the id equals 1
     * deleteRow("users", "id = 1");
     * }</pre>
     * <p>This example deletes the row in the "users" table where the "id" column equals 1.</p>
     *
     * @throws SQLException if there is an issue executing the SQL query. Errors will be logged to the console.
     */
    public void deleteRow(String tableName, String condition) {
        String sql = "DELETE FROM " + tableName + " WHERE " + condition;
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            int rowsAffected = preparedStatement.executeUpdate();
            System.out.println(rowsAffected + " row(s) deleted.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Updates a specific column in a table for rows that satisfy the given condition.
     *
     * @param tableName       the name of the table to update.
     * @param column          the column to be updated.
     * @param newValue        the new value to set for the specified column.
     * @param columnCondition the column used in the WHERE clause to identify rows to update.
     * @param condition       the value to match in the columnCondition to determine which rows to update.
     *
     * <p><strong>Example:</strong></p>
     * <pre>{@code
     * // Update the "name" column to "John Doe" for rows where "id" equals 1
     * updateRow("users", "name", "John Doe", "id", 1);
     * }</pre>
     * <p>This example updates the "name" column to "John Doe" for the row in the "users" table where the "id" column equals 1.</p>
     */
    public void updateRow(String tableName, String column, Object newValue, String columnCondition, Object condition) {
        String sql = "UPDATE " + tableName + " SET " + column + " = ? WHERE " + columnCondition + " = ?";
        executeUpdate(sql, stmt -> {
            stmt.setObject(1, newValue);
            stmt.setObject(2, condition);
        });
    }

    /**
     * Returns the current date (today) in the system's default time zone.
     *
     * @return the current date as a {@link LocalDate} object representing today.
     *
     * <p><strong>Example:</strong></p>
     * <pre>{@code
     * LocalDate today = getToday();
     * System.out.println("Today's date: " + today);
     * }</pre>
     * <p>This example will print today's date in the default format (e.g., YYYY-MM-DD).</p>
     */
    public LocalDate getToday() {
        return LocalDate.now();
    }

    /**
     * close access to database
     */
    public void close() {
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

package librarymanagement.data;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SQLiteInstance {
    private static final String CONNECTION_URL = "jdbc:sqlite:library.db";
    private static SQLiteInstance instance = null;
    protected Connection connection;

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
     * function to add row to table in SQLite
     * @param tableName table to insert
     * @param values table properties
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
     * function to add row to table in SQLite
     * @param tableName table to insert
     * @param values table properties
     */
    public void insertRow(String tableName, Object... values) {
        insertRow(tableName, Arrays.asList(values));
    }

    /**
     * findNotCondition function in SQLite
     * @param tableName table to look up
     * @param columnCheckCondition column to compare
     * @param condition condition compare with columnCheckCondition
     * @param columns columns return value columns
     * @return list of results after query
     */
    public List<List<Object>> find(String tableName, String columnCheckCondition, String condition, String... columns) {
        List<List<Object>> values = new ArrayList<>();
        List<String> columnsList = Arrays.asList(columns);

        if (columnsList.isEmpty()) {
            throw new IllegalArgumentException("Columns array must not be empty");
        }

        StringBuilder sql = new StringBuilder("SELECT " + columnsList.get(0));
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
     * find function for query without condition
     * @param tableName table to look up
     * @param columns columns of result
     * @return list of results after query
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
     * find function for important query
     * @param sql query sql
     * @param params Value to fill in ? in the query. If there is no ?, leave the params array empty.
     * @param columns columns of result
     * @return list of results after query
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
     * function delete row on table in SQLite
     * @param tableName table to delete
     * @param condition condition of row delete
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
     * update row in database
     * @param tableName The table needs to be updated.
     * @param column The column needs to be updated.
     * @param newValue new value
     * @param columnCondition column to check condition
     * @param condition condition
     */
    public void updateRow(String tableName, String column, Object newValue, String columnCondition, Object condition) {
        String sql = "UPDATE " + tableName + " SET " + column + " = ? WHERE " + columnCondition + " = ?";
        executeUpdate(sql, stmt -> {
            stmt.setObject(1, newValue);
            stmt.setObject(2, condition);
        });
    }

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

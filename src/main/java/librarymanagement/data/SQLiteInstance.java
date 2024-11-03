package librarymanagement.data;

import java.awt.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SQLiteInstance {
    private static final String CONNECTION_URL = "jdbc:sqlite:library.db";
    private static SQLiteInstance instance = null;
    private Connection connection;

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
                    Object value = values.get(i);
                    switch (value) {
                        case String s -> preparedStatement.setString(i, s);
                        case Integer integer -> preparedStatement.setInt(i, integer);
                        case Double v -> preparedStatement.setDouble(i, v);
                        case Float v -> preparedStatement.setFloat(i, v);
                        case Boolean b -> preparedStatement.setBoolean(i, b);
                        case Long l -> preparedStatement.setLong(i, l);
                        case Date date -> preparedStatement.setDate(i, date);
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


    public List<Integer> findInt(String tableName, String columnName, String value) {
        List<Integer> result = new ArrayList<>();
        String sql = "SELECT * FROM " + tableName + " WHERE " + columnName + " GLOB ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, value);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    result.add(resultSet.getInt(1));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    public List<String> findString(String tableName, String columnName, String value) {
        List<String> result = new ArrayList<>();
        String sql = "SELECT * FROM " + tableName + " WHERE " + columnName + " GLOB ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, value);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    result.add(resultSet.getString(1));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
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

    public void deleteRow(String tableName, String condition) {
        String sql = "DELETE FROM " + tableName + " WHERE " + condition;
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            int rowsAffected = preparedStatement.executeUpdate();
            System.out.println(rowsAffected + " row(s) deleted.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public void insertRow(String tableName, String... values) {
            insertRow(tableName, Arrays.asList(values));
    }

    public void close() {
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
    }
}

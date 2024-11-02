package librarymanagement.data;

import java.sql.*;
import java.util.Arrays;
import java.util.List;

public class SQLiteInstance {
    private static final String CONNECTION_URL = "jdbc:sqlite:database.db";
    private static SQLiteInstance instance = null;
    private Connection connection;

    private SQLiteInstance() {
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

    public void insertRow(String tableName, List<String> values) {
        try {
            StringBuilder insertSQL = new StringBuilder("INSERT INTO " + tableName + " VALUES (");
            for (int i = 0; i < values.size(); i++) {
                insertSQL.append("?");
                if (i < values.size() - 1) {
                    insertSQL.append(",");
                }
            }
            insertSQL.append(")");

            try (PreparedStatement preparedStatement = connection.prepareStatement(insertSQL.toString())) {
                for (int i = 0; i < values.size(); i++) {
                    preparedStatement.setString(i + 1, values.get(i));
                }

                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

//    public List<Integer> find(String tableName, String columnName, String value) {
//        List<Integer> result = new ArrayList<>();
//        try {
//            String sql = "SELECT * FROM " + tableName + " WHERE "
//                    + columnName + " GLOB ? ORDER BY absolute_path ASC";
//            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
//                preparedStatement.setString(1, value);
//                try (ResultSet resultSet = preparedStatement.executeQuery()) {
//                    while (resultSet.next()) {
//                        result.add(resultSet.getInt("hash_code"));
//                    }
//                }
//            }
//
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//
//        return result;
//    }

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
}

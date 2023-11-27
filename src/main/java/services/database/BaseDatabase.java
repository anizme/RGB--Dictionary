package services.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * TODO: change PRJ_PATH to run
 * Anizme: C:\Users\hiren\Documents\UET subjects\OOP\INT2204-23_OOP\Dictionary-RGB\
 * chuquangcan: C:\Users\ADMIN\IdeaProjects\Clone4\
 * HAnguyen-119: D:\HA\RGB-_-Dictionary\
 */

public abstract class BaseDatabase {
    private static final String DB_PATH = "src\\\\main\\\\resources\\\\data\\\\dict_hh.db";
    private static final String PRJ_PATH = "D:\\HA\\RGB-_-Dictionary\\";

    //private static final String PRJ_PATH = prj_path.replace("\\", "\\\\")
    private static final String SQL_URL = "jdbc:sqlite:" + PRJ_PATH + DB_PATH;

    protected static Connection connection;

    public BaseDatabase() {
        try {
            Class.forName("org.sqlite.JDBC");
            if (connection == null) {
                connection = DriverManager.getConnection(SQL_URL);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void close() throws SQLException {
        if (connection != null) {
            connection.close();
        }
    }

    protected void executeUpdateWithTransaction(PreparedStatement preparedStatement) throws SQLException {
        connection.setAutoCommit(false);
        try {
            preparedStatement.executeUpdate();
            connection.commit();
        } catch (SQLException e) {
            connection.rollback();
            throw e;
        } finally {
            connection.setAutoCommit(true);
        }
    }

    protected void handleSQLException(SQLException e) {
        try {
            connection.rollback();
        } catch (SQLException rollbackException) {
            rollbackException.printStackTrace();  // Handle rollback exception
        }
        throw new RuntimeException(e);
    }
}

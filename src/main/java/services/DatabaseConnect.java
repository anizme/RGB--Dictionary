package services;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

//TODO: change PRJ_PATH to run
public class DatabaseConnect {
    private static final String DB_PATH = "src\\\\main\\\\resources\\\\data\\\\dict_hh.db";
    public static Connection connection = null;
    private static String prj_path = "C:\\Users\\hiren\\Documents\\UET subjects\\OOP\\INT2204-23_OOP\\Dictionary-RGB\\";
    private static final String PRJ_PATH = prj_path.replace("\\", "\\\\");
    private static final String SQL_URL = "jdbc:sqlite:" + PRJ_PATH + DB_PATH;

    public static void tryConnect() throws SQLException {
        try {
            Class.forName("org.sqlite.JDBC");
        } catch (Exception e) {
            e.printStackTrace();
        }
        connection = DriverManager.getConnection(SQL_URL);
    }

    public static void close() throws SQLException {
        if (connection != null) {
            connection.close();
        }
    }

//    public static void CreatableMyStar() throws SQLException {
//        try {
//            Class.forName("org.sqlite.JDBC");
//        } catch (Exception e) {
//            // TODO: handle exception
//            e.printStackTrace();
//        }
//        try (Connection connection1 = DriverManager
//                .getConnection("jdbc:sqlite:D:\\Code_vsc\\BTLOOP\\DemoSQL\\src\\mystar.db");) {
//
//            if (connection1 != null) {
//                System.out.println("Connected");
//                System.out.println(connection1);
//            }
//            String quer = "";
//            quer += "CREATE TABLE mystarword (id INT PRIMARY_KEY,word VARCHAR(60),description VARCHAR(80),pronounce VARCHAR(80))";
//            java.sql.Statement stmt = connection1.createStatement();
//            stmt.executeUpdate(quer);
//
//        } catch (Exception e) {
//            e.printStackTrace();
//            // TODO: handle exception
//        }
//
//    }

    public static void insertWord(String def, String meaning) throws SQLException {
        if (connection == null) {
            tryConnect();
        }
        String query = String.format("INSERT INTO av (word, html) VALUES ('%s', '%s')", def, meaning);
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        try (preparedStatement) {
            connection.setAutoCommit(false);
            preparedStatement.executeUpdate();
            connection.commit();
        } catch (SQLException e) {
            connection.rollback();
            throw new RuntimeException(e);
        } finally {
            connection.setAutoCommit(true);
        }
    }

    public static void deleteWord(String word) throws SQLException {
        if (connection == null) {
            tryConnect();
        }
        String query = String.format("DELETE FROM av WHERE word = '%s'", word);
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        try (preparedStatement) {
            connection.setAutoCommit(false);
            preparedStatement.executeUpdate();
            connection.commit();
        } catch (SQLException e) {
            connection.rollback();
            throw new RuntimeException(e);
        } finally {
            connection.setAutoCommit(true);
        }
    }


    public static String getMeaning(String word) throws SQLException {
        String ans = "";
        if (connection == null) {
            tryConnect();
        }
        String query = String.format("SELECT * FROM av WHERE word LIKE '%s'", word);
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            ans = resultSet.getString(3);
        }
        return ans;
    }

    public static List<String> getListWordTargets(String word) throws SQLException {
        String query = String.format("SELECT word FROM av WHERE word LIKE '%s%%' ORDER BY word", word);
        List<String> listWord = new ArrayList<>();
        if (connection == null) {
            tryConnect();
        }
        System.out.println(query);
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            listWord.add(resultSet.getString(1));
        }
        return listWord;
    }

    public static void updateWord(String def, String mean) throws SQLException {
        String query = String.format("UPDATE av SET html = '%s' WHERE word = '%s'", mean, def);
        if (connection == null) {
            tryConnect();
        }
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        try (preparedStatement) {
            connection.setAutoCommit(false);
            preparedStatement.executeUpdate();
            connection.commit();
        } catch (SQLException e) {
            connection.rollback();
            throw new RuntimeException(e);
        } finally {
            connection.setAutoCommit(true);
        }

    }

    public static void main(String[] args) throws SQLException {
        Scanner sc = new Scanner(System.in);
        String tmp = sc.nextLine();
        System.out.println(getMeaning("hello"));


        // Close the database connection when you're done with it
        if (connection != null) {
            connection.close();
        }

        sc.close();
    }
}
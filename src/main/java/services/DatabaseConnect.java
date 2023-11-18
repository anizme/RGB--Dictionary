package services;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * TODO: change PRJ_PATH to run
 * Anizme: C:\Users\hiren\Documents\UET subjects\OOP\INT2204-23_OOP\Dictionary-RGB\
 * chuquangcan: C:\Users\ADMIN\IdeaProjects\Clone4\
 * HAnguyen-119:
 */

public class DatabaseConnect {
    private static final String DB_PATH = "src\\\\main\\\\resources\\\\data\\\\dict_hh.db";
    public static Connection connection = null;
    private static final String prj_path = "C:\\Users\\hiren\\Documents\\UET subjects\\OOP\\INT2204-23_OOP\\Dictionary-RGB\\";
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

    public static void insertHistory(String historyWord) throws SQLException {
        if (connection == null) {
            tryConnect();
        }
//        String query = "CREATE TABLE IF NOT EXISTS history (word VARCHAR(50))";
//        PreparedStatement preparedStatement = connection.prepareStatement(query);
//        try (preparedStatement) {
//            connection.setAutoCommit(false);
//            preparedStatement.executeUpdate();
//            connection.commit();
//        } catch (SQLException e) {
//            connection.rollback();
//            throw new RuntimeException(e);
//        } finally {
//            connection.setAutoCommit(true);
//        }
        String query = String.format("INSERT INTO history (word) VALUES ('%s')", historyWord);
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

    public static void insertFavorite(String word) throws SQLException {
        if (connection == null) {
            tryConnect();
        }
//        String query = "CREATE TABLE IF NOT EXISTS favorite (stt INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, word VARCHAR(70), shortmeaning VARCHAR(70))";
//        PreparedStatement preparedStatement = connection.prepareStatement(query);
//        try (preparedStatement) {
//            connection.setAutoCommit(false);
//            preparedStatement.executeUpdate();
//            connection.commit();
//        } catch (SQLException e) {
//            connection.rollback();
//            throw new RuntimeException(e);
//        } finally {
//            connection.setAutoCommit(true);
//        }

        String query = "INSERT INTO favorite (word, shortmeaning) VALUES (?, ?)";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        try (preparedStatement) {
            connection.setAutoCommit(false);
            preparedStatement.setString(1, word);
            preparedStatement.setString(2, getShortMeaning(word));
            preparedStatement.executeUpdate();
            connection.commit();
        } catch (SQLException e) {
            connection.rollback();
            throw new RuntimeException(e);
        } finally {
            connection.setAutoCommit(true);
        }
        String query1 = String.format("UPDATE favorite SET stt = stt - 1 WHERE stt > %d;", getFavorite().size());
        PreparedStatement preparedStatement1 = connection.prepareStatement(query1);
        preparedStatement1.executeUpdate();
    }

    public static List<String> getFavoriteWord(int stt) throws SQLException {
        List<String> res = new ArrayList<>();
        String ans = "";
        if (connection == null) {
            tryConnect();
        }
        String query = String.format("SELECT word, shortmeaning FROM favorite WHERE stt = %d", stt);
        System.out.println(query);
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            res.add(resultSet.getString(1));
            res.add(resultSet.getString(2));
        }
        return res;
    }

    public static List<String> getFavoriteWordShortMeaning() throws SQLException {
        List<String> res = new ArrayList<>();
        if (connection == null) {
            tryConnect();
        }
        String query = "SELECT word, shortmeaning FROM favorite";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        ResultSet resultSet1 = preparedStatement.executeQuery();
        while (resultSet1.next()) {
            res.add(resultSet1.getString(1) + '\n' + resultSet1.getString(2));
        }
        return res;
    }

    public static String getFavoriteWordByWord(String word) throws SQLException {
        String ans = null;
        if (connection == null) {
            tryConnect();
        }
        String query = String.format("SELECT word FROM favorite WHERE word = '%s'", word);
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            ans = resultSet.getString(1);
        }
        return ans;
    }

    public static List<String> getFavorite() throws SQLException {
        List<String> ans = new ArrayList<>();
        if (connection == null) {
            tryConnect();
        }
        String query = ("SELECT word FROM favorite");
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            ans.add(resultSet.getString(1));
        }
        return ans;
    }

    public static void clearFavoriteWord(String word) throws SQLException {
        if (connection == null) {
            tryConnect();
        }
        int rowDelete = 0;
        String query = String.format("SELECT stt FROM favorite WHERE word LIKE '%s'", word);
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        ResultSet resultSet = preparedStatement.executeQuery();
        if (resultSet.next()) {
            rowDelete = resultSet.getInt("stt");
        }

        query = String.format("DELETE FROM favorite WHERE word LIKE '%s'", word);
        preparedStatement = connection.prepareStatement(query);
        int rowsUpdated = preparedStatement.executeUpdate();
        System.out.println(rowDelete);
        query = String.format("UPDATE favorite SET stt = stt - 1 WHERE stt > %d;", rowDelete);
        preparedStatement = connection.prepareStatement(query);
        preparedStatement.executeUpdate();
    }

    public static void clearFavorite() throws SQLException {
        if (connection == null) {
            tryConnect();
        }
        String query = "DELETE FROM favorite;\n" +
                "VACUUM;";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        int rowsUpdated = preparedStatement.executeUpdate();
    }

    public static List<String> getHistory() throws SQLException {
        List<String> ans = new ArrayList<>();
        if (connection == null) {
            tryConnect();
        }
        String query = "SELECT word FROM history";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            ans.add(resultSet.getString(1));
        }
        return ans;
    }

    public static void clearHistoryWord(String word) throws SQLException {
        if (connection == null) {
            tryConnect();
        }
        String query = String.format("DELETE FROM history WHERE word LIKE '%s'", word);
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        int rowsUpdated = preparedStatement.executeUpdate();
    }

    public static void clearHistory() throws SQLException {
        if (connection == null) {
            tryConnect();
        }
        String query = "DELETE FROM history;\n" +
                "VACUUM;";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        int rowsUpdated = preparedStatement.executeUpdate();
    }

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

    public static String getShortMeaning(String src) throws SQLException {
        String ans = "";
        if (connection == null) {
            tryConnect();
        }
        String query = String.format("SELECT * FROM av WHERE word LIKE '%s'", src);
        System.out.println(query);
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            ans = ("/" + resultSet.getString(5) + "/\n" + resultSet.getString(4));
        }
        return ans;
    }

    public static String getShortPro(String src) throws SQLException {
        String ans = "";
        if (connection == null) {
            tryConnect();
        }
        String query = String.format("SELECT * FROM av WHERE word LIKE '%s'", src);
        System.out.println(query);
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            ans = resultSet.getString(5);
        }
        return ans;
    }

    public static String getWordByRegex(String pattern, String src) {
        Pattern p = Pattern.compile(pattern);
        Matcher m = p.matcher(src);
        m.find();
        return m.group(2);
    }

    public static void main(String[] args) throws SQLException {
        insertFavorite("H");
        // Close the database connection when you're done with it
        if (connection != null) {
            connection.close();
        }
    }
}
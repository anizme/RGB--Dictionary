package services;

import dictionary.Word;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

//TODO: change PRJ_PATH to run
public class DatabaseConnect extends DatabaseDictionary {
    private static final String DB_PATH = "src\\\\main\\\\resources\\\\data\\\\dict_hh.db";
    public static Connection connection = null;
    private static String prj_path = "C:\\Users\\hiren\\Documents\\UET subjects\\OOP\\INT2204-23_OOP\\Dictionary-RGB\\";
    private static final String PRJ_PATH = prj_path.replace("\\", "\\\\");
    private static final String SQL_URL = "jdbc:sqlite:" + PRJ_PATH + DB_PATH;

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

//    public static void insertWord(tmp a) {
//        int id = a.id;
//        String word = a.word;
//        String des = a.des;
//        String pro = a.pro;
//        try {
//            Class.forName("org.sqlite.JDBC");
//        } catch (Exception e) {
//            // TODO: handle exception
//            e.printStackTrace();
//        }
//        try (Connection connection1 = DriverManager.getConnection(
//                "jdbc:sqlite:C:\\Users\\hiren\\Downloads\\dict_hh.db");) {
//
//            if (connection1 != null) {
//                System.out.println("Connected from insertinto");
//                System.out.println(connection1);
//            }
//            String quer = String.format("INSERT INTO mystarword VALUES (%d,'%s','%s',\"%s\")", id, word, des, pro);
//            System.out.println(quer);
//            java.sql.Statement stmt = connection1.createStatement();
//            stmt.executeUpdate(quer);
//        } catch (Exception e) {
//            e.printStackTrace();
//            // TODO: handle exception
//        }
//
//    }

    public static void tryConnect() throws SQLException {
        try {
            Class.forName("org.sqlite.JDBC");
        } catch (Exception e) {
            e.printStackTrace();
        }
        connection = DriverManager.getConnection(SQL_URL);
    }

    public static String getMeaning(String word) throws SQLException {
        String ans = "";
        if (connection == null) {
            tryConnect();
        }
        String query = String.format("SELECT * FROM av WHERE word LIKE '%s'", word);
        System.out.println(query);
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
        PreparedStatement preparedStatement;
        System.out.println("connection state: " + connection);
        preparedStatement = connection.prepareStatement(query);
        System.out.println("prepare state: " + preparedStatement);
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            listWord.add(resultSet.getString(1));
        }
        return listWord;
    }

    public static void insertNewWord(String def, String mean) {
        String query = String.format("UPDATE av SET WHERE word = %s", def);
    }

    public static void main(String[] args) throws SQLException {
        Scanner sc = new Scanner(System.in);
        String tmp = sc.nextLine();
        List<String> l = getListWordTargets(tmp);
        for (String str : l) {
            System.out.println(str);
        }


        // Close the database connection when you're done with it
        if (connection != null) {
            connection.close();
        }

        sc.close();
    }

    @Override
    public void close() throws SQLException {
        if (connection != null) {
            connection.close();
        }
    }

    @Override
    public ArrayList<Word> getAllWords() {
        return null;
    }

    @Override
    public List<String> getAllWordTargets(String src) throws SQLException {
        return getListWordTargets(src);
    }

    @Override
    public String lookUpWord(String target) throws SQLException {
        return getMeaning(target);
    }

    @Override
    public boolean insertWord(String target, String definition) {
        return false;
    }

    @Override
    public boolean deleteWord(String target) {
        return false;
    }

    @Override
    public boolean updateWordDefinition(String target, String definition) {
        return false;
    }
}
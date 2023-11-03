package services;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class DatabaseConnect {

    static Connection connection = null;

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

    public static void tryConnect() {
        try {
            Class.forName("org.sqlite.JDBC");
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
        //TODO: change url link into this template: "jdbc:sqlite:...\src\main\resources\data\dict_hh.db"
        try (Connection tmpConnection = DriverManager.getConnection(
                "jdbc:sqlite:C:\\Users\\ADMIN\\IdeaProjects\\Clone4\\src\\main\\resources\\data\\dict_hh.db")) {
            if (tmpConnection != null) {
                System.out.println("Connected: " + tmpConnection);
                connection = tmpConnection;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void GetWordFromData() {
        try {
            Class.forName("org.sqlite.JDBC");
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
        //TODO: change url link into this template: "jdbc:sqlite:...\src\main\resources\data\dict_hh.db"
        try (Connection connection1 = DriverManager.getConnection(
                "jdbc:sqlite:C:\\Users\\hiren\\Documents\\UET subjects\\OOP\\INT2204-23_OOP\\Dictionary-RGB\\src\\main\\resources\\data\\dict_hh.db")) {
            if (connection1 != null) {
                System.out.println("Connected");
                System.out.println(connection1);
                connection = connection1;
            }
            Scanner scan = new Scanner(System.in);
            String tmp = scan.nextLine();
            // tmp += '%';
            String querry = String.format("SELECT * FROM av WHERE word LIKE '%s'", tmp);
            System.out.println(querry);
            PreparedStatement preparedStatement;
            System.out.println("connection state: " + connection);
            preparedStatement = connection.prepareStatement(querry);
            System.out.println("prepare state: " + preparedStatement);
            ResultSet resultSet = preparedStatement.executeQuery();
            int i = 0;
            while (resultSet.next() && i < 10) {
                int id = resultSet.getInt(1);
                String pro = resultSet.getString(5);
                System.out.println(id + "  " + pro);
                i++;
            }
        } catch (Exception e) {
            e.printStackTrace();
            // TODO: handle exception
        }

    }

    public static String getMeaning(String word) throws SQLException {
        String ans = "";
        if (connection == null) {
            tryConnect();
        }
        String query = String.format("SELECT * FROM av WHERE word LIKE '%s'", word);
        System.out.println(query);
        PreparedStatement preparedStatement;
        preparedStatement = connection.prepareStatement(query);
        ResultSet resultSet = preparedStatement.executeQuery();
        int i = 0;
        while (resultSet.next() && i < 10) {
            ans = resultSet.getString(3);
            i++;
        }
        return ans;
    }

    public static List<String> getWord(String query) throws SQLException {
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

    public static void main(String[] args) throws SQLException {
        Scanner sc = new Scanner(System.in);
        String tmp = sc.nextLine();
        String query = String.format("SELECT word FROM av WHERE word LIKE '%s%%' ORDER BY word", tmp);
        System.out.println("MAIN: " + getWord(query));
        sc.close();
    }
}
package services;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Scanner;

class tmp {
    public int id;
    public String word;
    public String des;
    public String pro;

    tmp() {
        id = 0;
        word = "";
        des = "";
        pro = "";
    }

    tmp(int i, String wo, String ds, String pr) {
        id = i;
        word = wo;
        des = ds;
        pro = pr;
    }
}

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

    public static void GetWordFromData() throws Exception {
        try {
            Class.forName("org.sqlite.JDBC");
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
        try (Connection connection1 = DriverManager.getConnection(
                "jdbc:sqlite:D:\\HA\\RGB-_-Dictionary\\src\\main\\resources\\data\\dict_hh.db");) {
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
            //List<tmp> entities = new ArrayList<tmp>();
            while (resultSet.next() == true && i < 10) {
                int id = resultSet.getInt(1);
                //String word = resultSet.getString(2);
                //String html = resultSet.getString(3);
                String pro = resultSet.getString(5);
                System.out.println(id + "  " + pro);
                i++;
                //tmp a = new tmp(id, word, html, pro);
                //entities.add(a);
            }
        } catch (Exception e) {
            e.printStackTrace();
            // TODO: handle exception
        }

    }

    public static String getMeaning(String word) {
        String ans = "";
        try {
            Class.forName("org.sqlite.JDBC");
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
        try (Connection connection1 = DriverManager.getConnection(
                "jdbc:sqlite:D:\\HA\\RGB-_-Dictionary\\src\\main\\resources\\data\\dict_hh.db");) {
            if (connection1 != null) {
                System.out.println("Connected");
                System.out.println(connection1);
                connection = connection1;
            }
            String querry = String.format("SELECT * FROM av WHERE word LIKE '%s'", word);
            System.out.println(querry);
            PreparedStatement preparedStatement;
            preparedStatement = connection.prepareStatement(querry);
            ResultSet resultSet = preparedStatement.executeQuery();
            int i = 0;
            while (resultSet.next() == true && i < 10) {
                String html = resultSet.getString(3);
                ans = html;
            }
        } catch (Exception e) {
            e.printStackTrace();
            // TODO: handle exception
        }
        return ans;
    }


    public static void main(String[] args) throws Exception {
        Scanner sc = new Scanner(System.in);
        String word = sc.nextLine();
        System.out.println("MAIN: " + getMeaning(word));
        sc.close();
    }
}
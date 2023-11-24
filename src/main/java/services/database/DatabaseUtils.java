package services.database;

import java.sql.SQLException;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static services.database.DatabaseConnect.getListFavoriteWordTargets;

public class DatabaseUtils {
    public static String getWordByRegex(String pattern, String src) {
        Pattern p = Pattern.compile(pattern);
        Matcher m = p.matcher(src);
        if (!m.find()) {
            return null;
        }
        return m.group(2);
    }

    public static void main(String[] args) throws SQLException {

    }
}

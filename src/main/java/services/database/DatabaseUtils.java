package services.database;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class DatabaseUtils {
    public static String getWordByRegex(String pattern, String src) {
        Pattern p = Pattern.compile(pattern);
        Matcher m = p.matcher(src);
        if (!m.find()) {
            return null;
        }
        return m.group(2);
    }

}

package controller.panes.favoriteServices;

import java.sql.SQLException;

import static controller.ApplicationStart.favoriteDB;

public class FavoriteUtils {
    /**
     * use for study mode and flash card feature in favorite pane
     * @param stt order in table (stt column)
     * @return word in String
     */
    public static String getFavoriteWordAt(int stt) throws SQLException {
        return favoriteDB.getFullCardProperties(favoriteDB.getFavoriteWord().get(stt)).get(0);
    }

    /**
     * use for study mode and flash car feature in favorite pane
     * @param stt order in table (stt column)
     * @return short meaning in String
     */
    public static String getFavoriteShortMeaningAt(int stt) throws SQLException {
        return favoriteDB.getFullCardProperties(favoriteDB.getFavoriteWord().get(stt)).get(1);
    }

    /**
     * @param property represents for word / short meaning
     * @param stt order in table (stt column)
     * @return word / short meaning in String
     */
    public static String getFavoriteSpecificPropertyAt(int property, int stt) throws SQLException {
        if (property == 0) {
            return getFavoriteWordAt(stt);
        }
        return getFavoriteShortMeaningAt(stt);
    }
}

package controller.panes.favoriteServices;

import java.sql.SQLException;

import static controller.ApplicationStart.favoriteDB;

public class FavoriteUtils {
    /**
     * use for study mode and flash card feature in favorite pane
     * @param stt
     * @return
     * @throws SQLException
     */
    public static String getFavoriteWordAt(int stt) throws SQLException {
        return favoriteDB.getFullCardProperties(favoriteDB.getFavoriteWord().get(stt)).get(0);
    }

    /**
     * use for study mode and flash car feature in favorite pane
     * @param stt
     * @return
     * @throws SQLException
     */
    public static String getFavoriteShortMeaningAt(int stt) throws SQLException {
        return favoriteDB.getFullCardProperties(favoriteDB.getFavoriteWord().get(stt)).get(1);
    }

    public static String getFavoriteSpecificPropertyAt(int property, int stt) throws SQLException {
        if (property == 0) {
            return getFavoriteWordAt(stt);
        }
        return getFavoriteShortMeaningAt(stt);
    }
}

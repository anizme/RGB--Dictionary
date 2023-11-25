package services.database;

import java.sql.SQLException;
import java.util.List;

public abstract class FavoriteDatabase extends BaseDatabase {
    /**
     * insert new word into favorite table
     * @param word
     * @throws SQLException
     */
    public abstract void insertFavorite(String word) throws SQLException;

    /**
     * returns all words (not include short meaning) in favorite table
     * @return
     * @throws SQLException
     */
    public abstract List<String> getFavoriteWord() throws SQLException;

    /**
     * use for search feature in favorite pane
     * returns all records in template in favorite table start with word
     * @param word
     * @return
     * @throws SQLException
     */
    public abstract List<String> getFavoriteTargets(String word) throws SQLException;

    /**
     * returns all records in template in favorite table
     * template:
     * word
     * short meaning
     * @return
     * @throws SQLException
     */
    public abstract List<String> getFavorite() throws SQLException;

    /**
     * use for flash card and study mode in favorite pane
     * returns list with 2 element:
     * 0: word
     * 1: shortmeaning
     * @param word
     * @return
     * @throws SQLException
     */
    public abstract List<String> getFullCardProperties(String word) throws SQLException;

    /**
     * check if a word is in favorite table
     * @param word
     * @return
     * @throws SQLException
     */
    public abstract boolean isWordInFavorite(String word) throws SQLException;

    /**
     * remove a word in favorite table
     * @param word
     * @throws SQLException
     */
    public abstract void removeFavoriteWord(String word) throws SQLException;

    /**
     * remove all words in favorite table
     * @throws SQLException
     */
    public abstract void clearFavorite() throws SQLException;
}

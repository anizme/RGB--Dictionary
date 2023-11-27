package services.database;

import javax.swing.plaf.metal.MetalBorders;
import java.sql.SQLException;
import java.util.List;

public abstract class DictionaryDatabase extends BaseDatabase {
    /**
     * @return all words in the dictionary
     * @throws SQLException error with SQLite
     */
    public abstract List<String> getAllWords() throws SQLException;

    /**
     * insert a new word into the dictionary
     * @param word
     * @param meaning
     * @throws SQLException
     */
    public abstract void insertWord(String word, String meaning) throws SQLException;

    /**
     * delete the word from dictionary (av table)
     * @param word
     * @throws SQLException
     */
    public abstract void deleteWord(String word) throws SQLException;

    /**
     * returns the specific meaning of a word
     * @param word
     * @return
     * @throws SQLException
     */
    public abstract String getMeaning(String word) throws SQLException;

    /**
     * Use for search feature, returns all words start with word-param
     * @param word
     * @return
     * @throws SQLException
     */
    public abstract List<String> getListWordTargets(String word) throws SQLException;

    /**
     * Use for search pane, update new meaning for word
     * @param word
     * @param newMeaning
     * @throws SQLException
     */
    public abstract void updateWord(String word, String newMeaning) throws SQLException;

    /**
     * Use for favorite pane, returns the short meaning of "word",
     * template:
     * /pronounce/
     * 1st meaning of the word
     * @param word
     * @return
     * @throws SQLException
     */
    public abstract String getShortMeaning(String word) throws SQLException;
}

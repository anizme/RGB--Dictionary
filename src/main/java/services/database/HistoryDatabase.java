package services.database;

import java.sql.SQLException;
import java.util.List;

public abstract class HistoryDatabase extends BaseDatabase {
    /**
     * add a new word (latest searched) into history table
     * @param word
     * @throws SQLException
     */
    public abstract void insertHistory(String word) throws SQLException;

    /**
     * get all words in the history table (to display in search list view)
     * @return
     * @throws SQLException
     */
    public abstract List<String> getHistory() throws SQLException;

    /**
     * remove a word in history table
     * @param word
     * @throws SQLException
     */
    public abstract void removeHistoryWord(String word) throws SQLException;

    /**
     * remove all words in history table
     * @throws SQLException
     */
    public abstract void clearHistory() throws SQLException;
}

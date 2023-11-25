package services.database;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class HistoryDB extends HistoryDatabase {
    private static final String INSERT_HISTORY_QUERY = "INSERT INTO history (word) VALUES (?)";
    private static final String SELECT_HISTORY_QUERY = "SELECT word FROM history";
    private static final String DELETE_HISTORY_WORD_QUERY = "DELETE FROM history WHERE word LIKE ?";
    private static final String CLEAR_HISTORY_QUERY = "DELETE FROM history; VACUUM;";

    @Override
    public void insertHistory(String word) {
        try (PreparedStatement preparedStatement = connection.prepareStatement(INSERT_HISTORY_QUERY)) {
            preparedStatement.setString(1, word);
            executeUpdateWithTransaction(preparedStatement);
        } catch (SQLException e) {
            handleSQLException(e);
        }
    }

    @Override
    public List<String> getHistory() {
        List<String> historyList = new ArrayList<>();
        try (PreparedStatement preparedStatement = connection.prepareStatement(SELECT_HISTORY_QUERY)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                historyList.add(resultSet.getString("word"));
            }
        } catch (SQLException e) {
            handleSQLException(e);
        }
        return historyList;
    }

    @Override
    public void removeHistoryWord(String word) {
        try (PreparedStatement preparedStatement = connection.prepareStatement(DELETE_HISTORY_WORD_QUERY)) {
            preparedStatement.setString(1, word);
            int rowsUpdated = preparedStatement.executeUpdate();
        } catch (SQLException e) {
            handleSQLException(e);
        }
    }

    @Override
    public void clearHistory() {
        try (PreparedStatement preparedStatement = connection.prepareStatement(CLEAR_HISTORY_QUERY)) {
            int rowsUpdated = preparedStatement.executeUpdate();
        } catch (SQLException e) {
            handleSQLException(e);
        }
    }
}

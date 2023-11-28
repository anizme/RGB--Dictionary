package services.database;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DictionaryDB extends DictionaryDatabase {
    private static final String INSERT_SHORT_MEANING_QUERY = "UPDATE av SET (description, pronounce) = (?, ?) WHERE word = ?";
    private static final String INSERT_WORD_QUERY = "INSERT INTO av (word, html) VALUES (?, ?)";
    private static final String DELETE_WORD_QUERY = "DELETE FROM av WHERE word = ?";
    private static final String SELECT_MEANING_QUERY = "SELECT html FROM av WHERE word LIKE ?";
    private static final String SELECT_LIST_WORD_QUERY = "SELECT word FROM av WHERE word LIKE ? ORDER BY word";
    private static final String UPDATE_WORD_QUERY = "UPDATE av SET html = ? WHERE word = ?";
    private static final String SELECT_SHORT_MEANING_QUERY = "SELECT * FROM av WHERE word LIKE ?";
    private static final String SELECT_ALL_WORD = "SELECT word FROM av";

    @Override
    public List<String> getAllWords() {
        List<String> listWord = new ArrayList<>();
        try (PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_WORD)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                listWord.add(resultSet.getString("word"));
            }
        } catch (SQLException e) {
            handleSQLException(e);
        }
        return listWord;
    }

    @Override
    public void insertWord(String word, String meaning) {
        try (PreparedStatement preparedStatement = connection.prepareStatement(INSERT_WORD_QUERY)) {
            preparedStatement.setString(1, word);
            preparedStatement.setString(2, meaning);
            executeUpdateWithTransaction(preparedStatement);
        } catch (SQLException e) {
            handleSQLException(e);
        }
    }

    @Override
    public void deleteWord(String word) {
        try (PreparedStatement preparedStatement = connection.prepareStatement(DELETE_WORD_QUERY)) {
            preparedStatement.setString(1, word);
            executeUpdateWithTransaction(preparedStatement);
        } catch (SQLException e) {
            handleSQLException(e);
        }
    }

    @Override
    public String getMeaning(String word) {
        try (PreparedStatement preparedStatement = connection.prepareStatement(SELECT_MEANING_QUERY)) {
            preparedStatement.setString(1, word);
            ResultSet resultSet = preparedStatement.executeQuery();
            return resultSet.next() ? resultSet.getString("html") : "";
        } catch (SQLException e) {
            handleSQLException(e);
            return "";
        }
    }

    @Override
    public List<String> getListWordTargets(String word) {
        List<String> listWord = new ArrayList<>();
        try (PreparedStatement preparedStatement = connection.prepareStatement(SELECT_LIST_WORD_QUERY)) {
            preparedStatement.setString(1, word + "%");
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                listWord.add(resultSet.getString("word"));
            }
        } catch (SQLException e) {
            handleSQLException(e);
        }
        return listWord;
    }

    @Override
    public void updateWord(String word, String newMeaning) {
        try (PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_WORD_QUERY)) {
            preparedStatement.setString(1, newMeaning);
            preparedStatement.setString(2, word);
            executeUpdateWithTransaction(preparedStatement);
        } catch (SQLException e) {
            handleSQLException(e);
        }
    }

    @Override
    public String getShortMeaning(String word) {
        try (PreparedStatement preparedStatement = connection.prepareStatement(SELECT_SHORT_MEANING_QUERY)) {
            preparedStatement.setString(1, word);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.getString(4) == null) {
                return null;
            }
            if (resultSet.next()) {
                return "/" + resultSet.getString(5) + "/\n" + resultSet.getString(4);
            }
        } catch (SQLException e) {
            handleSQLException(e);
        }
        return "";
    }

    @Override
    public void insertShortMeaning(String shortMeaning, String pro, String word) {
        try (PreparedStatement preparedStatement = connection.prepareStatement(INSERT_SHORT_MEANING_QUERY)) {
            preparedStatement.setString(1, shortMeaning);
            preparedStatement.setString(2, pro);
            preparedStatement.setString(3, word);
            executeUpdateWithTransaction(preparedStatement);
        } catch (SQLException e) {
            handleSQLException(e);
        }
    }

    public boolean isInDictionary(String word) {
        return !getMeaning(word).isEmpty();
    }
}

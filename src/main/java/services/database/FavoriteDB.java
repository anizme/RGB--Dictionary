package services.database;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static controller.ApplicationStart.dictionaryDB;

public class FavoriteDB extends FavoriteDatabase {
    private static final String INSERT_WORD_QUERY = "INSERT INTO favorite (word, shortmeaning) VALUES (?, ?)";
    private static final String UPDATE_ORDER_QUERY = "UPDATE favorite SET stt = stt - 1 WHERE stt > ?";
    private static final String DELETE_WORD_QUERY = "DELETE FROM favorite WHERE word LIKE ?";
    private static final String SELECT_WORD_QUERY = "SELECT word FROM favorite";
    private static final String SELECT_LIST_WORD_QUERY = "SELECT word, shortmeaning FROM favorite WHERE word LIKE ? ORDER BY word";
    private static final String SELECT_WORD_SHORT_MEANING_QUERY = "SELECT word, shortmeaning FROM favorite";
    private static final String SELECT_FULL_CARD_PROPERTIES = "SELECT word, shortmeaning FROM favorite WHERE word = ?";
    private static final String SELECT_WORD_TARGETS = "SELECT word FROM favorite WHERE word = ?";
    private static final String SELECT_STT_BY_WORD = "SELECT stt FROM favorite WHERE word LIKE ?";
    private static final String DELETE_ALL_RECORDS = "DELETE FROM favorite; VACUUM";

    @Override
    public void insertFavorite(String word) {
        try (PreparedStatement preparedStatement = connection.prepareStatement(INSERT_WORD_QUERY)) {
            connection.setAutoCommit(false);
            preparedStatement.setString(1, word);
            preparedStatement.setString(2, dictionaryDB.getShortMeaning(word));
            executeUpdateWithTransaction(preparedStatement);
        } catch (SQLException e) {
            handleSQLException(e);
        }
        //updateFavoriteOrder(getFavorite().size());
    }

    private void updateFavoriteOrder(int stt) {
        try (PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_ORDER_QUERY)) {
            preparedStatement.setInt(1, stt);
            executeUpdateWithTransaction(preparedStatement);
        } catch (SQLException e) {
            handleSQLException(e);
        }
    }

    @Override
    public List<String> getFavoriteWord() {
        List<String> favoriteWords = new ArrayList<>();
        try (PreparedStatement preparedStatement = connection.prepareStatement(SELECT_WORD_QUERY)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                favoriteWords.add(resultSet.getString(1));
            }
        } catch (SQLException e) {
            handleSQLException(e);
        }
        return favoriteWords;
    }

    @Override
    public List<String> getFavoriteTargets(String word) throws SQLException {
        List<String> listWords = new ArrayList<>();

        try (PreparedStatement preparedStatement = connection.prepareStatement(SELECT_LIST_WORD_QUERY)) {
            preparedStatement.setString(1, word + "%");
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                String tmpWord = resultSet.getString(1);
                String tmpShortmeaning = resultSet.getString(2);
                listWords.add(tmpWord + "\n" + tmpShortmeaning);
            }
        } catch (SQLException e) {
            handleSQLException(e);
        }
        return listWords;
    }

    @Override
    public List<String> getFavorite() throws SQLException {
        List<String> res = new ArrayList<>();
        try (PreparedStatement preparedStatement = connection.prepareStatement(SELECT_WORD_SHORT_MEANING_QUERY);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            while (resultSet.next()) {
                res.add(resultSet.getString("word") + "\n" + resultSet.getString("shortmeaning"));
            }
        }
        return res;
    }

    @Override
    public List<String> getFullCardProperties(String word) {
        List<String> res = new ArrayList<>();
        try (PreparedStatement preparedStatement = connection.prepareStatement(SELECT_FULL_CARD_PROPERTIES)) {
            preparedStatement.setString(1, word);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                res.add(resultSet.getString("word"));
                res.add(resultSet.getString("shortmeaning"));
            }
        } catch (SQLException e) {
            handleSQLException(e);
        }
        return res;
    }

    @Override
    public boolean isWordInFavorite(String word) throws SQLException {
        String result = null;
        try (PreparedStatement preparedStatement = connection.prepareStatement(SELECT_WORD_TARGETS)) {
            preparedStatement.setString(1, word);
            ResultSet resultSet = preparedStatement.executeQuery();
            result = resultSet.getString(1);
        } catch (SQLException e) {
            handleSQLException(e);
        }
        return result != null;
    }

    @Override
    public void removeFavoriteWord(String word) throws SQLException {
        int rowDelete = getSttByWord(word);
        try (PreparedStatement preparedStatement = connection.prepareStatement(DELETE_WORD_QUERY)) {
            preparedStatement.setString(1, word);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            handleSQLException(e);
        }
        updateFavoriteOrder(rowDelete);
    }

    private int getSttByWord(String word) {
        int rowDelete = 0;
        try (PreparedStatement preparedStatement = connection.prepareStatement(SELECT_STT_BY_WORD)) {
            preparedStatement.setString(1, word);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                rowDelete = resultSet.getInt("stt");
            }
        } catch (SQLException e) {
            handleSQLException(e);
        }
        return rowDelete;
    }

    @Override
    public void clearFavorite() {
        try (PreparedStatement preparedStatement = connection.prepareStatement(DELETE_ALL_RECORDS)) {
            executeUpdateWithTransaction(preparedStatement);
        } catch (SQLException e) {
            handleSQLException(e);
        }
    }
}

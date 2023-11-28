package controller.panes.favoriteServices;

import java.sql.SQLException;

public interface StudyMode {
    void showResult();
    void getNextQuestion() throws SQLException;
    void reStart();
}

package services;

import dictionary.Word;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public abstract class DatabaseDictionary {

    public void initialize() throws SQLException {}

    public void close() throws SQLException {}

    public abstract ArrayList<Word> getAllWords();

    public abstract List<String> getAllWordTargets(String src) throws SQLException;

    public abstract String lookUpWord(final String target) throws SQLException;

    public abstract boolean insertWord(final String target, final String definition);

    public abstract boolean deleteWord(final String target);

    public abstract boolean updateWordDefinition(final String target, final String definition);

    public String exportAllWords() {
        ArrayList<Word> allWords = getAllWords();
        StringBuilder result = new StringBuilder();
        for (Word word : allWords) {
            result.append(word.getWord_target())
                    .append('\t')
                    .append(word.getWord_explain())
                    .append('\n');
        }
        return result.toString();
    }

    public void exportToFile(String exportPath) throws IOException {
        Writer out =
                new BufferedWriter(
                        new OutputStreamWriter(
                                new FileOutputStream(exportPath), StandardCharsets.UTF_8));
        String export = exportAllWords();
        out.write(export);
        out.close();
    }
}

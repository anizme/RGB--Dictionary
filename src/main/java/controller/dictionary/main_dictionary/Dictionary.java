package controller.dictionary.main_dictionary;

import java.util.ArrayList;
import java.util.List;

public class Dictionary {
    private final List<Word> listOfWords;

    public Dictionary() {
        listOfWords = new ArrayList<>();
    }

    public List<Word> getListOfWords() {
        return listOfWords;
    }
}
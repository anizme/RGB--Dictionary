package controller.algorithm;

import controller.dictionary.main_dictionary.*;

import java.util.List;

public class Search {
    /* Returns the current position in dictionary of target word. */
    public static int findPositionInWordListByString(String word_target, List<Word> listOfWords) {
        for (int i = 0; i < listOfWords.size(); i++) {
            if (listOfWords.get(i).getWord_target().trim().equalsIgnoreCase(word_target.toLowerCase())) {
                return i;
            }
        }
        return -1;
    }

    /* Returns the current position in dictionary of the first word started with a given string. */
    public static int findPositionOfFirstWordStartedWithAGivenString(String searchWord, List<Word> listOfWords) {
        int n = searchWord.length();
        for (int i = 0; i < listOfWords.size(); i++) {
            if (listOfWords.get(i).getWord_target().length() >= n
                    && listOfWords.get(i).getWord_target().toLowerCase().substring(0, n).equals(searchWord.toLowerCase())) {
                return i;
            }
        }
        return -1;
    }
}
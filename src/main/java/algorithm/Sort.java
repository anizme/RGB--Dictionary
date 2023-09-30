package algorithm;

import controller.main_dictionary.Word;

import java.util.Comparator;
import java.util.List;
public class Sort {
    public static void sortDictionaryInAlphabeticalOrder(List<Word> listOfWords) {
        listOfWords.sort(new Comparator<Word>() {
            @Override
            public int compare(Word w1, Word w2) {
                return w1.getWord_target().toLowerCase().compareTo(w2.getWord_target().toLowerCase());
            }
        });
    }
}

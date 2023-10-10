package algorithms;

import dictionary.Word;

import java.util.List;

public class Search {
    /* Returns the position in dictionary of target word. */
    public static int findPositionInWordListByString(String word_target, List<Word> listOfWords) {
        return binarySearchPosInWordList(word_target, listOfWords, 0, listOfWords.size() - 1);
    }

    /* Optimize for function findPositionInWordListByString(). */
    public static int binarySearchPosInWordList(String word_target, List<Word> listOfWords, int start, int end) {
        if (start <= end) {
            int mid = start + (end - start) / 2;
            int cmp = listOfWords.get(mid).getWord_target().compareToIgnoreCase(word_target);
            if (cmp < 0) {
                return binarySearchPosInWordList(word_target, listOfWords, mid + 1, end);
            } else if (cmp > 0) {
                return binarySearchPosInWordList(word_target, listOfWords, start, mid - 1);
            } else {
                return mid;
            }
        } else {
            return -1;
        }
    }

    /* Returns the current position in dictionary of the first word started with a given string. */
    public static int findPosOfFirstContainedWord(String searchWord, List<Word> listOfWords) {
        int n = searchWord.length();
        for (int i = 0; i < listOfWords.size(); i++) {
            if (listOfWords.get(i).getWord_target().length() >= n
                    && listOfWords.get(i).getWord_target().toLowerCase().substring(0, n).equals(searchWord.toLowerCase())) {
                return i;
            }
        }
        return binarySearchPosContainedInWordList(searchWord, listOfWords, 0, listOfWords.size() - 1);
    }

    /* Optimize for function findPosOfFirstContainedWord(). */
    public static int binarySearchPosContainedInWordList(String word_target, List<Word> listOfWords,
                                                         int start, int end) {
        if (start > end) {
            return -1;
        }
        int mid = start + (end - start) / 2;
        int cmp = listOfWords.get(mid).getWord_target().compareToIgnoreCase(word_target);
        boolean isContain = isFirstContain(listOfWords.get(mid).getWord_target(), word_target);
        if (!isContain) {
            if (cmp < 0) {
                return binarySearchPosContainedInWordList(word_target, listOfWords, mid + 1, end);
            } else {
                return binarySearchPosContainedInWordList(word_target, listOfWords, start, mid - 1);
            }
        } else {
            if (mid - 1 >= 0 && listOfWords.get(mid - 1).getWord_target().contains(word_target)) {
                return binarySearchPosContainedInWordList(word_target, listOfWords, start, mid - 1);
            } else {
                return mid;
            }
        }
    }

    /* if string source has string target at beginning. */
    public static boolean isFirstContain(String source, String target) {
        int n = target.length();
        return source.substring(0, n).toLowerCase().equals(target.toLowerCase());
    }
}
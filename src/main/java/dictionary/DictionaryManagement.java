package dictionary;

import algorithms.Sort;
import algorithms.Search;
import controller.Alert.ConfirmationAlert;
import controller.Alert.DetailAlert;
import javafx.scene.control.Alert;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class DictionaryManagement {
    private static final String dictionaries_txt = "src/main/resources/data/";
    private Dictionary dictionary;

    public DictionaryManagement() {
        dictionary = new Dictionary();
    }

    public Dictionary getDictionary() {
        return this.dictionary;
    }

    /* Get data for dictionary by command line. */
    public void insertFromCommandline() {
        Scanner sc = new Scanner(System.in);
        int quantity = sc.nextInt();
        sc.nextLine();
        for (int i = 0; i < quantity; i++) {
            String eng = sc.nextLine();
            String viet = sc.nextLine();
            dictionaryAdd(eng.trim(), viet.trim());
        }
        sc.close();
    }

    /* Get data for dictionary by loading file. */
    public void insertFromFile() throws Exception {
        dictionary = new Dictionary();
        File dictionaryFile = new File(dictionaries_txt + "dictionaries.txt");
        Scanner sc = new Scanner(dictionaryFile);

        while (sc.hasNextLine()) {
            String line = sc.nextLine();
            String[] words = line.split("\t");
            dictionaryAdd(words[0].trim(), words[1].trim());
        }
        Sort.sortDictionaryInAlphabeticalOrder(dictionary.getListOfWords());
        sc.close();
    }

    /**
     * Export dictionaries.txt file
     * Save exported file at src/main/Data/EXPORT_...
     */
    public void dictionaryExportToFile() throws IOException {
        //Create file
        String exportDictionaryFile = dictionaries_txt + "EXPORT_dictionary.txt";
        File exportFile = new File(exportDictionaryFile);
        if (exportFile.createNewFile()) {
            System.out.println("EXPORTED: " + dictionaries_txt + " --INTO-- " + exportFile);
        } else {
            System.out.println(exportDictionaryFile + "EXISTS.");
        }
        //Write dictionary into file
        StringBuilder newDictionaryContent = new StringBuilder();
        for (Word word : dictionary.getListOfWords()) {
            newDictionaryContent.append(word.getWord_target()).append(":")
                    .append(word.getWord_explain()).append("\n");
        }
        FileWriter fileWriter = new FileWriter(exportDictionaryFile);
        PrintWriter printWriter = new PrintWriter(fileWriter);
        printWriter.print(newDictionaryContent);
        printWriter.close();
        fileWriter.close();
    }

    /**
     * Import a existing file in default data directory
     * ./src/main/resources/data/...
     */
    public void dictionaryImportFromFile(String fileName) throws FileNotFoundException {
        dictionary = new Dictionary();
        File dictionaryFile = new File(dictionaries_txt + fileName);
        Scanner sc = new Scanner(dictionaryFile);
        while (sc.hasNextLine()) {
            String line = sc.nextLine();
            String[] words = line.split(":");
            dictionaryAdd(words[0].trim(), words[1].trim());
        }
        Sort.sortDictionaryInAlphabeticalOrder(dictionary.getListOfWords());
        sc.close();
    }

    /* Update a word. */
    public boolean dictionaryUpdate(String word_target, String word_explain) {
        int pos = Search.findPositionInWordListByString(word_target, dictionary.getListOfWords());
        if (pos == -1) {    //if there is no word_target in current dictionary
            System.out.println("NO INFORMATION");
        } else {
            DetailAlert alert = new ConfirmationAlert("Confirm...",
                    "Do you want to change the meaning of this word?");
            if (alert.alertAction()) {
                dictionary.getListOfWords().get(pos).setWord_explain(word_explain);
                return true;
            }
        }
        return false;
    }

    /* Return words started with a given string. */
    public List<String> dictionarySearch(String searchWord) {
        List<String> searchWordsList = new ArrayList<>();
        searchWordsList.clear();
        List<Word> listOfWords = dictionary.getListOfWords();
        Sort.sortDictionaryInAlphabeticalOrder(listOfWords);
        int pos = Search.findPosOfFirstContainedWord(searchWord, listOfWords);
        if (pos != -1) {
            String word = listOfWords.get(pos).getWord_target().toLowerCase();
            while (word.length() >= searchWord.length() && pos < listOfWords.size() - 1
                    && word.substring(0, searchWord.length()).equals(searchWord.toLowerCase())) {
                searchWordsList.add(listOfWords.get(pos).getWord_target());
                pos++;
                if (pos == listOfWords.size()) {
                    break;
                }
                word = listOfWords.get(pos).getWord_target().toLowerCase();
            }
        }
        return searchWordsList;
    }

    /* Returns the meaning of the target word. */
    public String dictionaryLookup(String word_target) {
        int pos = Search.findPositionInWordListByString(word_target, dictionary.getListOfWords());
        if (pos == -1) {    //if there is no word_target in current dictionary
            return "...";
        } else {
            return dictionary.getListOfWords().get(pos).getWord_explain();
        }
    }

    /* Display all words. */
    public void dictionaryDisplay() {
        List<Word> listOfWords = dictionary.getListOfWords();
        Sort.sortDictionaryInAlphabeticalOrder(dictionary.getListOfWords());
        for (int i = 0; i < listOfWords.size(); i++) {
            System.out.printf("| %-5d| ", i);
            System.out.printf("%-30s| ", listOfWords.get(i).getWord_target());
            System.out.printf("%-50s| ", listOfWords.get(i).getWord_explain());
            System.out.println();
        }
    }

    /* Check if exist before add. */
    public boolean dictionaryConditionalAdd(String word_target, String word_explain, boolean isExist) {
        Alert addAlert = new Alert(Alert.AlertType.NONE);
        if (isExist) {
            DetailAlert alert = new ConfirmationAlert("CONFIRM",
                    "This word has in my dictionary with meaning: " + dictionaryLookup(word_target)
                            + ". Do you want to add another meaning for this word?");
            if (alert.alertAction()) {
                dictionaryAddAnotherMeaningOfWord(word_target, word_explain);
                return true;
            }
        } else {
            dictionaryAdd(word_target, word_explain);
            return true;
        }
        return false;
    }

    /* Add word into dictionary. */
    public void dictionaryAdd(String word_target, String word_explain) {
        dictionary.getListOfWords().add(new Word(word_target, word_explain));
    }

    /* Add another meaning of existing word into dictionary. */
    public void dictionaryAddAnotherMeaningOfWord(String word_target, String word_another_meaning) {
        int pos = Search.findPositionInWordListByString(word_target, dictionary.getListOfWords());
        String newMeaning = dictionary.getListOfWords().get(pos).getWord_explain() + "; " + word_another_meaning;
        dictionary.getListOfWords().get(pos).setWord_explain(newMeaning);
    }

    /* Remove a word by target word. */
    public boolean dictionaryRemove(String word_target) {
        int pos = Search.findPositionInWordListByString(word_target, dictionary.getListOfWords());
        if (pos == -1) {    //if there is no word_target in current dictionary
            return false;
        } else {
            dictionary.getListOfWords().remove(pos);
            return true;
        }
    }

    /* first game - fit the blank  */
    public void dictionaryLearningWord() {
//        Game1 game1 = new Game1();
//        game1.setSize(8);
//        game1.initBoard(dictionary.getListOfWords());
//        game1.showBoard();
//        List<Word> answer = game1.getAns();
//        Scanner sc = new Scanner(System.in);
//        int numAnswer = answer.size();
//        int count = 0;
//        System.out.println("There are " + numAnswer + " in this board!");
//        System.out.println("Your ans: ");
//        while (count < numAnswer) {
//            System.out.println("The " + count + "th word: ");
//            String ans = sc.next();
//            int pos = Search.findPositionInWordListByString(ans, answer);
//            if (pos == -1) {    //if there is no word_target in current dictionary
//                System.out.println("Your answer is false");
//            } else {
//                System.out.println("Your answer is correct!");
//                System.out.println("This word mean: " + answer.get(pos).getWord_explain());
//            }
//            System.out.println("Your ans: ");
//            count++;
//        }
    }
}

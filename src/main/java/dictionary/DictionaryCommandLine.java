package dictionary;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class DictionaryCommandLine {
    /*private static final Map<Integer, String> menu = new HashMap<>();
    private static DictionaryManagement dictionaryManagement;

    *//* Get data for dictionary. *//*
    public static void dictionaryBasic() throws Exception {
        dictionaryManagement = new DictionaryManagement();
        dictionaryManagement.insertFromFile();
    }

    *//* Display menu and load action. *//*
    public static void dictionaryAdvanced() throws Exception {
        setUpMenu();
        System.out.println("Welcome to My Application!");
        for (int i = 0; i < 10; i++) {
            System.out.print("[" + i + "] ");
            System.out.println(menu.get(i));
        }
        System.out.println("Your action: ");
        Scanner sc = new Scanner(System.in);
        int flag = sc.nextInt();
        while (flag != 0) {
            switch (flag) {
                case 1: //add
                    System.out.println("Word to be added: ");
                    sc.nextLine();
                    String word = sc.nextLine().trim();
                    System.out.print("---> What does this word mean?: ");
                    String mean = sc.nextLine();
                    mean = mean.trim();
                    boolean isExist = !dictionaryManagement.dictionaryLookup(word).equals("NO INFORMATION");
                    if (dictionaryManagement.dictionaryConditionalAdd(word, mean, isExist)) {
                        System.out.println("---> Added: " + word + " -> " + mean);
                    }
                    break;
                case 2: //remove
                    System.out.println("Word to be removed: ");
                    sc.nextLine();
                    dictionaryManagement.dictionaryRemove(sc.nextLine().trim());
                    break;
                case 3: //update
                    System.out.print("Word to be updated : ");
                    sc.nextLine();
                    String word_target = sc.nextLine().trim();
                    System.out.println("--> New meaning: ");
                    String word_explain = sc.nextLine().trim();
                    dictionaryManagement.dictionaryUpdate(word_target, word_explain);
                    break;
                case 4: //display
                    showAllWords();
                    break;
                case 5: //lookup
                    System.out.print("Look up for: ");
                    sc.nextLine();
                    System.out.println("--> Meaning: " + dictionaryManagement.dictionaryLookup(sc.nextLine().trim()));
                    break;
                case 6: //search
                    System.out.print("Search for: ");
                    sc.nextLine();
                    String searchWord = sc.nextLine();
                    System.out.println("--> Searched List: ");
                    for (String str : dictionaryManagement.dictionarySearch(searchWord)) {
                        System.out.println(str);
                    }
                    System.out.println("--------end--------");
                    break;
                case 7: //game
                    dictionaryManagement.dictionaryLearningWord();
                    break;
                case 8: //import from file
                    System.out.println("Enter txt file name to load (Default directory: ./src/main/Data/...): ");
                    sc.nextLine();
                    dictionaryManagement.dictionaryImportFromFile(sc.nextLine());
                    break;
                case 9: //export to file
                    dictionaryManagement.dictionaryExportToFile();
                    break;
                default:
                    System.out.println("Action not supported");
            }
            System.out.println("Your action: ");
            flag = sc.nextInt();
        }
        sc.close();
    }

    *//* Set up data for menu. *//*
    public static void setUpMenu() {
        menu.put(0, "Exit");
        menu.put(1, "Add");
        menu.put(2, "Remove");
        menu.put(3, "Update");
        menu.put(4, "Display");
        menu.put(5, "Lookup");
        menu.put(6, "Search");
        menu.put(7, "Game");
        menu.put(8, "Import from file");
        menu.put(9, "Export to file");
    }

    *//* Show all words in the dictionary. *//*
    public static void showAllWords() {
        System.out.printf("| %-5s| ", "NO");
        System.out.printf("%-30s| ", "ENGLISH");
        System.out.printf("%-50s| ", "VIETNAMESE");
        System.out.println();
        dictionaryManagement.dictionaryDisplay();
    }

    public static void main(String[] args) throws Exception {
        dictionaryBasic();
        dictionaryAdvanced();
    }*/
}

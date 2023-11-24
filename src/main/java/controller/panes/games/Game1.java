package controller.panes.games;


import dictionary.Word;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Game1 {
    ArrayList<ArrayList<String>> boardWord;
    List<Word> ans;
    int size;

    public Game1() {
        boardWord = new ArrayList<>();
        ans = new ArrayList<>();
    }

    public void setSize(int n) {
        size = n;
    }

    public boolean checkValid(int posX, int posY, int HorV, int length) {
        if (HorV == 1) {
            if (posX + length - 1 >= size) {
                return false;
            } else {
                for (int i = posX; i < posX + length; i++) {
                    if (!(boardWord.get(posY).get(i).equals("_"))) {
                        return false;
                    }
                }
            }
        } else {
            if (posY + length - 1 >= size) {
                return false;
            } else {
                for (int i = posY; i < posY + length; i++) {
                    if (!(boardWord.get(i).get(posX).equals("_"))) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    public void initBoard(List<Word> listWord) {
        int lengthListWord = listWord.size();
        ArrayList<String> englishWord = new ArrayList<>();
        Random rand = new Random();
        for (int i = 0; i < size / 4; i++) {
            int ranNum = rand.nextInt(lengthListWord - 1) + 1;
            englishWord.add(listWord.get(ranNum).getWord_target());
            ans.add(listWord.get(ranNum));
        }
        for (int i = 0; i < size; i++) {
            ArrayList<String> tmp = new ArrayList<>();
            for (int j = 0; j < size; j++) {
                tmp.add("_");
            }
            boardWord.add(tmp);
        }
        for (int i = 0; i < size / 4; i++) {
            String tmp = englishWord.get(i);
            int sizeOfWord = tmp.length();
            int ranHorV = rand.nextInt(2) + 1;
            int posX = rand.nextInt(size);
            int posY = rand.nextInt(size);
            if (ranHorV == 1) {
                while (!checkValid(posX, posY, 1, sizeOfWord)) {
                    posX = rand.nextInt(size);
                    posY = rand.nextInt(size);
                }
                int k = 0;
                for (int j = posX; j < (posX + sizeOfWord); j++) {
                    String letter = Character.toString(tmp.charAt(k));
                    letter = letter.toLowerCase();
                    boardWord.get(posY).set(j, letter);
                    k++;
                }
            } else {
                while (!checkValid(posX, posY, 2, sizeOfWord)) {
                    posX = rand.nextInt(size);
                    posY = rand.nextInt(size);
                }
                int k = 0;
                for (int j = posY; j < (posY + sizeOfWord); j++) {
                    String letter = Character.toString(tmp.charAt(k));
                    letter = letter.toLowerCase();
                    boardWord.get(j).set(posX, letter);
                    k++;
                }
            }
        }
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (boardWord.get(i).get(j).equals("_")) {
                    int randNum = rand.nextInt(26) + 97;
                    char c = (char) randNum;
                    String letter = Character.toString(c);
                    boardWord.get(i).set(j, letter);
                }
            }
        }
    }

    public void showBoard() {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                System.out.print(boardWord.get(i).get(j) + " ");
            }
            System.out.println();
        }
    }

    public List<Word> getAns() {
        return ans;
    }
}

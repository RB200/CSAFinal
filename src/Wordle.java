import java.util.ArrayList;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
public class Wordle {

    static ArrayList<String> allowedWords = new ArrayList<String>();

    static ArrayList<String> possibleWords = new ArrayList<String>();


    static Scanner scan = new Scanner(System.in);
    // init boards
    static char[][] board = new char[6][5];
    static char[][] statusBoard = new char[6][5];

    static int[] letterCounts = new int[5];

    static int row = 0;


    public static void main(String[] args){

        // init board
        for(int r = 0; r < board.length; r++){
            for(int c = 0; c < board[r].length; c++){
                board[r][c] = '■';
                statusBoard[r][c] = '■';

            }
        }

        try{
            // make lists of all possible words / possible answers
            Scanner fileRead = new Scanner(new File("src/possibleWords.txt"));
            Scanner file2Read = new Scanner(new File("src/possibleAnswers.txt"));
            while(fileRead.hasNext()){
                allowedWords.add(fileRead.next());
            }
            while(file2Read.hasNext()){
                possibleWords.add(file2Read.next());
            }
        }
        catch(FileNotFoundException e){
            System.out.println(e);
        }
        String selectedWord = possibleWords.get((int)(Math.random()*2315));

        while(row < 6){
            System.out.print("Input guess: ");
            String playerGuess = scan.next().toLowerCase();
            if(isValidWord(playerGuess)) {
                System.out.println();
                board[row] = playerGuess.toCharArray();
                row++;

                String rowWord = null;
                int statusBoardRow = 0;
                // printing board
                System.out.println("  GUESS     STATS");
                System.out.println("+-------+-----------+");
                for (char[] word : board) {
                    rowWord = "";
                    System.out.print("| ");
                    for (char c : word) {
                        System.out.print(c);
                        rowWord += c;
                    }
                    System.out.print(" | ");
                    for(char c : getStatus(rowWord,selectedWord)){
                        System.out.print(c + " ");
                    }
                    System.out.print("|");
                    System.out.println();
                    statusBoard[statusBoardRow] = getStatus(rowWord,selectedWord); // getStatus returns one "row"
                    statusBoardRow++;
                }
                System.out.println("+-------+-----------+");

                if (checkWin(row)) {
                    System.out.println();
                    System.out.println("You win!");
                    break;
                } else if (row == 6) {
                    System.out.println("You lose. The word was: " + selectedWord.toUpperCase());
                }
            }
            else{
                System.out.println("Invalid word, try again. ");
            }
        }
    }
    public static boolean isValidWord(String word){
        if(word.length() == 5) {
            for(String w: allowedWords){
                if (w.equals(word.toLowerCase())) {
                    return true;
                }
            }
        }
        return false;
    }
    public static char[] getStatus(String playerWord, String targetWord){
        char[] pWord = playerWord.toCharArray();
        char[] tWord = targetWord.toCharArray();
        char[] result = new char[5];

        int[] letterApps = new int[26];
        for(int i = 0; i < pWord.length; i++) {
            letterApps[(int) (tWord[i]) - 97]++;
        }
        // checking if the letter is in the word but wrong position ("yellow letter")
        for(int i = 0; i < pWord.length; i++){
            if((int)pWord[i]-97 < 26){ // (int)pWord[i]-97 gives a number between 0-25 if the character is a lowercase letter
                if(letterApps[(int)(pWord[i])-97] > 0){
                    if(pWord[i] != '■'){
                        if(pWord[i] == tWord[i]){
                            result[i] = '*';
                        }
                        else{
                            for(int j = 0; j < tWord.length; j++){
                                if(i != j){
                                    if(pWord[i] == tWord[j]){
                                        result[i] = '/';
                                    }
                                }
                            }
                        }
                    }
                    letterApps[(int)(pWord[i])-97] --;
                }
            }
        }
        return result;
    }
    public static boolean checkWin(int row){
        row--;
        for(int c = 0; c < statusBoard[row].length; c++){
            if(statusBoard[row][c] != '*') {
                return false;
            }
            if(statusBoard[row][0] != statusBoard[row][c]){
                return false;
            }
        }
        return true;
    }
}

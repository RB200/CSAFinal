import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;

public class Wordle {

    static ArrayList<String> allowedWords = new ArrayList<String>();

    static ArrayList<String> possibleWords = new ArrayList<String>();


    static Scanner scan = new Scanner(System.in);
    static char[][] board = new char[6][5];
    static char[][] statusBoard = new char[6][5];
    static int row = 0;


    public static void main(String[] args){
        for(int r = 0; r < board.length; r++){
            for(int c = 0; c < board[r].length; c++){
                board[r][c] = '■';
                statusBoard[r][c] = '■';

            }
        }

        try{
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
        System.out.println(selectedWord);
        while(row < 6){
            System.out.print("Input guess: ");
            String playerGuess = scan.next();
            if(isValidWord(playerGuess)){

                System.out.println();

                board[row] = playerGuess.toCharArray();
                row++;

                System.out.println("BOARD");

                for(char[] word : board){
                    String rowWord = "";
                    for(char c : word){
                        rowWord += c;
                    }
                    System.out.print(rowWord);
                    System.out.print("\t\t\t" + Arrays.toString(getStatus(rowWord, selectedWord)));

                    System.out.println();
                }
            }
            else{
                System.out.println("Invalid word, try again. ");
            }
            checkWin();
        }

        //System.out.println(selectedWord);
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
        for(int i = 0; i < pWord.length; i++){
            if(pWord[i] == tWord[i]){
                result[i] = '☑';
            }
            else{
                for(int j = 0; j < tWord.length; j++){
                    if(j != i){
                        if(pWord[i] == tWord[j]){
                            result[i] = '~';
                        }
                        else if(result[i] != '☑'){
                            result[i] = '✕';
                        }
                    }
                }
            }
        }
        return result;
    }
    public static void checkWin(){

    }
}

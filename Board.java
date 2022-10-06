import java.util.ArrayList;
import java.util.Scanner;
import java.lang.IllegalArgumentException;
import java.io.File;
import java.io.FileNotFoundException;

public class Board {
    // data members
    private ArrayList<ArrayList<Integer>> board;
    private ArrayList<Integer> availableNumbers;
    private final int EMPTY = 0;

    public Board(String filename){
        availableNumbers = new ArrayList<>();
        board = new ArrayList<>();

        // initialize 2D Arraylist board 9x9
        for(int i = 0; i <= 9; i++){
            board.add(new ArrayList<Integer>());
        }
        for(int i = 0; i < 9; i++){
            for(int j = 1; j <= 9; j++){
                board.get(i).add(EMPTY); // empty cell
            }
        }

        readBoard(filename); // read in provided board

        // represent the number of each digit that is left to be placed on the board
        // this for loop should be executed before you read the board because inside readBoard you will need it
        for (int i = 0; i < 9; i++){
            availableNumbers.add(9);
        }
    }

    private void readBoard(String filename) throws IllegalArgumentException{
        File file = new File(filename);

        try{
            Scanner read = new Scanner(file);
            for (int i = 0; i < 9; i++){
                for(int j = 0; j < 9; j++){
                    //if(board.get(i).get(j) != EMPTY){ // all cells are empty when you start reading from the file
                        //if(isAvailable(read.nextInt())){
                            int number = read.nextInt(); // you read the number once for each cell
                            board.get(i).set(j, number);
                            availableNumbers.set(number-1, availableNumbers.get(number-1)-1);
                            //checkMove(i, j); do not call checkMove here
                        //}
                        //else{
                        //    System.out.println("Cannot be placed");
                        //}
                    //}
                    //else{
                    //    board.get(i).set(j, read.nextInt());
                    //}
                }
            }
            read.close();
            // call checkMove here after you read the complete board - use two nested loops i, i and check each cell (i, j)
        }
        catch(FileNotFoundException e){
            System.out.println("File not found");
            System.exit(0);
        }
        
        throw new IllegalArgumentException("");
    }

    private boolean isAvailable(int digit){
            if(availableNumbers.get(digit-1) > 0){
                return true;
            }
        return false;
    }

    private boolean noNumbersLeft(){
        boolean isZero = true;
        for(int i = 0; i < 9; i++){
            if(availableNumbers.get(i) > 0){
                return false;
            } 
        }
        return true;
    }

    private boolean checkMove(int row, int col){
        int num = board.get(row).get(col);
        boolean b = true;
        int r = row - row % 3;
        int c = col - col % 3;
        for(int i = 0; i < 9; i++){
            if(board.get(row).get(i) == num){ // check also if i != col
                b = false;
            }
        }
        for(int i = 0; i < 9; i++){
            if(board.get(i).get(col) == num){ // check if i!= row too
                b = false;
            }
        }
        for(int i = r; i < r + 3; i++){
            for(int j = c; j < c + 3; j++){
                if(board.get(i).get(j) == num){ // check also if i!=row && j!=col
                    b = false;
                }
            }
        }
        return b;
    }

    public boolean solve(){
        for(int i = 0; i < 9; i++){
            for(int j = 0; j < 9; j++){
                if(board.get(i).get(j) == EMPTY){
                    for(int k = 1; k <= 9; k++){
                        if(isAvailable(k)) {
                            board.get(i).set(j, k);
                            if(checkMove(i, j) && solve()){
                                return true;
                            } else {
                                board.get(i).set(j, EMPTY);
                            }
                        }
                    }
                    return false;
                }
            }
        }
        return true;
    }

    public String toString(){
        String out = "";
        for(int i = 0; i < board.size(); i++){
            for(int j = 0; j < board.get(i).size(); j++){
                out += board.get(i).get(j);
            }
            out += "\n";
        }
        return out;
    }
}

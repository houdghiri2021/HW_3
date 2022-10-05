public class Test{
    public static void main(String[] args){
        try{
            Board puzzle = new Board("sudoku.txt");
            System.out.println(puzzle.toString());
            puzzle.solve();
            System.out.println(puzzle);
        }
        catch(IllegalArgumentException e){
            System.out.println(e.getMessage());
        }
    }
}

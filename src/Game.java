import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class Game {
  private Sudoku sudoku;

  Game(Sudoku sudoku) {
    this.sudoku = sudoku;
  }

  public void showSudoku() {
    System.out.println(sudoku);
  }

  /**
   * Implementation of the AC-3 algorithm
   * 
   * @return true if the constraints can be satisfied, else false
   */
  public boolean solve() {
    // TODO: implement AC-3
    return true;
  }

  /**
   * Checks the validity of a sudoku solution
   * 
   * @return true if the sudoku solution is correct
   */
  public boolean validSolution() {
    for (int x = 0; x < 9; x++) {
      List<Integer> row = new ArrayList<Integer>(sudoku.getRow(x));
      HashSet<Integer> rowset = new HashSet<Integer>(row);
      List<Integer> rowlist = new ArrayList<Integer>(rowset);
      if (rowlist.size() != 9) {
        return false;
      }
      List<Integer> column = new ArrayList<Integer>(sudoku.getColumn(x));
      HashSet<Integer> columnset = new HashSet<Integer>(column);
      List<Integer> columnlist = new ArrayList<Integer>(columnset);
      if (columnlist.size() != 9) {
        return false;
      }
    }
    for (int x = 0; x < 3; x++) {
      for (int y = 0; y < 3; y++) {
        List<Integer> square = new ArrayList<Integer>(sudoku.getSquare(x, y));
        HashSet<Integer> squareset = new HashSet<Integer>(square);
        List<Integer> squarelist = new ArrayList<Integer>(squareset);
        if (squarelist.size() != 9) {
          return false;
        }
      }
    }
    return true;
  }
}

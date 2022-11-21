import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;

public class Game {
  private Sudoku sudoku;

  Game(Sudoku sudoku) {
    this.sudoku = sudoku;
  }

  public Sudoku getSudoku() {
    return sudoku;
  }

  public void showSudoku() {
    for (int x = 0; x < 9; x++) {
      for (int y = 0; y < 9; y++) {
        System.out.println(sudoku.getBoard()[x][y].getDomain());
      }
    }
    System.out.println(sudoku);
  }

  public boolean AC3() {
    System.out.println("AC3");
    Queue<Field[]> queue = new LinkedList<>();
    for (Field[] row : sudoku.getBoard()) {
      for (Field field : row) {
        // System.out.println(field.getNeighbours());
        for (Field neighbour : field.getNeighbours()) {
          queue.add(new Field[] { field, neighbour });
        }
      }
    }
    for (Field[] arc : queue) {
      String print = "";
      for (Field field : arc) {
        print += field.getValue() + " ";
      }
      // System.out.println(print);
    }
    while (!queue.isEmpty()) {
      Field[] arc = queue.remove();
      if (arc[1].getValue() != 0) {
        if (arc[0].getDomain().contains(arc[1].getValue())) {
          boolean removed = arc[0].removeFromDomain(arc[1].getValue());
          if (arc[0].getDomain().size() == 0) {
            return false;
          }
          if (removed) {
            // System.out.println("Arc: " + arc[0].getValue() + " " + arc[1].getValue());
            // System.out.println("Removed " + arc[1].getValue() + " from " +
            // arc[0].getValue());
            // System.out.println("Domain of: " + arc[0].getValue() + ": " +
            // arc[0].getDomain());
            for (Field neighbour : arc[0].getOtherNeighbours(arc[1])) {
              if (!queue.contains(new Field[] { neighbour, arc[0] }) && neighbour.getValue() == 0) {
                queue.add(new Field[] { neighbour, arc[0] });
              }
            }
          }
        }
      }
    }
    return true;
  }

  /**
   * Implementation of the AC-3 algorithm
   * 
   * @return true if the constraints can be satisfied, else false
   */
  public boolean solve() {

    if (!AC3()) {
      return false;
    } else if (validSolution()) {
      return true;
    } else {
      for (int d = 2; d < 9; d++) {
        System.out.println("Trying domain size: " + d);
        for (int x = 0; x < 9; x++) {
          for (int y = 0; y < 9; y++) {
            if (sudoku.getBoard()[x][y].getDomainSize() == d) {
              System.out.println("Trying value: " + x + " " + y);
              System.out.println(sudoku.getBoard()[x][y].getDomain());
              for (int value : sudoku.getBoard()[x][y].getDomain()) {
                System.out.println("val: " + value);
                Sudoku saved = new Sudoku(sudoku);
                this.getSudoku().getBoard()[x][y].setValue(value);
                List<Integer> newdomain = new ArrayList<Integer>();
                newdomain.add(value);
                this.getSudoku().getBoard()[x][y].setDomain(newdomain);
                if (this.solve()) {
                  return true;
                } else {
                  this.sudoku = saved;
                }
              }
              System.out.println();
            }
          }
        }
      }
      return false;
    }
    // while (AC3() && !validSolution()) {
    // for (int d = 2; d < 9; d++) {
    // System.out.println("Trying domain size: " + d);
    // for (int x = 0; x < 9; x++) {
    // for (int y = 0; y < 9; y++) {
    // if (sudoku.getBoard()[x][y].getDomainSize() == d) {
    // System.out.println("Trying value: " + x + " " + y);
    // System.out.println(sudoku.getBoard()[x][y].getDomain());
    // for (int value : sudoku.getBoard()[x][y].getDomain()) {
    // System.out.println("val: " + value);
    // Sudoku saved = new Sudoku(sudoku);
    // this.getSudoku().getBoard()[x][y].setValue(value);
    // List<Integer> newdomain = new ArrayList<Integer>();
    // newdomain.add(value);
    // this.getSudoku().getBoard()[x][y].setDomain(newdomain);
    // if (this.solve()) {
    // return true;
    // } else {
    // this.sudoku = saved;
    // }
    // }
    // System.out.println();
    // }
    // }
    // }
    // }
    // if (validSolution())
    // return true;
    // else {
    // System.out.println("No solution found");
    // break;
    // }
    // }
    // if (validSolution())
    // return true;

    // return false;
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
    for (Field[] row : sudoku.getBoard()) {
      for (Field field : row) {
        if (field.getValue() == 0) {
          return false;
        }
      }
    }
    return true;
  }
}

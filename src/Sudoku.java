import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Sudoku {
  private Field[][] board;

  Sudoku(String filename) {
    this.board = readsudoku(filename);
  }

  @Override
  public String toString() {
    String output = "╔═══════╦═══════╦═══════╗\n";
    for (int i = 0; i < 9; i++) {
      if (i == 3 || i == 6) {
        output += "╠═══════╬═══════╬═══════╣\n";
      }
      output += "║ ";
      for (int j = 0; j < 9; j++) {
        if (j == 3 || j == 6) {
          output += "║ ";
        }
        output += board[i][j] + " ";
      }

      output += "║\n";
    }
    output += "╚═══════╩═══════╩═══════╝\n";
    return output;
  }

  /**
   * Reads sudoku from file
   * 
   * @param filename
   * @return 2d int array of the sudoku
   */
  public static Field[][] readsudoku(String filename) {
    assert filename != null && filename != "" : "Invalid filename";
    String line = "";
    Field[][] grid = new Field[9][9];
    try {
      FileInputStream inputStream = new FileInputStream(filename);
      Scanner scanner = new Scanner(inputStream);
      for (int i = 0; i < 9; i++) {
        if (scanner.hasNext()) {
          line = scanner.nextLine();
          for (int j = 0; j < 9; j++) {
            int numValue = Character.getNumericValue(line.charAt(j));
            if (numValue == 0) {
              grid[i][j] = new Field();
            } else if (numValue != -1) {
              grid[i][j] = new Field(numValue);
            }
          }
        }
      }
      scanner.close();
    } catch (FileNotFoundException e) {
      System.out.println("error opening file: " + filename);
    }
    addNeighbours(grid);
    return grid;
  }

  /**
   * Adds a list of neighbours to each field, i.e., arcs to be satisfied
   * 
   * @param grid
   */
  private static void addNeighbours(Field[][] grid) {
    // TODO: for each field, add its neighbours
  }

  /**
   * Generates fileformat output
   */
  public String toFileString() {
    String output = "";
    for (int i = 0; i < board.length; i++) {
      for (int j = 0; j < board[0].length; j++) {
        output += board[i][j].getValue();
      }
      output += "\n";
    }
    return output;
  }

  public Field[][] getBoard() {
    return board;
  }

  public List<Integer> getRow(int x) {
    List<Integer> row = new ArrayList<Integer>();
    for (int i = 0; i < 9; i++) {
      row.add(board[x][i].getValue());
    }
    return row;
  }

  public List<Integer> getColumn(int y) {
    List<Integer> column = new ArrayList<Integer>();
    for (int i = 0; i < 9; i++) {
      column.add(board[i][y].getValue());
    }
    return column;
  }

  public List<Integer> getSquare(int x, int y) {
    List<Integer> square = new ArrayList<Integer>();
    for (int i = 0; i < 3; i++) {
      for (int j = 0; j < 3; j++) {
        square.add(board[x * 3 + i][y * 3 + j].getValue());
      }
    }
    return square;
  }
}

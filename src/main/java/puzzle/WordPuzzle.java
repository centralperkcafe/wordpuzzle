package puzzle;

import java.util.List;
import java.util.ArrayList;
import java.util.HashSet;


public class WordPuzzle {

  // fields
  private static final int BOARD_SIZE = 3;
  private Board boardInstance;
  private Dictionary dictionary;
  private char[][] board;
  private List<String> result;   
  private boolean[][] visited;
  private int[] directionRow    = {-1, -1, -1,  0, 0,  1, 1, 1};
  private int[] directionColumn = {-1,  0,  1, -1, 1, -1, 0, 1};
  private boolean repeat = false;

  // Constructor
  public WordPuzzle(String dictionaryFileName) {
      this.boardInstance = new Board(BOARD_SIZE, dictionaryFileName);
      boardInstance.generateBoard();
      this.board = boardInstance.getBoard();

      this.dictionary = new Dictionary(dictionaryFileName);
      this.result = new ArrayList<>();
      this.visited = new boolean[BOARD_SIZE][BOARD_SIZE];
  }

  public List<String> findWords() {
      for (int i = 0; i < BOARD_SIZE; i++) {
          for (int j = 0; j < BOARD_SIZE; j++) {
              dfs(i, j, "");
          }
      }
      return result;
  }

  private void dfs(int row, int column, String prefix) {
      if (row < 0 || row >= BOARD_SIZE || column < 0 || column >= BOARD_SIZE || visited[row][column]) {
          return;
      }

      prefix += board[row][column];

      if (!dictionary.checkPrefix(prefix)) {
          return;
      }

      if (dictionary.checkWord(prefix)) {
          result.add(prefix);
      }

      visited[row][column] = true;
      for (int i = 0; i < 8; i++) {
          int nextRow = row + directionRow[i];
          int nextColumn = column + directionColumn[i];
          dfs(nextRow, nextColumn, prefix);
      }
      visited[row][column] = false;
  }
  
  public char[][] getBoard() {
    return board;
  }

public boolean is_repeat() {
    repeat = false; // Assuming 'repeat' is a boolean field in this class
    if (result != null && !result.isEmpty()) {
        HashSet<String> uniqueWords = new HashSet<>();
        for (String word : result) {
            if (!uniqueWords.add(word)) { // add returns false if the element was already in the set
                repeat = true;
                break;
            }
        }
    }
    return repeat;
}

  public static void main(String[] args) {
    WordPuzzle puzzle = new WordPuzzle("vocab.txt");

    List<String> words = puzzle.findWords();


    // while (puzzle.is_repeat()) {
    //   puzzle = new WordPuzzle("vocab.txt");
    //   words = puzzle.findWords();
    // }

    char[][] board = puzzle.getBoard();

    for (char[] row : board) {
        for (char cell : row) {
            System.out.print(cell + " ");
        }
        System.out.println();
    }

    System.out.println(words);
    System.out.println("repeated words in result: " + puzzle.is_repeat());
  }
}
package puzzle;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Board {
    private char[][] board;
    private int size;
    private String dictionaryFileName;
    private Random random = new Random();

    // initialize the board with size
    public Board(int size, String dictionaryFileName) {
        this.size = size;
        this.dictionaryFileName = dictionaryFileName;
        this.board = new char[size][size];

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                this.board[i][j] = '\0';
            }
        }
    }

    public void generateBoard() {
        // choose a word
        String word = chooseWord().toLowerCase();
        if (word == null) {
            System.out.println("No suitable word found in the dictionary.");
            return;
        }

        // System.out.println("Chosen word: " + word);

        // place the word on the board
        placeWord(word);
        // printBoard();

        // fill the rest of the board
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (this.board[i][j] == '\0') {
                    this.board[i][j] = LetterGenerator.generateLetter();
                }
            }
        }
    }

    // choose a word from file (5-8 letters)
    private String chooseWord() {
        List<String> words = new ArrayList<>();
        try (InputStream is = ClassLoader.getSystemResourceAsStream(dictionaryFileName);
             BufferedReader reader = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.length() >= 5 && line.length() <= 8) {
                    words.add(line.toLowerCase());
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

        if (!words.isEmpty()) {
            return words.get(random.nextInt(words.size()));
        } else {
            return null;
        }
    }

    private void placeWord(String word) {
        int length = word.length();
        boolean placed = false;
        int attempts = 0;
        int maxAttempts = 3;
        int directionAttemptsMax = 20;

        while (!placed && attempts < maxAttempts) {
            // set start position
            int curRow = random.nextInt(size);
            int curCol = random.nextInt(size);
            // System.out.println("startRow: " + curRow + ", startCol: " + curCol);

            if (!is_valid(curRow, curCol)) {
                attempts++;
                continue;
            }

            // place the first letter
            board[curRow][curCol] = word.charAt(0);

            boolean wordPlaced = true;
            for (int i = 1; i < length; i++) {
                int directionAttempts = 0;
                int[] direction = getRandomDirection();
                int nextRow = curRow + direction[0];
                int nextCol = curCol + direction[1];

                // check current position is valid
                while (!is_valid(nextRow, nextCol) && directionAttempts < directionAttemptsMax) {
                    direction = getRandomDirection();
                    nextRow = curRow + direction[0];
                    nextCol = curCol + direction[1];
                    directionAttempts++;
                }

                // if it's a dead end, then find another start position
                if (!is_valid(nextRow, nextCol)) {
                    wordPlaced = false;
                    break;
                }

                board[nextRow][nextCol] = word.charAt(i);
                curRow = nextRow;
                curCol = nextCol;
            }

            if (wordPlaced) {
                placed = true;
            } else {
                // Reset the board and attempt again
                resetBoard();
            }

            attempts++;
        }

        if (!placed) {
            System.out.println("Failed to place the word: " + word);
        }
    }

    private boolean is_valid(int row, int col) {
        return row >= 0 && row < size && col >= 0 && col < size && board[row][col] == '\0';
    }

    private int[] getRandomDirection() {
        int[][] directions = {{0, 1}, {1, 0}, {0, -1}, {-1, 0}, {-1, -1}, {-1, 1}, {1, -1}, {1, 1}};
        return directions[random.nextInt(directions.length)];
    }

    private void resetBoard() {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                board[i][j] = '\0';
            }
        }
    }


    // Method to get a deep copy of the board
    public char[][] getBoard() {
        char[][] boardCopy = new char[size][size];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < board[i].length; j++) {
                char lowerCaseChar = Character.toLowerCase(board[i][j]);
                if (lowerCaseChar < 'a' || lowerCaseChar > 'z') {
                    throw new IllegalArgumentException("Board contains invalid character: '" + board[i][j] + "' at position [" + i + "][" + j + "]");
                }
                boardCopy[i][j] = lowerCaseChar;
            }
        }
        return boardCopy;
    }

    public void printBoard() {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                System.out.print(board[i][j] + " ");
            }
            System.out.println();
        }
    }

    // public static void main(String[] args) {
    //     Board board = new Board(4, "vocab.txt");
    //     board.generateBoard();
    //     board.getBoard();
    // }
}

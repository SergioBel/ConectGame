import java.util.InputMismatchException;
import java.util.Scanner;

public class Assignment2 {
    public static void main(String[] args) {
        //We start creating the 2D Array
        char[][] gameBoard = new char[5][5];
        System.out.println("Welcome to the game");
        Scanner input = new Scanner(System.in);

        initialize(gameBoard);

        int playerPlaying = 1;
        boolean playerNumber = true;
        //Here is a loop in order to recognize a draw, when it loops for 25 times(the slots for the board it stops if there wasn't a winner)
        for (int i=0; i<=24; i++) {

            System.out.println("  1    2    3    4    5");

            displayBoard(gameBoard);
            //The function of the game is being looped until there is a winner, the method must be called in a proper way in order to work properly
            do {
                if (i == 24) {
                    System.out.println("It's a draw!");
                    break;
                }
                if (playerNumber)
                    System.out.print("Player 1 turn: ");
                else
                    System.out.print("Player 2 turn: ");
                //In this game then numbers must be numerical and in a range of 1-5, if it is not a warning must advice the player
                try {
                    int col = input.nextInt();
                    if (!putNumbers(gameBoard, col - 1, playerNumber ? '1' : '2')) {
                    System.out.println("That column is full");
                    continue;
                    }
                }
                catch (InputMismatchException e) {
                    System.err.println("The number must be numeric 1-5.");
                }
                 catch (ArrayIndexOutOfBoundsException e) {
                    System.err.println("The number must be between 1-5: " + e.getMessage() + " is the maximum number");
                }
                //Switcher the player every loop 1 to 2 or 2 to 1
                if (playerPlaying == 1) {
                    playerPlaying = 2;
                } else {
                    playerPlaying = 1;
                }
                playerNumber = !playerNumber;
            }

            while (checkWinner(gameBoard) == 10);

            char result = checkWinner(gameBoard);
            if (result == '1') {
                System.out.println("Player 1 wins!");
                break;
            }
            else if (result == '2') {
                System.out.println("Player 2 wins!");
                break;
            }
        }
    }
    //This array makes the 2D Array null
    public static void initialize(char[][] board) {
        for (int row = 0; row < board.length; row++) {
            for (int col = 0; col < board[row].length; col++)
                board[row][col] = ' ';
        }
    }
    //The 2D array gets from by defining its column with |
    public static void displayBoard(char[][] board) {

        for (int row = 0; row < board.length; row++) {
            for (int col = 0; col < board[row].length; col++) {

                System.out.print("| " + board[row][col] + " |");
            }
            System.out.println();
        }
    }
    //This method gets the collected number and print it into the place the player chosed, but always in the lowest row available
    public static boolean putNumbers (char[][] gameBoard, int col, char playerNumber) {
        if (gameBoard[0][col] != ' ')
            return false;
        for (int row = 0; row < 5; ++row) {

            if (gameBoard[row][col] != ' ') {

                gameBoard[row-1][col] = playerNumber;
                return true;
            }
        }
        gameBoard[gameBoard.length-1][col] = playerNumber;
        return true;
    }
    //check for the numbers if they are the same horizontally until it gets 3, if not it continues to the other methods
    private static char checkWinnerHorizontally(char[][] board) {

        for (int row = 0; row < 5; row++) {
            for (int col = 1; col < 5; col++) {
                int countNumbers = 0;
                if (board[row][col] != ' ' && board[row][col] == board[row][col-1])
                    countNumbers++;
                else
                    countNumbers = 1;
                if (countNumbers >= 3) {
                    return board[row][col];
                }
            }
        }
        return ' ';
    }
    //check for the numbers if they are the same vertically until it gets 3, if not it continues to the other methods
    private static char checkWinnerVertically(char[][] board) {

        for (int col = 0; col < 5; col++) {
            for (int row = 1; row < 5; row++) {
                int countNumbers = 0;
                if (board[row][col] != ' ' && board[row][col] == board[row - 1][col])
                    countNumbers++;
                else
                    countNumbers = 1;
                if (countNumbers >= 3) {
                    return board[row][col];
                }
            }
        }
        return ' ';
    }
    //check for the numbers if they are the same diagonal until it gets 3, if not it continues to the other methods
    private static char checkWinnerDiagonal(char[][] board) {

        for (int col = 0; col < 5; col++) {
            for (int row = 1; row < 5; row++) {
                int countNumbers = 0;
                if (col + row >= 5) break;
                if (board[row][col + row] != ' ' && board[row-1][col + row - 1] == board[row][col + row])
                    countNumbers++;
                else
                    countNumbers = 1;
                if (countNumbers >= 3) return board[row][col + row];
            }
        }
        for (int row = 0; row < 5; row++) {
            for (int col = 1; col < 5; col++) {
                int countNumbers = 0;
                if (col + row >= 5) break;
                if (board[row + col][col] != ' ' && board[row+col - 1][col - 1] == board[row + col][col])
                    countNumbers++;
                else
                    countNumbers = 1;
                if (countNumbers >= 3) return board[row + col][col];
            }
        }
        for (int col = 0; col < 5; col++) {
            for (int row = 1; row < 5; row++) {
                int countNumbers = 0;
                if (col - row < 0) break;
                if (board[row][col - row] != ' ' && board[row - 1][col - row + 1] == board[row][col - row])
                    countNumbers++;
                else
                    countNumbers = 1;
                if (countNumbers >= 3) return board[row][col-row];
            }
        }
        return ' ';
    }
    //If any of the check methods got 3 same numbers, here returns the winner to the main
    public static char checkWinner(char[][] board){
        char winner = checkWinnerHorizontally(board);
        if (winner != ' ') return winner;
        winner = checkWinnerVertically(board);
        if (winner != ' ') return winner;
        winner = checkWinnerDiagonal(board);
        if (winner != ' ') return winner;

        for (int i = 0; i < board.length; i++)
            for (int k = 0; k < board[i].length; k++)
                if (board[i][k] == ' ') return ' ';
        return 10;
    }
}
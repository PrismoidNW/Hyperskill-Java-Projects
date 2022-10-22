package tictactoe.game.players;

import tictactoe.game.Board;
import tictactoe.game.settings.Settings;

import java.util.Random;
import java.util.Scanner;

public class Player {

    public final Random random = new Random();
    public final Scanner scanner = new Scanner(System.in);
    private final Settings type;
    private final Board board;

    public Player(Settings type, Board board) {
        this.type = type;
        this.board = board;
    }

    public Board getBoard() {
        return this.board;
    }

    public void setSpot(int x, int y, char charToPlace) {
        Board.board[y][x] = charToPlace;
    }

    public void getInput(boolean isFirstPlayer) {
        int x;
        int y;
        while (true) {
            System.out.print("\nEnter the coordinates: ");
            try {
                y = scanner.nextInt();
                x = scanner.nextInt();
            } catch (Exception ignore) {
                System.out.print("You should enter numbers!");
                scanner.next();
                continue;
            }
            if (getBoard().cells.coordsOutOfRange(y, x)) {
                System.out.print("Coordinates should be from 1 to 3!");
                continue;
            } else if (Board.board[y - 1][x - 1] != '_') {
                System.out.print("This cell is occupied! Choose another one!");
                continue;
            }
            break;
        }
        if (isFirstPlayer)
            setSpot(x - 1, y - 1, 'X');
        else
            setSpot(x - 1, y - 1, 'O');

        getBoard().printBoard();
    }
}

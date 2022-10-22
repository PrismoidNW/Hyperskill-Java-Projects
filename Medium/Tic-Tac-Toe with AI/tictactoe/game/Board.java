package tictactoe.game;

import tictactoe.game.players.AI;
import tictactoe.game.players.Player;
import tictactoe.game.settings.DifficultyHard;
import tictactoe.game.settings.DifficultyMedium;
import tictactoe.game.settings.Settings;

import java.util.Arrays;

public class Board {
    public static char[][] board = new char[3][3];
    public final Cells cells = new Cells();
    private Player playerOne;
    private Player playerTwo;

    public Board(String playerOne, String playerTwo) {
        for (int i = 0; i < 3; i++) {
            Arrays.fill(board[i], '_');
        }

        if (Settings.fromType(playerOne).equals(Settings.EASY)) {
            this.playerOne = new AI(Settings.EASY, this);

        } else if (Settings.fromType(playerOne).equals(Settings.MEDIUM)) {
            this.playerOne = new DifficultyMedium(Settings.MEDIUM, this);

        } else if (Settings.fromType(playerOne).equals(Settings.HARD)) {
            this.playerOne = new DifficultyHard(Settings.HARD, this);

        } else if (Settings.fromType(playerOne).equals(Settings.USER)) {
            this.playerOne = new Player(Settings.USER, this);
        }

        if (Settings.fromType(playerTwo).equals(Settings.EASY)) {
            this.playerTwo = new AI(Settings.EASY, this);

        } else if (Settings.fromType(playerTwo).equals(Settings.MEDIUM)) {
            this.playerTwo = new DifficultyMedium(Settings.MEDIUM, this);

        } else if (Settings.fromType(playerTwo).equals(Settings.HARD)) {
            this.playerTwo = new DifficultyHard(Settings.HARD, this);

        } else if (Settings.fromType(playerTwo).equals(Settings.USER)) {
            this.playerTwo = new Player(Settings.USER, this);
        }
    }

    public void printBoard() {
        System.out.println("---------");
        for (int i = 0; i < 3; i++) {
            System.out.print("| ");
            for (int j = 0; j < 3; j++) {
                if (board[i][j] == '_') {
                    System.out.print("  ");
                } else {
                    System.out.print(board[i][j] + " ");
                }
            }
            System.out.println("|");
        }
        System.out.print("---------\n");
    }

    public void startGame() {
        printBoard();

        while (true) {

            playerOne.getInput(true);

            if (cells.finalMessage()) {
                return;
            }

            playerTwo.getInput(false);


            if (cells.finalMessage()) {
                return;
            }
        }
    }
}

package tictactoe;

import tictactoe.game.Board;
import tictactoe.game.settings.Settings;

import java.util.Scanner;

public class Main {
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        String playerTwo;
        String playerOne;
        while (true) {
            System.out.print("\nInput command:");
            String[] command = scanner.nextLine().split(" ");

            if (command[0].equalsIgnoreCase("exit")) {
                return;
            }

            if (command.length < 3) {
                System.out.print("Bad parameters!");
                continue;
            }
            playerOne = command[1];
            playerTwo = command[2];

            if (Settings.fromType(playerOne) == null && Settings.fromType(playerTwo) == null) {
                System.out.print("Bad parameters!");
                continue;
            }


            Board board = new Board(playerOne, playerTwo);
            board.startGame();
            System.out.println();
        }
    }
}
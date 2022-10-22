package tictactoe;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {
    public static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        String input = ".........";
        printBoard(input);
        int xOrO = 0;
        while (true) {
            int[] coordinates;
            int y;
            int x;

            coordinates = askForCoordinates(input);
            y = coordinates[0];
            x = coordinates[1];

            if (xOrO % 2 == 0) {
                input = calculateBoardString(input, 'X', y, x);
                input = calculateBoardString(input, 'X', y, x);
                xOrO++;
            } else {
                input = calculateBoardString(input, 'O', y, x);
                input = calculateBoardString(input, 'O', y, x);
                xOrO++;
            }

            printBoard(input);
            if (finalMessage(input)) {
                break;
            }
        }

    }

    public static boolean coordsOutOfRange(int y, int x) {
        return ((y > 3) || (y < 1)) || ((x > 3) || (x < 1));
    }

    public static int[] askForCoordinates(String input) {
        int y = 0;
        int x = 0;

        while (true) {
            System.out.print("Enter the coordinates: ");

            try {
                y = scanner.nextInt();
                x = scanner.nextInt();
            } catch (InputMismatchException e) {
                System.out.println("You should enter numbers!");
                scanner.next();
                continue;
            }

            if (!isEmpty(input, y, x)) {
                System.out.println("This cell is occupied! Choose another one!");
                continue;
            }

            if (coordsOutOfRange(y, x)) {
                System.out.println("Coordinates should be from 1 to 3!");
            } else {
                break;
            }
        }
        return new int[]{y, x};
    }

    public static boolean isEmpty(String input, int y, int x) {
        return input.charAt(getCount(y, x)) == '.';
    }

    public static int getCount(int y, int x) {
        int count = 0;
        if (y == 2) count += 3;
        else if (y == 3) count += 6;

        if (x == 2) count += 1;
        else if (x == 3) count += 2;
        return count;
    }

    public static String calculateBoardString(String originalInput, char charToAdd, int y, int x) {
        StringBuilder builder = new StringBuilder();

        if (!isEmpty(originalInput, y, x)) {
            return originalInput;
        }

        for (int i = 0; i < 9; i++) {
            if (getCount(y, x) == i) {
                builder.append(charToAdd);
                continue;
            }
            builder.append(originalInput.charAt(i));
        }
        return builder.toString();
    }

    public static void printBoard(String input) {
        System.out.println("---------");
        System.out.print("| ");

        for (int i = 0; i < 9; i++) {
            if (input.charAt(i) == '.') {
                System.out.print("  ");
            } else {
                System.out.print(input.charAt(i) + " ");
            }
            if ((i == 2) || (i == 5) || (i == 8)) {
                System.out.println("|");
                if (i != 8) {
                    System.out.print("| ");
                }
            }
        }
        System.out.println("---------");
    }

    public static boolean isThree(int num) {
        return num == 3;
    }

    public static boolean xWins(String input) {
        return crossThreeExists('X', input) || horizontalThreeExists('X', input) || verticalThreeExists('X', input);
    }

    public static boolean owins(String input) {
        return crossThreeExists('O', input) || horizontalThreeExists('O', input) || verticalThreeExists('O', input);
    }

    public static boolean moreThanOther(String input) {
        input = input.replace(".", "");
        int xCount = input.replace("o", "").length();
        int oCount = input.replace("x", "").length();

        if ((oCount - xCount) >= 2) return true;
        else return (xCount - oCount) >= 2;
    }

    public static boolean isImpossible(String input) {
        return owins(input) && xWins(input);
    }

    public static boolean finalMessage(String input) {
        if (isImpossible(input)) {
            System.out.println("Impossible");
            return true;
        } else if (xWins(input)) {
            System.out.println("X wins");
            return true;
        } else if (owins(input)) {
            System.out.println("O wins");
            return true;

        } else if (moreThanOther(input)) {
            System.out.println("Impossible");
            return true;
        }/* else if (input.length() == 9 && input.contains(".")) {
            System.out.println("Game not finished");
        }*/ else if (input.length() == 9 && !input.contains(".")) {
            System.out.println("Draw");
            return true;
        }
        return false;
    }

    public static boolean crossThreeExists(char charToCheck, String input) {
        int count = 0;

        for (int i = 0; i < input.length(); i += 4) {
            if (input.charAt(i) == charToCheck) {
                count++;
            }
        }

        if (isThree(count)) return true;
        else count = 0;

        for (int i = 2; i < 7; i += 2) {
            if (input.charAt(i) == charToCheck) {
                count++;
            }
        }

        return count == 3;
    }

    public static boolean horizontalThreeExists(char charToCheck, String input) {
        int count = 0;

        for (int i = 0; i < 3; i++) {
            if (input.charAt(i) == charToCheck) {
                count++;
            }
        }

        if (isThree(count)) return true;
        else count = 0;

        for (int i = 3; i < 6; i ++) {
            if (input.charAt(i) == charToCheck) {
                count++;
            }
        }

        if (isThree(count)) return true;
        else count = 0;

        for (int i = 6; i < input.length(); i ++) {
            if (input.charAt(i) == charToCheck) {
                count++;
            }
        }

        return count == 3;
    }

    public static boolean verticalThreeExists(char charToCheck, String input) {
        int count = 0;

        for (int i = 0; i < 7; i += 3) {
            if (input.charAt(i) == charToCheck) {
                count++;
            }
        }

        if (isThree(count)) return true;
        else count = 0;

        for (int i = 1; i < 8; i += 3) {
            if (input.charAt(i) == charToCheck) {
                count++;
            }
        }

        if (isThree(count)) return true;
        else count = 0;

        for (int i = 2; i < input.length(); i += 3) {
            if (input.charAt(i) == charToCheck) {
                count++;
            }
        }

        return count == 3;
    }
}
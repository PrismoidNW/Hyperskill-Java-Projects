package battleship;

import java.util.Scanner;

enum ShipType {
    AIRCRAFT(5, 5),
    BATTLESHIP(4, 4),
    SUBMARINE(3, 3),
    CRUISER(3, 3),
    DESTROYER(2, 2);

    int p1Cells;
    int p2Cells;

    ShipType(int p1Cells, int p2Cells) {
        this.p1Cells = p1Cells;
        this.p2Cells = p2Cells;
    }


}

public class Main {

    private static final Scanner scanner = new Scanner(System.in);
    private static String[][] p1Board;
    private static String[][] p2Board;
    private static boolean isFogOfWar = false;
    private static String p1AirCraftCoord;
    private static String p1BattleshipCoord;
    private static String p1SubmarineCoord;
    private static String p1CruiserCoord;
    private static String p1DestroyerCoord;
    private static int[] p1UnsankShips = new int[]{ShipType.AIRCRAFT.p1Cells, ShipType.BATTLESHIP.p1Cells,
            ShipType.SUBMARINE.p1Cells,
            ShipType.CRUISER.p1Cells, ShipType.DESTROYER.p1Cells};

    private static boolean isP1Turn = true;
    private static String p2AirCraftCoord;
    private static String p2BattleshipCoord;
    private static String p2SubmarineCoord;
    private static String p2CruiserCoord;
    private static String p2DestroyerCoord;
    private static int[] p2UnsankShips = new int[]{ShipType.AIRCRAFT.p2Cells, ShipType.BATTLESHIP.p2Cells,
            ShipType.SUBMARINE.p2Cells,
            ShipType.CRUISER.p2Cells, ShipType.DESTROYER.p2Cells};

    public static void main(String[] args) {
        p1Board = new String[][]{
                {" ", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10"},
                {"A", "~", "~", "~", "~", "~", "~", "~", "~", "~", "~"},
                {"B", "~", "~", "~", "~", "~", "~", "~", "~", "~", "~"},
                {"C", "~", "~", "~", "~", "~", "~", "~", "~", "~", "~"},
                {"D", "~", "~", "~", "~", "~", "~", "~", "~", "~", "~"},
                {"E", "~", "~", "~", "~", "~", "~", "~", "~", "~", "~"},
                {"F", "~", "~", "~", "~", "~", "~", "~", "~", "~", "~"},
                {"G", "~", "~", "~", "~", "~", "~", "~", "~", "~", "~"},
                {"H", "~", "~", "~", "~", "~", "~", "~", "~", "~", "~"},
                {"I", "~", "~", "~", "~", "~", "~", "~", "~", "~", "~"},
                {"J", "~", "~", "~", "~", "~", "~", "~", "~", "~", "~"}
        };
        p2Board = new String[][]{
                {" ", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10"},
                {"A", "~", "~", "~", "~", "~", "~", "~", "~", "~", "~"},
                {"B", "~", "~", "~", "~", "~", "~", "~", "~", "~", "~"},
                {"C", "~", "~", "~", "~", "~", "~", "~", "~", "~", "~"},
                {"D", "~", "~", "~", "~", "~", "~", "~", "~", "~", "~"},
                {"E", "~", "~", "~", "~", "~", "~", "~", "~", "~", "~"},
                {"F", "~", "~", "~", "~", "~", "~", "~", "~", "~", "~"},
                {"G", "~", "~", "~", "~", "~", "~", "~", "~", "~", "~"},
                {"H", "~", "~", "~", "~", "~", "~", "~", "~", "~", "~"},
                {"I", "~", "~", "~", "~", "~", "~", "~", "~", "~", "~"},
                {"J", "~", "~", "~", "~", "~", "~", "~", "~", "~", "~"}
        };

        System.out.println("Player 1, place your ships on the game field");
        printBoard();
        getInput();
        switchPlayers();
        System.out.println("Player 2, place your ships to the game field");
        printBoard();
        getInput();
        startGame();
    }

    private static void switchPlayers() {
        isP1Turn = !isP1Turn;
        System.out.println("\nPress Enter and pass the move to another player");
        scanner.nextLine();
    }

    private static void printWholeBoard() {
        if (isP1Turn) {
            isFogOfWar = true;
            isP1Turn = false;
            printBoard();
            System.out.print("---------------------");
            isFogOfWar = false;
            isP1Turn = true;
            printBoard();
        } else {
            isFogOfWar = true;
            isP1Turn = true;
            printBoard();
            System.out.print("---------------------");
            isFogOfWar = false;
            isP1Turn = false;
            printBoard();
        }
    }

    private static void printBoard() {
        System.out.println();
        String[][] board = isP1Turn ? p1Board : p2Board;
        for (String[] chars : board) {
            for (String letter : chars) {
                switch (letter) {
                    case "O": {
                        if (isFogOfWar) {
                            System.out.print("~ ");
                        } else {
                            System.out.print("O ");
                        }
                        break;
                    }
                    case "X": {
                        if (isFogOfWar) {
                            System.out.print("~ ");
                        } else {
                            System.out.print("X ");
                        }
                        break;
                    }
                    case "M": {
                        if (isFogOfWar) {
                            System.out.print("~ ");
                        } else {
                            System.out.print("M ");
                        }
                        break;
                    }
                    default: {
                        System.out.print(letter + " ");
                    }
                }
            }
            System.out.println();
        }

    }

    private static void getAircraftInput() {
        String letter1 = "A";
        String letter2 = "A";
        int coord1Num = 1;
        int coord2Num = 1;
        String coord1;
        String coord2;

        System.out.println("\nEnter the coordinates of the Aircraft Carrier (5 cells):\n");


        while (true) {
            String line = scanner.nextLine();

            if (line.length() < 5) {
                continue;
            }
            coord1 = line.split(" ")[0];
            coord2 = line.split(" ")[1];

            letter1 = coord1.charAt(0) + "";
            letter2 = coord2.charAt(0) + "";

            coord1Num = coord1.length() == 3 ? Integer.parseInt(coord1.charAt(1) + "" + coord1.charAt(2)) :
                    Integer.parseInt(coord1.charAt(1) + "");
            coord2Num = coord2.length() == 3 ? Integer.parseInt(coord2.charAt(1) + "" + coord2.charAt(2)) :
                    Integer.parseInt(coord2.charAt(1) + "");

            if (!isValidCoordinate(letter1, coord1Num) || !isValidCoordinate(letter2, coord2Num)) {
                System.out.println("\nError! Wrong ship location! Try again:");
                continue;
            }
            if (!isError(letter1, coord1Num, letter2, coord2Num, ShipType.AIRCRAFT)) {
                break;
            }
        }
        setCells(letter1, coord1Num, letter2, coord2Num, ShipType.AIRCRAFT);

        printBoard();
    }

    private static void getBattleshipInput() {
        String letter1;
        String letter2;
        int coord1Num;
        int coord2Num;
        String coord1;
        String coord2;

        System.out.println("\nEnter the coordinates of the Battleship (4 cells):\n");

        while (true) {
            String line = scanner.nextLine();

            if (line.length() < 5) {
                System.out.println("Error! You entered the wrong coordinates! Try again:");
                continue;
            }
            coord1 = line.split(" ")[0];
            coord2 = line.split(" ")[1];

            letter1 = coord1.charAt(0) + "";
            letter2 = coord2.charAt(0) + "";

            coord1Num = coord1.length() == 3 ? Integer.parseInt(coord1.charAt(1) + "" + coord1.charAt(2)) :
                    Integer.parseInt(coord1.charAt(1) + "");
            coord2Num = coord2.length() == 3 ? Integer.parseInt(coord2.charAt(1) + "" + coord2.charAt(2)) :
                    Integer.parseInt(coord2.charAt(1) + "");
            if (!isValidCoordinate(letter1, coord1Num) || !isValidCoordinate(letter2, coord2Num)) {
                continue;
            }
            if (!isError(letter1, coord1Num, letter2, coord2Num, ShipType.BATTLESHIP)) {
                break;
            }
        }
        setCells(letter1, coord1Num, letter2, coord2Num, ShipType.BATTLESHIP);

        printBoard();
    }

    private static void getSubmarineInput() {
        String letter1;
        String letter2;
        int coord1Num;
        int coord2Num;
        String coord1;
        String coord2;

        System.out.println("\nEnter the coordinates of the Submarine (3 cells):\n");

        while (true) {
            String line = scanner.nextLine();

            if (line.length() < 5) {
                System.out.println("Error! You entered the wrong coordinates! Try again:");
                continue;
            }
            coord1 = line.split(" ")[0];
            coord2 = line.split(" ")[1];

            letter1 = coord1.charAt(0) + "";
            letter2 = coord2.charAt(0) + "";

            coord1Num = coord1.length() == 3 ? Integer.parseInt(coord1.charAt(1) + "" + coord1.charAt(2)) :
                    Integer.parseInt(coord1.charAt(1) + "");
            coord2Num = coord2.length() == 3 ? Integer.parseInt(coord2.charAt(1) + "" + coord2.charAt(2)) :
                    Integer.parseInt(coord2.charAt(1) + "");
            if (!isValidCoordinate(letter1, coord1Num) || !isValidCoordinate(letter2, coord2Num)) {
                continue;
            }
            if (!isError(letter1, coord1Num, letter2, coord2Num, ShipType.SUBMARINE)) {
                break;
            }
        }
        setCells(letter1, coord1Num, letter2, coord2Num, ShipType.SUBMARINE);

        printBoard();
    }

    private static void getCruiserInput() {
        String letter1;
        String letter2;
        int coord1Num;
        int coord2Num;
        String coord1;
        String coord2;

        System.out.println("\nEnter the coordinates of the Cruiser (3 cells):\n");

        while (true) {
            String line = scanner.nextLine();

            if (line.length() < 5) {
                System.out.println("Error! You entered the wrong coordinates! Try again:");
                continue;
            }
            coord1 = line.split(" ")[0];
            coord2 = line.split(" ")[1];

            letter1 = coord1.charAt(0) + "";
            letter2 = coord2.charAt(0) + "";

            coord1Num = coord1.length() == 3 ? Integer.parseInt(coord1.charAt(1) + "" + coord1.charAt(2)) :
                    Integer.parseInt(coord1.charAt(1) + "");
            coord2Num = coord2.length() == 3 ? Integer.parseInt(coord2.charAt(1) + "" + coord2.charAt(2)) :
                    Integer.parseInt(coord2.charAt(1) + "");
            if (!isValidCoordinate(letter1, coord1Num) || !isValidCoordinate(letter2, coord2Num)) {
                continue;
            }
            if (!isError(letter1, coord1Num, letter2, coord2Num, ShipType.CRUISER)) {
                break;
            }
        }
        setCells(letter1, coord1Num, letter2, coord2Num, ShipType.CRUISER);

        printBoard();
    }

    private static void getDestroyerInput() {
        String letter1;
        String letter2;
        int coord1Num;
        int coord2Num;
        String coord1;
        String coord2;

        System.out.println("\nEnter the coordinates of the Destroyer (2 cells):\n");

        while (true) {
            String line = scanner.nextLine();

            if (line.length() < 5) {
                System.out.println("Error! You entered the wrong coordinates! Try again:");
                continue;
            }
            coord1 = line.split(" ")[0];
            coord2 = line.split(" ")[1];

            letter1 = coord1.charAt(0) + "";
            letter2 = coord2.charAt(0) + "";

            coord1Num = coord1.length() == 3 ? Integer.parseInt(coord1.charAt(1) + "" + coord1.charAt(2)) :
                    Integer.parseInt(coord1.charAt(1) + "");
            coord2Num = coord2.length() == 3 ? Integer.parseInt(coord2.charAt(1) + "" + coord2.charAt(2)) :
                    Integer.parseInt(coord2.charAt(1) + "");
            if (!isValidCoordinate(letter1, coord1Num) || !isValidCoordinate(letter2, coord2Num)) {
                continue;
            }
            if (!isError(letter1, coord1Num, letter2, coord2Num, ShipType.DESTROYER)) {
                break;
            }
        }
        setCells(letter1, coord1Num, letter2, coord2Num, ShipType.DESTROYER);

        printBoard();
    }

    private static void getInput() {
        getAircraftInput();
        getBattleshipInput();
        getSubmarineInput();
        getCruiserInput();
        getDestroyerInput();
    }

    private static void setCells(String letter1, int number1, String letter2, int number2, ShipType type) {
        int letter1Pos = getLetterIndex(letter1);
        int letter2Pos = getLetterIndex(letter2);
        StringBuilder coordinates = new StringBuilder();
        String[][] board = isP1Turn ? p1Board : p2Board;

        if (isVertical(letter1, number1, letter2, number2)) {
            for (int i = Math.min(letter1Pos, letter2Pos); i <= Math.max(letter1Pos, letter2Pos); i++) {
                board[i][number1] = "O";
                coordinates.append(board[i][0]).append(number1).append(" ");
            }
        } else {
            for (int i = Math.min(number1, number2); i <= Math.max(number1, number2); i++) {
                board[letter1Pos][i] = "O";
                coordinates.append(letter1).append(i).append(" ");
            }
        }

        if (isP1Turn) {
            switch (type) {
                case AIRCRAFT: {
                    p1AirCraftCoord = coordinates.toString();
                    break;
                }
                case BATTLESHIP: {
                    p1BattleshipCoord = coordinates.toString();
                    break;
                }
                case SUBMARINE: {
                    p1SubmarineCoord = coordinates.toString();
                    break;
                }
                case CRUISER: {
                    p1CruiserCoord = coordinates.toString();
                    break;
                }
                case DESTROYER: {
                    p1DestroyerCoord = coordinates.toString();
                    break;
                }
            }
        } else {
            switch (type) {
                case AIRCRAFT: {
                    p2AirCraftCoord = coordinates.toString();
                    break;
                }
                case BATTLESHIP: {
                    p2BattleshipCoord = coordinates.toString();
                    break;
                }
                case SUBMARINE: {
                    p2SubmarineCoord = coordinates.toString();
                    break;
                }
                case CRUISER: {
                    p2CruiserCoord = coordinates.toString();
                    break;
                }
                case DESTROYER: {
                    p2DestroyerCoord = coordinates.toString();
                    break;
                }
            }
        }
    }

    private static void setMiss(String letter, int number) {
        int letterIndex = getLetterIndex(letter);
        String[][] board = isP1Turn ? p2Board : p1Board;
        board[letterIndex][number] = "M";
    }

    private static int isSunk() {
        if (!isP1Turn) {
            int count = 0;
            for (int i : p1UnsankShips) {
                System.out.println("I = " + i);
                if (i == 0) {
                    count++;
                }
            }
            if (count == 5) {
                return 1;
            }
            System.out.println(count);
        } else {
            int count = 0;
            for (int i : p2UnsankShips) {
                System.out.println("I = " + i);
                if (i == 0) {
                    count++;
                }
            }
            System.out.println(count);
            if (count == 5) {
                return 1;
            }
        }

        return -1;
    }

    private static int fireWeapon(String letter, int number) {
        int letterIndex = getLetterIndex(letter);
        String[][] board = isP1Turn ? p2Board : p1Board;

        if (board[letterIndex][number].equals("~")) {
            setMiss(letter, number);
            System.out.print("\nYou missed!");
        } else if (board[letterIndex][number].equals("O")) {
            setHit(letter, number);
            return decrementCellCount(letter, number);
        }
        return -3;
    }

    private static void startGame() {
        String letter;
        int number;

        while (true) {
            switchPlayers();
            printWholeBoard();
            String[][] board = isP1Turn ? p2Board : p1Board;

            if (isP1Turn) {
                System.out.println("\nPlayer 1, it's your turn:\n");
            } else {
                System.out.println("\nPlayer 2, it's your turn:\n");
            }

            String input = scanner.nextLine();

            if (input.length() < 2) {
                System.out.println("\nError! You entered the wrong coordinates! Try again:\n");
                continue;
            }

            letter = input.charAt(0) + "";

            number = input.length() == 3 ? Integer.parseInt(input.charAt(1) + "" + input.charAt(2)) :
                    Integer.parseInt(input.charAt(1) + "");

            if (isValidCoordinate(letter, number)) {
                int status = fireWeapon(letter, number);
                if (isSunk() == 1) {
                    System.out.println("\nYou sank the last ship. You won. Congratulations!");
                    break;
                } else if (status == -2) {
                    System.out.println("\nYou hit a ship!");
                } else if (status == 0) {
                    System.out.println("\nYou sank a ship!\n");
                }
            }
        }
    }

    private static void setHit(String letter, int number) {
        int letterIndex = getLetterIndex(letter);
        String[][] board = isP1Turn ? p2Board : p1Board;
        board[letterIndex][number] = "X";
    }

    public static int getLetterIndex(String letter) {
        for (int i = 1; i < p1Board.length; i++) {
            if (letter.equalsIgnoreCase(p1Board[i][0])) {
                return i;
            }
        }
        return 0;
    }

    private static int decrementCellCount(String letter, int number) {
        String coord = letter + number;
        if (!isP1Turn) {
            if (p1AirCraftCoord.contains(coord)) {
                ShipType.AIRCRAFT.p1Cells--;
                p1UnsankShips[0] = ShipType.AIRCRAFT.p1Cells;
                if (ShipType.AIRCRAFT.p1Cells == 0) {
                    return 0;
                }
            } else if (p1BattleshipCoord.contains(coord)) {
                ShipType.BATTLESHIP.p1Cells--;
                p1UnsankShips[1] = ShipType.BATTLESHIP.p1Cells;
                if (ShipType.BATTLESHIP.p1Cells == 0) {
                    return 0;
                }
            } else if (p1SubmarineCoord.contains(coord)) {
                ShipType.SUBMARINE.p1Cells--;
                p1UnsankShips[2] = ShipType.SUBMARINE.p1Cells;
                if (ShipType.SUBMARINE.p1Cells == 0) {
                    return 0;
                }
            } else if (p1CruiserCoord.contains(coord)) {
                ShipType.CRUISER.p1Cells--;
                p1UnsankShips[3] = ShipType.CRUISER.p1Cells;
                if (ShipType.CRUISER.p1Cells == 0) {
                    return 0;
                }
            } else if (p1DestroyerCoord.contains(coord)) {
                ShipType.DESTROYER.p1Cells--;
                p1UnsankShips[4] = ShipType.DESTROYER.p1Cells;
                if (ShipType.DESTROYER.p1Cells == 0) {
                    return 0;
                }
            }
        } else {
            if (p2AirCraftCoord.contains(coord)) {
                ShipType.AIRCRAFT.p2Cells--;
                p2UnsankShips[0] = ShipType.AIRCRAFT.p2Cells;
                if (ShipType.AIRCRAFT.p2Cells == 0) {
                    return 0;
                }
            } else if (p2BattleshipCoord.contains(coord)) {
                ShipType.BATTLESHIP.p2Cells--;
                p2UnsankShips[1] = ShipType.BATTLESHIP.p2Cells;
                if (ShipType.BATTLESHIP.p2Cells == 0) {
                    return 0;
                }
            } else if (p2SubmarineCoord.contains(coord)) {
                ShipType.SUBMARINE.p2Cells--;
                p2UnsankShips[2] = ShipType.SUBMARINE.p2Cells;
                if (ShipType.SUBMARINE.p2Cells == 0) {
                    return 0;
                }
            } else if (p2CruiserCoord.contains(coord)) {
                ShipType.CRUISER.p2Cells--;
                p2UnsankShips[3] = ShipType.CRUISER.p2Cells;
                if (ShipType.CRUISER.p2Cells == 0) {
                    return 0;
                }
            } else if (p2DestroyerCoord.contains(coord)) {
                ShipType.DESTROYER.p2Cells--;
                p2UnsankShips[4] = ShipType.DESTROYER.p2Cells;
                if (ShipType.DESTROYER.p2Cells == 0) {
                    return 0;
                }
            }
        }
        return -2;
    }

    private static boolean isVertical(String letter1, int number1, String letter2, int number2) {
        return !letter1.equalsIgnoreCase(letter2) && (number1 == number2);
    }

    private static boolean isTooClose(String letter1, int number1, String letter2, int number2) {
        int letter1Pos = getLetterIndex(letter1);
        int letter2Pos = getLetterIndex(letter2);

        String[][] board = isP1Turn ? p1Board : p2Board;

        if (isVertical(letter1, number1, letter2, number2)) {
            int posUp = (Math.min(letter1Pos, letter2Pos) - 1) <= 0 ? Math.min(letter1Pos, letter2Pos) :
                    (Math.min(letter1Pos, letter2Pos) - 1);
            int posDown = (Math.max(letter1Pos, letter2Pos) + 1) >= 11 ? Math.max(letter1Pos, letter2Pos) :
                    Math.max(letter1Pos, letter2Pos) + 1;
            for (int i = posUp; i <= posDown; i++) {
                if (board[i][number1].equals("O")) {
                    System.out.println("\nError! You placed it too close to another one. Try again:\n");
                    return true;
                }
            }
        } else {
            for (int i = Math.min(number1, number2); i <= Math.max(number1, number2); i++) {
                if (board[letter1Pos][i].equals("O")) {
                    System.out.println("\nError! You placed it too close to another one. Try again:\n");
                    return true;
                }
            }
        }
        return false;
    }

    private static boolean isValidCoordinate(String letter, int number) {
        String[][] board = isP1Turn ? p1Board : p2Board;

        for (String[] strings : board) {
            if (strings[0].equals(letter) && (number <= 10 && number >= 1) && (getLetterIndex(letter) != 0)) {
                return true;
            }
        }
        return false;
    }

    private static boolean isError(String letter1, int number1, String letter2, int number2, ShipType type) {
        if ((number1 != number2 && !letter1.equalsIgnoreCase(letter2))) {
            System.out.println("\nError! Wrong ship location! Try again:\n");
            return true;
        }

        if (isTooClose(letter1, number1, letter2, number2)) {
            return true;
        }

        int letter1Pos = getLetterIndex(letter1);
        int letter2Pos = getLetterIndex(letter2);

        if (isVertical(letter1, number1, letter2, number2)) {
            switch (type) {
                case AIRCRAFT: {
                    if ((Math.abs(letter1Pos - letter2Pos) + 1) != 5) {
                        System.out.println("\nError! Wrong length of the Aircraft Carrier! Try again:\n");
                        return true;
                    }
                    break;
                }
                case BATTLESHIP: {
                    if ((Math.abs(letter1Pos - letter2Pos) + 1) != 4) {
                        System.out.println("\nError! Wrong length of the Battleship! Try again:\n");
                        return true;
                    }
                    break;
                }
                case SUBMARINE: {
                    if ((Math.abs(letter1Pos - letter2Pos) + 1) != 3) {
                        System.out.println("\nError! Wrong length of the Submarine! Try again:\n");
                        return true;
                    }
                    break;
                }
                case CRUISER: {
                    if ((Math.abs(letter1Pos - letter2Pos) + 1) != 3) {
                        System.out.println("\nError! Wrong length of the Cruiser! Try again:\n");
                        return true;
                    }
                    break;
                }
                case DESTROYER: {
                    if ((Math.abs(letter1Pos - letter2Pos) + 1) != 2) {
                        System.out.println("\nError! Wrong length of the Destroyer! Try again:\n");
                        return true;
                    }
                    break;
                }
            }
        } else {
            switch (type) {
                case AIRCRAFT: {
                    if ((Math.abs(number1 - number2) + 1) != 5) {
                        System.out.println("\nError! Wrong length of the Aircraft Carrier! Try again:\n");
                        return true;
                    }
                    break;
                }
                case BATTLESHIP: {
                    if ((Math.abs(number1 - number2) + 1) != 4) {
                        System.out.println("\nError! Wrong length of the Battleship! Try again:\n");
                        return true;
                    }
                    break;
                }
                case SUBMARINE: {
                    if ((Math.abs(number1 - number2) + 1) != 3) {
                        System.out.println("\nError! Wrong length of the Submarine! Try again:\n");
                        return true;
                    }
                    break;
                }
                case CRUISER: {
                    if ((Math.abs(number1 - number2) + 1) != 3) {
                        System.out.println("\nError! Wrong length of the Cruiser! Try again:\n");
                        return true;
                    }
                    break;
                }
                case DESTROYER: {
                    if ((Math.abs(number1 - number2) + 1) != 2) {
                        System.out.println("\nError! Wrong length of the Destroyer! Try again:\n");
                        return true;
                    }
                    break;
                }
            }
        }
        return false;
    }
}
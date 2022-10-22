package tictactoe.game;

public class Cells {

    public boolean isEmpty(int y, int x) {
        return Board.board[y][x] == '_';
    }

    public boolean isBoardEmpty() {
        int count = 0;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (isEmpty(i, j)) {
                    count++;
                }
            }
        }
        return count == 0;
    }

    public boolean coordsOutOfRange(int y, int x) {
        return ((y > 3) || (y < 1)) || ((x > 3) || (x < 1));
    }

    public boolean xWins() {
        return crossThreeExists('X') || horizontalThreeExists('X') || verticalThreeExists('X');
    }

    public boolean oWins() {
        return crossThreeExists('O') || horizontalThreeExists('O') || verticalThreeExists('O');
    }

    public int checkWinner() {
        if (xWins()) {
            return 10;
        } else if (oWins()) {
            return -10;
        } else if (!xWins() && !oWins() && isBoardEmpty()) {
            return 0;
        }
        return -111;
    }

    public boolean finalMessage() {
        int checkWinner = checkWinner();
        switch (checkWinner) {
            case 10: {
                System.out.print("X wins");
                return true;
            }
            case -10: {
                System.out.print("O wins");
                return true;
            }
            case 0: {
                System.out.print("Draw");
                return true;
            }
        }
        return false;
    }

    public boolean crossThreeExists(char charToCheck) {
        int count = 0;
        int y = 0;
        int x = 0;


        boolean isFirstDone = false;

        for (int i = 0; i < 2; i++) {
            if (!isFirstDone) {
                for (int j = 0; j < 3; j++) {
                    if (y == 3 || x == 3) {
                        break;
                    }
                    if (Board.board[y][x] == charToCheck) {
                        count++;
                    }
                    x++;
                    y++;
                }
                if (count == 3) return true;
            } else {
                x = 2;
                y = 0;
                count = 0;
                for (int j = 0; j < 3; j++) {
                    if (y == 3 || x == 3) {
                        break;
                    }
                    if (Board.board[y][x] == charToCheck) {
                        count++;
                    }
                    x--;
                    y++;
                }
            }
            isFirstDone = true;
        }
        return count == 3;
    }

    public boolean horizontalThreeExists(char charToCheck) {
        int count = 0;

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (Board.board[i][j] == charToCheck) {
                    count++;
                }
            }
            if (count == 3) {
                return true;
            } else {
                count = 0;
            }
        }

        return false;
    }

    public boolean verticalThreeExists(char charToCheck) {
        int count = 0;

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (Board.board[j][i] == charToCheck) {
                    count++;
                }
            }
            if (count == 3) {
                return true;
            } else {
                count = 0;
            }
        }
        return false;
    }
}

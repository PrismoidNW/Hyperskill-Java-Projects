package tictactoe.game.players;

import tictactoe.game.Board;
import tictactoe.game.Coordinates;
import tictactoe.game.settings.Settings;

import java.util.HashSet;
import java.util.Set;

public class AI extends Player {

    public AI(Settings type, Board board) {
        super(type, board);
    }

    public boolean crossOneSpotWin(char charToCheck, boolean opposite) {
        char oppositeOpponent = charToCheck;
        Set<Coordinates> coordinates;

        if (opposite) {
            oppositeOpponent = charToCheck == 'X' ? 'O' : 'X';
        }

        coordinates = checkCrossCoordinates(oppositeOpponent);

        if (coordinates.size() == 3) {
            for (Coordinates coordinate : coordinates) {
                if (!coordinate.isOccupied()) {
                    this.setSpot(coordinate.getX(), coordinate.getY(), charToCheck);
                    return true;
                }
            }
        }
        return false;
    }

    public boolean horizontalOneSpotWin(char charToCheck, boolean opposite) {
        char oppositeOpponent = charToCheck;
        Set<Coordinates> coordinates;

        if (opposite) {
            oppositeOpponent = charToCheck == 'X' ? 'O' : 'X';
        }
        coordinates = checkHorizontalCoordinates(oppositeOpponent);

        if (coordinates.size() == 3) {
            for (Coordinates coordinate : coordinates) {
                if (!coordinate.isOccupied()) {
                    setSpot(coordinate.getX(), coordinate.getY(), charToCheck);
                    return true;
                }
            }
        }
        return false;
    }

    public boolean verticalOneSpotWin(char charToCheck, boolean opposite) {
        char oppositeOpponent = charToCheck;
        Set<Coordinates> coordinates;

        if (opposite) {
            oppositeOpponent = charToCheck == 'X' ? 'O' : 'X';
        }
        coordinates = checkVerticalCoordinates(oppositeOpponent);

        if (coordinates.size() == 3) {
            for (Coordinates coordinate : coordinates) {
                if (!coordinate.isOccupied()) {
                    setSpot(coordinate.getX(), coordinate.getY(), charToCheck);
                    System.out.println(coordinate.getX() + ", " + coordinate.getY() + ", " + coordinate.isOccupied());
                    return true;
                }
            }
        }
        return false;
    }

    public boolean canWinWithOneMove(char charToCheck, boolean opposite) {
        if (crossOneSpotWin(charToCheck, opposite)) return true;
        if (horizontalOneSpotWin(charToCheck, opposite)) return true;
        return verticalOneSpotWin(charToCheck, opposite);
    }

    public boolean canBlockAttack(char charToCheck) {
        return canWinWithOneMove(charToCheck, true);
    }

    private boolean hasMultipleEmptySpots(Set<Coordinates> coordsToCheck) {
        int spots = 0;

        for (Coordinates coords : coordsToCheck) {
            if (!coords.isOccupied()) {
                spots++;
            }
        }
        return spots > 1;
    }

    private int countOccupiedSpots(Set<Coordinates> coordsToCheck) {
        int spots = 0;
        for (Coordinates coords : coordsToCheck) {
            if (coords.isOccupied()) {
                spots++;
            }
        }
        return spots;
    }

    public Set<Coordinates> checkCrossCoordinates(char charToCheck) {
        Set<Coordinates> coords = new HashSet<>();
        int y = 0;
        int x = 0;

        for (int j = 0; j < 3; j++) {
            if (Board.board[y][x] == charToCheck) {
                coords.add(new Coordinates(x, y, true));
            } else if (getBoard().cells.isEmpty(y, x)) {
                coords.add(new Coordinates(x, y, false));
            }
            x++;
            y++;
        }
        if (!hasMultipleEmptySpots(coords) && countOccupiedSpots(coords) == 2) {
            return coords;
        } else {
            coords = new HashSet<>();
        }

        x = 2;
        y = 0;
        for (int j = 0; j < 3; j++) {
            if (Board.board[y][x] == charToCheck) {
                coords.add(new Coordinates(x, y, true));
            } else if (getBoard().cells.isEmpty(y, x)) {
                coords.add(new Coordinates(x, y, false));
            }
            x--;
            y++;
        }

        if (!hasMultipleEmptySpots(coords) && countOccupiedSpots(coords) == 2) {
            return coords;
        }

        return new HashSet<>();
    }

    public Set<Coordinates> checkHorizontalCoordinates(char charToCheck) {
        Set<Coordinates> coords = new HashSet<>();

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (Board.board[i][j] == charToCheck) {
                    coords.add(new Coordinates(j, i, true));
                } else if (getBoard().cells.isEmpty(i, j)) {
                    coords.add(new Coordinates(j, i, false));
                }
            }
            if (hasMultipleEmptySpots(coords)) {
                coords = new HashSet<>();
            } else if (!hasMultipleEmptySpots(coords) && countOccupiedSpots(coords) == 2) {
                break;
            }
        }
        return coords;
    }

    public Set<Coordinates> checkVerticalCoordinates(char charToCheck) {
        Set<Coordinates> coords = new HashSet<>();

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (Board.board[j][i] == charToCheck) {
                    coords.add(new Coordinates(j, i, true));
                } else if (getBoard().cells.isEmpty(i, j)) {
                    coords.add(new Coordinates(j, i, false));
                }
            }
            if (hasMultipleEmptySpots(coords)) {
                coords = new HashSet<>();
            } else if (!hasMultipleEmptySpots(coords) && countOccupiedSpots(coords) == 2) {
                break;
            }
        }
        return coords;
    }

    @Override
    public void getInput(boolean isFirstPlayer) {

        System.out.println("Making move level \"easy\"");
        int x;
        int y;
        do {
            x = random.nextInt(0, 3);
            y = random.nextInt(0, 3);
        } while (!getBoard().cells.isEmpty(y, x));

        if (isFirstPlayer)
            setSpot(x, y, 'X');
        else
            setSpot(x, y, 'O');

        getBoard().printBoard();
    }
}

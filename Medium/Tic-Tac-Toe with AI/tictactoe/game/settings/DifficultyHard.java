package tictactoe.game.settings;

import tictactoe.game.Board;
import tictactoe.game.Coordinates;
import tictactoe.game.players.AI;

import java.util.ArrayList;
import java.util.List;

public class DifficultyHard extends AI {
    Coordinates aiMove;
    private Coordinates computerMove;

    /*private void bestMove(boolean isFirstPlayer) {
        int bestScore = Integer.MIN_VALUE;
        Coordinates bestPlay = null;
        char player = isFirstPlayer ? 'X' : 'O';


        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (Board.board[j][i] == '_') {
                    Coordinates coordinate = new Coordinates(i, j, false);
                    Board.board[j][i] = player;
                    int score = miniMax(Board.board, 0, isFirstPlayer);
                    Board.board[j][i] = '_';
                    if (score > bestScore) {
                        bestScore = score;
                        bestPlay = coordinate;
                    }
                }
            }
        }
        setSpot(bestPlay.getX(), bestPlay.getY(), player);
    }

    private int miniMax(char[][] board, int depth, boolean isMaximizing) {
        int result = getBoard().cells.checkWinner();
        if (result != -111) return result;

        int bestScore;
        if (isMaximizing) {
            bestScore = Integer.MIN_VALUE;
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    if (board[j][i] == '_') {
                        board[j][i] = 'X';
                        int score = miniMax(board, depth + 1, false);
                        board[j][i] = '_';
                        bestScore = Math.max(score, bestScore);
                    }
                }
            }
        } else {
            bestScore = Integer.MAX_VALUE;
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    if (board[j][i] == '_') {
                        board[j][i] = 'O';
                        int score = miniMax(board, depth + 1, true);
                        board[j][i] = '_';
                        bestScore = Math.min(score, bestScore);
                    }
                }
            }
        }
        return bestScore;
    }*/

    public DifficultyHard(Settings type, Board board) {
        super(type, board);
    }

    private int miniMax(int depth, boolean isFirstPlayer) {
        int result = getBoard().cells.checkWinner();
        if (result != -111) return result;

        List<Coordinates> pointsAvailable = getAvailableIndexes();
        if (pointsAvailable.isEmpty()) return 0;

        int min = Integer.MAX_VALUE;
        int max = Integer.MIN_VALUE;

        for (int i = 0; i < pointsAvailable.size(); ++i) {
            Coordinates pt = pointsAvailable.get(i);
            if (isFirstPlayer) {
                setSpot(pt.getX(), pt.getY(), 'X');
                int currentScore = miniMax(depth + 1, false);
                max = Math.max(currentScore, max);
                if (currentScore >= 0) {
                    if (depth == 0) {
                        computerMove = pt;
                    }
                }
                if (currentScore == 1) {
                    setSpot(pt.getX(), pt.getY(), '_');
                    break;
                }
                if (i == pointsAvailable.size() - 1 && max < 0) {
                    if (depth == 0) {
                        computerMove = pt;
                    }
                }
            } else {
                setSpot(pt.getX(), pt.getY(), 'O');
                int currentScore = miniMax(depth + 1, true);
                min = Math.min(currentScore, min);
                if (min == -1) {
                    setSpot(pt.getX(), pt.getY(), '_');
                    break;
                }
                if (currentScore <= 0) {
                    if (depth == 0) {
                        computerMove = pt;
                    }
                }
                if (currentScore == 1) {
                    setSpot(pt.getX(), pt.getY(), '_');
                    break;
                }
            }
            setSpot(pt.getX(), pt.getY(), '_');
        }
        return isFirstPlayer ? max : min;
    }


    private int returnNextMove(boolean isFirstPlayer) {
        if (getBoard().cells.checkWinner() != -111) return -1;
        miniMax(0, isFirstPlayer);
        return 10;
    }

    private List<Coordinates> getAvailableIndexes() {
        List<Coordinates> coordinates = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (Board.board[i][j] == '_') {
                    coordinates.add(new Coordinates(j, i, false));
                }
            }
        }
        return coordinates;
    }

    @Override
    public void getInput(boolean isFirstPlayer) {
        System.out.println("Making Coordinates level \"hard\"");
        //bestMove(isFirstPlayer);
        if (returnNextMove(isFirstPlayer) == 10) {
            if (computerMove != null) {
                int x = computerMove.getX();
                int y = computerMove.getY();
                if (getBoard().cells.isEmpty(y, x)) {
                    setSpot(x, y, isFirstPlayer ? 'X' : 'O');
                }
            }
        }
        getBoard().printBoard();
    }
}


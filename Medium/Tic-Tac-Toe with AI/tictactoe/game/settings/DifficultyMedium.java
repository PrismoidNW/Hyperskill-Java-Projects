package tictactoe.game.settings;

import tictactoe.game.Board;
import tictactoe.game.players.AI;

public class DifficultyMedium extends AI {

    public DifficultyMedium(Settings type, Board board) {
        super(type, board);
    }

    @Override
    public void getInput(boolean isFirstPlayer) {
        System.out.println("Making move level \"medium\"");
        int x;
        int y;
        if (isFirstPlayer) {
            if (canBlockAttack('X')) {
            } else if (canWinWithOneMove('X', false)) {
            } else {
                do {
                    x = random.nextInt(0, 3);
                    y = random.nextInt(0, 3);
                } while (!getBoard().cells.isEmpty(y, x));
                setSpot(x, y, 'X');
            }
        } else {
            if (canBlockAttack('O')) {
            } else if (canWinWithOneMove('O', false)) {
            } else {
                do {
                    x = random.nextInt(0, 3);
                    y = random.nextInt(0, 3);
                } while (!getBoard().cells.isEmpty(y, x));
                setSpot(x, y, 'O');
            }
        }
        getBoard().printBoard();
    }
}

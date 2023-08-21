package Engine;

import javax.swing.SwingUtilities;

import Graphics.UI;

public class Game implements MoveListener {

    private final UI ui;
    private Board board;
    private Symbol currentPlayer;

    public Game() {
        this.ui = new UI();
        this.board = new Board();
        this.currentPlayer = Symbol.CROSS;
        this.ui.setMoveListener(this);
    }

    public Board board() {
        return this.board;
    }

    public void setBoard(Board board) {
        this.board = board;
    }

    @Override
    public void onMoveSelected(int index) {
        if (board.gameIsOver()) return;
        Move recentMove = new Move(index, currentPlayer);
        play(recentMove);        

        SwingUtilities.invokeLater(() -> {
            AI ai = new AI();
        //Move aiM = ai.getAIMove(this, currentPlayer, AI.MAX_DEPTH); //MAKES GAME STUCK
        //System.out.println(	aiM.index());
        });
    }

    public void start() {
        ui.startUI();
    }

    public void play(Move move) {
        if (!board.moveIsPossible(move.index())) {
            throw new IllegalArgumentException("Move is not possible, index: " + move.index()+ " is already "+ board.getMarks()[move.index()].symbol());
        }
        board.setMarkAt(move.index(), move.symbol());
        ui.updateUI(this.board);
        if (board.gameIsOver()) {
            ui.announceWinner(board);
            return;
        }
        this.currentPlayer = currentPlayer == Symbol.CROSS ? Symbol.CIRCLE : Symbol.CROSS;
        ui.setCurrentPlayerLabel(currentPlayer);
    }
}

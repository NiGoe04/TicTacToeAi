package Engine;

import javax.swing.SwingWorker;

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
        if (board.gameIsOver())
            return;
        Move recentMove = new Move(index, currentPlayer);
        play(recentMove);

        SwingWorker<Move,Void> worker = new SwingWorker<>() {
            @Override
            protected Move doInBackground() {
                System.out.println("AI is thinking...");
                AI ai = new AI();
                Move move = ai.getAIMove(Game.this, currentPlayer, AI.MAX_DEPTH);
                System.out.println("AI is done thinking...");
                return move;
            }

            @Override
            protected void done() {
                try {
                    Move aiMove = get();
                    if (aiMove == null) {
                        throw new Exception("AI returned null move");
                    }
                    System.out.println("AI move: " + aiMove.index());
                    play(aiMove);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
        worker.execute();
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

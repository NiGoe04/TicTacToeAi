package Engine;

public class AI {
    private int counter;
    public static final int MAX_DEPTH = 9;

    public Move getAIMove(Game game, Symbol symbol, int depth) {
        assert(symbol == Symbol.CROSS || symbol == Symbol.CIRCLE);
        int bestScore;
        int bestMoveIndex = 0;
        if (symbol == Symbol.CROSS) bestScore = Integer.MIN_VALUE; else bestScore = Integer.MAX_VALUE;
        for (int i = 0; i < game.board().getMarks().length; i++) {
            int currentScore = 0;
            if (game.board().getMarks()[i].symbol() == Symbol.EMPTY) {
                Board copy = new Board(game.board());
                copy.setMarkAt(i, symbol);
                currentScore = minimax(copy, depth, Integer.MIN_VALUE, Integer.MAX_VALUE, symbol == Symbol.CROSS);
            }
            if (symbol == Symbol.CROSS) {
                if (currentScore > bestScore) {
                    bestScore = currentScore;
                    bestMoveIndex = i;
                }
            } else {
                if (currentScore < bestScore) {
                    bestScore = currentScore;
                    bestMoveIndex = i;
                }
            }
        }
        System.out.println("counter: " + counter); //for debugging purposes of minimax
        return new Move(bestMoveIndex, symbol);
    }

    private int evaluate(Board board) { //cross maximizes, circle minimizes
        if (board.crossHasWon()) return 10;
        else if (board.circleHasWon()) return -10;
        else return 0;
    }

    private int minimax(Board board, int depth, int alpha, int beta, boolean maximizingPlayer) {  //maximizing player is CROSS
        int maxEval, minEval;
        counter++;
        if (depth == 0 || board.gameIsOver()) return evaluate(board);

        if (maximizingPlayer) {
            maxEval = Integer.MIN_VALUE;
            for (int i = 0; i < board.getMarks().length; i ++) {
                if (board.getMarks()[i].symbol() == Symbol.EMPTY) {
                    Board copy = new Board(board);
                    copy.setMarkAt(i, Symbol.CROSS);
                    int eval = minimax(copy, depth-1, alpha, beta, false);
                    maxEval = Math.max(maxEval, eval);
                    alpha = Math.max(alpha, eval);
                    if (beta <= alpha) break; //pruning
                }
            }
            return maxEval;
        } else {
            minEval = Integer.MAX_VALUE;
            for (int i = 0; i < board.getMarks().length; i ++) {
                if (board.getMarks()[i].symbol() == Symbol.EMPTY) {
                    Board copy = new Board(board);
                    copy.setMarkAt(i, Symbol.CIRCLE);
                    int eval = minimax(copy, depth-1, alpha, beta, true);
                    minEval = Math.min(minEval, eval);
                    beta = Math.min(beta, eval);
                    if (beta <= alpha) break; //pruning
                }
            }
            return minEval;
        }
    }
}

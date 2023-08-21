package Engine;

public class Board {
    private Mark[] marks = new Mark[9];

    public Board() {
        initialiseBoard();
    }

    public Board(Board board) {
        // deep copy due to inner mutation in setMarkAt
        for (int i = 0; i < board.marks.length; i++) {
            this.marks[i] = new Mark(i, board.marks[i].symbol());
        }
    }
    private void initialiseBoard() {
        for (int i = 0; i < 9; i++) {
            marks[i] = new Mark(i, Symbol.EMPTY);
        }
    }
    public Mark[] getMarks() {
        return this.marks;
    }

    public boolean moveIsPossible(int index) throws IllegalArgumentException {
        if (index < 0 || index > 8) throw new IllegalArgumentException("Invalid board index");
        return marks[index].symbol() == Symbol.EMPTY;
    }

    public void setMarkAt(int index, Symbol symbol) {
        assert symbol != null;
        this.marks[index].setSymbol(symbol);
    }

    public boolean crossHasWon() {
        boolean b;
        b = (marks[0].symbol() == Symbol.CROSS && //row 1 win
            marks[1].symbol() == Symbol.CROSS &&
            marks[2].symbol() == Symbol.CROSS) ||

            (marks[3].symbol() == Symbol.CROSS && //row 2 win
            marks[4].symbol() == Symbol.CROSS &&
            marks[5].symbol() == Symbol.CROSS) ||

            (marks[6].symbol() == Symbol.CROSS && //row 3 win
            marks[7].symbol() == Symbol.CROSS &&
            marks[8].symbol() == Symbol.CROSS) ||

            (marks[0].symbol() == Symbol.CROSS && //col 1 win
            marks[3].symbol() == Symbol.CROSS &&
            marks[6].symbol() == Symbol.CROSS) ||

            (marks[1].symbol() == Symbol.CROSS && //col 2 win
            marks[4].symbol() == Symbol.CROSS &&
            marks[7].symbol() == Symbol.CROSS) ||

            (marks[2].symbol() == Symbol.CROSS && //col 3 win
            marks[5].symbol() == Symbol.CROSS &&
            marks[8].symbol() == Symbol.CROSS) ||

            (marks[0].symbol() == Symbol.CROSS && //col 2 win
            marks[4].symbol() == Symbol.CROSS &&
            marks[8].symbol() == Symbol.CROSS) ||

            (marks[2].symbol() == Symbol.CROSS && //col 3 win
            marks[4].symbol() == Symbol.CROSS &&
            marks[6].symbol() == Symbol.CROSS);
        return b;
    }

    public boolean circleHasWon() {
        boolean b;
        b = (marks[0].symbol() == Symbol.CIRCLE && //row 1 win
                marks[1].symbol() == Symbol.CIRCLE &&
                marks[2].symbol() == Symbol.CIRCLE) ||

                (marks[3].symbol() == Symbol.CIRCLE && //row 2 win
                        marks[4].symbol() == Symbol.CIRCLE &&
                        marks[5].symbol() == Symbol.CIRCLE) ||

                (marks[6].symbol() == Symbol.CIRCLE && //row 3 win
                        marks[7].symbol() == Symbol.CIRCLE &&
                        marks[8].symbol() == Symbol.CIRCLE) ||

                (marks[0].symbol() == Symbol.CIRCLE && //col 1 win
                        marks[3].symbol() == Symbol.CIRCLE &&
                        marks[6].symbol() == Symbol.CIRCLE) ||

                (marks[1].symbol() == Symbol.CIRCLE && //col 2 win
                        marks[4].symbol() == Symbol.CIRCLE &&
                        marks[7].symbol() == Symbol.CIRCLE) ||

                (marks[2].symbol() == Symbol.CIRCLE && //col 3 win
                        marks[5].symbol() == Symbol.CIRCLE &&
                        marks[8].symbol() == Symbol.CIRCLE) ||

                (marks[0].symbol() == Symbol.CIRCLE && //col 2 win
                        marks[4].symbol() == Symbol.CIRCLE &&
                        marks[8].symbol() == Symbol.CIRCLE) ||

                (marks[2].symbol() == Symbol.CIRCLE && //col 3 win
                        marks[4].symbol() == Symbol.CIRCLE &&
                        marks[6].symbol() == Symbol.CIRCLE);
        return b;
    }

    public boolean isDraw() {
        if (crossHasWon() || circleHasWon()) return false;
        boolean b = true;
        for (Mark mark : this.marks) {
            if (mark.symbol() == Symbol.EMPTY) b = false;
        }
        return b;
    }

    public Symbol getWinner() {
        if (circleHasWon()) return Symbol.CIRCLE;
        if (crossHasWon()) return Symbol.CROSS;
        return Symbol.EMPTY;
    }

    public boolean gameIsOver() {
        return crossHasWon() || circleHasWon() || isDraw();
    }

}

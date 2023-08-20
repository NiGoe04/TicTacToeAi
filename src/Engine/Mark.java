package Engine;

public class Mark {
    private final int index;
    private Symbol symbol;

    public Mark(int index, Symbol symbol) {
        this.index = index;
        this.symbol = symbol;
    }

    public Symbol symbol() {
        return this.symbol;
    }

    public int index() {
        return this.index;
    }

    public void setSymbol(Symbol symbol) {
        this.symbol = symbol;
    }
}

package emailschemafactory;

public class CharRange {
    private final char from;
    private final char to;

    public CharRange(char from, char to) {
        this.from = from;
        this.to = to;
    }

    public char from() {
        return from;
    }

    public char to() {
        return to;
    }

    public boolean contains(char c) {
        return from <= c && c <= to;
    }
}

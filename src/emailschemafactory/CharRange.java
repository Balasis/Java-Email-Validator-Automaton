package emailschemafactory;

public record CharRange(char from, char to) {

    public boolean contains(char c) {
        return from <= c && c <= to;
    }
}

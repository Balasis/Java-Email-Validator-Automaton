package emailschemafactory;

public record CharRange(char from, char to) {
    public CharRange {
        if (from > to) {
            throw new IllegalArgumentException("The 'from' character must be less than or equal to the 'to' character.");
        }
    }
}
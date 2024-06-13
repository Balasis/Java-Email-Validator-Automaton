package emailschemafactory;

import java.util.ArrayList;
import java.util.List;

public class EmailSchema {
    private List<String> domains; // Allowed domains
    private List<Character> disallowedChars; // Characters not allowed in the username
    private List<CharRange> allowedCharRanges; // Ranges of allowed characters for the username

    public EmailSchema() {
        domains = new ArrayList<>();
        disallowedChars = new ArrayList<>();
        allowedCharRanges = new ArrayList<>();
    }


}

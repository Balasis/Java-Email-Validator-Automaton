package emailschemafactory;

import java.util.ArrayList;
import java.util.List;

public class EmailSchema {
    private String emailLabel;
    private List<String> domains; // Allowed domains
    private List<Character> disallowedChars; // Characters not allowed in the username
    private List<CharRange> allowedCharRanges; // Ranges of allowed characters for the username

    public EmailSchema(String emailLabel) {
        this.emailLabel = emailLabel;
        domains = new ArrayList<>();
        disallowedChars = new ArrayList<>();
        allowedCharRanges = new ArrayList<>();
    }

    public String getEmailLabel() {
        return emailLabel;
    }

    public List<String> getDomains() {
        return domains;
    }

    public List<Character> getDisallowedChars() {
        return disallowedChars;
    }

    public List<CharRange> getAllowedCharRanges() {
        return allowedCharRanges;
    }
}

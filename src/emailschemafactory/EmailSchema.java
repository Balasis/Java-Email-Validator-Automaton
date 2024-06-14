package emailschemafactory;

import java.util.ArrayList;
import java.util.List;

public class EmailSchema {
    private String emailLabel;
    private List<String> domains; // Allowed domains
    private List<CharRange> allowedCharRanges; // Ranges of allowed characters for the username

    public EmailSchema(String emailLabel) {
        this.emailLabel = emailLabel;
        domains = new ArrayList<>();
        allowedCharRanges = new ArrayList<>();
    }

    public void addToDomains(String domainStringNoAT){
        domains.add(domainStringNoAT);
    }

    public void allowedCharRanges(CharRange range){
        allowedCharRanges.add(range);
    }

    public String getEmailLabel() {
        return emailLabel;
    }

    public List<String> getDomains() {
        return domains;
    }

    public List<CharRange> getAllowedCharRanges() {
        return allowedCharRanges;
    }

    public String toString(){
        return domains +"" + allowedCharRanges + "" + emailLabel;
    }
}

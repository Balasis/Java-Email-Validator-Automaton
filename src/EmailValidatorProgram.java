
import emailautomatonfactory.EmailAutomaton;
import emailautomatonfactory.EmailAutomatonsMerger;
import emailschemafactory.EmailSchema;
import exceptions.EmailAutCaseMergeException;
import exceptions.InvalidDomainFormException;

import java.util.ArrayList;
import java.util.List;

public class EmailValidatorProgram {
    private final List<EmailAutomaton> automatons;
    private EmailAutomatonsMerger automatonMerger;

    public EmailValidatorProgram() {
        this.automatons = new ArrayList<>();
    }

    // Adds a new automaton for a given email schema
    public void addAutomaton(EmailSchema emailSchema, boolean caseSensitiveDomain) {
        EmailAutomaton automaton = new EmailAutomaton(emailSchema, caseSensitiveDomain);
        automatons.add(automaton);
        updateAutomatonMerger();
    }

    // Checks if an email is valid against all the merged automatons
    public boolean isValidEmail(String email) {
        return automatonMerger != null && automatonMerger.isItAValidEmail(email);
    }

    // Creates a Google email schema and adds its automaton
    public void createGoogleAutomaton() throws InvalidDomainFormException {
        EmailSchema googleSchema = new EmailSchema("gmail.com");
        googleSchema.addAllBasicCharRanges();
        googleSchema.addAllRegularInvalids();
        addAutomaton(googleSchema, false);
    }

    // Creates an Outlook email schema and adds its automaton
    public void createOutlookAutomaton() throws InvalidDomainFormException {
        EmailSchema outlookSchema = new EmailSchema("outlook.com");
        outlookSchema.addAllBasicCharRanges();
        outlookSchema.addAllRegularInvalids();
        addAutomaton(outlookSchema, false);
    }

    // Private helper method to update the automaton merger
    private void updateAutomatonMerger() {
        try {
            automatonMerger = new EmailAutomatonsMerger(automatons);
        } catch (EmailAutCaseMergeException e) {
            System.out.println(e.getMessage());
        }
    }


}


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

    public boolean isValidEmail(String email) {
        return automatonMerger != null && automatonMerger.isItAValidEmail(email);
    }

    public void addAutomaton(EmailSchema emailSchema, boolean caseSensitiveDomain) {
        EmailAutomaton automaton = new EmailAutomaton(emailSchema, caseSensitiveDomain);
        automatons.add(automaton);
        updateAutomatonMerger();
    }

    public void removeAutomaton(int index) {
        if (index >= 0 && index < automatons.size()) {
            automatons.remove(index);
            updateAutomatonMerger();
        } else {
            System.out.println("Invalid index. No automaton removed.");
        }
    }

    public void clearAutomatons() {
        automatons.clear();
        updateAutomatonMerger();
    }



    public void createGoogleAutomaton() throws InvalidDomainFormException {
        EmailSchema googleSchema = new EmailSchema("gmail.com");
        googleSchema.addAllBasicCharRanges();
        googleSchema.addAllRegularInvalids();
        addAutomaton(googleSchema, false);
    }

    public void createOutlookAutomaton() throws InvalidDomainFormException {
        EmailSchema outlookSchema = new EmailSchema("outlook.com");
        outlookSchema.addAllBasicCharRanges();
        outlookSchema.addAllRegularInvalids();
        addAutomaton(outlookSchema, false);
    }

    private void updateAutomatonMerger() {
        try {
            automatonMerger = new EmailAutomatonsMerger(automatons);
        } catch (EmailAutCaseMergeException e) {
            System.out.println(e.getMessage());
        }
    }

}
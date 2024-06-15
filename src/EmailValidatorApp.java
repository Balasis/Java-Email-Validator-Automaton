import emailautomatonfactory.EmailAutCaseMergeException;
import emailautomatonfactory.EmailAutomaton;
import emailautomatonfactory.EmailAutomatonsMerger;
import emailschemafactory.CharRange;
import emailschemafactory.EmailSchema;
import emailschemafactory.InvalidDomainFormException;

import java.util.ArrayList;
import java.util.Arrays;

public class EmailValidatorApp {
    public static void main(String[] args) {
        EmailSchema check = null;
        try {
            check = new EmailSchema("@gmail.com");
          check.addToDomains("@subDomain.gmail.com");
        } catch (InvalidDomainFormException e) {
            throw new RuntimeException(e);
        }
        check.addAllBasicCharRanges();
        check.addAllRegularInvalids();
        EmailAutomaton checking = new EmailAutomaton( check ,true);




        EmailSchema checks = null;
        try {
            checks = new EmailSchema("@outlook.com");
            checks.addToDomains("@domainSub.outlook.com");
        } catch (InvalidDomainFormException e) {
            throw new RuntimeException(e);
        }
        checks.addAllBasicCharRanges();
        checks.addAllRegularInvalids();
        EmailAutomaton checkings = new EmailAutomaton( checks ,true);



        ArrayList<EmailAutomaton> automata = new ArrayList<>();
        automata.add(checking);
        automata.add(checkings);
        try {
            EmailAutomatonsMerger theMerge = new EmailAutomatonsMerger(automata);
            System.out.println(theMerge.isItAValidEmail("giovani1994a@gmail.com"));
        } catch (EmailAutCaseMergeException e) {
            System.out.println(e.getMessage());
        }



    }
}
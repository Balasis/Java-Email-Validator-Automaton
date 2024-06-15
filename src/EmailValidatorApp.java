import emailautomatonfactory.EmailAutomaton;
import emailschemafactory.CharRange;
import emailschemafactory.EmailSchema;
import emailschemafactory.InvalidDomainFormException;

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
        EmailAutomaton checking = new EmailAutomaton( check ,false);
        System.out.println(checking.isItAValidEmail("j_oAA-Ah.n@suBDOmain.gmail.com"));
    }
}
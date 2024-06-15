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
        check.addAllowedCharRanges(new CharRange('A','Z'));
        check.addAllowedCharRanges(new CharRange('a','z'));
        check.addAllowedCharRanges(new CharRange('0','9'));
        check.addAllowedCharRanges(new CharRange('_','_'));
        check.addAllowedCharRanges(new CharRange('.','.'));

        EmailAutomaton checking = new EmailAutomaton( check ,true);

        System.out.println(checking.isItAValidEmail("j_oAAAh.n@suBDOmain.gmail.com"));


    }
}
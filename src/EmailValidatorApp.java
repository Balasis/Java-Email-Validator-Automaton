import emailautomatonfactory.EmailAutomaton;
import emailschemafactory.CharRange;
import emailschemafactory.EmailSchema;
import emailschemafactory.InvalidDomainFormException;

public class EmailValidatorApp {
    public static void main(String[] args) {

        EmailSchema check = null;
        try {
            check = new EmailSchema("@gmail.com");
        } catch (InvalidDomainFormException e) {
            throw new RuntimeException(e);
        }
        check.addToDomains("@subDomain.gmail.com");
        check.allowedCharRanges(new CharRange('A','Z'));
        check.allowedCharRanges(new CharRange('a','z'));
        check.allowedCharRanges(new CharRange('0','9'));
        check.allowedCharRanges(new CharRange('_','_'));
        check.allowedCharRanges(new CharRange('.','.'));

        EmailAutomaton checking = new EmailAutomaton( check ,false);

        System.out.println(checking.isItAValidEmail("j_o23h.n@subdomain.gmail.com"));


    }
}
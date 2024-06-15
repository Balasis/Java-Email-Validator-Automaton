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
        check.allowedCharRanges(new CharRange('A','Z'));
        System.out.println(check);
        System.out.println(" ");

        EmailAutomaton checking = new EmailAutomaton( check );
        System.out.println(" ");

    }
}
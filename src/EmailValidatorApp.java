import emailautomatonfactory.EmailAutomaton;
import emailschemafactory.CharRange;
import emailschemafactory.EmailSchema;

public class EmailValidatorApp {
    public static void main(String[] args) {
        EmailSchema check =new EmailSchema("gmail");
        check.allowedCharRanges(new CharRange('A','Z'));
        System.out.println(check);
        System.out.println(" ");

        EmailAutomaton checking = new EmailAutomaton( check );

    }
}
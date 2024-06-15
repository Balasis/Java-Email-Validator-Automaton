import emailautomatonfactory.EmailAutomaton;
import emailschemafactory.CharRange;
import emailschemafactory.EmailSchema;

public class EmailValidatorApp {
    public static void main(String[] args) {
        EmailSchema check =new EmailSchema("ggi");
        check.allowedCharRanges(new CharRange('A','Z'));
        check.addToDomains("kidd.com");
        System.out.println(check);
        System.out.println(" ");

        EmailAutomaton checking = new EmailAutomaton( check );
        System.out.println(" ");
        System.out.println(checking.getFinalStatesF());
    }
}
import emailschemafactory.CharRange;
import emailschemafactory.EmailSchema;

public class EmailValidatorApp {
    public static void main(String[] args) {
        EmailSchema check =new EmailSchema("gmail");
        check.addToDomains("sigh.gmail.com");
        check.allowedCharRanges(new CharRange('A','Z'));
        System.out.println(check);
    }
}
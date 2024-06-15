import exceptions.InvalidDomainFormException;

public class EmailValidatorApp {
    public static void main(String[] args) {
        try {
            EmailValidatorProgram validatorProgram = new EmailValidatorProgram();
            validatorProgram.createGoogleAutomaton();
            validatorProgram.createOutlookAutomaton();
            // Test the validator
            System.out.println("Is valid email (test@gmail.com): " + validatorProgram.isValidEmail("test@gmail.com"));
            System.out.println("Is valid email (test@outlook.com): " + validatorProgram.isValidEmail("test@outlook.com"));
            System.out.println("Is valid email (test@yahoo.com): " + validatorProgram.isValidEmail("test@yahoo.com"));
        } catch (InvalidDomainFormException e) {
            System.out.println(e.getMessage());
        }
    }
}